package com.example.digitalassets.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class FlaskClientConfig {

    @Bean
    public WebClient flaskWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:5001")
                .build();
    }
}
