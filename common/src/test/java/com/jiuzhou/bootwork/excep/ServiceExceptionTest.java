package com.jiuzhou.bootwork.excep;

import org.junit.Test;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/03/28
 */
public class ServiceExceptionTest {

    @Test
    public void setHttpError() {
        try {
            throw new ApiGateWayException(HttpErrorEnum.USERNAME_HAS_ALREADY_EXISTED);
        } catch (ApiGateWayException e) {
            e.printStackTrace();
            HttpError httpError = e.getHttpError();
            System.out.println(httpError);
        }
    }
}