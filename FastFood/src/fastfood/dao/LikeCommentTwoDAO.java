/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fastfood.dao;

import fastfood.entity.LikeCommentOne;
import fastfood.entity.LikeCommentTwo;
import fastfood.sql.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Duy Cuong
 */
public class LikeCommentTwoDAO {
    public List<LikeCommentTwo> selectALL(String user, int idCommentOne) {
        String sql = "{CALL selectCommentTwo(?,?)}";
        return this.getById(sql, user,idCommentOne);
    }
    
    public void insert_update(String users,int idComment, int like){
        String sql = "{CALL proc_UserLikeCommentTwo(?,?,?)}";
        JDBCHelper.update(sql, users,idComment,like);
    }
    
    public LikeCommentTwo selectOne(String user, int idCommentOne){
        String sql = "{CALL selectCommentTwoAdd(?,?)}";
        return this.getById(sql, user,idCommentOne).get(0);
    }

    protected List<LikeCommentTwo> getById(String sql, Object... args) {
        List<LikeCommentTwo> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                LikeCommentTwo l = new LikeCommentTwo();
                l.setiDCommentTwo(rs.getInt(1));
                l.setiDCommentOne(rs.getInt(2));
                l.setUserComment(rs.getString(3));
                l.setContent(rs.getString(4));
                l.setDateComment(rs.getString(5));
                l.setTacGia(rs.getString(6));
                l.setImg(rs.getString(7));
                l.setLike(rs.getInt(8));
                l.setQuantityLike(rs.getInt(9));
                list.add(l);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
