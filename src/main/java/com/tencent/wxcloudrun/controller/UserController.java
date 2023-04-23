package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.dto.UserPost;
import com.tencent.wxcloudrun.dto.UserPut;
import com.tencent.wxcloudrun.dto.UserGet;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.common.JsonPage;
import com.tencent.wxcloudrun.dto.common.BaseBodyDelete;
import com.tencent.wxcloudrun.dto.common.BaseBodyDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息
 *
 * @author : chenzg
 * @date : 2023-04-23 17:20:22
 */
@Api(tags = {"用户信息"})
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("创建")
    @PostMapping
    public ApiResponse post(@RequestBody UserPost body) {
        return userService.post(body);
    }

    @ApiOperation("删除")
    @DeleteMapping
    public ApiResponse delete(@RequestBody BaseBodyDelete body) {
        return userService.delete(body);
    }

    @ApiOperation("修改")
    @PutMapping
    public ApiResponse put(@RequestBody UserPut body) {
        return userService.put(body);
    }

    @ApiOperation("列表")
    @PostMapping("/get")
    public ApiResponse get(@RequestBody UserGet body) {
        return ApiResponse.success(new JsonPage(userService.get(body), body));
    }

    @ApiOperation("详情")
    @PostMapping("/detail")
    public ApiResponse detail(@RequestBody BaseBodyDetail body) throws Exception {
        return userService.detail(body);
    }
}