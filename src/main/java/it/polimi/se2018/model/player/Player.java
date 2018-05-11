package it.polimi.se2018.model.player;

import it.polimi.se2018.model.ColourEnum;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.cards.SchemaCard;

/**
 * Class used to save the status of the player during the game
 * @author Silvia Franzini
 */
public class Player {
    private final String nickname;
    private boolean connectionStatus;
    private ColourEnum frameColour;
    private SchemaCard schemaCard;
    private int usableTokens;
    private int score;

    public Player(String nickname, Boolean connection, ColourEnum frameColour, SchemaCard schemaCard, int tokens){
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
            this.connectionStatus=connectionStatus;

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
     * @param dice the dice chosen by the player to perform an action
     * @param position the position in which the dice will be placed
     */
    public void updateSchema(Dice dice, Position position){

        this.schemaCard.getCellList().get(position.getIndexArrayPosition()).setDice(dice);

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

        this.usableTokens-= tokensUsed;
    }

    /**
     * Sets the score achieved by the player during the game
     * @param score the sum of the points scored by the player
     */
    public void setScore(int score){
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
