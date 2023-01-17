/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fastfood.entity;

import java.util.Date;

/**
 *
 * @author Duy Cuong
 */
public class Bill {
    private int idBill;
    private String user;
    private int idProduct;
    private String datePurchase;
    private int quantity;
    private double money;
    private String img;
    private String name;

    public Bill() {
    }

    public Bill(String user, int idProduct, int quantity, double money) {
        this.user = user;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.money = money;
    }

    
    public Bill(int idBill, String user, int idProduct, String datePurchase, int quantity, double money, String img) {
        this.idBill = idBill;
        this.user = user;
        this.idProduct = idProduct;
        this.datePurchase = datePurchase;
        this.quantity = quantity;
        this.money = money;
        this.img = img;
    }

    public Bill(int idBill, String user, int idProduct, String datePurchase, int quantity, double money, String img, String name) {
        this.idBill = idBill;
        this.user = user;
        this.idProduct = idProduct;
        this.datePurchase = datePurchase;
        this.quantity = quantity;
        this.money = money;
        this.img = img;
        this.name = name;
    }

    
    

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(String datePurchase) {
        this.datePurchase = datePurchase;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
}
