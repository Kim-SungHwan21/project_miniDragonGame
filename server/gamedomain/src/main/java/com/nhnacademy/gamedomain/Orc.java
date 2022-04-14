package com.nhnacademy.gamedomain;

/**
 * Some javadoc.
 */

public class Orc implements Game {
    String name = "오크";
    int lv = 2;
    int hp = 40;
    int power = 6;

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
