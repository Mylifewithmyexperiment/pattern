package com.elitecorelib.core.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by harshesh.soni on 3/27/2018.
 */

public class BlackListData extends RealmObject implements Parcelable {

    private String pattern;
    private String isRegExp;

    public BlackListData(Parcel in) {
        pattern = in.readString();
        isRegExp = in.readString();
    }

    public BlackListData() {

    }

    public static final Parcelable.Creator<BlackListData> CREATOR = new Parcelable.Creator<BlackListData>() {
        @Override
        public BlackListData createFromParcel(Parcel in) {
            return new BlackListData(in);
        }

        @Override
        public BlackListData[] newArray(int size) {
            return new BlackListData[size];
        }
    };

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getIsRegExp() {
        return isRegExp;
    }

    public void setIsRegExp(String isRegExp) {
        this.isRegExp = isRegExp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pattern);
        parcel.writeString(isRegExp);
    }
}
