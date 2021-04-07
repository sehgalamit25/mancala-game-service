package com.mancala.game.controller;

import com.mancala.game.Model.Player;
import com.mancala.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Player controller.
 */
@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    /**
     * Instantiates a new Player controller.
     *
     * @param playerService the player service
     */
    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Add player player.
     *
     * @param player the player
     * @return the player
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Player addPlayer(@RequestBody Player player) {
        return playerService.addPlayer(player);
    }

    /**
     * Gets players.
     *
     * @return the players
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Player> getPlayers() {
        return playerService.getPlayers();
    }
}
