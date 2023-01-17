
package fastfood.ui;

import fastfood.entity.Bill;
import fastfood.util.FormatNumber;
import fastfood.util.Ximage;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UiTkBill {
    public JPanel pnl = new JPanel(new BorderLayout(10, 0));
    public JLabel lblImg = new JLabel();
    private Bill bill;

    
    public UiTkBill(Bill bill) {
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
        
        pnlCenter = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        pnlCenter.add(lbl("Name SP : "+bill.getName(), 150, heigh));
        pnlCenter.add(lbl("SL mua : "+bill.getQuantity(), 90, heigh));
        pnlCenter.add(lbl("Turnover : "+FormatNumber.withLargeIntegers(bill.getMoney(), 1), 200,heigh));
        pnlCenter.add(lbl("Tax : 8%",70,heigh));
        pnlCenter.add(lbl("WageRealistic : "+FormatNumber.withLargeIntegers(bill.getMoney()*0.92, 1), 250,heigh));
    }
    
    private JPanel pnlCenter;
    public void panel(){
        lblImg.setIcon(Ximage.reSizeImgae2(Ximage.read(bill.getImg()), lblImg));
        pnl.add(lblImg,BorderLayout.WEST);
        pnl.add(pnlCenter,BorderLayout.CENTER);
    }
    
        public JLabel lbl(String string, int width, int heigh){
        JLabel lbl = new JLabel(string,JLabel.LEFT);
        lbl.setPreferredSize(new Dimension(width, heigh));
        lbl.setVerticalAlignment(JLabel.CENTER);
        return lbl;
    }
}
