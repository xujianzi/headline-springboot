package com.jianxu.utils;

import io.jsonwebtoken.*;
import io.micrometer.common.util.StringUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @projectName: springboot-headline-part
 * @author: Jian Xu
 * @version: 1.0
 * @time 3/13/2024
 * @description: TODO
 */

@Data
@Component
@ConfigurationProperties(prefix = "jwt.token")
public class JwtHelper {

    private long tokenExpiration; // 有效时间 单位毫秒 1000毫秒= 1s
    private String tokenSignKey;

    /**
     * 根据userId生成Token
     * @param userId
     * @return
     */
    public String createToken(Long userId){
        System.out.println("tokenExpiration = " + tokenExpiration);
        System.out.println("tokenSignKey = " + tokenSignKey);
        String token = Jwts.builder()
                .setSubject("YYGH-USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration*1000*60))
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compact();

        return  token;
    }

    /**
     * 判断Token是否过期
     * @param token
     * @return
     */
    public boolean isExpiration(String token){
        try {
            boolean isExpire = Jwts.parser()
                    .setSigningKey(tokenSignKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .before(new Date());
            // 灭有过期，有效，返回false
            return isExpire;
        } catch (Exception e) {
            //出现异常，返回true
            return true;
        }
    }

    /**
     * 从token字符串中获取userId
     * @param token
     * @return
     */
    public Long getUserId(String token){
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        Integer userId = (Integer) body.get("userId");
        return userId.longValue();
    }
}
