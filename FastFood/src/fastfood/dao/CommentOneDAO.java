
package fastfood.dao;

import fastfood.entity.CommentOne;
import fastfood.sql.JDBCHelper;
import java.util.List;

public class CommentOneDAO extends FastFoodDAO<CommentOne, Integer>{

    @Override
    public void insert(CommentOne enity) {
        String sql = "insert into CommentOne(USERS,IDProduct,Content,tacGia) values (?,?,?,?)";
        JDBCHelper.update(sql, enity.getUserComment(),enity.getiDProduct(),enity.getContent(),enity.getTacGia());
    }

    @Override
    public void update(CommentOne enity) {
        String sql = "update CommentOne set viTri = 1 where IDComment = ?";
        JDBCHelper.update(sql, enity.getiDCommentOne());
    }

    @Override
    public void delete(Integer key) {
        String sql = "delete CommentOne where IDComment = ?";
        JDBCHelper.update(sql, key);
    }

    @Override
    public CommentOne selectByID(Integer key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<CommentOne> getAllDate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected List<CommentOne> getById(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
