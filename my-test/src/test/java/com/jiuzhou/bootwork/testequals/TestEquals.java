package com.jiuzhou.bootwork.testequals;

import junit.framework.TestCase;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2017/12/27
 */
public class TestEquals extends TestCase {

    /**
     * integer 和 byte 类型无法直接比较
     * 只能取数值比较
     */
    public void test_integer_byte(){
        Integer i = 1;
        Byte b = 1;
        System.out.println(i.equals(b));
    }

    public void test_Byte(){
        Byte b = 0;
        Byte a = 1;
        System.out.println(b.byteValue() == a);
    }

}
