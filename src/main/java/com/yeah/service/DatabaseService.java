package com.yeah.service;

import com.yeah.repositories.BlockedPlayerRepository;
import com.yeah.repositories.PlayerWinStreakRepository;
import com.yeah.repositories.Top30PlayerRepository;
import com.yeah.repositories.TrainWinnerRepository;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {
    private final Top30PlayerRepository top30PlayerRepository;
    private final TrainWinnerRepository trainWinnerRepository;
    private final BlockedPlayerRepository blockedPlayerRepository;
    private final PlayerWinStreakRepository playerWinStreakRepository;

    public DatabaseService(Top30PlayerRepository top30PlayerRepository,
                          TrainWinnerRepository trainWinnerRepository,
                          BlockedPlayerRepository blockedPlayerRepository,
                          PlayerWinStreakRepository playerWinStreakRepository) {
        this.top30PlayerRepository = top30PlayerRepository;
        this.trainWinnerRepository = trainWinnerRepository;
        this.blockedPlayerRepository = blockedPlayerRepository;
        this.playerWinStreakRepository = playerWinStreakRepository;
    }

    public void resetDatabase() {
        top30PlayerRepository.deleteAll();
        blockedPlayerRepository.deleteAll();
        trainWinnerRepository.deleteAll();
        playerWinStreakRepository.deleteAll();
    }
}

