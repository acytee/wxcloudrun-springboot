package com.tencent.wxcloudrun.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author chenzg
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 默认异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public ApiResponse exceptionHandler(Exception e) {
        log.error("服务异常：", e);
        String msg = e.getMessage();
        if (e.getCause() != null) {
            msg = e.getCause().getMessage();
            if (e.getCause().getCause() != null) {
                msg = e.getCause().getCause().getMessage();
            }
        }
        boolean b1 = msg.contains("cannot be null") || msg.contains("Cannot add or update a child row") || msg.contains("Unknown column") || msg.contains("Cannot deserialize value of type");
        boolean b2 = msg.contains("Cannot delete or update a parent row");
        boolean b3 = msg.contains("Connection timed out");
        boolean b4 = msg.contains("Data too long for column");
        boolean b5 = msg.contains("returns more than 1 row") || msg.contains("in where clause is ambiguous") || msg.contains("You have an error in your SQL syntax");
        boolean b6 = msg.contains("java.lang.NullPointerException");
        if (b1) {
            msg = "请求参数错误！";
        } else if (b2) {
            msg = "禁止删除！";
        } else if (b3) {
            msg = "服务请求超时！";
        } else if (b4) {
            msg = "输入信息长度超限！";
        } else if (b5) {
            msg = "数据查询异常！";
        } else if (b6) {
            msg = "服务端异常！";
        }
        return ApiResponse.fail(msg);
    }
}
