package it.polimi.se2018.view.graphic.cli;

import it.polimi.se2018.model.ClientBoard;
import it.polimi.se2018.model.CommandTypeEnum;
import it.polimi.se2018.model.PlayerChoice;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.model.player.User;
import it.polimi.se2018.view.InputStrategy;
import it.polimi.se2018.view.PlayerSetupper;
import it.polimi.se2018.view.SyntaxController;
import it.polimi.se2018.view.graphic.TypeOfInputAsked;

import java.util.Scanner;

import static it.polimi.se2018.view.graphic.cli.CommandLineGraphic.showChoice;
import static it.polimi.se2018.view.graphic.cli.CommandLinePrint.*;


public class CommandLineInput implements InputStrategy {

    private boolean timeOut;
    private SyntaxController syntaxController;
    private PlayerSetupper playerSetupper;

    /**
     * Constructor sets Controllers for Syntax of Input
     * @param syntaxController Syntax controller for input in Game
     * @param playerSetupper Syntax Controller for Player's setup
     */
    public CommandLineInput(SyntaxController syntaxController, PlayerSetupper playerSetupper){
        this.syntaxController = syntaxController;
        this.playerSetupper = playerSetupper;
    }


    public void yourTurn(ClientBoard clientBoard, PlayerMove playerMove) {

        TypeOfInputAsked typeOfInputAsked = syntaxController.newMoveReceived(playerMove, clientBoard);
        CommandTypeEnum nextCommandType = typeOfInputAsked.getCommandTypeEnum();
        timeOut = false;

        Scanner scanner = new Scanner(System.in);
        printMessage(typeOfInputAsked.getMessage());

        while (nextCommandType != CommandTypeEnum.COMPLETE && !timeOut){

            typeOfInputAsked = syntaxController.validCommand(scanner.nextLine());
            nextCommandType = typeOfInputAsked.getCommandTypeEnum();
            printMessage(typeOfInputAsked.getMessage());
        }
        printMessage(typeOfInputAsked.getMessage());

    }

    public void makeChoice(PlayerChoice playerChoice){
        TypeOfInputAsked typeOfInputAsked = playerSetupper.newChoiceReceived(playerChoice);
        CommandTypeEnum nextCommandType = typeOfInputAsked.getCommandTypeEnum();

        showChoice(playerChoice, typeOfInputAsked.getCommandTypeEnum());

        timeOut = false;
        Scanner scanner = new Scanner(System.in);

        printMessage(typeOfInputAsked.getMessage());
        while (nextCommandType != CommandTypeEnum.COMPLETE && !timeOut){

            typeOfInputAsked = playerSetupper.validCommand(scanner.nextLine());
            nextCommandType = typeOfInputAsked.getCommandTypeEnum();
            printMessage(typeOfInputAsked.getMessage());
        }
        printMessage(1000);

    }

    public void choseNickname(User user){

        TypeOfInputAsked typeOfInputAsked = playerSetupper.newUserReceived(user);

        CommandTypeEnum nextCommandType = typeOfInputAsked.getCommandTypeEnum();
        printMessage(typeOfInputAsked.getMessage());

        timeOut = false;
        Scanner scanner = new Scanner(System.in);

        while (nextCommandType != CommandTypeEnum.COMPLETE && !timeOut){
            typeOfInputAsked = playerSetupper.validNickname(scanner.nextLine());
            nextCommandType = typeOfInputAsked.getCommandTypeEnum();
            printMessage(typeOfInputAsked.getMessage());
        }

        printMessage(1000);
    }

    @Override
    public void tryReconnection() {
        Scanner scanner = new Scanner(System.in);
        printMessage(4000);
        boolean choice = playerSetupper.reconnect(scanner.nextLine());

    }

    @Override
    public void validPath() {

    }

    public void showMessage(int idMessage){
        printMessage(idMessage);
    }

    public void showError(int idError){
        printError(idError);
    }

    @Override
    public SyntaxController getSyntaxController() {
        return syntaxController;
    }

    @Override
    public PlayerSetupper getPlayerSetupper() {
        return playerSetupper;
    }

}
