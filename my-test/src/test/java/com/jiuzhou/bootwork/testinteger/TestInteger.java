package com.jiuzhou.bootwork.testinteger;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.beans.Person;
import junit.framework.TestCase;

/**
 * @author wangjiuzhou (835540436@qq.com)
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

    public void test2(){
        Integer i = 2;
        System.out.println(i.toString());
    }

    public void test3(){
        Integer i = 2147483647;
//        System.out.println(i.compareTo(365) == 1);
        if (i.compareTo(365) == 1){
            System.out.println("大于365");
        }else {
            System.out.println("大于365");
        }
    }


    public void test4(){
        Integer i = 12100;
        Double d = 12.1d * 1000;

        System.out.println(i.intValue() == d.intValue());
    }

    public void test5(){
        Integer i = 1;
        Integer i1 = -1;
        System.out.println( i + i1);
    }

    public void test6(){
//        int maxValue = Integer.MAX_VALUE;
//        System.out.println(maxValue);

        int i = 0;
        int ii = 1009;
        System.out.println(i % ii);
        System.out.println(i / ii);
    }
}
