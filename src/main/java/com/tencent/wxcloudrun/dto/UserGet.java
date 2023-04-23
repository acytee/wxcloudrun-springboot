package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.dto.common.BaseBodyGet;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : chenzg
 * @date : 2023-04-23 17:20:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户信息", value = "UserGet")
public class UserGet extends BaseBodyGet {
    public UserGet() {
        this.setOrderBy("order by t1.create_time desc");
    }
}