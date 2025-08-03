package com.yeah.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "blocked_players")
public class BlockedPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_name", nullable = false, length = 100)
    private String playerName;

    @Column(name = "block_week", nullable = false)
    private int blockWeek;

    // Default constructor for JPA
    public BlockedPlayer() {}

    public BlockedPlayer(String playerName, int blockWeek) {
        this.playerName = playerName;
        this.blockWeek = blockWeek;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getBlockWeek() {
        return blockWeek;
    }

    public void setBlockWeek(int blockWeek) {
        this.blockWeek = blockWeek;
    }
}