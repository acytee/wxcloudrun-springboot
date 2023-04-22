package com.tencent.wxcloudrun.dto.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : chenzg
 * @date : 2022-07-25 13:58:42
 **/
@Data
@ApiModel(description = "请求基类", value = "BaseBody")
public class BaseBody implements IBody {
    @ApiModelProperty(notes = "当前用户", hidden = true)
    private String createUserId;
}
