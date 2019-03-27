package com.sterlite.dccmappfordealersterlite.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Track implements Parcelable {

    private String mMessage;
    private String mDescription;
    private String mDate;
    private OrderStatus mStatus;

    public Track() {
    }

    public Track(String mMessage, String mDate, OrderStatus mStatus) {
        this.mMessage = mMessage;
        this.mDate = mDate;
        this.mStatus = mStatus;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public OrderStatus getStatus() {
        return mStatus;
    }

    public void setStatus(OrderStatus mStatus) {
        this.mStatus = mStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mMessage);
        dest.writeString(this.mDate);
        dest.writeInt(this.mStatus == null ? -1 : this.mStatus.ordinal());
    }

    protected Track(Parcel in) {
        this.mMessage = in.readString();
        this.mDate = in.readString();
        int tmpMStatus = in.readInt();
        this.mStatus = tmpMStatus == -1 ? null : OrderStatus.values()[tmpMStatus];
    }

    public static final Parcelable.Creator<Track> CREATOR = new Parcelable.Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel source) {
            return new Track(source);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}