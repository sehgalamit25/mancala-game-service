package com.mancala.game.service;

import com.mancala.game.Model.Player;

import java.util.List;

/**
 * The interface Player service.
 */
public interface PlayerService {
    /**
     * Add player player.
     *
     * @param player the player
     * @return the player
     */
    Player addPlayer(Player player);

    /**
     * Gets players.
     *
     * @return the players
     */
    List<Player> getPlayers();
}
