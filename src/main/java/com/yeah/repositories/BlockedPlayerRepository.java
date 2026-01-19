package com.yeah.repositories;

import com.yeah.entities.BlockedPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlockedPlayerRepository extends JpaRepository<BlockedPlayer, Long> {
    @Query("SELECT b.playerName FROM BlockedPlayer b WHERE b.blockWeek IN :weeks")
    List<String> findBlockedPlayersByWeeks(@Param("weeks") List<Integer> weeks);

    @Query("SELECT b.playerName, b.blockWeek FROM BlockedPlayer b WHERE b.blockWeek IN :weeks")
    List<Object[]> findBlockedPlayersAndWeeksByWeeks(@Param("weeks") List<Integer> weeks);

    @Query("SELECT DISTINCT b.blockWeek FROM BlockedPlayer b ORDER BY b.blockWeek DESC")
    List<Integer> findDistinctBlockWeeksDesc();

    @Query("SELECT COUNT(b) > 0 FROM BlockedPlayer b WHERE b.playerName = :playerName AND b.blockWeek = :week")
    boolean existsByPlayerNameAndBlockWeek(@Param("playerName") String playerName, @Param("week") Integer week);
}