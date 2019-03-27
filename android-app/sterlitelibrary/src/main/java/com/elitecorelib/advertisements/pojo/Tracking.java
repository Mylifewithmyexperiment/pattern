package com.elitecorelib.advertisements.pojo;

import java.io.Serializable;

/**
 * Created by harshesh.soni on 12/11/2018.
 */

public class Tracking implements Serializable {
    private String URL;
    private String event;
    private String COMPLETE_URL;
    private String MIDPOINT_URL;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getCOMPLETE_URL() {
        return COMPLETE_URL;
    }

    public void setCOMPLETE_URL(String COMPLETE_URL) {
        this.COMPLETE_URL = COMPLETE_URL;
    }

    public String getMIDPOINT_URL() {
        return MIDPOINT_URL;
    }

    public void setMIDPOINT_URL(String MIDPOINT_URL) {
        this.MIDPOINT_URL = MIDPOINT_URL;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
