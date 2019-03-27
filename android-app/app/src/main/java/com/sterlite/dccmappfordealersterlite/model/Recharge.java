package com.sterlite.dccmappfordealersterlite.model;

import java.io.Serializable;

import com.sterlite.dccmappfordealersterlite.Utils.Constants;

/**
 * Created by etech-10 on 8/9/18.
 */

public class Recharge implements Serializable {
    String number,amount,comment;
    private  String currencySymbol= Constants.CURRANCY_SYMBOL;

    public Recharge() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
