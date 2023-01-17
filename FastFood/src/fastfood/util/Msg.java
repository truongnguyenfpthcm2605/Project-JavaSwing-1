
package fastfood.util;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.text.JTextComponent;

public class Msg {
    public static ImageIcon x(){
        return Ximage.getAppImagelcon("x.png");
    }
    
    public static ImageIcon v(){
        return Ximage.getAppImagelcon("v.png");
    }
    
    public static void setIconLabel(ImageIcon icon, JLabel... text) {
        for (JLabel t : text) {
            t.setIcon(icon);
        }
    }
    
    public static ImageIcon avt(String img, JLabel lbl) {
        return img != null ? Ximage.reSizeImgae2(Ximage.read(img), lbl)
                : Ximage.reSizeImgae2(Ximage.read("avt.jpg"), lbl);
    }
    
    
    public static void clearText(JTextComponent ...txt){
        for (JTextComponent jtext : txt) {
            jtext.setBackground(Color.WHITE);
            jtext.setText("");
        }
    }
    
    public static void clearLabel(JLabel ...lbl){
        for (JLabel jLabel : lbl) {
            jLabel.setText(" ");
        }
    }
}
