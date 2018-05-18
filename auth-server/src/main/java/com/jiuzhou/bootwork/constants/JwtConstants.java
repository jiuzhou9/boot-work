package com.jiuzhou.bootwork.constants;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/03/28
 */
@Deprecated
public class JwtConstants {
//    header: Authorization   # 头
//    userTokenHeader: UserAuthorization   # 头
//    appTokenHeader: AppAuthorization   # 头
//    appIdHeader: appId  #appid头
//    secret: 70463a4b66f635a06b3a350f2b62e11f
//    appExpiration: 86400  # 项目上线 考虑调配参数 App有效时间24小时 此参数单位秒
//    userExpiration: 300 #项目上线 考虑调配参数 用户令牌有效时间5分钟
//    tokenHead: "Basic "

    public static final String TOKEN_HEADER = "Authorization";
    public static final String USER_TOKEN_HEADER = "UserAuthorization";
    //
    //    @Value("${jwt.appIdHeader}")
    //    private String appIdheader;
    //
    //    @Value("${jwt.tokenHead}")
    //    private String tokenHead;
    //
    //    @Value("${jwt.appTokenHeader}")
    //    private String appTokenHeader;
}
