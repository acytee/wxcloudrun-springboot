package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.dto.common.BaseBody;
import com.tencent.wxcloudrun.model.Parking;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : chenzg
 * @date : 2023-04-23 17:20:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "车位信息", value = "ParkingPut")
public class ParkingPut extends Parking {
}