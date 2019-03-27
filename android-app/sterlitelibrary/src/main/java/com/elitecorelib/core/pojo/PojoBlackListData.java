package com.elitecorelib.core.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by harshesh.soni on 3/29/2018.
 */

public class PojoBlackListData extends RealmObject implements Parcelable {

    private String pattern;
    private String isRegExp;

    public PojoBlackListData(Parcel in) {
        pattern = in.readString();
        isRegExp = in.readString();
    }

    public PojoBlackListData() {

    }

    public static final Creator<PojoBlackListData> CREATOR = new Creator<PojoBlackListData>() {
        @Override
        public PojoBlackListData createFromParcel(Parcel in) {
            return new PojoBlackListData(in);
        }

        @Override
        public PojoBlackListData[] newArray(int size) {
            return new PojoBlackListData[size];
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