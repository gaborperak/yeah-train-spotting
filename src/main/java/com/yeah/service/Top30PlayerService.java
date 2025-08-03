package com.yeah.service;

import com.yeah.dto.Top30PlayersRequest;
import com.yeah.entities.PlayerWinStreak;
import com.yeah.entities.Top30Player;
import com.yeah.repositories.PlayerWinStreakRepository;
import com.yeah.repositories.Top30PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Top30PlayerService {
    private final Top30PlayerRepository top30PlayerRepository;
    private final PlayerWinStreakRepository playerWinStreakRepository;
    private final BlockedPlayerService blockedPlayerService;

    public Top30PlayerService(Top30PlayerRepository top30PlayerRepository,
                              PlayerWinStreakRepository playerWinStreakRepository,
                              BlockedPlayerService blockedPlayerService) {
        this.top30PlayerRepository = top30PlayerRepository;
        this.playerWinStreakRepository = playerWinStreakRepository;
        this.blockedPlayerService = blockedPlayerService;
    }

    public void uploadTop30Players(Top30PlayersRequest request) {
        int week = request.getWeek();
        List<Top30Player> players = request.getPlayerNames().stream()
                .map(name -> new Top30Player(null, week, name))
                .collect(Collectors.toList());
        top30PlayerRepository.saveAll(players);
        List<String> blockedPlayersLast4Weeks = blockedPlayerService.findBlockedPlayersLastNWeeks(4);
        for (Top30Player player : players) {
            if (!blockedPlayersLast4Weeks.contains(player.getPlayerName())) {
                String playerName = player.getPlayerName();
                Optional<PlayerWinStreak> existing = playerWinStreakRepository.findByPlayerName(playerName);
                PlayerWinStreak streak = existing.orElse(new PlayerWinStreak());
                streak.setPlayerName(playerName);
                streak.setWinStreak(existing.map(s -> s.getWinStreak() + 1).orElse(1));
                playerWinStreakRepository.save(streak);
            }
        }
    }

    public void updateTop30Players(int week, Top30PlayersRequest request) {
        top30PlayerRepository.deleteByWeek(week);
        List<Top30Player> players = request.getPlayerNames().stream()
                .map(name -> new Top30Player(null, week, name))
                .collect(Collectors.toList());
        top30PlayerRepository.saveAll(players);
    }
}

