package it.polimi.se2018.controller.toolcardactions;

import it.polimi.se2018.controller.Turn;
import it.polimi.se2018.model.DiceContainer;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.restriction.Restriction;
import it.polimi.se2018.model.restriction.RestrictionManager;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.se2018.model.Config.*;

public class OperationCanBePlaced implements ToolOperation {

    public OperationCanBePlaced(){
        //Doesn't need standard parameters
    }

    @Override
    public boolean start(DiceContainer diceContainer, PlayerMove playerMove, List<Die> dieList, List<Player> roundPlayerOrder, Turn turn) {
        List<Integer> restrictionErrors;
        for(Die die: dieList){
            for (int i = 0; i < NUMBER_OF_SCHEMA_ROW * NUMBER_OF_SCHEMA_COL; i++){
                restrictionErrors = new ArrayList<>();
                for (Restriction restriction: RestrictionManager.getStandardRestriction()) {
                    restrictionErrors.add(restriction.checkRestriction(playerMove.getPlayer().getSchemaCard(), die, new Position(i)));
                }
                if(restrictionErrors.stream().allMatch(n -> n == 0)) return true;
            }

        }
        return false;
    }
}
