package it.polimi.se2018.controller;

public class OperationString {
    private String operation;
    private String diceContainer;

    public OperationString(String operation, String diceContainer){
        this.operation = operation;
        this.diceContainer = diceContainer;
    }

    public String getDiceContainer() {
        return diceContainer;
    }

    public String getOperation() {
        return operation;
    }

}
