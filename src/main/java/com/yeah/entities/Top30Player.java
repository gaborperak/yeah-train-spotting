package com.yeah.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "top_30_players", uniqueConstraints = @UniqueConstraint(columnNames = {"week", "player_name"}))
public class Top30Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "week", nullable = false)
    private int week;

    @Column(name = "player_name", nullable = false, length = 100)
    private String playerName;

    // Constructors, getters, and setters
    public Top30Player() {}

    public Top30Player(Long id, int week, String playerName) {
        this.id = id;
        this.week = week;
        this.playerName = playerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}