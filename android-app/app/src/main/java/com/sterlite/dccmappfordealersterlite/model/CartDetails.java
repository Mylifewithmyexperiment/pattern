package com.sterlite.dccmappfordealersterlite.model;

import java.util.ArrayList;

/**
 * Created by etech-10 on 31/8/18.
 */

public class CartDetails {
    ArrayList<CartItem> cartItemList;
    String grandTotal;

    public ArrayList<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(ArrayList<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }
}
