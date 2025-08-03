package com.yeah.apicontroller;

import com.yeah.dto.Top30PlayersRequest;
import com.yeah.entities.PlayerWinStreak;
import com.yeah.entities.Top30Player;
import com.yeah.repositories.BlockedPlayerRepository;
import com.yeah.repositories.PlayerWinStreakRepository;
import com.yeah.repositories.Top30PlayerRepository;
import com.yeah.repositories.TrainWinnerRepository;
import com.yeah.service.BlockedPlayerService;
import com.yeah.service.Top30PlayerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/top30")
public class Top30PlayerController {

    private final Top30PlayerRepository top30PlayerRepository;
    private final Top30PlayerService top30PlayerService;

    public Top30PlayerController(Top30PlayerRepository top30PlayerRepository, Top30PlayerService top30PlayerService) {
        this.top30PlayerRepository = top30PlayerRepository;
        this.top30PlayerService = top30PlayerService;
    }

    @GetMapping
    public ResponseEntity<List<Top30Player>> getAllTopPerformers() {
        List<Top30Player> players = top30PlayerRepository.findAll();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/{week}")
    public ResponseEntity<List<Top30Player>> getTopPerformersByWeek(@PathVariable int week) {
        List<Top30Player> players = top30PlayerRepository.findByWeek(week);
        return ResponseEntity.ok(players);
    }

    // Increment win streak for each uploaded player after saving them
    @PostMapping("/upload")
    public ResponseEntity<String> uploadTop30Players(@RequestBody Top30PlayersRequest request, @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        final String requiredPasswordHash = "4bf5a4735b2e4276d9ca52608ddcc6e5b153db9178e93e88c80b2109280fa919";
        if (authHeader == null || !hashMatches(authHeader, requiredPasswordHash)) {
            return ResponseEntity.status(401).body("Unauthorized: Missing or invalid Authorization header.");
        }
        if (request.getWeek() <= 0 || request.getWeek() > 52) {
            return ResponseEntity.badRequest().body("Invalid week number. Week can only be 1-52.");
        }
        if (request.getPlayerNames() == null || request.getPlayerNames().isEmpty()) {
            return ResponseEntity.badRequest().body("Player names list cannot be empty.");
        }
        if (request.getPlayerNames().size() != 30) {
            return ResponseEntity.badRequest().body("Exactly 30 player names must be provided.");
        }
        if (request.getPlayerNames().stream().anyMatch(name -> name == null || name.trim().isEmpty())) {
            return ResponseEntity.badRequest().body("All player names must be non-empty.");
        }
        if (request.getPlayerNames().stream().distinct().count() != request.getPlayerNames().size()) {
            return ResponseEntity.badRequest().body("Player names must be unique.");
        }
        if (top30PlayerRepository.existsByWeek(request.getWeek())) {
            return ResponseEntity.badRequest().body("Week " + request.getWeek() + " already exists. Use PUT to update.");
        }
        top30PlayerService.uploadTop30Players(request);
        return ResponseEntity.ok("Top 30 players uploaded successfully for week " + request.getWeek());
    }

    @PutMapping("/update/{week}")
    public ResponseEntity<String> updateTop30Players(@PathVariable int week, @RequestBody Top30PlayersRequest request) {
        if (request.getWeek() <= 0 || request.getWeek() > 52) {
            return ResponseEntity.badRequest().body("Invalid week number. Week can only be 1-52.");
        }
        if (request.getPlayerNames() == null || request.getPlayerNames().isEmpty()) {
            return ResponseEntity.badRequest().body("Player names list cannot be empty.");
        }
        if (request.getPlayerNames().size() != 30) {
            return ResponseEntity.badRequest().body("Exactly 30 player names must be provided.");
        }
        if (request.getPlayerNames().stream().anyMatch(name -> name == null || name.trim().isEmpty())) {
            return ResponseEntity.badRequest().body("All player names must be non-empty.");
        }
        if (request.getPlayerNames().stream().distinct().count() != request.getPlayerNames().size()) {
            return ResponseEntity.badRequest().body("Player names must be unique.");
        }
        if (!top30PlayerRepository.existsByWeek(week)) {
            return ResponseEntity.badRequest().body("Week " + week + " does not exist. Use POST to create.");
        }
        top30PlayerService.updateTop30Players(week, request);
        return ResponseEntity.ok("Top 30 players updated successfully for week " + week);
    }

    private boolean hashMatches(String password, String expectedHash) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().equals(expectedHash);
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }
}