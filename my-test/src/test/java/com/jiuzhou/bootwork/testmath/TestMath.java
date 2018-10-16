package com.jiuzhou.bootwork.testmath;

import org.junit.Test;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/09/26
 */
public class TestMath {

    @Test
    public void testCos(){
//        double cos = Math.cos(1 * 45 * Math.PI / 180);
        double cos = Math.cos(30);
        System.out.println(cos);
    }

    @Test
    public void test(){
        double v = 8 * 360/8 * Math.PI / 180;
        System.out.println(v);
    }
}
