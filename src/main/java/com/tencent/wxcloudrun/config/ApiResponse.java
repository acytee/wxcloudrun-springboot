package com.tencent.wxcloudrun.config;

import com.tencent.wxcloudrun.utils.StrUtil;
import lombok.Data;

@Data
public final class ApiResponse {
    public final static class Codes {
        /**
         * 成功
         */
        public final static String SUCCESS = "SUCCESS";

        /**
         * 失败
         */
        public final static String FAIL = "FAIL";
    }

    private String code;
    private String message;
    //日志ID 用于需要手动添加日志
    private String ol;
    private Object content;

    public ApiResponse() {
    }

    public ApiResponse(String code, String message, Object content) {
        this.setCode(code);
        this.setMessage(message);
        this.setContent(content);
    }

    public ApiResponse(String code, String message, Object content, String ol) {
        this.setCode(code);
        this.setMessage(message);
        this.setContent(content);
        this.setOl(ol);
    }

    public static ApiResponse success(Object content) {
        return new ApiResponse(Codes.SUCCESS, null, content);
    }

    public static ApiResponse success(String message, Object content) {
        return new ApiResponse(Codes.SUCCESS, message, content);
    }


    public static ApiResponse success(String message, Object content, String ol) {
        return new ApiResponse(Codes.SUCCESS, message, content, ol);
    }

    public static ApiResponse fail(String message, Object content, String ol) {
        return new ApiResponse(Codes.FAIL, message, content, ol);
    }

    public static ApiResponse message(String message) {
        return new ApiResponse(Codes.SUCCESS, message, null);
    }

    public static ApiResponse success() {
        return success(null);
    }

    public static ApiResponse fail(String message, Object content) {
        return new ApiResponse(Codes.FAIL, message, content);
    }

    public static ApiResponse fail(String message) {
        return new ApiResponse(Codes.FAIL, message, null);
    }

    public static ApiResponse fail() {
        return new ApiResponse(Codes.FAIL, null, null);
    }

    public static ApiResponse failNoData() {
        return failNoData(null);
    }

    public static ApiResponse failNoData(String name) {
        String msg = StrUtil.isNull(name) ? "" : name;
        msg += "数据不存在！";
        return new ApiResponse(Codes.FAIL, msg, null);
    }

}
