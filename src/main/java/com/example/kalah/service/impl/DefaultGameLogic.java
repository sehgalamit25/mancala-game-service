package com.example.kalah.service.impl;

import com.example.kalah.exception.ValidationException;
import com.example.kalah.model.Game;
import com.example.kalah.model.Pit;
import com.example.kalah.model.enums.GameState;
import com.example.kalah.model.enums.PitType;
import com.example.kalah.model.enums.Player;
import com.example.kalah.service.GameLogic;
import com.example.kalah.service.GameService;
import com.example.kalah.service.PitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.summingInt;


@Service
@Slf4j
public class DefaultGameLogic implements GameLogic {

    private final GameService gameService;
    private final PitService pitService;

    /**
     * Instantiates a new Default game logic.
     *
     * @param gameService the game service
     * @param pitService  the pit service
     */
    @Autowired
    public DefaultGameLogic(GameService gameService, PitService pitService) {
        this.gameService = gameService;
        this.pitService = pitService;
    }

    @Override
    @Transactional
    public Game startNewGame() {
        Game game = gameService.createGame();
        Collection<Pit> pits = pitService.createPitsForPlayers(game);
        return formatPitsForGameStatusDisplay(game, pits);
    }

    @Override
    @Transactional
    public Game processMove(final Long gameId, final Integer playerId, final Integer pitNumber) throws ValidationException {
        Game game = validateGameInputs(gameId, playerId);
        Player player = Player.of(playerId);
        Pit currentPit = pitService.getPit(game, player, pitNumber);
        if (currentPit.getStoneCount() == 0) {
            throw new ValidationException("Pit " + pitNumber + " is empty. Select another pit !!!");
        }


        var pits = pitService.listAllPitsOrderByPitNumber(game);
        var pitMap = pits.stream().collect(Collectors.toMap(Pit::getPitNumber, pit -> pit));

        var endPit = processMove(player, currentPit, pitMap);
        processEndPitRule(game, player, endPit, pitMap);

        Map<Integer, Integer> playerStoneCount = checkGameStatus(pitMap);

        if (playerStoneCount.get(Player.ONE.getValue()) == 0
                || playerStoneCount.get(Player.TWO.getValue()) == 0) {
            game.setState(GameState.FINISHED);
            game.setTurn(null);

            pitMap.values().stream()
                    .filter(pit -> pit.getType() == PitType.SMALL)
                    .forEach(pit -> pit.setStoneCount(0));

            if (playerStoneCount.get(Player.ONE.getValue()) == 0) {
                addStonesToStorage(game, pitMap.get(Player.TWO.getStoragePit()), playerStoneCount.get(Player.TWO.getValue()), null);
            }
            if (playerStoneCount.get(Player.TWO.getValue()) == 0) {
                addStonesToStorage(game, pitMap.get(Player.ONE.getStoragePit()), playerStoneCount.get(Player.ONE.getValue()), null);
            }

            String winner = checkWinner(pitMap);
            game.setWinner(winner);
        }

        gameService.save(game);
        pitService.saveAll(pitMap.values());

        return formatPitsForGameStatusDisplay(game, pits);
    }

    @Override
    public Game getGame(Long id) {
        Game game = gameService.findById(id);
        Collection<Pit> pits = pitService.listAllPitsOrderByPitNumber(game);
        return formatPitsForGameStatusDisplay(game,pits);
    }

    /**
     * Validate game inputs game.
     *
     * @param gameId   the game id
     * @param playerId the player id
     * @return the game
     */
    public Game validateGameInputs(Long gameId, Integer playerId) {
        Game game = gameService.findById(gameId);
        if (game.getState() == GameState.FINISHED) {
            throw new ValidationException("Game Already Finished!!!");
        }
        if (!game.getTurn().equals(playerId)) {
            throw new ValidationException("Player " + game.getTurn() + " turn!!!");
        }


        return game;
    }


    /**
     * Process end pit rule game.
     *
     * @param game   the game
     * @param player the player
     * @param endPit the end pit
     * @param pitMap the pit map
     * @return the game
     */
    public Game processEndPitRule(Game game, Player player, Pit endPit, Map<Integer, Pit> pitMap) {

        if (Player.ONE == player) {
            game.setTurn(Player.TWO.getValue());
        } else {
            game.setTurn(Player.ONE.getValue());
        }

        if (Player.of(endPit.getPlayer()) == player && PitType.BIG == endPit.getType()) {
            game.setTurn(player.getValue());
            log.info(""+player.getValue());
            return game;
        }

        if (endPit.getStoneCount() == 1 && endPit.getPlayer().equals(player.getValue())) {
            var oppositePit = pitMap.get(14 - endPit.getPitNumber());
            var sum = endPit.getStoneCount() + oppositePit.getStoneCount();
            endPit.setStoneCount(0);
            oppositePit.setStoneCount(0);

            if (Player.ONE == player) {
                addStonesToStorage(game, pitMap.get(Player.ONE.getStoragePit()), sum, Player.TWO);
            } else if (Player.TWO == player) {
                addStonesToStorage(game, pitMap.get(Player.TWO.getStoragePit()), sum, Player.ONE);
            }
        }

        return game;
    }

    /**
     * Add stones to storage.
     *
     * @param game       the game
     * @param storagePit the storage pit
     * @param stoneCount the stone count
     * @param nextTurn   the next turn
     */
    public void addStonesToStorage(Game game, Pit storagePit, Integer stoneCount, Player nextTurn) {
        storagePit.setStoneCount(storagePit.getStoneCount() + stoneCount);
        game.setTurn(null != nextTurn ? nextTurn.getValue() : null);
    }

    /**
     * Check winner string.
     *
     * @param pitMap the pit map
     * @return the string
     */
    public String checkWinner(Map<Integer, Pit> pitMap) {
        Map<Integer, Integer> finalPitCountPerPlayer = pitMap.values().stream()
                .filter(pit -> pit.getType().equals(PitType.BIG))
                .collect(Collectors.toMap(Pit::getPlayer, Pit::getStoneCount));
        return finalPitCountPerPlayer.get(Player.ONE.getValue()) > finalPitCountPerPlayer.get(Player.TWO.getValue()) ?
                "Player 1 is winner!!!" : "Player 2 is winner!!!";
    }

    /**
     * Check game status map.
     *
     * @param pitMap the pit map
     * @return the map
     */
    public Map<Integer, Integer> checkGameStatus(Map<Integer, Pit> pitMap) {

        return pitMap.values().stream()
                .filter(pit -> !pit.getType().equals(PitType.BIG))
                .collect(Collectors.groupingBy(Pit::getPlayer, summingInt(Pit::getStoneCount)));
    }

    /**
     * Process move pit.
     *
     * @param player  the player
     * @param current the current
     * @param pitMap  the pit map
     * @return the pit
     */
    public Pit processMove(Player player, Pit current, Map<Integer, Pit> pitMap) {
        int numberOfStones = current.getStoneCount();
        current.setStoneCount(0);
        var index = current.getPitNumber() + 1;
        while (numberOfStones > 0) {
            if (index > 14) {
                index = 1;
            }
            var pit = pitMap.get(index);
            if ((Player.ONE == player && index == 14) || (Player.TWO == player && index == 7)) {
                index++;
                continue;
            }
            index++;
            pit.setStoneCount(pit.getStoneCount() + 1);
            numberOfStones--;
        }
        return pitMap.get(index > 15 ? 1 : index-1);
    }

    /**
     * Format pits for game status display game.
     *
     * @param game the game
     * @param pits the pits
     * @return the game
     */
    public Game formatPitsForGameStatusDisplay(final Game game, Collection<Pit> pits) {
        String str1 = formatPits(pits.stream().filter(pit -> pit.getPlayer() == 1), Comparator.comparing(Pit::getPitNumber));
        String str2 = formatPits(pits.stream().filter(pit -> pit.getPlayer() == 2), Comparator.comparing(Pit::getPitNumber, Comparator.reverseOrder()));

        game.setPlayer1Pits("   | " + str1);
        game.setPlayer2Pits(str2 + " |   ");

        log.info("   | " + str1);
        log.info(str2 + " |   ");

        return game;
    }

    /**
     * Format pits string.
     *
     * @param pits          the pits
     * @param pitComparator the pit comparator
     * @return the string
     */
    public String formatPits(final Stream<Pit> pits, final Comparator<Pit> pitComparator) {
        NumberFormat formatter = new DecimalFormat("00");
        return pits
                .sorted(pitComparator)
                .map(Pit::getStoneCount)
                .map(formatter::format)
                .collect(Collectors.joining(" | "));
    }
}
