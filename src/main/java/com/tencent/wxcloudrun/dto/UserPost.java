package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.model.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : chenzg
 * @date : 2023-04-23 17:20:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户信息", value = "UserPost")
public class UserPost extends User {
}