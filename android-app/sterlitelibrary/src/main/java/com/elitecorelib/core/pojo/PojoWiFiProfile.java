package com.elitecorelib.core.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by chirag.parmar on 3/26/2018.
 */


public class PojoWiFiProfile extends RealmObject implements Parcelable {

    public static final Creator<PojoWiFiProfile> CREATOR = new Creator<PojoWiFiProfile>() {
        @Override
        public PojoWiFiProfile createFromParcel(Parcel in) {
            return new PojoWiFiProfile(in);
        }

        @Override
        public PojoWiFiProfile[] newArray(int size) {
            return new PojoWiFiProfile[size];
        }
    };
    @PrimaryKey
    private int profileId;
    private boolean isPreferable;
    private String description;
    private String androidSettingName;
    private String removeAllowFromApp;
    private boolean isLocal;
    private RealmList<PojoWiFiConnection> wifiSettingSet;

    public PojoWiFiProfile() {
    }

    protected PojoWiFiProfile(Parcel in) {
        profileId = in.readInt();
        isPreferable = in.readByte() != 0;
        description = in.readString();
        androidSettingName = in.readString();
        removeAllowFromApp = in.readString();
        isLocal = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(profileId);
        dest.writeByte((byte) (isPreferable ? 1 : 0));
        dest.writeString(description);
        dest.writeString(androidSettingName);
        dest.writeString(removeAllowFromApp);
        dest.writeByte((byte) (isLocal ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public boolean isPreferable() {
        return isPreferable;
    }

    public void setPreferable(boolean preferable) {
        isPreferable = preferable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAndroidSettingName() {
        return androidSettingName;
    }

    public void setAndroidSettingName(String androidSettingName) {
        this.androidSettingName = androidSettingName;
    }

    public String getRemoveAllowFromApp() {
        return removeAllowFromApp;
    }

    public void setRemoveAllowFromApp(String removeAllowFromApp) {
        this.removeAllowFromApp = removeAllowFromApp;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }


    public RealmList<PojoWiFiConnection> getWifiSettingSet() {
        return wifiSettingSet;
    }

    public void setWifiSettingSet(RealmList<PojoWiFiConnection> wifiConnctionSet) {
        this.wifiSettingSet = wifiConnctionSet;
    }

    @Override
    public boolean equals(Object obj) {
        PojoWiFiProfile wiFiConnection = (PojoWiFiProfile) obj;
        return this.androidSettingName.equals(wiFiConnection.androidSettingName);
    }

}
