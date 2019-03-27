package com.sterlite.dccmappfordealersterlite.model;

import java.io.Serializable;

public class Inventory implements Cloneable ,Serializable{
    private String id;
    private String number;
    private String numberType;
    private String price;
    private String discountText;
    private String expireDateText;
    private String luckyNumber;

    public String getLuckyNumber() {
        return luckyNumber;
    }

    public void setLuckyNumber(String luckyNumber) {
        this.luckyNumber = luckyNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscountText() {
        return discountText;
    }

    public void setDiscountText(String discountText) {
        this.discountText = discountText;
    }

    public String getExpireDateText() {
        return expireDateText;
    }

    public void setExpireDateText(String expireDateText) {
        this.expireDateText = expireDateText;
    }
}
