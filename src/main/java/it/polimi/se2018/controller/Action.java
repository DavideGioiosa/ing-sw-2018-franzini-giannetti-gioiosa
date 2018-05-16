package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.player.Player;

public class Action {
    private Gameboard gameboard;
    private CheckRestriction checkRestriction;

    /**
     * Builder method of Action class
     * @param gameboard the game board of the match with all the elements on the board
     */
    public Action(Gameboard gameboard){
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
              placeDice(playerMove.getPlayer(), gameboard.getBoardDice().getDiceList().get(playerMove.getDiceBoardIndex()), playerMove.getDiceSchemaWhereToLeave());
          }catch(IllegalArgumentException e){
              return false;
          }
              break;
          case TOOL: try{
              useTool(playerMove);
          }catch(IllegalArgumentException e){
              return false;
          } break;
          case PASS: return true; break;
          case ROLL:  break;
          case EXTRACT: for(int i=0; i<gameboard.getPlayerList().size()*2 +1; i++){
              gameboard.getBagDice().extractDice();
          } break;
          default: break;

      }
      return true;
    }

    /**
     * Method to place a die on the board
     * @param actualPlayer player that selected the action
     * @param dice die chosen
     * @param position position where to put the die
     * @return true if action completed, false otherwise
     */
    private boolean placeDice(Player actualPlayer, Dice dice, Position position){
        boolean success=false;
        try{
            success = checkRestriction.adjacentColourRestriction(actualPlayer.getSchemaCard(), dice, position);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("ERROR: incorrect parameters");
        }
        if(success){
            try{
                success = checkRestriction.adjacentRestriction(actualPlayer.getSchemaCard(), dice, position);
            }catch(IllegalArgumentException e){
                throw new IllegalArgumentException("ERROR: incorrect parameters");
            }
            if(success){
                try{
                    success = checkRestriction.adjacentValueRestriction(actualPlayer.getSchemaCard(), dice, position);
                }catch(IllegalArgumentException e){
                    throw new IllegalArgumentException("ERROR: incorrect parameters");
                }
                if(success){
                    try{
                        success = checkRestriction.cellColourRestriction(actualPlayer.getSchemaCard(), dice, position);
                    }catch(IllegalArgumentException e){
                        throw new IllegalArgumentException("ERROR: incorrect parameters");
                    }
                    if(success){
                        try{
                            success = checkRestriction.cellValueRestriction(actualPlayer.getSchemaCard(), dice, position);
                        }catch(IllegalArgumentException e){
                            throw new IllegalArgumentException("ERROR: incorrect parameters");
                        }
                    }else return false;

                }else return false;

            }else return false;

        } else return false;

        actualPlayer.getSchemaCard().getCellList().get(position.getIndexArrayPosition()).insertDice(dice);
        return true;
    }

    /**
     * Mthod invokes the Tool card chosen by the player
     * @param playerMove the player choice
     * @return true if action completed, false otherwise
     */
    private boolean useTool(PlayerMove playerMove){

    }
}
