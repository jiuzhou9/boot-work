/*
 * Copyright (c) 2018, Cardinal Operations and/or its affiliates. All rights reserved.
 * CARDINAL OPERATIONS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.jiuzhou.bootwork;


import com.alibaba.fastjson.JSON;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/06/01
 */
public class DateUtil {

    /**
     * 获取某年的第n周的开始日期
     * 例如:
     *  2018年的第1个星期的开始时间："2018-01-01"
     *  2015年的第1个星期的开始时间："2015-01-05"
     *
     * @param year
     * @param weekNum
     * @return
     */
    public static LocalDate getFirstDayOfWeek(int year, int weekNum) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 0);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, weekNum * 7);
        return getFirstDayOfWeek(toLocalDateTime(cal.getTime())).toLocalDate();
    }

    /**
     * 获取某年的第n周的结束日期
     * 例如：
     *  2015年的第1个星期的结束时间："2015-01-11"
     *  2018年的第1个星期的结束时间："2018-01-07"
     *
     * @param year
     * @param week
     * @return
     */
    public static LocalDate getLastDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 0);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getLastDayOfWeek(toLocalDateTime(cal.getTime())).toLocalDate();
    }

    /**
     * 获取当前时间所在自然周的开始日期
     * 例如：
     *  2018年6月4号对应的自然周的开始："2018-06-04T14:23:42.393"
     *
     * @param dateTime
     * @return
     */
    public static LocalDateTime getFirstDayOfWeek(LocalDateTime dateTime) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(toDate(dateTime));
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());

        return toLocalDateTime(c.getTime());
    }

    /**
     * 获取当前时间所在自然周的结束日期
     * 例如：
     *  2018年6月4号对应的自然周结束日期是："2018-06-10T14:28:21.204"
     *
     * @param dateTime
     * @return
     */
    public static LocalDateTime getLastDayOfWeek(LocalDateTime dateTime) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(toDate(dateTime));
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
        return toLocalDateTime(c.getTime());
    }

    /**
     * 获取指定时间所在年的自然周数
     * 例如：2018年6月4号 对应的自然周是：第23周
     *
     * @param localDateTime
     * @return
     */
    public static int getWeekOfYear(LocalDateTime localDateTime) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(toDate(localDateTime));

        return c.get(Calendar.WEEK_OF_YEAR);
    }


    public static LocalDateTime toLocalDateTime(Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static Date toDate(LocalDateTime dateTime){
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 任意时间，横跨自然周的个数
     * 例如：获取2018/1/1~2018/1/8这段时间内的所有自然周个数
     * 返回:2
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long weeks(LocalDate startDate, LocalDate endDate){
        List<NaturalWeek> weekStartEnd = getWeekStartEnd(startDate, endDate);
        return weekStartEnd.size();
    }

    /**
     * 获取一段时间所在的所有自然周的起始时间和结束时间信息
     * 例如：获取2018/1/1~2018/1/8这段时间内的所有自然周的开始时间和结束时间， 返回信息：
     *  [
     *      {"end":"2018-01-08","month":0,"start":"2018-01-01","weekNum":1,"year":2018},
     *      {"end":"2018-01-15","month":0,"start":"2018-01-08","weekNum":2,"year":2018}
     *  ]
     *
     * @param startDate 起始时间
     * @param endDate 结束时间
     * @return
     */
    public static List<NaturalWeek> getWeekStartEnd(LocalDate startDate, LocalDate endDate){
        startDate = getFirstDayOfWeek(startDate.atStartOfDay()).toLocalDate();
        LocalDate lastDayOfWeek = startDate.plusWeeks(1);
        List<NaturalWeek> list = new ArrayList<>();
        long i = 0;
        while (startDate.plusWeeks(i).isBefore(endDate) || startDate.plusWeeks(i).isEqual(endDate)){
            NaturalWeek time = new NaturalWeek();
            time.setStart(startDate.plusWeeks(i));
            time.setEnd(lastDayOfWeek.plusWeeks(i));
            time.setYear(startDate.plusWeeks(i).getYear());
            time.setWeekNum(getWeekOfYear(startDate.plusWeeks(i).atStartOfDay()));
            list.add(time);
            i++;
        }

        return list;
    }

    /**
     * 任意时间，所横跨的自然月信息，
     * 比如：获取2018/1/1~2018/4/8这段时间内的所有自然月的开始时间和结束时间，
     * 返回信息：
     *      [
     *          {"end":"2018-01-31","month":1,"start":"2018-01-01","year":2018},
     *          {"end":"2018-02-28","month":2,"start":"2018-02-01","year":2018},
     *          {"end":"2018-03-31","month":3,"start":"2018-03-01","year":2018},
     *          {"end":"2018-04-30","month":4,"start":"2018-04-01","year":2018}
     *      ]
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<NaturalMonth> getMonthStartEnd(LocalDate startDate, LocalDate endDate){
        //获取起始时间所在月份的第一天，即1号
        startDate = LocalDate.of(startDate.getYear(), startDate.getMonthValue(), 1);

        List<NaturalMonth> list = new ArrayList<>();
        long i = 0;
        //每次循环加一个月，循环成立的条件是：只要起始时间加完1个月，不大于结束时间即可进行循环
        while (startDate.plusMonths(i).isBefore(endDate) || startDate.plusMonths(i).isEqual(endDate)){
            //循环内容
            NaturalMonth time = new NaturalMonth();
            time.setStart(startDate.plusMonths(i));
            time.setEnd(LocalDate.of(startDate.plusMonths(i).getYear(), startDate.plusMonths(i).getMonthValue(), startDate.plusMonths(i).getMonthValue() == 2 ? IsoChronology.INSTANCE.isLeapYear(startDate.plusMonths(i).getYear()) ? 29 : 28 : startDate.plusMonths(i).getMonth().maxLength()));
            time.setYear(startDate.plusMonths(i).getYear());
            time.setMonthNum(startDate.plusMonths(i).getMonthValue());
            list.add(time);
            i++;
        }

        return list;
    }

    public static void main(String[] args) {
        List<NaturalMonth> monthStartEnd = getMonthStartEnd(LocalDate.of(2017, 1, 1), LocalDate.of(2018, 3, 1));
        System.out.println(JSON.toJSONString(monthStartEnd));
    }

    /**
     * 自然周(月)
     */
    private static class NaturalWeekOrMonth {
        /**
         * 自然周（月）的起始时间
         */
        private LocalDate start;
        /**
         * 自然周（月）的结束时间
         */
        private LocalDate end;

        /**
         * 年份
         */
        private int year;

        public LocalDate getStart() {
            return start;
        }

        protected void setStart(LocalDate start) {
            this.start = start;
        }

        public LocalDate getEnd() {
            return end;
        }

        protected void setEnd(LocalDate end) {
            this.end = end;
        }

        public int getYear() {
            return year;
        }

        protected void setYear(int year) {
            this.year = year;
        }

    }

    /**
     * 自然周
     */
    public static class NaturalWeek extends NaturalWeekOrMonth {
        /**
         * 自然周所在年份的第多少周
         */
        private int weekNum;

        public int getWeekNum() {
            return weekNum;
        }

        private void setWeekNum(int weekNum) {
            this.weekNum = weekNum;
        }

    }

    /**
     * 自然月
     */
    public static class NaturalMonth extends NaturalWeekOrMonth {
        /**
         * 自然月
         */
        private int monthNum;

        public int getMonthNum() {
            return monthNum;
        }

        private void setMonthNum(int monthNum) {
            this.monthNum = monthNum;
        }

    }
}
