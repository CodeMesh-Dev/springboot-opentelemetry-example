package dev.codemesh.opentelemetry.cart;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfiguration {

    //interceptor is added automatically, need to check
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}