package com.sterlite.dccmappfordealersterlite.model.MyOrdersListing;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by etech-10 on 18/9/18.
 */

public class Total {

    @JsonProperty("currencyIso")
    private String currencyIso;
    @JsonProperty("formattedValue")
    private String formattedValue;
    @JsonProperty("priceType")
    private String priceType;
    @JsonProperty("value")
    private Long value;

    @JsonProperty("currencyIso")
    public String getCurrencyIso() {
        return currencyIso;
    }

    @JsonProperty("currencyIso")
    public void setCurrencyIso(String currencyIso) {
        this.currencyIso = currencyIso;
    }

    @JsonProperty("formattedValue")
    public String getFormattedValue() {
        return formattedValue;
    }

    @JsonProperty("formattedValue")
    public void setFormattedValue(String formattedValue) {
        this.formattedValue = formattedValue;
    }

    @JsonProperty("priceType")
    public String getPriceType() {
        return priceType;
    }

    @JsonProperty("priceType")
    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    @JsonProperty("value")
    public Long getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(Long value) {
        this.value = value;
    }
}
