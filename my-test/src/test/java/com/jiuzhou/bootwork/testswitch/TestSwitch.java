package com.jiuzhou.bootwork.testswitch;

import junit.framework.TestCase;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2017/12/28
 */
public class TestSwitch extends TestCase {

    public static final int ALL1 = 1;
    private static final int ALL2 = 2;
    private static final int ALL3 = 3;

    public void test(){
        String s = switchMethod(1);
        System.out.println(s == "");
    }

    public String switchMethod(Integer s){
        String str = "";
        switch (s.intValue()){
            case ALL1: str = "a";
            break;

            case 2: str = "b";
            break;

            case 3: str = "c";
            break;
        }
        return str;
    }
}
