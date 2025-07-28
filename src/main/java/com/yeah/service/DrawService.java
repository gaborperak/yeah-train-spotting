package com.yeah.service;

import com.yeah.entities.BlockedPlayer;
import com.yeah.entities.TrainWinner;
import com.yeah.repositories.BlockedPlayerRepository;
import com.yeah.repositories.Top30PlayerRepository;
import com.yeah.repositories.TrainWinnerRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DrawService {

    private final Top30PlayerRepository top30PlayerRepository;
    private final TrainWinnerRepository trainWinnerRepository;
    private final BlockedPlayerRepository blockedPlayerRepository;

    public DrawService(Top30PlayerRepository top30PlayerRepository,
                       TrainWinnerRepository trainWinnerRepository,
                       BlockedPlayerRepository blockedPlayerRepository) {
        this.top30PlayerRepository = top30PlayerRepository;
        this.trainWinnerRepository = trainWinnerRepository;
        this.blockedPlayerRepository = blockedPlayerRepository;
    }

    public List<TrainWinner> drawWinners(int currentWeek, LocalDate drawDate) {
        // Uncomment to restrict to Sundays only
        // if (drawDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
        //     throw new IllegalStateException("Draws are only allowed on Sundays.");
        // }

        //we make the draw for the next week on Sundays, so when you say currentWeek its the week that is ending
        //after buster day
        List<String> top30 = top30PlayerRepository.findPlayerNamesByWeek(currentWeek);

        List<Integer> last4Weeks = List.of(currentWeek - 1, currentWeek - 2, currentWeek - 3, currentWeek - 4);
        List<String> blocked = blockedPlayerRepository.findBlockedPlayersByWeeks(last4Weeks);

        List<String> eligible = top30.stream()
                .filter(p -> !blocked.contains(p))
                .collect(Collectors.toList());

        // If there are not enough eligible players, reduce the block list to ensure we can still draw 7 winners
        if (eligible.size() < 7) {
            List<Integer> last3Weeks = List.of(currentWeek - 1, currentWeek - 2, currentWeek - 3);
            List<String> blocked3Weeks = blockedPlayerRepository.findBlockedPlayersByWeeks(last3Weeks);
            eligible = top30.stream()
                    .filter(p -> !blocked3Weeks.contains(p))
                    .collect(Collectors.toList());
        }

        Collections.shuffle(eligible);
        List<String> winners = eligible.stream().limit(7).toList();
        List<TrainWinner> winnersWithDays = new ArrayList<>();

        int i = 0;
        for (String winner : winners) {
            i++;
            // Create a TrainWinner object for each winner with the corresponding day of the week
            TrainWinner winnerWithDay = new TrainWinner(winner,currentWeek, java.time.DayOfWeek.of(i).name());
            winnersWithDays.add(winnerWithDay);

            trainWinnerRepository.save(winnerWithDay);
            blockedPlayerRepository.save(new BlockedPlayer(winner, currentWeek));
        }

        return winnersWithDays;
    }
}