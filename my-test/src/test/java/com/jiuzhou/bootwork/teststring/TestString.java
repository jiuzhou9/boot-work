package com.jiuzhou.bootwork.teststring;

import junit.framework.TestCase;
import org.springframework.util.StringUtils;

import javax.sound.midi.Soundbank;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/03/07
 */
public class TestString extends TestCase{

    /**
     * api路径去参数化
     * @param apiPathWithParameter 带有参数的api路径
     * @param count 参数个数
     * @return 去参数后的api路径
     */
    private String removeParameter(String apiPathWithParameter, int count){
        String[] split = apiPathWithParameter.split("/");
        StringBuilder apiPath = new StringBuilder();

        for (int i = 0; i < split.length - count; i++) {
            if (i != 0){
                apiPath.append("/" + split[i]);
            }
        }

        return apiPath.toString();
    }


    public void test(){
        String type = "srn::menu::";
        String[] split = type.split(":");
        System.out.println(split[2]);
    }

    public void test_split(){
        String url = "/eureka-api/api/v1/hello";
        int count = 3;
        String s = removeParameter(url, count);
        System.out.println(s);


        //        String[] split1 = url.split("/");
//        System.out.println(split1.length);
//        System.out.println(split1[1]);
//        String s = "";

//        String[] split = url.split("/");
//        url = "";
//        for (int i = 0; i < split.length - count; i++) {
//            if (i != 0){
//                url = url + "/" + split[i];
//            }
//        }
//        System.out.println(url);

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
        String s = " a bc";
        String trim = s.trim();
        System.out.println(trim);

        String a = " ";
        System.out.println(StringUtils.isEmpty(a));
    }
}
