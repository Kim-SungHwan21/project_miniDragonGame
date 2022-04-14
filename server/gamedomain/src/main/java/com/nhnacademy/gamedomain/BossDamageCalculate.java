package com.nhnacademy.gamedomain;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Some javadoc.
 */

@SuppressWarnings("all")
public class BossDamageCalculate {

    static boolean flag;


    /**
     * Some javadoc.
     */

    public void attack(DataOutputStream dataOutputStream) throws InterruptedException {


        Thread soldierAttack = new Thread(new BossDamageCalculate.SoldierAttack(dataOutputStream));
        Thread monsterAttack = new Thread(new BossDamageCalculate.MonsterAttack(dataOutputStream));
        soldierAttack.start();
        monsterAttack.start();
        soldierAttack.join();
        monsterAttack.join();




    }

    @SuppressWarnings("all")
    static class SoldierAttack extends Thread {
        int soldierPower;
        int dragonHp = 100;
        Random random = new Random();
        DataOutputStream dataOutputStream;


        public SoldierAttack(DataOutputStream dataOutputStream) {
            this.dataOutputStream = dataOutputStream;
        }


        @Override
        public void run() {

            while (dragonHp > 0) {
                if (flag) {
                    break;
                }
                soldierPower = random.nextInt(20) + 1;
                try {
                    dataOutputStream.writeUTF("Soldier(이)가 공격력 " + soldierPower + "으로 공격 했습니다.");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                dragonHp -= soldierPower;
                if (dragonHp < 0) {
                    try {
                        dataOutputStream.writeUTF("DragonHp가 0이되어 쓰러졌습니다.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        dataOutputStream.writeUTF("Dragon을 물리쳤다.\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    flag = true;
                    break;
                } else if (dragonHp > 0) {
                    try {
                        dataOutputStream.writeUTF("DragonHp가 " + dragonHp + "남았습니다.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }

            }
        }




    }

    @SuppressWarnings("all")
    static class MonsterAttack extends Thread {
        int soldierHp = 150;
        int dragonPower;
        Random random = new Random();
        DataOutputStream dataOutputStream;

        public MonsterAttack(DataOutputStream dataOutputStream) {
            this.dataOutputStream = dataOutputStream;
        }


        @Override
        public void run() {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            while (soldierHp > 0) {
                if (flag) {
                    break;
                }

                dragonPower = random.nextInt(10) + 1;
                if (dragonPower == 10) {
                    dragonPower = 15;
                    try {
                        dataOutputStream.writeUTF("Dragon이(가) 특수공격 브레스를 사용했습니다.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                try {
                    dataOutputStream.writeUTF("Dragon이(가) 공격력 " + dragonPower + "으로 공격 했습니다.");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                soldierHp -= dragonPower;
                if (soldierHp < 0) {
                    try {
                        dataOutputStream.writeUTF("SoldierHp가 0이되어 쓰러졌습니다.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        dataOutputStream.writeUTF("Soldier이(가) 졌습니다.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    flag = true;
                    break;
                } else if (soldierHp > 0) {
                    try {
                        dataOutputStream.writeUTF("SoldierHp가 " + soldierHp + "남았습니다.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }

        }
    }
}
