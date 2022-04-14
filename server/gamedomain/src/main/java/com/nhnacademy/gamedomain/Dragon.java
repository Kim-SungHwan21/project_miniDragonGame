package com.nhnacademy.gamedomain;

/**
 * Some javadoc.
 */

public class Dragon implements Game {
    String name = "드래곤";
    int lv = 3;
    int hp = 100;
    int power = 10;


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
