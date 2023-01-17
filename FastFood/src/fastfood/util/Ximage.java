    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fastfood.util;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author truong
 */
public class Ximage {

    public static ImageIcon reSizeImgae(String path, JLabel lblImage) {
        ImageIcon myImage = Ximage.getAppImagelcon(path);
        Image img = myImage.getImage();
        Image newimg = img.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newimg);
        return image;
    }

    public static ImageIcon getAppImagelcon(String path) {
        URL url = Ximage.class.getResource("/fastfood.img/"+path);
        return new ImageIcon(url);
    }

    public static ImageIcon reSizeImgae2(String path, JLabel lblImage) {
        ImageIcon myImage = new ImageIcon(path);
        Image img = myImage.getImage();
        Image newimg = img.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newimg);
        return image;
    }
    public static String read(String fileName){
        File path = new File("resource", fileName);
        return path.getAbsolutePath();
    }
    
    public static void save(File src){
        File dst = new File("resource", src.getName());
        if(!dst.getParentFile().exists()){
            dst.getParentFile().mkdirs();
        }
        
        try{
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
   

     
}
