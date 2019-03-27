package com.elitecorelib.deal.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by viratsinh.parmar on 9/26/2015.
 */
public class PojoDealDisplayInfo   extends RealmObject implements Parcelable {

    public PojoDealDisplayInfo() {
    }

    @PrimaryKey
    private int id;
    private Long dealId;
    private String dealDisplayInfoId;
    private String languageCode;
    private String offerDescription;
    private String reedmptionDetails;
    private String dealHeadline;
    private String offer;
    private String displayDealName;

    protected PojoDealDisplayInfo(Parcel in) {
        id = in.readInt();
        if (in.readByte() == 0) {
            dealId = null;
        } else {
            dealId = in.readLong();
        }
        dealDisplayInfoId = in.readString();
        languageCode = in.readString();
        offerDescription = in.readString();
        reedmptionDetails = in.readString();
        dealHeadline = in.readString();
        offer = in.readString();
        displayDealName = in.readString();
    }

    public static final Creator<PojoDealDisplayInfo> CREATOR = new Creator<PojoDealDisplayInfo>() {
        @Override
        public PojoDealDisplayInfo createFromParcel(Parcel in) {
            return new PojoDealDisplayInfo(in);
        }

        @Override
        public PojoDealDisplayInfo[] newArray(int size) {
            return new PojoDealDisplayInfo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getDealId() {
        return dealId;
    }

    public void setDealId(Long dealId) {
        this.dealId = dealId;
    }

    public String getDealDisplayInfoId() {
        return dealDisplayInfoId;
    }

    public void setDealDisplayInfoId(String dealDisplayInfoId) {
        this.dealDisplayInfoId = dealDisplayInfoId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public String getReedmptionDetails() {
        return reedmptionDetails;
    }

    public void setReedmptionDetails(String reedmptionDetails) {
        this.reedmptionDetails = reedmptionDetails;
    }

    public String getDealHeadline() {
        return dealHeadline;
    }

    public void setDealHeadline(String dealHeadline) {
        this.dealHeadline = dealHeadline;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getDisplayDealName() {
        return displayDealName;
    }

    public void setDisplayDealName(String displayDealName) {
        this.displayDealName = displayDealName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        if (dealId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(dealId);
        }
        dest.writeString(dealDisplayInfoId);
        dest.writeString(languageCode);
        dest.writeString(offerDescription);
        dest.writeString(reedmptionDetails);
        dest.writeString(dealHeadline);
        dest.writeString(offer);
        dest.writeString(displayDealName);
    }
}
