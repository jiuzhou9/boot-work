package com.jiuzhou.bootwork.testlocaldate;

import junit.framework.TestCase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/02/08
 */
public class TestLocalDate extends TestCase {

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
        System.out.println("是否相等" + of1.isEqual(of2));

        System.out.println(localDateTime);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String format = localDateTime.format(df);
        System.out.println(format);

        ZoneId zoneId = ZoneId.systemDefault();
        Date from = Date.from(localDateTime.atZone(zoneId).toInstant());
        System.out.println(new Date());
    }


    public void test1(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println("" + now.getYear() + now.getMonth().getValue() + now.getDayOfMonth() + now.getHour() + now.getMinute() + now.getSecond());

        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        int i = c.get(Calendar.WEEK_OF_YEAR);
        System.out.println(i);

        LocalDate now1 = LocalDate.now();
        System.out.println(now1.atStartOfDay());
    }
}
