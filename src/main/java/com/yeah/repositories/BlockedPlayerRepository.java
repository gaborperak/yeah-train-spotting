package com.yeah.repositories;

import com.yeah.entities.BlockedPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlockedPlayerRepository extends JpaRepository<BlockedPlayer, Long> {
    List<BlockedPlayer> findByBlockMonth(int blockMonth);
}