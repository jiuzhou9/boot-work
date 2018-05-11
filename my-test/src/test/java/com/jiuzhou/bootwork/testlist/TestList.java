package com.jiuzhou.bootwork.testlist;

import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/02/02
 */
public class TestList extends TestCase{

    /**
     * list集合中可以存储null值，而且还可以存储多个null
     */
    public void testNull(){
        List<String> list = new ArrayList<>();
        list.add("");
        list.add(null);
        list.add(null);
        list.add(null);
        System.out.println(list);
    }

    /**
     * 集合迭代器，是可以删除集合中指定的元素的，一边遍历一边删除
     */
    public void test_remove(){
        List<String> list = new ArrayList<>();
        list.add("-1");

        ListIterator<String> stringListIterator =
                        list.listIterator();
        while (stringListIterator.hasNext()){
            String next = stringListIterator.next();
            if ("-1".equals(next)){
                stringListIterator.remove();
            }
        }
        System.out.println(JSON.toJSONString(list));
    }
}
