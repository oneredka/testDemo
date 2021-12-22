package com.hong.common.core.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashMap;

/**
 * 返回处理结果
 *
 * @author HongYi@10004580
 * @createTime 2021年05月26日 14:08:00
 */
@SuppressWarnings("serial")
public final class Feedback extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public static final String CODE_TAG = "code";

    public static final String MSG_TAG = "msg";

    public static final String DATA_TAG = "data";

    /**
     * 状态类型
     */
    public enum Type {
        /**
         * 成功
         */
        SUCCESS(0),
        /**
         * 警告
         */
        WARN(301),
        /**
         * 错误
         */
        ERROR(500);
        private final int value;

        Type(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    /**
     * 状态类型
     */
    private Type type;

    /**
     * 状态码
     */
    private int code;

    /**
     * 返回内容
     */
    private String msg;

    /**
     * 数据对象
     */
    private Object data;

    /**
     * 初始化一个新创建的 Feedback 对象，使其表示一个空消息。
     */
    public Feedback() {
    }

    /**
     * 初始化一个新创建的 Feedback 对象
     *
     * @param type 状态类型
     * @param msg  返回内容
     */
    public Feedback(Type type, String msg) {
        this.type = type;
        this.msg = msg;
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 Feedback 对象
     *
     * @param type 状态类型
     * @param msg  返回内容
     * @param data 数据对象
     */
    public Feedback(Type type, String msg, Object data) {
        this.type = type;
        this.code = type.value;
        this.msg = msg;
        this.data = data;
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
        if (null != data) {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static Feedback success() {
        return Feedback.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static Feedback success(Object data) {
        return Feedback.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static Feedback success(String msg) {
        return Feedback.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static Feedback success(String msg, Object data) {
        return new Feedback(Type.SUCCESS, msg, data);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static Feedback warn(String msg) {
        return Feedback.warn(msg, null);
    }

    /**
     * 返回警告消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static Feedback warn(String msg, Object data) {
        return new Feedback(Type.WARN, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static Feedback error() {
        return Feedback.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static Feedback error(String msg) {
        return Feedback.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static Feedback error(String msg, Object data) {
        return new Feedback(Type.ERROR, msg, data);
    }

    /**
     * 返回结果是否成功
     *
     * @return
     */
    public boolean succeed() {
        return String.valueOf(Type.SUCCESS.value).equals(this.get(CODE_TAG).toString());
    }

    /**
     * 获取消息
     *
     * @return
     */
    public String gainMsg() {
        return this.get(MSG_TAG).toString();
    }

    /**
     * 获取状态
     *
     * @return
     */
    public Integer gainCode() {
        return Integer.parseInt(this.get(CODE_TAG).toString());
    }

    /**
     * 获取数据
     *
     * @return
     */
    public Object gainData() {
        return this.get(DATA_TAG);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
        super.put(CODE_TAG, type.value);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
        super.put(MSG_TAG, msg);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
        super.put(DATA_TAG, data);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("code", getCode())
                .append("msg", getMsg())
                .append("data", getData())
                .toString();
    }
}
