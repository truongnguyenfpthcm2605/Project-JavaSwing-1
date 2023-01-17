
package fastfood.ui;

import fastfood.entity.Cart;
import fastfood.util.Ximage;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class UiCart {
    public JLabel lblSL = new JLabel();
    public JLabel money = new JLabel();
    public JPanel pnl = new JPanel(new BorderLayout(15, 0));
    public JCheckBox chk = new JCheckBox();
    public JLabel lblImg = new JLabel();
    public JButton btnIncrease = new JButton(Ximage.getAppImagelcon("plus.png"));
    public JButton btnDecrease = new JButton(Ximage.getAppImagelcon("minus.png"));
    public JLabel lblTT = new JLabel("Thanh toán");
    public JLabel lblDelete = new JLabel("Xóa");
    private Cart c;

    public Cart getC() {
        return c;
    }

    public void setC(Cart c) {
        this.c = c;
    }
    

    public UiCart(Cart c) {
        this.c = c;
        lblSL.setText(String.valueOf(c.getQuantityCart()));
        money.setText(String.valueOf(c.getPayMoney()));
    }
    

    
    
    public void increase(){
        c.increaseCart();
        lblSL.setText(String.valueOf(c.getQuantityCart()+""));
        money.setText(String.valueOf(c.getPayMoney()));
    }
    public void decrease(){
        c.decreaseCart();
        lblSL.setText(String.valueOf(c.getQuantityCart()+""));
        money.setText(String.valueOf(c.getPayMoney()));
    }
    
    public void setHeighPanel(int heigh){
        pnl.setSize(new Dimension(100,heigh));
        pnl.setPreferredSize(new Dimension(100, heigh));
        lblImg.setSize(new Dimension(100, heigh));
        lblImg.setPreferredSize(new Dimension(100, heigh));
    }
    
    public void panel(){
        JPanel pnlTrai = new JPanel(new BorderLayout(5, 0));
        pnlTrai.add(chk,BorderLayout.WEST);
        lblImg.setIcon(Ximage.reSizeImgae2(Ximage.read(c.getImg()), lblImg));
        pnlTrai.add(lblImg);
        pnl.add(pnlTrai,BorderLayout.WEST);
        
        JPanel pnlPhai = new JPanel(new GridLayout(2, 1, 10, 0));
        JPanel pnlTren = new JPanel(new FlowLayout(FlowLayout.LEFT,20,0));
        pnlTren.add(setPrefer(new JLabel(c.getName()), 90));
        pnlTren.add(xoaVienBtn(btnDecrease, pnlTren));
        pnlTren.add(setPrefer(lblSL, 20));
        pnlTren.add(xoaVienBtn(btnIncrease, pnlTren));
        pnlTren.add(new JLabel(" * "));
        pnlTren.add(setPrefer(new JLabel(Double.parseDouble(String.format("%.3f", c.getMoney()))+""), 70));
        pnlTren.add(new JLabel(" = "));
        pnlTren.add(setPrefer(money, 70));
        pnlPhai.add(pnlTren);
        
        JPanel pnlAo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel pnlDuoi = new JPanel(new GridLayout(1, 2, 20, 0));
        pnlDuoi.setPreferredSize(new Dimension(200, 100));
        pnlDuoi.add(xoaVienLabel(lblTT, pnlDuoi));
        pnlDuoi.add(xoaVienLabel(lblDelete, pnlDuoi));
        pnlAo.add(pnlDuoi);
        
        pnlPhai.add(pnlAo);
        pnl.add(pnlPhai,BorderLayout.CENTER);
    }
    
    private JLabel setPrefer(JLabel lbl, int width){
        lbl.setPreferredSize(new Dimension(width,80));
        return lbl;
    }
    
    private JLabel xoaVienLabel(JLabel lbl,JPanel pnl){
        lbl.setBackground(pnl.getBackground());
        lbl.setVerticalAlignment(SwingConstants.TOP);
        return lbl;
    }
    
    private JButton xoaVienBtn(JButton btn, JPanel pnl){
        btn.setBorder(null);
        btn.setFocusable(false);
        btn.setBackground(pnl.getBackground());
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        return btn;
    }
    
    
}
