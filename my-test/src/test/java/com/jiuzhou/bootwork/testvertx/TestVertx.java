package com.jiuzhou.bootwork.testvertx;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.beans.Person;
import junit.framework.TestCase;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/10/12
 */
public class TestVertx extends TestCase {

    public static void main(String[] args) {
        ServerExample serverExample = new ServerExample();
        String verticleID = ServerExample.class.getName();
        serverExample.runExample(verticleID);
    }

    public void test(){
        Person person = new Person();
        person.setName("zhangsan");
        System.out.println(JSON.toJSONString(person));
    }
}
