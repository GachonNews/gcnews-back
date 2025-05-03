package com.yourorg.summary.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DeliveryConfig {

    @Bean("deliveryRestTemplate")
    public RestTemplate deliveryRestTemplate() {
        return new RestTemplate();
        // 필요 시 커스텀 설정 (예: 타임아웃, 인터셉터)
    }
}