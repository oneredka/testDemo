package com.icarus.demo.common;

public enum MsgEnum {
    SUCCESS(0, "SUCCESS"),
    FAIL(-1, "FAIL");

    final Integer errorCode;
    final String msg;

    private MsgEnum(Integer errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }

    public Integer getCode() {
        return this.errorCode;
    }

    public String getMsg() {
        return this.msg;
    }
}
