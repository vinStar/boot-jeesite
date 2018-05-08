package com.thinkgem.jeesite.common.comEnum;

/**
 * Created by vin on 07/05/2018.
 * 返回状态枚举类，
 * eg 200，成功
 */
public enum ResultStatus {
    SUCCESS(200, "成功"),
    FAIL(-200,"失败"),
    USERNAME_OR_PASSWORD_ERROR(-1001, "用户名或密码错误"),
    USER_NOT_FOUND(-1002, "用户不存在"),
    USER_NOT_LOGIN(-1003, "用户未登录"),
    DATA_NOT_NULL(-1004, "字段不能为空");


    /**
     * 返回码
     */
    private int code;
    /**
     * 返回结果描述
     */
    private String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
