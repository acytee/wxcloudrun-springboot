package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.common.BaseBodyDelete;
import com.tencent.wxcloudrun.dto.common.BaseBodyDetail;
import com.tencent.wxcloudrun.dto.common.ResultList;
import com.tencent.wxcloudrun.utils.SqlUtil;
import com.tencent.wxcloudrun.utils.StrUtil;
import com.tencent.wxcloudrun.dao.UserRepository;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.dto.UserGet;
import com.tencent.wxcloudrun.dto.UserPost;
import com.tencent.wxcloudrun.dto.UserPut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

/**
 * 用户信息
 *
 * @author : chenzg
 * @date : 2023-04-23 17:20:22
 */
@Service
public class UserService {

    @Autowired
    private SqlUtil sqlUtil;
    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackOn = Exception.class)
    public ApiResponse post(UserPost body) {
        User obj = new User();
        BeanUtils.copyProperties(body, obj, StrUtil.getNullPropertyNames(body));
        String id = userRepository.save(obj).getUserId();
        return ApiResponse.success(id);
    }

    @Transactional(rollbackOn = Exception.class)
    public ApiResponse delete(BaseBodyDelete body) {
        Optional<User> optional = userRepository.findById(body.getId());
        if (!optional.isPresent()) {
            return ApiResponse.failNoData();
        }
        userRepository.deleteByUserId(Arrays.asList(body.getId().split(",")));
        return ApiResponse.success();
    }

    @Transactional(rollbackOn = Exception.class)
    public ApiResponse put(UserPut body) {
        Optional<User> optional = userRepository.findById(body.getUserId());
        if (!optional.isPresent()) {
            return ApiResponse.failNoData();
        }
        User obj = optional.get();
        BeanUtils.copyProperties(body, obj, StrUtil.getNullPropertyNames(body));
        return ApiResponse.success();
    }

    public ResultList get(UserGet body) {
        String sql = "select t1.userId,t1.unionId,t1.openId,t1.nickName,t1.avatarUrl,t1.mobile," +

                "t1.createTime " +
                
                "from pt_user t1";
        body.setDeleteStatus(SqlUtil.FLAG_NO_FIELD);
        return sqlUtil.get(sql, body, null, null, null);
    }

    public ApiResponse detail(BaseBodyDetail body) throws Exception {
        String sql = "select t1.userId,t1.unionId,t1.openId,t1.nickName,t1.avatarUrl,t1.mobile," +

                "t1.createTime " +
                
                "from pt_user t1";
        body.setDeleteStatus(SqlUtil.FLAG_NO_FIELD);
        return sqlUtil.detail(sql, body, null, null, null);
    }
}