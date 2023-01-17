
package fastfood.dao;

import fastfood.entity.CommentTwo;
import fastfood.sql.JDBCHelper;
import java.util.List;

public class CommentTwoDAO extends FastFoodDAO<CommentTwo, Integer>{

    @Override
    public void insert(CommentTwo enity) {
        String sql = "insert into CommentTwo(USERS,IDComment,Content,tacGia) values (?,?,?,?)";
        JDBCHelper.update(sql, enity.getUserComment(),enity.getiDCommentOne(),enity.getContent(),enity.getTacGia());
    }

    @Override
    public void update(CommentTwo enity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Integer key) {
        String sql = "delete CommentTwo where IDCommentTwo = ?";
        JDBCHelper.update(sql, key);
    }

    @Override
    public CommentTwo selectByID(Integer key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<CommentTwo> getAllDate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected List<CommentTwo> getById(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
