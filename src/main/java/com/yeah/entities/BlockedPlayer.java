package com.yeah.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "blocked_players")
public class BlockedPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_name", nullable = false)
    private String playerName;

    @Column(name = "block_month", nullable = false)
    private int blockMonth;

    // Default constructor for JPA
    public BlockedPlayer() {}

    public BlockedPlayer(Long id, String playerName, int blockMonth) {
        this.id = id;
        this.playerName = playerName;
        this.blockMonth = blockMonth;
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

    public int getBlockMonth() {
        return blockMonth;
    }

    public void setBlockMonth(int blockMonth) {
        this.blockMonth = blockMonth;
    }
}