package com.jiuzhou.bootwork.teststream;

import com.jiuzhou.bootwork.beans.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/12/28
 */
public class TestStream {

    /**
     * 将某个list中的所有对象某一属性（例如ID），抽取成一个list
     */
    @Test
    public void listToList(){
        Person p1 = new Person();
        p1.setAge(1);
        p1.setId(1);
        p1.setName("一");
        Person p2 = new Person();
        p2.setAge(2);
        p2.setId(2);
        p2.setName("二");
        Person p3 = new Person();
        p3.setAge(3);
        p3.setId(3);
        p3.setName("三");
        List<Person> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);

        List<Integer> ids = list.stream().map(Person::getId).collect(Collectors.toList());
        System.out.println(ids);
    }
}
