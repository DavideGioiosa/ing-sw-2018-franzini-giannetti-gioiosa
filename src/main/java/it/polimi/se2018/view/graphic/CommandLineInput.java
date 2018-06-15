package it.polimi.se2018.view.graphic;

import it.polimi.se2018.view.InputStrategy;

import java.util.Scanner;

import static it.polimi.se2018.view.graphic.CommandLinePrint.print;
import static it.polimi.se2018.view.graphic.CommandLinePrint.println;


public class CommandLineInput implements InputStrategy {

    /**
     * Standard string for invalid input
     */
    private final String invalidInput = "Invalid input";

    /**
     * Get input from Command Line
     *
     * @param idMessage id message to show - needs to load from file
     * @return Input written by user
     */
    public String getInput(Integer idMessage) {

        Scanner scanner = new Scanner(System.in);

        boolean okMessage = false;
        String message = null;

        while (!okMessage) {
            print(idMessage.toString());

            message = scanner.nextLine();
            okMessage = true;
            println(invalidInput);
        }
        return message;
    }
}
