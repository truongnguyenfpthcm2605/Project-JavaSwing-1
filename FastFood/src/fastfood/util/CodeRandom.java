/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fastfood.util;


import java.util.Random;


/**
 *
 * @author truong
 */
public class CodeRandom {


    public static String getranDomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int ranCode = random.nextInt(10);
            str.append(ranCode);
        }
        return str.toString().trim();
    }
}
