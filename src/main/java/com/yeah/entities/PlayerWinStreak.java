package com.yeah.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "player_win_streaks")
public class PlayerWinStreak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String playerName;
    private Integer winStreak;

    public PlayerWinStreak() {}

    public PlayerWinStreak(String playerName, Integer winStreak) {
        this.playerName = playerName;
        this.winStreak = winStreak;
    }

    public String getPlayerName() {
        return playerName;
    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public Integer getWinStreak() {
        return winStreak;
    }
    public void setWinStreak(Integer winStreak) {
        this.winStreak = winStreak;
    }
}