package it.polimi.se2018.view;

import it.polimi.se2018.model.*;

/**
 * View's Class: Command Controller
 *
 * @author Davide Gioiosa
 */

public class CommandController{

    /**
     * Checks the validity of the Player Move
     */
    private CheckRestriction checkRestriction;
    /**
     *
     */
    private View view;

    /**
     * Builder of the class, creates a checkRestriction object
     * @param view
     */
    public CommandController (View view){
        checkRestriction = new CheckRestriction();
        this.view = view;
    }

    /**
     * Checks if the action that the current player wants to do is legal and acceptable for the rules
     * 1 - All the CheckRestriction (0,1,4,5,6,7,8,10,11,12)
     * 2 - All the CheckRestriction except cellColourRestriction (2)
     * 3 - All the CheckRestriction except cellValueRestriction (3)
     * 4 - All the CheckRestriction except adjacentRestriction (9)
     * @param playerMove Contains the complete action of the current player
     * @return 0 if the action is correct, else the ID code of the error why the action can't be done
     */
    public int checkMoveValidity (PlayerMove playerMove, ClientBoard clientBoard){

        switch(playerMove.getTypeOfChoice()) {
            case PASS:
                return 0;

            case EXTRACT:
                if (clientBoard.getBoardDice().getDieList().isEmpty()) return 0;
                else return 1000;

            case ROLL:
                try {
                    if (clientBoard.getBoardDice().getDieList().get(0).getValue() == 0) return 0;
                    else return 1000;
                } catch (NullPointerException e) {
                    return 1000;
                }

            case PICK:
            case TOOL:
                return checkRestrictionValidity(playerMove, clientBoard);

            default: return 0;

        }
    }

    private int checkRestrictionValidity (PlayerMove playerMove, ClientBoard clientBoard) {

        if (playerMove.getPlayer().getSchemaCard().isEmpty() &&
                !checkRestriction.isOnTheBorder(playerMove.getDiceSchemaWhereToLeave().get(0))) {
            return 1;
        }

        if(!playerMove.getPlayer().getSchemaCard().isEmpty() &&
                playerMove.getIdToolCard() != 9 && !checkRestriction.adjacentRestriction(playerMove.getPlayer().getSchemaCard(),
                clientBoard.getBoardDice().getDieList().get(playerMove.getDiceBoardIndex()),
                playerMove.getDiceSchemaWhereToLeave().get(0))){
            return 2;
        }

        if(playerMove.getIdToolCard() != 2 && !checkRestriction.cellColourRestriction(playerMove.getPlayer().getSchemaCard(),
                clientBoard.getBoardDice().getDieList().get(playerMove.getDiceBoardIndex()),
                playerMove.getDiceSchemaWhereToLeave().get(0))){
            return 3;
        }

        if(playerMove.getIdToolCard() != 3 && !checkRestriction.cellValueRestriction(playerMove.getPlayer().getSchemaCard(),
                clientBoard.getBoardDice().getDieList().get(playerMove.getDiceBoardIndex()),
                playerMove.getDiceSchemaWhereToLeave().get(0))){
            return 4;
        }

        if(!playerMove.getPlayer().getSchemaCard().isEmpty() &&
                !checkRestriction.adjacentColourRestriction(playerMove.getPlayer().getSchemaCard(),
                        clientBoard.getBoardDice().getDieList().get(playerMove.getDiceBoardIndex()),
                        playerMove.getDiceSchemaWhereToLeave().get(0))){
            return 5;
        }

        if(!playerMove.getPlayer().getSchemaCard().isEmpty() &&
                !checkRestriction.adjacentValueRestriction(playerMove.getPlayer().getSchemaCard(),
                        clientBoard.getBoardDice().getDieList().get(playerMove.getDiceBoardIndex()),
                        playerMove.getDiceSchemaWhereToLeave().get(0))){
            return 6;
        }

        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setCheckMove(playerMove);
        return 0;
    }

    public PlayerMove getResetPlayerMove(PlayerMove playerMove){
        PlayerMove newPlayerMove = new PlayerMove();
        newPlayerMove.setPlayer(playerMove.getPlayer());
        return newPlayerMove;
    }

}
