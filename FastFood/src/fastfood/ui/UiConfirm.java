/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fastfood.ui;

/**
 *
 * @author truong
 */


import fastfood.entity.UserStore;
import fastfood.util.Msg;
import fastfood.util.Ximage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UiConfirm {
    private UserStore usS;
    public JLabel lblImg = new JLabel();
    public JPanel pnlNhap = new JPanel(new GridLayout(1, 2, 10, 0));
    public JPanel pnl = new JPanel(new BorderLayout(5, 0));
    public JLabel lblOk = new JLabel("Chấp thuận");
    public JLabel lblKOk = new JLabel("K chấp thuận");
    
    
    public UiConfirm(UserStore usS) {
        this.usS = usS;
        lblOk.setIcon(Ximage.getAppImagelcon("minus.png"));
        lblKOk.setIcon(Ximage.getAppImagelcon("plus.png"));
    }

    public UserStore getUsS() {
        return usS;
    }

    public void setUsS(UserStore usS) {
        this.usS = usS;
    }
    
    
    public void setSize(int heigh){
        pnl.setSize(new Dimension(100,heigh));
        pnl.setPreferredSize(new Dimension(100, heigh));
        lblImg.setSize(new Dimension(100, heigh));
        lblImg.setPreferredSize(new Dimension(100, heigh));
    }
    
    public void panel(){
        lblImg.setIcon(Msg.avt(usS.getImg(), lblImg));
        pnl.add(lblImg,BorderLayout.WEST);
        
        JPanel pnlTT = new JPanel(new BorderLayout(0, 15));
        JPanel pnlTren = new JPanel(new FlowLayout(0, 0, 0));
        JLabel lbl = new JLabel("User : "+usS.getUser());
        pnlTren.add(lbl(lbl));
        lbl = new JLabel("Name : "+usS.getName());
        pnlTren.add(lbl(lbl));
        pnlTT.add(pnlTren, BorderLayout.NORTH);
        
        JLabel lblNote = new JLabel(usS.getNote());
        lblNote.setVerticalAlignment(JLabel.TOP);
        pnlTT.add(lblNote, BorderLayout.CENTER);
        
        pnl.add(pnlTT, BorderLayout.CENTER);
        
        pnlNhap.setPreferredSize(new Dimension(250, 0));
        lblOk.setIcon(Msg.v());
        pnlNhap.add(lblOk);
        lblKOk.setIcon(Msg.x());
        pnlNhap.add(lblKOk);
        pnl.add(pnlNhap, BorderLayout.EAST);
    }
    
    public JLabel lbl(JLabel lbl){
//        lbl.setSize(new Dimension(100, 50));
        lbl.setPreferredSize(new Dimension(150, 35));
        lbl.setVerticalAlignment(JLabel.CENTER);
        return lbl;
    }
}
