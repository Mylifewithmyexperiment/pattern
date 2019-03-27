package com.elitecorelib.andsf.pojo;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by harshesh.soni on 9/22/2016.
 */
@RealmClass
public class ANDSFValidityArea implements Serializable, RealmModel {

    public RealmList<ANDSFWLANLocation> WLAN_Location;
    public String RPLMN = "";
    public String name = "";
    public RealmList<ANDSFLocation3GPP> location_3GPP;
    public RealmList<ANDSFwiMAXLocation> wiMAX_Location;
    public RealmList<ANDSFGeoLocation> geo_Location_;
    public RealmList<ANDSFWLANLocation> getWLAN_Location() {
        return WLAN_Location;
    }

    public void setWLAN_Location(RealmList<ANDSFWLANLocation> WLAN_Location) {
        this.WLAN_Location = WLAN_Location;
    }

    public String getRPLMN() {
        return RPLMN;
    }

    public void setRPLMN(String RPLMN) {
        this.RPLMN = RPLMN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<ANDSFLocation3GPP> getLocation_3GPP() {
        return location_3GPP;
    }

    public void setLocation_3GPP(RealmList<ANDSFLocation3GPP> location_3GPP) {
        this.location_3GPP = location_3GPP;
    }

    public RealmList<ANDSFwiMAXLocation> getWiMAX_Location() {
        return wiMAX_Location;
    }

    public void setWiMAX_Location(RealmList<ANDSFwiMAXLocation> wiMAX_Location) {
        this.wiMAX_Location = wiMAX_Location;
    }

    public RealmList<ANDSFGeoLocation> getGeo_Location_() {
        return geo_Location_;
    }

    public void setGeo_Location_(RealmList<ANDSFGeoLocation> geo_Location_) {
        this.geo_Location_ = geo_Location_;
    }
}
