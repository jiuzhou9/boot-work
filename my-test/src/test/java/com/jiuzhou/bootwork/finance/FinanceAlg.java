package com.jiuzhou.bootwork.finance;

class FinanceAlg {

    public static void main(String[] args) {
        AlgFinance finance = new AlgFinance(AlgFinance.CIB201811);
        double total = finance.total();
        System.out.println("total :: >> " + total);
        System.out.println("count :: >> " + finance.count());
        System.out.println("plusTotal :: >> " + finance.plusTotal());
        System.out.println("negativeTotal :: >> " + finance.negativeTotal());
        System.out.println("plusCount :: >> " + finance.plusCount());
        System.out.println("negativeCount :: >> " + finance.negativeCount());

    }
}