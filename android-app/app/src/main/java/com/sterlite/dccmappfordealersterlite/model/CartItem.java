package com.sterlite.dccmappfordealersterlite.model;

/**
 * Created by etech-10 on 31/8/18.
 */

public class CartItem {
   String imageUrl,title, description,price,rowTotal;
   int quantity,maxQuantity;

    public CartItem(String imageUrl, String title, String description, String price, String rowTotal, int quantity, int maxQuantity) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.price = price;
        this.rowTotal = rowTotal;
        this.quantity = quantity;
        this.maxQuantity = maxQuantity;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRowTotal() {
        return rowTotal;
    }

    public void setRowTotal(String rowTotal) {
        this.rowTotal = rowTotal;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }
}
