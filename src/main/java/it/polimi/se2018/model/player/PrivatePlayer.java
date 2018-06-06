package it.polimi.se2018.model.player;


import it.polimi.se2018.model.cards.PrivateObjCard;

import java.io.Serializable;


/**
* Class used to save the Private Objective Card
* in order not to allow the other players to find it out
* @author Silvia Franzini
*/
public class PrivatePlayer implements Serializable {
    private PrivateObjCard privateObj;
    private Player player;

    /**
     * Builder method of PrivatePlayer's class
     * @param player Player associated to this PrivatePlayer
     * @param card Private Objective Card associated to the Player
     */
    public PrivatePlayer(Player player, PrivateObjCard card){
        if(player == null){
            throw new NullPointerException("ERROR: Player not existing");
        }
        if(card == null){
            throw new NullPointerException("ERROR: PrivateObj card not existing");
        }
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
     * Method returns the Player
     * @return Player whose Private Objective Card is stored
     */
    public Player getPlayer(){
        return player;
    }
}
