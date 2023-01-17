/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fastfood.dao;

import fastfood.entity.Product;
import fastfood.sql.JDBCHelper;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;


public class StoreDao {

    public String getNameStore(int id, int idproduct) {
        String sql = "select store.NAMES from STORE join PRODUCT on STORE.IDUSER = ? and PRODUCT.IDPRODUCT =?";
        String nameStore = (String) JDBCHelper.value(sql, id, idproduct);
        return nameStore;
    }

    public String getNameStoreofProduct(int idProduct) {
        String sql = "select store.NAMES from STORE join PRODUCT on STORE.IDSTORE = ?";
        String name = (String) JDBCHelper.value(sql, idProduct);
        return name;
    }

    public Object[] getNameStoreAndQuantity(int idProduct) {
        String sql = "select s.names,p.quantity,DBO.bill_SLBuy(P.IDPRODUCT) SLMUA from product p join store s on p.idstore = s.idstore where p.idproduct = ?";
        String []column = {"NAMES","QUANTITY","SLMUA"};
        return XaiChungDAO.getListOfArray(sql,column, idProduct).get(0);
    }
    
    public Object[] getImgStoreByIdStore(int idStore){
        String sql = "select s.NAMES,s.IDUSER IDUSERS,u.IMAGES IMG "
                + "from store s join users u on s.IDUSER = u.USERS where IDSTORE = ?";
        String []column = {"NAMES","IDUSERS","IMG"};
        return XaiChungDAO.getListOfArray(sql, column, idStore).get(0);
    }

    public List<Product> getProductOnlyUser(String iduser) {
        ProductDAO dao = new ProductDAO();
        String sql = """
                    select PRODUCT.*
                                    from PRODUCT join STORE on PRODUCT.IDSTORE = STORE.IDSTORE 
                                    where IDUSER = ? and EXISTSS = 1""";
        return dao.getByIdStore(sql, iduser);
    }
    

    public void update(String user, String name) {
        String sql = "update STORE set NAMES = ? where IDUSER = ? ";
        JDBCHelper.update(sql, name, user);
    }

    public String getNameByUser(String user) {
        String sql = "select names from store where IDUSER = ?";
        String nameStore = (String) JDBCHelper.value(sql, user);
        return nameStore;
    }

    public int getIdStoreByUser(String user) {
        String sql = "select IDSTORE from store where IDUSER = ?";
        int idStore = (int) JDBCHelper.value(sql, user);
        return idStore;
    }

    protected List<Object[]> getById(String sql, Object... args) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                Object[] obj = new Object[]{rs.getString(1), rs.getInt(2), rs.getInt(3)};
                list.add(obj);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
