package com.jiuzhou.bootwork.utils;

import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/08/21
 *
 * 字符编码工具
 */
public class CharsetUtils {

    /**
     * ISO-8859-1   --->   UTF-8
     * @param string
     * @return
     */
    public static String ISOToUTF8(String string){
        try {
            string = (string == null || StringUtils.isEmpty(string.trim())) ?
                            null :
                            new String(string.getBytes("ISO-8859-1"), "UTF-8");
            return string;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     * urlCode--->utf-8
     * @param str
     * @return
     */
    public static String URLCodeToUTF8(String str){
        String decode = null;
        try {
            decode = URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decode;
    }


}
