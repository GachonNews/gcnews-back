package com.yourorg.user_info.config;

// Microservice(Spring MVC)에서 CORS 설정 예시
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsGlobalConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "http://localhost:8080",
                "http://138.2.124.21:8080",
                "http://localhost:3000",
                "http://localhost:5173"
            )
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}
    