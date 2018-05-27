package it.polimi.se2018.view;

import it.polimi.se2018.model.GameBoard;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.TypeOfChoiceEnum;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.player.Player;

import static it.polimi.se2018.model.Config.*;

/**
 * Controls the Syntax of message received and sets PlayerMove's attributes
 *
 * @author Cristian Giannetti
 */
public class SyntaxController {

    /**
     * Game Board used for the checks
     */
    private GameBoard gameBoard;
    /**
     * Contains the next type of command that the software expects
     */
    private CommandTypeEnum nextCommandType;
    /**
     * Player who wants to makes the action
     */
    private Player player;
    /**
     * Player Move representing the action that the player wants to make
     */
    private PlayerMove playerMove;

    /**
     * Constructor sets the first nextCommandType
     * @param gameBoard Game Board for the move checks
     * @param player The player that wants to make this action
     */
    public SyntaxController(GameBoard gameBoard, Player player){
        this.gameBoard = gameBoard;
        nextCommandType = CommandTypeEnum.TYPEOFCHOICE;
        this.player = player;
    }

    /**
     * Checks if the value inserted is valid
     * @param message String with Player's input
     * @return Error or OK message
     */
    public String validCommand(String message){
        String outputMessage;

        if (message == null) return "COMANDO ERRATO";
        message = message.toUpperCase();

        if (message.equals(RESETCOMMAND)){
            nextCommandType = CommandTypeEnum.TYPEOFCHOICE;
            playerMove = new PlayerMove();
            playerMove.setPlayer(player);
            return "OK MESSAGE";

        }

        switch (nextCommandType) {
            case TYPEOFCHOICE:
                try {
                    playerMove.setTypeOfChoice(TypeOfChoiceEnum.valueOf(message));
                    setNextCommandType(playerMove);
                    return "OK MESSAGE";
                } catch (IllegalArgumentException e) {
                    return "COMANDO ERRATO";
                }

            case TOOLCARDID:
                for (ToolCard toolCard : gameBoard.getCardOnBoard().getToolCardList()) {
                    if (Integer.valueOf(message) == toolCard.getId()) {
                        playerMove.setIdToolCard(Integer.valueOf(message));
                        setNextCommandType(playerMove);
                        return "OK MESSAGE";
                    }
                }
                return "COMANDO ERRATO";

            case DICEBOARDINDEX:
                if (Integer.valueOf(message) >= 0 && Integer.valueOf(message) < gameBoard.getBoardDice().getDieList().size()) {
                    playerMove.setDiceBoardIndex(Integer.valueOf(message));
                    setNextCommandType(playerMove);
                    return "OK MESSAGE";
                }
                return "COMANDO ERRATO";

            case VALUE:
                playerMove.setValue(Integer.valueOf(message));
                setNextCommandType(playerMove);
                return "OK MESSAGE";
        }

        int value1 = Integer.parseInt(message.split(" ")[0]);
        int value2 = Integer.parseInt(message.split(" ")[1]);

        switch (nextCommandType) {

            case TRACKBOARDINDEX:
                if (value1 >= 0 && value1 < gameBoard.getTrackBoardDice().getDiceList().size() && value2 >=0 &&
                        value2 < gameBoard.getTrackBoardDice().getDiceList().get(value1).size()){

                    playerMove.setTrackBoardIndex(value1,value2);
                    setNextCommandType(playerMove);
                    return "OK MESSAGE";
                }
                return "COMANDO ERRATO";

            case DICESCHEMAWHERETOTAKE:
                if (value1 >= 0 && value1 < NUMBEROFSCHEMAROW && value2 >=0 && value2 < NUMBEROFSCHEMACOL){

                    playerMove.insertDiceSchemaWhereToTake(new Position(value1,value2));
                    setNextCommandType(playerMove);
                    return "OK MESSAGE";
                }
                return "COMANDO ERRATO";

            case DICESCHEMAWHERETOLEAVE:
                if (value1 >= 0 && value1 < NUMBEROFSCHEMAROW && value2 >=0 && value2 < NUMBEROFSCHEMACOL){

                    playerMove.insertDiceSchemaWhereToLeave(new Position(value1,value2));
                    setNextCommandType(playerMove);
                    return "OK MESSAGE";
                }
                return "COMANDO ERRATO";
        }

        return "COMANDO ERRATO";
    }

    /**
     * Sets the next command type to be received
     * @param playerMove Actual status of PlayerMove
     */
    private void setNextCommandType(PlayerMove playerMove){

        if (playerMove.getTypeOfChoice() == null){
                nextCommandType = CommandTypeEnum.TYPEOFCHOICE;
                return;
        }

        if (playerMove.getTypeOfChoice() == TypeOfChoiceEnum.PICK) {
            if (playerMove.getDiceBoardIndex() == -1) {
                nextCommandType = CommandTypeEnum.DICEBOARDINDEX;
                return;
            }
            if (playerMove.getDiceSchemaWhereToLeave() == null) {
                nextCommandType = CommandTypeEnum.DICESCHEMAWHERETOLEAVE;
                return;
            }
            nextCommandType = CommandTypeEnum.COMPLETE;
            return;
        }

        if (playerMove.getTypeOfChoice() == TypeOfChoiceEnum.EXTRACT) {
            nextCommandType = CommandTypeEnum.COMPLETE;
            return;
        }

        if (playerMove.getTypeOfChoice() == TypeOfChoiceEnum.ROLL){
            nextCommandType = CommandTypeEnum.COMPLETE;
            return;
        }

        if (playerMove.getTypeOfChoice() == TypeOfChoiceEnum.PASS) {
            nextCommandType = CommandTypeEnum.COMPLETE;
            return;
        }

        if (playerMove.getTypeOfChoice() == TypeOfChoiceEnum.TOOL){
            nextCommandType = CommandTypeEnum.TOOLCARDID;
            return;
        }
    }

}
