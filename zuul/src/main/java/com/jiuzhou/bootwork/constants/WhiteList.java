package com.jiuzhou.bootwork.constants;

import java.util.Arrays;
import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/04/01
 */
public class WhiteList {

    private static final String AUTH_SERVER = "/auth-server";
    private static final String[] RESOURCE_PATH =
                    {
                                    //此处可以指定白名单路径
                                    AUTH_SERVER+"/api/v1/user/register",
                                    AUTH_SERVER+"/api/v1/user/create-user-token",
                                    AUTH_SERVER+"/api/v1/app/create",
                                    AUTH_SERVER+"/api/v1/app/refresh",
                    };

    /**
     * 1.所有swagger文档请求全部放行
     * @param resourcePath
     * @return
     */
    public static boolean contain(String resourcePath){
        List<String> list = Arrays.asList(RESOURCE_PATH);
        boolean contains = list.contains(resourcePath);
        if (resourcePath.contains("swagger") || resourcePath.contains("api-docs")){
            contains = true;
        }/*else if (resourcePath.contains("eureka-api")){
            contains = true;
        }*/
        return contains;
    };
}
