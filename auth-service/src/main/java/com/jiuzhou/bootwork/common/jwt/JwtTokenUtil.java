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
    private static final Long userExpiration = (3600L * 1000);

    /**
     * claims
     */
    private static Map<String, Object> claims = new HashMap<>();

    private static final String CLAIM_USERNAME = "username";

    /**
     * todo
     * 后面需要迁移到配置文件
     */
    private static String userSecret = "67cd9e77-b62a-40ab-b67a-8d416cec5f25";

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
     * usertoken有效期
     * @return
     */
    private static Date generateUserExpirationDate() {
        return new Date(System.currentTimeMillis() + userExpiration );
    }

    public static String getUserName(String token){
        Claims claims = getClaimsFromToken(token, userSecret);
        String username = (String) claims.get(CLAIM_USERNAME);
        return username;
    }

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

}
