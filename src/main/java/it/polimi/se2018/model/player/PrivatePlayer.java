package it.polimi.se2018.model.player;

import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.cards.PrivateObjCard;
import it.polimi.se2018.model.cards.SchemaCard;

/**
* Class used to save the Private Objective Card
* in order not to allow the other players to find it out
* @author Silvia Franzini
*/
public class PrivatePlayer {
    private PrivateObjCard privateObj;
    private Player ply;

    public PrivatePlayer(SchemaCard schema, PrivateObjCard card){
        this. ply=ply;
        this.privateObj=card;
    }

    /**
     * @return Private Objective Card of the player
     */
    public PrivateObjCard getPrivateObj(){
        return privateObj;
    }

    /**
     *
     * @return Player whose Private Objective Card is stored
     */
    public Player getPlayer(){
        return ply;
    }
}
