/*
 * Copyright (c) 2017-2018, Cardinal Operations and/or its affiliates. All rights reserved.
 * CARDINAL OPERATIONS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.jiuzhou.bootwork.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/04/10
 */
public class JwtUtil {

    /**
     * claims
     */
    private static Map<String, Object> claims = new HashMap<>();
    private static final String KEY = "key";


    /**
     * 校验APP令牌是否过期,如果过期返回true
     *
     * @param token key 的令牌
     * @param secret key secret
     *
     * @return
     */
    public static Boolean checkAppTokenExpired(String token, String secret) {
        final Date expiration = getExpirationDateFromToken(token, secret);
        if (expiration == null){
            return true;
        }
        return expiration.before(new Date());
    }

    /**
     * 令牌解析获取key
     * @param token
     * @param secret
     * @return
     */
    public static String getKey(String token, String secret){
        Claims claims = getClaimsFromToken(token, secret);
        String key = (String) claims.get(KEY);
        return key;
    }

    /**
     * 根据key,key的 secret生成token
     * @param key 加密的内容
     * @param privateSecret key密钥，用于生成key令牌的时候使用
     * @param date 令牌的失效时间点
     * @return
     */
    public static String generateAppToken(String key, String privateSecret, Date date) {
        claims.put(KEY, key);
        return Jwts.builder().setClaims(claims).setExpiration(date)
                        .signWith(SignatureAlgorithm.HS512, privateSecret).compact();
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
