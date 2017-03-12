package com.example.nader.grapesnberries;

public class Product {

    String id, productDescription;
    int price;
    Image image;

    public Product(String id, String productDescription, int price, Image image) {
        this.id = id;
        this.productDescription = productDescription;
        this.price = price;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
