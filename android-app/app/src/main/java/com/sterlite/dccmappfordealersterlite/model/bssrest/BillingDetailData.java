package com.sterlite.dccmappfordealersterlite.model.bssrest;

/**
 * Created by elitecore on 31/10/18.
 */

import java.io.Serializable;

import com.sterlite.dccmappfordealersterlite.model.bssrest.receivablemethod.OnlineMethodData;

public class BillingDetailData implements Serializable,Cloneable {
    private double totalAmount;
    private String billDate;
    private String formattedBillDate;
    private String billNumber;
    private String billStatus;
    private String currencyCode;
    private String billType;
    private double unpaidAmount;
    private String billDueDate;
    private String formattedBillDueDate;
    private String billCycle;
    private String paymentDate;
    private String formattedPaymentDate;
    private String externalSystemPaymentNumber;
    private String transactionId;
    private String billExportPath;
    private String billFileName;
    private OnlineMethodData onlineMethod;

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return this.totalAmount;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getBillDate() {
        return this.billDate;
    }

    public void setFormattedBillDate(String formattedBillDate) {
        this.formattedBillDate = formattedBillDate;
    }

    public String getFormattedBillDate() {
        return this.formattedBillDate;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getBillNumber() {
        return this.billNumber;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getBillStatus() {
        return this.billStatus;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillType() {
        return this.billType;
    }

    public void setUnpaidAmount(double unpaidAmount) {
        this.unpaidAmount = unpaidAmount;
    }

    public double getUnpaidAmount() {
        return this.unpaidAmount;
    }

    public void setBillDueDate(String billDueDate) {
        this.billDueDate = billDueDate;
    }

    public String getBillDueDate() {
        return this.billDueDate;
    }

    public void setFormattedBillDueDate(String formattedBillDueDate) {
        this.formattedBillDueDate = formattedBillDueDate;
    }

    public String getFormattedBillDueDate() {
        return this.formattedBillDueDate;
    }

    public void setBillCycle(String billCycle) {
        this.billCycle = billCycle;
    }

    public String getBillCycle() {
        return this.billCycle;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentDate() {
        return this.paymentDate;
    }

    public void setFormattedPaymentDate(String formattedPaymentDate) {
        this.formattedPaymentDate = formattedPaymentDate;
    }

    public String getFormattedPaymentDate() {
        return this.formattedPaymentDate;
    }

    public void setExternalSystemPaymentNumber(String externalSystemPaymentNumber) {
        this.externalSystemPaymentNumber = externalSystemPaymentNumber;
    }

    public String getExternalSystemPaymentNumber() {
        return this.externalSystemPaymentNumber;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setBillExportPath(String billExportPath) {
        this.billExportPath = billExportPath;
    }

    public String getBillExportPath() {
        return this.billExportPath;
    }

    public void setBillFileName(String billFileName) {
        this.billFileName = billFileName;
    }

    public String getBillFileName() {
        return this.billFileName;
    }

    public void setOnlineMethod(OnlineMethodData onlineMethod) {
        this.onlineMethod = onlineMethod;
    }

    public OnlineMethodData getOnlineMethod() {
        return this.onlineMethod;
    }
}