package com.icarus.demo.common;

import java.io.Serializable;
import java.util.Objects;


import com.icarus.demo.common.MsgEnum;
import lombok.Data;

/**
 * Wrap return result of API
 *
 * @author Caden
 * @since 2022-04-08 09:43:10
 */
@Data
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    private Integer code;
    private String message;
    private String module;
    private T data;

    public ApiResult(String module) {
        this.code = MsgEnum.SUCCESS.getCode();
        this.message = MsgEnum.SUCCESS.getMsg();
        this.module = module;
    }

    public boolean whetherSuccess() {
        return Objects.equals(MsgEnum.SUCCESS.getCode(), this.code);
    }

    private ApiResult<T> setError(final Integer code, final String message) {
        this.code = code;
        this.message = message;
        return this;
    }

    public static <T> ApiResult<T> build(final String module) {
        return new ApiResult<>(module);
    }

    public static <T> ApiResult<T> build(final T data, final String module) {
        ApiResult<T> result = new ApiResult<>(module);
        result.setData(data);
        return result;
    }

    public static <T> ApiResult<T> build(final Integer code, final String message, final String module) {
        ApiResult<T> result = new ApiResult<>(module);
        result.setError(code, message);
        return result;
    }

    public static <T> ApiResult<T> build(boolean flag, final String module) {
        ApiResult<T> result = new ApiResult<>(module);
        return flag ? result : result.setError(MsgEnum.FAIL.getCode(), MsgEnum.FAIL.getMsg());
    }

    public static <T> ApiResult<T> build(final Integer code, final String message, final T data, final String module) {
        ApiResult<T> result = new ApiResult<>(module);
        result.setError(code, message);
        result.setData(data);
        return result;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", module='" + module + '\'' +
                ", data=" + data +
                '}';
    }
}
