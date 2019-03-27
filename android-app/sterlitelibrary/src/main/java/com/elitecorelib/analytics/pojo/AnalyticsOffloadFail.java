package com.elitecorelib.analytics.pojo;


import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class AnalyticsOffloadFail extends RealmObject implements BaseDTO {

    @PrimaryKey
    private long id;
    private long IMEI;
    private String cellId;
    private String SSID;
    private String PLMN;
    private Integer policyId;
    private String policyName;
    private Date timestamp;
    private String connectionStatus;
    private String categoryType;
    private String categoryName;
    private String successValue;
    private String actualValue;
    private String evalutionid;

    public AnalyticsOffloadFail() {
    }

    public AnalyticsOffloadFail(long IMEI, String cellId, String SSID, String PLMN, Integer policyId, String policyName, Date timestamp, String connectionStatus, String categoryType, String categoryName, String successValue, String actualValue) {
        this.IMEI = IMEI;
        this.cellId = cellId;
        this.SSID = SSID;
        this.PLMN = PLMN;
        this.policyId = policyId;
        this.policyName = policyName;
        this.timestamp = timestamp;
        this.connectionStatus = connectionStatus;
        this.categoryType = categoryType;
        this.categoryName = categoryName;
        this.successValue = successValue;
        this.actualValue = actualValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIMEI() {
        return IMEI;
    }

    public void setIMEI(long IMEI) {
        this.IMEI = IMEI;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public String getSSId() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getPLMN() {
        return PLMN;
    }

    public void setPLMN(String PLMN) {
        this.PLMN = PLMN;
    }

    public Integer getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSuccessValue() {
        return successValue;
    }

    public void setSuccessValue(String successValue) {
        this.successValue = successValue;
    }

    public String getActualValue() {
        return actualValue;
    }

    public void setActualValue(String actualValue) {
        this.actualValue = actualValue;
    }

    public String getEvalutionid() {
        return evalutionid;
    }

    public void setEvalutionid(String evalutionid) {
        this.evalutionid = evalutionid;
    }

    public String getSSID() {
        return SSID;
    }
}
