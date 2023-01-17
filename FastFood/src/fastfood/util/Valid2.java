/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fastfood.util;

import java.awt.Color;
import java.text.Normalizer;
import javax.swing.JLabel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Duy Cuong
 */
public class Valid2 {
    private static String loi;



    public static boolean sai(JTextComponent txt, JLabel lbl, String msg) {
        txt.setBackground(Color.YELLOW);
        lbl.setText(msg);
        loi += msg + "\n";
        return false;
    }

    public static boolean dung(JTextComponent txt, JLabel lbl) {
        txt.setBackground(Color.WHITE);
        lbl.setText(" ");
//        setLoi("");
        return true;
    }

    public static boolean isEmpty(JTextComponent txt, JLabel lbl, String name) {
        String x = txt.getText().trim();
        return x.isEmpty() ? sai(txt, lbl, name + " cann't be empty") : dung(txt, lbl);
    }

    public static boolean compare(JTextComponent txt, JLabel lbl, String msg, String value) {
        return txt.getText().equals(value) ? dung(txt, lbl) : sai(txt, lbl, msg);
    }

    private static boolean reMatch(JTextComponent txt, JLabel lbl, String name, String reString) {
        String x = txt.getText().trim();
        return x.matches(reString) ? dung(txt, lbl) : sai(txt, lbl, name + " malformed");
    }

    private static boolean length(JTextComponent txt, JLabel lbl,String name, int length){
        return txt.getText().length() < length ? sai(txt, lbl, name+" length has to bigger than "+length) : dung(txt, lbl);
    }
    public static boolean reMatchLower(JTextComponent txt, JLabel lbl, String name, String reString) {
        String x = Normalizer.normalize(txt.getText().trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "").toLowerCase().replaceAll("đ", "d");
        return x.matches(reString) ? dung(txt, lbl) : sai(txt, lbl, name + " malformed");
    }

    public static boolean number(JTextComponent txt, JLabel lbl, String name) {
        try {
            Double.parseDouble(txt.getText());
        } catch (NumberFormatException num) {
            return sai(txt, lbl, name + " has to be number");
        }
        return dung(txt, lbl);
    }

    public static boolean numberInteger(JTextComponent txt, JLabel lbl, String name) {
        try {
            Double.parseDouble(txt.getText());
            try {
                Integer.parseInt(txt.getText());
            } catch (NumberFormatException num) {
                return sai(txt, lbl, name + " has to be number integer");
            }
        } catch (NumberFormatException num) {
            return sai(txt, lbl, name + " has to be number");
        }
        return dung(txt, lbl);
    }

    
    public static boolean lower(JTextComponent txt, JLabel lbl, String name, Double so) {
        double x = Double.parseDouble(txt.getText().trim());
        return x < so ? dung(txt, lbl) : sai(txt, lbl, name + " has to lower than " + so);
    }

    public static boolean lowerEqual(JTextComponent txt, JLabel lbl, String name, Double so) {
        double x = Double.parseDouble(txt.getText().trim());
        return x <= so ? dung(txt, lbl) : sai(txt, lbl, name + " has to lower than " + so);
    }

    public static boolean bigger(JTextComponent txt, JLabel lbl, String name, Double so) {
        double x = Double.parseDouble(txt.getText().trim());
        return x > so ? dung(txt, lbl) : sai(txt, lbl, name + " has to bigger than " + so);
    }

    public static boolean biggerEqual(JTextComponent txt, JLabel lbl, String name, Double so) {
        double x = Double.parseDouble(txt.getText().trim());
        return x >= so ? dung(txt, lbl) : sai(txt, lbl, name + " has to bigger than " + so);
    }

    public static boolean multiReMatch(JTextComponent txt, JLabel lbl, String name, String re) {
        return isEmpty(txt, lbl, name) ? reMatch(txt, lbl, name, re) : false;
    }

    public static boolean multiNumber(JTextComponent txt, JLabel lbl, String name) {
        return isEmpty(txt, lbl, name) ? number(txt, lbl, name) : false;
    }

    public static boolean multiNumberInteger(JTextComponent txt, JLabel lbl, String name) {
        return isEmpty(txt, lbl, name) ? numberInteger(txt, lbl, name) : false;
    }

    public static boolean multiBiggerEqual(JTextComponent txt, JLabel lbl, String name, double number) {
        return multiNumber(txt, lbl, name) ? biggerEqual(txt, lbl, name, number) : false;
    }

    public static boolean multiLowerEqual(JTextComponent txt, JLabel lbl, String name, double number) {
        return multiNumber(txt, lbl, name) ? lowerEqual(txt, lbl, name, number) : false;
    }

    public static boolean multiBiggerAndLower(JTextComponent txt, JLabel lbl, String name, double bigger, double lower) {
        return multiBiggerEqual(txt, lbl, name, bigger) ? lowerEqual(txt, lbl, name, lower) : false;
    }

    public static boolean multiBiggerEqual(JTextComponent txt, JLabel lbl, String name, int number) {
        return multiNumberInteger(txt, lbl, name) ? biggerEqual(txt, lbl, name, (double) number) : false;
    }

    public static boolean multiBiggerAndLower(JTextComponent txt, JLabel lbl, String name, int bigger, int lower) {
        return multiBiggerEqual(txt, lbl, name, bigger) ? lowerEqual(txt, lbl, name, (double) lower) : false;
    }

    public static boolean multiLenght(JTextComponent txt, JLabel lbl, String name) {
        return isEmpty(txt, lbl, name) ? getLenght(txt, lbl) : sai(txt, lbl, name);
    }
    
    public static boolean multiLength(JTextComponent txt, JLabel lbl, String name, int length){
        return isEmpty(txt, lbl, name) ? length(txt, lbl, name, length) : false;
    }

    public static String reDate() {
        return "^([0-9]{4}[-/]?((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))|([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00)[-/]?02[-/]?29)$";
    }

    public static String rePhone() {
        return "^0[1-9]\\d{8}$";
    }

    public static String reEmail() {
        return "^\\w{3,}+@\\w{3,}+(\\.\\w{2,}+){1,2}$";
    }

    public static String reName() {
        return "^[0-9a-zA-Z ]{3,40}$";
    }

    public static boolean getLenght(JTextComponent txt, JLabel lbl) {
        return txt.getText().length() <= 50 ? dung(txt, lbl) : false;
    }
}
