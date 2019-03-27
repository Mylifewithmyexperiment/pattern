package com.sterlite.dccmappfordealersterlite.model;

/**
 * Created by etech-10 on 8/9/18.
 */

public class ReccomandedPlanPOJO {
    String amount,sortDesc,currencyCode;

    public ReccomandedPlanPOJO() {
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSortDesc() {
        return sortDesc;
    }

    public void setSortDesc(String sortDesc) {
        this.sortDesc = sortDesc;
    }
}
