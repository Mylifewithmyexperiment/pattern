package com.elitecorelib.core.pojo;

import java.io.Serializable;

public class PojoConfigModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String serverUnreachbleMessage;
    private int communicationMode;
    private int locationSyncRange;
    private int nfCallBackMode;
    private int nfCallBackInterval;
    private int adRefreshInterval;
    private String termsAndConditionMode;
    private String termsAndCondition;
    private int syncIntervalTime;
    private int adEnable;
    private int wifiConnectionTimeout;
    private String wifiSetting;
    private String userIdentity;
    private int locationBaseNotification;
    private int eventAnalyticsInterval;
    private String eventAnalyticsMode;
    private int eventAnalyticsEnable;

    public int getLocationBaseNotification() {
        return locationBaseNotification;
    }

    public void setLocationBaseNotification(int locationBaseNotification) {
        this.locationBaseNotification = locationBaseNotification;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }

    public int getWifiConnectionTimeout() {
        return wifiConnectionTimeout;
    }

    public void setWifiConnectionTimeout(int wifiConnectionTimeout) {
        this.wifiConnectionTimeout = wifiConnectionTimeout;
    }

    public String getWifiSetting() {
        return wifiSetting;
    }

    public void setWifiSetting(String wifiSetting) {
        this.wifiSetting = wifiSetting;
    }

    public String getTermsAndConditionMode() {
        return termsAndConditionMode;
    }

    public void setTermsAndConditionMode(String termsAndConditionMode) {
        this.termsAndConditionMode = termsAndConditionMode;
    }

    public String getTermsAndCondition() {
        return termsAndCondition;
    }

    public void setTermsAndCondition(String termsAndCondition) {
        this.termsAndCondition = termsAndCondition;
    }

    public int getSyncIntervalTime() {
        return syncIntervalTime;
    }

    public void setSyncIntervalTime(int syncIntervalTime) {
        this.syncIntervalTime = syncIntervalTime;
    }

    public int getAdEnable() {
        return adEnable;
    }

    public void setAdEnable(int adEnable) {
        this.adEnable = adEnable;
    }

    public int getAdRefreshInterval() {
        return adRefreshInterval;
    }

    public void setAdRefreshInterval(int adRefreshInterval) {
        this.adRefreshInterval = adRefreshInterval;
    }

    public int getNfCallBackMode() {
        return nfCallBackMode;
    }

    public void setNfCallBackMode(int nfCallBackMode) {
        this.nfCallBackMode = nfCallBackMode;
    }

    public int getNfCallBackInterval() {
        return nfCallBackInterval;
    }

    public void setNfCallBackInterval(int nfCallBackInterval) {
        this.nfCallBackInterval = nfCallBackInterval;
    }

    public String getServerUnreachbleMessage() {
        return serverUnreachbleMessage;
    }

    public void setServerUnreachbleMessage(String serverUnreachbleMessage) {
        this.serverUnreachbleMessage = serverUnreachbleMessage;
    }

    public int getCommunicationMode() {
        return communicationMode;
    }

    public void setCommunicationMode(int communicationMode) {
        this.communicationMode = communicationMode;
    }

    public int getLocationSyncRange() {
        return locationSyncRange;
    }

    public void setLocationSyncRange(int locationSyncRange) {
        this.locationSyncRange = locationSyncRange;
    }

    public int getEventAnalyticsInterval() {
        return eventAnalyticsInterval;
    }

    public void setEventAnalyticsInterval(int eventAnalyticsInterval) {
        this.eventAnalyticsInterval = eventAnalyticsInterval;
    }

    public String getEventAnalyticsMode() {
        return eventAnalyticsMode;
    }

    public void setEventAnalyticsMode(String eventAnalyticsMode) {
        this.eventAnalyticsMode = eventAnalyticsMode;
    }

    public int getEventAnalyticsEnable() {
        return eventAnalyticsEnable;
    }

    public void setEventAnalyticsEnable(int eventAnalyticsEnable) {
        this.eventAnalyticsEnable = eventAnalyticsEnable;
    }
}
