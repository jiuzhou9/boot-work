package com.jiuzhou.bootwork.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/29
 */
public class JwtTokenUtil {

    /**
     * 单位毫秒
     */
    private static final Long USER_EXPIRATION = (3600L * 1000);

    /**
     * 单位毫秒
     */
    private static final Long APP_EXPIRATION = (3600L * 1000 * 24 * 30);

    /**
     * claims
     */
    private static Map<String, Object> claims = new HashMap<>();

    private static final String CLAIM_USERNAME = "username";
    private static final String CLAIM_APP_NAME = "appname";

    /**
     * todo
     * 后面需要迁移到配置文件
     */
    private static String userSecret = "67cd9e77-b62a-40ab-b67a-8d416cec5f25";


    /**
     * 校验APP令牌是否过期,如果过期返回true
     *
     * @param token
     *
     * @return
     */
    public static Boolean checkAppTokenExpired(String token, String appSecret) {
        final Date expiration = getExpirationDateFromToken(token, appSecret);
        if (expiration == null){
            return true;
        }
        return expiration.before(new Date());
    }

    /**
     * APP 令牌解析
     * @param token
     * @param appSecret
     * @return
     */
    public static String getAppName(String token, String appSecret){
        Claims claims = getClaimsFromToken(token, appSecret);
        String appname = (String) claims.get(CLAIM_APP_NAME);
        return appname;
    }

    /**
     * 根据APP名,APP secret获取token
     * @param appName
     * @return
     */
    public static String generateAppToken(String appName, String appSecret) {
        claims.put(CLAIM_APP_NAME, appName);
        return Jwts.builder().setClaims(claims).setExpiration(generateAppExpirationDate())
                        .signWith(SignatureAlgorithm.HS512, appSecret).compact();
    }

    /**
     * 根据用户名获取token
     * @param username
     * @return
     */
    public static String generateUserToken(String username) {
        claims.put(CLAIM_USERNAME, username);
        return Jwts.builder().setClaims(claims).setExpiration(generateUserExpirationDate())
                        .signWith(SignatureAlgorithm.HS512, userSecret).compact();
    }

    /**
     * 获取用户令牌的用户名
     * @param token
     * @return
     */
    public static String getUserName(String token){
        Claims claims = getClaimsFromToken(token, userSecret);
        String username = (String) claims.get(CLAIM_USERNAME);
        return username;
    }

    /**
     * 校验用户令牌是否过期,如果过期返回true
     *
     * @param token
     *
     * @return
     */
    public static Boolean checkUserTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token, userSecret);
        if (expiration == null){
            return true;
        }
        return expiration.before(new Date());
    }

    /**
     * 获取token中的过期时间
     * @param token
     * @param secret
     * @return
     */
    private static Date getExpirationDateFromToken(String token, String secret) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token, secret);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    /**
     * usertoken有效期
     * @return
     */
    private static Date generateAppExpirationDate() {
        return new Date(System.currentTimeMillis() + APP_EXPIRATION);
    }

    /**
     * usertoken有效期
     * @return
     */
    private static Date generateUserExpirationDate() {
        return new Date(System.currentTimeMillis() + USER_EXPIRATION );
    }

//    ---------------------------------------------------------

    /**
     * 解析token中的信息
     * @param token
     * @param secret
     * @return
     */
    private static Claims getClaimsFromToken(String token, String secret) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }
        return claims;
    }

}
