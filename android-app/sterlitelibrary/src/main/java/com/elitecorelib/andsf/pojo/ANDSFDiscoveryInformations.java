package com.elitecorelib.andsf.pojo;

import java.io.Serializable;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by harshesh.soni on 9/23/2016.
 */
@RealmClass
public class ANDSFDiscoveryInformations implements Serializable, RealmModel {
    public String name = "";
    public String accessNetworkType = "";
    public ANDSFAccessNetworkArea accessNetworkArea;
    public ANDSFAccessNetworkInformationWLAN accessNetworkInformationWLAN;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessNetworkType() {
        return accessNetworkType;
    }

    public void setAccessNetworkType(String accessNetworkType) {
        this.accessNetworkType = accessNetworkType;
    }

    public ANDSFAccessNetworkArea getAccessNetworkArea() {
        return accessNetworkArea;
    }

    public void setAccessNetworkArea(ANDSFAccessNetworkArea accessNetworkArea) {
        this.accessNetworkArea = accessNetworkArea;
    }

    public ANDSFAccessNetworkInformationWLAN getAccessNetworkInformationWLAN() {
        return accessNetworkInformationWLAN;
    }

    public void setAccessNetworkInformationWLAN(ANDSFAccessNetworkInformationWLAN accessNetworkInformationWLAN) {
        this.accessNetworkInformationWLAN = accessNetworkInformationWLAN;
    }
}
