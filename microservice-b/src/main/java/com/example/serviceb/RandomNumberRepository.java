package com.example.serviceb;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RandomNumberRepository extends JpaRepository<RandomNumberEntity, Long> {
}
