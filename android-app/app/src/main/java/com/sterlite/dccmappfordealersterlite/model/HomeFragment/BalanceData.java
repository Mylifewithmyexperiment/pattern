package com.sterlite.dccmappfordealersterlite.model.HomeFragment;

import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;


public class BalanceData {

    @JsonProperty("serviceAlias")
    private String serviceAlias;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("balanceType")
    private String balanceType;
    @JsonProperty("usedBalance")
    private float usedBalance;
    @JsonProperty("unusedBalance")
    private float unusedBalance;
    @JsonProperty("availableBalance")
    private float availableBalance;
    @JsonProperty("totalUsageBalance")
    private float totalUsageBalance;
    @JsonProperty("actualUsageBalance")
    private float actualUsageBalance;
    @JsonProperty("validFromDate")
    private float validFromDate;
    @JsonProperty("validToDate")
    private float validToDate;
    @JsonProperty("planName")
    private String planName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonIgnore
    private int menuResId;
    @JsonIgnore
    private String menuName;
    @JsonIgnore
    private int menuCirleColor;



    @JsonProperty("serviceAlias")
    public String getServiceAlias() {
        return serviceAlias;
    }

    @JsonProperty("serviceAlias")
    public void setServiceAlias(String serviceAlias) {
        this.serviceAlias = serviceAlias;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("balanceType")
    public String getBalanceType() {
        return balanceType;
    }

    @JsonProperty("balanceType")
    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

    @JsonProperty("usedBalance")
    public float getUsedBalance() {
        return usedBalance;
    }

    @JsonProperty("usedBalance")
    public void setUsedBalance(float usedBalance) {
        this.usedBalance = usedBalance;
    }

    @JsonProperty("unusedBalance")
    public float getUnusedBalance() {
        return unusedBalance;
    }

    @JsonProperty("unusedBalance")
    public void setUnusedBalance(float unusedBalance) {
        this.unusedBalance = unusedBalance;
    }

    @JsonProperty("availableBalance")
    public float getAvailableBalance() {
        return availableBalance;
    }

    @JsonProperty("availableBalance")
    public void setAvailableBalance(float availableBalance) {
        this.availableBalance = availableBalance;
    }

    @JsonProperty("totalUsageBalance")
    public float getTotalUsageBalance() {
        return totalUsageBalance;
    }

    @JsonProperty("totalUsageBalance")
    public void setTotalUsageBalance(float totalUsageBalance) {
        this.totalUsageBalance = totalUsageBalance;
    }

    @JsonProperty("actualUsageBalance")
    public float getActualUsageBalance() {
        return actualUsageBalance;
    }

    @JsonProperty("actualUsageBalance")
    public void setActualUsageBalance(float actualUsageBalance) {
        this.actualUsageBalance = actualUsageBalance;
    }

    @JsonProperty("validFromDate")
    public float getValidFromDate() {
        return validFromDate;
    }

    @JsonProperty("validFromDate")
    public void setValidFromDate(float validFromDate) {
        this.validFromDate = validFromDate;
    }

    @JsonProperty("validToDate")
    public float getValidToDate() {
        return validToDate;
    }

    @JsonProperty("validToDate")
    public void setValidToDate(float validToDate) {
        this.validToDate = validToDate;
    }

    @JsonProperty("planName")
    public Object getPlanName() {
        return planName;
    }

    @JsonProperty("planName")
    public void setPlanName(String planName) {
        this.planName = planName;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public int getMenuResId() {
        return menuResId;
    }

    public void setMenuResId(int menuResId) {
        this.menuResId = menuResId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuCirleColor() {
        return menuCirleColor;
    }

    public void setMenuCirleColor(int menuCirleColor) {
        this.menuCirleColor = menuCirleColor;
    }

    @Override
    public String toString() {
        return "BalanceData{" +
                "serviceAlias='" + serviceAlias + '\'' +
                ", currency='" + currency + '\'' +
                ", balanceType='" + balanceType + '\'' +
                ", usedBalance=" + usedBalance +
                ", unusedBalance=" + unusedBalance +
                ", availableBalance=" + availableBalance +
                ", totalUsageBalance=" + totalUsageBalance +
                ", actualUsageBalance=" + actualUsageBalance +
                ", validFromDate=" + validFromDate +
                ", validToDate=" + validToDate +
                ", planName='" + planName + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}