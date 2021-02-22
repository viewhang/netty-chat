package com.ethan.nettychat.common;

import java.util.HashMap;

/**
 * 返回值定义
 */
public class Result<T> {
    /**
     * 成功的返回码为 0
     */
    private static int CODE_SUCCESS = 0;
    /**
     * 返回码
     */
    private int code;
    /**
     * 返回消息
     */
    private String message;
    /**
     * 数据
     */
    private T data;

    private Integer count;

    private Result() {
    }

    /**
     * 构造函数
     *
     * @param code
     * @param message
     */
    private Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数
     *
     * @param code
     * @param message
     * @param data
     */
    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 构造函数
     *
     * @param count
     * @param data
     */
    private Result(int count, T data) {
        this.count = count;
        this.data = data;
    }

    /**
     * 成功
     *
     * @return 成功时返回码为0
     */
    public static Result success() {
        return new Result(CODE_SUCCESS, "");
    }

    /**
     * 成功
     *
     * @param message
     * @return 成功时返回码为0
     */
    public static Result success(String message) {
        return new Result(CODE_SUCCESS, message);
    }

    /**
     * 成功
     *
     * @param message
     * @param data
     * @return 成功时返回码为0
     */
    public static Result success(String message, Object data) {
        return new Result(CODE_SUCCESS, message, data);
    }

    /**
     * 成功
     *
     * @param data
     * @return 成功时返回码为0
     */
    public static Result successData(Object data) {
        return new Result(CODE_SUCCESS, "", data);
    }

    /**
     * 分页 成功
     *
     * @param data
     * @return 成功时返回码为0
     */
    public static Result successPage(Object data, Integer count) {
        return new Result(count, data);
    }

    /**
     * 失败
     *
     * @param message 错误信息，默认错误码为1
     */
    public static Result error(String message) {

        return new Result(1, message);
    }

    /**
     * 失败
     *
     * @param code 返回码，由于默认0为成功时的返回码，如此参数传入0将自动替换为1
     */
    public static Result error(int code) {
        if (code == CODE_SUCCESS)
            code = 1;
        return new Result(code, "");
    }

    /**
     * 失败
     *
     * @param code    返回码，由于默认0为成功时的返回码，如此参数传入0将自动替换为1
     * @param message
     */
    public static Result error(int code, String message) {
        if (code == CODE_SUCCESS)
            code = 1;
        return new Result(code, message);
    }

    /**
     * 失败
     *
     * @param code    返回码，由于默认0为成功时的返回码，如此参数传入0将自动替换为1
     * @param message
     * @param data
     */
    public static Result error(int code, String message, Object data) {
        if (code == CODE_SUCCESS)
            code = 1;
        return new Result(code, message, data);
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Integer getCount() {
        return count;
    }

    /**
     * 是否成功
     */
    public boolean isSuccess() {
        return code == CODE_SUCCESS;
    }

    /**
     * 转换为map，便于动态添加属性
     *
     * @return
     */
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("message", message);
        map.put("data", data);
        map.put("success", isSuccess());
        if (count != null) {
            map.put("count", count);
        }
        return map;
    }

}

