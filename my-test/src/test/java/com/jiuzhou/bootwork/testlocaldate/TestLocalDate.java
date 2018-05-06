package com.jiuzhou.bootwork.testlocaldate;

import junit.framework.TestCase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/02/08
 */
public class TestLocalDate extends TestCase{

    public void test(){
        //当前天
        LocalDateTime localDateTime = LocalDate.now().atStartOfDay();
        System.out.println(localDateTime);
        //昨天
        LocalDateTime localDateTime2 = LocalDate.now().minusDays(1).atStartOfDay();
        System.out.println(localDateTime2);
        //明天
        LocalDateTime localDateTime3 = LocalDate.now().plusDays(1).atStartOfDay();
        System.out.println(localDateTime3);
        int year = LocalDate.now().getYear();
        Month month = LocalDate.now().getMonth();
        int dayOfMonth = LocalDate.now().getDayOfMonth();
        System.out.println(year + "" + month.getValue() + "" + dayOfMonth);
        String s = LocalDate.now().toString();
        String replace = s.replace("-", "");
        System.out.println(replace);

        //比较时间
        LocalDate of1 = LocalDate.of(2018, 12, 12);
        LocalDate of2 = LocalDate.of(2018, 12, 12);
        System.out.println(of1.equals(of2));
    }
}
