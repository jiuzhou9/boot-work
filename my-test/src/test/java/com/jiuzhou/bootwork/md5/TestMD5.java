package com.jiuzhou.bootwork.md5;

import com.jiuzhou.bootwork.testpassword.MD5Util;
import junit.framework.TestCase;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/11/23
 */
public class TestMD5 extends TestCase {


    public void test(){
        Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
        String s = md5PasswordEncoder.encodePassword("123", "$1$abcde");
        System.out.println(s);

        String s2 = Md5Crypt.md5Crypt("123".getBytes(), "$1$abcde");
        System.out.println(s2);

        String s1 = MD5Util.MD5("1233176fde1c63c4a38b85cb4a9da439a8f");
        System.out.println(s1);
    }
}
