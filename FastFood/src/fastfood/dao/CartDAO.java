/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fastfood.dao;

import fastfood.entity.Cart;
import fastfood.sql.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Duy Cuong
 */
public class CartDAO {

    public boolean insert(String user, int idProduct) {
        String sql = "insert into cart values (?,?,1)";
        try {
            JDBCHelper.update(sql, user, idProduct);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void update(String user, int idProduct, int sl) {
        String sql = "update CART set QUANTITY = ? where USERS = ? and IDPRODUCT = ?";
        JDBCHelper.update(sql, sl, user, idProduct);
    }

    public void delete(String user, int idProduct) {
        String sql = "delete from CART where USERS = ? and IDPRODUCT = ?";
        JDBCHelper.update(sql, user, idProduct);
    }

    public List<Cart> seletecAll(String user) {
        String sql = "select PRODUCT.*,CART.QUANTITY QuantityCart "
                + "from product join cart on PRODUCT.IDPRODUCT = CART.IDPRODUCT "
                + "where CART.USERS = ?";
        return this.getById(sql, user);
    }

    protected List<Cart> getById(String sql, Object... args) {
        List<Cart> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                Cart c = new Cart();
                c.setIdProduct(rs.getInt("IDPRODUCT"));
                c.setIdStore(rs.getInt("IDSTORE"));
                c.setTypeProduct(rs.getInt("TYPEPRODUCT"));
                c.setName(rs.getString("NAMES"));
                c.setMoney(rs.getDouble("PRICE"));
                c.setQuantity(rs.getInt("QUANTITY"));
                c.setImg(rs.getString("IMAGES"));
                c.setNote(rs.getString("NOTE"));
                c.setExistss(rs.getBoolean("EXISTSS"));
                c.setQuantityCart(rs.getInt("QuantityCart"));
                list.add(c);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
