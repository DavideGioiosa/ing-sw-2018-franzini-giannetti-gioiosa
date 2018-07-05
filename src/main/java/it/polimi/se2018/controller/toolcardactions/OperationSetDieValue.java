package it.polimi.se2018.controller.toolcardactions;

import it.polimi.se2018.controller.Turn;
import it.polimi.se2018.model.Config;
import it.polimi.se2018.model.DiceContainer;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.player.Player;

import java.util.List;

public class OperationSetDieValue implements ToolOperation{

    public OperationSetDieValue(){
        //Doesn't need standard attributes
    }

    @Override
    public boolean start(DiceContainer diceContainer, PlayerMove playerMove, List<Die> dieList, List<Player> roundPlayerOrder, Turn turn) {
        if (playerMove.getValue() <= Config.DIE_MAX_VALUE && playerMove.getValue() >= Config.DIE_MIN_VALUE) {
            for (Die die : dieList) die.setValue(playerMove.getValue());
            return true;
        } else return false;
    }
}
