package com.jiuzhou.bootwork.result;

import com.jiuzhou.bootwork.excep.HttpError;

import java.io.Serializable;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/02/22
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String SUCCESS_CODE = "0";

    private String code;
    private String message;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public static <T> Result<T> buildSuccess() {
        return buildResult(true, null, "Success", null);
    }

    public static <T> Result<T> buildSuccess(T obj) {
        return buildResult(true, null, "Success", obj);
    }

    public static <T> Result<T> buildSuccess(String code, T obj) {
        return buildResult(true, code, "Success", obj);
    }

    public static <T> Result<T> buildSuccess(String code, String msg, T obj) {
        return buildResult(true, code, msg, obj);
    }

    private static <T> Result<T> buildFailed(String msg) {
        return buildResult(false, "1", msg, null);
    }

    private static <T> Result<T> buildFailed(String code, String msg) {
        return buildResult(false, code, msg, null);
    }

    private static <T> Result<T> buildResult(boolean statu, String code, String msg, T obj) {
        if (code == null) {
            code = "0";
        }
        Result<T> r = new Result<T>();
        r.setCode(code);
        r.setMessage(msg);
        r.setData(obj);
        return r;
    }

//    --------------
    public static <T> Result<T> buildFailed(HttpError httpError){
        return buildFailed(httpError.getCode(), httpError.getMessage());
    }
}
