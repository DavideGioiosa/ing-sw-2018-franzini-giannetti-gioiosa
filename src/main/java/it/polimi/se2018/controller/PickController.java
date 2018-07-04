package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.restriction.*;

import java.util.List;

/**
 * Manages the single player's move
 * @author Silvia Franzini
 */
public class PickController implements Action{

    private PlayerMove playerMove;

    private List<Restriction> restrictionList;

    /**
     * Constructor method of PickController class
     */
    PickController(){
        restrictionList = RestrictionManager.getStandardRestriction();
    }

    /**
     * Makes the move chose by the player
     * @param playerMove Contains information about the Player's move
     * @param gameBoard Game board of the match with all the elements on the board
     * @return Error code, 0 if there isn't any error
     */
    private int pickExecution(PlayerMove playerMove, GameBoard gameBoard){
        Die dieExtracted = null;
        try{
            for(Player player: gameBoard.getPlayerList()) {
                if (player.getNickname().equals(playerMove.getPlayer().getNickname())){
                    dieExtracted = gameBoard.getBoardDice().takeDie(playerMove.getDiceBoardIndex());
                    int error = placeDice(player, dieExtracted, playerMove.getDiceSchemaWhereToLeave().get(0));
                    if(error != 0){
                        gameBoard.getBoardDice().insertDie(dieExtracted);
                        return error;
                    }
                }
            }
        }catch(IllegalArgumentException e){
            if (dieExtracted != null) gameBoard.getBoardDice().insertDie(dieExtracted);

            //TODO: lanciare errore
        }
        return 0;
    }

    /**
     * Choice of the player
     * @return playerMove related to the action
     */
    public boolean isPass() {
        return playerMove != null && playerMove.getTypeOfChoice() == TypeOfChoiceEnum.PASS;
    }

    @Override
    public PlayerMove getPlayerMove() {
        return new PlayerMove();
    }

    /**
     * Method used to call the correct action chosen by the player
     * @param playerMove choices of the player
     * @return true if action completed, false otherwise
     */
    public int doAction(GameBoard gameBoard, PlayerMove playerMove, List<Player> roundPlayerOrder, Turn turn){

        this.playerMove = playerMove;
        switch (playerMove.getTypeOfChoice()){
            case PICK:
                return pickExecution(playerMove, gameBoard);

            case PASS:
                return 0;

            default:
                return 1000;
        }
    }

    /**
     * Method to place a die on the board
     * @param actualPlayer player that selected the action
     * @param die die chosen
     * @param position position where to put the die
     * @return true if action completed, false otherwise
     */
    private int placeDice(Player actualPlayer, Die die, Position position){
        if(actualPlayer.getSchemaCard().getCellList().get(position.getIndexArrayPosition()).isEmpty()) {
            for (Restriction restriction : restrictionList) {
                int error = restriction.checkRestriction(actualPlayer.getSchemaCard(), die, playerMove.getDiceSchemaWhereToLeave().get(0));
                if (error != 0) return error;
            }

            actualPlayer.getSchemaCard().setDiceIntoCell(position, die);
            return 0;
        }
        return 2008;
    }

    @Override
    public int doDefaultMove(GameBoard gameBoard){
        playerMove = new PlayerMove();
        playerMove.setTypeOfChoice(TypeOfChoiceEnum.PASS);
        return 0;
    }

    @Override
    public boolean isComplete() {
        return playerMove != null;
    }

}
