package com.jiuzhou.bootwork.finance;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/10/11
 */
public class AlgFinance extends AbstractFinance {

    //倒序
    public static double[] CIB201811 = {18,27.3,20.6,299,29.94,72.9,130.5,144};

    //正序
    public static double[] FlowerGarden201810 = { 10, 3.9, 16.4, 3.9, 16.7, 0.99, 5, 4, 14.7, 5, 3.9, 25.1, 14.6, 3.9 };

    public static double[] jd = { 81.6 };

    public AlgFinance(double[] finances) {
        super(finances);
    }
}

