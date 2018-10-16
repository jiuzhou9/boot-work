package com.jiuzhou.bootwork.testmap;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.beans.Person;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/02/05
 */
public class TestMap extends TestCase {

    /**
     * 测试集合中的对象属性问题
     */
    public void test(){
        Map<String, List<Person>> map = new HashMap<>();
        List<Person> list = new ArrayList<>();
        map.put("1",list);
        Person person = new Person();
        person.setAge(11);
        list.add(person);
        System.out.println(JSON.toJSONString(map));
    }

    /**
     * 测试集合中的对象属性问题
     */
    public void test1(){
        Map<String, Person> map = new HashMap<>();
        map.put("1", new Person());
        Person person = map.get("1");
        person.setName("zhangsan");
        System.out.println(JSON.toJSONString(map));
    }

    public void test2(){
        TreeMap<String, Person> map = new TreeMap<>();

        ConcurrentHashMap<String, Person> concurrentHashMap = new ConcurrentHashMap<>();
        Person person = new Person();
        person.setName("嘿嘿1");
        concurrentHashMap.put("1", person);

        Person person1 = new Person();
        person1.setName("嘿嘿2");
        concurrentHashMap.put("0", person1);

        Person person2 = new Person();
        person2.setName("嘿嘿3");
        concurrentHashMap.put("2", person2);

        System.out.println(JSON.toJSONString(concurrentHashMap));
    }
}
