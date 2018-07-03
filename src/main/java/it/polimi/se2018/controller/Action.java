package it.polimi.se2018.controller;

import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.player.Player;

import java.util.List;

public interface Action {

    int doAction(GameBoard gameBoard, PlayerMove playerMove, List<Player> roundPlayerOrder, Turn turn);
    boolean isComplete();
    int doDefaultMove();
    boolean isPass();
    PlayerMove getPlayerMove();
}
