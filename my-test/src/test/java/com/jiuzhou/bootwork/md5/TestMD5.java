package com.jiuzhou.bootwork.md5;

import com.jiuzhou.bootwork.testpassword.MD5Util;
import junit.framework.TestCase;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.security.MessageDigest;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/11/23
 */
public class TestMD5 extends TestCase {


    public void test(){
        Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
        String s = md5PasswordEncoder.encodePassword("123a", "$1$abcde");
        System.out.println(s);

        String s2 = Md5Crypt.md5Crypt("123a".getBytes(), "$1$abcde");
        System.out.println(s2);

        String s1 = MD5Util.MD5("123a" + "$1$abcde");
        System.out.println(s1);

        String s3 = MD5("123a" + "$1$abcde");
        System.out.println(s3);
    }

    public static String MD5(String key) {
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}
