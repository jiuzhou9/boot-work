package com.jiuzhou.bootwork.testlong;

import junit.framework.TestCase;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/01/10
 */
public class TestLong extends TestCase{

    public void test(){
        Long l = 0L;
        Long L = 0L;
        System.out.println(l.equals(L));
    }

    public void test_toString(){
        long l = 100;
        long l1 = 2000;
        System.out.println(Long.toString(l1 - l));
    }
}
