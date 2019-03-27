package com.elitecorelib.andsf.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.elitecorelib.andsf.exception.InvalidDataException;
import com.elitecorelib.andsf.utility.CustomConstant;
import com.elitecorelib.andsf.utility.CustomMessage;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by harshesh.soni on 9/22/2016.
 */
@RealmClass
public class ANDSFPrioritizedAccess extends RealmObject implements Comparable<ANDSFPrioritizedAccess>,Parcelable {
    public int accessNetworkPriority;
    public int accessTechnology;
    public String accessId = "";
    public String secondaryAccessId = "";

    protected ANDSFPrioritizedAccess(Parcel in) {
        accessNetworkPriority = in.readInt();
        accessTechnology = in.readInt();
        accessId = in.readString();
        secondaryAccessId = in.readString();
    }

    public ANDSFPrioritizedAccess() {
    }

    public static final Creator<ANDSFPrioritizedAccess> CREATOR = new Creator<ANDSFPrioritizedAccess>() {
        @Override
        public ANDSFPrioritizedAccess createFromParcel(Parcel in) {
            return new ANDSFPrioritizedAccess(in);
        }

        @Override
        public ANDSFPrioritizedAccess[] newArray(int size) {
            return new ANDSFPrioritizedAccess[size];
        }
    };

    public int getAccessNetworkPriority() {
        return accessNetworkPriority;
    }

    public void setAccessNetworkPriority(int accessNetworkPriority) {
        this.accessNetworkPriority = accessNetworkPriority;
    }

    public int getAccessTechnology() {
        return accessTechnology;
    }

    public void setAccessTechnology(int accessTechnology) {
        this.accessTechnology = accessTechnology;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getSecondaryAccessId() {
        return secondaryAccessId;
    }

    public void setSecondaryAccessId(String secondaryAccessId) {
        this.secondaryAccessId = secondaryAccessId;
    }

    @Override
    public int compareTo(ANDSFPrioritizedAccess another) {
        int comparePriority = ((ANDSFPrioritizedAccess) another).getAccessNetworkPriority();
        //ascending order
        return this.accessNetworkPriority - comparePriority;
    }

    public void validate() throws InvalidDataException {
        Log.d(CustomConstant.ApplicationTag,"Priorited Access validation is started "+this.toString());

        //First validate Access Technology
        //If access technology Mentioned as WLAN :3 then either access id or secondary accessid is mandatory.
        if(this.accessTechnology!=1 && this.accessTechnology!=3 && this.accessTechnology!=4){
            throw new InvalidDataException(CustomMessage.invalidAccessTechnology);
        }

        //Access Network priority should be between 1-250 and 254 and 255.
        // Other value is considered to be invalid.
        if(this.accessNetworkPriority <= 0 || this.accessNetworkPriority >255){
            throw new InvalidDataException(CustomMessage.invalidAccessNetworkPriority);
        }

        if(this.accessNetworkPriority > 250 && this.accessNetworkPriority > 254){
            throw new InvalidDataException(CustomMessage.invalidAccessNetworkPriority);
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(accessNetworkPriority);
        dest.writeInt(accessTechnology);
        dest.writeString(accessId);
        dest.writeString(secondaryAccessId);
    }
}
