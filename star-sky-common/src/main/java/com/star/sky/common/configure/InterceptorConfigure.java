package com.star.sky.common.configure;

import com.star.sky.common.interceptor.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfigure implements WebMvcConfigurer {

    @Bean
    public Interceptor getInterceptor() {
        return new Interceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.getInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/access/register")
                .excludePathPatterns("/access/login")
                .excludePathPatterns("/**/error") // spring boot 全局异常捕获会请求 error，放行
                .excludePathPatterns("/**/*.js")
                .excludePathPatterns("/**/*.css")
                .excludePathPatterns("/**/*.html");
    }

}
