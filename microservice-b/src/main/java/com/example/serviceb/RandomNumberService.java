package com.example.serviceb;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RandomNumberService {

    private final RandomNumberRepository repository;
    private final StringRedisTemplate redisTemplate;

    public RandomNumberService(
            RandomNumberRepository repository,
            StringRedisTemplate redisTemplate) {
        this.repository = repository;
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public RandomNumberResponse generateAndStore() {
        int value = ThreadLocalRandom.current().nextInt(1, 1_000_001);
        Instant generatedAt = Instant.now();

        RandomNumberEntity saved = repository.save(
                new RandomNumberEntity(value, generatedAt));

        // Redis stores the generated value using the database ID as its key.
        redisTemplate.opsForValue().set(
                "random:number:" + saved.getId(),
                String.valueOf(saved.getValue()));

        return new RandomNumberResponse(
                saved.getId(), saved.getValue(), saved.getGeneratedAt());
    }
}
