package fastfood.entity;

public class LikeCommentTwo extends CommentTwo {

    private String userLike;
    private int like;
    private int quantityLike;

    public LikeCommentTwo() {
    }

    public LikeCommentTwo(String userLike, int like, int quantityLike, int iDCommentTwo, int iDCommentOne, String userComment, String content, String dateComment, String tacGia, String img) {
        super(iDCommentTwo, iDCommentOne, userComment, content, dateComment, tacGia, img);
        this.userLike = userLike;
        this.like = like;
        this.quantityLike = quantityLike;
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

    public void increaseQuantityLike() {
        quantityLike++;
    }

    public void decreaseQuantityLike() {
        quantityLike--;
    }
}
