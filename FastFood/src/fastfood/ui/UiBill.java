
package fastfood.ui;

import fastfood.entity.Bill;
import fastfood.util.Ximage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UiBill {
    public JPanel pnl = new JPanel(new BorderLayout(20, 0));
    public JLabel lblImg = new JLabel();
    public JLabel lblUser;
    public JLabel lblCart;
    public JLabel lblUpdate;
    private JPanel pnlAo;
    private Bill bill;

    public UiBill(Bill bill) {
        this.bill = bill;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
    
    public void setHeighPanel(int heigh){
        pnl.setSize(new Dimension(100,heigh));
        pnl.setPreferredSize(new Dimension(100, heigh));
        lblImg.setSize(new Dimension(100, heigh));
        lblImg.setPreferredSize(new Dimension(100, heigh));
    }
    
    public void panel(){
        lblImg.setIcon(Ximage.reSizeImgae2(Ximage.read(bill.getImg()), lblImg));
        pnl.add(lblImg,BorderLayout.WEST);
        JPanel pnlCenter = new JPanel(new GridLayout(2, 1, 0, 0));
        
        JPanel pnlTren = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlTren.add(lbl("Name:"+bill.getName()));
        pnlTren.add(lbl("Sl mua :"+bill.getQuantity()));
        pnlTren.add(lbl("Price :"+bill.getMoney()));
        pnlTren.add(lbl("Thành tiền :"+(bill.getQuantity()*bill.getMoney())));
        
        pnlCenter.add(pnlTren);
        
        pnlAo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlAo.add(lbl("Ngày mua:"+bill.getDatePurchase()));
        
        pnlCenter.add(pnlAo);
        
        pnl.add(pnlCenter,BorderLayout.CENTER);
    }
    
    public void user(){
        lblCart = new JLabel("Add Cart");
        lblCart.setIcon(Ximage.getAppImagelcon("cart.png"));
        lblCart.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblCart.setPreferredSize(new Dimension(150,100));
        lblCart.setHorizontalAlignment(JLabel.LEFT);
        pnl.add(lblCart,BorderLayout.EAST);
    }
    
    public void store(){
        lblUpdate = new JLabel("Update product");
        lblUpdate.setPreferredSize(new Dimension(150,100));
        lblUpdate.setHorizontalAlignment(JLabel.LEFT);
        lblUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pnl.add(lblUpdate,BorderLayout.EAST);
     
        lblUser = new JLabel(bill.getUser());
        lblUser.setForeground(Color.red);
        lblUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pnlAo.add(lblUser);
    }
    
    
    private JLabel lbl(String information){
        JLabel lbl = new JLabel(information);
        lbl.setPreferredSize(new Dimension(140, 45));
        lbl.setVerticalAlignment(JLabel.CENTER);
        lbl.setHorizontalAlignment(JLabel.LEFT);
        return lbl;
    }
    
}
