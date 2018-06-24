package it.polimi.se2018.controller.toolcardactions;

import it.polimi.se2018.controller.Turn;
import it.polimi.se2018.model.DiceContainer;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.player.Player;

import java.util.List;

public class OperationPickDice implements ToolOperation{

    private int minNumberOfDice;
    private int maxNumberOfDice;

    public OperationPickDice(int minNumberOfDice, int maxNumberOfDice){
        this.minNumberOfDice = minNumberOfDice;
        this.maxNumberOfDice = maxNumberOfDice;
    }

    @Override
    public boolean start(DiceContainer diceContainer, PlayerMove playerMove, List<Die> dieList, List<Player> roundPlayerOrder, Turn turn) {

        for(int i = 0; i < maxNumberOfDice && (i < minNumberOfDice || i < getNumberOfDice(playerMove)); i++){
            diceContainer.pickDice(playerMove, dieList);
        }
        return true;
    }

    private int getNumberOfDice(PlayerMove playerMove){
        if (!playerMove.getDiceSchemaWhereToLeave().isEmpty()) return playerMove.getDiceSchemaWhereToLeave().size();
        return 1;
    }
}

