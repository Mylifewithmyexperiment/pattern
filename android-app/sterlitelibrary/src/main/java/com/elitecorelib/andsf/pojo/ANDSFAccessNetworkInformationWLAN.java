package com.elitecorelib.andsf.pojo;

import java.io.Serializable;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by harshesh.soni on 9/23/2016.
 */
@RealmClass
public class ANDSFAccessNetworkInformationWLAN implements Serializable, RealmModel {
    public boolean ssidHidden = false;
    public String geoRadius = "";
    public String networkMode = "";
    public String nodeName = "";
    public String SSIDName = "";
    public String EAPTypeAuths = "";
    public String password = "";
    public int signalLevel;
    public boolean isHexPassword = false;
    public String bssid = "";
    public String hssid = "";
    public boolean autoJoin = false;
    public String securityType = "";
    public boolean policyHotspot = false;
    public boolean useWPAPSK = false;
    public String userName = "";
    public String longitude = "";
    public String authProtocols = "";
    public String latitude = "";

    public boolean isSsidHidden() {
        return ssidHidden;
    }

    public void setSsidHidden(boolean ssidHidden) {
        this.ssidHidden = ssidHidden;
    }

    public String getGeoRadius() {
        return geoRadius;
    }

    public void setGeoRadius(String geoRadius) {
        this.geoRadius = geoRadius;
    }

    public String getNetworkMode() {
        return networkMode;
    }

    public void setNetworkMode(String networkMode) {
        this.networkMode = networkMode;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getSSIDName() {
        return SSIDName;
    }

    public void setSSIDName(String SSIDName) {
        this.SSIDName = SSIDName;
    }

    public String getEAPTypeAuths() {
        return EAPTypeAuths;
    }

    public void setEAPTypeAuths(String EAPTypeAuths) {
        this.EAPTypeAuths = EAPTypeAuths;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSignalLevel() {
        return signalLevel;
    }

    public void setSignalLevel(int signalLevel) {
        this.signalLevel = signalLevel;
    }

    public boolean isHexPassword() {
        return isHexPassword;
    }

    public void setHexPassword(boolean hexPassword) {
        isHexPassword = hexPassword;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public String getHssid() {
        return hssid;
    }

    public void setHssid(String hssid) {
        this.hssid = hssid;
    }

    public boolean isAutoJoin() {
        return autoJoin;
    }

    public void setAutoJoin(boolean autoJoin) {
        this.autoJoin = autoJoin;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public boolean isPolicyHotspot() {
        return policyHotspot;
    }

    public void setPolicyHotspot(boolean policyHotspot) {
        this.policyHotspot = policyHotspot;
    }

    public boolean isUseWPAPSK() {
        return useWPAPSK;
    }

    public void setUseWPAPSK(boolean useWPAPSK) {
        this.useWPAPSK = useWPAPSK;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAuthProtocols() {
        return authProtocols;
    }

    public void setAuthProtocols(String authProtocols) {
        this.authProtocols = authProtocols;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
