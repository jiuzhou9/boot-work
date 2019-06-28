package com.jiuzhou.bootwork.beans;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.result.Result;
import lombok.Data;

import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/01/08
 */
@Data
public class Person {
    private Integer id;

    private String name;

    private Integer age;

    private List<String> companyList;

    public static void main(String[] args) {
        Result<Person> result = new Result<>();
        Person person = new Person();
        person.setAge(12);
        person.setId(1);
        person.setName("张三");
        result.setData(person);
        System.out.println(JSON.toJSONString(result));
    }
}
