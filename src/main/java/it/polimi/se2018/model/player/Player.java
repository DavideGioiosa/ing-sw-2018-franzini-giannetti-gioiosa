package it.polimi.se2018.model.player;

import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.cards.SchemaCard;

import java.io.Serializable;

/**
 * Class used to save the status of the player during the game
 * @author Silvia Franzini
 */
public class Player implements Serializable {
    private final String nickname;
    private boolean connectionStatus;
    private ColourEnum frameColour;
    private SchemaCard schemaCard;
    private int usableTokens;
    private int score;

    /**
     * Builder Method for class Player
     * @param nickname name chosen by the player
     * @param connection status of player's connection
     * @param frameColour frame colour chosen by the player
     * @param schemaCard window pattern card chosen by the player
     * @param tokens tokens of that window pattern card
     */
    public Player(String nickname, Boolean connection, ColourEnum frameColour, SchemaCard schemaCard, int tokens){
        if(schemaCard == null){
            throw new NullPointerException("ERROR: SchemaCard not existing");
        }
        this.nickname=nickname;
        this.frameColour=frameColour;
        this.schemaCard=schemaCard;
        this.usableTokens=tokens;
        this.score=0;
        this.connectionStatus=true;
    }

    /**
     * Used to save the actual connection status of the player
     * @param connectionStatus boolean used to identify if the player is connected or not
     */
    public void setConnectionStatus(boolean connectionStatus){
        this.connectionStatus = connectionStatus;
    }

    /**
     * Used to get the actual connection status of the player
     * @return true if connected, otherwise false
     */
    public boolean getConnectionStatus(){
        return connectionStatus;
    }

    /**
     * Used to get which frame colour the player chose
     * @return a colour in the ColourEnum enum
     */
    public ColourEnum getFrameColour(){
        return frameColour;
    }

    /**
     * Returns the player's window pattern card
     * @return window pattern card
     */
    public SchemaCard getSchemaCard(){
        return schemaCard;
    }

    /**
     * Updates the player's window pattern card after a move is made
     * @param die the die chosen by the player to perform an action
     * @param position the position in which the die will be placed
     */
    public void updateSchema(Die die, Position position){

        if(die == null){
            throw  new IllegalArgumentException("ERROR: die don't exists");
        }
        if(position.getIndexArrayPosition() < 0 || position.getIndexArrayPosition()> schemaCard.getCellList().size()) {
            throw new IndexOutOfBoundsException("ERROR: invalid position");
        }
        this.schemaCard.getCellList().get(position.getIndexArrayPosition()).insertDice(die);

    }

    /**
     * Returns the player's still available tokens
     * @return the number of tokens still available for the player
     */
    public int getTokens(){
        return usableTokens;
    }

    /**
     * Updates the number of available tokens after the usage of a ToolCard
     * @param tokensUsed the number of tokens that the ToolCard required
     */
    public void updateTokens(int tokensUsed){
        if(tokensUsed < 0 || tokensUsed > usableTokens){
            throw new IllegalArgumentException("ERROR: Cannot use that tokens");
        }
        this.usableTokens-= tokensUsed;
    }

    /**
     * Sets the score achieved by the player during the game
     * @param score the sum of the points scored by the player
     */
    public void setScore(int score){
        if(score < 0){
            throw new IllegalArgumentException("ERROR: Score cannot be negative");
        }
        this.score=score;
    }

    /**
     * Returns the total score of the player
     * @return the score totalized by the player
     */
    public int getScore(){
        return score;
    }

    /**
     * Returns the nickname chosen by the player
     * @return the player's nickname
     */
    public String getNickname(){
        return nickname;
    }
}
