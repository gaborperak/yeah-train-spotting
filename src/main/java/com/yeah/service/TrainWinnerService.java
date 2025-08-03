package com.yeah.service;

import com.yeah.entities.TrainWinner;
import com.yeah.repositories.TrainWinnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainWinnerService {
    private final TrainWinnerRepository trainWinnerRepository;

    public TrainWinnerService(TrainWinnerRepository trainWinnerRepository) {
        this.trainWinnerRepository = trainWinnerRepository;
    }

    public List<TrainWinner> findAllTrainWinners() {
        return trainWinnerRepository.findAll();
    }

    public List<TrainWinner> findTrainWinnersByWeek(int week) {
        return trainWinnerRepository.findByWeek(week);
    }

    public List<TrainWinner> findAllTrainWinnersSortedByWeekDesc() {
        List<TrainWinner> winners = findAllTrainWinners();
        winners.sort((a, b) -> Integer.compare(b.getWeek(), a.getWeek()));
        return winners;
    }
}