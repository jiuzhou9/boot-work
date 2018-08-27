package com.jiuzhou.bootwork.testcompare;

import junit.framework.TestCase;

import java.math.BigDecimal;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/01/15
 */
public class TestCompare extends TestCase {

    /**
     * compare 小于结果：-1
     * compare 大于结果：1
     */
    public void test(){
        BigDecimal a = new BigDecimal(3);
        BigDecimal b = new BigDecimal(2);
        System.out.println(b.compareTo(a) > 0);
    }

}
