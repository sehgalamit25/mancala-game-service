package com.example.kalah.service;

import com.example.kalah.model.Game;
import com.example.kalah.model.Pit;
import com.example.kalah.model.enums.Player;

import java.util.Collection;

/**
 * The interface Pit service.
 */
public interface PitService {
    /**
     * Create pits for players collection.
     *
     * @param game the game
     * @return the collection
     */
    Collection<Pit> createPitsForPlayers(Game game);

    /**
     * List all pits of player collection.
     *
     * @param game   the game
     * @param player the player
     * @return the collection
     */
    Collection<Pit> listAllPitsOfPlayer(Game game, Integer player);

    /**
     * Gets pit.
     *
     * @param game      the game
     * @param player    the player
     * @param pitNumber the pit number
     * @return the pit
     */
    Pit getPit(Game game, Player player, Integer pitNumber);

    /**
     * List all pits order by pit number collection.
     *
     * @param game the game
     * @return the collection
     */
    Collection<Pit> listAllPitsOrderByPitNumber(Game game);

    /**
     * Save all collection.
     *
     * @param pits the pits
     * @return the collection
     */
    Collection<Pit> saveAll(Collection<Pit> pits);
}
