package com.jiuzhou.bootwork.testswitch;

import junit.framework.TestCase;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2017/12/28
 */
public class TestSwitch extends TestCase {

    public void test(){
        String s = switchMethod(1);
        System.out.println(s == "");
    }

    public String switchMethod(Integer s){
        String str = "";
        switch (s){
            case 1:
            break;

            case 2: str = "b";
            break;

            case 3: str = "c";
            break;
        }
        return str;
    }
}
