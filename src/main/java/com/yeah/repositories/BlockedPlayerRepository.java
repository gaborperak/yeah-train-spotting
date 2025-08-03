package com.yeah.repositories;

import com.yeah.entities.BlockedPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlockedPlayerRepository extends JpaRepository<BlockedPlayer, Long> {
    @Query("SELECT b.playerName FROM BlockedPlayer b WHERE b.blockWeek IN :weeks")
    List<String> findBlockedPlayersByWeeks(@Param("weeks") List<Integer> weeks);

    @Query("SELECT DISTINCT b.blockWeek FROM BlockedPlayer b ORDER BY b.blockWeek DESC")
    List<Integer> findDistinctBlockWeeksDesc();
}