
package fastfood.ui;

import fastfood.entity.LikeCommentOne;
import fastfood.util.Msg;
import fastfood.util.Ximage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class UiComment {
    public JPanel pnlAll = new JPanel();
    public JPanel pnl = new JPanel(new BorderLayout());
    public JLabel lblUser = new JLabel();
    public JLabel lblImg = new JLabel();
    public JLabel lblFeedBack = new JLabel();
    public JCheckBox chkHeart = new JCheckBox();
    public JCheckBox chkDislike = new JCheckBox();
    
    private LikeCommentOne likeCommentOne;

    public UiComment(LikeCommentOne likeCommentOne) {
        this.likeCommentOne = likeCommentOne;
    }

    public LikeCommentOne getLikeCommentOne() {
        return likeCommentOne;
    }

    public void setLikeCommentOne(LikeCommentOne likeCommentOne) {
        this.likeCommentOne = likeCommentOne;
    }
    
    
    public void setSize(int heigh){
        pnl.setSize(new Dimension(100,heigh));
        pnl.setPreferredSize(new Dimension(100, heigh));
        lblImg.setSize(new Dimension(100, heigh));
        lblImg.setPreferredSize(new Dimension(100, heigh));
    }
    
    public JPanel pnlSouth;
    public void panel(){
        pnlAll.setLayout(new BoxLayout(pnlAll, BoxLayout.Y_AXIS));
        pnlAll.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        lblImg.setIcon(Msg.avt(likeCommentOne.getImg(), lblImg));
        lblImg.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pnl.add(lblImg,BorderLayout.WEST);
        
        JPanel pnlTT = new JPanel(new BorderLayout(0, 5));
        
        JPanel pnlTren = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        lblUser.setText(likeCommentOne.getUserComment());
        lblUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pnlTren.add(lbl(lblUser, 100));
        JLabel lblTacGia = new JLabel(likeCommentOne.getTacGia());
        lblTacGia.setForeground(Color.red);
        pnlTren.add(lbl(lblTacGia, 50));
        
        pnlTT.add(pnlTren,BorderLayout.NORTH);
        
        JLabel lbl = new JLabel(likeCommentOne.getContent());
        lbl.setVerticalAlignment(SwingConstants.TOP);
        pnlTT.add(lbl,BorderLayout.CENTER);
        
        pnlSouth = new JPanel(new FlowLayout(FlowLayout.LEFT,5,0));
        
        
        pnlTT.add(pnlSouth,BorderLayout.SOUTH);
        pnl.add(pnlTT,BorderLayout.CENTER);
        
    }
    
    private void icon(){
        chkHeart.setIcon(Ximage.getAppImagelcon("heartWhite16.png"));
        chkHeart.setSelectedIcon(Ximage.getAppImagelcon("heartRed16.png"));
        
        chkDislike.setIcon(Ximage.getAppImagelcon("dislikeWhite.png"));
        chkDislike.setSelectedIcon(Ximage.getAppImagelcon("dislikeRed.png"));
    }
    
    public void commentOne(){
        JLabel lbl = new JLabel(likeCommentOne.getDateComment());
        pnlSouth.add(lbl(lbl,80));
        
        lblFeedBack.setText("Feedback");
        lblFeedBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pnlSouth.add(lbl(lblFeedBack,200));
        
        chkHeart.setText(String.valueOf(likeCommentOne.getQuantityLike()));
        pnlSouth.add(chk(chkHeart, 80));
        
        pnlSouth.add(chk(chkDislike, 40));
        icon();
    }
    
    public JLabel lbl(JLabel lbl,int width){
        lbl.setPreferredSize(new Dimension(width, 17));
        return lbl;
    }
    
    public JCheckBox chk(JCheckBox chk, int width){
        chk.setPreferredSize(new Dimension(width, 17));
        return chk;
    }
    
    
}
