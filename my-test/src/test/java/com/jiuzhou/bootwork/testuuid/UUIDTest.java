package com.jiuzhou.bootwork.testuuid;

import junit.framework.TestCase;

import java.util.UUID;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/03/06
 */
public class UUIDTest extends TestCase{

    public void test(){
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        System.out.println(s.replaceAll("-", ""));
        System.out.println(s.length());
    }
}
