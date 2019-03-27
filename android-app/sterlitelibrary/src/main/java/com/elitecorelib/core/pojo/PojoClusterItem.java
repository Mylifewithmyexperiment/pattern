package com.elitecorelib.core.pojo;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Chirag Parmar on 28-Mar-17.
 */

public class PojoClusterItem implements ClusterItem {

    private final LatLng mPosition;
    private String mTitle;
    private String mSnippet;
    private int mIcon;

    public PojoClusterItem(double lat, double lng, String snippet, int icon) {
        mPosition = new LatLng(lat, lng);
        this.mIcon = icon;
        this.mSnippet = snippet;
    }

    public PojoClusterItem(double lat, double lng, String title, String snippet) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
    }

    public int getmIcon() {
        return mIcon;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }

}
