package com.elitecorelib.core.pojo;


import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class PojoLocation extends RealmObject implements Parcelable {

    public static final Creator<PojoLocation> CREATOR = new Creator<PojoLocation>() {
        @Override
        public PojoLocation createFromParcel(Parcel in) {
            return new PojoLocation(in);
        }

        @Override
        public PojoLocation[] newArray(int size) {
            return new PojoLocation[size];
        }
    };
    public double latitude;
    private long locationId;
    private String locationName;
    private double longitude;
    private double radius;
    private String category;
    private String locationDescription;
    private int zoneId;
    private String param1;
    private String param2;
    private double distanceLocation;
    private String offlineMessage;
    private String agentId;

    public PojoLocation() {
    }

    protected PojoLocation(Parcel in) {
        locationId = in.readLong();
        locationName = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        radius = in.readDouble();
        category = in.readString();
        locationDescription = in.readString();
        zoneId = in.readInt();
        param1 = in.readString();
        param2 = in.readString();
        distanceLocation = in.readDouble();
        offlineMessage = in.readString();
        agentId = in.readString();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }


    public String getOfflineMessage() {
        return offlineMessage;
    }

    public void setOfflineMessage(String offlineMessage) {
        this.offlineMessage = offlineMessage;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDistanceLocation() {
        return distanceLocation;
    }

    public void setDistanceLocation(double distanceLocation) {
        this.distanceLocation = distanceLocation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(locationId);
        dest.writeString(locationName);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeDouble(radius);
        dest.writeString(category);
        dest.writeString(locationDescription);
        dest.writeInt(zoneId);
        dest.writeString(param1);
        dest.writeString(param2);
        dest.writeDouble(distanceLocation);
        dest.writeString(offlineMessage);
        dest.writeString(agentId);
    }
}
