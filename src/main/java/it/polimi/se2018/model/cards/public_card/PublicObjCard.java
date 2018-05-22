package it.polimi.se2018.model.cards.public_card;

import it.polimi.se2018.model.cards.Card;
import it.polimi.se2018.model.cards.SchemaCard;

/**
 * Public Objective Cards
 * @author Silvia Franzini
 */
public class PublicObjCard extends Card {
    private ScoreStrategy scoreStrategy;

    /**
     * Builder method of Public Objective Card class
     * @param id identifier of the card
     * @param name name of the card
     * @param description description of the card
     * @param scoreStrategy used for Strategy Pattern
     */
    public PublicObjCard(int id, String name, String description,String scoreStrategy){
        super(id,name,description);
        this.scoreStrategy = setScoreStrategy(scoreStrategy);
        if(this.scoreStrategy == null){
            throw new NullPointerException("ERROR: ScoreStrategy not existing");
        }
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
        return scoreStrategy.getScore(schemaCard);
    }

    private ScoreStrategy setScoreStrategy(String strategy) {
        ScoreStrategy scoreStrategy = null;
        switch(strategy){
            case "DiffColoursRow":
                scoreStrategy = new DiffColoursRow();
                break;
            case "DiffColoursColumn":
                scoreStrategy = new DiffColoursColumn();
                break;
            case "DiffNumbersRow":
                scoreStrategy = new DiffNumbersRow();
                break;
            case "DiffNumbersColumn":
                scoreStrategy = new DiffNumbersColumn();
                break;
            case "SmallNumbers":
                scoreStrategy = new SmallNumbers();
                break;
            case "MiddleNumbers":
                scoreStrategy = new MiddleNumbers();
                break;
            case "BigNumbers":
                scoreStrategy = new BigNumbers();
                break;
            case "DiffNumbers":
                scoreStrategy = new DiffNumbers();
                break;
            case "ColouredDiagonal":
                scoreStrategy = new ColouredDiagonal();
                break;
            case "DiffColours":
                scoreStrategy = new DiffColours();
                break;
        }
        return scoreStrategy;
    }
}
