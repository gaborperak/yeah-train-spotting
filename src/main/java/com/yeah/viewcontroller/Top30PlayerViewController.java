package com.yeah.viewcontroller;

import com.yeah.entities.Top30Player;
import com.yeah.service.DrawService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class Top30PlayerViewController {

    private final DrawService drawService;

    public Top30PlayerViewController(DrawService drawService) {
        this.drawService = drawService;
    }

    // In Top30PlayerViewController.java
    @GetMapping("/top30")
    public String showTop30Players(Model model) {
        List<Top30Player> players = drawService.getTop30PlayersSortedByWeekDesc();
        model.addAttribute("allTop30Players", players);
        return "top30";
    }
    @GetMapping("/top30/weekly")
    public String showWeeklyTop30Players(@RequestParam("week") int week, Model model) {
        model.addAttribute("weeklyPlayers", drawService.getTop30PlayersByWeek(week));
        model.addAttribute("week", week);
        return "weeklyTop30";
    }
}