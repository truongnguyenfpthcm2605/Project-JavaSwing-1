/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fastfood.util;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author truong
 */
public class Message {

    public static int getconfirm(Component com, String content, String title) {
        return JOptionPane.showConfirmDialog(com, content, title, JOptionPane.YES_NO_OPTION);
    }

    public static void getNotify(Component com, String content, String title) {
        JOptionPane.showMessageDialog(com, content, title, JOptionPane.DEFAULT_OPTION);
    }

    public static String getprompt(Component com, String content, String title) {
        return JOptionPane.showInputDialog(com, content, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static int getOption(Component com, String content, String []option){
        return JOptionPane.showOptionDialog(com, content, 
                    "Option Dialog box", 0, JOptionPane.QUESTION_MESSAGE, 
                    null, option, null);
    }
}
