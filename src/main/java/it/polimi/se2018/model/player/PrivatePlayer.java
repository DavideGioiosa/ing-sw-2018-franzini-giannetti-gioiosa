package it.polimi.se2018.model.player;

**
        * Class used to save the Private Objective Card
        * in order not to allow the other players to find it out
        * @author Silvia Franzini
        */
public class PrivatePlayer extends Player {
    private PrivateObjCard privateObj;

    public PrivatePlayer(String nickname, Boolean connection, ColourEnum frame, SchemaCard schema, int tokens, PrivateObjCard card){
        super(nickname,connection,frame,schema,tokens);
        this.privateObj=card;
    }

    /**
     * @return Private Objective Card of the player
     */
    public PrivateObjCard getPrivateObj(){
        return privateObj;
    }
}
