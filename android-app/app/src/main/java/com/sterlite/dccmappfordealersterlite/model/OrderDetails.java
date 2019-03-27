package com.sterlite.dccmappfordealersterlite.model;

import java.util.ArrayList;

/**
 * Created by etech-10 on 5/9/18.
 */

public class OrderDetails {

    private String displayOrderId;
    private String orderDate;
    private String orderShipTo;
    private String orderBillTo;
    private String orderTotal;
    private String orderStatus;
    private String paymentMethod;
    private String grandTotal;
    private String shippingAmount;
    private Address billingAddressDetails;
    private Address shippingAddressDetails;
    private Address storeAddressDetails;
    private String storeName;
    private ArrayList<CartItem> productDetails;
    private String shippingMethod;
    private String itemTotal;
    private boolean isTrackable;
    private String totalBeforeTax;
    private String taxAmount;

    private String planName;
    private String planBenefits;
    private String deliveryAmount;
    private String number;
    private String numberAmount;
    private String simStatus;
    private String simAmount;
    private String deliveryDate;
    private String extraPlanDetail;
    private String userEmail;
    private String planAmount;
    private String deliveryType;

    private ArrayList<Track> arrTrackDetails;
    private String currentDeliveryStatus;

    public OrderDetails(String displayOrderId, String orderDate, String orderShipTo, String orderBillTo, String orderTotal, String orderStatus, String paymentMethod, String grandTotal, String shippingAmount, Address billingAddressDetails, Address shippingAddressDetails, Address storeAddressDetails, String storeName, ArrayList<CartItem> productDetails, String shippingMethod, String itemTotal, boolean isTrackable, String totalBeforeTax, String taxAmount) {
        this.displayOrderId = displayOrderId;
        this.orderDate = orderDate;
        this.orderShipTo = orderShipTo;
        this.orderBillTo = orderBillTo;
        this.orderTotal = orderTotal;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
        this.grandTotal = grandTotal;
        this.shippingAmount = shippingAmount;
        this.billingAddressDetails = billingAddressDetails;
        this.shippingAddressDetails = shippingAddressDetails;
        this.storeAddressDetails = storeAddressDetails;
        this.storeName = storeName;
        this.productDetails = productDetails;
        this.shippingMethod = shippingMethod;
        this.itemTotal = itemTotal;
        this.isTrackable = isTrackable;
        this.totalBeforeTax = totalBeforeTax;
        this.taxAmount = taxAmount;
    }

    public String getCurrentDeliveryStatus() {
        return currentDeliveryStatus;
    }

    public void setCurrentDeliveryStatus(String currentDeliveryStatus) {
        this.currentDeliveryStatus = currentDeliveryStatus;
    }

    public ArrayList<Track> getArrTrackDetails() {
        return arrTrackDetails;
    }

    public void setArrTrackDetails(ArrayList<Track> arrTrackDetails) {
        this.arrTrackDetails = arrTrackDetails;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public OrderDetails() {
    }

    public String getDisplayOrderId() {
        return displayOrderId;
    }

    public void setDisplayOrderId(String displayOrderId) {
        this.displayOrderId = displayOrderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderShipTo() {
        return orderShipTo;
    }

    public void setOrderShipTo(String orderShipTo) {
        this.orderShipTo = orderShipTo;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(String shippingAmount) {
        this.shippingAmount = shippingAmount;
    }


    public ArrayList<CartItem> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ArrayList<CartItem> productDetails) {
        this.productDetails = productDetails;
    }

    public boolean isTrackable() {
        return isTrackable;
    }

    public void setTrackable(boolean trackable) {
        isTrackable = trackable;
    }

    public String getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(String itemTotal) {
        this.itemTotal = itemTotal;
    }

    public String getTotalBeforeTax() {
        return totalBeforeTax;
    }

    public void setTotalBeforeTax(String totalBeforeTax) {
        this.totalBeforeTax = totalBeforeTax;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getOrderBillTo() {
        return orderBillTo;
    }

    public void setOrderBillTo(String orderBillTo) {
        this.orderBillTo = orderBillTo;
    }

    public Address getBillingAddressDetails() {
        return billingAddressDetails;
    }

    public void setBillingAddressDetails(Address billingAddressDetails) {
        this.billingAddressDetails = billingAddressDetails;
    }

    public Address getShippingAddressDetails() {
        return shippingAddressDetails;
    }

    public void setShippingAddressDetails(Address shippingAddressDetails) {
        this.shippingAddressDetails = shippingAddressDetails;
    }

    public Address getStoreAddressDetails() {
        return storeAddressDetails;
    }

    public void setStoreAddressDetails(Address storeAddressDetails) {
        this.storeAddressDetails = storeAddressDetails;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanBenefits() {
        return planBenefits;
    }

    public void setPlanBenefits(String planBenefits) {
        this.planBenefits = planBenefits;
    }

    public String getDeliveryAmount() {
        return deliveryAmount;
    }

    public void setDeliveryAmount(String deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumberAmount() {
        return numberAmount;
    }

    public void setNumberAmount(String numberAmount) {
        this.numberAmount = numberAmount;
    }

    public String getSimStatus() {
        return simStatus;
    }

    public void setSimStatus(String simStatus) {
        this.simStatus = simStatus;
    }

    public String getSimAmount() {
        return simAmount;
    }

    public void setSimAmount(String simAmount) {
        this.simAmount = simAmount;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getExtraPlanDetail() {
        return extraPlanDetail;
    }

    public void setExtraPlanDetail(String extraPlanDetail) {
        this.extraPlanDetail = extraPlanDetail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPlanAmount() {
        return planAmount;
    }

    public void setPlanAmount(String planAmount) {
        this.planAmount = planAmount;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "displayOrderId='" + displayOrderId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderShipTo='" + orderShipTo + '\'' +
                ", orderBillTo='" + orderBillTo + '\'' +
                ", orderTotal='" + orderTotal + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", grandTotal='" + grandTotal + '\'' +
                ", shippingAmount='" + shippingAmount + '\'' +
                ", billingAddressDetails=" + billingAddressDetails +
                ", shippingAddressDetails=" + shippingAddressDetails +
                ", storeAddressDetails=" + storeAddressDetails +
                ", storeName='" + storeName + '\'' +
                ", productDetails=" + productDetails +
                ", shippingMethod='" + shippingMethod + '\'' +
                ", itemTotal='" + itemTotal + '\'' +
                ", isTrackable=" + isTrackable +
                ", totalBeforeTax='" + totalBeforeTax + '\'' +
                ", taxAmount='" + taxAmount + '\'' +
                ", planName='" + planName + '\'' +
                ", planBenefits='" + planBenefits + '\'' +
                ", deliveryAmount='" + deliveryAmount + '\'' +
                ", number='" + number + '\'' +
                ", numberAmount='" + numberAmount + '\'' +
                ", simStatus='" + simStatus + '\'' +
                ", simAmount='" + simAmount + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", extraPlanDetail='" + extraPlanDetail + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", planAmount='" + planAmount + '\'' +
                ", deliveryType='" + deliveryType + '\'' +
                ", arrTrackDetails=" + arrTrackDetails +
                ", currentDeliveryStatus='" + currentDeliveryStatus + '\'' +
                '}';
    }
}
