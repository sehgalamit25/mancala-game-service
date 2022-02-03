package com.example.kalah.service.impl;

import com.example.kalah.exception.ValidationException;
import com.example.kalah.model.Game;
import com.example.kalah.model.Pit;
import com.example.kalah.model.enums.PitType;
import com.example.kalah.model.enums.Player;
import com.example.kalah.repository.PitRepository;
import com.example.kalah.service.PitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * The type Default pit service.
 */
@Service
public class DefaultPitService implements PitService {

    private final PitRepository pitRepository;

    /**
     * Instantiates a new Default pit service.
     *
     * @param pitRepository the pit repository
     */
    @Autowired
    public DefaultPitService(PitRepository pitRepository) {
        this.pitRepository = pitRepository;
    }

    @Override
    @Transactional
    public List<Pit> createPitsForPlayers(final Game game) {
        Pit pit0 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(1).stoneCount(6).type(PitType.SMALL).build();
        Pit pit1 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(2).stoneCount(6).type(PitType.SMALL).build();
        Pit pit2 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(3).stoneCount(6).type(PitType.SMALL).build();
        Pit pit3 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(4).stoneCount(6).type(PitType.SMALL).build();
        Pit pit4 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(5).stoneCount(6).type(PitType.SMALL).build();
        Pit pit5 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(6).stoneCount(6).type(PitType.SMALL).build();
        Pit pit6 = Pit.builder().game(game).player(Player.ONE.getValue()).pitNumber(7).stoneCount(0).type(PitType.BIG).build();

        Pit pit7 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(8).stoneCount(6).type(PitType.SMALL).build();
        Pit pit8 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(9).stoneCount(6).type(PitType.SMALL).build();
        Pit pit9 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(10).stoneCount(6).type(PitType.SMALL).build();
        Pit pit10 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(11).stoneCount(6).type(PitType.SMALL).build();
        Pit pit11 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(12).stoneCount(6).type(PitType.SMALL).build();
        Pit pit12 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(13).stoneCount(6).type(PitType.SMALL).build();
        Pit pit13 = Pit.builder().game(game).player(Player.TWO.getValue()).pitNumber(14).stoneCount(0).type(PitType.BIG).build();

        return pitRepository.saveAll(List.of(pit0, pit1, pit2, pit3, pit4, pit5, pit6, pit7, pit8, pit9, pit10, pit11, pit12, pit13));
    }

    @Override
    public List<Pit> listAllPitsOfPlayer(Game game, Integer player) {
        return pitRepository.findAllByGameAndPlayer(game, player);
    }

    @Override
    public Pit getPit(Game game, Player player, Integer pitNumber) {
        return pitRepository.findAllByGameAndPlayerAndPitNumber(game, player.getValue(), pitNumber)
                .orElseThrow(() -> new ValidationException("Pit "+ pitNumber +" not found for game " + game.getId() + " played by player : " + player.getValue()));
    }

    @Override
    public List<Pit> listAllPitsOrderByPitNumber(Game game) {
        return pitRepository.findAllByGameOrderByPitNumber(game);
    }

    @Override
    public Collection<Pit> saveAll(Collection<Pit> pits) {
        return pitRepository.saveAll(pits);
    }
}
