package com.mancala.game.service;

import com.mancala.game.Model.Player;
import com.mancala.game.repository.PlayerRepository;
import com.mancala.game.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Player service.
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    /**
     * Instantiates a new Player service.
     *
     * @param playerRepository the player repository
     */
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player addPlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }
}
