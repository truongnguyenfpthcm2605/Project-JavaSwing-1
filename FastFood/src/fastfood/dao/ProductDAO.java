package fastfood.dao;

import fastfood.entity.Product;
import fastfood.sql.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LE VO PHU THIEN
 */
public class ProductDAO extends FastFoodDAO<Product, Integer> {

    @Override
    public void insert(Product p) {
        String sql = "insert into PRODUCT(IDSTORE,TYPEPRODUCT,NAMES,PRICE,QUANTITY,NOTE,IMAGES) values (?,?,?,?,?,?,?)";
        JDBCHelper.update(sql, p.getIdStore(), p.getTypeProduct(), p.getName(), p.getMoney(), p.getQuantity(), p.getNote(), p.getImg());
    }

    @Override
    public void update(Product p) {
        String sql = "update PRODUCT "
                + "set TYPEPRODUCT = ?, NAMES=?, PRICE=?, QUANTITY=?, NOTE=?, IMAGES =? where IDPRODUCT=?";
        JDBCHelper.update(sql, p.getTypeProduct() ,p.getName(), p.getMoney(), p.getQuantity(), p.getNote(),p.getImg(), p.getIdProduct());
    }

    @Override
    public void delete(Integer key) {
        String sql = "update PRODUCT "
                + "SET EXISTSS = 0 WHERE IDPRODUCT=?";
        JDBCHelper.update(sql, key);
    }

    @Override
    public Product selectByID(Integer key) {
        String sql = "SELECT * FROM PRODUCT WHERE IDPRODUCT =?";
        List<Product> list = this.getByIdStore(sql, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Product> getAllDate() {
        String sql = "SELECT * FROM PRODUCT where existss = 1";
        return this.getById(sql);
    }

    @Override
    protected List<Product> getById(String sql, Object... args) {
        List<Product> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                Product p = new Product();
                p.setIdProduct(rs.getInt(1));
                p.setIdStore(rs.getInt(2));
                p.setTypeProduct(rs.getInt(3));
                p.setName(rs.getString(4));
                p.setMoney(rs.getDouble(5));
                p.setQuantity(rs.getInt(6));
                p.setImg(rs.getString(7));
                p.setNote(rs.getString(8));
                p.setExistss(rs.getBoolean(9));
                p.setSlLike(rs.getInt(10));
                p.setLike(rs.getBoolean(11));
                list.add(p);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    protected List<Product> getByIdStore(String sql, Object... args) {
        List<Product> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                Product p = new Product();
                p.setIdProduct(rs.getInt(1));
                p.setIdStore(rs.getInt(2));
                p.setTypeProduct(rs.getInt(3));
                p.setName(rs.getString(4));
                p.setMoney(rs.getDouble(5));
                p.setQuantity(rs.getInt(6));
                p.setImg(rs.getString(7));
                p.setNote(rs.getString(8));
                p.setExistss(rs.getBoolean(9));
                list.add(p);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
    public List<Product> selectByName(String user, String nameProduct){
        String sql = "{CALL product_Name(?,?)}";
        return this.getById(sql, nameProduct,user);
    }
    
    public List<Product> selectByTypeProduct(String user,int typeProduct){
        String sql = "{CALL product_TypeProduct(?,?)}";
        return this.getById(sql, typeProduct,user);
    }
    
    public void tim(String user, int idProduct, boolean like){
        String sql = "{CALL proc_user_like(?,?,?)}";
        JDBCHelper.update(sql, user,idProduct,like);
    }

    public List<Product> selectUserViewStore(String user, int idStore){
        String sql = "{CALL userViewStore(?,?)}";
        return this.getById(sql, idStore,user);
    }
    
    public String isTacGia(String user, int idProduct){
        String sql = "{CALL isTacGia(?,?)}";
        return (String)JDBCHelper.value(sql, idProduct,user);
    }


}


