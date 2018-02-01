package com.jiuzhou.bootwork.testinteger;

import junit.framework.TestCase;

import java.text.DecimalFormat;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/01/29
 */
public class TestInteger extends TestCase {

    /**
     * 测试整数的除法
     */
    public void test(){
        Integer i = 0;
        Integer i1 = 3;
        DecimalFormat decimalFormat = new DecimalFormat("0.00%");
//        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String format = decimalFormat.format((float) i / (float) i1);
        System.out.println(format);
    }
}
