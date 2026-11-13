package com.example.servicea;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RandomNumberController {

    private final RandomNumberClient randomNumberClient;

    public RandomNumberController(RandomNumberClient randomNumberClient) {
        this.randomNumberClient = randomNumberClient;
    }

    @GetMapping("/random")
    public RandomNumberResponse generateRandomNumber() {
        return randomNumberClient.requestRandomNumberFromB();
    }
}
