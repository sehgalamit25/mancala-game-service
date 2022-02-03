package com.example.kalah.service.impl;

import com.example.kalah.exception.ValidationException;
import com.example.kalah.model.Game;
import com.example.kalah.model.enums.GameState;
import com.example.kalah.model.enums.Player;
import com.example.kalah.repository.GameRepository;
import com.example.kalah.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Default game service.
 */
@Service
public class DefaultGameService implements GameService {

    private final GameRepository gameRepository;

    /**
     * Instantiates a new Default game service.
     *
     * @param gameRepository the game repository
     */
    @Autowired
    public DefaultGameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    @Transactional
    public Game createGame() {
        Game game = Game.builder()
                .player1(Player.ONE.getValue())
                .player2(Player.TWO.getValue())
                .state(GameState.IN_PROGRESS)
                .turn(Player.ONE.getValue())
                .build();
        return gameRepository.save(game);
    }

    @Override
    public Game findById(final Long id) throws ValidationException {
        return gameRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Game not found with id :: " + id));
    }

    @Override
    @Transactional
    public Game save(Game game) {
        return gameRepository.save(game);
    }
}
