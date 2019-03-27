package com.sterlite.dccmappfordealersterlite.data.network.model;

/**
 * Created by etech3 on 2/7/18.
 */

public class DeviceToken {
    private String userId;
    private String os;
    private String deviceToken;
    private String deviceId;
    private String deviceName;
    private String deviceModel;
    private String deviceVersion;
    private String environment;
    private String isBadgeEnable;
    private String isAlertEnable;
    private String isSoundEnable;
    private String language;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getIsBadgeEnable() {
        return isBadgeEnable;
    }

    public void setIsBadgeEnable(String isBadgeEnable) {
        this.isBadgeEnable = isBadgeEnable;
    }

    public String getIsAlertEnable() {
        return isAlertEnable;
    }

    public void setIsAlertEnable(String isAlertEnable) {
        this.isAlertEnable = isAlertEnable;
    }

    public String getIsSoundEnable() {
        return isSoundEnable;
    }

    public void setIsSoundEnable(String isSoundEnable) {
        this.isSoundEnable = isSoundEnable;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
