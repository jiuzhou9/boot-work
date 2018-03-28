package com.jiuzhou.bootwork.excep;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/28
 */
public class ServiceException extends Exception {

    private HttpError httpError;

    public ServiceException(HttpError httpError) {
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
