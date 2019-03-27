package com.sterlite.dccmappfordealersterlite.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Plan {

    private String planId;
    private String planType;
    private String planTitle;
    private String pricePerMonth;
    private String dataInGB;
    private String rolloverDataInGB;
    private String freeBenefitInRupee;
    private ArrayList<PlanBenefit> arrBenefits;
    private ArrayList<String> arrAdditionalBenefits;
    private String callRate;
    private String sms;
    private String validity;

    public String getFreeBenefitInRupee() {
        return freeBenefitInRupee;
    }

    public void setFreeBenefitInRupee(String freeBenefitInRupee) {
        this.freeBenefitInRupee = freeBenefitInRupee;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    public String getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(String pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    public String getDataInGB() {
        return dataInGB;
    }

    public void setDataInGB(String dataInGB) {
        this.dataInGB = dataInGB;
    }

    public String getRolloverDataInGB() {
        return rolloverDataInGB;
    }

    public void setRolloverDataInGB(String rolloverDataInGB) {
        this.rolloverDataInGB = rolloverDataInGB;
    }

    public ArrayList<PlanBenefit> getArrBenefits() {

        return arrBenefits;
    }

    public void setArrBenefits(ArrayList<PlanBenefit> arrBenefits) {
        this.arrBenefits = arrBenefits;
    }

    public ArrayList<String> getArrAdditionalBenefits() {
        return arrAdditionalBenefits;
    }

    public void setArrAdditionalBenefits(ArrayList<String> arrAdditionalBenefits) {
        this.arrAdditionalBenefits = arrAdditionalBenefits;
    }

    public String getCallRate() {
        return callRate;
    }

    public void setCallRate(String callRate) {
        this.callRate = callRate;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "planId='" + planId + '\'' +
                ", planType='" + planType + '\'' +
                ", planTitle='" + planTitle + '\'' +
                ", pricePerMonth='" + pricePerMonth + '\'' +
                ", dataInGB='" + dataInGB + '\'' +
                ", rolloverDataInGB='" + rolloverDataInGB + '\'' +
                ", freeBenefitInRupee='" + freeBenefitInRupee + '\'' +
                ", arrBenefits=" + arrBenefits +
                ", arrAdditionalBenefits=" + arrAdditionalBenefits +
                ", callRate='" + callRate + '\'' +
                ", sms='" + sms + '\'' +
                ", validity='" + validity + '\'' +
                '}';
    }

    public static ArrayList<Plan> sortArray(ArrayList<Plan> planItems) {
        Collections.sort(planItems, new Comparator<Plan>() {
            @Override
            public int compare(Plan o1, Plan o2) {
                return ((int)Float.parseFloat(o1.getPricePerMonth())) - ((int)Float.parseFloat(o2.getPricePerMonth()));
            }
        });
        return planItems;
    }
}
