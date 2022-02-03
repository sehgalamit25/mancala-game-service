package com.example.kalah.service;

import com.example.kalah.exception.ValidationException;
import com.example.kalah.model.Game;
import com.example.kalah.model.Pit;
import com.example.kalah.model.enums.GameState;
import com.example.kalah.model.enums.PitType;
import com.example.kalah.model.enums.Player;
import com.example.kalah.repository.PitRepository;
import com.example.kalah.service.impl.DefaultPitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

/**
 * The type Pit service test.
 */
@ExtendWith(MockitoExtension.class)
public class PitServiceTest {

    @Mock
    private PitRepository pitRepository;
    private PitService pitService;

    /**
     * The Player 1.
     */
    Player player1;
    /**
     * The Player 2.
     */
    Player player2;

    /**
     * The Game.
     */
    Game game;

    /**
     * The Pits player 1.
     */
    List<Pit> pitsPlayer1;
    /**
     * The Pits player 2.
     */
    List<Pit> pitsPlayer2;

    /**
     * The Pits.
     */
    List<Pit> pits;

    /**
     * The Pit 0.
     */
    Pit pit0,
    /**
     * The Pit 1.
     */
    pit1,
    /**
     * The Pit 2.
     */
    pit2,
    /**
     * The Pit 3.
     */
    pit3,
    /**
     * The Pit 4.
     */
    pit4,
    /**
     * The Pit 5.
     */
    pit5,
    /**
     * The Pit 6.
     */
    pit6,
    /**
     * The Pit 7.
     */
    pit7,
    /**
     * The Pit 8.
     */
    pit8,
    /**
     * The Pit 9.
     */
    pit9,
    /**
     * The Pit 10.
     */
    pit10,
    /**
     * The Pit 11.
     */
    pit11,
    /**
     * The Pit 12.
     */
    pit12,
    /**
     * The Pit 13.
     */
    pit13;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {

        this.pitService = new DefaultPitService(pitRepository);

        game = Game.builder().id(1L).player1(1).player2(2).turn(1).state(GameState.IN_PROGRESS).build();

        //Create Pits
        pit0 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(1).stoneCount(6).type(PitType.SMALL).build();
        pit1 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(2).stoneCount(6).type(PitType.SMALL).build();
        pit2 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(3).stoneCount(6).type(PitType.SMALL).build();
        pit3 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(4).stoneCount(6).type(PitType.SMALL).build();
        pit4 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(5).stoneCount(6).type(PitType.SMALL).build();
        pit5 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(6).stoneCount(6).type(PitType.SMALL).build();
        pit6 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(7).stoneCount(0).type(PitType.BIG).build();

        pit7 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(8).stoneCount(6).type(PitType.SMALL).build();
        pit8 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(9).stoneCount(6).type(PitType.SMALL).build();
        pit9 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(10).stoneCount(6).type(PitType.SMALL).build();
        pit10 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(11).stoneCount(6).type(PitType.SMALL).build();
        pit11 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(12).stoneCount(6).type(PitType.SMALL).build();
        pit12 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(13).stoneCount(6).type(PitType.SMALL).build();
        pit13 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(14).stoneCount(0).type(PitType.BIG).build();

        pits = List.of(pit0, pit1, pit2, pit3, pit4, pit5, pit6, pit7, pit8, pit9, pit10, pit11, pit12, pit13);
        pitsPlayer1 = List.of(pit0, pit1, pit2, pit3, pit4, pit5, pit6);
        pitsPlayer2 = List.of(pit7, pit8, pit9, pit10, pit11, pit12, pit13);

        //player setup
        player1 = Player.ONE;
        player2 = Player.TWO;

    }


    /**
     * List all pits of player test.
     */
    @Test
    @DisplayName("Find All Pits of given player in a given game")
    void listAllPitsOfPlayerTest() {

        given(pitRepository.findAllByGameAndPlayer(game, player1.getValue())).willReturn(pitsPlayer1);

        //test
        Collection<Pit> actual = pitService.listAllPitsOfPlayer(game, 1);

        assertEquals(7, actual.size());

    }

    /**
     * Save all test.
     */
    @Test
    @DisplayName("Save All Pits ")
    void saveAllTest() {
        given(pitRepository.saveAll(pits)).willReturn(pits);


        Collection<Pit> pits = pitService.saveAll(this.pits);

        assertEquals(14, pits.size());

    }

    /**
     * Gets pit test success.
     */
    @Test
    @DisplayName("Test Return pit information for a given game and given player")
    void getPitTest_Success() {

        given(pitRepository.findAllByGameAndPlayerAndPitNumber(game, player2.getValue(), 8))
                .willReturn(Optional.ofNullable(pit9));

        Pit pit = pitService.getPit(game, player2, 8);

        assertEquals(pit9,pit);
    }

    /**
     * Gets pit test validation exeption.
     */
    @Test
    @DisplayName("Test Return pit information for a given game and given player")
    void getPitTest_ValidationExeption() {

        given(pitRepository.findAllByGameAndPlayerAndPitNumber(game, player2.getValue(), 2))
                .willReturn(Optional.empty());

        assertThrows(ValidationException.class,() ->pitService.getPit(game, player2, 2));

    }


    /**
     * List all pit of player test.
     */
    @Test
    @DisplayName("Test Return list of pits for a given player")
    void listAllPitOfPlayerTest()
    {
        given(pitRepository.findAllByGameAndPlayer(game, player2.getValue()))
                .willReturn(pitsPlayer2);

        Collection<Pit> pitsOfPlayer2 = pitService.listAllPitsOfPlayer(game, player2.TWO.getValue());

        assertEquals(pitsPlayer2,pitsOfPlayer2);
        assertTrue(pitsOfPlayer2.containsAll(pitsOfPlayer2));


    }
}
