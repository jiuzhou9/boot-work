package com.jiuzhou.bootwork.excep;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/03/28
 */
public class ApiGateWayException extends Exception {

    private HttpError httpError;

    public ApiGateWayException(HttpError httpError) {
        super(httpError.getMessage());
        this.httpError = httpError;
    }

    public HttpError getHttpError() {
        return httpError;
    }

    public void setHttpError(HttpError httpError) {
        this.httpError = httpError;
    }
}
