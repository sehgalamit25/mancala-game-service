package com.example.kalah.service;

import com.example.kalah.exception.ValidationException;
import com.example.kalah.model.Game;

/**
 * The interface Game logic.
 */
public interface GameLogic {
    /**
     * Start new game game.
     *
     * @return the game
     */
    Game startNewGame();

    /**
     * Process move game.
     *
     * @param gameId    the game id
     * @param playerId  the player id
     * @param pitNumber the pit number
     * @return the game
     * @throws ValidationException the validation exception
     */
    Game processMove(Long gameId, Integer playerId, Integer pitNumber) throws ValidationException;

    Game getGame(Long id);
}
