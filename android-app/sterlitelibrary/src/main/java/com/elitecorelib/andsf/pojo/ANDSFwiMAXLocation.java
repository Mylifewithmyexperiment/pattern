package com.elitecorelib.andsf.pojo;

import java.io.Serializable;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by harshesh.soni on 9/23/2016.
 */
@RealmClass
public class ANDSFwiMAXLocation implements Serializable, RealmModel {
    public String NAP_ID = "";
    public String BS_ID = "";

    public String getNAP_ID() {
        return NAP_ID;
    }

    public void setNAP_ID(String NAP_ID) {
        this.NAP_ID = NAP_ID;
    }

    public String getBS_ID() {
        return BS_ID;
    }

    public void setBS_ID(String BS_ID) {
        this.BS_ID = BS_ID;
    }
}
