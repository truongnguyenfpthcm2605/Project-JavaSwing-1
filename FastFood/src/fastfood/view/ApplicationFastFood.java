
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fastfood.view;

import com.formdev.flatlaf.icons.FlatSearchIcon;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import fastfood.dao.BillDAO;
import fastfood.dao.CartDAO;
import fastfood.dao.CommentOneDAO;
import fastfood.dao.CommentTwoDAO;
import fastfood.dao.LikeCommentOneDAO;
import fastfood.dao.LikeCommentTwoDAO;
import fastfood.dao.MoneyDao;
import fastfood.dao.ProductDAO;
import fastfood.dao.UserDao;
import fastfood.dao.StoreDao;
import fastfood.dao.TypeProductDAo;
import fastfood.entity.Bill;
import fastfood.entity.Cart;
import fastfood.entity.CommentOne;
import fastfood.entity.CommentTwo;
import fastfood.entity.LikeCommentOne;
import fastfood.entity.LikeCommentTwo;
import fastfood.entity.Product;
import fastfood.entity.TypeProduct;
import fastfood.entity.User;
import fastfood.entity.UserStore;
import fastfood.sql.JDBCHelper;
import fastfood.ui.Spam;
import fastfood.ui.UiBill;
import fastfood.ui.UiCart;
import fastfood.ui.UiComment;
import fastfood.ui.UiComment2;
import fastfood.ui.UiCommentTwo;
import fastfood.ui.UiConfirm;
import fastfood.ui.UiInputComment;
import fastfood.ui.UiProduct;
import fastfood.ui.UiTkBill;
import fastfood.util.Authention;
import fastfood.util.CodeRandom;
import fastfood.util.Email;
import fastfood.util.FormatNumber;
import fastfood.util.ListLikeComment;
import fastfood.util.Message;
import fastfood.util.Msg;
import fastfood.util.Valid;
import fastfood.util.Ximage;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Timer;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 *
 * @author truong
 */
public class ApplicationFastFood extends javax.swing.JFrame implements Runnable {

    /**
     * Creates new form ApplicationFastFood
     */
    Valid data = new Valid();
    UserDao usDao = new UserDao();
    StoreDao storeDao = new StoreDao();
    ProductDAO productDao = new ProductDAO();
    TypeProductDAo typeDao = new TypeProductDAo();
    CartDAO cartDAO = new CartDAO();
    BillDAO billDAO = new BillDAO();
    MoneyDao money = new MoneyDao();
    CommentOneDAO commentOneDao = new CommentOneDAO();
    CommentTwoDAO commentTwoDao = new CommentTwoDAO();
    LikeCommentOneDAO likeCommentOneDAO = new LikeCommentOneDAO();
    LikeCommentTwoDAO likeCommentTwoDAO = new LikeCommentTwoDAO();
    Map<Integer, Boolean[]> hashLike = ListLikeComment.put();
    List<JComponent[]> listGetBack = new ArrayList<>();

    public ApplicationFastFood() {
        initComponents();
        lblLogoApp.setIcon(Ximage.reSizeImgae("logo-png-01.png", lblLogoApp));
        this.setIconImage(Ximage.reSizeImgae("logo-png-01.png", lblLogoApp).getImage());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        txtAdminSearch.setLeadingIcon(new FlatSearchIcon());
        fillCboTypeProductHome();
        listGetBackAdd(layerChinh, pnlHome);
        loadMoney();
        Runlabel();
        fillTableAdmin();
        new Hi(this, true).setVisible(true);

    }

    public void setCusor() {
        lblHome.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblConfirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblMyStore.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblManager.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblGetBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        setAvtroot();
    }

    public void Runlabel() {
        new Thread(() -> {
            String txt = lblRunLabel.getText() + "                     ";
            while (true) {
                txt = txt.charAt(txt.length() - 1) + txt.substring(0, txt.length() - 1);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ApplicationFastFood.class.getName()).log(Level.SEVERE, null, ex);
                }
                lblRunLabel.setText(txt);
            }
        }).start();
    }

    private void listGetBackAdd(JLayeredPane layer, JComponent comp) {
        layer(layer, comp);
        if (layer == layerViewMyStore) {
            layer(layerChinh, pnlViewStore);
        }
        listGetBack.add(new JComponent[]{layer, comp});
        if (listGetBack.size() > 1) {
            lblGetBack.setEnabled(true);
        }
    }

    private void listGetBack() {
        if (listGetBack.size() > 1) {
            JLayeredPane layer = (JLayeredPane) listGetBack.get(listGetBack.size() - 2)[0];
            JComponent comp = listGetBack.get(listGetBack.size() - 2)[1];

            if (layer == layerViewMyStore) {
                layer(layerChinh, pnlViewStore);
            }
            layer(layer, comp);
            listGetBack.remove(listGetBack.size() - 1);
            if (listGetBack.size() <= 1) {
                lblGetBack.setEnabled(false);
            }
        }
    }

    private boolean requestLogin() {
        if (Authention.authention == null) {
            Message.getNotify(this, "Please Login", "Required");
            listGetBackAdd(layerChinh, pnlLogin);
            Msg.setIconLabel(Ximage.getAppImagelcon(""), lblIconLoginUser, lblIconLoginPass);
            Msg.clearText(txtLoginUser, txtLoginPass);
            Msg.clearLabel(lblerrLoginUSer, lblerrLoginPass);
            return false;
        }
        return true;
    }

    private boolean checkLogin() {
        if (Authention.authention == null) {
            Message.getNotify(this, "You have to login new can use this function!", "Required");
            return false;
        }
        return true;
    }

    private void viewInforUser(User user) {
        setLabelinformationView(user);
        lblViewNameStore.setText(storeDao.getNameByUser(user.getUser()));
        listGetBackAdd(layerChinh, pnlViewinformation);
    }

    private MouseListener mouseViewUser(User user) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                viewInforUser(user);
                pnlViewLabel.removeAll();
            }
        };
    }

    private void viewInforProduct(Product p) {
        setLblInforProduct(p.getProduct());
        listGetBackAdd(layerChinh, pnlInforProduct);
    }

    private MouseListener mouseViewProduct(Product p) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                viewInforProduct(p);
            }
        };
    }

    private void addCart(Product p) {
        if (!checkLogin()) {
            return;
        }
        if (!p.isExistss()) {
            Message.getNotify(this, "This product deleted", "Message");
            return;
        }

        if (Message.getconfirm(null, "you want to add product " + p.getName() + " enter cart", "Message") == JOptionPane.YES_OPTION) {
            if (cartDAO.insert(Authention.authention.getUser(), p.getIdProduct())) {
                Message.getNotify(null, "Add product " + p.getName() + " success enter cart", verify);
            } else {
                Message.getNotify(null, "You had add this product enter your cart.", "Message");
            }
        }
    }

    private MouseListener mouseAddCart(Product p) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addCart(p);
            }

        };
    }

    private ActionListener actionLikeProduct(UiProduct p) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!checkLogin()) {
                    p.chkLike.setSelected(false);
                    return;
                }
                if (p.chkLike.isSelected()) {
                    p.increaseLike();
                    productDao.tim(Authention.authention.getUser(), p.getProduct().getIdProduct(), true);
                } else {
                    p.decreaseLike();
                    productDao.tim(Authention.authention.getUser(), p.getProduct().getIdProduct(), false);
                }
            }
        };
    }

    public void fillTableAdmin() {
        DefaultTableModel table = (DefaultTableModel) tblListAccount.getModel();
        table.setRowCount(0);
        List<User> list = usDao.getAllAccount();
        list.stream().forEach((e) -> {
            table.addRow(new Object[]{e.getUser(), e.getName(), e.getPass(), e.getPhone(), e.getEmail(), e.getAddress(), e.getBirth(), e.isGender() ? "Male" : "Female", e.getRoles()});
        });
    }

    private void setAvtroot() {
        lblAvt.setIcon(Ximage.reSizeImgae2(Ximage.read("Avt.jpg"), lblAvt));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        lblAvt = new fastfood.component.Label();
        lblManager = new javax.swing.JLabel();
        lblConfirm = new javax.swing.JLabel();
        lblMyStore = new javax.swing.JLabel();
        lblHome = new javax.swing.JLabel();
        lblGetBack = new javax.swing.JLabel();
        lblMoney = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        layerChinh = new javax.swing.JLayeredPane();
        pnlHome = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblLogoApp = new javax.swing.JLabel();
        lblOclock = new javax.swing.JLabel();
        txtSearch = new fastfood.component.TextFeild();
        lblCart = new fastfood.component.Label();
        btnSearch = new fastfood.component.Button();
        lblQRcore = new fastfood.component.Label();
        cboTypeProductHome = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlProduct = new javax.swing.JPanel();
        lblRunLabel = new javax.swing.JLabel();
        lblMap = new javax.swing.JLabel();
        pnlViewStore = new javax.swing.JPanel();
        layerViewMyStore = new javax.swing.JLayeredPane();
        jScrollpnlStore = new javax.swing.JScrollPane();
        pnlStore = new javax.swing.JPanel();
        pnlViewInsertProduct = new javax.swing.JPanel();
        lblIPImg = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lblIPIconName = new javax.swing.JLabel();
        lblIPIconPrice = new javax.swing.JLabel();
        lblIPErrorName = new javax.swing.JLabel();
        lblIPErrorPrice = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        lblIPIconAddress = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        lblIPErrorQuantity = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtIPNote = new fastfood.component.TextArea();
        lblIPIconQuantity = new javax.swing.JLabel();
        lblIPErrorNote = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtIPName = new fastfood.component.TextFeild();
        txtIPPrice = new fastfood.component.TextFeild();
        txtIPQuantity = new fastfood.component.TextFeild();
        cboIPType = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        pnlViewCart = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        pnlCart = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        lblCartMoney = new javax.swing.JLabel();
        pnlLogin = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblTitleLoginUser = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtLoginUser = new fastfood.component.TextFeild();
        txtLoginPass = new fastfood.component.TextPass();
        lblerrLoginUSer = new javax.swing.JLabel();
        lblerrLoginPass = new javax.swing.JLabel();
        lblIconLoginUser = new javax.swing.JLabel();
        lblIconLoginPass = new javax.swing.JLabel();
        btnLogin = new fastfood.component.Button();
        lblForgotPass = new javax.swing.JLabel();
        lblRegister = new javax.swing.JLabel();
        button3 = new fastfood.component.Button();
        pnlUpdateInformation = new javax.swing.JPanel();
        lblUsImage = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtUsName = new fastfood.component.TextFeild();
        lblUserrName = new javax.swing.JLabel();
        txtUsBirth = new fastfood.component.TextFeild();
        jLabel39 = new javax.swing.JLabel();
        lblUserrBirth = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txtUsPhone = new fastfood.component.TextFeild();
        jLabel45 = new javax.swing.JLabel();
        lblUserrPhone = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        lblUserrEmail = new javax.swing.JLabel();
        txtUsEmail = new fastfood.component.TextFeild();
        jLabel59 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtUsAddress = new fastfood.component.TextArea();
        lblUserrAddrss = new javax.swing.JLabel();
        rdoUsMale = new javax.swing.JRadioButton();
        rdoUsFemale = new javax.swing.JRadioButton();
        lblTitleName = new javax.swing.JLabel();
        btnChange = new fastfood.component.Button();
        jLabel16 = new javax.swing.JLabel();
        pnlChangePass = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtChangConfirmPass = new fastfood.component.TextPass();
        txtChangCurrentPass = new fastfood.component.TextPass();
        txtChangNewPass = new fastfood.component.TextPass();
        lbliconChangCurrentPass = new javax.swing.JLabel();
        lbliconChangNewPass = new javax.swing.JLabel();
        lbliconChangCheckConfirmPass = new javax.swing.JLabel();
        lblChangerrCurrentPass = new javax.swing.JLabel();
        lblChangerrNewPass = new javax.swing.JLabel();
        lblChangerrConfirmPass = new javax.swing.JLabel();
        btnChangePass = new fastfood.component.Button();
        btnCancel = new fastfood.component.Button();
        chkShow = new javax.swing.JCheckBox();
        pnlRegister = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtReUser = new fastfood.component.TextFeild();
        lblReErrorUSe = new javax.swing.JLabel();
        lblIconReUserName = new javax.swing.JLabel();
        lbliconRePass = new javax.swing.JLabel();
        lblReErrorPass = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblIconReConfrim = new javax.swing.JLabel();
        lblReerrConfrimPass = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lbliconReEmail = new javax.swing.JLabel();
        lblReerremail = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtReConfrimPass = new fastfood.component.TextPass();
        btnRegister = new fastfood.component.Button();
        btnLoginBack = new fastfood.component.Button();
        lblflagReCode = new javax.swing.JLabel();
        lblReerrCode = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtReEmail = new fastfood.component.TextFeild();
        txtReCode = new fastfood.component.TextFeild();
        txtRePass = new fastfood.component.TextPass();
        pnlViewConfrim = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        pnlConfirm = new javax.swing.JPanel();
        pnlViewinformation = new javax.swing.JPanel();
        lblViewImg = new javax.swing.JLabel();
        lblViewName = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblViewDateOfBirth = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblViewGender = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblViewphone = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblViewEmail = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblViewAddress = new javax.swing.JLabel();
        pnlViewLabel = new javax.swing.JPanel();
        lblViewUpdate = new javax.swing.JLabel();
        lblViewLogOut = new javax.swing.JLabel();
        pnlViewUserStore = new javax.swing.JLabel();
        lblViewInforAddType = new javax.swing.JLabel();
        lblHistory = new javax.swing.JLabel();
        lblHistoryStore = new javax.swing.JLabel();
        pnlViewNameStore = new javax.swing.JPanel();
        lblViewNameStore = new javax.swing.JLabel();
        lblUserQrcode = new javax.swing.JLabel();
        pnlInforProduct = new javax.swing.JPanel();
        lblInforImgProduct = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        lblInforNameProduct = new javax.swing.JLabel();
        lblInforPriceProduct = new javax.swing.JLabel();
        lblInforNameStore = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        lblInforNoteProduct = new fastfood.component.TextArea();
        lblInforCart = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        lblInforQuantityProduct = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        lblInforSlBuyProduct = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        txtCommentOne = new fastfood.component.TextArea();
        jScrollPane13 = new javax.swing.JScrollPane();
        pnlInforCommentOneProduct = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        pnlAdmin = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblListAccount = new fastfood.component.Table();
        btnAdimndelete = new fastfood.component.Button();
        txtAdminSearch = new fastfood.component.TextFeild();
        btnExcel = new fastfood.component.Button();
        button1 = new fastfood.component.Button();
        button2 = new fastfood.component.Button();
        pnlHistoryUser = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        pnlHistoryUsers = new javax.swing.JPanel();
        pnlHistoryStore = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        pnlHistoryStores = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        lblHistoryStoreTurnover = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        lblHistoryStoreWage = new javax.swing.JLabel();
        cboHistory = new javax.swing.JComboBox<>();
        jLabel44 = new javax.swing.JLabel();
        pnlTkStore = new javax.swing.JPanel();
        cbolTkStore = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        pnlTkStores = new javax.swing.JPanel();
        pnlBill = new javax.swing.JPanel();
        lblBillImg = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        lblBillName = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        lblBillSLMua = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        lblBillPriceSP = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        lblBillMoney = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        lblBillNoteSp = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        pnlUserViewStore = new javax.swing.JPanel();
        lblUserViewStoreImg = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        lblUserViewStoreNameStore = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        pnlUserViewStores = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lblAvt.setBackground(new java.awt.Color(255, 51, 51));
        lblAvt.setOpaque(true);
        lblAvt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAvtMouseClicked(evt);
            }
        });

        lblManager.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        lblManager.setForeground(new java.awt.Color(204, 0, 0));
        lblManager.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblManager.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/License-manager-icon.png"))); // NOI18N
        lblManager.setText("Manager");
        lblManager.setOpaque(true);
        lblManager.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblManagerMouseClicked(evt);
            }
        });

        lblConfirm.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        lblConfirm.setForeground(new java.awt.Color(204, 0, 0));
        lblConfirm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConfirm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/confirm-security-icon.png"))); // NOI18N
        lblConfirm.setText("Confirm");
        lblConfirm.setOpaque(true);
        lblConfirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblConfirmMouseClicked(evt);
            }
        });

        lblMyStore.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        lblMyStore.setForeground(new java.awt.Color(204, 0, 0));
        lblMyStore.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMyStore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/Clothing-Store-icon.png"))); // NOI18N
        lblMyStore.setText("My Store");
        lblMyStore.setOpaque(true);
        lblMyStore.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMyStoreMouseClicked(evt);
            }
        });

        lblHome.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        lblHome.setForeground(new java.awt.Color(204, 0, 0));
        lblHome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/home-icon.png"))); // NOI18N
        lblHome.setText("Home");
        lblHome.setOpaque(true);
        lblHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHomeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblHomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblHomeMouseExited(evt);
            }
        });

        lblGetBack.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblGetBack.setForeground(new java.awt.Color(255, 51, 51));
        lblGetBack.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGetBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/back-icon.png"))); // NOI18N
        lblGetBack.setText("Get back");
        lblGetBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGetBackMouseClicked(evt);
            }
        });

        lblMoney.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        lblMoney.setForeground(new java.awt.Color(255, 204, 51));
        lblMoney.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMoney.setText(" ");

        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/coin-us-dollar-icon.png"))); // NOI18N
        jLabel61.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel61MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblManager, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMyStore, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblConfirm, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblGetBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 45, Short.MAX_VALUE)
                .addComponent(lblAvt, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel61)
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAvt, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMoney, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addComponent(lblHome, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMyStore, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblManager, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblGetBack, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layerChinh.setBackground(new java.awt.Color(255, 255, 255));
        layerChinh.setLayout(new java.awt.CardLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("FastFood.com.vn");

        lblOclock.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblOclock.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblOclock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/Clock-2-icon.png"))); // NOI18N
        lblOclock.setText(" ");
        lblOclock.setToolTipText("");

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        lblCart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/cart-icon.png"))); // NOI18N
        lblCart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCartMouseClicked(evt);
            }
        });

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/search-icon.png"))); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        lblQRcore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/Ecommerce-Qr-Code-icon.png"))); // NOI18N
        lblQRcore.setText("label1");
        lblQRcore.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQRcoreMouseClicked(evt);
            }
        });

        cboTypeProductHome.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboTypeProductHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTypeProductHomeActionPerformed(evt);
            }
        });

        pnlProduct.setLayout(new java.awt.GridLayout(0, 4, 35, 35));
        jScrollPane1.setViewportView(pnlProduct);

        lblRunLabel.setFont(new java.awt.Font("Segoe UI Semibold", 2, 14)); // NOI18N
        lblRunLabel.setForeground(new java.awt.Color(204, 204, 0));
        lblRunLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRunLabel.setText("Fast Food is the best application in the world");

        lblMap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/map.png"))); // NOI18N
        lblMap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMapMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlHomeLayout = new javax.swing.GroupLayout(pnlHome);
        pnlHome.setLayout(pnlHomeLayout);
        pnlHomeLayout.setHorizontalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addGroup(pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHomeLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(24, 24, 24)
                        .addComponent(lblLogoApp, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMap)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblQRcore, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlHomeLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(cboTypeProductHome, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(lblOclock, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlHomeLayout.createSequentialGroup()
                .addGap(313, 313, 313)
                .addComponent(lblRunLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(323, Short.MAX_VALUE))
        );
        pnlHomeLayout.setVerticalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addGroup(pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLogoApp, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addGroup(pnlHomeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblCart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblQRcore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboTypeProductHome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOclock))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRunLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layerChinh.add(pnlHome, "card2");

        layerViewMyStore.setLayout(new java.awt.CardLayout());

        pnlStore.setLayout(new java.awt.GridLayout(0, 3, 20, 20));
        jScrollpnlStore.setViewportView(pnlStore);

        layerViewMyStore.add(jScrollpnlStore, "card3");

        lblIPImg.setBackground(new java.awt.Color(255, 51, 51));
        lblIPImg.setOpaque(true);
        lblIPImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIPImgMouseClicked(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel26.setText("Name");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel27.setText("Price");

        lblIPErrorName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIPErrorName.setText(" ");

        lblIPErrorPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIPErrorPrice.setText(" ");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel29.setText("Quantity");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel30.setText("Note");

        lblIPErrorQuantity.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIPErrorQuantity.setText(" ");

        txtIPNote.setColumns(20);
        txtIPNote.setRows(5);
        jScrollPane7.setViewportView(txtIPNote);

        lblIPErrorNote.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIPErrorNote.setText(" ");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel31.setText("Xác nhận");
        jLabel31.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel31MouseClicked(evt);
            }
        });

        cboIPType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnlViewInsertProductLayout = new javax.swing.GroupLayout(pnlViewInsertProduct);
        pnlViewInsertProduct.setLayout(pnlViewInsertProductLayout);
        pnlViewInsertProductLayout.setHorizontalGroup(
            pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlViewInsertProductLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlViewInsertProductLayout.createSequentialGroup()
                        .addGroup(pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27))
                        .addGap(18, 18, 18)
                        .addGroup(pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblIPErrorPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                                    .addComponent(lblIPErrorName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(txtIPName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtIPPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblIPIconName, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(lblIPIconPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(lblIPImg, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63)
                .addGroup(pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlViewInsertProductLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGroup(pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlViewInsertProductLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblIPErrorQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlViewInsertProductLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboIPType, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIPQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblIPIconQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlViewInsertProductLayout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addGap(18, 18, 18)
                        .addGroup(pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblIPErrorNote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblIPIconAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 128, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlViewInsertProductLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(287, 287, 287))
        );
        pnlViewInsertProductLayout.setVerticalGroup(
            pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlViewInsertProductLayout.createSequentialGroup()
                .addGroup(pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlViewInsertProductLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(lblIPImg, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlViewInsertProductLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(cboIPType, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlViewInsertProductLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIPQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblIPErrorQuantity)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlViewInsertProductLayout.createSequentialGroup()
                        .addGroup(pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlViewInsertProductLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblIPIconName, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIPName, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblIPIconQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlViewInsertProductLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblIPErrorName)
                                .addGap(43, 43, 43)
                                .addGroup(pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblIPIconPrice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlViewInsertProductLayout.createSequentialGroup()
                                        .addComponent(txtIPPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblIPErrorPrice))))
                            .addGroup(pnlViewInsertProductLayout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlViewInsertProductLayout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addGroup(pnlViewInsertProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblIPIconAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)))
                .addComponent(lblIPErrorNote)
                .addGap(18, 18, 18)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78))
        );

        layerViewMyStore.add(pnlViewInsertProduct, "card3");

        jLabel18.setFont(new java.awt.Font("Segoe UI Semilight", 3, 14)); // NOI18N
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/add-1-icon.png"))); // NOI18N
        jLabel18.setText("Add New Product");
        jLabel18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI Semilight", 3, 14)); // NOI18N
        jLabel19.setText("View My Store");
        jLabel19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel46.setText("Spam");
        jLabel46.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel46.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel46MouseClicked(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel48.setText("Thống kê");
        jLabel48.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel48.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel48MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlViewStoreLayout = new javax.swing.GroupLayout(pnlViewStore);
        pnlViewStore.setLayout(pnlViewStoreLayout);
        pnlViewStoreLayout.setHorizontalGroup(
            pnlViewStoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlViewStoreLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(layerViewMyStore, javax.swing.GroupLayout.PREFERRED_SIZE, 954, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
            .addGroup(pnlViewStoreLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132)
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel46)
                .addGap(187, 187, 187))
        );
        pnlViewStoreLayout.setVerticalGroup(
            pnlViewStoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlViewStoreLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlViewStoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46)
                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(layerViewMyStore)
                .addContainerGap())
        );

        layerChinh.add(pnlViewStore, "card3");

        pnlCart.setLayout(new javax.swing.BoxLayout(pnlCart, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane5.setViewportView(pnlCart);

        jLabel58.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel58.setText("Pay");
        jLabel58.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel58MouseClicked(evt);
            }
        });

        jLabel60.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel60.setText("Total money :");

        lblCartMoney.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblCartMoney.setText("jLabel61");

        javax.swing.GroupLayout pnlViewCartLayout = new javax.swing.GroupLayout(pnlViewCart);
        pnlViewCart.setLayout(pnlViewCartLayout);
        pnlViewCartLayout.setHorizontalGroup(
            pnlViewCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlViewCartLayout.createSequentialGroup()
                .addGap(0, 47, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 933, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 47, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlViewCartLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel60)
                .addGap(30, 30, 30)
                .addComponent(lblCartMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88))
        );
        pnlViewCartLayout.setVerticalGroup(
            pnlViewCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlViewCartLayout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addGroup(pnlViewCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addGroup(pnlViewCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCartMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31))
        );

        layerChinh.add(pnlViewCart, "card4");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/bannerlogin.jpg"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Showcard Gothic", 3, 36)); // NOI18N
        jLabel3.setText("LOGIN");

        jLabel4.setFont(new java.awt.Font("Eras Bold ITC", 3, 14)); // NOI18N
        jLabel4.setText("LOGIN INTO YOUR ACCOUNT");

        lblTitleLoginUser.setFont(new java.awt.Font("Segoe UI Emoji", 3, 12)); // NOI18N
        lblTitleLoginUser.setText("Usename");

        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 3, 12)); // NOI18N
        jLabel6.setText("Password");

        txtLoginUser.setPlaceholderText("Please enter user\n");

        txtLoginPass.setPlaceholderText("Please enter pass");

        lblerrLoginUSer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblerrLoginUSer.setText(" ");

        lblerrLoginPass.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblerrLoginPass.setText(" ");

        lblIconLoginUser.setText(" ");

        lblIconLoginPass.setText(" ");

        btnLogin.setBackground(new java.awt.Color(51, 51, 51));
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        lblForgotPass.setText("ForgotPass ?");
        lblForgotPass.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblRegister.setText("Register ?");
        lblRegister.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRegisterMouseClicked(evt);
            }
        });

        button3.setText("Login Qrcode");
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLoginLayout = new javax.swing.GroupLayout(pnlLogin);
        pnlLogin.setLayout(pnlLoginLayout);
        pnlLoginLayout.setHorizontalGroup(
            pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLoginLayout.createSequentialGroup()
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLoginLayout.createSequentialGroup()
                        .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLoginLayout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLoginLayout.createSequentialGroup()
                                        .addComponent(lblForgotPass, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(51, 51, 51)
                                        .addComponent(lblRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(62, 62, 62))
                                    .addGroup(pnlLoginLayout.createSequentialGroup()
                                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(pnlLoginLayout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblerrLoginPass, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblerrLoginUSer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblTitleLoginUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtLoginUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtLoginPass, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIconLoginUser, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblIconLoginPass, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlLoginLayout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(jLabel2))
        );
        pnlLoginLayout.setVerticalGroup(
            pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoginLayout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(76, 76, 76)
                .addComponent(lblTitleLoginUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLoginUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIconLoginUser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblerrLoginUSer)
                .addGap(7, 7, 7)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLoginPass, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIconLoginPass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblerrLoginPass)
                .addGap(29, 29, 29)
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblForgotPass)
                    .addComponent(lblRegister))
                .addGap(27, 27, 27)
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(84, Short.MAX_VALUE))
            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        layerChinh.add(pnlLogin, "card2");

        lblUsImage.setBackground(new java.awt.Color(255, 0, 0));
        lblUsImage.setText("jLabel36");
        lblUsImage.setOpaque(true);
        lblUsImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUsImageMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblUsImageMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblUsImageMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblUsImageMousePressed(evt);
            }
        });

        jLabel35.setText("Name");

        txtUsName.setText("textFeild5");

        lblUserrName.setForeground(new java.awt.Color(255, 51, 51));
        lblUserrName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserrName.setText(" ");

        txtUsBirth.setText("textFeild5");

        jLabel39.setText("Date of birth(yyyy/mm/dd)");

        lblUserrBirth.setForeground(new java.awt.Color(255, 51, 51));
        lblUserrBirth.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserrBirth.setText(" ");

        jLabel42.setText("Gender");

        txtUsPhone.setText("textFeild5");

        jLabel45.setText("Phone");

        lblUserrPhone.setForeground(new java.awt.Color(255, 51, 51));
        lblUserrPhone.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserrPhone.setText(" ");

        jLabel54.setText("Address");

        lblUserrEmail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserrEmail.setText(" ");

        txtUsEmail.setEditable(false);
        txtUsEmail.setText("textFeild5");

        jLabel59.setText("Email");

        txtUsAddress.setColumns(20);
        txtUsAddress.setRows(5);
        jScrollPane3.setViewportView(txtUsAddress);

        lblUserrAddrss.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserrAddrss.setText(" ");

        buttonGroup1.add(rdoUsMale);
        rdoUsMale.setText("Male");

        buttonGroup1.add(rdoUsFemale);
        rdoUsFemale.setText("FeMale");

        lblTitleName.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        lblTitleName.setText("My Name");

        btnChange.setBackground(new java.awt.Color(0, 102, 51));
        btnChange.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/edit-icon.png"))); // NOI18N
        btnChange.setText("Change Password");
        btnChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeActionPerformed(evt);
            }
        });

        jLabel16.setText("Change information");
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlUpdateInformationLayout = new javax.swing.GroupLayout(pnlUpdateInformation);
        pnlUpdateInformation.setLayout(pnlUpdateInformationLayout);
        pnlUpdateInformationLayout.setHorizontalGroup(
            pnlUpdateInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUpdateInformationLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(pnlUpdateInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addGroup(pnlUpdateInformationLayout.createSequentialGroup()
                        .addComponent(rdoUsMale, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rdoUsFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlUpdateInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblUserrPhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtUsPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE))
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlUpdateInformationLayout.createSequentialGroup()
                        .addComponent(lblUsImage, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(lblTitleName, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlUpdateInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblUserrName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtUsName, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE))
                    .addGroup(pnlUpdateInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblUserrBirth, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtUsBirth, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                .addGroup(pnlUpdateInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlUpdateInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblUserrEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtUsEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE))
                    .addGroup(pnlUpdateInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblUserrAddrss, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)))
                .addGap(116, 116, 116))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUpdateInformationLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(btnChange, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        pnlUpdateInformationLayout.setVerticalGroup(
            pnlUpdateInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUpdateInformationLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlUpdateInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsImage, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitleName))
                .addGap(62, 62, 62)
                .addGroup(pnlUpdateInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUpdateInformationLayout.createSequentialGroup()
                        .addComponent(jLabel59)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUsEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUserrEmail))
                    .addGroup(pnlUpdateInformationLayout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUsName, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUserrName)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUpdateInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUpdateInformationLayout.createSequentialGroup()
                        .addComponent(jLabel54)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUserrAddrss))
                    .addGroup(pnlUpdateInformationLayout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUsBirth, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUserrBirth)
                        .addGap(3, 3, 3)
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlUpdateInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoUsMale)
                            .addComponent(rdoUsFemale))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUsPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUserrPhone)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(pnlUpdateInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnChange, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        layerChinh.add(pnlUpdateInformation, "card3");

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/bannerChangPass.jpg"))); // NOI18N

        jLabel22.setFont(new java.awt.Font("Showcard Gothic", 3, 24)); // NOI18N
        jLabel22.setText("CHANGE PASSWORD");

        jLabel23.setFont(new java.awt.Font("Segoe UI Emoji", 3, 12)); // NOI18N
        jLabel23.setText("Current password");

        jLabel24.setFont(new java.awt.Font("Segoe UI Emoji", 3, 12)); // NOI18N
        jLabel24.setText("New Password");

        jLabel25.setFont(new java.awt.Font("Segoe UI Emoji", 3, 12)); // NOI18N
        jLabel25.setText("Confrim Password");

        txtChangConfirmPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtChangConfirmPassActionPerformed(evt);
            }
        });

        lbliconChangCurrentPass.setText(" ");

        lbliconChangNewPass.setText(" ");

        lbliconChangCheckConfirmPass.setText(" ");

        lblChangerrCurrentPass.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChangerrCurrentPass.setText(" ");

        lblChangerrNewPass.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChangerrNewPass.setText(" ");

        lblChangerrConfirmPass.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChangerrConfirmPass.setText(" ");

        btnChangePass.setBackground(new java.awt.Color(51, 51, 51));
        btnChangePass.setForeground(new java.awt.Color(255, 255, 255));
        btnChangePass.setText("Change");
        btnChangePass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePassActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        chkShow.setFont(new java.awt.Font("Segoe UI Emoji", 3, 12)); // NOI18N
        chkShow.setText("Show");
        chkShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlChangePassLayout = new javax.swing.GroupLayout(pnlChangePass);
        pnlChangePass.setLayout(pnlChangePassLayout);
        pnlChangePassLayout.setHorizontalGroup(
            pnlChangePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlChangePassLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(pnlChangePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlChangePassLayout.createSequentialGroup()
                        .addGroup(pnlChangePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlChangePassLayout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(jLabel22))
                            .addGroup(pnlChangePassLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(pnlChangePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel25)
                                    .addComponent(chkShow, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnlChangePassLayout.createSequentialGroup()
                                        .addGroup(pnlChangePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(lblChangerrCurrentPass, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtChangCurrentPass, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addComponent(lbliconChangCurrentPass, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlChangePassLayout.createSequentialGroup()
                                        .addGroup(pnlChangePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lblChangerrNewPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtChangNewPass, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addComponent(lbliconChangNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlChangePassLayout.createSequentialGroup()
                                        .addGroup(pnlChangePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(lblChangerrConfirmPass, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtChangConfirmPass, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addComponent(lbliconChangCheckConfirmPass, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(39, 39, 39))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlChangePassLayout.createSequentialGroup()
                        .addGap(0, 80, Short.MAX_VALUE)
                        .addComponent(btnChangePass, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(126, 126, 126)))
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlChangePassLayout.setVerticalGroup(
            pnlChangePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChangePassLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jLabel22)
                .addGap(91, 91, 91)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlChangePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtChangCurrentPass, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbliconChangCurrentPass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblChangerrCurrentPass)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlChangePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtChangNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbliconChangNewPass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblChangerrNewPass)
                .addGap(10, 10, 10)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlChangePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtChangConfirmPass, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbliconChangCheckConfirmPass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblChangerrConfirmPass)
                .addGap(19, 19, 19)
                .addComponent(chkShow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlChangePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChangePass, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(78, 134, Short.MAX_VALUE))
            .addGroup(pnlChangePassLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        layerChinh.add(pnlChangePass, "card5");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/bannerRegister.jpg"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Showcard Gothic", 2, 36)); // NOI18N
        jLabel8.setText("Register");

        jLabel9.setFont(new java.awt.Font("Segoe UI Emoji", 3, 12)); // NOI18N
        jLabel9.setText("Username");

        txtReUser.setPlaceholderText("Please enter user");

        lblReErrorUSe.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        lblIconReUserName.setText(" ");

        lbliconRePass.setText(" ");

        lblReErrorPass.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblReErrorPass.setText(" ");

        jLabel14.setFont(new java.awt.Font("Segoe UI Emoji", 3, 12)); // NOI18N
        jLabel14.setText("Password");

        lblIconReConfrim.setText(" ");

        lblReerrConfrimPass.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblReerrConfrimPass.setText(" ");

        jLabel17.setFont(new java.awt.Font("Segoe UI Emoji", 3, 12)); // NOI18N
        jLabel17.setText("Confirm password");

        lbliconReEmail.setText(" ");

        lblReerremail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblReerremail.setText(" ");

        jLabel20.setFont(new java.awt.Font("Segoe UI Emoji", 3, 12)); // NOI18N
        jLabel20.setText("Email");

        txtReConfrimPass.setPlaceholderText("Please  enterconfrim password");
        txtReConfrimPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReConfrimPassActionPerformed(evt);
            }
        });

        btnRegister.setBackground(new java.awt.Color(51, 51, 51));
        btnRegister.setForeground(new java.awt.Color(255, 255, 255));
        btnRegister.setText("Register");
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        btnLoginBack.setForeground(new java.awt.Color(51, 51, 51));
        btnLoginBack.setText("Login");
        btnLoginBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginBackActionPerformed(evt);
            }
        });

        lblflagReCode.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblflagReCode.setForeground(new java.awt.Color(102, 255, 102));
        lblflagReCode.setText(" ");

        lblReerrCode.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblReerrCode.setText(" ");

        jLabel28.setFont(new java.awt.Font("Segoe UI Emoji", 3, 12)); // NOI18N
        jLabel28.setText("Verification codes");

        txtReEmail.setPlaceholderText("Please enter email");

        txtReCode.setPlaceholderText("Please enter email");
        txtReCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReCodeActionPerformed(evt);
            }
        });
        txtReCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtReCodeKeyTyped(evt);
            }
        });

        txtRePass.setPlaceholderText("Please enter password");

        javax.swing.GroupLayout pnlRegisterLayout = new javax.swing.GroupLayout(pnlRegister);
        pnlRegister.setLayout(pnlRegisterLayout);
        pnlRegisterLayout.setHorizontalGroup(
            pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRegisterLayout.createSequentialGroup()
                .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRegisterLayout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlRegisterLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlRegisterLayout.createSequentialGroup()
                                .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtReUser, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblReErrorUSe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblIconReUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlRegisterLayout.createSequentialGroup()
                                .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblReErrorPass, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                                    .addComponent(txtRePass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbliconRePass, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlRegisterLayout.createSequentialGroup()
                                .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtReConfrimPass, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblReerrConfrimPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblIconReConfrim, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlRegisterLayout.createSequentialGroup()
                                .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlRegisterLayout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblReerremail, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                                    .addComponent(txtReEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbliconReEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlRegisterLayout.createSequentialGroup()
                                .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnLoginBack, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtReCode, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                                    .addComponent(lblReerrCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblflagReCode, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                .addComponent(jLabel7))
        );
        pnlRegisterLayout.setVerticalGroup(
            pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegisterLayout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                    .addGroup(pnlRegisterLayout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtReUser, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblIconReUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblReErrorUSe, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbliconRePass, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRePass, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblReErrorPass)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblIconReConfrim)
                            .addComponent(txtReConfrimPass, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblReerrConfrimPass)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbliconReEmail)
                            .addComponent(txtReEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblReerremail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblflagReCode)
                            .addComponent(txtReCode, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblReerrCode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLoginBack, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52))))
        );

        layerChinh.add(pnlRegister, "card4");

        pnlConfirm.setLayout(new java.awt.GridLayout(0, 1, 0, 29));
        jScrollPane6.setViewportView(pnlConfirm);

        javax.swing.GroupLayout pnlViewConfrimLayout = new javax.swing.GroupLayout(pnlViewConfrim);
        pnlViewConfrim.setLayout(pnlViewConfrimLayout);
        pnlViewConfrimLayout.setHorizontalGroup(
            pnlViewConfrimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlViewConfrimLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 958, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );
        pnlViewConfrimLayout.setVerticalGroup(
            pnlViewConfrimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlViewConfrimLayout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 29, Short.MAX_VALUE))
        );

        layerChinh.add(pnlViewConfrim, "card9");

        lblViewImg.setBackground(new java.awt.Color(255, 51, 0));
        lblViewImg.setOpaque(true);

        lblViewName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewName.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setText("Name");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel10.setText("Date of birth");

        lblViewDateOfBirth.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewDateOfBirth.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel11.setText("Gender");

        lblViewGender.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewGender.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel12.setText("Phone");

        lblViewphone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewphone.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel13.setText("Address");

        lblViewEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewEmail.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel15.setText("Email");

        lblViewAddress.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewAddress.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        pnlViewLabel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 5));

        lblViewUpdate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/update.png"))); // NOI18N
        lblViewUpdate.setText("Update information");
        lblViewUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblViewUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblViewUpdateMouseClicked(evt);
            }
        });
        pnlViewLabel.add(lblViewUpdate);

        lblViewLogOut.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewLogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/logout.png"))); // NOI18N
        lblViewLogOut.setText("Log out");
        lblViewLogOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblViewLogOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblViewLogOutMouseClicked(evt);
            }
        });
        pnlViewLabel.add(lblViewLogOut);

        pnlViewUserStore.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlViewUserStore.setText("Bán hàng");
        pnlViewUserStore.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlViewUserStore.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlViewUserStoreMouseClicked(evt);
            }
        });
        pnlViewLabel.add(pnlViewUserStore);

        lblViewInforAddType.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewInforAddType.setText("Add type product");
        lblViewInforAddType.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblViewInforAddType.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblViewInforAddTypeMouseClicked(evt);
            }
        });
        pnlViewLabel.add(lblViewInforAddType);

        lblHistory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblHistory.setText("History");
        lblHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHistoryMouseClicked(evt);
            }
        });
        pnlViewLabel.add(lblHistory);

        lblHistoryStore.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblHistoryStore.setText("HistoryStore");
        lblHistoryStore.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblHistoryStore.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHistoryStoreMouseClicked(evt);
            }
        });
        pnlViewLabel.add(lblHistoryStore);

        pnlViewNameStore.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblViewNameStore.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblViewNameStore.setForeground(new java.awt.Color(255, 0, 0));
        lblViewNameStore.setText("jLabel26");
        lblViewNameStore.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblViewNameStore.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblViewNameStoreMouseClicked(evt);
            }
        });
        pnlViewNameStore.add(lblViewNameStore);

        lblUserQrcode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUserQrcodeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlViewinformationLayout = new javax.swing.GroupLayout(pnlViewinformation);
        pnlViewinformation.setLayout(pnlViewinformationLayout);
        pnlViewinformationLayout.setHorizontalGroup(
            pnlViewinformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlViewinformationLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(pnlViewinformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlViewinformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlViewinformationLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(lblViewName, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlViewinformationLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlViewinformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblViewDateOfBirth, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblViewGender, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addGroup(pnlViewinformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlViewinformationLayout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(lblViewphone, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlViewinformationLayout.createSequentialGroup()
                        .addGroup(pnlViewinformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(pnlViewinformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblViewAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblViewEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(165, Short.MAX_VALUE))
            .addGroup(pnlViewinformationLayout.createSequentialGroup()
                .addGroup(pnlViewinformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlViewinformationLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(lblViewImg, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 166, Short.MAX_VALUE)
                        .addComponent(pnlViewLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95))
                    .addGroup(pnlViewinformationLayout.createSequentialGroup()
                        .addGap(198, 198, 198)
                        .addComponent(pnlViewNameStore, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(lblUserQrcode, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        pnlViewinformationLayout.setVerticalGroup(
            pnlViewinformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlViewinformationLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(pnlViewinformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlViewinformationLayout.createSequentialGroup()
                        .addGroup(pnlViewinformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblViewImg, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlViewLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                        .addComponent(pnlViewNameStore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnlViewinformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblViewEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlViewinformationLayout.createSequentialGroup()
                                .addGroup(pnlViewinformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(lblViewName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnlViewinformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblViewphone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(pnlViewinformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlViewinformationLayout.createSequentialGroup()
                                        .addGap(87, 87, 87)
                                        .addGroup(pnlViewinformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lblViewDateOfBirth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlViewinformationLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(82, 82, 82)
                        .addGroup(pnlViewinformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblViewGender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblViewAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(55, 55, 55))
                    .addGroup(pnlViewinformationLayout.createSequentialGroup()
                        .addComponent(lblUserQrcode, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        layerChinh.add(pnlViewinformation, "card10");

        lblInforImgProduct.setText("jLabel32");

        jLabel33.setText("Name Product");

        jLabel34.setText("Price Product");

        jLabel36.setText("Name Store");

        jLabel37.setText("Note");

        lblInforNameProduct.setText("jLabel38");

        lblInforPriceProduct.setText("jLabel40");

        lblInforNameStore.setForeground(new java.awt.Color(255, 51, 51));
        lblInforNameStore.setText("jLabel41");
        lblInforNameStore.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblInforNameStore.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInforNameStoreMouseClicked(evt);
            }
        });

        lblInforNoteProduct.setColumns(20);
        lblInforNoteProduct.setRows(5);
        jScrollPane4.setViewportView(lblInforNoteProduct);

        lblInforCart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/shopping-cart-icon.png"))); // NOI18N
        lblInforCart.setText("jLabel32");
        lblInforCart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInforCartMouseClicked(evt);
            }
        });

        jLabel38.setText("Quantity :");

        lblInforQuantityProduct.setText("jLabel40");

        jLabel47.setText("SL Buy");

        lblInforSlBuyProduct.setText("jLabel48");

        jLabel57.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(51, 51, 255));
        jLabel57.setText("Bình Luận");

        txtCommentOne.setColumns(20);
        txtCommentOne.setRows(5);
        jScrollPane12.setViewportView(txtCommentOne);

        pnlInforCommentOneProduct.setLayout(new javax.swing.BoxLayout(pnlInforCommentOneProduct, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane13.setViewportView(pnlInforCommentOneProduct);

        jButton1.setText("Comment");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlInforProductLayout = new javax.swing.GroupLayout(pnlInforProduct);
        pnlInforProduct.setLayout(pnlInforProductLayout);
        pnlInforProductLayout.setHorizontalGroup(
            pnlInforProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInforProductLayout.createSequentialGroup()
                .addGroup(pnlInforProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInforProductLayout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(lblInforImgProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(620, 620, 620)
                        .addComponent(lblInforCart, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlInforProductLayout.createSequentialGroup()
                        .addGroup(pnlInforProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInforProductLayout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addGroup(pnlInforProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlInforProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(pnlInforProductLayout.createSequentialGroup()
                                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(78, 78, 78)
                                            .addComponent(jLabel47)
                                            .addGap(29, 29, 29)
                                            .addComponent(lblInforSlBuyProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(83, 83, 83)))
                                    .addGroup(pnlInforProductLayout.createSequentialGroup()
                                        .addGroup(pnlInforProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblInforNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(pnlInforProductLayout.createSequentialGroup()
                                                .addComponent(jLabel38)
                                                .addGap(29, 29, 29)
                                                .addComponent(lblInforQuantityProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(lblInforPriceProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))))
                            .addGroup(pnlInforProductLayout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addComponent(jLabel57)))
                        .addGroup(pnlInforProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlInforProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblInforNameStore, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pnlInforProductLayout.createSequentialGroup()
                                    .addComponent(jScrollPane4)
                                    .addGap(106, 106, 106)))))
                    .addGroup(pnlInforProductLayout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addGroup(pnlInforProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInforProductLayout.createSequentialGroup()
                                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1))
                            .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(150, Short.MAX_VALUE))
        );
        pnlInforProductLayout.setVerticalGroup(
            pnlInforProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInforProductLayout.createSequentialGroup()
                .addGroup(pnlInforProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInforProductLayout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(lblInforCart))
                    .addGroup(pnlInforProductLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblInforImgProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(pnlInforProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel36)
                    .addComponent(jLabel47)
                    .addComponent(lblInforSlBuyProduct))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInforProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInforNameProduct)
                    .addComponent(lblInforNameStore))
                .addGroup(pnlInforProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInforProductLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlInforProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInforProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel38)
                                .addComponent(lblInforQuantityProduct))
                            .addComponent(jLabel37))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlInforProductLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblInforPriceProduct)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel57)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlInforProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInforProductLayout.createSequentialGroup()
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInforProductLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(23, 23, 23)))
                .addComponent(jScrollPane13)
                .addGap(12, 12, 12))
        );

        layerChinh.add(pnlInforProduct, "card11");

        jLabel32.setFont(new java.awt.Font("Showcard Gothic", 3, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 51, 0));
        jLabel32.setText("Manger  Account");

        tblListAccount.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "User", "Password", "Name", "Phone", "Email", "Address", "DayofBrith", "Gender", "Roles"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(tblListAccount);

        btnAdimndelete.setBackground(new java.awt.Color(255, 102, 102));
        btnAdimndelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/Trash-can-icon.png"))); // NOI18N
        btnAdimndelete.setText("Delete Account");
        btnAdimndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdimndeleteActionPerformed(evt);
            }
        });

        txtAdminSearch.setPlaceholderText("Search");

        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/Microsoft-Excel-2013-icon.png"))); // NOI18N
        btnExcel.setText("Export Excel");
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });

        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/Microsoft-Word-2013-icon.png"))); // NOI18N
        button1.setText("Export Word");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fastfood.img/pdf-icon.png"))); // NOI18N
        button2.setText("Export PDF");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAdminLayout = new javax.swing.GroupLayout(pnlAdmin);
        pnlAdmin.setLayout(pnlAdminLayout);
        pnlAdminLayout.setHorizontalGroup(
            pnlAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdminLayout.createSequentialGroup()
                .addGroup(pnlAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAdminLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(pnlAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 976, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAdminSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlAdminLayout.createSequentialGroup()
                        .addGap(201, 201, 201)
                        .addComponent(btnAdimndelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(btnExcel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        pnlAdminLayout.setVerticalGroup(
            pnlAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdminLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAdminSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(pnlAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdimndelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        layerChinh.add(pnlAdmin, "card12");

        pnlHistoryUsers.setLayout(new java.awt.GridLayout(0, 1, 10, 30));
        jScrollPane9.setViewportView(pnlHistoryUsers);

        javax.swing.GroupLayout pnlHistoryUserLayout = new javax.swing.GroupLayout(pnlHistoryUser);
        pnlHistoryUser.setLayout(pnlHistoryUserLayout);
        pnlHistoryUserLayout.setHorizontalGroup(
            pnlHistoryUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHistoryUserLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 988, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlHistoryUserLayout.setVerticalGroup(
            pnlHistoryUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHistoryUserLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 607, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        layerChinh.add(pnlHistoryUser, "card13");

        pnlHistoryStores.setLayout(new java.awt.GridLayout(0, 1, 30, 10));
        jScrollPane10.setViewportView(pnlHistoryStores);

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel40.setText("TurnOver :");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel41.setText("Tax : 8%");

        jLabel43.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel43.setText("WageRealistic :");

        cboHistory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboHistoryMouseClicked(evt);
            }
        });
        cboHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHistoryActionPerformed(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel44.setText("Print");
        jLabel44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel44MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlHistoryStoreLayout = new javax.swing.GroupLayout(pnlHistoryStore);
        pnlHistoryStore.setLayout(pnlHistoryStoreLayout);
        pnlHistoryStoreLayout.setHorizontalGroup(
            pnlHistoryStoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHistoryStoreLayout.createSequentialGroup()
                .addGroup(pnlHistoryStoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHistoryStoreLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 990, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlHistoryStoreLayout.createSequentialGroup()
                        .addGap(224, 224, 224)
                        .addComponent(cboHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlHistoryStoreLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40)
                .addGap(18, 18, 18)
                .addComponent(lblHistoryStoreTurnover, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jLabel41)
                .addGap(46, 46, 46)
                .addComponent(jLabel43)
                .addGap(18, 18, 18)
                .addComponent(lblHistoryStoreWage, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );
        pnlHistoryStoreLayout.setVerticalGroup(
            pnlHistoryStoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHistoryStoreLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(cboHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(pnlHistoryStoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHistoryStoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblHistoryStoreWage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlHistoryStoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblHistoryStoreTurnover, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        layerChinh.add(pnlHistoryStore, "card14");

        cbolTkStore.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbolTkStore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbolTkStoreActionPerformed(evt);
            }
        });

        pnlTkStores.setLayout(new java.awt.GridLayout(0, 1, 10, 22));
        jScrollPane2.setViewportView(pnlTkStores);

        javax.swing.GroupLayout pnlTkStoreLayout = new javax.swing.GroupLayout(pnlTkStore);
        pnlTkStore.setLayout(pnlTkStoreLayout);
        pnlTkStoreLayout.setHorizontalGroup(
            pnlTkStoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTkStoreLayout.createSequentialGroup()
                .addGroup(pnlTkStoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTkStoreLayout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(cbolTkStore, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlTkStoreLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 981, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        pnlTkStoreLayout.setVerticalGroup(
            pnlTkStoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTkStoreLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(cbolTkStore, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        layerChinh.add(pnlTkStore, "card15");

        lblBillImg.setText("jLabel49");

        jLabel49.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel49.setText("Name Sp :");

        lblBillName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblBillName.setText("jLabel50");

        jLabel51.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel51.setText("Sl mua :");

        lblBillSLMua.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblBillSLMua.setText("jLabel52");

        jLabel50.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel50.setText("Price SP :");

        lblBillPriceSP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblBillPriceSP.setText("jLabel52");

        jLabel52.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel52.setText("Money Bill :");

        lblBillMoney.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblBillMoney.setText("jLabel53");

        jLabel55.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel55.setText("Note :");

        lblBillNoteSp.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblBillNoteSp.setText("jLabel56");
        lblBillNoteSp.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel53.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 51, 51));
        jLabel53.setText("Thanh toán");
        jLabel53.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel53.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel53MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel53MouseEntered(evt);
            }
        });

        javax.swing.GroupLayout pnlBillLayout = new javax.swing.GroupLayout(pnlBill);
        pnlBill.setLayout(pnlBillLayout);
        pnlBillLayout.setHorizontalGroup(
            pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBillLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBillLayout.createSequentialGroup()
                        .addComponent(lblBillImg, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBillLayout.createSequentialGroup()
                        .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlBillLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlBillLayout.createSequentialGroup()
                                .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel49)
                                    .addComponent(jLabel51)
                                    .addComponent(jLabel50))
                                .addGap(34, 34, 34)
                                .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblBillName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblBillSLMua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblBillPriceSP, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 143, Short.MAX_VALUE)
                                .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel52)
                                    .addComponent(jLabel55))))
                        .addGap(50, 50, 50)
                        .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblBillMoney, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                            .addComponent(lblBillNoteSp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(116, 116, 116))))
        );
        pnlBillLayout.setVerticalGroup(
            pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBillLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lblBillImg, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBillLayout.createSequentialGroup()
                        .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel49)
                            .addComponent(lblBillName)
                            .addComponent(jLabel55))
                        .addGap(75, 75, 75)
                        .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel51)
                            .addComponent(lblBillSLMua)))
                    .addComponent(lblBillNoteSp, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(92, 92, 92)
                .addGroup(pnlBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(lblBillPriceSP)
                    .addComponent(jLabel52)
                    .addComponent(lblBillMoney))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );

        layerChinh.add(pnlBill, "card16");

        lblUserViewStoreImg.setText("jLabel56");

        jLabel56.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel56.setText("Name Store");

        lblUserViewStoreNameStore.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblUserViewStoreNameStore.setText("jLabel57");

        pnlUserViewStores.setLayout(new java.awt.GridLayout(0, 4, 35, 30));
        jScrollPane11.setViewportView(pnlUserViewStores);

        javax.swing.GroupLayout pnlUserViewStoreLayout = new javax.swing.GroupLayout(pnlUserViewStore);
        pnlUserViewStore.setLayout(pnlUserViewStoreLayout);
        pnlUserViewStoreLayout.setHorizontalGroup(
            pnlUserViewStoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUserViewStoreLayout.createSequentialGroup()
                .addGroup(pnlUserViewStoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUserViewStoreLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(lblUserViewStoreImg, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addGroup(pnlUserViewStoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUserViewStoreNameStore, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlUserViewStoreLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 945, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        pnlUserViewStoreLayout.setVerticalGroup(
            pnlUserViewStoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUserViewStoreLayout.createSequentialGroup()
                .addGroup(pnlUserViewStoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUserViewStoreLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel56)
                        .addGap(18, 18, 18)
                        .addComponent(lblUserViewStoreNameStore))
                    .addGroup(pnlUserViewStoreLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblUserViewStoreImg, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                .addContainerGap())
        );

        layerChinh.add(pnlUserViewStore, "card17");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(layerChinh))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(layerChinh)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fillCboTypeProductHome() {

        DefaultComboBoxModel model = (DefaultComboBoxModel) cboTypeProductHome.getModel();
        model.removeAllElements();
        List<TypeProduct> list = new ArrayList<>();
        list.add(new TypeProduct(0, "All"));
        list.addAll(typeDao.getAllDate());
        list.forEach(type -> {
            model.addElement(type);
        });
    }

    private void fillSP(Object a) {
        pnlProduct.removeAll();
        List<Product> list;
        String user = Authention.authention == null ? "" : Authention.authention.getUser();
        try {
            int i = (int) a;
            if (i > 0) {
                list = productDao.selectByTypeProduct(user, i);
            } else {
                list = productDao.selectByName(user, "");
            }
        } catch (Exception e) {
            list = productDao.selectByName(user, (String) a);
        }

        for (Product product : list) {
            UiProduct p = new UiProduct(product);
            p.setSizeImage(220, 130);
            p.panel();
            p.cart();
            p.moveLblImg("buy.png");
            p.lblCart.addMouseListener(mouseAddCart(p.getProduct()));
            p.chkLike.addActionListener(actionLikeProduct(p));
            p.lblImg.addMouseListener(mouseViewProduct(p.getProduct()));
            pnlProduct.add(p.pnl);
        }
        int sl = 8 - list.size();
        for (int i = 0; i < sl; i++) {
            UiProduct p = new UiProduct(new Product(i, i, "Ao " + i, i * 100, "avt.jpg"));
//            p.setSizeImage(220, 125);
//            p.panel();
            pnlProduct.add(p.pnl);
        }
    }

    private void layer(JLayeredPane layer, JComponent comp) {
        layer.removeAll();
        layer.add(comp);
        layer.repaint();
        layer.revalidate();
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Thread t = new Thread(this);
        t.start();
    }//GEN-LAST:event_formWindowOpened

    private void lblQRcoreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQRcoreMouseClicked
        try {
//            String data = "https://www.facebook.com/profile.php?id=100065380270425";
//            QRCodeWriter qrCodeWriter = new QRCodeWriter();
//            BitMatrix matrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);
//
//            // Write to file image
//            String outputFile = "./resource/image.png";
//            Path path = FileSystems.getDefault().getPath(outputFile);2
//            MatrixToImageWriter.writeToPath(matrix, "PNG", path);

            // new frame
            JDialog jf = new JDialog(this, true);
            ImageIcon icon = new ImageIcon("./resource/image.png");
            JLabel labelqr = new JLabel(icon);
            labelqr.setSize(200, 200);
            jf.add(labelqr);
            jf.setSize(200, 200);
            jf.setLocationRelativeTo(null);
            jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jf.setUndecorated(false);
            jf.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_lblQRcoreMouseClicked

    public void fillStore() {
        pnlStore.removeAll();
        List<Product> list = storeDao.getProductOnlyUser(Authention.authention.getUser());
        for (Product product : list) {
            UiProduct p = new UiProduct(product);
            p.setSizeImage(290, 200);
            p.panel();
            p.btnUpdate = new JButton("Update");
            p.btnDelete = new JButton("Delete");
            btnStoreUpdate(p);
            btnStoreDelete(p);
            p.store();
            pnlStore.add(p.pnl);
        }
        int sl = 6 - list.size();
        for (int i = 0; i < sl; i++) {
            UiProduct p = new UiProduct(new Product(i, i, "Ao " + i, i * 100, "avt.jpg"));
//            p.panel();
            pnlStore.add(p.pnl);
        }
    }

    public void btnStoreUpdate(UiProduct ui) {
        ui.btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listGetBackAdd(layerViewMyStore, pnlViewInsertProduct);
                setProductInsert(ui.getProduct());
                Msg.clearLabel(lblIPErrorNote, lblIPErrorName, lblIPErrorPrice, lblIPErrorQuantity);
                Msg.setIconLabel(Ximage.getAppImagelcon(""), lblIPIconAddress, lblIPIconName, lblIPIconPrice, lblIPIconQuantity);
            }
        });
    }

    public void btnStoreDelete(UiProduct ui) {
        ui.btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layer(ui.layer, ui.pnlAo);
                ui.btnUpdate.setEnabled(false);
                ui.btnDelete.setEnabled(false);
                productDao.delete(ui.getProduct().getIdProduct());
                ui.pnl.remove(ui.pnlName);
            }
        });
    }

    private void fillCart() {
        pnlCart.removeAll();
        lblCartMoney.setText("0");
//        hashCart.clear();
        List<Cart> list = cartDAO.seletecAll(Authention.authention.getUser());
        for (Cart c : list) {
            UiCart uc = new UiCart(c);
            uc.setHeighPanel(120);
            uc.panel();
            uc.pnl.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            chkCartAction(uc);
            uc.lblImg.addMouseListener(mouseViewProduct(uc.getC().getProduct()));
            btnQuantityCart(uc);
            labelCartAction(uc);
            pnlCart.add(uc.pnl);
        }
    }

    Map<Integer, Cart> hashCart = new HashMap<>();

    private void cartPayAll() {
        double tong = 0;
        for (Map.Entry<Integer, Cart> entry : hashCart.entrySet()) {
            tong += entry.getValue().getPayMoney();
        }
        lblCartMoney.setText(FormatNumber.withLargeIntegers(tong, 2));
        lblCartMoney.setToolTipText(String.valueOf(tong));
    }

    private void fillConfirm() throws SQLException {
        pnlConfirm.removeAll();
        String sql = "{CALL viewConfirm}";
        ResultSet rs = JDBCHelper.query(sql);
        int count = 0;
        while (rs.next()) {
            UserStore usS = new UserStore(rs.getString(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5));
            UiConfirm uc = new UiConfirm(usS);
            uc.setSize(100);
            uc.panel();
            uc.lblImg.addMouseListener(mouseViewUser(usDao.selectByID(usS.getUser())));
            mouseLblConfirm(uc);
            pnlConfirm.add(uc.pnl);
            count++;
        }
        int sl = 5 - count;
        if (sl > 0) {
            for (int i = 0; i < sl; i++) {
                UserStore usS = new UserStore("" + "", "", "",
                        "", "");
                UiConfirm uc = new UiConfirm(usS);
                uc.setSize(100);
                pnlConfirm.add(uc.pnl);
            }
        }
    }

    private void mouseLblConfirm(UiConfirm uc) {
        uc.lblOk.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!uc.lblKOk.getText().equals("") && !uc.lblOk.getText().equals("")) {
                    uc.lblOk.setEnabled(false);
                    uc.lblKOk.setText("");
                    uc.lblKOk.setIcon(Ximage.getAppImagelcon(""));
                    sttmCart(uc, 1);
                }
            }
        });

        uc.lblKOk.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!uc.lblOk.getText().equals("") && !uc.lblKOk.getText().equals("")) {
                    uc.lblKOk.setEnabled(false);
                    uc.lblOk.setText("");
                    uc.lblOk.setIcon(Ximage.getAppImagelcon(""));
                    sttmCart(uc, 0);
                }
            }
        });
    }

    private void sttmCart(UiConfirm uc, int i) {
        String sql = "{CALL confirm(?,?)}";
        JDBCHelper.update(sql, uc.getUsS().getUser(), i);
    }

    private void chkCartAction(UiCart uc) {
        if (!uc.getC().isExistss()) {
            uc.chk.setSelected(false);
            uc.chk.setEnabled(false);
        }
        uc.chk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (uc.chk.isSelected()) {
                    uc.lblDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    uc.lblTT.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    hashCart.put(uc.getC().getIdProduct(), uc.getC());
                } else {
                    uc.lblTT.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    uc.lblDelete.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    hashCart.remove(uc.getC().getIdProduct());
                }
                cartPayAll();
            }
        });
    }

    private void btnQuantityCart(UiCart c) {
        c.btnDecrease.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.decrease();
                cartDAO.update(Authention.authention.getUser(), c.getC().getIdProduct(), c.getC().getQuantityCart());
                if (Integer.parseInt(c.lblSL.getText()) <= 0) {
                    c.btnDecrease.setEnabled(false);
                }
                cartPayAll();
            }
        });

        c.btnIncrease.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.increase();
                cartDAO.update(Authention.authention.getUser(), c.getC().getIdProduct(), c.getC().getQuantityCart());
                c.btnDecrease.setEnabled(true);
                cartPayAll();
            }
        });
    }

    private void labelCartAction(UiCart uc) {
        uc.lblTT.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (uc.chk.isSelected()) {
                    if (uc.getC().getQuantityCart() == 0) {
                        return;
                    }
                    if (uc.getC().isExistss()) {
                        listGetBackAdd(layerChinh, pnlBill);
                        setCart(uc.getC());
                    } else {
                        Message.getNotify(null, "You couldn't buy this product Because this product has deleted", "Message");
                    }
                }
            }

        });

        uc.lblDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (uc.chk.isSelected()) {
                    if (Message.getconfirm(null, "You want to delete this product out from your cart", "Message") == JOptionPane.YES_OPTION) {
                        cartDAO.delete(Authention.authention.getUser(), uc.getC().getIdProduct());
                        Message.getNotify(null, "You deleted " + uc.getC().getName() + " product out from your product", "Message");
                        uc.chk.setSelected(false);
                        uc.chk.setEnabled(false);
                    }
                }
            }
        });
    }

    private void lblCartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCartMouseClicked
        // TODO add your handling code here:
        if (!requestLogin()) {
            return;
        }
        listGetBackAdd(layerChinh, pnlViewCart);
        fillCart();
    }//GEN-LAST:event_lblCartMouseClicked

    public void informationUserLabel(User user) {
        lblViewImg.setIcon(Ximage.reSizeImgae2(Ximage.read(user.getImg()), lblViewImg));
    }
    private void lblAvtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAvtMouseClicked
        if (!requestLogin()) {
            return;
        }
        viewInforUser(Authention.authention);
        viewLabel();
    }//GEN-LAST:event_lblAvtMouseClicked


    private void lblRegisterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegisterMouseClicked
        listGetBackAdd(layerChinh, pnlRegister);
    }//GEN-LAST:event_lblRegisterMouseClicked

    private void txtReConfrimPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReConfrimPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReConfrimPassActionPerformed

    private void btnLoginBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginBackActionPerformed
        listGetBackAdd(layerChinh, pnlLogin);
    }//GEN-LAST:event_btnLoginBackActionPerformed

    private void txtChangConfirmPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChangConfirmPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtChangConfirmPassActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        layer(layerChinh, pnlUpdateInformation);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeActionPerformed
        listGetBackAdd(layerChinh, pnlChangePass);
    }//GEN-LAST:event_btnChangeActionPerformed

    public User getUserUpdate() {
        return new User(Authention.authention.getUser(), Authention.authention.getPass(),
                txtUsName.getText(), txtUsPhone.getText(),
                txtUsEmail.getText(), txtUsAddress.getText(),
                lblUsImage.getToolTipText(), txtUsBirth.getText(),
                Authention.authention.getRoles(), rdoUsMale.isSelected());
    }
    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        getLogin();
        loadMoney();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        getRegister();
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void lblViewUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblViewUpdateMouseClicked
        getTextFiledinformationView(Authention.authention);
    }//GEN-LAST:event_lblViewUpdateMouseClicked

    private void lblUsImageMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsImageMouseEntered
        lblUsImage.setIcon(Ximage.reSizeImgae2(Ximage.read("changeavt.jpg"), lblUsImage));
    }//GEN-LAST:event_lblUsImageMouseEntered

    private void lblUsImageMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsImageMousePressed

    }//GEN-LAST:event_lblUsImageMousePressed

    private void lblUsImageMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsImageMouseExited
        lblUsImage.setIcon(Ximage.reSizeImgae2(Ximage.read(lblUsImage.getToolTipText()), lblUsImage));
    }//GEN-LAST:event_lblUsImageMouseExited

    private void txtReCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReCodeActionPerformed

    }//GEN-LAST:event_txtReCodeActionPerformed

    Timer timerRetxtCode;
    private void txtReCodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtReCodeKeyTyped
        timerRetxtCode = new Timer(12, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtReCode.getText().equals(verify)) {
                    verify = "-1";
                    Authention.authention = new User(txtReUser.getText(), txtRePass.getText(), txtReEmail.getText());
                    Authention.authention.setRoles("user");
                    usDao.insert(Authention.authention);
                    layer(layerChinh, pnlViewinformation);
                    viewLabel();
                    viewInforUser(Authention.authention);
//                    getViewInformtion(Authention.authention);
                    Message.getNotify(null, "Register successfully", "Message");
                    getQrcode(txtReUser.getText());
                    listGetBack.clear();
                    listGetBackAdd(layerChinh, pnlViewinformation);
                }
                timerRetxtCode.stop();
            }
        });
        timerRetxtCode.start();
    }//GEN-LAST:event_txtReCodeKeyTyped

    private void chkShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowActionPerformed
        if (chkShow.isSelected()) {
            txtChangCurrentPass.setEchoChar((char) 0);
            txtChangNewPass.setEchoChar((char) 0);
            txtChangConfirmPass.setEchoChar((char) 0);
        } else {
            txtChangCurrentPass.setEchoChar('*');
            txtChangNewPass.setEchoChar('*');
            txtChangConfirmPass.setEchoChar('*');
        }
    }//GEN-LAST:event_chkShowActionPerformed

    private void btnChangePassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePassActionPerformed
        getChangePassword();
    }//GEN-LAST:event_btnChangePassActionPerformed

    private void lblViewLogOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblViewLogOutMouseClicked
        Authention.authention = null;
        setAvtroot();
        listGetBack.clear();
        lblMoney.setText("");
        listGetBackAdd(layerChinh, pnlHome);
    }//GEN-LAST:event_lblViewLogOutMouseClicked

    private void lblUsImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsImageMouseClicked
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            Ximage.save(file);
            ImageIcon icon = Ximage.reSizeImgae2(Ximage.read(file.getName()), lblUsImage);
            lblUsImage.setIcon(icon);
            lblUsImage.setToolTipText(file.getName());
        }
    }//GEN-LAST:event_lblUsImageMouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        // TODO add your handling code here:
        Authention.authention = getUserUpdate();
        usDao.update(Authention.authention);
        lblAvt.setIcon(Msg.avt(Authention.authention.getImg(), lblAvt));
        viewInforUser(Authention.authention);
        layer(layerChinh, pnlViewinformation);
        listGetBackAdd(layerChinh, pnlViewinformation);
    }//GEN-LAST:event_jLabel16MouseClicked

    private void pnlViewUserStoreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlViewUserStoreMouseClicked
        // TODO add your handling code here:
        try {
            String x = Message.getprompt(this, "Tại sao bạn muốn bán hàng", "Why?");
            if (x.length() <= 30) {
                Message.getNotify(this, "Lý do phải trên 30 ký tự", "Thông báo");
                return;
            }
            String sql = "insert into USERSTORE values (?,?)";
            JDBCHelper.update(sql, Authention.authention.getUser(), x);
            Message.getNotify(this, "Gửi phản hồi thành công. Chúng tôi sẽ thông tin cho bạn sớm nhất", "Message");
        } catch (Exception e) {

        }
    }//GEN-LAST:event_pnlViewUserStoreMouseClicked

    private void lblViewNameStoreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblViewNameStoreMouseClicked
        // TODO add your handling code here:
        if (Authention.authention != null) {
            if (Authention.authention.getUser().equals(lblViewImg.getToolTipText())) {
                try {
                    String name = Message.getprompt(this, "Bạn muốn sửa tên thành?", "Message");
                    storeDao.update(Authention.authention.getUser(), name);
                    lblViewNameStore.setText(name);
                } catch (Exception e) {

                }
            }
        }
    }//GEN-LAST:event_lblViewNameStoreMouseClicked

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        // TODO add your handling code here:
        setProductInsert(new Product(0, storeDao.getIdStoreByUser(Authention.authention.getUser())));
        Msg.clearLabel(lblIPErrorName, lblIPErrorNote, lblIPErrorPrice, lblIPErrorQuantity);
        Msg.setIconLabel(Ximage.getAppImagelcon(""), lblIPIconAddress, lblIPIconName, lblIPIconPrice, lblIPIconQuantity);
        layer(layerViewMyStore, pnlViewInsertProduct);
    }//GEN-LAST:event_jLabel18MouseClicked

    private void lblIPImgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIPImgMouseClicked
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            Ximage.save(file);
            ImageIcon icon = Ximage.reSizeImgae2(Ximage.read(file.getName()), lblIPImg);
            lblIPImg.setIcon(icon);
            lblIPImg.setToolTipText(file.getName());
        }
    }//GEN-LAST:event_lblIPImgMouseClicked

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        // TODO add your handling code here:
        fillStore();
        layer(layerViewMyStore, jScrollpnlStore);
    }//GEN-LAST:event_jLabel19MouseClicked

    
    private boolean checkInsertProduct(){
        Valid vl = new Valid();
        vl.isEmpty(txtIPName, lblIPErrorName, "Name");
        vl.multiBiggerAndLower(txtIPPrice, lblIPErrorPrice, "Price", 1000.0, 1e6);
        vl.multiBiggerAndLower(txtIPQuantity, lblIPErrorQuantity, "Quantity", 10, 10000);
        vl.multiLength(txtIPNote, lblIPErrorNote, "Address", 30);
        if (lblIPImg.getToolTipText() == null) {
            Message.getNotify(this, "Bạn chưa chọn ảnh", "Message");
            return false;
        }
        return vl.getLoi().equals("");
    }
    private void jLabel31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel31MouseClicked
        // TODO add your handling code here:
        if(!checkInsertProduct()){
            return;
        }
        Product p = getProductInsert();
        if (p.getIdProduct() == 0) {
            productDao.insert(p);
        } else {
            productDao.update(p);
        }
        fillStore();
        layer(layerViewMyStore, jScrollpnlStore);
    }//GEN-LAST:event_jLabel31MouseClicked

    private void lblConfirmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblConfirmMouseClicked
        if (!checkLogin()) {
            return;
        }

        if (!Authention.authention.getRoles().equals("admin")) {
            Message.getNotify(this, "You must be a admin to use this function", "Required");
            return;
        }

        listGetBackAdd(layerChinh, pnlViewConfrim);
        try {
            fillConfirm();
        } catch (SQLException ex) {
            Logger.getLogger(ApplicationFastFood.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lblConfirmMouseClicked

    private void lblMyStoreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMyStoreMouseClicked
        if (!requestLogin()) {
            return;
        }
        if (Authention.authention.getRoles().equals("store")) {
            fillStore();
            listGetBackAdd(layerViewMyStore, jScrollpnlStore);
        } else {
            Message.getNotify(this, "You must be a store to use this function", "Required");
        }
    }//GEN-LAST:event_lblMyStoreMouseClicked

    private void lblHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHomeMouseClicked
        listGetBackAdd(layerChinh, pnlHome);
        fillCboTypeProductHome();
        this.validate();
    }//GEN-LAST:event_lblHomeMouseClicked

    private void lblManagerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblManagerMouseClicked
        if (Authention.authention == null) {
            Message.getNotify(this, "Only for Admin", "Warring");
        } else {
            if (Authention.authention.getRoles().equals("admin")) {
                listGetBackAdd(layerChinh, pnlAdmin);

            } else {
                Message.getNotify(this, "You not role", "Role");
            }
        }
    }//GEN-LAST:event_lblManagerMouseClicked
    int location = -1;
    private void btnAdimndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdimndeleteActionPerformed
        location = tblListAccount.getSelectedRow();
        if (location != -1) {
            if (Message.getconfirm(this, "Do you want to delete this Account?", "Delete") == JOptionPane.YES_OPTION) {
                usDao.delete((String) tblListAccount.getValueAt(location, 0));
                Message.getNotify(this, "Delete  Successfull", verify);
                fillTableAdmin();
                location = -1;
            }
        }
    }//GEN-LAST:event_btnAdimndeleteActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void lblHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHomeMouseEntered
    }//GEN-LAST:event_lblHomeMouseEntered

    private void lblHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHomeMouseExited
    }//GEN-LAST:event_lblHomeMouseExited

    private void lblGetBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGetBackMouseClicked
        // TODO add your handling code here:
        listGetBack();
    }//GEN-LAST:event_lblGetBackMouseClicked

    private void lblInforCartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInforCartMouseClicked
        // TODO add your handling code here:
        String[] arr = lblInforCart.getToolTipText().split(",");
        addCart(new Product(
                Integer.parseInt(arr[0]),
                lblInforNameProduct.getText(),
                arr[1].equals("1")
        )
        );
    }//GEN-LAST:event_lblInforCartMouseClicked

    private void lblViewInforAddTypeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblViewInforAddTypeMouseClicked
        // TODO add your handling code here:
        try {
            String name = Message.getprompt(this, "Input type product", "Message");
            typeDao.insert(name);
            Message.getNotify(this, "Add type product new successfully", "Message");
        } catch (Exception e) {
            Message.getNotify(this, "You aren't yet input name product", "Message");
        }
    }//GEN-LAST:event_lblViewInforAddTypeMouseClicked

    private void historyUser() {
        pnlHistoryUsers.removeAll();
        List<Bill> list = billDAO.user(Authention.authention.getUser());
        for (Bill bill : list) {
            UiBill ub = new UiBill(bill);
            ub.setHeighPanel(127);
            ub.panel();
            ub.user();
            ub.lblImg.addMouseListener(mouseViewProduct(productDao.selectByID(ub.getBill().getIdProduct())));
            ub.lblCart.addMouseListener(
                    mouseAddCart(productDao.selectByID(ub.getBill().getIdProduct()))
            );
            pnlHistoryUsers.add(ub.pnl);
        }

        int sl = 4 - list.size();
        for (int i = 0; i < sl; i++) {
            UiBill ub = new UiBill(new Bill());
            ub.setHeighPanel(127);
            pnlHistoryUsers.add(ub.pnl);
        }
    }

    private void lblHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHistoryMouseClicked
        // TODO add your handling code here:
        historyUser();
        listGetBackAdd(layerChinh, pnlHistoryUser);

    }//GEN-LAST:event_lblHistoryMouseClicked

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        String[] column = {"Stt", "Username", "Password", "Name", "Phone", "Email", "Address", "Day of Birth", "Gender", "Roles"};
        try {

            XSSFWorkbook wordbook = new XSSFWorkbook();
            XSSFSheet sheet = wordbook.createSheet("List Account");
            Cell cell;
            XSSFRow row;
            row = sheet.createRow(3);
            for (int i = 0; i < column.length; i++) {
                cell = row.createCell(i, CellType.STRING);
                cell.setCellValue(column[i]);
            }
            List<User> listexecl = usDao.getAllDate();
            for (int i = 0; i < listexecl.size(); i++) {
                row = sheet.createRow(4 + i);
                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(i + 1);
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(listexecl.get(i).getUser());
                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(listexecl.get(i).getPass());
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(listexecl.get(i).getName());
                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(listexecl.get(i).getPhone());
                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(listexecl.get(i).getEmail());
                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(listexecl.get(i).getAddress());
                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(listexecl.get(i).getBirth());
                cell = row.createCell(8, CellType.STRING);
                cell.setCellValue(listexecl.get(i).isGender() ? "Male" : "FeMale");
                cell = row.createCell(9, CellType.STRING);
                cell.setCellValue(listexecl.get(i).getRoles());
            }
            File fexcel = new File("resource", "fileexcel.xlsx");
            if (fexcel.exists()) {
                fexcel.delete();
                fexcel.createNewFile();
            }
            try ( FileOutputStream out = new FileOutputStream(fexcel)) {
                wordbook.write(out);
            }
            Message.getNotify(this, "Export File Excel SuccessFull", "Success");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnExcelActionPerformed

    List<Bill> listHistoryStore = new ArrayList<>();

    private void historyStore() {
        pnlHistoryStores.removeAll();
        listHistoryStore.clear();
        String x = (String) cboHistory.getSelectedItem();
        String yearMonth = x.equals("All") ? "" : x;
        listHistoryStore = billDAO.store(Authention.authention.getUser(), yearMonth);
        Double money = 0.0;
        for (Bill bill : listHistoryStore) {
            money += bill.getMoney() * bill.getQuantity();
            UiBill ub = new UiBill(bill);
            ub.setHeighPanel(110);
            ub.panel();
            ub.store();
            ub.lblImg.addMouseListener(mouseViewProduct(productDao.selectByID(ub.getBill().getIdProduct())));
            lblHistoryStoreUpdateProduct(ub);
            ub.lblUser.addMouseListener(mouseViewUser(usDao.selectByID(ub.getBill().getUser())));
            pnlHistoryStores.add(ub.pnl);
        }

        int sl = 4 - listHistoryStore.size();
        for (int i = 0; i < sl; i++) {
            UiBill ub = new UiBill(new Bill());
            ub.setHeighPanel(110);
            pnlHistoryStores.add(ub.pnl);
        }

        lblHistoryStoreTurnover.setText(FormatNumber.withLargeIntegers(money, 1));
        lblHistoryStoreWage.setText(FormatNumber.withLargeIntegers(money * 0.92, 2));
    }

    private void lblHistoryStoreUpdateProduct(UiBill ub) {
        ub.lblUpdate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                listGetBackAdd(layerViewMyStore, pnlViewInsertProduct);
                setProductInsert(productDao.selectByID(ub.getBill().getIdProduct()));
                Msg.clearLabel(lblIPErrorNote, lblIPErrorName, lblIPErrorPrice, lblIPErrorQuantity);
                Msg.setIconLabel(Ximage.getAppImagelcon(""), lblIPIconAddress, lblIPIconName, lblIPIconPrice, lblIPIconQuantity);
            }

        });
    }

    private void fillCboYearMothHistoryStore(JComboBox combo) {
        combo.removeAllItems();
        List<Object[]> list = billDAO.yearMonth(Authention.authention.getUser());
        combo.addItem("All");
        for (Object[] objects : list) {
            combo.addItem((String) objects[0]);
        }
    }
    private void lblHistoryStoreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHistoryStoreMouseClicked
        listGetBackAdd(layerChinh, pnlHistoryStore);
        fillCboYearMothHistoryStore(cboHistory);
    }//GEN-LAST:event_lblHistoryStoreMouseClicked

    private void cboHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHistoryActionPerformed
        Object x = cboHistory.getSelectedItem();
        if (x != null) {
            historyStore();
        }
    }//GEN-LAST:event_cboHistoryActionPerformed

    private void cboHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboHistoryMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cboHistoryMouseClicked

    private void cboTypeProductHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTypeProductHomeActionPerformed
        // TODO add your handling code here:
        TypeProduct type = (TypeProduct) cboTypeProductHome.getSelectedItem();
        if (type != null) {
            fillSP(type.getIdType());
        }
    }//GEN-LAST:event_cboTypeProductHomeActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        fillSP(txtSearch.getText());
    }//GEN-LAST:event_btnSearchActionPerformed

    private void jLabel44MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseClicked
        // TODO add your handling code here:
        try {
            XSSFWorkbook wordBook = new XSSFWorkbook();
            XSSFSheet sheet = wordBook.createSheet("sadf");
            XSSFRow row = null;
            Cell cell = null;
            row = sheet.createRow(3);
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("ID Bill");

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("User");

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("ID Product");

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Name product");

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Date purchase");

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Quantity");

            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue("Price");

            for (int i = 0; i < listHistoryStore.size(); i++) {
                Bill bill = listHistoryStore.get(i);
                row = sheet.createRow(4 + i);
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(bill.getIdBill());

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(bill.getUser());

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(bill.getIdProduct());

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(bill.getName());

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(bill.getDatePurchase());

                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(bill.getQuantity());

                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(bill.getMoney());
            }
//            File dst = new File("resource", (String)cboHistory.getSelectedItem()+"xlsx");
//            if (!dst.getParentFile().exists()) {
//                dst.getParentFile().mkdirs();
//            }
            File f = new File("resource", Authention.authention.getUser() + "_" + (String) cboHistory.getSelectedItem() + ".xlsx");
            try {
                FileOutputStream fis = new FileOutputStream(f);
                wordBook.write(fis);
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jLabel44MouseClicked

    private void jLabel46MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MouseClicked
        // TODO add your handling code here:
        new Spam().setVisible(true);
    }//GEN-LAST:event_jLabel46MouseClicked

    private void jLabel48MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseClicked
        // TODO add your handling code here:
        listGetBackAdd(layerChinh, pnlTkStore);
        fillCboYearMothHistoryStore(cbolTkStore);
    }//GEN-LAST:event_jLabel48MouseClicked

    List<Bill> listBillTK = new ArrayList<>();

    private void fillTKStore() {
        pnlTkStores.removeAll();
        listBillTK.clear();
        String x = (String) cbolTkStore.getSelectedItem();
        String yearMonth = x.equals("All") ? "" : x;
        listBillTK = billDAO.tkStore(Authention.authention.getUser(), yearMonth);
        for (Bill bill : listBillTK) {
            UiTkBill utb = new UiTkBill(bill);
            utb.setHeighPanel(90);
            utb.panel();
            utb.lblImg.addMouseListener(mouseViewProduct(productDao.selectByID(utb.getBill().getIdProduct())));
            pnlTkStores.add(utb.pnl);
        }

        int sl = 5 - listBillTK.size();
        for (int i = 0; i < sl; i++) {
            UiTkBill utb = new UiTkBill(new Bill());
            pnlTkStores.add(utb.pnl);
        }
    }

    private void cbolTkStoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbolTkStoreActionPerformed
        // TODO add your handling code here:
        if (cbolTkStore.getSelectedItem() != null) {
            fillTKStore();
        }
    }//GEN-LAST:event_cbolTkStoreActionPerformed

    private void jLabel53MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel53MouseClicked

        if (Message.getconfirm(null, "You really want to buy this product ?", "Message") == JOptionPane.YES_OPTION) {
            if (pay(lblBillMoney.getToolTipText())) {
                Bill bill = getCart();
                Object[] obj = billDAO.insert(bill);
                if (String.valueOf(obj[0]).equals("1")) {
                    Message.getNotify(this, "Buy Product successfull", "Success");
                    money.update(Float.parseFloat(lblBillMoney.getToolTipText()), Authention.authention.getUser());
                    loadMoney();
                    if (Message.getconfirm(this, "Do you want to print bill product", "Bill") == JOptionPane.YES_OPTION) {
                        printBill();
                    }
                } else {
                    Message.getNotify(this, "You cannot buy the product because the quantity is not enough", "Warring");
                }
            }

        }
    }//GEN-LAST:event_jLabel53MouseClicked

    private void lblInforNameStoreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInforNameStoreMouseClicked
        // TODO add your handling code here:
        listGetBackAdd(layerChinh, pnlUserViewStore);
        fillUserViewStore(Integer.parseInt(lblInforNameStore.getToolTipText()));
    }//GEN-LAST:event_lblInforNameStoreMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (!checkLogin()) {
            return;
        }

        if (!txtCommentOne.getText().isEmpty()) {
            String x[] = lblInforCart.getToolTipText().split(",");
            commentOneDao.insert(new CommentOne(Authention.authention.getUser(), Integer.parseInt(x[0]), txtCommentOne.getText(), txtCommentOne.getToolTipText()));
            LikeCommentOne like = likeCommentOneDAO.selectOne(Authention.authention.getUser(), Integer.parseInt(x[0]));
            pnlInforCommentOneProduct.add(uiComment(like), 0);
            pnlInforCommentOneProduct.revalidate();
            Message.getNotify(this, "Comment successfully", "Message");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed

        try ( XWPFDocument document = new XWPFDocument()) {
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun title = paragraph.createRun();
            title.setFontFamily("Eras Medium ITC");
            title.setFontSize(18);
            title.setBold(true);
            title.setText(" List Account");
            List<User> list = usDao.getAllAccount();
            for (User e : list) {
                XWPFParagraph paragraph1 = document.createParagraph();
                XWPFRun run1 = paragraph1.createRun();
                run1.setText("Username :" + e.getUser());
                XWPFParagraph paragraph2 = document.createParagraph();
                XWPFRun run2 = paragraph2.createRun();
                run2.setText("Password :" + e.getPass());
                XWPFParagraph paragraph3 = document.createParagraph();
                XWPFRun run3 = paragraph3.createRun();
                run3.setText("Name :  " + e.getName());
                XWPFParagraph paragraph4 = document.createParagraph();
                XWPFRun run4 = paragraph4.createRun();
                run4.setText("Phone : " + e.getPhone());
                XWPFParagraph paragraph5 = document.createParagraph();
                XWPFRun run5 = paragraph5.createRun();
                run5.setText("Email :'" + e.getEmail());
                XWPFParagraph paragraph6 = document.createParagraph();
                XWPFRun run6 = paragraph6.createRun();
                run6.setText("Address : " + e.getAddress());
                XWPFParagraph paragraph7 = document.createParagraph();
                XWPFRun run7 = paragraph7.createRun();
                run7.setText("Day of Birth : " + e.getBirth());
                XWPFParagraph paragraph8 = document.createParagraph();
                XWPFRun run8 = paragraph8.createRun();
                run8.setText(e.isGender() ? "Gender  : Male" : "Gender : Female");
                XWPFParagraph paragraph9 = document.createParagraph();
                XWPFRun run9 = paragraph9.createRun();
                run9.setText("Roles : " + e.getRoles());
            }

            File file = new File("resource", "listword.docx");
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }
            try (
                     FileOutputStream out = new FileOutputStream(file)) {
                document.write(out);
            }
        } catch (IOException ex) {
            Logger.getLogger(ApplicationFastFood.class.getName()).log(Level.SEVERE, null, ex);
        }

        Message.getNotify(this,
                "Export Word Success", "Notify");

    }//GEN-LAST:event_button1ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        MessageFormat h = new MessageFormat("List Account");
        MessageFormat f = new MessageFormat("End");
        try {
            tblListAccount.print(JTable.PrintMode.NORMAL, h, f);
        } catch (PrinterException ex) {
            Logger.getLogger(ApplicationFastFood.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_button2ActionPerformed

    private void lblUserQrcodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUserQrcodeMouseClicked
        JFileChooser filechooser = new JFileChooser();
        int result = filechooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = filechooser.getSelectedFile();
            try {
                //  CHUA XU LY
//                File f2 = new File("resource", "user1.png");
//                FileInputStream input = new FileInputStream(f2);
//                int length = input.available();
//                byte[] data = new byte[length];
//                input.read(data);
//                FileOutputStream output = new FileOutputStream(file);
//                output.write(data);
//                input.close();
//                output.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_lblUserQrcodeMouseClicked

    private void lblMapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMapMouseClicked
        new GoogleMap().setVisible(true);
    }//GEN-LAST:event_lblMapMouseClicked

    private void jLabel58MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel58MouseClicked
        // TODO add your handling code here:
        if(pay(lblCartMoney.getToolTipText())){
           return; 
        }
        Connection con = null;
        PreparedStatement st = null;
        
        try {
            String sql = "{CALL insert_Bill(?,?,?,?)}";
            con = JDBCHelper.getConnection();
            st = con.prepareCall(sql);
            for (Map.Entry<Integer, Cart> entry : hashCart.entrySet()) {
                st.setString(1, Authention.authention.getUser());
                st.setInt(2, entry.getValue().getIdProduct());
                st.setInt(3, entry.getValue().getQuantityCart());
                st.setDouble(4, entry.getValue().getMoney());
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    if (rs.getString(1).equals("0")) {
                        Message.getNotify(this, "Product " + entry.getValue().getName() + " is not enough quantity", "Message");
                        con.rollback();
                        break;
                    }
                }
            }
            Message.getNotify(this, "Buy successfully", "Message");
        } catch (Exception e) {

        } finally {
            try {
                st.close();
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ApplicationFastFood.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ApplicationFastFood.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jLabel58MouseClicked

    private void jLabel53MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel53MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel53MouseEntered

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        new ScannerQrcode().setVisible(true);
    }//GEN-LAST:event_button3ActionPerformed

    private void jLabel61MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel61MouseClicked

        if(!requestLogin()){
            return;
        }
        String naptien = JOptionPane.showInputDialog(this, "Plese input Money : ");
        if (naptien != null) {
            try {
                float t = Float.parseFloat(naptien);
                if (t <= 0) {
                    Message.getNotify(this, "Money > 0", "Warring");
                } else {
                    MoneyDao m = new MoneyDao();
                    Float a = Float.parseFloat(lblMoney.getText()) + t;
                    m.update(a, Authention.authention.getUser());
                    Message.getNotify(this, "Thanh Cong", "success");
                    loadMoney();
                }
            } catch (Exception e) {
                Message.getNotify(this, "Please inuput number", "Warring");
            }
        }
    }//GEN-LAST:event_jLabel61MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ApplicationFastFood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        new ApplicationFastFood().setVisible(true);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private fastfood.component.Button btnAdimndelete;
    private fastfood.component.Button btnCancel;
    private fastfood.component.Button btnChange;
    private fastfood.component.Button btnChangePass;
    private fastfood.component.Button btnExcel;
    private fastfood.component.Button btnLogin;
    private fastfood.component.Button btnLoginBack;
    private fastfood.component.Button btnRegister;
    private fastfood.component.Button btnSearch;
    private fastfood.component.Button button1;
    private fastfood.component.Button button2;
    private fastfood.component.Button button3;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboHistory;
    private javax.swing.JComboBox<String> cboIPType;
    private javax.swing.JComboBox<String> cboTypeProductHome;
    private javax.swing.JComboBox<String> cbolTkStore;
    private javax.swing.JCheckBox chkShow;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JScrollPane jScrollpnlStore;
    private javax.swing.JLayeredPane layerChinh;
    private javax.swing.JLayeredPane layerViewMyStore;
    private fastfood.component.Label lblAvt;
    private javax.swing.JLabel lblBillImg;
    private javax.swing.JLabel lblBillMoney;
    private javax.swing.JLabel lblBillName;
    private javax.swing.JLabel lblBillNoteSp;
    private javax.swing.JLabel lblBillPriceSP;
    private javax.swing.JLabel lblBillSLMua;
    private fastfood.component.Label lblCart;
    private javax.swing.JLabel lblCartMoney;
    private javax.swing.JLabel lblChangerrConfirmPass;
    private javax.swing.JLabel lblChangerrCurrentPass;
    private javax.swing.JLabel lblChangerrNewPass;
    private javax.swing.JLabel lblConfirm;
    private javax.swing.JLabel lblForgotPass;
    private javax.swing.JLabel lblGetBack;
    private javax.swing.JLabel lblHistory;
    private javax.swing.JLabel lblHistoryStore;
    private javax.swing.JLabel lblHistoryStoreTurnover;
    private javax.swing.JLabel lblHistoryStoreWage;
    private javax.swing.JLabel lblHome;
    private javax.swing.JLabel lblIPErrorName;
    private javax.swing.JLabel lblIPErrorNote;
    private javax.swing.JLabel lblIPErrorPrice;
    private javax.swing.JLabel lblIPErrorQuantity;
    private javax.swing.JLabel lblIPIconAddress;
    private javax.swing.JLabel lblIPIconName;
    private javax.swing.JLabel lblIPIconPrice;
    private javax.swing.JLabel lblIPIconQuantity;
    private javax.swing.JLabel lblIPImg;
    private javax.swing.JLabel lblIconLoginPass;
    private javax.swing.JLabel lblIconLoginUser;
    private javax.swing.JLabel lblIconReConfrim;
    private javax.swing.JLabel lblIconReUserName;
    private javax.swing.JLabel lblInforCart;
    private javax.swing.JLabel lblInforImgProduct;
    private javax.swing.JLabel lblInforNameProduct;
    private javax.swing.JLabel lblInforNameStore;
    private fastfood.component.TextArea lblInforNoteProduct;
    private javax.swing.JLabel lblInforPriceProduct;
    private javax.swing.JLabel lblInforQuantityProduct;
    private javax.swing.JLabel lblInforSlBuyProduct;
    private javax.swing.JLabel lblLogoApp;
    private javax.swing.JLabel lblManager;
    private javax.swing.JLabel lblMap;
    private javax.swing.JLabel lblMoney;
    private javax.swing.JLabel lblMyStore;
    private javax.swing.JLabel lblOclock;
    private fastfood.component.Label lblQRcore;
    private javax.swing.JLabel lblReErrorPass;
    private javax.swing.JLabel lblReErrorUSe;
    private javax.swing.JLabel lblReerrCode;
    private javax.swing.JLabel lblReerrConfrimPass;
    private javax.swing.JLabel lblReerremail;
    private javax.swing.JLabel lblRegister;
    private javax.swing.JLabel lblRunLabel;
    private javax.swing.JLabel lblTitleLoginUser;
    private javax.swing.JLabel lblTitleName;
    private javax.swing.JLabel lblUsImage;
    private javax.swing.JLabel lblUserQrcode;
    private javax.swing.JLabel lblUserViewStoreImg;
    private javax.swing.JLabel lblUserViewStoreNameStore;
    private javax.swing.JLabel lblUserrAddrss;
    private javax.swing.JLabel lblUserrBirth;
    private javax.swing.JLabel lblUserrEmail;
    private javax.swing.JLabel lblUserrName;
    private javax.swing.JLabel lblUserrPhone;
    private javax.swing.JLabel lblViewAddress;
    private javax.swing.JLabel lblViewDateOfBirth;
    private javax.swing.JLabel lblViewEmail;
    private javax.swing.JLabel lblViewGender;
    private javax.swing.JLabel lblViewImg;
    private javax.swing.JLabel lblViewInforAddType;
    private javax.swing.JLabel lblViewLogOut;
    private javax.swing.JLabel lblViewName;
    private javax.swing.JLabel lblViewNameStore;
    private javax.swing.JLabel lblViewUpdate;
    private javax.swing.JLabel lblViewphone;
    private javax.swing.JLabel lblerrLoginPass;
    private javax.swing.JLabel lblerrLoginUSer;
    private javax.swing.JLabel lblflagReCode;
    private javax.swing.JLabel lbliconChangCheckConfirmPass;
    private javax.swing.JLabel lbliconChangCurrentPass;
    private javax.swing.JLabel lbliconChangNewPass;
    private javax.swing.JLabel lbliconReEmail;
    private javax.swing.JLabel lbliconRePass;
    private javax.swing.JPanel pnlAdmin;
    private javax.swing.JPanel pnlBill;
    private javax.swing.JPanel pnlCart;
    private javax.swing.JPanel pnlChangePass;
    private javax.swing.JPanel pnlConfirm;
    private javax.swing.JPanel pnlHistoryStore;
    private javax.swing.JPanel pnlHistoryStores;
    private javax.swing.JPanel pnlHistoryUser;
    private javax.swing.JPanel pnlHistoryUsers;
    private javax.swing.JPanel pnlHome;
    private javax.swing.JPanel pnlInforCommentOneProduct;
    private javax.swing.JPanel pnlInforProduct;
    private javax.swing.JPanel pnlLogin;
    private javax.swing.JPanel pnlProduct;
    private javax.swing.JPanel pnlRegister;
    private javax.swing.JPanel pnlStore;
    private javax.swing.JPanel pnlTkStore;
    private javax.swing.JPanel pnlTkStores;
    private javax.swing.JPanel pnlUpdateInformation;
    private javax.swing.JPanel pnlUserViewStore;
    private javax.swing.JPanel pnlUserViewStores;
    private javax.swing.JPanel pnlViewCart;
    private javax.swing.JPanel pnlViewConfrim;
    private javax.swing.JPanel pnlViewInsertProduct;
    private javax.swing.JPanel pnlViewLabel;
    private javax.swing.JPanel pnlViewNameStore;
    private javax.swing.JPanel pnlViewStore;
    private javax.swing.JLabel pnlViewUserStore;
    private javax.swing.JPanel pnlViewinformation;
    private javax.swing.JRadioButton rdoUsFemale;
    private javax.swing.JRadioButton rdoUsMale;
    private fastfood.component.Table tblListAccount;
    private fastfood.component.TextFeild txtAdminSearch;
    private fastfood.component.TextPass txtChangConfirmPass;
    private fastfood.component.TextPass txtChangCurrentPass;
    private fastfood.component.TextPass txtChangNewPass;
    private fastfood.component.TextArea txtCommentOne;
    private fastfood.component.TextFeild txtIPName;
    private fastfood.component.TextArea txtIPNote;
    private fastfood.component.TextFeild txtIPPrice;
    private fastfood.component.TextFeild txtIPQuantity;
    private fastfood.component.TextPass txtLoginPass;
    private fastfood.component.TextFeild txtLoginUser;
    private fastfood.component.TextFeild txtReCode;
    private fastfood.component.TextPass txtReConfrimPass;
    private fastfood.component.TextFeild txtReEmail;
    private fastfood.component.TextPass txtRePass;
    private fastfood.component.TextFeild txtReUser;
    private fastfood.component.TextFeild txtSearch;
    private fastfood.component.TextArea txtUsAddress;
    private fastfood.component.TextFeild txtUsBirth;
    private fastfood.component.TextFeild txtUsEmail;
    private fastfood.component.TextFeild txtUsName;
    private fastfood.component.TextFeild txtUsPhone;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        while (true) {
            Date now = new Date();
            SimpleDateFormat formatDate = new SimpleDateFormat();
            formatDate.applyPattern("hh:mm:ss aa");
            String timeNow = formatDate.format(now);
            lblOclock.setText(timeNow);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                break;
            }
        }
    }

    public void getLogin() {
        Valid vl = new Valid();
        Msg.setIconLabel(Msg.x(), lblIconLoginUser, lblIconLoginPass);
        if (vl.isEmpty(txtLoginUser, lblerrLoginUSer, "User")) {
            lblIconLoginUser.setIcon(Msg.v());
        }

        if (vl.isEmpty(txtLoginPass, lblerrLoginPass, "Password")) {
            lblIconLoginPass.setIcon(Msg.v());
        }

        if (vl.getLoi().equals("")) {
            User us = usDao.login(txtLoginUser.getText(), txtLoginPass.getText());
            if (us != null) {
                if (us.getRoles().equals("delete")) {
                    Message.getNotify(this, "Your account has been deleted ", verify);
                } else {
                    Message.getNotify(this, "Login Successfull ", "Notify");
                    Authention.authention = us;
                    viewInforUser(Authention.authention);
                    lblAvt.setIcon(Msg.avt(Authention.authention.getImg(), lblAvt));
                    lblViewNameStore.setText(storeDao.getNameByUser(Authention.authention.getUser()));
                    viewLabel();
                    listGetBack.clear();
                    listGetBackAdd(layerChinh, pnlViewinformation);
                }
            } else {
                vl.sai(txtLoginUser, lblerrLoginUSer, "Wrong account or password");
                vl.sai(txtLoginPass, lblerrLoginPass, "Wrong account or password");
                Msg.setIconLabel(Msg.x(), lblIconLoginUser, lblIconLoginPass);
            }
        }
    }

    public User getUserexist(String id) {
        User us = usDao.selectByID(id);
        return us;
    }
    String verify = "-1";
    Timer timeVerify;

    public void getRegister() {
        Msg.setIconLabel(Msg.x(), lblIconReUserName, lbliconRePass, lblIconReConfrim, lbliconReEmail);
        String pass = new String(txtRePass.getPassword());
        if (data.isEmpty(txtReUser, lblReErrorUSe, "User")) {
            lblIconReUserName.setIcon(Msg.v());
        }

        if (data.isEmpty(txtRePass, lblReErrorPass, "Password")) {
            lbliconRePass.setIcon(Msg.v());
        }

        if (data.isEmpty(txtReConfrimPass, lblReerrConfrimPass, "Confrim Password")) {
            lblIconReConfrim.setIcon(Msg.v());
            if (data.compare(txtReConfrimPass, lblReerrConfrimPass, "Confirm password is incorrect", pass)) {
                lblIconReConfrim.setIcon(Msg.v());
            }
        }
        if (data.multiReMatch(txtReEmail, lblReerremail, "Email", data.reEmail())) {
            lbliconReEmail.setIcon(Msg.v());
        }

        if (data.getLoi().equals("")) {
            if (getUserexist(txtReUser.getText()) != null) {
                Message.getNotify(this, "User already exists, Please choose another username ", "Warring");
            } else {
                if (verify.equals("-1")) {
                    verify = CodeRandom.getranDomCode();
                    Email.sendEmail(verify, txtReEmail.getText(), "Verification codes");
                    Message.getNotify(this, "Please check your email for the confirmation code", "Notify");
                    timeVerify = new Timer(1000, new ActionListener() {
                        int i = 60;

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            i--;
                            lblflagReCode.setText(String.valueOf(i));
                            if (i == 0) {
                                timeVerify.stop();
                                verify = "-1";
                            }
                        }
                    });
                    timeVerify.start();
                }

            }
        } else {
            verify = "-1";
        }

    }

    public void setLabelinformationView(User us) {
        lblUserQrcode.setIcon(Ximage.reSizeImgae2(Ximage.read(us.getUser() + ".png"), lblUserQrcode));
        lblViewImg.setIcon(Msg.avt(us.getImg(), lblViewImg));
        lblViewImg.setToolTipText(us.getUser());
        lblViewName.setText(us.getName());
        lblViewDateOfBirth.setText(us.getBirth());
        lblViewGender.setText(us.isGender() ? "Male" : "Female");
        lblViewphone.setText(us.getPhone());
        lblViewEmail.setText(us.getEmail());
        lblViewAddress.setText(us.getAddress());
    }

    public void getTextFiledinformationView(User us) {
        listGetBackAdd(layerChinh, pnlUpdateInformation);
        lblUsImage.setIcon(Msg.avt(us.getImg(), lblUsImage));
        lblUsImage.setToolTipText(us.getImg());
        lblTitleName.setText(us.getName());
        txtUsName.setText(us.getName());
        txtUsBirth.setText(us.getBirth());
        rdoUsMale.setSelected(us.isGender());
        rdoUsFemale.setSelected(!us.isGender());
        txtUsPhone.setText(us.getPhone());
        txtUsEmail.setText(us.getEmail());
        txtUsAddress.setText(us.getAddress());
    }

    public void getChangePassword() {
        Msg.setIconLabel(Msg.x(), lbliconChangCurrentPass, lbliconChangNewPass, lbliconChangCheckConfirmPass);
        String pass = new String(txtChangNewPass.getPassword());
        if (data.multiLenght(txtChangCurrentPass, lblChangerrCurrentPass, "Can't be more than 50 characters and empty")) {
            lbliconChangCurrentPass.setIcon(Msg.v());
            if (data.compare(txtChangCurrentPass, lblChangerrCurrentPass, "Current password is not correct", Authention.authention.getPass())) {
                lbliconChangCurrentPass.setIcon(Msg.v());
            }
        }
        if (data.multiLenght(txtChangNewPass, lblChangerrNewPass, "Can't be more than 50 characters and  empty")) {
            lbliconChangNewPass.setIcon(Msg.v());
        }
        if (data.multiLenght(txtChangConfirmPass, lblChangerrConfirmPass, "Can't be more than 50 characters and empty")) {
            lbliconChangCheckConfirmPass.setIcon(Msg.v());
            if (data.compare(txtChangConfirmPass, lblChangerrConfirmPass, "Current password is not correct", pass)) {
                lbliconChangCheckConfirmPass.setIcon(Msg.v());
            }
        }
        if (data.getLoi().equals("")) {
            usDao.updatePass(pass, Authention.authention.getUser());
            Message.getNotify(this, "Update  Password Successful", "Success");
            layer(layerChinh, pnlHome);
        }

    }

    private void viewLabel() {
        pnlViewLabel.removeAll();
        pnlViewLabel.add(lblViewUpdate);
        pnlViewLabel.add(lblViewLogOut);
        if (Authention.authention.getRoles().equals("user")) {
            pnlViewLabel.add(pnlViewUserStore);
        }

        if (Authention.authention.getRoles().equals("store")) {
            pnlViewNameStore.add(lblViewNameStore);
        }
        pnlViewLabel.add(lblViewInforAddType);
        pnlViewLabel.add(lblHistory);

        if (Authention.authention.getRoles().equals("store")) {
            pnlViewLabel.add(lblHistoryStore);
        }

    }

    public void setProductInsert(Product p) {
        cboIPType.setToolTipText(String.valueOf(p.getIdProduct()) + "," + String.valueOf(p.getIdStore()));
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboIPType.getModel();
        model.removeAllElements();
        List<TypeProduct> list = typeDao.getAllDate();
        for (TypeProduct typeProduct : list) {
            model.addElement(typeProduct);
        }

        lblIPImg.setIcon(Msg.avt(p.getImg(), lblIPImg));
        lblIPImg.setToolTipText(p.getImg());
        txtIPName.setText(p.getName());
        txtIPPrice.setText(String.valueOf(p.getMoney()));
        txtIPQuantity.setText(String.valueOf(p.getQuantity()));
        txtIPNote.setText(p.getNote());
    }

    public void setCart(Cart c) {
        lblBillImg.setToolTipText(String.valueOf(c.getIdProduct()));
        lblBillImg.setIcon(Ximage.reSizeImgae2(Ximage.read(c.getImg()), lblBillImg));
        lblBillName.setText(c.getName());
        lblBillSLMua.setText(String.valueOf(c.getQuantityCart()));
        lblBillPriceSP.setText(FormatNumber.withLargeIntegers(c.getMoney(), 2));
        lblBillPriceSP.setToolTipText(String.valueOf(c.getMoney()));
        lblBillNoteSp.setText(c.getNote());
        double money = c.getMoney() * c.getQuantityCart();
        lblBillMoney.setText(FormatNumber.withLargeIntegers(money, 2));
        lblBillMoney.setToolTipText(String.valueOf(money));
    }

    public Bill getCart() {
        return new Bill(Authention.authention.getUser(), Integer.parseInt(lblBillImg.getToolTipText()),
                Integer.parseInt(lblBillSLMua.getText()), Double.parseDouble(lblBillPriceSP.getToolTipText()));
    }

    public Product getProductInsert() {
        String[] arr = cboIPType.getToolTipText().split(",");
        int idProduct = Integer.parseInt(arr[0]);
        int idStore = Integer.parseInt(arr[1]);
        return new Product(idProduct, ((TypeProduct) cboIPType.getSelectedItem()).getIdType(),
                Integer.parseInt(txtIPQuantity.getText()), idStore,
                txtIPName.getText(), txtIPNote.getText(),
                Double.parseDouble(txtIPPrice.getText()), lblIPImg.getToolTipText(),
                true);
    }

    private void setLblInforProduct(Product p) {
        String exists = p.isExistss() ? "1" : "0";
        Object[] obj = storeDao.getNameStoreAndQuantity(p.getIdProduct());
        lblInforCart.setToolTipText(String.valueOf(p.getIdProduct()) + "," + exists);
        lblInforImgProduct.setIcon(Ximage.reSizeImgae2(Ximage.read(p.getImg()), lblInforImgProduct));
        lblInforNameProduct.setText(p.getName());
        lblInforNameStore.setText((String) obj[0]);
        lblInforNameStore.setToolTipText(String.valueOf(p.getIdStore()));
        lblInforPriceProduct.setText(p.getMoney() + "");
        lblInforNoteProduct.setText(p.getNote());
        lblInforQuantityProduct.setText(String.valueOf(obj[1]));
        String slBuy = String.valueOf(obj[2]);
        lblInforSlBuyProduct.setText(slBuy.equals("null") ? "0" : slBuy);

        String user = Authention.authention == null ? "" : Authention.authention.getUser();
        txtCommentOne.setToolTipText(productDao.isTacGia(user, p.getIdProduct()));

        txtCommentOne.setText("");
        fillCommentOne(p.getIdProduct());
    }

    private JPanel uiComment(LikeCommentOne like) {
        UiComment uc = new UiComment(like);
        uc.setSize(70);
        uc.panel();
        uc.commentOne();
        chkCommentOneSetSelected(uc);
        uc.lblImg.addMouseListener(mouseViewUser(usDao.selectByID(uc.getLikeCommentOne().getUserComment())));
        uc.lblUser.addMouseListener(mouseViewUser(usDao.selectByID(uc.getLikeCommentOne().getUserComment())));
        chkCommentOneAction(uc);
        lblFeedBackCommentOne(uc);
        pnlCommentOneAction(uc);
        uc.pnl.setToolTipText("pnl" + like.getiDCommentOne());
        uc.pnlAll.add(uc.pnl);
        if (uc.getLikeCommentOne().getQuantityComment() > 0) {
            uc.pnlAll.add(lblViewFeedBackComment(uc));
        }
        return uc.pnlAll;
    }

    private JLabel lblViewFeedBackComment(UiComment uc) {
        JLabel lbl = new JLabel("View feedback " + uc.getLikeCommentOne().getQuantityComment(), JLabel.LEFT);
        lbl.setToolTipText("lbl" + uc.getLikeCommentOne().getiDCommentOne());
        lbl.setMinimumSize(new Dimension(556, 20));
        lbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                uc.pnlAll.remove(lbl);
                fillCommentTwo(uc);
            }
        });
        return lbl;
    }

    private void fillCommentOne(int idProduct) {
        pnlInforCommentOneProduct.removeAll();
        String user = Authention.authention == null ? "" : Authention.authention.getUser();
        List<LikeCommentOne> list = likeCommentOneDAO.selectALL(user, idProduct);
        for (LikeCommentOne like : list) {
            pnlInforCommentOneProduct.add(uiComment(like));
        }
    }

    public void chkCommentOneAction(UiComment uc) {
        uc.chkHeart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Authention.authention == null) {
                    uc.chkDislike.setSelected(false);
                    uc.chkHeart.setSelected(false);
                    Message.getNotify(null, "You cannot like this product because you are not yet login!", "Message");
                } else {
                    if (uc.chkHeart.isSelected()) {
                        uc.getLikeCommentOne().increaseQuantityLike();
                        uc.getLikeCommentOne().setLike(1);
                        likeCommentOneDAO.insert_update(Authention.authention.getUser(), uc.getLikeCommentOne().getiDCommentOne(), uc.getLikeCommentOne().getLike());
                        uc.chkHeart.setText(String.valueOf(uc.getLikeCommentOne().getQuantityLike()));
                        uc.chkDislike.setSelected(false);
                    } else {
                        uc.getLikeCommentOne().decreaseQuantityLike();
                        uc.getLikeCommentOne().setLike(0);
                        likeCommentOneDAO.insert_update(Authention.authention.getUser(), uc.getLikeCommentOne().getiDCommentOne(), uc.getLikeCommentOne().getLike());
                        uc.chkHeart.setText(String.valueOf(uc.getLikeCommentOne().getQuantityLike()));
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
                            uc.getLikeCommentOne().decreaseQuantityLike();
                            uc.chkHeart.setText(String.valueOf(uc.getLikeCommentOne().getQuantityLike()));
                            uc.chkHeart.setSelected(false);
                        }
                        uc.getLikeCommentOne().setLike(2);
                        likeCommentOneDAO.insert_update(Authention.authention.getUser(), uc.getLikeCommentOne().getiDCommentOne(), uc.getLikeCommentOne().getLike());
                    } else {
                        uc.getLikeCommentOne().setLike(0);
                        likeCommentOneDAO.insert_update(Authention.authention.getUser(), uc.getLikeCommentOne().getiDCommentOne(), uc.getLikeCommentOne().getLike());
                    }
                }
            }
        });
    }

    public void chkCommentOneSetSelected(UiComment uc) {
        boolean heart = hashLike.get(uc.getLikeCommentOne().getLike())[0];
        boolean dislike = hashLike.get(uc.getLikeCommentOne().getLike())[1];
        uc.chkHeart.setSelected(heart);
        uc.chkDislike.setSelected(dislike);
    }

    private void lblFeedBackCommentOne(UiComment uc) {
        uc.lblFeedBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!checkLogin()) {
                    return;
                }
                UiInputComment uic = new UiInputComment();
                uic.panel();
                uc.pnlAll.add(uic.pnl, 1);
                txtCommentTwo(uc, uic);
                uc.pnlAll.revalidate();
            }
        });
    }

    private void txtCommentTwo(UiComment uc, UiInputComment input) {
        input.lblComment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                commentTwoDao.insert(new CommentTwo(uc.getLikeCommentOne().getiDCommentOne(), Authention.authention.getUser(), input.txt.getText(), txtCommentOne.getToolTipText()));
                uc.getLikeCommentOne().increaseQuantityComment();
                LikeCommentTwo like = likeCommentTwoDAO.selectOne(Authention.authention.getUser(), uc.getLikeCommentOne().getiDCommentOne());
                uc.pnlAll.remove(input.pnl);
                uc.pnlAll.add(uiCommnent(like, uc), 1);
                uc.pnlAll.revalidate();
            }

        });
    }

    private void pnlCommentOneAction(UiComment uc) {
        uc.pnl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Authention.authention != null) {
                    if (e.getClickCount() == 2) {
                        /*Tac gia*/
                        if (!txtCommentOne.getToolTipText().equals("")) {
                            String[] options = {"Ghim text", "Delete text"};
                            int i = Message.getOption(null, "Ghim or delte", options);
                            if (i == 0) {
                                commentOneDao.update(uc.getLikeCommentOne());
                            } else if (i == 1) {
                                commentOneDao.delete(uc.getLikeCommentOne().getiDCommentOne());
                                pnlInforCommentOneProduct.remove(uc.pnl);
                            }
                            return;
                        }

                        if (uc.getLikeCommentOne().getUserComment().equals(Authention.authention.getUser())) {
                            if (Message.getconfirm(null, "You want delete comment your", "Message") == JOptionPane.YES_OPTION) {
                                commentOneDao.delete(uc.getLikeCommentOne().getiDCommentOne());
                                pnlInforCommentOneProduct.remove(uc.pnlAll);
                                pnlInforCommentOneProduct.repaint();
                                pnlInforCommentOneProduct.revalidate();
                            }
                        }
                    }
                }
            }
        });
    }

    public void newUiCommentTwo(UiComment uc) {
        new UiCommentTwo(this, true, uc, txtCommentOne.getToolTipText()).setVisible(true);
    }

    private void fillUserViewStore(int idStore) {
        Object[] obj = storeDao.getImgStoreByIdStore(idStore);
        lblUserViewStoreNameStore.setText((String) obj[0]);
        lblUserViewStoreNameStore.setToolTipText((String) obj[1]);
        lblUserViewStoreImg.setIcon(Msg.avt((String) obj[2], lblUserViewStoreImg));

        pnlUserViewStores.removeAll();
        String user = Authention.authention == null ? "" : Authention.authention.getUser();
        List<Product> list = productDao.selectUserViewStore(user, idStore);
        for (Product product : list) {
            UiProduct p = new UiProduct(product);
            p.setSizeImage(210, 128);
            p.panel();
            p.cart();
            p.moveLblImg("buy.png");
            p.lblCart.addMouseListener(mouseAddCart(product));
            p.chkLike.addActionListener(actionLikeProduct(p));
            p.lblImg.addMouseListener(mouseViewProduct(p.getProduct()));
            pnlUserViewStores.add(p.pnl);
        }
        int sl = 8 - list.size();
        for (int i = 0; i < sl; i++) {
            UiProduct p = new UiProduct(new Product(i, i, "Ao " + i, i * 100, "avt.jpg"));
//            p.setSizeImage(220, 125);
//            p.panel();
            pnlUserViewStores.add(p.pnl);
        }
    }

    private void fillCommentTwo(UiComment uc) {
        String user = Authention.authention == null ? "" : Authention.authention.getUser();
        List<LikeCommentTwo> list = likeCommentTwoDAO.selectALL(user, uc.getLikeCommentOne().getiDCommentOne());
        for (LikeCommentTwo like : list) {
            uc.pnlAll.add(uiCommnent(like, uc));
        }
        uc.pnlAll.revalidate();
    }

    private JPanel uiCommnent(LikeCommentTwo like, UiComment ucOne) {
        UiComment2 uc = new UiComment2(like);
        uc.setSize(70);
        uc.panel();
        uc.commentOne();
        uc.lblImg.addMouseListener(mouseViewUser(usDao.selectByID(uc.getLikeCommentTwo().getUserComment())));
        uc.lblUser.addMouseListener(mouseViewUser(usDao.selectByID(uc.getLikeCommentTwo().getUserComment())));
        chkCommentTwoSetSelected(uc);
        chkCommentTwoAction(uc);
        pnlCommentTwoAction(uc, ucOne);
        uc.pnl.setToolTipText("pnlCommentTwo" + like.getiDCommentTwo());
        return uc.pnl;
    }

    private void pnlCommentTwoAction(UiComment2 uc, UiComment ucOne) {
        uc.pnl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Authention.authention != null) {
                    if (e.getClickCount() == 2) {
                        /*Tac gia*/
                        if (!txtCommentOne.getToolTipText().equals("")) {
                            if (Message.getconfirm(null, "You want to delete this comment", "Message") == JOptionPane.YES_OPTION) {
                                commentTwoDao.delete(uc.getLikeCommentTwo().getiDCommentTwo());
                                ucOne.pnlAll.remove(uc.pnl);
                                ucOne.pnlAll.revalidate();
                                pnlInforCommentOneProduct.revalidate();
                            }
                            return;
                        }

                        if (uc.getLikeCommentTwo().getUserComment().equals(Authention.authention.getUser())) {
                            if (Message.getconfirm(null, "You want delete comment your", "Message") == JOptionPane.YES_OPTION) {
                                commentTwoDao.delete(uc.getLikeCommentTwo().getiDCommentTwo());
                                ucOne.pnlAll.remove(uc.pnl);
                                ucOne.pnlAll.revalidate();
                                pnlInforCommentOneProduct.revalidate();
                            }
                        }
                    }
                }
            }
        });
    }

    public void chkCommentTwoAction(UiComment2 uc) {
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
                        likeCommentTwoDAO.insert_update(Authention.authention.getUser(), uc.getLikeCommentTwo().getiDCommentTwo(), uc.getLikeCommentTwo().getLike());
                        uc.chkHeart.setText(String.valueOf(uc.getLikeCommentTwo().getQuantityLike()));
                        uc.chkDislike.setSelected(false);
                    } else {
                        uc.getLikeCommentTwo().decreaseQuantityLike();
                        uc.getLikeCommentTwo().setLike(0);
                        likeCommentTwoDAO.insert_update(Authention.authention.getUser(), uc.getLikeCommentTwo().getiDCommentTwo(), uc.getLikeCommentTwo().getLike());
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
                        likeCommentTwoDAO.insert_update(Authention.authention.getUser(), uc.getLikeCommentTwo().getiDCommentTwo(), uc.getLikeCommentTwo().getLike());
                    } else {
                        uc.getLikeCommentTwo().setLike(0);
                        likeCommentTwoDAO.insert_update(Authention.authention.getUser(), uc.getLikeCommentTwo().getiDCommentTwo(), uc.getLikeCommentTwo().getLike());
                    }
                }
            }
        });
    }

    public void chkCommentTwoSetSelected(UiComment2 uc) {
        boolean heart = hashLike.get(uc.getLikeCommentTwo().getLike())[0];
        boolean dislike = hashLike.get(uc.getLikeCommentTwo().getLike())[1];
        uc.chkHeart.setSelected(heart);
        uc.chkDislike.setSelected(dislike);
    }

    public void getQrcode(String a) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix matrix = qrCodeWriter.encode(a, BarcodeFormat.QR_CODE, 200, 200);
            String outputFile = "./resource/" + a + ".png";
            Path path = FileSystems.getDefault().getPath(outputFile);
            MatrixToImageWriter.writeToPath(matrix, "PNG", path);
        } catch (Exception e) {
        }
    }

    public void loadMoney() {
        if (Authention.authention != null) {
            lblMoney.setText(String.valueOf(money.getMonney(Authention.authention.getUser())));
        } else {
            lblMoney.setText("0.0");
        }

    }

    public boolean pay(String lbl) {
        double y = Double.parseDouble(lblMoney.getText()) - Double.parseDouble(lbl);
        if (y <= 0) {
            Message.getNotify(this, "You don't enough money", "Warring");
            return false;
        }
        return true;
    }

    public void printBill() {
        try ( XWPFDocument document = new XWPFDocument()) {
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun title = paragraph.createRun();
            title.setFontFamily("Eras Medium ITC");
            title.setFontSize(18);
            title.setBold(true);
            title.setText("Biil of " + Authention.authention.getUser());
            XWPFParagraph paragraph1 = document.createParagraph();
            XWPFRun run1 = paragraph1.createRun();
            run1.setText("Name Product : " + lblBillName.getText());
            XWPFParagraph paragraph2 = document.createParagraph();
            XWPFRun run2 = paragraph2.createRun();
            run2.setText("Quanity Product  :" + lblBillSLMua.getText());
            XWPFParagraph paragraph3 = document.createParagraph();
            XWPFRun run3 = paragraph3.createRun();
            run3.setText("Price Product  :  " + lblBillPriceSP.getText());
            XWPFParagraph paragraph4 = document.createParagraph();
            XWPFRun run4 = paragraph4.createRun();
            run4.setText("Note Product : " + lblBillNoteSp.getText());
            XWPFParagraph paragraph5 = document.createParagraph();
            XWPFRun run5 = paragraph5.createRun();
            run5.setText("Bill Money :'" + lblBillMoney.getText());

            File file = new File("resource", "bill.docx");
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }
            try (
                     FileOutputStream out = new FileOutputStream(file)) {
                document.write(out);
            }
        } catch (IOException ex) {
            Logger.getLogger(ApplicationFastFood.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
