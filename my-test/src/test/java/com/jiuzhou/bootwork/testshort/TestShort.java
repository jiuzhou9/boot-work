package com.jiuzhou.bootwork.testshort;

import org.junit.Test;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/08/21
 */
public class TestShort {

    @Test
    public void test(){
        Short aShort = new Short("1");
        System.out.println(aShort);
        short s = 1;
        System.out.println(aShort.equals(s));
    }
}
