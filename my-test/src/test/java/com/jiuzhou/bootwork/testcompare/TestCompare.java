package com.jiuzhou.bootwork.testcompare;

import junit.framework.TestCase;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/01/15
 */
public class TestCompare extends TestCase {

    /**
     * compare 小于结果：-1
     * compare 大于结果：1
     */
    public void test(){
        Integer a = 0;
        System.out.println(a.compareTo(0));
    }
}
