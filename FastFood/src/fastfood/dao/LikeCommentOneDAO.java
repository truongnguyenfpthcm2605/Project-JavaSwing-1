/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fastfood.dao;

import fastfood.entity.LikeCommentOne;
import fastfood.entity.Product;
import fastfood.sql.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LikeCommentOneDAO {

    public List<LikeCommentOne> selectALL(String user, int idproduct) {
        String sql = "{CALL selectCommentOne(?,?)}";
        return this.getById(sql, user,idproduct);
    }
    
    public void insert_update(String users,int idComment, int like){
        String sql = "{CALL proc_UserLikeCommentOne(?,?,?)}";
        JDBCHelper.update(sql, users,idComment,like);
    }
    
    public LikeCommentOne selectOne(String user, int idproduct){
        String sql = "{CALL selectCommentOneAdd(?,?)}";
        return this.getById(sql, user,idproduct).get(0);
    }

    protected List<LikeCommentOne> getById(String sql, Object... args) {
        List<LikeCommentOne> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                LikeCommentOne l = new LikeCommentOne();
                l.setiDCommentOne(rs.getInt(1));
                l.setUserComment(rs.getString(2));
                l.setiDProduct(rs.getInt(3));
                l.setContent(rs.getString(4));
                l.setDateComment(rs.getString(5));
                l.setvT(rs.getBoolean(6));
                l.setTacGia(rs.getString(7));
                l.setImg(rs.getString(8));
                l.setLike(rs.getInt(9));
                l.setQuantityLike(rs.getInt(10));
                l.setQuantityComment(rs.getInt(11));
                list.add(l);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
