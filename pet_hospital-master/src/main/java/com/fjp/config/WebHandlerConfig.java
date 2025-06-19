package com.fjp.config;

import com.fjp.config.handler.UserContextHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebHandlerConfig implements WebMvcConfigurer {
    @Autowired
    private UserContextHandler userContextHandler;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userContextHandler).addPathPatterns("/**");
    }
}
