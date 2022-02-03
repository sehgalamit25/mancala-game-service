package com.example.kalah.service;

import com.example.kalah.exception.ValidationException;
import com.example.kalah.model.Game;
import com.example.kalah.model.enums.GameState;
import com.example.kalah.repository.GameRepository;
import com.example.kalah.service.impl.DefaultGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * The type Game service test.
 */
@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    /**
     * The Game repository.
     */
    @Mock
    GameRepository gameRepository;

    /**
     * The Game service.
     */
    GameService gameService;

    /**
     * The Game.
     */
    Game game;

    /**
     * Sets .
     */
    @BeforeEach
    void setup() {
        game = Game.builder().player1(1).player2(2)
                .state(GameState.IN_PROGRESS)
                .turn(1).build();

        gameService = new DefaultGameService(gameRepository);
    }

    /**
     * Create game test.
     */
    @Test
    @DisplayName("Test Game Creation")
    void createGameTest() {
        when(gameRepository.save(game)).thenReturn(game);

        Game game = gameService.createGame();

        assertEquals(this.game, game);
    }

    /**
     * Find by id test success.
     */
    @Test
    @DisplayName("Test to find the game by id")
    void findByIdTest_Success()
    {
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        Game actual = gameService.findById(1L);

        assertEquals(this.game,actual);

   }

    /**
     * Find by id test failure.
     */
    @Test
    @DisplayName("Test to validation exception when no game exists")
    void findByIdTest_Failure()
    {
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ValidationException.class,()->gameService.findById(1L));

    }

    /**
     * Save test.
     */
    @Test
    @DisplayName("Test to check save game functionality")
    void saveTest()
    {
        when(gameRepository.save(game)).thenReturn(game);
        Game expected = Game.builder().player1(1).player2(2)
                .state(GameState.IN_PROGRESS)
                .turn(1).build();

        Game actual = gameService.save(this.game);

        assertEquals(expected, actual);
    }


}
