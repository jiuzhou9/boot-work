package com.jiuzhou.bootwork.testhttpstatus;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/09/12
 */
public class TestHttpStatus {

    @Test
    public void test(){
        HttpStatus s = HttpStatus.OK;
        Assert.assertEquals(s, HttpStatus.OK);
    }
}
