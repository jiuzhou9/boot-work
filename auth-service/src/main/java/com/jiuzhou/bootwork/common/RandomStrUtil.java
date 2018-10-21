package com.jiuzhou.bootwork.common;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/15
 */
public class RandomStrUtil {

    /**
     * 获取指定位数的随机字符串(包含小写字母、大写字母、数字,0<length)
     * @param length 字符串长度，需要大于0
     * @return 生成的字符串
     */
    public static String getRandomString(int length) {
        //随机字符串的随机字符库
        String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        int len = KeyString.length();
        for (int i = 0; i < length; i++) {
            sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        return sb.toString();
    }

}
