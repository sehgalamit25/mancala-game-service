package com.example.kalah.repository;

import com.example.kalah.model.Game;
import com.example.kalah.model.Pit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PitRepository extends JpaRepository<Pit, Long> {

    /**
     * Find all by game and player list.
     *
     * @param game   the game
     * @param player the player
     * @return the list for a given game and player
     */
    List<Pit> findAllByGameAndPlayer(Game game, Integer player);

    /**
     * Find all by game and player and pit number optional.
     *
     * @param game      the game
     * @param player    the player
     * @param pitNumber the pit number
     * @return the pit for a given game and given player and given pitNumber
     */
    Optional<Pit> findAllByGameAndPlayerAndPitNumber(Game game, Integer player, Integer pitNumber);

    /**
     * Find all by game order by pit number list.
     *
     * @param game the game
     * @return the list of order pit for a given game
     */
    List<Pit> findAllByGameOrderByPitNumber(Game game);

}
