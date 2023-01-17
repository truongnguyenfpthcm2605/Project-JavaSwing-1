
package fastfood.entity;

public class CommentOne {
    private int iDCommentOne;
    private String userComment;
    private int iDProduct;
    private String content;
    private String dateComment;
    private boolean vT;
    private String tacGia;
    private String img;

    public CommentOne() {
    }

    public CommentOne(int iDCommentOne, String userComment, int iDProduct, String content, String dateComment, boolean vT, String tacGia, String img) {
        this.iDCommentOne = iDCommentOne;
        this.userComment = userComment;
        this.iDProduct = iDProduct;
        this.content = content;
        this.dateComment = dateComment;
        this.vT = vT;
        this.tacGia = tacGia;
        this.img = img;
    }

    public CommentOne(String userComment, int iDProduct, String content, String tacGia) {
        this.userComment = userComment;
        this.iDProduct = iDProduct;
        this.content = content;
        this.tacGia = tacGia;
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

    public int getiDProduct() {
        return iDProduct;
    }

    public void setiDProduct(int iDProduct) {
        this.iDProduct = iDProduct;
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

    public boolean isvT() {
        return vT;
    }

    public void setvT(boolean vT) {
        this.vT = vT;
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
