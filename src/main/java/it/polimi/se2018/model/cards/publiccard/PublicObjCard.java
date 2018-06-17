package it.polimi.se2018.model.cards.publiccard;

import it.polimi.se2018.model.cards.Card;
import it.polimi.se2018.model.cards.SchemaCard;

import java.io.Serializable;

/**
 * Public Objective Cards
 * @author Silvia Franzini
 */
public class PublicObjCard extends Card implements Serializable {
    /**
     * Scoring method
     */
    private transient ScoreStrategy scoreStrategy;
    /**
     * Bonus points for each satisfied requirement
     */
    private int bonus;

    /**
     * Builder method of Public Objective Card class
     * @param id identifier of the card
     * @param name name of the card
     * @param description description of the card
     * @param bonus number of points for each completed objective
     * @param scoreStrategy used for Strategy Pattern
     */
    public PublicObjCard(int id, String name, String description, int bonus, String scoreStrategy){
        super(id,name,description);
        setScoreStrategy(scoreStrategy);
        this.bonus = bonus;
        if(this.scoreStrategy == null){
            throw new NullPointerException("ERROR: ScoreStrategy not existing");
        }
    }

    /**
     * Copy Constructor
     * @param publicObjCard Public Objective Card to be cloned
     */
    public PublicObjCard(PublicObjCard publicObjCard){
        super(publicObjCard);
        this.bonus = publicObjCard.bonus;
        this.scoreStrategy = publicObjCard.scoreStrategy;
    }

    /**
     * returns the scoring related to that specific Public Objective Card
     * @param schemaCard window pattern card
     * @return the score got with that Public Objective Card
     */
    public int scoreCalculation(SchemaCard schemaCard){
        if(schemaCard == null){
            throw new NullPointerException("ERROR: Schema Card not existing");
        }
        return this.bonus * scoreStrategy.getScore(schemaCard);
    }

    /**
     * Setter method for Scoring strategy
     * @param strategy String that describes the type of Scoring strategy
     */
    private void setScoreStrategy(String strategy) {
        switch(strategy){
            case "DiffColoursRow":
                this.scoreStrategy = new DiffColoursRow();
                break;
            case "DiffColoursColumn":
                this.scoreStrategy = new DiffColoursColumn();
                break;
            case "DiffNumbersRow":
                this.scoreStrategy = new DiffNumbersRow();
                break;
            case "DiffNumbersColumn":
                this.scoreStrategy = new DiffNumbersColumn();
                break;
            case "SmallNumbers":
                this.scoreStrategy = new SmallNumbers();
                break;
            case "MiddleNumbers":
                this.scoreStrategy = new MiddleNumbers();
                break;
            case "BigNumbers":
                this.scoreStrategy = new BigNumbers();
                break;
            case "DiffNumbers":
                this.scoreStrategy = new DiffNumbers();
                break;
            case "ColouredDiagonal":
                this.scoreStrategy = new ColouredDiagonal();
                break;
            case "DiffColours":
                this.scoreStrategy = new DiffColours();
                break;
            default: this.scoreStrategy = null;
        }
    }

    /**
     * Getter method for bonus points
     * @return bonus points of the card
     */
    public int getBonus() {
        return bonus;
    }
}
