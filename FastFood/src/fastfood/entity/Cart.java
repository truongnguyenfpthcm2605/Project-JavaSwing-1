
package fastfood.entity;

public class Cart extends Product{
    private int quantityCart;

    public Cart(int idProduct, int idStore, int typeProduct, String name, double money, int quantity, String img, String note, boolean existss,int quantityCart) {
        super(idProduct, idStore, typeProduct, name, money, quantity, img, note, existss);
        this.quantityCart = quantityCart;
    }

    public Cart() {
    }

    public int getQuantityCart() {
        return quantityCart;
    }

    public void setQuantityCart(int quantityCart) {
        this.quantityCart = quantityCart;
    }
    
    public void increaseCart(){
        this.quantityCart++;
    }
    
    public void decreaseCart(){
        this.quantityCart--;
    }
    
    public double getPayMoney(){
        return Double.parseDouble(String.format("%.2f",quantityCart * this.getMoney()));
    }
    
    
}
