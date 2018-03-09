package com.jiuzhou.bootwork.testuuid;

import junit.framework.TestCase;

import java.util.UUID;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/06
 */
public class UUIDTest extends TestCase{

    public void test(){
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        System.out.println(uuid);
        System.out.println(s.length());
    }
}
