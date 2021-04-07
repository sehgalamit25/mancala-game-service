package com.mancala.game.controller;

import com.mancala.game.Model.Game;
import com.mancala.game.Model.Pit;
import com.mancala.game.service.GameService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * The type Game controller.
 */
@RestController("/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping(value = "/create",produces = MediaType.APPLICATION_JSON_VALUE)
    public Game createGame(){
        return gameService.create();
    }

    @PostMapping(value = "/{gameId}/player/{playerid}/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Game move(@PathVariable Long gameId,@PathVariable Long playerid,@PathVariable Integer index) throws Exception {
        return gameService.move(gameId,playerid,index);
    }
}
