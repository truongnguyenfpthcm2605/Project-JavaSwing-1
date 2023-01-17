/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Waypoint;

import fastfood.util.Ximage;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author truong
 */
public class buttonWaypoint  extends JButton{

    public buttonWaypoint() {
        setContentAreaFilled(false);
        setIcon(Ximage.getAppImagelcon("map.png"));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setSize(new Dimension(24,24));
    }
    
}
