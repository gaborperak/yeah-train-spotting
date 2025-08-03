package com.yeah.viewcontroller;

import com.yeah.entities.PlayerWinStreak;
import com.yeah.repositories.PlayerWinStreakRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WinstreakViewController {

    private final PlayerWinStreakRepository playerWinStreakRepository;

    public WinstreakViewController(PlayerWinStreakRepository playerWinStreakRepository) {
        this.playerWinStreakRepository = playerWinStreakRepository;
    }

    @GetMapping("/winstreaks")
    public String showWinstreaks(Model model) {
        List<PlayerWinStreak> winstreaks = playerWinStreakRepository.findAll();
        winstreaks.sort((a, b) -> Integer.compare(b.getWinStreak(), a.getWinStreak())); // Descending by streak
        model.addAttribute("winstreaks", winstreaks);
        return "winstreaks";
    }
}