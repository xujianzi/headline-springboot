package com.jianxu.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @projectName: springboot-headline-part
 * @author: Jian Xu
 * @version: 1.0
 * @time 3/13/2024
 * @description: TODO
 */
@SpringBootTest
public class JwtTest {

    @Autowired
    private JwtHelper jwtHelper;

    @Test
    public void test(){
        //生成 传入用户标识
        String token = jwtHelper.createToken(1L);
        System.out.println("token: " + token);
        // 解析用户标识
        Long userId = jwtHelper.getUserId(token);
        System.out.println("userId : " + userId);
        // 校验是否到期， false表示没有到期
        boolean expiration = jwtHelper.isExpiration(token);
        System.out.println("expiration :" + expiration);
    }
}
