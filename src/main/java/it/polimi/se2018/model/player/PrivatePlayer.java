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
    private Player player;

    /**
     * Builder method of PrivatePlayer's class
     * @param player Player associated to this PrivatePlayer
     * @param card Private Objective Card associated to the Player
     */
    public PrivatePlayer(Player player, PrivateObjCard card){
        this.player=player;
        this.privateObj=card;
    }

    /**
     * Method returns the Private Objective Card
     * @return Private Objective Card of the player
     */
    public PrivateObjCard getPrivateObj(){
        return privateObj;
    }

    /**
     *Method returns the Player
     * @return Player whose Private Objective Card is stored
     */
    public Player getPlayer(){
        return player;
    }
}
