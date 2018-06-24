package it.polimi.se2018.controller.toolcardactions;

import it.polimi.se2018.controller.Turn;
import it.polimi.se2018.model.DiceContainer;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.player.Player;

import java.util.List;

public class OperationDoublePick implements ToolOperation{

    public OperationDoublePick(){
        //Doesn't need standard attributes
    }

    @Override
    public boolean start(DiceContainer diceContainer, PlayerMove playerMove, List<Die> dieList, List<Player> roundPlayerOrder, Turn turn) {

        for(int i = roundPlayerOrder.size() - 1; i > 0; i--)
            if (roundPlayerOrder.get(i).getNickname().equals(playerMove.getPlayer().getNickname())) {
                roundPlayerOrder.remove(i);
                turn.incrementPossiblePicks();
                return true;
            }

        return false;
    }
}
