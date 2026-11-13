package com.example.serviceb;

import java.time.Instant;

public record RandomNumberResponse(Long id, Integer value, Instant generatedAt) {
}
