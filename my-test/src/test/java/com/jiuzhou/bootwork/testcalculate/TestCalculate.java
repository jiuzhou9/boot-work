package com.jiuzhou.bootwork.testcalculate;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/08/07
 */
public class TestCalculate {

    @Test
    public void test(){
        BigDecimal origin = new BigDecimal(1222);
        BigDecimal profit = new BigDecimal(1.14);
        BigDecimal tmp = new BigDecimal(25);
//        BigDecimal divide = tmp.divide(new BigDecimal(66), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal divide = origin.divide(profit, 2, BigDecimal.ROUND_HALF_UP);
        System.out.println(divide);

//        BigDecimal multiply = profit.multiply(new BigDecimal(66), MathContext.DECIMAL32);
//        System.out.println(multiply);
    }


}
