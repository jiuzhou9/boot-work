package com.jiuzhou.bootwork.testtime;

import junit.framework.TestCase;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/01/16
 */
public class TestTime extends TestCase {

    public void test(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        LocalDate now1 = LocalDate.now();
        System.out.println(now1.toString());
    }
}
