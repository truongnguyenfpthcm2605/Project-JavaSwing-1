package fastfood.dao;

import fastfood.entity.User;
import fastfood.sql.JDBCHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class UserDao extends FastFoodDAO<User, String> {

    @Override
    public void insert(User e) {
        String sql = "insert into users(USERS,PASS,EMAIL,ROLES) values (?,?,?,?)";
        JDBCHelper.update(sql, e.getUser(), e.getPass(), e.getEmail(), "user");
    }

    @Override
    public void update(User e) {
        String sql = "update users "
                + "set NAMES=?, PHONE=?, EMAIL=?, ADDRESSS=?, IMAGES=?, BRITH=?, ROLES=?, GENDER=? where users=?";
        JDBCHelper.update(sql, e.getName(), e.getPhone(), e.getEmail(), e.getAddress(), e.getImg(), e.getBirth(), e.getRoles(), e.isGender(), e.getUser());
    }

    @Override
    public void delete(String key) {
        String sql = "update users "
                + "set NAMES=null, PHONE=null, EMAIL='delete', ADDRESSS=null, IMAGES=null, BRITH=null, ROLES='delete', GENDER=null where users=?";
        JDBCHelper.update(sql, key);
    }

    @Override
    public User selectByID(String key) {
        String sql = " SELECT * FROM users WHERE USERS = ?";
        List<User> list = getById(sql, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<User> getAllDate() {
        String sql = " SELECT * FROM USERS";
        return this.getById(sql);
    }

    @Override
    protected List<User> getById(String sql, Object... args) {
        List<User> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                User us = new User();
                us.setUser(rs.getString(1));
                us.setPass(rs.getString(2));
                us.setName(rs.getString(3));
                us.setPhone(rs.getString(4));
                us.setEmail(rs.getString(5));
                us.setAddress(rs.getString(6));
                us.setImg(rs.getString(7));
                us.setBirth(rs.getString(8));
                us.setRoles(rs.getString(9));
                us.setGender(rs.getBoolean(10));
                list.add(us);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void insertfull(User e) {

    }

    public void updatePass(String pass, String user) {
        String sql = "update users "
                + "set PASS=? where users=?";
        JDBCHelper.update(sql, pass, user);
    }

    public User login(String user, String pass) {
        String sql = " SELECT * FROM users WHERE USERS = ? and PASS = ?";
        List<User> list = getById(sql, user, pass);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<User> getAllAccount() {
        String sql = "select * from users where ROLES = 'user' or ROLES = 'store'";
        return this.getById(sql);
    }
    

}
