package com.mancala.game.repository;

import com.mancala.game.Model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Player repository.
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player,Long> {
}
