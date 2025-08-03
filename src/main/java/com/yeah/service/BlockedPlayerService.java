package com.yeah.service;

import com.yeah.entities.BlockedPlayer;
import com.yeah.repositories.BlockedPlayerRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockedPlayerService {
    private final BlockedPlayerRepository blockedPlayerRepository;

    public BlockedPlayerService(BlockedPlayerRepository blockedPlayerRepository) {
        this.blockedPlayerRepository = blockedPlayerRepository;
    }

    public List<String> findBlockedPlayersByWeeks(@Param("weeks") List<Integer> weeks) {
        return blockedPlayerRepository.findBlockedPlayersByWeeks(weeks);
    }

    public List<BlockedPlayer> findAllBlockedPlayers() {
        return blockedPlayerRepository.findAll();
    }

    public List<String> findBlockedPlayersLastNWeeks(int lastNWeeks) {
        List<Integer> recentWeeks = blockedPlayerRepository.findDistinctBlockWeeksDesc();
        List<Integer> topNWeeks = recentWeeks.stream().limit(lastNWeeks).toList();
        return blockedPlayerRepository.findBlockedPlayersByWeeks(topNWeeks);
    }

    public List<BlockedPlayer> findAllBlockedPlayersSortedByWeekDesc() {
        List<BlockedPlayer> players = blockedPlayerRepository.findAll();
        players.sort((a, b) -> Integer.compare(b.getBlockWeek(), a.getBlockWeek()));
        return players;
    }

}
