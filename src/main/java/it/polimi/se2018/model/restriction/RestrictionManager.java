package it.polimi.se2018.model.restriction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestrictionManager {

    private static final HashMap<String, Restriction> restrictionHashMap = new HashMap<>();
    static{
        restrictionHashMap.put("adjacent", new RestrictionAdjacent());
        restrictionHashMap.put("adjacentcolour", new RestrictionAdjacentColour());
        restrictionHashMap.put("adjacentvalue", new RestrictionAdjacentValue());
        restrictionHashMap.put("cellcolour", new RestrictionCellColour());
        restrictionHashMap.put("cellvalue", new RestrictionCellValue());
        restrictionHashMap.put("firstdieonboarder", new RestrictionFirstDieOnBorder());
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
