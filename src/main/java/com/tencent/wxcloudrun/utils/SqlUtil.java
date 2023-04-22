package com.tencent.wxcloudrun.utils;


import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.common.BaseBodyDetail;
import com.tencent.wxcloudrun.dto.common.BaseBodyGet;
import com.tencent.wxcloudrun.dto.common.PublicCodeEnum;
import com.tencent.wxcloudrun.dto.common.ResultList;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class SqlUtil {
    @Autowired
    private EntityManager entityManager;
    public final static Integer FLAG_NO_FIELD = -1;
    private final String PATTERN_FIELD = "[A-Z]";
    private final String PAGE_NUMBER = "pageNumber";
    private final String PAGE_SIZE = "pageSize";
    private final String ID = "id";
    private final String CREATE_USERID = "createUserId";
    private final String CREATE_TIME = "createTime";
    private final String UPDATE_USERID = "updateUserId";
    private final String UPDATE_TIME = "updateTime";
    private final String PK_ID_FIELD_NAME = "pkIdFieldName";


    /**
     * 详情
     *
     * @param sqlStr      不带条件的完整sql语句
     * @param where       查询条件
     * @param whereKeys   可以为空，前端使用对象 BaseBodyGet，数据库字段不匹配或多个表有相同字段情况添加映射关系
     * @param whereValues 可以为空，where条件参数值
     * @param appendWhere 可以为空，扩展条件，如：exists等复杂where条件
     */
    public ApiResponse detail(String sqlStr, BaseBodyDetail where, Map<String, String> whereKeys, Map<String, Object> whereValues, String appendWhere) throws Exception {
        if (where == null || StrUtil.isNull(where.getId())) {
            throw new Exception("记录ID不能为空");
        }
        where.setPageSize(SqlUtil.FLAG_NO_FIELD);
        List list = list(sqlStr, where, whereKeys, whereValues, appendWhere).getData();
        if (list == null || list.size() == 0) {
            throw new Exception(ApiResponse.failNoData().getMessage());
        }
        return ApiResponse.success(list.get(0));
    }

    /**
     * 分页，特殊处理排序，jap排序限制子查询不能有排序：QueryUtils#applySorting
     *
     * @param sqlStr      不带条件的完整sql语句
     * @param where       查询条件
     * @param whereKeys   可以为空，前端使用对象 BaseBodyGet，数据库字段不匹配或多个表有相同字段情况添加映射关系
     * @param whereValues 可以为空，where条件参数值
     * @param appendWhere 可以为空，扩展条件，如：exists等复杂where条件
     */
    public ResultList list(String sqlStr, BaseBodyGet where, Map<String, String> whereKeys, Map<String, Object> whereValues, String appendWhere) {
        if (where == null) {
            where = new BaseBodyGet();
        }
        if (whereKeys == null) {
            whereKeys = new HashMap<>();
        }
        if (whereValues == null) {
            whereValues = new HashMap<>();
        }
        if (StrUtil.isNull(where.getDeleteStatus())) {
            where.setDeleteStatus(PublicCodeEnum.DELETE_STATUS_0.getCode());
        }
        //表没有删除状态字段
        if (where.getDeleteStatus().equals(FLAG_NO_FIELD)) {
            where.setDeleteStatus(null);
            whereValues.remove("deleteStatus");
        }
        sqlStr = sqlStr.replace("\n", " ");
        int from = sqlStr.toLowerCase().lastIndexOf(" from ");
        String sql1 = sqlStr.substring(0, from);
        String sql2 = sqlStr.substring(from);
        StringBuilder sql = new StringBuilder(sql2);
        boolean hasWhere = sql.toString().toLowerCase().contains(" where ");
        if (!hasWhere) {
            sql.append(" where 1=1 ");
        }
        List<Field> fieldList = new ArrayList<>();
        Class tempClass = where.getClass();
        //当父类为null的时候说明到达了最上层的父类(Object类).
        while (tempClass != null) {
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            //得到父类,然后赋给自己
            tempClass = tempClass.getSuperclass();
        }
        for (Field field : fieldList) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = null;
            try {
                value = field.get(where);
            } catch (IllegalAccessException e) {
                log.error(name + "属性值获取异常!");
            }
            if (ignoreField(name, value)) {
                continue;
            }
            if (StrUtil.equals(name, ID)) {
                //数据库主键名不为id
                String tn = sqlStr.substring(from + 6).trim();
                tn = tn.split(" ")[0];
                name = pkId(tn, where.getPkIdFieldName());
            }

            //字段映射
            String mappingField = whereKeys.get(name);
            String str = !StrUtil.isNull(mappingField) ? mappingField : paramToField(name);
            sql.append(" and ").append(str);
            //模糊查询
            if (where.getLikeFieldName().contains(str)) {
                sql.append(" like ");
                value = "%" + value + "%";
            } else {
                sql.append(" = ");
            }
            sql.append(" :").append(name).append(" ");
            whereValues.put(name, value);
        }
        //追加特殊where条件
        if (!StrUtil.isNull(appendWhere)) {
            sql.append(appendWhere);
        }
        int count = 0;
        Set<String> keys = whereValues.keySet();
        Session session = entityManager.unwrap(Session.class);
        StringBuilder querySql = new StringBuilder();
        querySql.append(sql1).append(sql);
        //排序
        if (StrUtil.isNotNull(where.getOrderBy())) {
            querySql.append(" ").append(where.getOrderBy());
        }
        //分页
        Integer size = where.getPageSize();
        boolean isPage = size != null && size > 0;
        if (isPage) {
            //计算总数
            NativeQuery c = session.createSQLQuery("select count(*) " + sql);
            for (String key : keys) {
                //点位符值不在where条件情况
                if (sql.toString().contains(":" + key)) {
                    Object obj = whereValues.get(key);
                    if (obj instanceof Date) {
                        obj = DateUtil.dateString1((Date) obj);
                    }
                    c.setParameter(key, obj);
                }
            }
            count = Integer.parseInt(c.uniqueResult() + "");
            //分页
            long idx = where.getPageNumber();
            if (idx < 1) {
                idx = 1;
            }
            querySql.append(" limit :").append(PAGE_NUMBER).append(",:").append(PAGE_SIZE);
            whereValues.put(PAGE_NUMBER, (idx - 1) * size);
            whereValues.put(PAGE_SIZE, size);
        }
        NativeQuery query = session.createNativeQuery(querySql.toString());
        for (String key : keys) {
            Object obj = whereValues.get(key);
            if (obj instanceof Date) {
                obj = DateUtil.dateString1((Date) obj);
            }
            query.setParameter(key, obj);
        }
        //返回Map
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List data = query.getResultList();
        if (!isPage) {
            count = data.size();
        }
        return new ResultList(count, data);
    }

    /**
     * 将驼峰转为下划线
     *
     * @param str
     * @return
     */
    private String paramToField(String str) {
        Pattern compile = Pattern.compile(PATTERN_FIELD);
        Matcher matcher = compile.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 忽略字段
     *
     * @param name  字段名
     * @param value 字段值
     * @return
     */
    private boolean ignoreField(String name, Object value) {
        boolean pageNumber = StrUtil.equals(name, PAGE_NUMBER);
        boolean pageSize = StrUtil.equals(name, PAGE_SIZE);
        boolean orderBy = StrUtil.equals(name, "orderBy");
        boolean likeFieldName = StrUtil.equals(name, "likeFieldName");
        boolean createUserId = StrUtil.equals(name, "createUserId");
        boolean pkIdName = StrUtil.equals(name, PK_ID_FIELD_NAME);
        return StrUtil.isNull(name) || StrUtil.isNull(value) || StrUtil.equals(name, "null") || pkIdName ||

                StrUtil.equals(value + "", "null") || name.startsWith("__$") || orderBy || likeFieldName || pageNumber || pageSize || createUserId;
    }

    private String pkId(String tableName, String customValue) {
        return StrUtil.isNotNull(customValue) ? customValue : tableName.substring(tableName.lastIndexOf("_") + 1) + "_id";
    }

}
