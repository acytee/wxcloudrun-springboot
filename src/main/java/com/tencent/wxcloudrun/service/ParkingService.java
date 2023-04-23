package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.common.BaseBodyDelete;
import com.tencent.wxcloudrun.dto.common.BaseBodyDetail;
import com.tencent.wxcloudrun.dto.common.ResultList;
import com.tencent.wxcloudrun.utils.SqlUtil;
import com.tencent.wxcloudrun.utils.StrUtil;
import com.tencent.wxcloudrun.dao.ParkingRepository;
import com.tencent.wxcloudrun.model.Parking;
import com.tencent.wxcloudrun.dto.ParkingGet;
import com.tencent.wxcloudrun.dto.ParkingPost;
import com.tencent.wxcloudrun.dto.ParkingPut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

/**
 * 车位信息
 *
 * @author : chenzg
 * @date : 2023-04-23 17:20:21
 */
@Service
public class ParkingService {

    @Autowired
    private SqlUtil sqlUtil;
    @Autowired
    private ParkingRepository parkingRepository;

    @Transactional(rollbackOn = Exception.class)
    public ApiResponse post(ParkingPost body) {
        Parking obj = new Parking();
        BeanUtils.copyProperties(body, obj, StrUtil.getNullPropertyNames(body));
        String id = parkingRepository.save(obj).getParkingId();
        return ApiResponse.success(id);
    }

    @Transactional(rollbackOn = Exception.class)
    public ApiResponse delete(BaseBodyDelete body) {
        Optional<Parking> optional = parkingRepository.findById(body.getId());
        if (!optional.isPresent()) {
            return ApiResponse.failNoData();
        }
        parkingRepository.deleteByParkingId(Arrays.asList(body.getId().split(",")));
        return ApiResponse.success();
    }

    @Transactional(rollbackOn = Exception.class)
    public ApiResponse put(ParkingPut body) {
        Optional<Parking> optional = parkingRepository.findById(body.getParkingId());
        if (!optional.isPresent()) {
            return ApiResponse.failNoData();
        }
        Parking obj = optional.get();
        BeanUtils.copyProperties(body, obj, StrUtil.getNullPropertyNames(body));
        return ApiResponse.success();
    }

    public ResultList get(ParkingGet body) {
        String sql = "select t1.parkingId,t1.type,t1.status,t1.color,t1.browsers,t1.rent," +

                "t1.address,t1.longitude,t1.latitude,t1.week,t1.st,t1.et," +

                "t1.createUserId,t1.createTime " +
                
                "from pt_parking t1";
        body.setDeleteStatus(SqlUtil.FLAG_NO_FIELD);
        return sqlUtil.get(sql, body, null, null, null);
    }

    public ApiResponse detail(BaseBodyDetail body) throws Exception {
        String sql = "select t1.parkingId,t1.type,t1.status,t1.color,t1.browsers,t1.rent," +

                "t1.address,t1.longitude,t1.latitude,t1.week,t1.st,t1.et," +

                "t1.createUserId,t1.createTime " +
                
                "from pt_parking t1";
        body.setDeleteStatus(SqlUtil.FLAG_NO_FIELD);
        return sqlUtil.detail(sql, body, null, null, null);
    }
}