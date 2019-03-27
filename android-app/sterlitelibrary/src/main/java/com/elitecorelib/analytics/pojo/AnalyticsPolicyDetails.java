package com.elitecorelib.analytics.pojo;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class AnalyticsPolicyDetails extends RealmObject implements BaseDTO {
    @PrimaryKey
    private long id;
    private long IMEI;
    private Integer policyId;
    private String policyName;
    private String city;
    private Date fetchTime;
    private String PLMN;
    private String fetchStatus;
    private String reason;
    private String cellId;

    public AnalyticsPolicyDetails() {
    }

    public AnalyticsPolicyDetails(long IMEI, Integer policyId, String policyName, String city, Date fetchTime, String PLMN, String fetchStatus, String reason) {
        this.IMEI = IMEI;
        this.policyId = policyId;
        this.policyName = policyName;
        this.city = city;
        this.fetchTime = fetchTime;
        this.PLMN = PLMN;
        this.fetchStatus = fetchStatus;
        this.reason = reason;
    }

    public long getIMEI() {
        return IMEI;
    }

    public void setIMEI(long IMEI) {
        this.IMEI = IMEI;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getFetchTime() {
        return fetchTime;
    }

    public void setFetchTime(Date fetchTime) {
        this.fetchTime = fetchTime;
    }

    public String getPLMN() {
        return PLMN;
    }

    public void setPLMN(String PLMN) {
        this.PLMN = PLMN;
    }

    public String getFetchStatus() {
        return fetchStatus;
    }

    public void setFetchStatus(String fetchStatus) {
        this.fetchStatus = fetchStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }
}
