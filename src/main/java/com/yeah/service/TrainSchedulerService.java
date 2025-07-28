package com.yeah.service;

import com.yeah.entities.BlockedPlayer;
import com.yeah.entities.Top30Player;
import com.yeah.entities.TrainWinner;
import com.yeah.repositories.BlockedPlayerRepository;
import com.yeah.repositories.Top30PlayerRepository;
import com.yeah.repositories.TrainWinnerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainSchedulerService {

    private final Top30PlayerRepository top30PlayerRepository;
    private final TrainWinnerRepository trainWinnerRepository;
    private final BlockedPlayerRepository blockedPlayerRepository;

    public TrainSchedulerService(Top30PlayerRepository top30PlayerRepository,
                                 TrainWinnerRepository trainWinnerRepository,
                                 BlockedPlayerRepository blockedPlayerRepository) {
        this.top30PlayerRepository = top30PlayerRepository;
        this.trainWinnerRepository = trainWinnerRepository;
        this.blockedPlayerRepository = blockedPlayerRepository;
    }

    public void drawWinners(int week) {
        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();

        List<Top30Player> topPlayers = top30PlayerRepository.findByWeek(week);
        List<String> blockedPlayers = blockedPlayerRepository.findByBlockMonth(currentMonth)
                .stream()
                .map(BlockedPlayer::getPlayerName) // Use the getter method
                .collect(Collectors.toList());

        List<String> eligiblePlayers = topPlayers.stream()
                .map(Top30Player::getPlayerName) // Use the getter method
                .filter(player -> !blockedPlayers.contains(player))
                .collect(Collectors.toList());

        Collections.shuffle(eligiblePlayers);

        for (int day = 1; day <= 7; day++) {
            if (eligiblePlayers.isEmpty()) {
                break;
            }

            String winner = eligiblePlayers.remove(0);
            trainWinnerRepository.save(new TrainWinner(null, week, day, winner));
            blockedPlayerRepository.save(new BlockedPlayer(null, winner, currentMonth));
        }
    }
}