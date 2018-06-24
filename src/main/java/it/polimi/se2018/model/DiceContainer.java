package it.polimi.se2018.model;

import java.util.List;

public interface DiceContainer {

    public List<Die> pickDice(PlayerMove playerMove);
    public List<Die> exchangeDice(PlayerMove playerMove, List<Die> dieList);
    public void leaveDice(PlayerMove playerMove, List<Die> dieList);

}
