package com.elitecorelib.andsf.pojo;

import java.io.Serializable;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by harshesh.soni on 9/22/2016.
 */
@RealmClass
public class ANDSFExt implements Serializable, RealmModel {

    public int batteryLife;
    public int wifiStrength;

    public int getBatteryLife() {
        return batteryLife;
    }

    public void setBatteryLife(int batteryLife) {
        this.batteryLife = batteryLife;
    }

    public int getWifiStrength() {
        return wifiStrength;
    }

    public void setWifiStrength(int wifiStrength) {
        this.wifiStrength = wifiStrength;
    }
}
