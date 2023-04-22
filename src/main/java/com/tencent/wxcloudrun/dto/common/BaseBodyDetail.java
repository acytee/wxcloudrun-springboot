package com.tencent.wxcloudrun.dto.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理端不需要 enterpriseId
 * 创建企业账户请求body
 *
 * @author : chenzg
 * @date : 2022-07-19 17:18:42
 **/
@Data
@ApiModel(description = "详情", value = "BaseBodyDetail")
@EqualsAndHashCode(callSuper = true)
public class BaseBodyDetail extends BaseBodyGet {
    @ApiModelProperty(notes = "主键ID", required = true, example = "8940714a1cd1415d826b234sefa22sas")
    private String id;
    @ApiModelProperty(notes = "分页", hidden = true, example = "1")
    private Integer pageNumber = 1;
    @ApiModelProperty(notes = "分页大小", hidden = true, example = "10")
    private Integer pageSize = 10;

    public BaseBodyDetail() {
    }

    public BaseBodyDetail(String id) {
        this.id = id;
    }
}
