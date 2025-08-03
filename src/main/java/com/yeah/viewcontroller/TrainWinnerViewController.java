package com.yeah.viewcontroller;

import com.yeah.entities.TrainWinner;
import com.yeah.service.TrainWinnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TrainWinnerViewController {
    private final TrainWinnerService trainWinnerService;

    public TrainWinnerViewController(TrainWinnerService trainWinnerService) {
        this.trainWinnerService = trainWinnerService;
    }

    // In Top30PlayerViewController.java
    @GetMapping("/trainWinners")
    public String showTrainWinners(Model model) {
        List<TrainWinner> players = trainWinnerService.findAllTrainWinnersSortedByWeekDesc();
        model.addAttribute("allTrainWinners", players);
        return "trainWinners";
    }
}
