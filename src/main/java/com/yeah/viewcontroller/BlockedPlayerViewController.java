package com.yeah.viewcontroller;

import com.yeah.entities.BlockedPlayer;
import com.yeah.service.BlockedPlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BlockedPlayerViewController {
    private final BlockedPlayerService blockedPlayerService;

    public BlockedPlayerViewController(BlockedPlayerService blockedPlayerService) {
        this.blockedPlayerService = blockedPlayerService;
    }

    @GetMapping("/blockedPlayers")
    public String showBlockedPlayers(Model model) {
        List<BlockedPlayer> players = blockedPlayerService.findAllBlockedPlayersSortedByWeekDesc();
        model.addAttribute("blockedPlayers", players);
        return "blockedPlayers";
    }
}