package com.jiuzhou.bootwork.testresult;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.excep.HttpError;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.result.Result;
import org.junit.Test;

import javax.xml.ws.Service;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/04/09
 */
public class TestResult {

    @Test
    public void test_Result(){
        try {
            test_throw();
        } catch (ServiceException e) {
            e.printStackTrace();
            HttpError httpError = e.getHttpError();
            Result result1 = new Result();
            result1.setHttpError(httpError);
            System.out.println(JSON.toJSONString(result1));
        }
    }

    public void test_throw() throws ServiceException {
        Result result = new Result();
        result.setHttpError(HttpErrorEnum.HAS_NO_AUTHORITY);
        HttpError error = HttpErrorEnum.getError("10010068");
        throw new ServiceException(error);
    }
}
