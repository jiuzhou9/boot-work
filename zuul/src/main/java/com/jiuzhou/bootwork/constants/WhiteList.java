package com.jiuzhou.bootwork.constants;

import java.util.Arrays;
import java.util.List;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
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
//                                    //临时添加 todo 后面会删除
//                                    AUTH_SERVER+"/swagger-ui.html",
//                                    AUTH_SERVER+"/webjars/springfox-swagger-ui/springfox.css",
//                                    AUTH_SERVER+"/webjars/springfox-swagger-ui/springfox.js",
//                                    AUTH_SERVER+"/webjars/springfox-swagger-ui/swagger-ui-standalone-preset.js",
//                                    "/auth-server/webjars/springfox-swagger-ui/favicon-32x32.png",
//                                    "/auth-server/webjars/springfox-swagger-ui/favicon-16x16.png",
//                                    "/auth-server/swagger-resources/configuration/ui",
//                                    "/auth-server/swagger-resources/configuration/security",
//                                    "/auth-server/swagger-resources",
//                                    "/auth-server/swagger-ui.html/swagger-resources/configuration/ui",
//                                    "/auth-server/swagger-ui.html/swagger-resources/configuration/security",
//                                    "/auth-server/swagger-ui.html/swagger-resources",
//                                    "/auth-server/v2/api-docs",
//                                    "/eureka-api/v2/api-docs",
//                                    "/eureka-api/api/v1/hello/page",
                    };

    /**
     * 所有swagger文档请求全部放行
     * @param resourcePath
     * @return
     */
    public static boolean contain(String resourcePath){
        List<String> list = Arrays.asList(RESOURCE_PATH);
        boolean contains = list.contains(resourcePath);
        if (resourcePath.contains("swagger") || resourcePath.contains("api-docs")){
            contains = true;
        }
        return contains;
    };
}
