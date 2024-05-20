package com.jianxu.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jianxu.utils.JwtHelper;
import com.jianxu.utils.Result;
import com.jianxu.utils.ResultCodeEnum;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

/**
 * @projectName: springboot-headline-part
 * @author: Jian Xu
 * @version: 1.0
 * @time 5/19/2024
 * @description: TODO
 */

@Component
public class LoginProtectInterceptor implements HandlerInterceptor {

    @Autowired
    JwtHelper jwtHelper;

    /**
     * 登录验证，需要有token且没有过期才放行
     *    拦截条件
     *    1.没有token
     *    2，token过期
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (!(request instanceof ContentCachingRequestWrapper)) {
//            request = new ContentCachingRequestWrapper(request);
//        }

        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token) || jwtHelper.isExpiration(token)){
            Result<Object> result = Result.build(null, ResultCodeEnum.NOTLOGIN);
            // java 对象转成字符串
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(result);
            response.getWriter().print(json);
            return false ; // 拦截
        }
        return true; // 放行
    }

}
