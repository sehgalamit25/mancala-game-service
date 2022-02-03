package com.example.kalah.repository;

import com.example.kalah.model.Game;
import com.example.kalah.model.Pit;
import com.example.kalah.model.enums.GameState;
import com.example.kalah.model.enums.PitType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * The type Pit repository test.
 */
@DataJpaTest
class PitRepositoryTest {

    @Autowired
    private PitRepository pitRepository;

    @Autowired
    private GameRepository gameRepository;

    /**
     * Find all by game and player test.
     */
    @Test
    void findAllByGameAndPlayerTest() {
        // Given
        Game game = gameRepository.save(Game.builder().player1(1).player2(2).turn(1).state(GameState.IN_PROGRESS).build());
        Pit pit1 = pitRepository.save(Pit.builder().game(game).player(1).type(PitType.SMALL).pitNumber(1).stoneCount(6).build());
        Pit pit2 = pitRepository.save(Pit.builder().game(game).player(1).type(PitType.SMALL).pitNumber(2).stoneCount(6).build());
        Pit pit3 = pitRepository.save(Pit.builder().game(game).player(1).type(PitType.SMALL).pitNumber(3).stoneCount(6).build());
        Pit pit4 = pitRepository.save(Pit.builder().game(game).player(1).type(PitType.SMALL).pitNumber(4).stoneCount(6).build());
        Pit pit5 = pitRepository.save(Pit.builder().game(game).player(1).type(PitType.SMALL).pitNumber(5).stoneCount(6).build());
        Pit pit6 = pitRepository.save(Pit.builder().game(game).player(1).type(PitType.SMALL).pitNumber(6).stoneCount(6).build());
        Pit pit7 = pitRepository.save(Pit.builder().game(game).player(1).type(PitType.BIG).pitNumber(7).stoneCount(0).build());

        Pit pit8 = pitRepository.save(Pit.builder().game(game).player(2).type(PitType.BIG).pitNumber(7).stoneCount(0).build());
        List<Pit> expected = List.of(pit1, pit2, pit3, pit4, pit5, pit6, pit7);

        // When
        List<Pit> actual = pitRepository.findAllByGameAndPlayer(game, 1);

        // Then
        assertThat(actual).hasSameElementsAs(expected);
    }

    /**
     * Find all by game and player and pit number test.
     */
    @Test
    void findAllByGameAndPlayerAndPitNumberTest() {
        // Given
        Game game = gameRepository.save(Game.builder().player1(1).player2(2).turn(1).state(GameState.IN_PROGRESS).build());
        Pit pit1 = pitRepository.save(Pit.builder().game(game).player(1).type(PitType.SMALL).pitNumber(1).stoneCount(6).build());

        // When
        Optional<Pit> actual = pitRepository.findAllByGameAndPlayerAndPitNumber(game, 1, 1);

        // Then
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(pit1, actual.get());
    }

    /**
     * Find all by game order by pit number.
     */
    @Test
    @DisplayName("Get All Pits Order by PitNumber")
    void findAllByGameOrderByPitNumber(){
        // Given
        Game game = gameRepository.save(Game.builder().player1(1).player2(2).turn(1).state(GameState.IN_PROGRESS).build());
        Pit pit1 = pitRepository.save(Pit.builder().game(game).player(1).type(PitType.SMALL).pitNumber(1).stoneCount(6).build());
        Pit pit2 = pitRepository.save(Pit.builder().game(game).player(1).type(PitType.SMALL).pitNumber(2).stoneCount(6).build());
        Pit pit3 = pitRepository.save(Pit.builder().game(game).player(1).type(PitType.SMALL).pitNumber(3).stoneCount(6).build());
        Pit pit4 = pitRepository.save(Pit.builder().game(game).player(1).type(PitType.SMALL).pitNumber(4).stoneCount(6).build());
        Pit pit5 = pitRepository.save(Pit.builder().game(game).player(1).type(PitType.SMALL).pitNumber(5).stoneCount(6).build());
        Pit pit6 = pitRepository.save(Pit.builder().game(game).player(1).type(PitType.SMALL).pitNumber(6).stoneCount(6).build());
        Pit pit7 = pitRepository.save(Pit.builder().game(game).player(1).type(PitType.BIG).pitNumber(7).stoneCount(0).build());
        Pit pit8 = pitRepository.save(Pit.builder().game(game).player(2).type(PitType.SMALL).pitNumber(8).stoneCount(6).build());
        Pit pit9 = pitRepository.save(Pit.builder().game(game).player(2).type(PitType.SMALL).pitNumber(9).stoneCount(6).build());
        Pit pit10 = pitRepository.save(Pit.builder().game(game).player(2).type(PitType.SMALL).pitNumber(10).stoneCount(6).build());
        Pit pit11 = pitRepository.save(Pit.builder().game(game).player(2).type(PitType.SMALL).pitNumber(11).stoneCount(6).build());
        Pit pit12 = pitRepository.save(Pit.builder().game(game).player(2).type(PitType.SMALL).pitNumber(12).stoneCount(6).build());
        Pit pit13 = pitRepository.save(Pit.builder().game(game).player(2).type(PitType.SMALL).pitNumber(13).stoneCount(6).build());
        Pit pit14 = pitRepository.save(Pit.builder().game(game).player(2).type(PitType.BIG).pitNumber(14).stoneCount(0).build());

        List<Pit> expected = List.of(pit1, pit2, pit3, pit4, pit5, pit6, pit7, pit8, pit9, pit10, pit11, pit12, pit13, pit14);
        // When
        List<Pit> actual = pitRepository.findAllByGameOrderByPitNumber(game);

        //Then
       assertThat(actual).hasSameElementsAs(expected);

    }
}