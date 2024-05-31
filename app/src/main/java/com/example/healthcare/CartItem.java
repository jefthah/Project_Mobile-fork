package com.example.healthcare;

public class CartItem {
    private String product;
    private String price;

    public CartItem() {
        // Diperlukan untuk Firebase
    }

    public CartItem(String product, String price) {
        this.product = product;
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
