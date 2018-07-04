package it.polimi.se2018.model;

import it.polimi.se2018.model.restriction.Restriction;

import java.util.List;

public interface DiceContainer {

    boolean pickDice(PlayerMove playerMove, List<Die> dieList, int min, int max);
    boolean exchangeDice(PlayerMove playerMove, List<Die> dieList);
    boolean leaveDice(PlayerMove playerMove, List<Die> dieList, List<Restriction> restrictionList);
    List<Die> getClonedDieList();

}
