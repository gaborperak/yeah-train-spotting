package com.yeah.initialdata;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.boot.ApplicationRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class DataSeeder {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void seed() throws Exception {
        seedTop30Players();
        seedTrainWinners();
        seedBlockedPlayers();
    }

    private void seedTop30Players() throws Exception {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM top_30_players", Integer.class);
        if (count != null && count == 0) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new ClassPathResource("data/top_30_players.csv").getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",", 2);
                    jdbcTemplate.update("INSERT INTO top_30_players (week, player_name) VALUES (?, ?)",
                            Integer.parseInt(parts[0].trim()), parts[1].trim());
                }
            }
        }
    }

    private void seedTrainWinners() throws Exception {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM train_winners", Integer.class);
        if (count != null && count == 0) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new ClassPathResource("data/train_winners.csv").getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",", 3);
                    jdbcTemplate.update("INSERT INTO train_winners (week, day, player_name) VALUES (?, ?, ?)",
                            Integer.parseInt(parts[0].trim()), parts[1].trim(), parts[2].trim());
                }
            }
        }
    }

    private void seedBlockedPlayers() throws Exception {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM blocked_players", Integer.class);
        if (count != null && count == 0) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new ClassPathResource("data/blocked_players.csv").getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",", 2);
                    jdbcTemplate.update("INSERT INTO blocked_players (player_name, block_week) VALUES (?, ?)",
                            parts[0].trim(), Integer.parseInt(parts[1].trim()));
                }
            }
        }
    }
}