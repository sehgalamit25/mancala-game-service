package com.example.kalah.controller;

import com.example.kalah.dto.ErrorDetails;
import com.example.kalah.exception.ValidationException;
import com.example.kalah.model.Game;
import com.example.kalah.service.GameLogic;
import com.example.kalah.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * The type Game controller.
 */
@RestController
@RequestMapping("/game")
@Validated
public class GameController {

    private final GameLogic gameLogic;

    /**
     * Instantiates a new Game controller.
     *
     * @param gameLogic   the game logic
     */
    @Autowired
    public GameController(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    /**
     * Gets game.
     *
     * @param id the id
     * @return the game with given id
     * @throws ValidationException the validation exception
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get game status By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns game status",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Game.class))),
            @ApiResponse(responseCode = "400", description = "Bad request.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class)))
    })
    public Game getGame(@PathVariable final Long id) throws ValidationException {
        return gameLogic.getGame(id);
      //  return gameService.findById(id);
    }

    /**
     * Start new game .
     *
     * @return returns new game
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Start a game")
    @ApiResponse(responseCode = "201", description = "Returns Game and Player's Id",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Game.class)))
    public Game startNewGame() {
        return gameLogic.startNewGame();
    }


    /**
     * Move stones
     *
     * @param gameId    the game id
     * @param playerId  the player id
     * @param pitNumber the pit number
     * @return the game after the move
     * @throws ValidationException the validation exception
     */
    @PostMapping(value = "/{gameId}/player/{playerId}/pit/{pitNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Player X of Game ID Y Select the Pit Z to move")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns Game Status with Board",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Game.class))),
            @ApiResponse(responseCode = "400", description = "Bad request.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class)))
    })
    public Game moveStones(@PathVariable final Long gameId,
                           @PathVariable @Min(1) @Max(2) final Integer playerId,
                           @PathVariable @Min(1) @Max(14) final Integer pitNumber) throws ValidationException {
        if (pitNumber == 7 || pitNumber == 14) {
            throw new ValidationException("Operation not allowed on Pit 7 and 14 !!!");
        }
        return gameLogic.processMove(gameId, playerId, pitNumber);
    }
}
