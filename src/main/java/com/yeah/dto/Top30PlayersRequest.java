package com.yeah.dto;

import java.util.List;

public class Top30PlayersRequest {

    private int week;
    private List<String> playerNames;

    // Getters and setters
    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(List<String> playerNames) {
        this.playerNames = playerNames;
    }
}