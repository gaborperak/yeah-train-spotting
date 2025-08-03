package com.yeah.apicontroller;

import com.yeah.repositories.BlockedPlayerRepository;
import com.yeah.repositories.PlayerWinStreakRepository;
import com.yeah.repositories.Top30PlayerRepository;
import com.yeah.repositories.TrainWinnerRepository;
import com.yeah.service.DatabaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/db")
public class DatabaseController {
    private final DatabaseService databaseService;

    public DatabaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @DeleteMapping("/reset")
    public ResponseEntity<String> resetDatabase() {
        databaseService.resetDatabase();
        return ResponseEntity.ok("Database has been reset successfully.");
    }
}