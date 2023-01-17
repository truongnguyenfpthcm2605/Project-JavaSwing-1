
package fastfood.entity;


public class Product {
    private int idProduct;
    private int idStore;
    private int typeProduct;
    private String name;
    private double money;
    private int quantity;
    private String img;
    private String note;
    private boolean existss;
    private int slLike;
    private boolean like;
    public Product() {
    }

    public Product(int idProduct, int idStore) {
        this.idProduct = idProduct;
        this.idStore = idStore;
    }

    public Product(int idProduct, String name, boolean existss) {
        this.idProduct = idProduct;
        this.name = name;
        this.existss = existss;
    }
    
    

    public Product(int idProduct, int quantity, String name, double money, String img) {
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.name = name;
        this.money = money;
        this.img = img;
    }
    
    
    
    public Product(int idProduct, int typeProduct, int quantity, int idStore, String name, String note, double money, String img, boolean existss) {
        this.idProduct = idProduct;
        this.typeProduct = typeProduct;
        this.quantity = quantity;
        this.idStore = idStore;
        this.name = name;
        this.note = note;
        this.money = money;
        this.img = img;
        this.existss = existss;
    }

    public Product(int typeProduct, int quantity, int idStore, String name, String note, double money, String img) {
        this.typeProduct = typeProduct;
        this.quantity = quantity;
        this.idStore = idStore;
        this.name = name;
        this.note = note;
        this.money = money;
        this.img = img;
    }

    public Product(int idProduct, int idStore, int typeProduct, String name, double money, int quantity, String img, String note, boolean existss) {
        this.idProduct = idProduct;
        this.idStore = idStore;
        this.typeProduct = typeProduct;
        this.name = name;
        this.money = money;
        this.quantity = quantity;
        this.img = img;
        this.note = note;
        this.existss = existss;
    }

    public Product(int idProduct, int idStore, int typeProduct, String name, double money, int quantity, String img, String note, boolean existss, int slLike, boolean like) {
        this.idProduct = idProduct;
        this.idStore = idStore;
        this.typeProduct = typeProduct;
        this.name = name;
        this.money = money;
        this.quantity = quantity;
        this.img = img;
        this.note = note;
        this.existss = existss;
        this.slLike = slLike;
        this.like = like;
    }
    
    
    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(int typeProduct) {
        this.typeProduct = typeProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIdStore() {
        return idStore;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isExistss() {
        return existss;
    }

    public void setExistss(boolean existss) {
        this.existss = existss;
    }

    public int getSlLike() {
        return slLike;
    }

    public void setSlLike(int slLike) {
        this.slLike = slLike;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
    
    public void increaseLike(){
        slLike++;
    }
    
    public void decreaseLike(){
        slLike--;
    }
    
    public Product getProduct(){
        Product p = new Product();
        p.setIdProduct(this.idProduct);
        p.setExistss(this.existss);
        p.setIdStore(this.idStore);
        p.setImg(this.img);
        p.setMoney(this.money);
        p.setName(this.name);
        p.setNote(this.note);
        p.setQuantity(this.quantity);
        p.setTypeProduct(this.typeProduct);
        return p;
    }

  
}
