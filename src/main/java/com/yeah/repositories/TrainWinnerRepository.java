package com.yeah.repositories;

import com.yeah.entities.TrainWinner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainWinnerRepository extends JpaRepository<TrainWinner, Long> {
}