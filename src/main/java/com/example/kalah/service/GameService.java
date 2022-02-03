package com.example.kalah.service;

import com.example.kalah.exception.ValidationException;
import com.example.kalah.model.Game;

/**
 * The interface Game service.
 */
public interface GameService {

    /**
     * Create game game.
     *
     * @return the game
     */
    Game createGame();

    /**
     * Find by id game.
     *
     * @param id the id
     * @return the game
     * @throws ValidationException the validation exception
     */
    Game findById(Long id) throws ValidationException;

    /**
     * Save game.
     *
     * @param game the game
     * @return the game
     */
    Game save(Game game);
}
