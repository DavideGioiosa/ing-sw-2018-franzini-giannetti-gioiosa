package it.polimi.se2018.model;

import it.polimi.se2018.model.restriction.Restriction;

import java.util.List;

public interface DiceContainer {

    void pickDice(PlayerMove playerMove, List<Die> dieList);
    void exchangeDice(PlayerMove playerMove, List<Die> dieList);
    void leaveDice(PlayerMove playerMove, List<Die> dieList, List<Restriction> restrictionList);
    List<Die> getClonedDieList();

}
