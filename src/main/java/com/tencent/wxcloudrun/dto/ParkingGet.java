package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.dto.common.BaseBodyGet;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : chenzg
 * @date : 2023-04-23 17:20:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "车位信息", value = "ParkingGet")
public class ParkingGet extends BaseBodyGet {
    public ParkingGet() {
        this.setOrderBy("order by t1.create_time desc");
    }
}