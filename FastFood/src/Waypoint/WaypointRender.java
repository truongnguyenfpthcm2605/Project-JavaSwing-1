/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Waypoint;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import javax.swing.JButton;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.WaypointPainter;

/**
 *
 * @author truong
 */
public class WaypointRender extends  WaypointPainter<MyWayponit>{
    @Override
    protected void doPaint(Graphics2D g,JXMapViewer map, int witch, int height){
        for (MyWayponit wb : getWaypoints()) {
            Point2D  p = map.getTileFactory().geoToPixel(wb.getPosition(), map.getZoom());
            Rectangle rec = map.getViewportBounds();
            int x = (int)(p.getX()-rec.getX());
            int y = (int)(p.getY()-rec.getY());
            JButton  cmd =wb.getButton();
            cmd.setLocation(x -cmd.getWidth() / 2, y  - cmd.getHeight());
        }
    }
}
