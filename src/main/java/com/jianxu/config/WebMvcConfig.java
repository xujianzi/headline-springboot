package com.jianxu.config;

import com.jianxu.interceptor.HeadLineInterceptor;
import com.jianxu.interceptor.LoginProtectInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @projectName: springboot-headline-part
 * @author: Jian Xu
 * @version: 1.0
 * @time 5/20/2024
 * @description: TODO
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginProtectInterceptor loginProtectInterceptor;

    @Autowired
    private HeadLineInterceptor headLineInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginProtectInterceptor).addPathPatterns("/headline/**");
//        registry.addInterceptor(headLineInterceptor)
//                .addPathPatterns("/headline/update", "/headline/removeByHid");
    }
}
