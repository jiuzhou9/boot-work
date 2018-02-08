package com.jiuzhou.bootwork.testinteger;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.testnull.Person;
import junit.framework.TestCase;

import java.text.DecimalFormat;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/01/29
 */
public class TestInteger extends TestCase {

    /**
     * 负数
     */
    public void test(){
        Integer integer = 0;
        Integer integer1 = 10;
        Person person = new Person();
        person.setAge(integer - integer1 < 0? 0 :integer - integer1);
        System.out.println(JSON.toJSONString(person));
    }

    /**
     * 测试整数的除法
     */
    public void test_percent(){
        Integer i = 0;

        Integer i1 = 0;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
//        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String format = decimalFormat.format((float) i / (float) i1);
        System.out.println(format);
    }
}
