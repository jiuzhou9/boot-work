package com.jiuzhou.bootwork.testjwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/05/17
 */
public class JwtTokenUtilBSon extends JwtTokenUtilB {

    /**
     * 根据APP名,APP secret获取token
     * @param appName 加密的内容
     * @param appSecret 加密的密钥
     * @param time 令牌的过期时间，单位秒，例如：3600
     * @return
     */
    public static String generateAppToken(String appName, String appSecret, Long time) {
        claims.put(CLAIM_APP_NAME, appName);
        return Jwts.builder().setClaims(claims).setExpiration(generateAppExpirationDate(time))
                        .signWith(SignatureAlgorithm.HS512, appSecret).compact();
    }


    /**
     * APP的有效期时间点
     * @param time 时间区间
     * @return
     */
    private static Date generateAppExpirationDate(Long time){
        return new Date(System.currentTimeMillis() + time);
    }



}
