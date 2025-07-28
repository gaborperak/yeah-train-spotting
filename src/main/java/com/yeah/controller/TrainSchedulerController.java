package com.yeah.controller;

import com.yeah.service.TrainSchedulerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/train-scheduler")
public class TrainSchedulerController {

    private final TrainSchedulerService trainSchedulerService;

    public TrainSchedulerController(TrainSchedulerService trainSchedulerService) {
        this.trainSchedulerService = trainSchedulerService;
    }

    @PostMapping("/draw-winners/{week}")
    @Operation(summary = "Draw winners for a specific week")
    public String drawWinners(@PathVariable int week) {
        trainSchedulerService.drawWinners(week);
        return "Winners for week " + week + " have been drawn.";
    }
}