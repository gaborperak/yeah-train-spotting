package com.yeah.controller;

import com.yeah.entities.TrainWinner;
import com.yeah.service.DrawService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

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
    public String drawWinners(@PathVariable int week) {
        List<TrainWinner> winners = drawService.drawWinners(week, java.time.LocalDate.now());
        //print the winners with their days
        StringBuilder result = new StringBuilder("Winners for week " + week + ":\n");
        for (TrainWinner winner : winners) {
            result.append(winner.getPlayerName())
                  .append(" - Day: ")
                  .append(winner.getDay())
                  .append("\n");
        }
        return result.toString();
    }
}