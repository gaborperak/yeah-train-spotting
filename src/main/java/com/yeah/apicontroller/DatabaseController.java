package com.yeah.apicontroller;

import com.yeah.entities.BlockedPlayer;
import com.yeah.entities.Top30Player;
import com.yeah.entities.TrainWinner;
import com.yeah.repositories.BlockedPlayerRepository;
import com.yeah.repositories.Top30PlayerRepository;
import com.yeah.repositories.TrainWinnerRepository;
import com.yeah.service.DatabaseService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/db")
public class DatabaseController {
    private final DatabaseService databaseService;
    private final BlockedPlayerRepository blockedPlayerRepository;
    private final Top30PlayerRepository top30PlayerRepository;
    private final TrainWinnerRepository trainWinnerRepository;

    public DatabaseController(
            DatabaseService databaseService,
            BlockedPlayerRepository blockedPlayerRepository,
            Top30PlayerRepository top30PlayerRepository,
            TrainWinnerRepository trainWinnerRepository
    ) {
        this.databaseService = databaseService;
        this.blockedPlayerRepository = blockedPlayerRepository;
        this.top30PlayerRepository = top30PlayerRepository;
        this.trainWinnerRepository = trainWinnerRepository;
    }

    @DeleteMapping("/reset")
    public ResponseEntity<String> resetDatabase() {
        databaseService.resetDatabase();
        return ResponseEntity.ok("Database has been reset successfully.");
    }

    @GetMapping(value = "/export/blocked-players", produces = MediaType.TEXT_PLAIN_VALUE)
    public String exportBlockedPlayers() {
        List<BlockedPlayer> players = blockedPlayerRepository.findAll();
        return players.stream()
                .sorted((p1, p2) -> Integer.compare(p2.getBlockWeek(), p1.getBlockWeek()))
                .map(p -> p.getPlayerName() + ", " + p.getBlockWeek())
                .collect(Collectors.joining("\n"));
    }

    @GetMapping(value = "/export/top-30-players", produces = MediaType.TEXT_PLAIN_VALUE)
    public String exportTop30Players() {
        List<Top30Player> players = top30PlayerRepository.findAll();
        return players.stream()
                .sorted((p1, p2) -> Integer.compare(p2.getWeek(), p1.getWeek()))
                .map(p -> p.getWeek() + "," + p.getPlayerName())
                .collect(Collectors.joining("\n"));
    }

    @GetMapping(value = "/export/train-winners", produces = MediaType.TEXT_PLAIN_VALUE)
    public String exportTrainWinners() throws InterruptedException {
        Thread.sleep(30000);
        List<TrainWinner> winners = trainWinnerRepository.findAll();
        return winners.stream()
                .sorted((w1, w2) -> Integer.compare(w2.getWeek(), w1.getWeek()))
                .map(w -> w.getWeek() + "," + w.getDay() + "," + w.getPlayerName())
                .collect(Collectors.joining("\n"));
    }
}