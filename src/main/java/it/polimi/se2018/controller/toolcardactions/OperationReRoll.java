package it.polimi.se2018.controller.toolcardactions;

import it.polimi.se2018.controller.Turn;
import it.polimi.se2018.model.DiceContainer;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.player.Player;

import java.util.List;

public class OperationReRoll implements ToolOperation {


    private int numberOfRelativeTurn;

    public OperationReRoll(int numberOfRelativeTurn){

        this.numberOfRelativeTurn = numberOfRelativeTurn;
    }

    @Override
    public boolean start(DiceContainer diceContainer, PlayerMove playerMove, List<Die> dieList, List<Player> roundPlayerOrder, Turn turn) {

        if (numberOfRelativeTurn == 0 || numberOfRelativeTurn == 3 - roundPlayerOrder.stream().
                filter(p -> p.getNickname().equals(playerMove.getPlayer().getNickname())).count()){
            for(Die die: dieList) die.reRoll();
            return true;
        }
        return false;
    }

}
