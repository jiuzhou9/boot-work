package com.jiuzhou.bootwork.testlist;

import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/02/02
 */
public class TestList extends TestCase{

    public void test(){
        List<String> list = new ArrayList<>();
        list.add("");
        list.add(null);
        System.out.println(list);
    }

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
