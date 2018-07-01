package it.polimi.se2018.view.graphic.cli;

import it.polimi.se2018.model.ClientBoard;
import it.polimi.se2018.model.CommandTypeEnum;
import it.polimi.se2018.model.PlayerMove;
import it.polimi.se2018.view.InputStrategy;
import it.polimi.se2018.view.SyntaxController;
import it.polimi.se2018.view.graphic.TypeOfInputAsked;

import java.util.Scanner;

import static it.polimi.se2018.view.graphic.cli.CommandLinePrint.print;
import static it.polimi.se2018.view.graphic.cli.CommandLinePrint.println;


public class CommandLineInput implements InputStrategy {

    /**
     * Standard string for invalid input
     */
    private final String invalidInput = "Invalid input";
    private boolean timeOut;
    private SyntaxController syntaxController;

    public CommandLineInput(SyntaxController syntaxController){
        this.syntaxController = syntaxController;
    }


    public void yourTurn(ClientBoard clientBoard, PlayerMove playerMove) {

        TypeOfInputAsked typeOfInputAsked = syntaxController.newMoveReceived(playerMove, clientBoard);
        CommandTypeEnum nextCommandType = typeOfInputAsked.getCommandTypeEnum();
        timeOut = false;

        while (nextCommandType != CommandTypeEnum.COMPLETE && !timeOut){
            println(typeOfInputAsked.getMessage());

            Scanner scanner = new Scanner(System.in);

            typeOfInputAsked = syntaxController.validCommand(scanner.nextLine());
            nextCommandType = typeOfInputAsked.getCommandTypeEnum();
        }
    }


    public void yourTurn(int idMessage) {

        Scanner scanner = new Scanner(System.in);

        boolean okMessage = false;
        String message = null;

        while (!okMessage) {
            print(idMessage);

            message = scanner.nextLine();
            okMessage = true;

        }
    }

    public String getInput(String requestMessage) {

        Scanner scanner = new Scanner(System.in);

        boolean okMessage = false;
        String message = null;

        while (!okMessage) {
            print(requestMessage);

            message = scanner.nextLine();
            okMessage = true;
        }
        return message;
    }

    public void showMessage(int idMessage){
        println(idMessage);
    }

    public void showError(int idError){
        println(idError);
    }

    @Override
    public SyntaxController getSyntaxController() {
        return syntaxController;
    }
}
