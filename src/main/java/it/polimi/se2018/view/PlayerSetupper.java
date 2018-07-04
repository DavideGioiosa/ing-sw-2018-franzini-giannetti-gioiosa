package it.polimi.se2018.view;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.SchemaCard;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.view.graphic.TypeOfInputAsked;

import java.util.HashMap;

import static it.polimi.se2018.model.CommandTypeEnum.*;


/**
 * Manages the setup of the player at the beginning of the game
 * @author Cristian Giannetti
 */
public class PlayerSetupper extends Observable<PlayerMessage>{

    private User user;

    private PlayerChoice playerChoice;

    private CommandTypeEnum nextCommandType;

    private HashMap<CommandTypeEnum, Integer> messageMap;
    /**
     * Constructor sets input class
     */
    public PlayerSetupper(){
        playerChoice = null;
        nextCommandType = null;
        this.user = null;

        this.messageMap = new HashMap<>();
        messageMap.put(FRAMECOLOUR, 3500);
        messageMap.put(IDSCHEMA, 3501);
        messageMap.put(NICKNAME, 3503);
        messageMap.put(COMPLETE, 3510);
    }

    /**
     *
     * @param playerChoice
     * @return
     */
    public TypeOfInputAsked newChoiceReceived(PlayerChoice playerChoice){
        this.playerChoice = playerChoice;
        setCommandType();
        return new TypeOfInputAsked(nextCommandType, null, 0, messageMap.get(nextCommandType));
    }

    /**
     *
     * @param user
     * @return
     */
    public TypeOfInputAsked newUserReceived(User user){
        if(user == null){
            nextCommandType = COMPLETE;
            return new TypeOfInputAsked(nextCommandType, null, 0, messageMap.get(nextCommandType));
        }
        this.user = user;
        nextCommandType = NICKNAME;
        return new TypeOfInputAsked(nextCommandType, null, 0, 3503);
    }

    /**
     *
     */
    private void setCommandType() {
        if(playerChoice.getChosenColour() == null && playerChoice.getColourEnumList() != null && !playerChoice.getColourEnumList().isEmpty()) {
            nextCommandType = FRAMECOLOUR;
            return;
        }
        if(playerChoice.getIdChosenSchema() == 0 && playerChoice.getSchemaCardList() != null && !playerChoice.getSchemaCardList().isEmpty()){
            nextCommandType = IDSCHEMA;
            return;
        }
        setCompleteMove();
    }



    /**
     * Checks the Colour Frame choice made by the player
     * @return Error or Ok message
     */
    public TypeOfInputAsked validCommand(String inputReceived){

        switch (nextCommandType) {
            case FRAMECOLOUR:
                try{
                    setFrameColour(inputReceived);
                    return new TypeOfInputAsked(nextCommandType, null, 0, nextMessage(COMPLETE));
                } catch (IllegalArgumentException e) {
                    return new TypeOfInputAsked(nextCommandType, null, 2500, nextMessage(FRAMECOLOUR));
                }

            case IDSCHEMA:
                try{
                    setIdSchema(inputReceived);
                    return new TypeOfInputAsked(nextCommandType, null, 0, nextMessage(COMPLETE));
                } catch (IllegalArgumentException e) {
                    return new TypeOfInputAsked(nextCommandType, null, 2501, nextMessage(IDSCHEMA));
                }
            default:
        }

        setCommandType();
        return new TypeOfInputAsked(nextCommandType, null, 2501, nextMessage(nextCommandType));
    }

    private int nextMessage(CommandTypeEnum commandTypeEnum){
        return messageMap.get(commandTypeEnum);
    }

    private void setFrameColour(String inputReceived){
        inputReceived = inputReceived.toUpperCase();
        ColourEnum colourToCheck;
        colourToCheck = ColourEnum.valueOf(inputReceived);
        for (ColourEnum colour : playerChoice.getColourEnumList()) {
            if (colourToCheck == colour){
                playerChoice.setChosenColour(colour);
                setCompleteMove();
                return;
            }
        }
        throw new IllegalArgumentException();
    }

    private void setCompleteMove(){
        nextCommandType = COMPLETE;
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setChoice(playerChoice);
        notify(playerMessage);
    }

    private void setIdSchema(String inputReceived){
        int idSchema = Integer.parseInt(inputReceived);

        for(SchemaCard schema: playerChoice.getSchemaCardList()){
            if (schema.getId() == idSchema) {
                playerChoice.setIdChosenSchema(idSchema);
                setCompleteMove();
                return;
            }
        }
        throw new IllegalArgumentException();
    }



    /**
     * Sets nickname to User received
     * @param nickname nickname selected by the User
     * @return User with nickname set
     */
    public TypeOfInputAsked validNickname(String nickname){
        if(nickname == null || nickname == "") {
            nextCommandType = NICKNAME;
            return new TypeOfInputAsked(nextCommandType, null, 2502, messageMap.get(nextCommandType));
        }
        if( nickname.length() > 25) {
            nextCommandType = NICKNAME;
            return new TypeOfInputAsked(nextCommandType, null, 2503, messageMap.get(nextCommandType));
        }

        user.setNickname(nickname);
        nextCommandType = COMPLETE;
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setUser(user);
        notify(playerMessage);
        return new TypeOfInputAsked(nextCommandType,null, 0, messageMap.get(nextCommandType));
    }



    /*public TypeOfInputAsked validPath(String message){
        if(message == "" || message == null){
            PlayerMessage playerMessage = new PlayerMessage();
            playerMessage.setId(PlayerMessageTypeEnum.DISCONNECTED);
            playerMessage.setServerPath();
        }

    }*/

    public boolean reconnect(String message){
        if (!(message.equalsIgnoreCase("no") || message.equalsIgnoreCase("n"))){
            PlayerMessage playerMessage = new PlayerMessage();
            playerMessage.setId(PlayerMessageTypeEnum.DISCONNECTED);
            notify(playerMessage);
            return true;
        }
        return false;
    }
}
