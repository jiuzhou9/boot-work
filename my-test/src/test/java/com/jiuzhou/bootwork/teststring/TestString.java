package com.jiuzhou.bootwork.teststring;

import junit.framework.TestCase;
import org.springframework.util.StringUtils;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/07
 */
public class TestString extends TestCase{

    public void test(){
        String s = "srn:unicorn:::api";
        String[] split = s.split(":");
        System.out.println(split.length);

        int i = StringUtils.countOccurrencesOf(s, ":");
        System.out.println(i);
    }
}
