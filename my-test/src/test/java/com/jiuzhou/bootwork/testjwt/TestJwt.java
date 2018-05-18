package com.jiuzhou.bootwork.testjwt;

import junit.framework.TestCase;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/05/17
 */
public class TestJwt extends TestCase {

    public void test(){
        String s = "bd7d7137-52fc-4e9b-8db6-dd72cda75713";
        System.out.println(s);

        Long time = 3600L * 1000;
//        String appToken = JwtTokenUtilBSon.generateAppToken("abc", s, time);

        String token = "eyJhbGciOiJIUzUxMiJ9.eyJhcHBuYW1lIjoiYWJjIiwiZXhwIjoxNTI2NjE2NzU4fQ.pcvEXHWKTG0Dg2ThozlkJ62vsI0mkgb9giV0gTY2xjPf5tu3V9s8BgiAYVhU2I6OcCcey2uJ7Ebnj61PLG9zxw";
        Boolean aBoolean = JwtTokenUtilB.checkAppTokenExpired(token, s);
//        String appName = JwtTokenUtilB.getAppName(appToken, s);

//        System.out.println(appToken);
        System.out.println(aBoolean);
//        System.out.println(appName);
    }
}
