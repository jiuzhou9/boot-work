package com.jiuzhou.bootwork.beans;

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
}
