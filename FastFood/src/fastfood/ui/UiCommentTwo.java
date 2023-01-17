/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package fastfood.ui;

import fastfood.dao.CommentTwoDAO;
import fastfood.dao.LikeCommentTwoDAO;
import fastfood.entity.CommentOne;
import fastfood.entity.CommentTwo;
import fastfood.entity.LikeCommentOne;
import fastfood.entity.LikeCommentTwo;
import fastfood.util.Authention;
import fastfood.util.ListLikeComment;
import fastfood.util.Message;
import fastfood.view.ApplicationFastFood;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Duy Cuong
 */
public class UiCommentTwo extends javax.swing.JDialog {

    UiComment uc;
    CommentTwoDAO commentTwoDAO = new CommentTwoDAO();
    String tacGia;
    LikeCommentTwoDAO likecommentTwoDAO = new LikeCommentTwoDAO();
    Map<Integer, Boolean[]> hashLike = ListLikeComment.put();

    public UiCommentTwo(java.awt.Frame parent, boolean modal, UiComment uc, String tacGia) {
        super(parent, modal);
        initComponents();
        this.uc = uc;
        this.tacGia = tacGia;
        fillCommentOne(uc.getLikeCommentOne().getiDCommentOne());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Comment :");

        txt.setColumns(20);
        txt.setRows(5);
        jScrollPane1.setViewportView(txt);

        jButton1.setText("Submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridLayout(0, 1, 10, 14));
        jScrollPane2.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jButton1)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (Authention.authention != null) {
            commentTwoDAO.insert(new CommentTwo(uc.getLikeCommentOne().getiDCommentOne(), Authention.authention.getUser(), txt.getText(), tacGia));
            uc.getLikeCommentOne().increaseQuantityComment();
            LikeCommentTwo like = likecommentTwoDAO.selectOne(Authention.authention.getUser(), this.uc.getLikeCommentOne().getiDCommentOne());
            jPanel1.add(uiCommnent(like),0);
//            uc.lblFeedBack.setText(uc.getLikeCommentOne().getQuantityComment());
        } else {
            Message.getNotify(this, "You have to login new can comment this product", "Message");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void fillCommentOne(int idProduct) {
        jPanel1.removeAll();
        String user = Authention.authention == null ? "" : Authention.authention.getUser();
        List<LikeCommentTwo> list = likecommentTwoDAO.selectALL(user, idProduct);
        for (LikeCommentTwo like : list) {
            jPanel1.add(uiCommnent(like));
        }

        int sl = 3 - list.size();
        for (int i = 0; i < sl; i++) {
            UiComment2 uc = new UiComment2(new LikeCommentTwo());
            jPanel1.add(uc.pnl);
        }
    }

    private JPanel uiCommnent(LikeCommentTwo like) {
        UiComment2 uc = new UiComment2(like);
        uc.setSize(70);
        uc.panel();
        uc.commentOne();
        chkCommentOneSetSelected(uc);
//            lblCommentOneImg(uc);
        pnlCommentTwoAction(uc);
        chkCommentOneAction(uc);
        return uc.pnl;
    }

    public void chkCommentOneAction(UiComment2 uc) {
        uc.chkHeart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Authention.authention == null) {
                    uc.chkDislike.setSelected(false);
                    uc.chkHeart.setSelected(false);
                    Message.getNotify(null, "You cannot like this product because you are not yet login!", "Message");
                } else {
                    if (uc.chkHeart.isSelected()) {
                        uc.getLikeCommentTwo().increaseQuantityLike();
                        uc.getLikeCommentTwo().setLike(1);
                        likecommentTwoDAO.insert_update(Authention.authention.getUser(), uc.getLikeCommentTwo().getiDCommentTwo(), uc.getLikeCommentTwo().getLike());
                        uc.chkHeart.setText(String.valueOf(uc.getLikeCommentTwo().getQuantityLike()));
                        uc.chkDislike.setSelected(false);
                    } else {
                        uc.getLikeCommentTwo().decreaseQuantityLike();
                        uc.getLikeCommentTwo().setLike(0);
                        likecommentTwoDAO.insert_update(Authention.authention.getUser(), uc.getLikeCommentTwo().getiDCommentTwo(), uc.getLikeCommentTwo().getLike());
                        uc.chkHeart.setText(String.valueOf(uc.getLikeCommentTwo().getQuantityLike()));
                    }
                }
            }
        });

        uc.chkDislike.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Authention.authention == null) {
                    uc.chkDislike.setSelected(false);
                    uc.chkHeart.setSelected(false);
                    Message.getNotify(null, "You cannot like this product because you are not yet login!", "Message");
                } else {
                    if (uc.chkDislike.isSelected()) {
                        if (uc.chkHeart.isSelected()) {
                            uc.getLikeCommentTwo().decreaseQuantityLike();
                            uc.chkHeart.setText(String.valueOf(uc.getLikeCommentTwo().getQuantityLike()));
                            uc.chkHeart.setSelected(false);
                        }
                        uc.getLikeCommentTwo().setLike(2);
                        likecommentTwoDAO.insert_update(Authention.authention.getUser(), uc.getLikeCommentTwo().getiDCommentTwo(), uc.getLikeCommentTwo().getLike());
                    } else {
                        uc.getLikeCommentTwo().setLike(0);
                        likecommentTwoDAO.insert_update(Authention.authention.getUser(), uc.getLikeCommentTwo().getiDCommentTwo(), uc.getLikeCommentTwo().getLike());
                    }
                }
            }
        });
    }

    public void chkCommentOneSetSelected(UiComment2 uc) {
        boolean heart = hashLike.get(uc.getLikeCommentTwo().getLike())[0];
        boolean dislike = hashLike.get(uc.getLikeCommentTwo().getLike())[1];
        uc.chkHeart.setSelected(heart);
        uc.chkDislike.setSelected(dislike);
    }

    private void pnlCommentTwoAction(UiComment2 uc){
        uc.pnl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(Authention.authention != null){
                    if(e.getClickCount() == 2){
                    /*Tac gia*/
                    if(!tacGia.equals("")){
                        if(Message.getconfirm(null, "You want to delete this comment", "Message") == JOptionPane.YES_OPTION){
                            commentTwoDAO.delete(uc.getLikeCommentTwo().getiDCommentTwo());
                            jPanel1.remove(uc.pnl);
                        }
                        return;
                    }
                    
                    
                    if(uc.getLikeCommentTwo().getUserComment().equals(Authention.authention.getUser())){
                        if(Message.getconfirm(null, "You want delete comment your", "Message") == JOptionPane.YES_OPTION){
                            commentTwoDAO.delete(uc.getLikeCommentTwo().getiDCommentTwo());
                            jPanel1.remove(uc.pnl);
                        }
                    }
                }
                }
            }
            
        });
    }
    
    private void lblCommentOneImg(UiComment2 uc) {
        uc.lblImg.addMouseListener(commentOneViewUser(uc));
        uc.lblUser.addMouseListener(commentOneViewUser(uc));
    }

    private MouseAdapter commentOneViewUser(UiComment2 uc) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                getViewInformtion(usDao.selectByID(uc.getLikeCommentOne().getUserComment()));
//                pnlViewLabel.removeAll();
//                lblViewNameStore.setText("");
//                listGetBackAdd(layerChinh, pnlViewinformation);
            }
        };
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea txt;
    // End of variables declaration//GEN-END:variables
}
