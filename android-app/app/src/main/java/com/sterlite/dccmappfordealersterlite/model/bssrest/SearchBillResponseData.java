package com.sterlite.dccmappfordealersterlite.model.bssrest;

/**
 * Created by elitecore on 31/10/18.
 */

import java.io.Serializable;
import java.util.List;

public class SearchBillResponseData implements Serializable {
    private String responseCode;
    private String responseMessage;
    private double creditLimit;
    private double unBilledAmount;
    private String nextBillDate;
    private String formattedNextBillDate;
    private List<BillingDetailData> billingDetailDatas;

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return this.responseMessage;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public double getCreditLimit() {
        return this.creditLimit;
    }

    public void setUnBilledAmount(double unBilledAmount) {
        this.unBilledAmount = unBilledAmount;
    }

    public double getUnBilledAmount() {
        return this.unBilledAmount;
    }

    public void setNextBillDate(String nextBillDate) {
        this.nextBillDate = nextBillDate;
    }

    public String getNextBillDate() {
        return this.nextBillDate;
    }

    public void setFormattedNextBillDate(String formattedNextBillDate) {
        this.formattedNextBillDate = formattedNextBillDate;
    }

    public String getFormattedNextBillDate() {
        return this.formattedNextBillDate;
    }

    public void setBillingDetailDatas(List<BillingDetailData> billingDetailDatas) {
        this.billingDetailDatas = billingDetailDatas;
    }

    public List<BillingDetailData> getBillingDetailDatas() {
        return this.billingDetailDatas;
    }
}