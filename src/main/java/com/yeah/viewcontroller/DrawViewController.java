package com.yeah.viewcontroller;

import com.yeah.service.DrawService;
import com.yeah.entities.TrainWinner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
public class DrawViewController {

    private final DrawService drawService;

    @Autowired
    public DrawViewController(DrawService drawService) {
        this.drawService = drawService;
    }

    @GetMapping("/drawWinners")
    public String showDrawForm() {
        return "drawWinners";
    }

    @PostMapping("/drawWinners")
    public String drawWinners(@RequestParam("week") int week, @RequestParam("password") String password, Model model) {
        List<TrainWinner> winners = drawService.drawWinners(week, LocalDate.now());
        model.addAttribute("winners", winners);
        model.addAttribute("success", true);
        model.addAttribute("week", week);
        return "drawWinners";
    }
}