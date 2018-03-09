package com.jiuzhou.bootwork.testreturn;

import com.jiuzhou.bootwork.beans.Person;
import junit.framework.TestCase;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/01/09
 */
public class TestReturn extends TestCase {

    /**
     * person对象属于引用传递
     * integer属于值传递
     */
    public void test(){
        Person person = new Person();
        Integer count = 0;
        method(person, count);
        System.out.println(count);
        System.out.println(person.getName());
    }


    private void method(Person p, Integer count){
        p.setName("zhangsan");
        count+=1;
    }
}
