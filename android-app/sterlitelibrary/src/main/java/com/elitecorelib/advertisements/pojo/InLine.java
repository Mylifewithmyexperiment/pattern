package com.elitecorelib.advertisements.pojo;

import java.io.Serializable;

/**
 * Created by harshesh.soni on 12/11/2018.
 */

public class InLine implements Serializable {

    public String AdSystem;
    public String AdTitle;
    public String Description;

    public Video video;
    public TrackingEvents trackingEvents;

    public String getAdSystem() {
        return AdSystem;
    }

    public void setAdSystem(String adSystem) {
        AdSystem = adSystem;
    }

    public String getAdTitle() {
        return AdTitle;
    }

    public void setAdTitle(String adTitle) {
        AdTitle = adTitle;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public TrackingEvents getTrackingEvents() {
        return trackingEvents;
    }

    public void setTrackingEvents(TrackingEvents trackingEvents) {
        this.trackingEvents = trackingEvents;
    }
}
