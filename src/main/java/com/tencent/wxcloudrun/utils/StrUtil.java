package com.tencent.wxcloudrun.utils;

import com.tencent.wxcloudrun.dto.common.EnumValid;
import com.tencent.wxcloudrun.dto.common.PublicCodeEnum;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * 杂项工具类
 */
public class StrUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static boolean equals(Object s1, Object s2) {
        return (String.valueOf(s1)).equals(String.valueOf(s2));
    }

    public static boolean equalsIgnoreCase(Object s1, Object s2) {
        return (String.valueOf(s1)).equalsIgnoreCase((String.valueOf(s2)));
    }

    public static boolean isNull(Object... objects) {
        for (Object obj : objects) {
            String str = String.valueOf(obj);
            boolean bool = equals("null", str) || str.trim().length() == 0;
            if (!bool) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotNull(Object... objects) {
        for (Object obj : objects) {
            String str = String.valueOf(obj);
            boolean bool = isNull(str);
            if (bool) {
                return false;
            }
        }
        return true;
    }

    /**
     * 校验@ApiModelProperty修饰的实体非空字段
     *
     * @param obj
     * @throws Exception
     */
    public static void check(Object obj) throws Exception {
        List<Field> fieldList = new ArrayList<>();
        Class tempClass = obj.getClass();
        //当父类为null的时候说明到达了最上层的父类(Object类).
        Set<String> sets = new HashSet<>();
        while (tempClass != null) {
            Field[] fields = tempClass.getDeclaredFields();
            for (Field field : fields) {
                if (sets.add(field.getName())) {
                    fieldList.add(field);
                }
            }
            //得到父类,然后赋给自己
            tempClass = tempClass.getSuperclass();
        }
        for (Field field : fieldList) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
            }
            //不为空字段校验
            ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);
            if (property != null) {
                boolean required = property.required();
                boolean hidden = property.hidden();
                boolean isNull = StrUtil.isNull(value);
                String notes = property.notes() + "";
                notes = notes.split(":")[0];
                notes = notes.split("：")[0];
                notes = notes.split("\\(")[0];
                notes = notes.split("（")[0];
                notes = notes.split(" ")[0];
                if (isNull) {
                    field.set(obj, null);
                }
                boolean bool = required && !hidden && (isNull || equals((value + "").replace(" ", ""), "[]"));
                if (bool) {
                    throw new Exception(notes + "不能为空！");
                }
                //检验接口传参是否为设置的枚举值
                EnumValid enumValid = field.getAnnotation(EnumValid.class);
                if (!isNull && StrUtil.isNotNull(enumValid) && equals(enumValid.enumName(), "PublicCodeEnum")) {
                    PublicCodeEnum.codeCheck(field.getName(), enumValid.prefix(), value);
                }
            }
        }
    }

    /**
     * 获取对象空属性
     *
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source, String... field) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet();
        for (java.beans.PropertyDescriptor pd : pds) {
            try {
                Object srcValue = src.getPropertyValue(pd.getName());
                if (StrUtil.isNull(srcValue)) {
                    emptyNames.add(pd.getName());
                }
            } catch (Exception e) {
                emptyNames.add(pd.getName());
            }
        }
        if (field != null && field.length > 0) {
            emptyNames.addAll(Arrays.asList(field));
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * @param src
     * @param start
     * @param end
     * @param replaceChar
     * @return 150****8877
     */
    public static String replaceChar(String src, int start, int end, String replaceChar) {
        if (isNull(src)) {
            return "";
        }
        char[] cs = src.toCharArray();
        StringBuilder str = new StringBuilder();
        boolean bool = false;
        for (int i = 0; i < cs.length; i++) {
            if (i >= start && i <= end) {
                str.append(replaceChar);
                bool = true;
            } else {
                str.append(cs[i]);
            }
        }
        if (!bool) {
            return new StringBuilder(src).replace(1, src.length(), replaceChar).toString();
        }
        return str.toString();
    }

    public static long priceFormat(String s1) {
        if (isNull(s1)) {
            return 0;
        }
        BigDecimal a1 = new BigDecimal(s1);
        BigDecimal b1 = new BigDecimal(100);
        BigDecimal result = a1.multiply(b1);
        BigDecimal one = new BigDecimal(1);
        return result.divide(one, 1, BigDecimal.ROUND_HALF_UP).longValue();
    }

    public static void main(String[] args) {
        Object s1=null;
        String s2="";
        System.out.println(s1.equals(s2));
    }
}
