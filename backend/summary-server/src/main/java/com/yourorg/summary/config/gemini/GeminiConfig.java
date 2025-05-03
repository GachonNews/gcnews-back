package com.yourorg.summary.config.gemini;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration; // ✅ 추가
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration // ✅ 필수 추가
public class GeminiConfig {
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
