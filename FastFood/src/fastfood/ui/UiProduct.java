
package fastfood.ui;

import fastfood.entity.Product;
import fastfood.util.Ximage;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class UiProduct {
    public JPanel pnl = new JPanel(new BorderLayout(0, 5));
    public JLabel lblImg = new JLabel();
    public JPanel pnlName = new JPanel(new GridLayout(0, 1, 0, 5));
    public JPanel pnlAo;
    public JLayeredPane layer = new JLayeredPane();

    private Product product;

    public UiProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    

    
    
    public void setSizePanel(int width, int heigh){
        pnl.setSize(new Dimension(width,heigh));
        pnl.setPreferredSize(new Dimension(width, heigh));
    }
    
    public void setSizeImage(int width, int heigh){
        lblImg.setSize(new Dimension(width, heigh));
        lblImg.setPreferredSize(new Dimension(width, heigh));
    }
    public void panel(){
        layer.setLayout(new CardLayout());
        this.pnl.setToolTipText(String.valueOf(product.getIdProduct()));
        lblImg.setIcon(Ximage.reSizeImgae2(Ximage.read(product.getImg()), lblImg));
        pnl.add(lblImg,BorderLayout.NORTH);
        layer.add(pnlName);
        pnl.add(layer, BorderLayout.CENTER);
        pnlName.add(new JLabel("Name  : "+product.getName()));
        pnlName.add(new JLabel("SL    : "+String.valueOf(product.getQuantity())));
        pnlName.add(new JLabel("Price : "+String.valueOf(product.getMoney())));
    }
    
    public JLabel lblCart;
    public JCheckBox chkLike;
    public JLabel lblLuotLike;
    public void cart(){
        JPanel pnlca = new JPanel(new BorderLayout(10, 0));
        
        JPanel pnlLike = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 0));
        chkLike = new JCheckBox();
        chkLike.setSelected(product.isLike());
        chkLike.setIcon(Ximage.getAppImagelcon("heartWhite.png"));
        chkLike.setSelectedIcon(Ximage.getAppImagelcon("heartRed.png"));
        pnlLike.add(chkLike);
        lblLuotLike = new JLabel(slLike());
        pnlLike.add(lblLuotLike);
        pnlca.add(pnlLike,BorderLayout.WEST);
        this.pnl.add(pnlca,BorderLayout.SOUTH);
        lblCart = new JLabel(Ximage.getAppImagelcon("cart.png"));
        pnlca.add(lblCart,BorderLayout.EAST);
        pnl.add(pnlca,BorderLayout.SOUTH);
    }
    
    public void increaseLike(){
        product.increaseLike();
        lblLuotLike.setText(slLike());
    }
    
    public void decreaseLike(){
        product.decreaseLike();
        lblLuotLike.setText(slLike());
    }
    
    
    
    public String slLike(){
        return String.valueOf(product.getSlLike());
    }
    
    public void moveLblImg(String img){
        lblImg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblImg.setIcon(Ximage.reSizeImgae2(Ximage.read(img), lblImg));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblImg.setIcon(Ximage.reSizeImgae2(Ximage.read(product.getImg()), lblImg));
            }
        });
    }
    
    public JButton btnUpdate,btnDelete;
    public void store(){
        pnlAo = new JPanel(new GridLayout(1, 2, 20, 0));
        pnlAo.add(btnUpdate);
        pnlAo.add(btnDelete);
        pnlName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                layer(pnlAo);
            }
        });
        mouseBtn(btnUpdate);
        mouseBtn(btnDelete);
        mouseBtn(pnlAo);
    }
    
    public void mouseBtn(Component comp){
        comp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                layer(pnlName);
            }
            
        });
    }
    
    public void layer(JPanel pnl){
        layer.removeAll();
        layer.add(pnl);
        layer.repaint();
        layer.revalidate();
    }
}
