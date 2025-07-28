package com.yeah.controller;

import com.yeah.dto.Top30PlayersRequest;
import com.yeah.entities.Top30Player;
import com.yeah.repositories.Top30PlayerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/top30")
public class Top30PlayerController {

    private final Top30PlayerRepository top30PlayerRepository;

    public Top30PlayerController(Top30PlayerRepository top30PlayerRepository) {
        this.top30PlayerRepository = top30PlayerRepository;
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

    @PostMapping("/upload")
    public ResponseEntity<String> uploadTop30Players(@RequestBody Top30PlayersRequest request) {
        // Validate week
        if (request.getWeek() <= 0 || request.getWeek() > 52) {
            return ResponseEntity.badRequest().body("Invalid week number. Week can only be 1-52.");
        }

        // Validate player count
/*        if (request.getPlayerNames().size() != 30) {
            return ResponseEntity.badRequest().body("Exactly 30 player names must be provided.");
        }*/

        // Check if the week already exists
        if (top30PlayerRepository.existsByWeek(request.getWeek())) {
            return ResponseEntity.badRequest().body("Week " + request.getWeek() + " already exists. Use PUT to update.");
        }

        // Save new players
        List<Top30Player> players = request.getPlayerNames().stream()
                .map(name -> new Top30Player(null, request.getWeek(), name))
                .collect(Collectors.toList());

        top30PlayerRepository.saveAll(players);
        return ResponseEntity.ok("Top 30 players uploaded successfully for week " + request.getWeek());
    }

    @PutMapping("/update/{week}")
    public ResponseEntity<String> updateTop30Players(@PathVariable int week, @RequestBody Top30PlayersRequest request) {
        // Validate week
        if (request.getWeek() <= 0 || request.getWeek() > 52) {
            return ResponseEntity.badRequest().body("Invalid week number. Week can only be 1-52.");
        }

        // Validate player count
/*        if (request.getPlayerNames().size() != 30) {
            return ResponseEntity.badRequest().body("Exactly 30 player names must be provided.");
        }*/

        // Check if the week exists
        if (!top30PlayerRepository.existsByWeek(week)) {
            return ResponseEntity.badRequest().body("Week " + week + " does not exist. Use POST to create.");
        }

        // Update players for the week
        top30PlayerRepository.deleteByWeek(week);
        List<Top30Player> players = request.getPlayerNames().stream()
                .map(name -> new Top30Player(null, week, name))
                .collect(Collectors.toList());

        top30PlayerRepository.saveAll(players);
        return ResponseEntity.ok("Top 30 players updated successfully for week " + week);
    }
}