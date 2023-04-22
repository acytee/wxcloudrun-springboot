package com.tencent.wxcloudrun.dto.common;

import com.tencent.wxcloudrun.utils.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 有日期时间请求body
 *
 * @author : chenzg
 * @date : 2022-07-19 17:18:42
 **/
@Data
@ApiModel(description = "查询", value = "BaseBodyDate")
@EqualsAndHashCode(callSuper = true)
public class BaseBodyDate extends BaseBodyGet {
    @ApiModelProperty(notes = "开始时间", example = "2022-01-01")
    private String sd;
    @ApiModelProperty(notes = "结束时间", example = "2022-12-31")
    private String ed;

    public void appendDateSql(Map<String, Object> mv, StringBuilder aw) {
        appendDateSql(mv, aw, "t1.create_time");
    }

    public void appendDateSql(Map<String, Object> mv, StringBuilder aw, String field) {
        //时间
        if (StrUtil.isNotNull(sd)) {
            aw.append(" and  ").append(field).append(">=:sd");
            mv.put("sd", sd);
            //已经拼接的字段置空
            setSd(null);
        }
        if (StrUtil.isNotNull(ed)) {
            aw.append(" and  ").append(field).append("<=:ed");
            mv.put("ed", ed);
            //已经拼接的字段置空
            setEd(null);
        }
    }
}
