package com.yourorg.article.config;

// Microservice(Spring MVC)에서 CORS 설정 예시
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsGlobalConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://138.2.124.21:8080") // Gateway코드 또는 Swagger UI 띄우는 주소
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}
