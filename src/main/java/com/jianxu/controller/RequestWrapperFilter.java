package com.jianxu.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;

/**
 * @projectName: springboot-headline-part
 * @author: Jian Xu
 * @version: 1.0
 * @time 5/20/2024
 * @description: 在所有请求到达拦截器之前，使用过滤器将 HttpServletRequest 包装成 ContentCachingRequestWrapper。
 */

@Component
public class RequestWrapperFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper((HttpServletRequest) httpRequest);
        filterChain.doFilter(wrappedRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
