package com.elitecorelib.core.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class PojoAdMapping extends RealmObject implements Parcelable {

    /**
     *
     */
    private static final long serialVersionUID = -3630260877273759792L;


    private String screenLocation;
    private String deviceCatagory;
    private String invocationCode;
    private String screenName;

    public PojoAdMapping(Parcel in) {
        screenLocation = in.readString();
        deviceCatagory = in.readString();
        invocationCode = in.readString();
        screenName = in.readString();
    }

    public PojoAdMapping() {

    }


    public static final Creator<PojoAdMapping> CREATOR = new Creator<PojoAdMapping>() {
        @Override
        public PojoAdMapping createFromParcel(Parcel in) {
            return new PojoAdMapping(in);
        }

        @Override
        public PojoAdMapping[] newArray(int size) {
            return new PojoAdMapping[size];
        }
    };

    public String getScreenLocation() {
        return screenLocation;
    }

    public void setScreenLocation(String screenLocation) {
        this.screenLocation = screenLocation;
    }

    public String getDeviceCatagory() {
        return deviceCatagory;
    }

    public void setDeviceCatagory(String deviceCatagory) {
        this.deviceCatagory = deviceCatagory;
    }

    public String getInvocationCode() {
        return invocationCode;
    }

    public void setInvocationCode(String invocationCode) {
        this.invocationCode = invocationCode;
    }


    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(screenLocation);
        parcel.writeString(deviceCatagory);
        parcel.writeString(invocationCode);
        parcel.writeString(screenName);
    }
}
