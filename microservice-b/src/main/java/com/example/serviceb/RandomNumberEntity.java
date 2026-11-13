package com.example.serviceb;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "random_numbers")
public class RandomNumberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer value;

    @Column(name = "generated_at", nullable = false)
    private Instant generatedAt;

    protected RandomNumberEntity() {
    }

    public RandomNumberEntity(Integer value, Instant generatedAt) {
        this.value = value;
        this.generatedAt = generatedAt;
    }

    public Long getId() {
        return id;
    }

    public Integer getValue() {
        return value;
    }

    public Instant getGeneratedAt() {
        return generatedAt;
    }
}
