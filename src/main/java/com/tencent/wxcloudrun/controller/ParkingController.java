package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.dto.ParkingPost;
import com.tencent.wxcloudrun.dto.ParkingPut;
import com.tencent.wxcloudrun.dto.ParkingGet;
import com.tencent.wxcloudrun.service.ParkingService;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.common.JsonPage;
import com.tencent.wxcloudrun.dto.common.BaseBodyDelete;
import com.tencent.wxcloudrun.dto.common.BaseBodyDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 车位信息
 *
 * @author : chenzg
 * @date : 2023-04-23 17:20:21
 */
@Api(tags = {"车位信息"})
@RequestMapping("/parking")
@RestController
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @ApiOperation("创建")
    @PostMapping
    public ApiResponse post(@RequestBody ParkingPost body) {
        return parkingService.post(body);
    }

    @ApiOperation("删除")
    @DeleteMapping
    public ApiResponse delete(@RequestBody BaseBodyDelete body) {
        return parkingService.delete(body);
    }

    @ApiOperation("修改")
    @PutMapping
    public ApiResponse put(@RequestBody ParkingPut body) {
        return parkingService.put(body);
    }

    @ApiOperation("列表")
    @PostMapping("/get")
    public ApiResponse get(@RequestBody ParkingGet body) {
        return ApiResponse.success(new JsonPage(parkingService.get(body), body));
    }

    @ApiOperation("详情")
    @PostMapping("/detail")
    public ApiResponse detail(@RequestBody BaseBodyDetail body) throws Exception {
        return parkingService.detail(body);
    }
}