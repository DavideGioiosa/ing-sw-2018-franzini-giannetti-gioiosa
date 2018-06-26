package it.polimi.se2018.controller;

import java.io.Serializable;

public class OperationString implements Serializable {
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
