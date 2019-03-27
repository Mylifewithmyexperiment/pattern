package com.elitecorelib.core.pojo;

import java.io.Serializable;

public class PojoWiFiConnections implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6875101966867884700L;

    private Long wifiSettingId;
    private String ssidName;
    private String autoJoin;
    private String hidden;
    private String eapType;
    private String securityType;
    private String protocolType;
    private String userIdentity;
    private String password;
    private String isPreferable;
    private String notificationMessage;
    private String networkName;
    private String selectedNetwork;
    private String MCC;
    private String MNC;
    private String validForAllNetwork;
    //private Timestamp createDate;
    //private Long createdByStaffId;
    //private Timestamp lastModifiedDate;
    //private Long lastModifiedByStaffId;

    private PojoWiFiProfiles androidSetting;

    public PojoWiFiProfiles getAndroidSetting() {
        return androidSetting;
    }

    public void setAndroidSetting(PojoWiFiProfiles androidSetting) {
        this.androidSetting = androidSetting;
    }

    public Long getWifiSettingId() {
        return wifiSettingId;
    }

    public void setWifiSettingId(Long wifiSettingId) {
        this.wifiSettingId = wifiSettingId;
    }

    public String getSsidName() {
        return ssidName;
    }

    public void setSsidName(String ssidName) {
        this.ssidName = ssidName;
    }

    public String getAutoJoin() {
        return autoJoin;
    }

    public void setAutoJoin(String autoJoin) {
        this.autoJoin = autoJoin;
    }

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsPreferable() {
        return isPreferable;
    }

    public void setIsPreferable(String isPreferable) {
        this.isPreferable = isPreferable;
    }


    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getMCC() {
        return MCC;
    }

    public void setMCC(String mCC) {
        MCC = mCC;
    }

    public String getMNC() {
        return MNC;
    }

    public void setMNC(String mNC) {
        MNC = mNC;
    }


    public String getEapType() {
        return eapType;
    }

    public void setEapType(String eapType) {
        this.eapType = eapType;
    }

    public String getSelectedNetwork() {
        return selectedNetwork;
    }

    public void setSelectedNetwork(String selectedNetwork) {
        this.selectedNetwork = selectedNetwork;
    }

    public String getValidForAllNetwork() {
        return validForAllNetwork;
    }

    public void setValidForAllNetwork(String validForAllNetwork) {
        this.validForAllNetwork = validForAllNetwork;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((MCC == null) ? 0 : MCC.hashCode());
        result = prime * result + ((MNC == null) ? 0 : MNC.hashCode());
        result = prime * result + ((autoJoin == null) ? 0 : autoJoin.hashCode());
        result = prime * result + ((eapType == null) ? 0 : eapType.hashCode());
        result = prime * result + ((hidden == null) ? 0 : hidden.hashCode());
        result = prime * result + ((isPreferable == null) ? 0 : isPreferable.hashCode());

        result = prime * result + ((networkName == null) ? 0 : networkName.hashCode());
        result = prime * result + ((notificationMessage == null) ? 0 : notificationMessage.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((protocolType == null) ? 0 : protocolType.hashCode());
        result = prime * result + ((securityType == null) ? 0 : securityType.hashCode());
        result = prime * result + ((selectedNetwork == null) ? 0 : selectedNetwork.hashCode());
        result = prime * result + ((ssidName == null) ? 0 : ssidName.hashCode());
        result = prime * result + ((userIdentity == null) ? 0 : userIdentity.hashCode());
        result = prime * result + ((validForAllNetwork == null) ? 0 : validForAllNetwork.hashCode());
        result = prime * result + ((wifiSettingId == null) ? 0 : wifiSettingId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PojoWiFiConnections other = (PojoWiFiConnections) obj;
        if (MCC == null) {
            if (other.MCC != null)
                return false;
        } else if (!MCC.equals(other.MCC))
            return false;
        if (MNC == null) {
            if (other.MNC != null)
                return false;
        } else if (!MNC.equals(other.MNC))
            return false;
        if (autoJoin == null) {
            if (other.autoJoin != null)
                return false;
        } else if (!autoJoin.equals(other.autoJoin))
            return false;


        if (eapType == null) {
            if (other.eapType != null)
                return false;
        } else if (!eapType.equals(other.eapType))
            return false;
        if (hidden == null) {
            if (other.hidden != null)
                return false;
        } else if (!hidden.equals(other.hidden))
            return false;
        if (isPreferable == null) {
            if (other.isPreferable != null)
                return false;
        } else if (!isPreferable.equals(other.isPreferable))
            return false;

        if (networkName == null) {
            if (other.networkName != null)
                return false;
        } else if (!networkName.equals(other.networkName))
            return false;
        if (notificationMessage == null) {
            if (other.notificationMessage != null)
                return false;
        } else if (!notificationMessage.equals(other.notificationMessage))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (protocolType == null) {
            if (other.protocolType != null)
                return false;
        } else if (!protocolType.equals(other.protocolType))
            return false;
        if (securityType == null) {
            if (other.securityType != null)
                return false;
        } else if (!securityType.equals(other.securityType))
            return false;
        if (selectedNetwork == null) {
            if (other.selectedNetwork != null)
                return false;
        } else if (!selectedNetwork.equals(other.selectedNetwork))
            return false;
        if (ssidName == null) {
            if (other.ssidName != null)
                return false;
        } else if (!ssidName.equals(other.ssidName))
            return false;
        if (userIdentity == null) {
            if (other.userIdentity != null)
                return false;
        } else if (!userIdentity.equals(other.userIdentity))
            return false;
        if (validForAllNetwork == null) {
            if (other.validForAllNetwork != null)
                return false;
        } else if (!validForAllNetwork.equals(other.validForAllNetwork))
            return false;
        if (wifiSettingId == null) {
            if (other.wifiSettingId != null)
                return false;
        } else if (!wifiSettingId.equals(other.wifiSettingId))
            return false;
        return true;
    }


}
