package com.jiuzhou.bootwork.excep;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/03/28
 * 这是一个应用在Service层的异常类，Service层抛异常的时候需要使用
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

}
