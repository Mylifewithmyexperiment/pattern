package com.sterlite.dccmappfordealersterlite.model;


import java.io.Serializable;
import java.util.Calendar;

public class Address implements Serializable {
    String fname,lname,mobileNo,addressLine1,addressLine2,city,state,country,pin;
    Calendar date;
    boolean isUseSameForShipping;

    public Address(String fname, String lname, String mobileNo, String addressLine1, String addressLine2, String city, String state, String country, String pin, boolean isUseSameForShipping) {
        this.fname = fname;
        this.lname = lname;
        this.mobileNo = mobileNo;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pin = pin;
        this.isUseSameForShipping = isUseSameForShipping;
    }

    public Address() {
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public boolean getIsUseSameForShipping() {
        return isUseSameForShipping;
    }

    public void setIsUseSameForShipping(boolean isUseSameForShipping) {
        this.isUseSameForShipping = isUseSameForShipping;
    }

    @Override
    public String toString() {
        return "Address{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", pin='" + pin + '\'' +
                ", date=" + date +
                ", isUseSameForShipping=" + isUseSameForShipping +
                '}';
    }
}
