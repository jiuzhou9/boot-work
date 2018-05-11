package com.jiuzhou.bootwork.testjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiuzhou.bootwork.beans.Person;
import com.jiuzhou.bootwork.result.Result;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/03/08
 */
public class TestJson extends TestCase {

    public void test(){
        String s = JSON.toJSONString(true);
        System.out.println(s);
    }

    public void test_json_parse(){
        Person person = new Person();
        person.setAge(12);
        person.setName("ertyui");
        String s = JSON.toJSONString(person);
        JSONObject personJsonObject = JSONObject.parseObject(s);
        System.out.println(personJsonObject);
    }

    public void test_1(){
        Person person = new Person();
        person.setAge(12);
        person.setName("ertyui");
        Map<String, Person> map = new HashMap<>();
        map.put("a", person);
//        Result<String> result = new Result<>();
//        result.setData(JSON.toJSONString(person));
        System.out.println(JSON.toJSONString(person));
    }
}
