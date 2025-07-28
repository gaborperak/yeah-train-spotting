package com.yeah.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public record TrainWinner(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,
        int week,
        int day,
        String playerName
) {
    // Default constructor for JPA
    public TrainWinner() {
        this(null, 0, 0, null);
    }
}