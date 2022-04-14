package com.nhnacademy.gamedomain;

import java.io.DataOutputStream;

/**
 * Some javadoc.
 */

public class StageTwo {

    StwoDamageCalculate stdc = new StwoDamageCalculate();

    @Override
    public String toString() {
        return "야생의 오크가 나타났다.\n1. 공격\n2. 도망간다 (게임종료)\n";
    }

    /**
     * Some javadoc.
     */

    public String oneButton(DataOutputStream dataOutputStream) {
        stdc.attack(dataOutputStream);

        return null;

    }
}