package com.yeah.controller;

import com.yeah.repositories.BlockedPlayerRepository;
import com.yeah.repositories.Top30PlayerRepository;
import com.yeah.repositories.TrainWinnerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/db")
public class DatabaseController {

    private final Top30PlayerRepository top30PlayerRepository;
    private final TrainWinnerRepository trainWinnersRepository;
    private final BlockedPlayerRepository blockedPlayersRepository;

    public DatabaseController(Top30PlayerRepository top30PlayerRepository,
                              TrainWinnerRepository trainWinnerRepository,
                              BlockedPlayerRepository blockedPlayersRepository) {
        this.top30PlayerRepository = top30PlayerRepository;
        this.blockedPlayersRepository = blockedPlayersRepository;
        this.trainWinnersRepository = trainWinnerRepository;
    }

    @DeleteMapping("/reset")
    public ResponseEntity<String> resetDatabase() {
        top30PlayerRepository.deleteAll();
        blockedPlayersRepository.deleteAll();
        trainWinnersRepository.deleteAll();
        return ResponseEntity.ok("Database has been reset successfully.");
    }
}