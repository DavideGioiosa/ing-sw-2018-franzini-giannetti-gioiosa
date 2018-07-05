package it.polimi.se2018.model.restriction;

import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.cards.SchemaCard;

public interface Restriction {

    int checkRestriction(SchemaCard schemaCard, Die die, Position position);

}
