package com.yourorg.gateway.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order(2)
public class LoggingWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        System.out.println("Request: " + exchange.getRequest().getURI());
        return chain.filter(exchange)
                .doOnTerminate(() -> 
                    System.out.println("Response: " + exchange.getResponse().getStatusCode())
                );
    }
}
