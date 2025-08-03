package com.yeah.service;

import com.yeah.entities.BlockedPlayer;
import com.yeah.entities.PlayerWinStreak;
import com.yeah.entities.Top30Player;
import com.yeah.entities.TrainWinner;
import com.yeah.repositories.BlockedPlayerRepository;
import com.yeah.repositories.PlayerWinStreakRepository;
import com.yeah.repositories.Top30PlayerRepository;
import com.yeah.repositories.TrainWinnerRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@DependsOn("dataSeeder")
@Service
public class DrawService {

    private final Top30PlayerRepository top30PlayerRepository;
    private final TrainWinnerRepository trainWinnerRepository;
    private final BlockedPlayerRepository blockedPlayerRepository;
    private final PlayerWinStreakRepository playerWinStreakRepository;

    public DrawService(Top30PlayerRepository top30PlayerRepository,
                       TrainWinnerRepository trainWinnerRepository,
                       BlockedPlayerRepository blockedPlayerRepository,
                       PlayerWinStreakRepository playerWinStreakRepository) {
        this.playerWinStreakRepository = playerWinStreakRepository;
        this.top30PlayerRepository = top30PlayerRepository;
        this.trainWinnerRepository = trainWinnerRepository;
        this.blockedPlayerRepository = blockedPlayerRepository;
    }

    @PostConstruct
    public void recalculateAllWinstreaks() {
        Integer latestWeek = top30PlayerRepository.findMaxWeek();
        Integer minWeek = top30PlayerRepository.findMinWeek();
        if (latestWeek == null) return;

        List<Top30Player> allTop30Players = top30PlayerRepository.findAll();
        Set<String> allPlayerNames = allTop30Players.stream()
                .map(Top30Player::getPlayerName)
                .collect(Collectors.toSet());

        for (String playerName : allPlayerNames) {
            // Check if blocked in the latest 4 weeks
            List<Integer> last4Weeks = List.of(latestWeek, latestWeek - 1, latestWeek - 2, latestWeek - 3);
            List<String> blocked = trainWinnerRepository.findPlayerNamesByWeeks(last4Weeks);
            int winstreak = 1;
            if (!blocked.contains(playerName)) {
                winstreak = 1;
                int week = latestWeek - 1;
                while (week >= minWeek) {
                    Top30Player prev = top30PlayerRepository.findByWeekAndPlayerName(week, playerName);
                    if (prev == null) break;
                    List<Integer> prevLast4Weeks = List.of(week, week - 1, week - 2, week - 3);
                    List<String> prevBlocked = trainWinnerRepository.findPlayerNamesByWeeks(prevLast4Weeks);
                    if (prevBlocked.contains(playerName)) break;
                    winstreak++;
                    week--;
                }
            } else {
                winstreak = 0; // Reset streak if blocked
            }
            // Save or update win streak
            Optional<PlayerWinStreak> existing = playerWinStreakRepository.findByPlayerName(playerName);
            PlayerWinStreak streak = existing.orElse(new PlayerWinStreak());
            streak.setPlayerName(playerName);
            streak.setWinStreak(winstreak);
            playerWinStreakRepository.save(streak);
        }
    }

    public List<Top30Player> getTop30Players() {
        return top30PlayerRepository.findAll();
    }

    public List<Top30Player> getTop30PlayersByWeek(int week) {
        return top30PlayerRepository.findByWeek(week);
    }

    public List<TrainWinner> drawWinners(int currentWeek, LocalDate drawDate) {

        // Check if winners already exist for the given week
        List<TrainWinner> existingWinners = trainWinnerRepository.findByWeek(currentWeek);
        if (!existingWinners.isEmpty()) {
            throw new IllegalStateException("Winners have already been drawn for week " + currentWeek);
        }

        // Fetch Top30Player entities for the week
        List<Top30Player> top30Players = top30PlayerRepository.findByWeek(currentWeek);
        if (top30Players.isEmpty()) {
            throw new IllegalStateException("You haven't uploaded this weeks records ");
        }

        // Get blocked players from the last 4 weeks
        List<Integer> last4Weeks = List.of(currentWeek - 1, currentWeek - 2, currentWeek - 3, currentWeek - 4);
        List<String> blocked = blockedPlayerRepository.findBlockedPlayersByWeeks(last4Weeks);

        // remove blocked players from the top30Players list
        List<String> finalBlocked4weeks = blocked;
        List<Top30Player> top30PlayersFiltered = top30Players.stream()
                .filter(player -> !finalBlocked4weeks.contains(player.getPlayerName()))
                .toList();

        //less than 7
        if (top30PlayersFiltered.size() < 7) {
            List<Integer> last3weeks = List.of(currentWeek - 1, currentWeek - 2, currentWeek - 3);
            blocked = blockedPlayerRepository.findBlockedPlayersByWeeks(last3weeks);
            List<String> finalBlocked3weeks = blocked;
            top30PlayersFiltered = top30Players.stream()
                    .filter(player -> !finalBlocked3weeks.contains(player.getPlayerName()))
                    .toList();
        }

        // Build the draw pool: each player appears as many times as their winstreak
        List<Top30Player> drawPool = new ArrayList<>();
        for (Top30Player player : top30PlayersFiltered) {
                int winStreak = playerWinStreakRepository
                        .findByPlayerName(player.getPlayerName())
                        .map(PlayerWinStreak::getWinStreak)
                        .orElse(1);
                for (int i = 0; i < winStreak; i++) {
                    drawPool.add(player);
                }
        }

        // Shuffle and pick up to 7 unique winners
        Collections.shuffle(drawPool);
        List<Top30Player> uniqueWinners = drawPool.stream()
                .distinct()
                .limit(7)
                .toList();

        List<TrainWinner> winnersWithDays = new ArrayList<>();
        int i = 0;
        for (Top30Player winner : uniqueWinners) {
            i++;
            TrainWinner winnerWithDay = new TrainWinner(
                    winner.getPlayerName(),
                    currentWeek,
                    java.time.DayOfWeek.of(i).name()
            );
            winnersWithDays.add(winnerWithDay);

            // reset win streak to 0
            Optional<PlayerWinStreak> existingStreak = playerWinStreakRepository.findByPlayerName(winner.getPlayerName());
            if (existingStreak.isPresent()) {
                PlayerWinStreak streak = existingStreak.get();
                streak.setWinStreak(0);
                playerWinStreakRepository.save(streak);
            }

            trainWinnerRepository.save(winnerWithDay);
            blockedPlayerRepository.save(new BlockedPlayer(winner.getPlayerName(), currentWeek));
        }
        return winnersWithDays;
    }

    public List<Top30Player> getTop30PlayersSortedByWeekDesc() {
        List<Top30Player> players = top30PlayerRepository.findAll();
        players.sort((a, b) -> Integer.compare(b.getWeek(), a.getWeek()));
        return players;
    }
}