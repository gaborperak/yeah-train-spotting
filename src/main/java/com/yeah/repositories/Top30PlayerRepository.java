package com.yeah.repositories;

import com.yeah.entities.Top30Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Top30PlayerRepository extends JpaRepository<Top30Player, Long> {
    List<Top30Player> findByWeek(int week);
}