package com.nhnacademy.gamedomain;

import java.io.DataOutputStream;

/**
 * Some javadoc.
 */

public class BossStage {

    BossDamageCalculate bdc = new BossDamageCalculate();

    @Override
    public String toString() {
        return "*보스* 드래곤이 나타났다.\n1. 공격\n2. 도망간다 (게임종료)\n";
    }

    /**
     * Some javadoc.
     */

    public String oneButton(DataOutputStream dataOutputStream) throws InterruptedException {
        bdc.attack(dataOutputStream);

        return null;
    }
}
