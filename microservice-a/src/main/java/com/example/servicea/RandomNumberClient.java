package com.example.servicea;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RandomNumberClient {

    private final RestClient restClient;

    public RandomNumberClient(
            RestClient.Builder builder,
            @Value("${services.microservice-b.base-url}") String serviceBBaseUrl) {
        this.restClient = builder.baseUrl(serviceBBaseUrl).build();
    }

    public RandomNumberResponse requestRandomNumberFromB() {
        return restClient.post()
                .uri("/api/random")
                .retrieve()
                .body(RandomNumberResponse.class);
    }
}
