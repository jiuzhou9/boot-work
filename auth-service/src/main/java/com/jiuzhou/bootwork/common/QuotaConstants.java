package com.jiuzhou.bootwork.common;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/19
 */
public class QuotaConstants {

    /**
     * 没有设置计费方式（可以免费调用）
     */
    public static final Integer NO_CALCULATION = -1;

    /**
     * 按照时间方式计算额度
     */
    public static final Integer ACCORDING_TO_THE_TIME = 0;

    /**
     * 按照次数计算额度
     */
    public static final Integer ACCORDING_TO_THE_NUMBER_OF_TIMES = 1;

    /**
     * 按照车次计算额度
     */
    public static final Integer ACCORDING_TO_THE_NUMBER_OF_CARS = 2;
}
