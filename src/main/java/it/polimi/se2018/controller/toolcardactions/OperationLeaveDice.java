package it.polimi.se2018.controller.toolcardactions;

import it.polimi.se2018.controller.Turn;
import it.polimi.se2018.model.DiceContainer;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.restriction.Restriction;
import it.polimi.se2018.model.restriction.RestrictionManager;

import java.util.List;

public class OperationLeaveDice implements ToolOperation{

    private List<Restriction> restrictionList;

    public OperationLeaveDice(String avoidedRestriction){

        restrictionList = RestrictionManager.getRestrictionWithoutAvoided(avoidedRestriction);
    }

    @Override
    public boolean start(DiceContainer diceContainer, PlayerMove playerMove, List<Die> dieList, List<Player> roundPlayerOrder, Turn turn) {
        if(!dieList.isEmpty()){
            return diceContainer.leaveDice(playerMove, dieList, restrictionList);
        }
        return true;
    }

}
