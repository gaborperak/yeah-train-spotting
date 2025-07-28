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

    @PostMapping("/upload")
    public ResponseEntity<String> uploadTop30Players(@RequestBody Top30PlayersRequest request) {
        List<Top30Player> players = request.getPlayerNames().stream()
                .map(name -> new Top30Player(null, request.getWeek(), name))
                .collect(Collectors.toList());

        top30PlayerRepository.saveAll(players);
        return ResponseEntity.ok("Top 30 players uploaded successfully for week " + request.getWeek());
    }
}