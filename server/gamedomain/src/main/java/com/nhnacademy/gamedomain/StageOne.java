package com.nhnacademy.gamedomain;

import java.io.DataOutputStream;

/**
 * Some javadoc.
 */

public class StageOne {

    SoneDamageCalculate sdc = new SoneDamageCalculate();

    @Override
    public String toString() {
        return "야생의 슬라임이 나타났다.\n1. 공격\n2. 도망간다 (게임종료)\n";
    }

    /**
     * Some javadoc.
     */

    public String oneButton(DataOutputStream dataOutputStream) {
        sdc.attack(dataOutputStream);

        return null;
    }
}
