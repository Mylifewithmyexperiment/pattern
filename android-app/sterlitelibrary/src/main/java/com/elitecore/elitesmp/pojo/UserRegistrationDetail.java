package com.elitecore.elitesmp.pojo;

import java.io.Serializable;

/**
 * Created by salmankhan.yusufjai on 9/11/2015.
 */
public class UserRegistrationDetail implements Serializable {
    private String userName;
    private String passWord;
    private String paymentMethod;
    private String partnerName;
    private String currencyType;
    private String userAgent_Type;
    private String WIPURL;
    private String brandName;
    private String cardIdentifyNo;
    private CreditCardDetail creditCardDetail;
    private WifiPackageDetail wifiPackageDetail;
    private String registrationMethod;

    public String getUserAgent_Type() {
        return userAgent_Type;
    }

    public void setUserAgent_Type(String userAgent_Type) {
        this.userAgent_Type = userAgent_Type;
    }

    public String getWIPURL() {
        return WIPURL;
    }

    public void setWIPURL(String WIPURL) {
        this.WIPURL = WIPURL;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCardIdentifyNo() {
        return cardIdentifyNo;
    }

    public void setCardIdentifyNo(String cardIdentifyNo) {
        this.cardIdentifyNo = cardIdentifyNo;
    }

    public String getRegistrationMethod() {
        return registrationMethod;
    }

    public void setRegistrationMethod(String registrationMethod) {
        this.registrationMethod = registrationMethod;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public CreditCardDetail getCreditCardDetail() {
        return creditCardDetail;
    }

    public void setCreditCardDetail(CreditCardDetail creditCardDetail) {
        this.creditCardDetail = creditCardDetail;
    }

    public WifiPackageDetail getWifiPackageDetail() {
        return wifiPackageDetail;
    }

    public void setWifiPackageDetail(WifiPackageDetail wifiPackageDetail) {
        this.wifiPackageDetail = wifiPackageDetail;
    }

}
