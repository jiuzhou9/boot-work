package com.jiuzhou.bootwork.testpassword;

import junit.framework.TestCase;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/05
 */
public class PasswordTest extends TestCase {

    /**
     * spring security 包中的加密
     */
    public void test() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("abc");
        System.out.println(encode);
        System.out.println(encode.length());
        boolean b = bCryptPasswordEncoder
                        .matches("abc", encode);
        System.out.println(b);
    }
}
