package com.elitecore.elitesmp.pojo;

import java.io.Serializable;

/**
 * Created by salmankhan.yusufjai on 9/11/2015.
 */
public class WifiPackageDetail implements Serializable {
    private String id;
    private String validity;
    private String price;

    @Override
    public String toString() {
        return "WifiPackageDetail{" +
                "id='" + id + '\'' +
                ", validity='" + validity + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WifiPackageDetail)) return false;

        WifiPackageDetail that = (WifiPackageDetail) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (validity != null ? !validity.equals(that.validity) : that.validity != null)
            return false;
        return !(price != null ? !price.equals(that.price) : that.price != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (validity != null ? validity.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
