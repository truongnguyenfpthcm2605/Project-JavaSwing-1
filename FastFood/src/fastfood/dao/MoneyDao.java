/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fastfood.dao;

import fastfood.sql.JDBCHelper;

/**
 *
 * @author truong
 */
public class MoneyDao {
    public double getMonney(String id){
        String sql = "select moneys from Moneys where users = ?";
        return (double)JDBCHelper.value(sql, id);
    }
    public void update(double money,String id){
        String sql = "update Moneys set moneys = ?  where users = ?";
        JDBCHelper.update(sql, money,id);
    }
}
