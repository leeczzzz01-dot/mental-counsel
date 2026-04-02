package com.metal.counsel.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metal.counsel.common.result.Result;
import com.metal.counsel.common.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

/**
 * JWT 拦截器
 */
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 放行 OPTIONS 预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (token == null || !jwtUtils.validateToken(token)) {
            writeUnauthorized(response);
            return false;
        }

        // 将 userId 存入 request 属性
        Long userId = jwtUtils.getUserId(token);
        request.setAttribute("userId", userId);
        return true;
    }

    private void writeUnauthorized(HttpServletResponse response) throws IOException {
        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(Result.fail(401, "未登录或 token 已过期")));
    }
}
