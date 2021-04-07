package com.mancala.game.service;

import com.mancala.game.Model.Game;
import com.mancala.game.Model.Pit;
import com.mancala.game.Model.Player;
import com.mancala.game.repository.GameRepository;
import com.mancala.game.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private Player firstPlayer;
    private Player secondPlayer;
    private Pit pit0;
    private Pit pit1;
    private Pit pit2;
    private Pit pit3;
    private Pit pit4;
    private Pit pit5;
    private Pit pit6;
    private Pit pit7;
    private Pit pit8;
    private Pit pit9;
    private Pit pit10;
    private Pit pit11;
    private Pit pit12;
    private Pit pit13;

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    public GameService(PlayerRepository playerRepository, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    List<Pit> pits;

    public Game create() {

        pit0 = Pit.builder().pitIndex(0).nextPitRef(1).oppPitRef(12).numOfStones(6).owner(firstPlayer).build();
        pit1 = Pit.builder().pitIndex(1).nextPitRef(2).oppPitRef(11).numOfStones(6).owner(firstPlayer).build();
        pit2 = Pit.builder().pitIndex(2).nextPitRef(3).oppPitRef(10).numOfStones(6).owner(firstPlayer).build();
        pit3 = Pit.builder().pitIndex(3).nextPitRef(4).oppPitRef(9).numOfStones(6).owner(firstPlayer).build();
        pit4 = Pit.builder().pitIndex(4).nextPitRef(5).oppPitRef(8).numOfStones(6).owner(firstPlayer).build();
        pit5 = Pit.builder().pitIndex(5).nextPitRef(6).oppPitRef(7).numOfStones(6).owner(firstPlayer).build();
        pit6 = Pit.builder().pitIndex(6).nextPitRef(7).oppPitRef(null).numOfStones(6).owner(firstPlayer).build();
        pit7 = Pit.builder().pitIndex(7).nextPitRef(8).oppPitRef(5).numOfStones(6).owner(secondPlayer).build();
        pit8 = Pit.builder().pitIndex(8).nextPitRef(9).oppPitRef(4).numOfStones(6).owner(secondPlayer).build();
        pit9 = Pit.builder().pitIndex(9).nextPitRef(10).oppPitRef(3).numOfStones(6).owner(secondPlayer).build();
        pit10 = Pit.builder().pitIndex(10).nextPitRef(11).oppPitRef(2).numOfStones(6).owner(secondPlayer).build();
        pit11 = Pit.builder().pitIndex(11).nextPitRef(12).oppPitRef(1).numOfStones(6).owner(secondPlayer).build();
        pit12 = Pit.builder().pitIndex(12).nextPitRef(13).oppPitRef(0).numOfStones(6).owner(secondPlayer).build();
        pit13 = Pit.builder().pitIndex(13).nextPitRef(0).oppPitRef(null).numOfStones(6).owner(secondPlayer).build();

        List<Pit> pitsFirstplayer = List.of(pit0, pit1, pit2, pit3, pit4, pit5, pit6);
        List<Pit> pitsSecondplayer = List.of(pit7, pit8, pit9, pit10, pit11, pit12, pit13);

        pits = List.of(pit0, pit1, pit2, pit3, pit4, pit5, pit6, pit7, pit8, pit9, pit10, pit11, pit12, pit13);
        firstPlayer = Player.builder().id(1L).playername("John").pitSet(pitsFirstplayer).build();
        secondPlayer = Player.builder().id(2L).playername("Green").pitSet(pitsSecondplayer).build();

        return Game.builder().firstPlayer(firstPlayer).secondPlayer(secondPlayer).status("CREATED").build();

    }

    public Game move(Long gameId, Long playerId, Integer index) throws Exception {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new Exception());
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new Exception());

        Pit pit = positionToMove(player, index);
        //to check the state of the game after each move
        //game =checkGameState(game, firstPlayer,secondPlayer);

        if (game.getStatus().equals("Finished")) {
            return null;/*"Player :"+ player.getPlayername()+" won"*/
        }
        //to check if last stone ends in player's storage
        else if (null == pit.getOppPitRef() && player.getId() == pit.getOwner().getId()) {
            game.setPlayerTurn(player.getPlayername());
        }
        else if (player.getId() == pit.getOwner().getId() && pit.getNumOfStones() == 1) {
            captureOppStone(player, pit);
            game.setPlayerTurn(getCurrentPlayer(player).getPlayername());
        } else {
            game.setPlayerTurn(getCurrentPlayer(player).getPlayername());
        }
        return game;
    }

    private void captureOppStone(Player player, Pit pit) {

        Pit oppositePit = pits.get(pit.getOppPitRef());

        //Add opposite stones to storage pit
        for (Pit pitref : pits) {
            if (null == pitref.getOppPitRef() && player.getId() == pitref.getOwner().getId()) {
                pitref.setNumOfStones(pitref.getNumOfStones() + oppositePit.getNumOfStones() + pit.getNumOfStones());
            }
        }
        //empty opposite pit
        oppositePit.setNumOfStones(0);
        //empty own pit
        pit.setNumOfStones(0);

    }

    private Pit positionToMove(Player player, Integer index) {
         /*player.getPitSet().get(index).getNumOfStones();
        player.getPitSet().get(index).setNumOfStones(0);*/
        Pit pit = pits.get(index);
        Integer takeNumberofStones = pit.getNumOfStones();
        pit.setNumOfStones(0);
        Integer nextPitRef = 0;

        //Add stones to the next pit
        for (int i = index; takeNumberofStones != 0; i++) {
            nextPitRef = pits.get(index).getNextPitRef();
            //add to next pit only if it's not opposite player pit
            //if ( player.getId() != pit.getOwner().getId() && null != pit.getOppPitRef()) { // not correct for now
            pits.get(nextPitRef).setNumOfStones(pits.get(nextPitRef).getNumOfStones() + 1);
            takeNumberofStones--;
            //}


        }
        return pits.get(nextPitRef);
    }

    private Player getCurrentPlayer(Player player) {
        return firstPlayer.getId() == player.getId() ? firstPlayer : secondPlayer;

    }
}
