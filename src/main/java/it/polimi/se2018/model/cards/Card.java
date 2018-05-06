package it.polimi.se2018.model.cards;

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
