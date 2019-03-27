package com.elitecorelib.andsf.pojo;

import java.io.Serializable;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by harshesh.soni on 9/23/2016.
 */
@RealmClass
public class ANDSFWLANLocation implements Serializable, RealmModel {
    public String SSID = "";
    public String HESSID = "";
    public String BSSID = "";

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getHESSID() {
        return HESSID;
    }

    public void setHESSID(String HESSID) {
        this.HESSID = HESSID;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }
}
