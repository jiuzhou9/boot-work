package com.jiuzhou.bootwork.finance;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/10/11
 */
public abstract class AbstractFinance {

    protected final double[] finances;

    public double total() {
        double count = 0;
        for (int i = 0; i < finances.length; i++) {
            count += finances[i];
        }
        return count;
    }

    public double plusTotal(){
        double count = 0;
        for (int i = 0; i < finances.length; i++) {
            if (finances[i] > 0){
                count += finances[i];
            }
        }
        return count;
    }

    public double negativeTotal(){
        double count = 0;
        for (int i = 0; i < finances.length; i++) {
            if (finances[i] < 0){
                count += finances[i];
            }
        }
        return count;
    }

    public int count() {
        return finances.length;
    }

    public int plusCount(){
        int count = 0;
        for (double finance : finances) {
            if (finance > 0){
                count++;
            }
        }
        return count;
    }

    public int negativeCount(){
        int count = 0;
        for (double finance : finances) {
            if (finance < 0){
                count++;
            }
        }
        return count;
    }

    public double getDetail(int index) {
        return finances[index];
    }

    public AbstractFinance(double[] finances) {
        this.finances = finances;
    }
}
