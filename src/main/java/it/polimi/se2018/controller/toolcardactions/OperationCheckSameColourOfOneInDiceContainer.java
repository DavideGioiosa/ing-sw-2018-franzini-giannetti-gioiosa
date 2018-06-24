package it.polimi.se2018.controller.toolcardactions;

import it.polimi.se2018.controller.Turn;
import it.polimi.se2018.model.DiceContainer;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.player.Player;

import java.util.List;

public class OperationCheckSameColourOfOneInDiceContainer implements ToolOperation{

    public OperationCheckSameColourOfOneInDiceContainer(){
        //Doesn't need standard attributes
    }

    @Override
    public boolean start(DiceContainer diceContainer, PlayerMove playerMove, List<Die> dieList, List<Player> roundPlayerOrder, Turn turn) {

        if(dieList.stream().map(Die::getColour).distinct().count() == 1){
            for(Die die: diceContainer.getClonedDieList()) if (die.getColour() == dieList.get(0).getColour()) return true;
        }
        return false;
    }
}
