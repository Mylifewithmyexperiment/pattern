package com.elitecorelib.advertisements.pojo;

import java.io.Serializable;

/**
 * Created by harshesh.soni on 12/11/2018.
 */

public class MediaFiles implements Serializable {

    public MediaFile mediaFile;

    public MediaFile getMediaFile() {
        return mediaFile;
    }

    public void setMediaFile(MediaFile mediaFile) {
        this.mediaFile = mediaFile;
    }
}
