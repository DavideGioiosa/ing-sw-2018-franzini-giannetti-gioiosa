package it.polimi.se2018.model.restriction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestrictionManager {

    private static final HashMap<String, Restriction> restrictionHashMap = new HashMap<>();
    static{
        restrictionHashMap.put("Adjacent", new RestrictionAdjacent());
        restrictionHashMap.put("AdjacentColour", new RestrictionAdjacentColour());
        restrictionHashMap.put("AdjacentValue", new RestrictionAdjacentValue());
        restrictionHashMap.put("CellColour", new RestrictionCellColour());
        restrictionHashMap.put("CellValue", new RestrictionCellValue());
        restrictionHashMap.put("FirstDieOnBoarder", new RestrictionFirstDieOnBorder());
    }

    private static final List<Restriction> standardRestriction = new ArrayList<>();
    static{
        for (Map.Entry<String, Restriction> entry : restrictionHashMap.entrySet()) {
            standardRestriction.add(entry.getValue());
        }
    }

    public RestrictionManager(){
        //Doesn't need standard attributes
    }

    public static List<Restriction> getRestrictionWithoutAvoided(String avoidedRestriction) {

        List<Restriction> restrictionList = new ArrayList<>();
        for (Map.Entry<String, Restriction> entry : restrictionHashMap.entrySet()) {
            if(!entry.getKey().equals(avoidedRestriction)) restrictionList.add(entry.getValue());
        }
        return restrictionList;
    }

    public static List<Restriction> getStandardRestriction() {
        return standardRestriction;
    }

}
