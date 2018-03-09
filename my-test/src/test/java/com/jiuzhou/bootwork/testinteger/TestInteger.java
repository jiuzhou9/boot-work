package com.jiuzhou.bootwork.testinteger;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.beans.Person;
import junit.framework.TestCase;

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

}
