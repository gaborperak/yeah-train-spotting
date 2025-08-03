package com.yeah.apicontroller;

import com.yeah.entities.TrainWinner;
import com.yeah.service.DrawService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/api/train-scheduler")
public class TrainSchedulerController {

    private final DrawService drawService;

    public TrainSchedulerController(DrawService drawService) {
        this.drawService = drawService;
    }

    @PostMapping("/draw-winners/{week}")
    @Operation(summary = "Draw winners for a specific week")
    public ResponseEntity<List<TrainWinner>> drawWinners(@PathVariable int week, @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        final String requiredPasswordHash = "4bf5a4735b2e4276d9ca52608ddcc6e5b153db9178e93e88c80b2109280fa919";
        if (authHeader == null || !hashMatches(authHeader, requiredPasswordHash)) {
            return ResponseEntity.status(401).build();
        }
        List<TrainWinner> winners = drawService.drawWinners(week, java.time.LocalDate.now());
        return ResponseEntity.ok(winners);
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