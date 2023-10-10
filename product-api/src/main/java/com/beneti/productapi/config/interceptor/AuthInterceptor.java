package com.beneti.productapi.config.interceptor;

import com.beneti.productapi.modules.jwt.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private JwtService service;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(isOptionsMethodRequest(request)) {
            return true;
        }
        var authorization = request.getHeader(AUTHORIZATION);
        service.validateAuthorization(authorization);
        return true;
    }

    private boolean isOptionsMethodRequest(HttpServletRequest request) {
        return HttpMethod.OPTIONS.name().equals(request.getMethod());
    }
}
