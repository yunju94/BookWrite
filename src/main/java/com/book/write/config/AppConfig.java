package com.book.write.config;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    String apiKey = "6670820564805133";
    String secretKey = "Wghm8njzidJI4XhfBuxRDb3cXVlVXqJwz7xMd7VXpJvDlM2pLgdjNJzvKGQU60T2QhZC2msAWZloOqBc";

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(apiKey, secretKey);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
