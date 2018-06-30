package it.polimi.se2018.view;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.view.graphic.cli.CommandLineInput;

import static it.polimi.se2018.model.Config.*;
import static it.polimi.se2018.model.CommandTypeEnum.*;

/**
 * Controls the Syntax of message received and sets PlayerMove's attributes
 *
 * @author Cristian Giannetti
 */
public class SyntaxController {

    /**
     * Game Board used for the checks
     */
    private ClientBoard clientBoard;
    /**
     * Player who wants to makes the action
     */
    //private Player player;
    /**
     * Player Move representing the action that the player wants to make
     */
    private PlayerMove playerMove;

    private CommandLineInput commandLineInput;

    private final  String okMessage = "OK MESSAGE";
    private final String errorMessage = "ERROR MESSAGE";

    /**
     * Constructor sets the first nextCommandType
     */
    public SyntaxController(CommandLineInput commandLineInput){
//        this.player = player;
        this.commandLineInput = new CommandLineInput();
        this.commandLineInput = commandLineInput;
    }

    /**
     * Checks if the value inserted is valid
     * @param messageString String with Player's input
     * @return Error or OK message
     */
    public PlayerMove validCommand(String messageString, PlayerMove receivedPlayerMove, ClientBoard clientBoard){

        String message;
        CommandTypeEnum nextCommandType = resetCommand(receivedPlayerMove);

        while (nextCommandType != COMPLETE){

            message = commandLineInput.getInput(messageString);

            if (message == null) message = commandLineInput.getInput(errorMessage);
            message = message.toUpperCase();

            if (message.equals(RESET_COMMAND)){
                nextCommandType = resetCommand(receivedPlayerMove);

                message = commandLineInput.getInput(okMessage);

            }

            switch (nextCommandType) {
                case TYPEOFCHOICE:
                    try {
                        playerMove.setTypeOfChoice(TypeOfChoiceEnum.valueOf(message));
                        nextCommandType = setNextCommandType(playerMove);
                        break;
                    } catch (IllegalArgumentException e) {
                        break;
                    }

                case TOOLCARDID:
                    for (ToolCard toolCard : clientBoard.getCardOnBoard().getToolCardList()) {
                        if (Integer.valueOf(message) == toolCard.getId()) {
                            playerMove.setIdToolCard(Integer.valueOf(message));
                            nextCommandType = setNextCommandType(playerMove);
                            break;
                        }
                    }
                    break;

                case DICEBOARDINDEX:
                    if (Integer.valueOf(message) >= 0 && Integer.valueOf(message) < clientBoard.getBoardDice().getDieList().size()) {
                        playerMove.setDiceBoardIndex(Integer.valueOf(message));
                        nextCommandType = setNextCommandType(playerMove);
                    }
                    break;

                case VALUE:
                    playerMove.setValue(Integer.valueOf(message));
                    nextCommandType = setNextCommandType(playerMove);
                    break;
                default:
                    int value1 = Integer.parseInt(message.split(" ")[0]);
                    int value2 = Integer.parseInt(message.split(" ")[1]);
                    switch (nextCommandType) {

                        case TRACKBOARDINDEX:
                            if (value1 >= 0 && value1 < clientBoard.getTrackBoardDice().getDiceList().size() && value2 >= 0 &&
                                    value2 < clientBoard.getTrackBoardDice().getDiceList().get(value1).size()) {

                                playerMove.setTrackBoardIndex(value1, value2);
                                nextCommandType = setNextCommandType(playerMove);
                            }
                            break;

                        case DICESCHEMAWHERETOTAKE:
                            if (value1 >= 0 && value1 < NUMBER_OF_SCHEMA_ROW && value2 >= 0 && value2 < NUMBER_OF_SCHEMA_COL) {

                                playerMove.insertDiceSchemaWhereToTake(new Position(value1, value2));
                                nextCommandType = setNextCommandType(playerMove);
                            }
                            break;

                        case DICESCHEMAWHERETOLEAVE:
                            if (value1 >= 0 && value1 < NUMBER_OF_SCHEMA_ROW && value2 >= 0 && value2 < NUMBER_OF_SCHEMA_COL) {

                                playerMove.insertDiceSchemaWhereToLeave(new Position(value1, value2));
                                nextCommandType = setNextCommandType(playerMove);
                                break;
                            }
                            break;

                        default:
                    }
            }
        }
        return playerMove;
    }

    /**
     * Sets the next command type to be received
     * @param playerMove Actual status of PlayerMove
     */
    private CommandTypeEnum setNextCommandType(PlayerMove playerMove){

        switch(playerMove.getTypeOfChoice()) {

            case PICK:
                if (playerMove.getDiceBoardIndex() == -1) {
                    return CommandTypeEnum.DICEBOARDINDEX;
                }
                if (playerMove.getDiceSchemaWhereToLeave() == null || playerMove.getDiceSchemaWhereToLeave().isEmpty()) {
                    return CommandTypeEnum.DICESCHEMAWHERETOLEAVE;
                }
                return COMPLETE;

            case EXTRACT:
            case ROLL:
            case PASS:
                return COMPLETE;

            case TOOL:
                return CommandTypeEnum.TOOLCARDID;

            default:
                return TYPEOFCHOICE;
        }
    }

    /**
     * Gets Player Move
     * @return Actual status of the PlayerMove
     */
    public PlayerMove getPlayerMove(){
        return playerMove;
    }

    /**
     * Resets Command
     */
    private CommandTypeEnum resetCommand(PlayerMove receivedPlayerMove){
        try{
            playerMove = receivedPlayerMove.getClone();
        }catch (NullPointerException e){
            playerMove = new PlayerMove();
        }

//        this.playerMove.setPlayer(this.player);
        return CommandTypeEnum.TYPEOFCHOICE;
    }

}
