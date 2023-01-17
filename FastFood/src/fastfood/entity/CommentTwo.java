
package fastfood.entity;

public class CommentTwo {
    private int iDCommentTwo;
    private int iDCommentOne;
    private String userComment;
    private String content;
    private String dateComment;
    private String tacGia;
    private String img;

    public CommentTwo() {
    }

    public CommentTwo(int iDCommentTwo, int iDCommentOne, String userComment, String content, String dateComment, String tacGia, String img) {
        this.iDCommentTwo = iDCommentTwo;
        this.iDCommentOne = iDCommentOne;
        this.userComment = userComment;
        this.content = content;
        this.dateComment = dateComment;
        this.tacGia = tacGia;
        this.img = img;
    }

    public CommentTwo(int iDCommentOne, String userComment, String content, String tacGia) {
        this.iDCommentOne = iDCommentOne;
        this.userComment = userComment;
        this.content = content;
        this.tacGia = tacGia;
    }
    
    

    public int getiDCommentTwo() {
        return iDCommentTwo;
    }

    public void setiDCommentTwo(int iDCommentTwo) {
        this.iDCommentTwo = iDCommentTwo;
    }

    public int getiDCommentOne() {
        return iDCommentOne;
    }

    public void setiDCommentOne(int iDCommentOne) {
        this.iDCommentOne = iDCommentOne;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateComment() {
        return dateComment;
    }

    public void setDateComment(String dateComment) {
        this.dateComment = dateComment;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    
}
