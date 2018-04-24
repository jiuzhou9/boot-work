package com.jiuzhou.bootwork.testbigdecimal;

import junit.framework.TestCase;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/02/28
 */
public class TestBigDecimal extends TestCase {

    /**
     * 测试整数的除法
     */
    public void test_percent(){
        Integer i = 100;

        Integer i1 = 10000;
//        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        DecimalFormat decimalFormat = new DecimalFormat("0.00%");
        String format = decimalFormat.format((float) i / (float) i1);
        System.out.println(format);

    }

    public void test(){
        Long l1 = 5L;
        Long l2 = 2L;
        System.out.println(l1 / l2);
        System.out.println(l1 % l2 == 0);

        BigDecimal b1 = new BigDecimal(5);
        BigDecimal b2 = new BigDecimal(2);
        System.out.println(b1.divide(b2));
        System.out.println(b1.divideToIntegralValue(b2));
    }

    /**
     * 测试四舍五入
     */
    public void test_sishewuru(){
        BigDecimal bigDecimal = new BigDecimal(12345.5567);
        DecimalFormat decimalFormat = new DecimalFormat("0");
        System.out.println(decimalFormat.format(bigDecimal));
    }
}
