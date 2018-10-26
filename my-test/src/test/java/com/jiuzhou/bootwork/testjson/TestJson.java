package com.jiuzhou.bootwork.testjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jiuzhou.bootwork.beans.Person;
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

    public void test_2(){
        String s = "{\"status\":\"1\",\"info\":\"OK\",\"infocode\":\"10000\",\"count\":\"1\","
                        + "\"geocodes\":[{\"formatted_address\":\"北京市朝阳区阜通东大街|6号\",\"province\":\"北京市\","
                        + "\"citycode\":\"010\",\"city\":\"北京市\",\"district\":\"朝阳区\",\"township\":[],"
                        + "\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},"
                        + "\"adcode\":\"110105\",\"street\":\"阜通东大街\",\"number\":\"6号\",\"location\":\"116.483038,"
                        + "39.990633\",\"level\":\"门牌号\"}]}";

        JSONObject jsonObject = JSON.parseObject(s);

        JSONArray geocodes = jsonObject.getJSONArray("geocodes");
        geocodes.size();
        JSONObject jsonObject1 = geocodes.getJSONObject(0);
        System.out.println(geocodes);
    }
}
