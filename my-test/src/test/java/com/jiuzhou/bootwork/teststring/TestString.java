package com.jiuzhou.bootwork.teststring;

import junit.framework.TestCase;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/03/07
 */
public class TestString extends TestCase{

    public void test_split(){
        String url = "/eureka-api/api/v1/hello";
        int count = 4;
//        String[] split1 = url.split("/");
//        System.out.println(split1.length);
//        System.out.println(split1[1]);
//        String s = "";

        String[] split = url.split("/");
        url = "";
        for (int i = 0; i < split.length - count; i++) {
            if (i != 0){
                url = url + "/" + split[i];
            }
        }
        System.out.println(url);

//        String resource;
//        for (int i = 2; i < split1.length; i++){
//            resource = "/" + split1[i];
//        }
//
//        String s = "srn:unicorn:::api";
//        String[] split = s.split(":");
//        System.out.println(split.length);
//
//        int i = StringUtils.countOccurrencesOf(s, ":");
//        System.out.println(i);
    }

    public void test_trim(){
        String s = " abc";
        String trim = s.trim();
        System.out.println(trim);
    }
}
