package com.elitecorelib.deal.pojo;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class PojoDealAll extends RealmObject implements Parcelable {
    public PojoDealAll() {
    }

    @PrimaryKey
    private Long dealId;
    private String dealName;
    private String dealDescription;
    private String dealStartdate;
    private String dealStopdate;
    private String currency;
    public String dealHeadline;
    private String dealImagepath;
    private String dealThumbail_imagepath;
    private Long price;
    private Long customerCostPerVoucher;
    private Long merchantPrice;
    private String offer;
    private String offerDescription;
    private String reedmptionDetails;
    private Double latitude;
    private Double longitude;
    private String dealTags;
    private Double distance;
    private String isVoucher;
    private String redirectURL;
    public PojoDealDisplayInfo dealDisplayInfoData;


    protected PojoDealAll(Parcel in) {
        if (in.readByte() == 0) {
            dealId = null;
        } else {
            dealId = in.readLong();
        }
        dealName = in.readString();
        dealDescription = in.readString();
        dealStartdate = in.readString();
        dealStopdate = in.readString();
        currency = in.readString();
        dealHeadline = in.readString();
        dealImagepath = in.readString();
        dealThumbail_imagepath = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readLong();
        }
        if (in.readByte() == 0) {
            customerCostPerVoucher = null;
        } else {
            customerCostPerVoucher = in.readLong();
        }
        if (in.readByte() == 0) {
            merchantPrice = null;
        } else {
            merchantPrice = in.readLong();
        }
        offer = in.readString();
        offerDescription = in.readString();
        reedmptionDetails = in.readString();
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
        dealTags = in.readString();
        if (in.readByte() == 0) {
            distance = null;
        } else {
            distance = in.readDouble();
        }
        isVoucher = in.readString();
        redirectURL = in.readString();
        dealDisplayInfoData = in.readParcelable(PojoDealDisplayInfo.class.getClassLoader());
    }

    public static final Creator<PojoDealAll> CREATOR = new Creator<PojoDealAll>() {
        @Override
        public PojoDealAll createFromParcel(Parcel in) {
            return new PojoDealAll(in);
        }

        @Override
        public PojoDealAll[] newArray(int size) {
            return new PojoDealAll[size];
        }
    };

    public Long getDealId() {
        return dealId;
    }

    public void setDealId(Long dealId) {
        this.dealId = dealId;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getDealDescription() {
        return dealDescription;
    }

    public void setDealDescription(String dealDescription) {
        this.dealDescription = dealDescription;
    }

    public String getDealStartdate() {
        return dealStartdate;
    }

    public void setDealStartdate(String dealStartdate) {
        this.dealStartdate = dealStartdate;
    }

    public String getDealStopdate() {
        return dealStopdate;
    }

    public void setDealStopdate(String dealStopdate) {
        this.dealStopdate = dealStopdate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDealHeadline() {
        return dealHeadline;
    }

    public void setDealHeadline(String dealHeadline) {
        this.dealHeadline = dealHeadline;
    }

    public String getDealImagepath() {
        return dealImagepath;
    }

    public void setDealImagepath(String dealImagepath) {
        this.dealImagepath = dealImagepath;
    }

    public String getDealThumbail_imagepath() {
        return dealThumbail_imagepath;
    }

    public void setDealThumbail_imagepath(String dealThumbail_imagepath) {
        this.dealThumbail_imagepath = dealThumbail_imagepath;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getCustomerCostPerVoucher() {
        return customerCostPerVoucher;
    }

    public void setCustomerCostPerVoucher(Long customerCostPerVoucher) {
        this.customerCostPerVoucher = customerCostPerVoucher;
    }

    public Long getMerchantPrice() {
        return merchantPrice;
    }

    public void setMerchantPrice(Long merchantPrice) {
        this.merchantPrice = merchantPrice;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDealTags() {
        return dealTags;
    }

    public void setDealTags(String dealTags) {
        this.dealTags = dealTags;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getIsVoucher() {
        return isVoucher;
    }

    public void setIsVoucher(String isVoucher) {
        this.isVoucher = isVoucher;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    public PojoDealDisplayInfo getDealDisplayInfoData() {
        return dealDisplayInfoData;
    }

    public void setDealDisplayInfoData(PojoDealDisplayInfo dealDisplayInfoData) {
        this.dealDisplayInfoData = dealDisplayInfoData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (dealId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(dealId);
        }
        dest.writeString(dealName);
        dest.writeString(dealDescription);
        dest.writeString(dealStartdate);
        dest.writeString(dealStopdate);
        dest.writeString(currency);
        dest.writeString(dealHeadline);
        dest.writeString(dealImagepath);
        dest.writeString(dealThumbail_imagepath);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(price);
        }
        if (customerCostPerVoucher == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(customerCostPerVoucher);
        }
        if (merchantPrice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(merchantPrice);
        }
        dest.writeString(offer);
        dest.writeString(offerDescription);
        dest.writeString(reedmptionDetails);
        if (latitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(latitude);
        }
        if (longitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(longitude);
        }
        dest.writeString(dealTags);
        if (distance == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(distance);
        }
        dest.writeString(isVoucher);
        dest.writeString(redirectURL);
        dest.writeParcelable(dealDisplayInfoData, flags);
    }
}
