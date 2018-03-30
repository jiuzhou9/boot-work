package com.jiuzhou.bootwork.jwt;

import org.junit.Test;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/30
 */
public class JwtTokenUtilTest {

    @Test
    public void generateUserToken() {
        String abc = JwtTokenUtil.generateUserToken("abc");
        System.out.println(abc);
    }
}