package com.elitecorelib.core.pojo;

/**
 * Created by salmankhan.yusufjai on 10/6/2015.
 */
public class PojoWifiAutoLogin {
    private String phoneNumber;
    private String OTP;
    private String packageId;
    private Boolean offload;
    private Integer channel;
    private String ipAddress;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "PojoWifiAutoLogin{" +
        "phoneNumber=" + phoneNumber +
                ", OTP=" + OTP +
                ", packageId='" + packageId + '\'' +
                ", offload=" + offload +
                ", channel=" + channel +
                ", ipAddress=" + ipAddress +
                '}';
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public Boolean isOffload() {
        return offload;
    }

    public void setOffload(Boolean offload) {
        this.offload = offload;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }
}
