package com.jiuzhou.bootwork.testpassword;

import junit.framework.TestCase;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/03/05
 */
public class PasswordTest extends TestCase {

    /**
     * spring security 包中的加密
     */
    public void test() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

        String encode = bCryptPasswordEncoder.encode("stringstringstringstringstringstringstringstringstringstringstringstring");
        System.out.println(encode);
        System.out.println(encode.length());
        boolean b = bCryptPasswordEncoder
                        .matches("stringstringstringstringstringstringstringstringstringstringstringstring", encode);
        System.out.println(b);
    }

    public void test_md5(){
        String qwertyui = MD5Util.MD5("qwertyui" + "123456");
        System.out.println(qwertyui);
    }
}
