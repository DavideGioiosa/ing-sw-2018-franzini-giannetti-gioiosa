package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.player.Player;
import it.polimi.se2018.model.restriction.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the single player's move
 * @author Silvia Franzini
 */
public class Action {
    private GameBoard gameBoard;
    private PlayerMove playerMove;

    private List<Restriction> restrictionList;

    /**
     * Builder method of Action class
     * @param gameBoard the game board of the match with all the elements on the board
     */
    public Action(GameBoard gameBoard){
        this.gameBoard = gameBoard;

        restrictionList = new ArrayList<>();
        restrictionList.add(new RestrictionFirstDieOnBorder());
        restrictionList.add(new RestrictionAdjacent());
        restrictionList.add(new RestrictionCellValue());
        restrictionList.add(new RestrictionCellColour());
        restrictionList.add(new RestrictionAdjacentColour());
        restrictionList.add(new RestrictionAdjacentValue());
    }

    /**
     * Choice of the player
     * @return playerMove related to the action
     */
    public PlayerMove getPlayerMove() {
        return playerMove;
    }

    /**
     * Method used to call the correct action chosen by the player
     * @param playerMove choices of the player
     * @return true if action completed, false otherwise
     */
    public boolean selectAction(PlayerMove playerMove){
        this.playerMove = playerMove;
        switch (playerMove.getTypeOfChoice()){
            case PICK:
                try{
                    for(Player player: gameBoard.getPlayerList()) {
                        if (player.getNickname().equals(playerMove.getPlayer().getNickname())){
                            Die dieExtracted = gameBoard.getBoardDice().takeDie(playerMove.getDiceBoardIndex());
                            int error = placeDice(player, dieExtracted, playerMove.getDiceSchemaWhereToLeave().get(0));
                            if(error == 0) gameBoard.getBoardDice().insertDie(dieExtracted);
                            //TODO: else Notifica VIEW
                        }
                    }
                }catch(IllegalArgumentException e){
                    return false;
                }
                break;
            case TOOL:
                try{
                    useTool(playerMove);
                }catch(IllegalArgumentException e){
                    return false;
                }
                break;
            case PASS:
                break;
            case ROLL:
                for(Die die : gameBoard.getBoardDice().getDieList()){
                    try{
                        die.firstRoll();
                    }catch(RuntimeException e){
                        return false;
                    }
                }
                break;
            case EXTRACT:
                for(int i = 0; i < gameBoard.getPlayerList().size()*2 +1; i++){
                    //try{}catch da implementare nella classe BoardDice
                    Die die;
                    try{
                        die = gameBoard.getBagDice().extractDice();
                    }catch(RuntimeException e){
                        return false;
                    }
                    gameBoard.getBoardDice().insertDie(die);
                } break;
            default:
                break;
        }
        return true;
    }

    /**
     * Method to place a die on the board
     * @param actualPlayer player that selected the action
     * @param die die chosen
     * @param position position where to put the die
     * @return true if action completed, false otherwise
     */
    private int placeDice(Player actualPlayer, Die die, Position position){

        for(Restriction restriction: restrictionList){
            int error = restriction.checkRestriction(playerMove.getPlayer().getSchemaCard(),
                    gameBoard.getBoardDice().getDieList().get(playerMove.getDiceBoardIndex()),
                    playerMove.getDiceSchemaWhereToLeave().get(0));
            if(error != 0) return error;
        }

        actualPlayer.getSchemaCard().setDiceIntoCell(position,die);
        return 0;
    }

    /**
     * Method invokes the Tool card chosen by the player
     * @param playerMove the player choice
     * @return true if action completed, false otherwise
     */
    private boolean useTool(PlayerMove playerMove){
        return false;
    }
}
