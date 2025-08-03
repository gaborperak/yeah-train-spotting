package com.yeah.repositories;

import com.yeah.entities.PlayerWinStreak;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerWinStreakRepository extends JpaRepository<PlayerWinStreak, Long> {


    Optional<PlayerWinStreak> findByPlayerName(String playerName);
}