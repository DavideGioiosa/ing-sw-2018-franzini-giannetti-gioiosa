package it.polimi.se2018.model.cards.public_card;

/**
 * Public Objective Cards
 * @author Silvia Franzini
 */
public class PublicObjCard extends Card {
    private ScoreStrategy scoreStrategy;

    public PublicObjCard(int id, String name, String description,ScoreStrategy scoreStrategy){
        super(id,name,description);
        this.scoreStrategy=scoreStrategy;
    }

    /**
     * returns the scoring related to that specific Public Objective Card
     * @param schemaCard, window pattern card
     */
    public int scoreCalculation(SchemaCard schemaCard){
        return scoreStrategy.getScore(schemaCard);
    }
}
