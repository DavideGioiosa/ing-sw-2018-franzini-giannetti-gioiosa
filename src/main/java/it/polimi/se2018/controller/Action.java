package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.player.Player;

public class Action {
    private GameBoard gameboard;
    private CheckRestriction checkRestriction;

    /**
     * Builder method of Action class
     * @param gameboard the game board of the match with all the elements on the board
     */
    public Action(GameBoard gameboard){
        this.gameboard=gameboard;
        checkRestriction = new CheckRestriction();
    }

    /**
     * Method used to call the correct action chosen by the player
     * @param playerMove choices of the player
     * @return true if action completed, false otherwise
     */
    public boolean selectAction(PlayerMove playerMove){
      switch (playerMove.getTypeOfChoice()){
          case PICK: try{
             placeDice(playerMove.getPlayer(), gameboard.getBoardDice().getDieList().get(playerMove.getDiceBoardIndex()),playerMove.getDiceSchemaWhereToLeave().get(0));
          }catch(IllegalArgumentException e){
              return false;
          }
              break;
          case TOOL: try{
              useTool(playerMove);
          }catch(IllegalArgumentException e){
              return false;
          }
          break;
          case PASS: break;
          case ROLL: for(Die die : gameboard.getBoardDice().getDieList()){
              try{
                  die.firstRoll();
              }catch(RuntimeException e){return false;}

          }; break;
          case EXTRACT: for(int i=0; i<gameboard.getPlayerList().size()*2 +1; i++){
              //try{}catch da implementare nella classe BoardDice
              Die die;
              try{
                 die = gameboard.getBagDice().extractDice();
              }catch(RuntimeException e){
                  return false;
              }
             gameboard.getBoardDice().insertDice(die);
          } break;
          default: break;
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
    private boolean placeDice(Player actualPlayer, Die die, Position position){
        boolean success = false;

        try{
            success = checkRestriction.adjacentColourRestriction(actualPlayer.getSchemaCard(), die, position);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("ERROR: incorrect parameters");
        }
        if(!success){return false;}
        try{
            success = checkRestriction.adjacentRestriction(actualPlayer.getSchemaCard(), die, position);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("ERROR: incorrect parameters");
        }
        if(!success){return false;}
        try{
            success = checkRestriction.adjacentValueRestriction(actualPlayer.getSchemaCard(), die, position);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("ERROR: incorrect parameters");
        }
        if(!success){return false;}
        try{
            success = checkRestriction.cellColourRestriction(actualPlayer.getSchemaCard(), die, position);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("ERROR: incorrect parameters");
        }
        if(!success){return false;}
        try{
            success = checkRestriction.cellValueRestriction(actualPlayer.getSchemaCard(), die, position);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("ERROR: incorrect parameters");
        }
        if(!success){return false;}

        actualPlayer.getSchemaCard().getCellList().get(position.getIndexArrayPosition()).insertDice(die);
        return true;
    }

    /**
     * Mthod invokes the Tool card chosen by the player
     * @param playerMove the player choice
     * @return true if action completed, false otherwise
     */
    private boolean useTool(PlayerMove playerMove){
        return false;
    }
}
