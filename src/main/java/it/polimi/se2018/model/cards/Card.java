package it.polimi.se2018.model.cards;

/**
 * Card class indicates a generic Card with particular ID, name and description
 * @author Cristian Giannetti
 */

public class Card {

    /**
     * Card's identifier
     */
    private final int id;

    /**
     * Name of the Card
     */
    private final String name;

    /**
     * Description of the Card
     */
    private final String description;

    /**
     * Constructor needs to know all parameters to create a Card.
     * They cannot be modified during the game
     * @param id Identifier of Card
     * @param name Name of the Card
     * @param description Description of the Card
     */
    public Card(int id, String name, String description){
        if (id == 0) throw new IllegalArgumentException("ERROR: Invalid Card ID");
        if (name == null) throw new IllegalArgumentException("ERORR: Invalid Card Name");
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * Gets the identifier of the Card
     * @return Identifier of the Card
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the Card
     * @return Name of the Card
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the Description of the Card
     * @return Description of the Card
     */
    public String getDescription() {
        return description;
    }

}
