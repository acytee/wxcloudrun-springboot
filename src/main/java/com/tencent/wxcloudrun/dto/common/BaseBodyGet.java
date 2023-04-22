package com.tencent.wxcloudrun.dto.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建企业账户请求body
 *
 * @author : chenzg
 * @date : 2022-07-19 17:18:42
 **/
@Data
@ApiModel(description = "分页", value = "BaseBodyGet")
@EqualsAndHashCode(callSuper = true)
public class BaseBodyGet extends BaseBody {
    @ApiModelProperty(notes = "分页", example = "1")
    private Integer pageNumber = 1;
    @ApiModelProperty(notes = "分页大小", example = "10")
    private Integer pageSize = 10;
    @ApiModelProperty(notes = "排序规则(如：staff_id asc,user_id desc)", hidden = true, example = "id asc,name desc")
    private String orderBy;
    @ApiModelProperty(notes = "模糊查询字段", hidden = true, example = "[]")
    private List<String> likeFieldName = new ArrayList<>();
    @ApiModelProperty(notes = "删除状态，备用", hidden = true, example = "1")
    private Integer deleteStatus;
    @ApiModelProperty(notes = "表主键名称（表主键名不是id指定）", hidden = true, example = "id")
    private String pkIdFieldName;
}
