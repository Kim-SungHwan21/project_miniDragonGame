package com.nhnacademy.gamedomain;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Some javadoc.
 */

@SuppressWarnings("all")
public class StwoDamageCalculate {



    static boolean flag;


    /**
     * Some javadoc.
     */

    public void attack(DataOutputStream dataOutputStream) {


        Thread soldierAttack = new Thread(new StwoDamageCalculate.SoldierAttack(dataOutputStream));
        Thread monsterAttack = new Thread(new StwoDamageCalculate.MonsterAttack(dataOutputStream));
        soldierAttack.start();
        monsterAttack.start();




    }

    @SuppressWarnings("all")
    static class SoldierAttack extends Thread {
        int soldierPower;
        int orcHp = 40;
        Random random = new Random();
        DataOutputStream dataOutputStream;


        public SoldierAttack(DataOutputStream dataOutputStream) {
            this.dataOutputStream = dataOutputStream;
        }


        @Override
        public void run() {


            while (orcHp > 0) {
                if (flag) {
                    break;
                }
                soldierPower = random.nextInt(10) + 1;
                try {
                    dataOutputStream.writeUTF("Soldier(이)가 공격력 " + soldierPower + "으로 공격 했습니다.");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                orcHp -= soldierPower;
                if (orcHp < 0) {
                    try {
                        dataOutputStream.writeUTF("OrcHp가 0이되어 쓰러졌습니다.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        dataOutputStream.writeUTF("Orc를 물리쳤다.\nSoldier이(가) 레벨2로 상승했다."
                            + "\n용사 상태\n레벨: 2"
                            + "\n체력: 150\n공격력: 20\n-- 계속 진행하시려면 엔터를 입력해주세요. -- ");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    flag = true;
                    break;
                } else if (orcHp > 0) {
                    try {
                        dataOutputStream.writeUTF("OrcHp가 " + orcHp + "남았습니다.");
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
        int soldierHp = 100;
        int orcPower;
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

                orcPower = random.nextInt(6) + 1;
                try {
                    dataOutputStream.writeUTF("Orc이(가) 공격력 " + orcPower + "으로 공격 했습니다.");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                soldierHp -= orcPower;
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
