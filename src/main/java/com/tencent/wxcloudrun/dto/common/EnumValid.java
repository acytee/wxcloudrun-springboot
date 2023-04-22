package com.tencent.wxcloudrun.dto.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 检验接口传参是否为设置的枚举值
 *
 * @author : chenzg
 * @date : 2023-03-03 14:42:53
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValid {
    /**
     * 枚举类名
     */
    String enumName() default "PublicCodeEnum";

    /**
     * 枚举名称前缀 默认启用、禁用状态
     */
    String prefix() default "DATA_STATUS_";
}
