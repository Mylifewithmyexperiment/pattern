package com.elitecorelib.advertisements.pojo;

import java.io.Serializable;

/**
 * Created by harshesh.soni on 12/11/2018.
 */

public class TrackingEvents implements Serializable {
    public Tracking tracking;

    public Tracking getTracking() {
        return tracking;
    }

    public void setTracking(Tracking tracking) {
        this.tracking = tracking;
    }
}
