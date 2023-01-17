/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fastfood.dao;

import fastfood.entity.Bill;
import fastfood.sql.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    public Object[] insert(Bill bill) {
        String sql = "{CALL insert_Bill(?,?,?,?)}";
        String [] cols = {"DungSai","SL"};
        return this.getListOfArray(sql, cols, bill.getUser(),bill.getIdProduct(),bill.getQuantity(),bill.getMoney()).get(0);
    }
    
    public List<Object[]> yearMonth(String userStore){
        String sql = "{CALL bill_year_month(?)}";
        String []cols = {"THANG"};
        return this.getListOfArray(sql, cols, userStore);
    }
    
    public List<Bill> store(String user, String monthYear){
        String sql = "{CALL bill_store(?,?)}";
        return getList(sql, user,monthYear);
    }
    
    public List<Object[]> triAn(String user){
        String sql = "{CALL triAn(?)}";
        String []cols = {"email"};
        return getListOfArray(sql, cols, user);
    }
    
    public List<Bill> user(String user){
        String sql = "select b.*,IMAGES,NAMES "
                + "from bill b inner join product p on b.idproduct = p.idproduct "
                + "where b.USERS=? "
                + "order by b.DATEPURCHASE desc";
        return this.getList(sql, user);
    }
    
    public List<Bill> tkStore(String user, String monthYear){
        String sql = "{CALL tk_Bill(?,?)}";
        return this.getListTK(sql, user,monthYear);
    }
    
    
    public List<Bill> getList(String sql, Object ...args){
        try{
            List<Bill> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.query(sql, args);
            while(rs.next()){
                Bill bill = new Bill();
                bill.setIdBill(rs.getInt(1));
                bill.setUser(rs.getString(2));
                bill.setIdProduct(rs.getInt(3));
                bill.setDatePurchase(rs.getString(4));
                bill.setQuantity(rs.getInt(5));
                bill.setMoney(rs.getInt(6));
                bill.setImg(rs.getString(7));
                bill.setName(rs.getString(8));
                list.add(bill);
            }
            rs.getStatement().getConnection().close();
            return list;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    public List<Bill> getListTK(String sql, Object ...args){
        try{
            List<Bill> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.query(sql, args);
            while(rs.next()){
                Bill bill = new Bill();
                bill.setIdProduct(rs.getInt(1));
                bill.setQuantity(rs.getInt(2));
                bill.setMoney(rs.getInt(3));
                bill.setImg(rs.getString(4));
                bill.setName(rs.getString(5));
                list.add(bill);
            }
            rs.getStatement().getConnection().close();
            return list;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    private List<Object[]> getListOfArray(String sql, String[] cols, Object ...args){
        try{
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.query(sql, args);
            while(rs.next()){
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    
}
