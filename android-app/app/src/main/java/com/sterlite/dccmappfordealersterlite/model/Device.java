package com.sterlite.dccmappfordealersterlite.model;

import java.util.ArrayList;

public class Device {

    private String id;
    private String name;
    private String price;
    private String priceAfterDisCount;
    private String priceSaved;
    private Boolean inStock;
    private ArrayList<String> arrFeatures;
    private ArrayList<String> arrTechSpecification;


    public String getPriceSaved() {
        return priceSaved;
    }

    public void setPriceSaved(String priceSaved) {
        this.priceSaved = priceSaved;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceAfterDisCount() {
        return priceAfterDisCount;
    }

    public void setPriceAfterDisCount(String priceAfterDisCount) {
        this.priceAfterDisCount = priceAfterDisCount;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public ArrayList<String> getArrFeatures() {
        return arrFeatures;
    }

    public void setArrFeatures(ArrayList<String> arrFeatures) {
        this.arrFeatures = arrFeatures;
    }

    public ArrayList<String> getArrTechSpecification() {
        return arrTechSpecification;
    }

    public void setArrTechSpecification(ArrayList<String> arrTechSpecification) {
        this.arrTechSpecification = arrTechSpecification;
    }
}
