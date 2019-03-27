package com.elitecorelib.analytics.pojo;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class AnalyticsOffloadSuccess extends RealmObject implements BaseDTO {
    @PrimaryKey
    private long id;
    private long IMEI;
    private Integer policyId;
    private String policyName;
    private String city;
    private String cellId;
    private String SSID;
    private String PLMN;
    private long uploadData;
    private long downloadData;
    private Date startTime;
    private Date endTime;
    private long totalSessionTime;
    private String eventTrigger;

    public AnalyticsOffloadSuccess() {
    }

    public AnalyticsOffloadSuccess(long IMEI, Integer policyId, String policyName, String city, String SSID, String PLMN, long uploadData, long downloadData, Date startTime, Date endTime, long totalSessionTime, String eventTrigger) {
        this.IMEI = IMEI;
        this.policyId = policyId;
        this.policyName = policyName;
        this.city = city;
        this.SSID = SSID;
        this.PLMN = PLMN;
        this.uploadData = uploadData;
        this.downloadData = downloadData;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalSessionTime = totalSessionTime;
        this.eventTrigger = eventTrigger;
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

    public String getSSID() {
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

    public long getUploadData() {
        return uploadData;
    }

    public void setUploadData(long uploadData) {
        this.uploadData = uploadData;
    }

    public long getDownloadData() {
        return downloadData;
    }

    public void setDownloadData(long downloadData) {
        this.downloadData = downloadData;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getTotalSessionTime() {
        return totalSessionTime;
    }

    public void setTotalSessionTime(long totalSessionTime) {
        this.totalSessionTime = totalSessionTime;
    }

    public String getEventTrigger() {
        return eventTrigger;
    }

    public void setEventTrigger(String eventTrigger) {
        this.eventTrigger = eventTrigger;
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
