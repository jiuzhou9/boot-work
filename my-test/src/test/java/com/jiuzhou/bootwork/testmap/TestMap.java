package com.jiuzhou.bootwork.testmap;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.beans.Person;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/02/05
 */
public class TestMap extends TestCase {

    public void test(){
        Map<String, List<Person>> map = new HashMap<>();
        List<Person> list = new ArrayList<>();
        map.put("1",list);
        Person person = new Person();
        person.setAge(11);
        list.add(person);
        System.out.println(JSON.toJSONString(map));
    }
}
