package com.example.kalah.controller;

import com.example.kalah.exception.ValidationException;
import com.example.kalah.model.Game;
import com.example.kalah.model.enums.GameState;
import com.example.kalah.service.GameLogic;
import com.example.kalah.service.GameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Game controller test.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;
    @MockBean
    private GameLogic gameLogic;

    /**
     * The Game.
     */
    final Game game = Game.builder().id(1L)
            .player1(1).player2(2).player1Pits("6").player2Pits("6")
            .turn(1).state(GameState.IN_PROGRESS).build();

    /**
     * Start new game test.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Test /game endpoint ")
    void startNewGameTest() throws Exception {
        given(gameLogic.startNewGame()).willReturn(game);

        this.mockMvc.perform(post("/game")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));
    }

    /**
     * Process move test.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Test player move endpoint")
    void processMoveTest_Success() throws Exception {

        var gameID = 1L;
        var playerId = 1;
        var pitNumber = 1;

        given(gameLogic.processMove(gameID, playerId, pitNumber)).willReturn(game);

        this.mockMvc.perform(post("/game/{gameID}/player/{playerId}/pit/{pitNumber}", gameID, playerId, pitNumber)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.state", is("IN_PROGRESS")))
                .andExpect(jsonPath("$.turn", is(1)));

    }

    /**
     * Process move test failure.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Test bad request for player move endpoint")
    void processMoveTest_Failure() throws Exception
    {
        var gameID = 1L;
        var playerId = 1;
        var pitNumber = 1;
        var exceptionMessage="Bad Request";

        given(gameLogic.processMove(gameID, playerId, pitNumber)).willThrow(new ValidationException(exceptionMessage));

        this.mockMvc.perform(post("/game/{gameID}/player/{playerId}/pit/{pitNumber}", gameID, playerId, pitNumber)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.message", is(exceptionMessage)));

    }

    @Test
    @DisplayName("Test when big pit is selected")
    void processMoveTest_Failure_BigPitSelected() throws Exception
    {
        var gameID = 1L;
        var playerId = 1;
        var pitNumber = 7;
        var exceptionMessage="Operation not allowed on Pit 7 and 14 !!!";

        given(gameLogic.processMove(gameID, playerId, pitNumber)).willThrow(new ValidationException(exceptionMessage));

        this.mockMvc.perform(post("/game/{gameID}/player/{playerId}/pit/{pitNumber}", gameID, playerId, pitNumber)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andExpect(jsonPath("$.message", is(exceptionMessage)));

    }

    /**
     * Gets game test failure.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Test game not found for get game endpoint")
    void getGameTest_Failure() throws Exception {
        var id = 2L;
        var exceptionMessage = "Game not found with id :: 2";
        given(gameLogic.getGame(id)).willThrow(new ValidationException(exceptionMessage));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/game/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is(exceptionMessage)));

    }

    /**
     * Gets game test success.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Test get game endpoint")
    void getGameTest_Success() throws Exception {
        var id = 1L;
        given(gameLogic.getGame(1L)).willReturn(game);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/game/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state", is("IN_PROGRESS")))
                .andExpect(jsonPath("$.turn", is(1)));

    }

}
