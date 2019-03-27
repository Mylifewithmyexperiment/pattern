package com.elitecorelib.andsf.pojo;

import java.io.Serializable;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by harshesh.soni on 9/22/2016.
 */
@RealmClass
public class ANDSFCircular implements Serializable, RealmModel {
    public String radius = "";
    public String longitude = "";
    public String latitude = "";

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
