/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fastfood.entity;

public class LikeCommentOne extends CommentOne{

    private String userLike;
    private int like;
    private int quantityLike;
    private int quantityComment;
    
    public LikeCommentOne(String userLike, int like, int quantityLike, int iDCommentOne, String userComment, int iDProduct, String content, String dateComment, boolean vT, String tacGia, String img) {
        super(iDCommentOne, userComment, iDProduct, content, dateComment, vT, tacGia, img);
        this.userLike = userLike;
        this.like = like;
        this.quantityLike = quantityLike;
    }

    public LikeCommentOne(int iDCommentOne, String userComment, int iDProduct, String content, String dateComment, boolean vT, String tacGia, String img, int like, int quantityLike, int quantityComment) {
        super(iDCommentOne, userComment, iDProduct, content, dateComment, vT, tacGia, img);
        this.like = like;
        this.quantityLike = quantityLike;
        this.quantityComment = quantityComment;
    }
    
    

    public LikeCommentOne() {
    }

    public String getUserLike() {
        return userLike;
    }

    public void setUserLike(String userLike) {
        this.userLike = userLike;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getQuantityLike() {
        return quantityLike;
    }

    public void setQuantityLike(int quantityLike) {
        this.quantityLike = quantityLike;
    }

    public int getQuantityComment() {
        return quantityComment;
    }

    public void setQuantityComment(int quantityComment) {
        this.quantityComment = quantityComment;
    }
    
    public void increaseQuantityLike(){
        quantityLike ++;
    }
    
    public void decreaseQuantityLike(){
        quantityLike --;
    }
    
    public void increaseQuantityComment(){
        quantityComment++;
    }
    
    
}
