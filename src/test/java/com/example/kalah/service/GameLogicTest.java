package com.example.kalah.service;

import com.example.kalah.exception.ValidationException;
import com.example.kalah.model.Game;
import com.example.kalah.model.Pit;
import com.example.kalah.model.enums.GameState;
import com.example.kalah.model.enums.PitType;
import com.example.kalah.model.enums.Player;
import com.example.kalah.service.impl.DefaultGameLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * The type Game logic test.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GameLogicTest {


    public Pit pit1,
            pit2,
            pit3,
            pit4,
            pit5,
            pit6,
            pit7,
            pit8,
            pit9,
            pit10,
            pit11,
            pit12,
            pit13,
            pit14;
    public Game game;

    public Map<Integer, Pit> pitMap;

    DefaultGameLogic defaultGameLogic;
    @Mock
    private GameService gameService;
    @Mock
    private PitService pitService;


    /**
     * Initial Setup for the test.
     */
    @BeforeEach
    void setup() {
        game = Game.builder().id(1L).player1(1).player2(2).turn(1).state(GameState.IN_PROGRESS).build();


        pit1 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(1).stoneCount(6).type(PitType.SMALL).build();
        pit2 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(2).stoneCount(6).type(PitType.SMALL).build();
        pit3 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(3).stoneCount(6).type(PitType.SMALL).build();
        pit4 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(4).stoneCount(6).type(PitType.SMALL).build();
        pit5 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(5).stoneCount(6).type(PitType.SMALL).build();
        pit6 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(6).stoneCount(6).type(PitType.SMALL).build();
        pit7 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(7).stoneCount(0).type(PitType.BIG).build();

        pit8 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(8).stoneCount(6).type(PitType.SMALL).build();
        pit9 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(9).stoneCount(6).type(PitType.SMALL).build();
        pit10 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(10).stoneCount(6).type(PitType.SMALL).build();
        pit11 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(11).stoneCount(6).type(PitType.SMALL).build();
        pit12 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(12).stoneCount(6).type(PitType.SMALL).build();
        pit13 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(13).stoneCount(6).type(PitType.SMALL).build();
        pit14 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(14).stoneCount(0).type(PitType.BIG).build();

        pitMap = Map.ofEntries(Map.entry(1, pit1), Map.entry(2, pit2), Map.entry(3, pit3), Map.entry(4, pit4), Map.entry(5, pit5), Map.entry(6, pit6), Map.entry(7, pit7), Map.entry(8, pit8), Map.entry(9, pit9), Map.entry(10, pit10), Map.entry(11, pit11), Map.entry(12, pit12), Map.entry(13, pit13), Map.entry(14, pit14));

        defaultGameLogic = Mockito.spy(new DefaultGameLogic(gameService, pitService));
    }


    /**
     * Process move main test failure.
     */
    @Test
    @DisplayName("Test process move for empty pit selected")
    void processMoveMainTest_Failure() {

        Mockito.doReturn(game).when(defaultGameLogic).validateGameInputs(any(), any());
        pit13.setStoneCount(0);
        when(pitService.getPit(any(), any(), any())).thenReturn(pit13);
        assertThrows(ValidationException.class, () -> defaultGameLogic.processMove(this.game.getId(), Player.TWO.getValue(), 13));

    }


    /**
     * Validate game inputs test failure game is finished.
     */
    @Test
    @DisplayName("Test for valid moves")
    void validateGameInputsTest_Failure_GameIsFinished() {
        Mockito.doReturn(game).when(gameService).findById(any());
        game.setState(GameState.FINISHED);

        assertThrows(ValidationException.class, () -> defaultGameLogic.validateGameInputs(game.getId(), Player.ONE.getValue()));

    }

    /**
     * Validate game inputs test failure wrong player turn.
     */
    @Test
    @DisplayName("Test for valid moves")
    void validateGameInputsTest_Failure_WrongPlayerTurn() {
        when(gameService.findById(any())).thenReturn(game);
        game.setState(GameState.FINISHED);

        assertThrows(ValidationException.class, () -> defaultGameLogic.validateGameInputs(game.getId(), Player.ONE.getValue()));

    }

    /**
     * Validate game inputs test success.
     */
    @Test
    @DisplayName("Test for valid game input")
    void validateGameInputsTest_Success() {
        when(gameService.findById(any())).thenReturn(game);

        Game expected = Game.builder().id(1L).player1(1).player2(2).turn(1).state(GameState.IN_PROGRESS).build();
        Game actual = defaultGameLogic.validateGameInputs(this.game.getId(), Player.ONE.getValue());
        assertEquals(expected, actual);
    }

    /**
     * Process move main test success with player 2 winner.
     */
    @Test
    @DisplayName("process Move Main Success with Player2 Winner")
    void processMoveMainTest_Success_With_Player2Winner() {

        Mockito.doReturn(game).when(defaultGameLogic).validateGameInputs(any(), any());
        when(pitService.getPit(any(), any(), any())).thenReturn(pit13);
        when(pitService.listAllPitsOrderByPitNumber(game)).thenReturn(pitMap.values());
        Mockito.doReturn(pit14).when(defaultGameLogic).processMove(any(Player.class), any(), any());
        Mockito.doReturn(game).when(defaultGameLogic).processEndPitRule(any(), any(), any(), any());

        Mockito.doReturn(Map.of(1, 0, 2, 1)).when(defaultGameLogic).checkGameStatus(any());

        Mockito.doReturn("Player 2 is winner!!! ").when(defaultGameLogic).checkWinner(any());
        Mockito.doReturn(game).when(defaultGameLogic).formatPitsForGameStatusDisplay(any(), any());

        when(pitService.getPit(any(), any(), any())).thenReturn(pit13);
        Game actual = defaultGameLogic.processMove(this.game.getId(), Player.TWO.getValue(), 13);

        assertEquals(game, actual);
    }

    /**
     * Process move main test success with player 1 winner.
     */
    @Test
    @DisplayName("Test to check process Move Main Success with Player 1 Winner")
    void processMoveMainTest_Success_With_Player1Winner() {

        Mockito.doReturn(game).when(defaultGameLogic).validateGameInputs(any(), any());
        when(pitService.getPit(any(), any(), any())).thenReturn(pit6);
        when(pitService.listAllPitsOrderByPitNumber(game)).thenReturn(pitMap.values());
        Mockito.doReturn(pit14).when(defaultGameLogic).processMove(any(Player.class), any(), any());
        Mockito.doReturn(game).when(defaultGameLogic).processEndPitRule(any(), any(), any(), any());

        Mockito.doReturn(Map.of(1, 10, 2, 1)).when(defaultGameLogic).checkGameStatus(any());

        Mockito.doReturn("Player 1 is winner!!! ").when(defaultGameLogic).checkWinner(any());
        Mockito.doReturn(game).when(defaultGameLogic).formatPitsForGameStatusDisplay(any(), any());

        when(pitService.getPit(any(), any(), any())).thenReturn(pit13);
        Game actual = defaultGameLogic.processMove(this.game.getId(), Player.TWO.getValue(), 13);

        assertEquals(game, actual);
    }

    /**
     * Process end pit rule test player 1 last stone in big pit.
     */
    @Test
    @DisplayName("Test End Pit Rule when player 1 stone ends in big pit")
    void processEndPitRuleTest_Player1_LastStoneInBigPit() {
        Game expected = Game.builder().id(1L).player1(1).player2(2).turn(1).state(GameState.IN_PROGRESS).build();

        Game actual = defaultGameLogic.processEndPitRule(this.game, Player.ONE, pit7, pitMap);

        assertEquals(expected, actual);
    }

    /**
     * Process end pit rule test player 1 last stone in empty pit.
     */
    @Test
    @DisplayName("Test End Pit Rule when player 1 stone ends in own empty pit")
    void processEndPitRuleTest_Player1_LastStoneInEmptyPit() {
        Game expected = Game.builder().id(1L).player1(1).player2(2).turn(2).state(GameState.IN_PROGRESS).build();

        pit6.setStoneCount(1);
        Game actual = defaultGameLogic.processEndPitRule(this.game, Player.ONE, pit6, pitMap);
        assertEquals(expected, actual);

    }

    /**
     * Process end pit rule test player 2 last stone in big pit.
     */
    @Test
    @DisplayName("Test End Pit Rule when player 2 stone ends in big pit")
    void processEndPitRuleTest_Player2_LastStoneInBigPit() {
        Game expected = Game.builder().id(1L).player1(1).player2(2).turn(2).state(GameState.IN_PROGRESS).build();

        Game actual = defaultGameLogic.processEndPitRule(this.game, Player.TWO, pit14, pitMap);

        assertEquals(expected, actual);
    }

    /**
     * Process end pit rule test player 2 last stone in empty pit.
     */
    @Test
    @DisplayName("Test endpit rules when player 2 stone ends in own empty pit")
    void processEndPitRuleTest_Player2_LastStoneInEmptyPit() {
        Game expected = Game.builder().id(1L).player1(1).player2(2).turn(1).state(GameState.IN_PROGRESS).build();

        pit13.setStoneCount(1);
        Game actual = defaultGameLogic.processEndPitRule(this.game, Player.TWO, pit13, pitMap);
        assertEquals(expected, actual);

    }

    /**
     * Add stones to storage test.
     */
    @Test
    @DisplayName("Test for adding new stones and setting next turn")
    void addStonesToStorageTest() {
        var newStones = 5;
        var currentStoneCount = pit14.getStoneCount();
        var expected = currentStoneCount + newStones;
        defaultGameLogic.addStonesToStorage(game, pit14, newStones, Player.ONE);

        assertEquals(expected, pit14.getStoneCount());
        assertEquals(Player.ONE.getValue(), game.getTurn());
    }

    /**
     * Check winner test.
     */
    @Test
    @DisplayName("Test to check winner")
    void checkWinnerTest() {
        pit7.setStoneCount(15);
        String actual = defaultGameLogic.checkWinner(pitMap);
        assertEquals("Player 1 is winner!!!", actual);

        pit14.setStoneCount(20);
        actual = defaultGameLogic.checkWinner(pitMap);
        assertEquals("Player 2 is winner!!!", actual);
    }


    /**
     * Check game status test.
     */
    @Test
    @DisplayName("Test to check game status")
    void checkGameStatusTest() {
        Map<Integer, Integer> actual = defaultGameLogic.checkGameStatus(pitMap);
        Map<Integer, Integer> expected = Map.of(1, 36, 2, 36);

        assertEquals(expected, actual);

    }

    /**
     * Process move test player 1.
     */
    @Test
    @DisplayName("Test processMove for a given pitNumber by  player one")
    void processMoveTest_Player1() {
        Pit current = getPitByPitNumber(1);
        var pitMap = new HashMap<Integer, Pit>(this.pitMap);
        var expected = getPitByPitNumber(7);

        Pit pit = defaultGameLogic.processMove(Player.ONE, current, pitMap);

        assertEquals(expected, pit);

    }


    /**
     * Process move test player 2.
     */
    @Test
    @DisplayName("Test ProcessMove for a given pitNumber by player two")
    void processMoveTest_Player2() {
        Pit current = getPitByPitNumber(10);
        var pitMap = new HashMap<Integer, Pit>(this.pitMap);
        var expected = getPitByPitNumber(2);

        Pit pit = defaultGameLogic.processMove(Player.TWO, current, pitMap);

        assertEquals(expected, pit);

    }

    private Pit getPitByPitNumber(Integer pitNumber) {
        return pitMap.get(pitNumber);

    }
}
