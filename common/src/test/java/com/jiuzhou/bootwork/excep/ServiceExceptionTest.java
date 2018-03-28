package com.jiuzhou.bootwork.excep;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.*;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/28
 */
public class ServiceExceptionTest {

    @Test
    public void setHttpError() {
        try {
            throw new ServiceException(HttpErrorEnum.USERNAME_HAS_ALREADY_EXISTED);
        } catch (ServiceException e) {
            e.printStackTrace();
            HttpError httpError = e.getHttpError();
            System.out.println(httpError);
        }
    }
}