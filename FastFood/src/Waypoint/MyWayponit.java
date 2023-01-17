/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Waypoint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

/**
 *
 * @author truong
 */
public class MyWayponit  extends DefaultWaypoint{
    private String name;
    private JButton button;

    public MyWayponit() {
    }

    public MyWayponit(String name,EventWaypoint event,GeoPosition coord) {
        super(coord);
        this.name = name;
        initButton(event);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }
    
    private void initButton(EventWaypoint event){
        button = new buttonWaypoint();
        button.addActionListener((ActionEvent e) -> {
           event.selected(this);
        });
    }
    
    
    
}
