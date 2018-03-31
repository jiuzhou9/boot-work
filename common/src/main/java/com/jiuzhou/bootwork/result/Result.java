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
    private T data;
    private HttpError httpError;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    private void setCode(String code) {
        this.code = code;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static <T> Result<T> buildSuccess(T obj) {
        return buildResult(null, obj);
    }


    private static <T> Result<T> buildResult(String code, T obj) {
        if (code == null) {
            code = "0";
        }
        Result<T> r = new Result<T>();
        r.setCode(code);
        r.setData(obj);
        return r;
    }

//    --------------
    public void setHttpError(HttpError httpError) {
        this.httpError = httpError;
        if (httpError != null){
            this.code = httpError.getCode();
        }
    }

    public HttpError getHttpError() {
        return httpError;
    }
}
