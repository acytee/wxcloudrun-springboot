package com.tencent.wxcloudrun.dto.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : chenzg
 * @date : 2022-08-22 11:16:09
 **/
@Data
@ApiModel(description = "删除基类", value = "BaseBodyDelete")
@EqualsAndHashCode(callSuper = true)
public class BaseBodyDelete extends BaseBodyDetail {
    @ApiModelProperty(notes = "确认删除 （1确认删除提示，首次提交不传，相同参数确认删除传1）", example = "0")
    private Integer confirm;
}
