package com.jiuzhou.bootwork.hash;

import javax.print.DocFlavor;
import java.io.UnsupportedEncodingException;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2020/08/17
 */
public class Hash {

    public static void main(String[] args) throws UnsupportedEncodingException {
        int i = "啊".hashCode();
        System.out.println(i);

        String AA = "aa";
        String s = new String(AA.getBytes(), "UTF-8");
        System.out.println(s);
    }
}
