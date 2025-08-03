package com.yeah.repositories;

import com.yeah.entities.TrainWinner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainWinnerRepository extends JpaRepository<TrainWinner, Long> {
    List<TrainWinner> findByWeek(int week);

    @Query("SELECT t.playerName FROM TrainWinner t WHERE t.week IN :weeks")
    List<String> findPlayerNamesByWeeks(@Param("weeks") List<Integer> weeks);
}