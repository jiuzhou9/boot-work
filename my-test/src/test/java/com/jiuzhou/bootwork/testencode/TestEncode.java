package com.jiuzhou.bootwork.testencode;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/07/07
 */
public class TestEncode {

    @Test
    public void test(){
        String s = "%E8%A5%BF%E5%8C%97%E4%BA%8B%E4%B8%9A%E9%83%A8";
        String decode = null;
        try {
            decode = URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(decode);
    }

}
