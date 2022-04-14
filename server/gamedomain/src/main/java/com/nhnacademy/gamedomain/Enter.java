package com.nhnacademy.gamedomain;


import java.util.Scanner;
/**
 * Some javadoc.
 */

public class Enter implements Game {

    Scanner in = new Scanner(System.in);
    String select;



    @Override
    public String toString() {
        return
                "계속 진행 하려면 엔터를 입력해주세요.";
    }

    @Override
    public String oneButton() {
        select = in.nextLine();
        return null;
    }

}
