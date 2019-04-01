package com.jiuzhou.bootwork.teststream;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.beans.Person;
import org.junit.Test;
import sun.nio.ch.sctp.PeerAddrChange;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wangjiuzhou (835540436@qq.com)
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
        p3.setName("一");
        Person p4 = new Person();
        p4.setAge(3);
        p4.setId(4);
        p4.setName("一");
        List<Person> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);

//        List<Person> list = new ArrayList<>();
//        for (int i = 0; i < 10000000; i++) {
//            Person p1 = new Person();
//            p1.setAge(i);
//            p1.setId(i);
//        }

        //获取集合中的某一属性集合
        long l = System.currentTimeMillis();
        List<Integer> ids = list.stream().map(Person::getId).collect(Collectors.toList());
        System.out.println(System.currentTimeMillis() - l);


        List<Integer> idList = new ArrayList<>();
        long l1 = System.currentTimeMillis();
        for (Person person : list) {
            idList.add(person.getId());
        }
        /*list.forEach(person -> {
            idList.add(person.getId());
        });*/
        System.out.println(System.currentTimeMillis() - l1);

        //按照对象某一属性进行分组
        Map<String, List<Person>> collect = list.stream().collect(Collectors.groupingBy(Person::getName));
        System.out.println(collect);

        //按照对象某一属性值分组，然后再按照某属性值进行二级分组
        Map<String, Map<Integer, List<Person>>> collect1 = list.stream()
                        .collect(Collectors.groupingBy(Person::getName, Collectors.groupingBy(Person::getAge)));
        System.out.println(JSON.toJSONString(collect1));

        //按照组合属性作为主键
        Map<String, Person> collect2 = list.stream()
                        .collect(Collectors.toMap(k -> k.getName() + "::" + k.getId(), person -> person));
        System.out.println(JSON.toJSONString(collect2));
    }
}
