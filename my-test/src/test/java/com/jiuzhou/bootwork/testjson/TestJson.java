package com.jiuzhou.bootwork.testjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiuzhou.bootwork.beans.Person;
import junit.framework.TestCase;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/08
 */
public class TestJson extends TestCase {

    public void test(){
        Person person = new Person();
        person.setAge(12);
        person.setName("ertyui");
        String s = JSON.toJSONString(person);
        JSONObject personJsonObject = JSONObject.parseObject(s);
        System.out.println(personJsonObject);
    }
}
