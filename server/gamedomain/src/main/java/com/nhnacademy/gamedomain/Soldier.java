package com.nhnacademy.gamedomain;

/**
 * Some javadoc.
 */

public class Soldier implements Game {
    String name = "용사";
    int soldierLv = 1;
    int soldierHp = 100;
    int soldierPower = 10;


    @Override
    public String toString() {
        return name + " 상태" + "\n"
            + "아이디: share"  + "\n"
            + "레벨: " + soldierLv + "\n"
            + "체력: " + soldierHp + "\n"
            + "공격력: " + soldierPower + "\n";
    }

    @Override
    public String oneButton() {

        return null;
    }
}
