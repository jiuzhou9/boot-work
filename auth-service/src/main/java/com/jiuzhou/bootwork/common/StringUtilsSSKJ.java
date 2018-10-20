package com.jiuzhou.bootwork.common;

import org.springframework.util.StringUtils;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/09/13
 */
public class StringUtilsSSKJ {

    /**
     * 把一位的String前面补0，变成两位的
     * @return
     */
    public static String format2(String str){
        if (str.length() < 2){
            str = "0" + str;
            return str;
        }
        return str;
    }

    /**
     * 把一位的String前面补0，变成六位的
     * @return
     */
    public static String format6(String str){
        int length = str.length();
        if (length < 6){
            for (int i = 0; i < 6 - length; i++) {
                str = "0" + str;
            }
            return str;
        }
        return str;
    }

    /**
     * String s = "xxx:aaa:xxx:xxx:xxx"
     * 获取aaa位置内容
     *
     * @return
     */
    public static String get2(String string){
        String[] split = string.split(":", 5);
        return split[1];
    }

    /**
     * String s = "xxx:xxx:xxx::aaa"
     * 获取aaa位置内容
     *
     * @return
     */
    public static String get5(String string){
        String[] split = string.split(":", 5);
        return split[4];
    }

//    public static void main(String[] args) {
//        String s = get5("xxx:xxx:xxx::");
//        System.out.println(s);
//    }

    /**
     * "xxx:xxx:xxx:xxx:aaa"
     * @param string
     * @return
     */
    public static boolean validateResourceType(String string){
        boolean b;
        String[] split = string.split(":");
        b = split.length == 5
                        &&
        !StringUtils.isEmpty(split[0])
                        &&
        !StringUtils.isEmpty(split[1])
                        &&
        !StringUtils.isEmpty(split[2])
                        &&
        !StringUtils.isEmpty(split[4]);
        return b;
    }



    /**
     * 根据完整的apiPath获取ServerName
     * 例如：/unicorn-api/api/v1/xxx,得到：unicorn-api
     * @param apiPath
     * @return
     */
    public static String getServerNameByApiPath(String apiPath){
        String[] split = apiPath.split("/");
        return split[1];
    }
}
