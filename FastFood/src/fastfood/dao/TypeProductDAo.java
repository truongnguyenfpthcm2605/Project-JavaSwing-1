/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fastfood.dao;

import java.util.List;
import fastfood.entity.TypeProduct;
import fastfood.sql.JDBCHelper;
import java.util.ArrayList;
import java.sql.*;

public class TypeProductDAo {

    public void insert(String name){
        String sql = "insert into TYPEPRODUCT values (?)";
        JDBCHelper.update(sql, name);
    }
    
    public TypeProduct selectByID(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<TypeProduct> getAllDate() {
        String sql = "select * from TYPEPRODUCT";
        return this.getById(sql);
    }

    protected List<TypeProduct> getById(String sql, Object... args) {
        List<TypeProduct> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                TypeProduct type = new TypeProduct(rs.getInt(1), rs.getString(2));
                list.add(type);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
