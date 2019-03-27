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
public class ANDSFGeoLocation implements Serializable,RealmModel {

    public String geoLocationName = "";
    public RealmList<ANDSFCircular> circular;

    public String getGeoLocationName() {
        return geoLocationName;
    }

    public void setGeoLocationName(String geoLocationName) {
        this.geoLocationName = geoLocationName;
    }
    public RealmList<ANDSFCircular> getCircular() {
        return circular;
    }

    public void setCircular(RealmList<ANDSFCircular> circular) {
        this.circular = circular;
    }
}
