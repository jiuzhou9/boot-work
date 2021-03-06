package com.jiuzhou.bootwork.testnull;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.beans.Person;
import junit.framework.TestCase;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/01/08
 */
public class TestNull extends TestCase {

    public void testNull(){
        String s = null;
        String s2 = null;
        StringBuffer sb = new StringBuffer();
        sb.append(s);
        sb.append(s2);
        boolean empty = StringUtils.isEmpty(sb);
    }


    public void test_set_null(){
        Person person = new Person();
        person.setName(null);
        System.out.println(person);
    }

    public void test_beanUtilsCopy(){
        Person person1 = new Person();
        person1.setName("zz");

        Person person2 = new Person();
        person2.setAge(22);

        Person person3 = new Person();
        BeanUtils.copyProperties(person1, person3);
    }

    public static void main(String[] args) {
        Person person1 = new Person();
        person1.setName("zz");

        Person person2 = new Person();
        person2.setAge(22);

//        Person person3 = new Person();
//        BeanUtils.copyProperties(person1, person3);
        BeanUtils.copyProperties(person1, person2);

        System.out.println(JSON.toJSONString(person2));
    }
}
