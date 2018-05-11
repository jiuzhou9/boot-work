package com.jiuzhou.bootwork.exception;

import com.jiuzhou.bootwork.excep.HttpError;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/11
 * 主要应用在controller层的异常
 */
public class ApiException extends Exception {

    private HttpError httpError;

    public ApiException(HttpError httpError) {
        this.httpError = httpError;
    }

    public HttpError getHttpError() {
        return httpError;
    }
}
