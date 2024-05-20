package com.jianxu.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jianxu.pojo.Headline;
import com.jianxu.service.HeadlineService;
import com.jianxu.utils.JwtHelper;
import com.jianxu.utils.Result;
import com.jianxu.utils.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @projectName: springboot-headline-part
 * @author: Jian Xu
 * @version: 1.0
 * @time 5/20/2024
 * @description: 拦截HeadLine的删除和更新，保证登录用户是文章的创作者匹配
 */
@Component
public class HeadLineInterceptor implements HandlerInterceptor {


    @Autowired
    JwtHelper jwtHelper;

    @Autowired
    HeadlineService headlineService;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * 判断登录用户和文章创作者是否匹配
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }

        // 获取用户id
        String token =  request.getHeader("token");
        Integer userId = jwtHelper.getUserId(token).intValue();
        // 处理headline相关
        String uri = request.getRequestURI();
        Integer headlineId = null;
        // 使用spring提供的request包装类

        if (uri.contains("/update")) {
            // 获取请求体中的headline信息
            Headline headline = getHeadlineFromBody((ContentCachingRequestWrapper) request); // 你需要解析请求体获取 Headline 对象
            headlineId = headline.getHid();
        } else if (uri.contains("/removeByHid")) {
            headlineId = Integer.valueOf(request.getParameter("hid"));
        }

        if (headlineId != null) {
            Headline headline = headlineService.getById(headlineId);
            if (headline == null || !headline.getPublisher().equals(userId)) {
                // java 对象转成字符串
                Result<Object> result = Result.build(null, ResultCodeEnum.NOTLOGIN);
                String json = objectMapper.writeValueAsString(result);
                response.getWriter().print(json);
                return false;
            }
        }

        return true;
    }

    private Headline getHeadlineFromBody(ContentCachingRequestWrapper request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String body = stringBuilder.toString();

        return objectMapper.readValue(body, Headline.class);
    }
}
