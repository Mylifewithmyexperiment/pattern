package com.elitecorelib.core.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by chirag.parmar on 3/26/2018.
 */

public class PojoWiFiConnection extends RealmObject implements Parcelable {

    public static final Creator<PojoWiFiConnection> CREATOR = new Creator<PojoWiFiConnection>() {
        @Override
        public PojoWiFiConnection createFromParcel(Parcel in) {
            return new PojoWiFiConnection(in);
        }

        @Override
        public PojoWiFiConnection[] newArray(int size) {
            return new PojoWiFiConnection[size];
        }
    };
    @PrimaryKey
    private int id;
    private int profileId;
    private String ssidName;
    private String isSMPIntegrate;
    private String autoJoin;
    private String hidden;
    private String eapType;
    private String securityType;
    private String protocolType;
    private String userIdentity;
    private String password;
    private boolean isPreferable;
    private String notificationMessage;
    private String networkName;
    private String selectedNetwork;
    private String MCC;
    private String MNC;
    private String validForAllNetwork;
    private String sim_operator_name;
    private boolean showPassword = false;
    private boolean isOutOfRange;
    private boolean isWisprEnabled;
    private String wisprAuthenticationMethod;
    private String wisprUsarname;
    private String wisprPassword;
    private boolean isLocal;

    public PojoWiFiConnection() {

    }

    protected PojoWiFiConnection(Parcel in) {
        id = in.readInt();
        profileId = in.readInt();
        ssidName = in.readString();
        isSMPIntegrate = in.readString();
        autoJoin = in.readString();
        hidden = in.readString();
        eapType = in.readString();
        securityType = in.readString();
        protocolType = in.readString();
        userIdentity = in.readString();
        password = in.readString();
        isPreferable = in.readByte() != 0;
        notificationMessage = in.readString();
        networkName = in.readString();
        selectedNetwork = in.readString();
        MCC = in.readString();
        MNC = in.readString();
        validForAllNetwork = in.readString();
        sim_operator_name = in.readString();
        showPassword = in.readByte() != 0;
        isOutOfRange = in.readByte() != 0;
        isWisprEnabled = in.readByte() != 0;
        wisprAuthenticationMethod = in.readString();
        wisprUsarname = in.readString();
        wisprPassword = in.readString();
        isLocal = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(profileId);
        dest.writeString(ssidName);
        dest.writeString(isSMPIntegrate);
        dest.writeString(autoJoin);
        dest.writeString(hidden);
        dest.writeString(eapType);
        dest.writeString(securityType);
        dest.writeString(protocolType);
        dest.writeString(userIdentity);
        dest.writeString(password);
        dest.writeByte((byte) (isPreferable ? 1 : 0));
        dest.writeString(notificationMessage);
        dest.writeString(networkName);
        dest.writeString(selectedNetwork);
        dest.writeString(MCC);
        dest.writeString(MNC);
        dest.writeString(validForAllNetwork);
        dest.writeString(sim_operator_name);
        dest.writeByte((byte) (showPassword ? 1 : 0));
        dest.writeByte((byte) (isOutOfRange ? 1 : 0));
        dest.writeByte((byte) (isWisprEnabled ? 1 : 0));
        dest.writeString(wisprAuthenticationMethod);
        dest.writeString(wisprUsarname);
        dest.writeString(wisprPassword);
        dest.writeByte((byte) (isLocal ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        PojoWiFiConnection wiFiConnection = (PojoWiFiConnection) obj;
        return this.ssidName.equals(wiFiConnection.ssidName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getSsidName() {
        return ssidName;
    }

    public void setSsidName(String ssidName) {
        this.ssidName = ssidName;
    }

    public String getIsSMPIntegrate() {
        return isSMPIntegrate;
    }

    public void setIsSMPIntegrate(String isSMPIntegrate) {
        this.isSMPIntegrate = isSMPIntegrate;
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

    public String getEapType() {
        return eapType;
    }

    public void setEapType(String eapType) {
        this.eapType = eapType;
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

    public boolean isPreferable() {
        return isPreferable;
    }

    public void setPreferable(boolean preferable) {
        isPreferable = preferable;
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

    public String getSelectedNetwork() {
        return selectedNetwork;
    }

    public void setSelectedNetwork(String selectedNetwork) {
        this.selectedNetwork = selectedNetwork;
    }

    public String getMCC() {
        return MCC;
    }

    public void setMCC(String MCC) {
        this.MCC = MCC;
    }

    public String getMNC() {
        return MNC;
    }

    public void setMNC(String MNC) {
        this.MNC = MNC;
    }

    public String getValidForAllNetwork() {
        return validForAllNetwork;
    }

    public void setValidForAllNetwork(String validForAllNetwork) {
        this.validForAllNetwork = validForAllNetwork;
    }

    public String getSim_operator_name() {
        return sim_operator_name;
    }

    public void setSim_operator_name(String sim_operator_name) {
        this.sim_operator_name = sim_operator_name;
    }

    public boolean isShowPassword() {
        return showPassword;
    }

    public void setShowPassword(boolean showPassword) {
        this.showPassword = showPassword;
    }

    public boolean isOutOfRange() {
        return isOutOfRange;
    }

    public void setOutOfRange(boolean outOfRange) {
        isOutOfRange = outOfRange;
    }

    public boolean isWisprEnabled() {
        return isWisprEnabled;
    }

    public void setWisprEnabled(boolean wisprEnabled) {
        isWisprEnabled = wisprEnabled;
    }

    public String getWisprAuthenticationMethod() {
        return wisprAuthenticationMethod;
    }

    public void setWisprAuthenticationMethod(String wisprAuthenticationMethod) {
        this.wisprAuthenticationMethod = wisprAuthenticationMethod;
    }

    public String getWisprUsarname() {
        return wisprUsarname;
    }

    public void setWisprUsarname(String wisprUsarname) {
        this.wisprUsarname = wisprUsarname;
    }

    public String getWisprPassword() {
        return wisprPassword;
    }

    public void setWisprPassword(String wisprPassword) {
        this.wisprPassword = wisprPassword;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }
}
