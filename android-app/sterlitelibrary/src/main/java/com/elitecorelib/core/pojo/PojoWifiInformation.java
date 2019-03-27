package com.elitecorelib.core.pojo;

import java.io.Serializable;

/**
 * Created by salmankhan.yusufjai on 9/28/2015.
 */
public class PojoWifiInformation implements Serializable,Comparable<PojoWifiInformation> {
    private Integer wifiInfoId;
    private String ssidName;
    private Boolean operatorWifi;
    private Integer priority;
    private Boolean autoLogin;
    private String securityMethod;
    private String identity;
    private String password;
    private String authenMethod;
    private String phase2Authentication;
    private Integer autoRemovealTimerInterval;
    private Boolean delteOnTurnOffWiFi;
    @Override
    public String toString() {
        return "PojoWifiInformation{" +
                "wifiInfoId=" + wifiInfoId +
                ", ssidName='" + ssidName + '\'' +
                ", operatorWifi=" + operatorWifi +
                ", priority=" + priority +
                ", autoLogin=" + autoLogin +
                ", securityMethod='" + securityMethod + '\'' +
                ", identity='" + identity + '\'' +
                ", password='" + password + '\'' +
                ", authenMethod='" + authenMethod + '\'' +
                ", phase2Authentication='" + phase2Authentication + '\'' +
                ", delteOnTurnOffWiFi=" + delteOnTurnOffWiFi +
                ", autoRemovealTimerInterval=" + autoRemovealTimerInterval +
                '}';
    }



    public Boolean getDelteOnTurnOffWiFi() {
        return delteOnTurnOffWiFi;
    }

    public void setDelteOnTurnOffWiFi(Boolean delteOnTurnOffWiFi) {
        this.delteOnTurnOffWiFi = delteOnTurnOffWiFi;
    }




    public void setAutoRemovealTimerInterval(Integer autoRemovealTimerInterval) {
        this.autoRemovealTimerInterval = autoRemovealTimerInterval;
    }


    public Integer getAutoRemovealTimerInterval() {
        return autoRemovealTimerInterval;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PojoWifiInformation)) return false;

        PojoWifiInformation that = (PojoWifiInformation) o;

        return !(ssidName != null ? !ssidName.equals(that.ssidName) : that.ssidName != null);

    }

    @Override
    public int hashCode() {
        return ssidName != null ? ssidName.hashCode() : 0;
    }

    public String getPhase2Authentication() {
        return phase2Authentication;
    }

    public void setPhase2Authentication(String phase2Authentication) {
        this.phase2Authentication = phase2Authentication;
    }

    public Boolean isOperatorWifi() {
        return operatorWifi;
    }

    public void setOperatorWifi(Boolean isTataDocmoWifi) {
        this.operatorWifi = isTataDocmoWifi;
    }

    public Boolean isAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(Boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public Integer getWifiInfoId() {
        return wifiInfoId;
    }

    public void setWifiInfoId(Integer wifiInfoId) {
        this.wifiInfoId = wifiInfoId;
    }

    public String getSsidName() {
        return ssidName;
    }

    public void setSsidName(String ssidName) {
        this.ssidName = ssidName;
    }

    public String getSecurityMethod() {
        return securityMethod;
    }

    public void setSecurityMethod(String securityMethod) {
        this.securityMethod = securityMethod;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthenMethod() {
        return authenMethod;
    }

    public void setAuthenMethod(String authenMethod) {
        this.authenMethod = authenMethod;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(PojoWifiInformation another) {
        if(this.isOperatorWifi()) {
            if(another.isOperatorWifi()) {
               return compareObject(another);
            }
            return -1;
        } else {
            return compareObject(another);
        }
    }
    private int compareObject(PojoWifiInformation another) {
        int i = this.getPriority().compareTo(another.getPriority());
        if(i != 0)
            return i;
        return this.getSsidName().compareTo(another.getSsidName());
    }
}
