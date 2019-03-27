package com.elitecorelib.core.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

@SuppressWarnings("serial")
public class PojoProfile  implements Parcelable {

    private String name;
    private boolean isDefault;
    private boolean isActive;
    private boolean isLocal;

    public PojoProfile() {
    }

    public PojoProfile(String name, boolean isDefault) {
        this.name = name;
        this.isDefault = isDefault;
    }

    protected PojoProfile(Parcel in) {
        name = in.readString();
        isDefault = in.readByte() != 0;
        isActive = in.readByte() != 0;
        isLocal = in.readByte() != 0;
    }

    public static final Creator<PojoProfile> CREATOR = new Creator<PojoProfile>() {
        @Override
        public PojoProfile createFromParcel(Parcel in) {
            return new PojoProfile(in);
        }

        @Override
        public PojoProfile[] newArray(int size) {
            return new PojoProfile[size];
        }
    };

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (isDefault ? 1 : 0));
        dest.writeByte((byte) (isActive ? 1 : 0));
        dest.writeByte((byte) (isLocal ? 1 : 0));
    }
}
