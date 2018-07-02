package it.polimi.se2018.view.graphic;

import it.polimi.se2018.model.CommandTypeEnum;

public class TypeOfInputAsked {

    private CommandTypeEnum commandTypeEnum;

    private int error;

    private InputFormatEnum inputFormat;

    private int message;

    public TypeOfInputAsked(CommandTypeEnum commandTypeEnum, InputFormatEnum inputFormat, int error, int message){
        this.commandTypeEnum = commandTypeEnum;
        this.inputFormat = inputFormat;
        this.error = error;
        this.message = message;
    }

    public CommandTypeEnum getCommandTypeEnum() {
        return commandTypeEnum;
    }

    public int getError() {
        return error;
    }

    public int getMessage() {
        return message;
    }

    public InputFormatEnum getInputFormat() {
        return inputFormat;
    }
}
