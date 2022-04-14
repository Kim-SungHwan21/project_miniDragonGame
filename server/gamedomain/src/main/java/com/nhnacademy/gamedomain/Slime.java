package com.nhnacademy.gamedomain;

/**
 * Some javadoc.
 */

public class Slime implements Game {
    String name = "슬라임";
    int lv = 1;
    int hp = 30;
    int power = 4;

    @Override
    public String toString() {
        return name + " 상태" + "\n"
            + "레벨: " + lv + "\n"
            + "체력: " + hp + "\n"
            + "공격력: " + power + "\n";
    }

    @Override
    public String oneButton() {

        return null;
    }
}
