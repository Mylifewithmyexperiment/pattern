package com.elitecorelib.andsf.pojo;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by harshesh.soni on 9/23/2016.
 */
@RealmClass
public class ANDSFAccessNetworkArea implements Serializable, RealmModel {
    public String name = "";
    public RealmList<ANDSFwiMAXLocation> wiMax_Locations;
    public RealmList<ANDSFWLANLocation> wlan_Locations;
    public ANDSFGeoLocation geo_Location_;
    public RealmList<ANDSFLocation3GPP> location_3gpps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<ANDSFwiMAXLocation> getWiMax_Locations() {
        return wiMax_Locations;
    }

    public void setWiMax_Locations(RealmList<ANDSFwiMAXLocation> wiMax_Locations) {
        this.wiMax_Locations = wiMax_Locations;
    }

    public RealmList<ANDSFWLANLocation> getWlan_Locations() {
        return wlan_Locations;
    }

    public void setWlan_Locations(RealmList<ANDSFWLANLocation> wlan_Locations) {
        this.wlan_Locations = wlan_Locations;
    }

    public ANDSFGeoLocation getGeo_Location_() {
        return geo_Location_;
    }

    public void setGeo_Location_(ANDSFGeoLocation geo_Location_) {
        this.geo_Location_ = geo_Location_;
    }

    public RealmList<ANDSFLocation3GPP> getLocation_3gpps() {
        return location_3gpps;
    }

    public void setLocation_3gpps(RealmList<ANDSFLocation3GPP> location_3gpps) {
        this.location_3gpps = location_3gpps;
    }
}
