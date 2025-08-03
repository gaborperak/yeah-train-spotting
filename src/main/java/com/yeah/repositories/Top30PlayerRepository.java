package com.yeah.repositories;

import com.yeah.entities.Top30Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Top30PlayerRepository extends JpaRepository<Top30Player, Long> {
    List<Top30Player> findByWeek(int week);

    boolean existsByWeek(int week);

    void deleteByWeek(int week);

    @Query("SELECT t.playerName FROM Top30Player t WHERE t.week = :week")
    List<String> findPlayerNamesByWeek(@Param("week") int week);

    @Query("SELECT t FROM Top30Player t WHERE t.week = :week AND t.playerName = :playerName")
    Top30Player findByWeekAndPlayerName(int week, String playerName);

    @Query("SELECT MAX(t.week) FROM Top30Player t")
    Integer findMaxWeek();

    @Query("SELECT MIN(t.week) FROM Top30Player t")
    Integer findMinWeek();

}