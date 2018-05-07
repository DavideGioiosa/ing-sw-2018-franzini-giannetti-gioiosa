package it.polimi.se2018.model.cards;

/**
 * Card class indicates a generic Card with particular ID, name and description
 */

public class Card {

    private final int id;
    private final String name;
    private final String description;

    public Card(int id, String name, String description){
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
