package com.jiuzhou.bootwork.jwt;

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
    private static String key = "67cd9e77-b62a-40ab-b67a-8d416cec5f25";


    public static String generateUserToken(String username) {
        claims.put(CLAIM_USERNAME, username);
        return Jwts.builder().setClaims(claims).setExpiration(generateUserExpirationDate())
                        .signWith(SignatureAlgorithm.HS512, key).compact();
    }

    private static Date generateUserExpirationDate() {
        return new Date(System.currentTimeMillis() + userExpiration );
    }

}
