
package fastfood.ui;

import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UiInputComment {
    
    public JPanel pnl = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
    public JTextField txt = new JTextField();
    public JLabel lblComment = new JLabel("Comment");
    
    
    public void panel(){
//        txt.setSize(120, 20);
        txt.setColumns(40);
        pnl.add(txt);
        pnl.add(lblComment);
        pnl.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
    }
    
}
