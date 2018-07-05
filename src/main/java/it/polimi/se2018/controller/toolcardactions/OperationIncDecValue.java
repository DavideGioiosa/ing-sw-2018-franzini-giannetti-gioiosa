package it.polimi.se2018.controller.toolcardactions;

import it.polimi.se2018.controller.Turn;
import it.polimi.se2018.model.DiceContainer;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.player.Player;

import java.util.List;

public class OperationIncDecValue implements ToolOperation {

    public OperationIncDecValue(){
        //Doesn't need standard attributes
    }

    @Override
    public boolean start(DiceContainer diceContainer, PlayerMove playerMove, List<Die> dieList, List<Player> roundPlayerOrder, Turn turn) {

        for(Die die: dieList) {
            if(playerMove.getValue() == 1) die.increaseValue();
            else if(playerMove.getValue() == -1) die.decreaseValue();
            else return false;
        }
        return true;
    }
}
