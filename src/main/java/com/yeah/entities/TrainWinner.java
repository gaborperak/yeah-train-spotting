package com.yeah.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "train_winners",
        uniqueConstraints = @UniqueConstraint(columnNames = {"week", "day", "player_name"})
)
public class TrainWinner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int week;

    @Column(nullable = false)
    private String day;

    @Column(name = "player_name", nullable = false)
    private String playerName;

    // Default constructor for JPA
    public TrainWinner() {}

    public TrainWinner(String playerName, int week, String day) {
        this.playerName = playerName;
        this.week = week;
        this.day = day;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}