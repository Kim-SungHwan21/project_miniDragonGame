package com.nhnacademy.gamedomain;


import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Some javadoc.
 */

@SuppressWarnings("all")
public class SoneDamageCalculate {


    static int soldierPower;
    static boolean flag;


    /**
     * Some javadoc.
     */

    public  void attack(DataOutputStream dataOutputStream) {


        Thread soldierAttack = new Thread(new SoldierAttack(dataOutputStream));
        Thread monsterAttack = new Thread(new MonsterAttack(dataOutputStream));
        soldierAttack.start();
        monsterAttack.start();

    }

    @SuppressWarnings("all")
    static class SoldierAttack extends Thread {
        int slimeHp = 30;

        Random random = new Random();
        DataOutputStream dataOutputStream;


        public SoldierAttack(DataOutputStream dataOutputStream) {
            this.dataOutputStream = dataOutputStream;

        }


        @Override
        public void run() {


            while (slimeHp > 0) {
                if (flag) {
                    break;
                }
                soldierPower = random.nextInt(10) + 1;
                try {
                    dataOutputStream.writeUTF("Soldier(이)가 공격력 " + soldierPower + "으로 공격 했습니다.");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                slimeHp -= soldierPower;
                if (slimeHp < 0) {
                    try {
                        dataOutputStream.writeUTF("SlimeHp가 0이되어 쓰러졌습니다.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        dataOutputStream.writeUTF("Slime을 물리쳤다.\n-- 계속 진행하시려면 엔터를 입력해주세요. -- ");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    flag = true;
                    break;
                } else if (slimeHp > 0) {
                    try {
                        dataOutputStream.writeUTF("slimeHp가 " + slimeHp + "남았습니다.");
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


    static class MonsterAttack extends Thread {
        int soldierHp = 100;
        int slimePower;
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

                slimePower = random.nextInt(4) + 1;
                try {
                    dataOutputStream.writeUTF("Slime이(가) 공격력 " + slimePower + "으로 공격 했습니다.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                soldierHp -= slimePower;
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
















