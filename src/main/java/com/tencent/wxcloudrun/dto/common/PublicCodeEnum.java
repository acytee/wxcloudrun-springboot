package com.tencent.wxcloudrun.dto.common;

/**
 * 公共处理 枚举
 */
public enum PublicCodeEnum {

    //数据状态
    STATUS_0(0, "停用"),
    STATUS_1(1, "启用"),

    //数据删除状态,
    DELETE_STATUS_0(0, "正常"),
    DELETE_STATUS_1(1, "删除"),
    TOP_LEVEL(0, "1级");


    private final Integer code;

    private final String message;

    PublicCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 通过名称校验code是否存在
     *
     * @param prefix
     * @param value
     */
    public static void codeCheck(String fieldName, String prefix, Object value) throws Exception {
        boolean bool = false;
        for (PublicCodeEnum codeEnum : PublicCodeEnum.values()) {
            bool = codeEnum.name().startsWith(prefix) && codeEnum.getCode().equals(value);
            if (bool) {
                break;
            }
        }
        if (!bool) {
            throw new Exception("无法识别的参数(" + fieldName + ")值：" + value);
        }
    }

}
