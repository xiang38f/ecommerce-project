package com.ordesystemdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 告訴 Spring 這是一個設定檔
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // "/**" 代表套用此規則到所有的 API 路徑
                .allowedOrigins("http://localhost:5173") // 允許來自這個來源的請求 (您前端的網址)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允許的 HTTP 方法
                .allowedHeaders("*") // 允許所有的 request header
                .allowCredentials(true) // 是否允許攜帶 cookie 等憑證
                .maxAge(3600); // pre-flight request 的快取時間 (秒)
    }
}