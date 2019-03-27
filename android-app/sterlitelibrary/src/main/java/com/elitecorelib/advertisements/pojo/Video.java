package com.elitecorelib.advertisements.pojo;

import java.io.Serializable;

/**
 * Created by harshesh.soni on 12/11/2018.
 */

public class Video implements Serializable {

    public String Duration;
    public String AdID;

    public MediaFiles mediaFiles;

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getAdID() {
        return AdID;
    }

    public void setAdID(String adID) {
        AdID = adID;
    }

    public MediaFiles getMediaFiles() {
        return mediaFiles;
    }

    public void setMediaFiles(MediaFiles mediaFiles) {
        this.mediaFiles = mediaFiles;
    }
}
