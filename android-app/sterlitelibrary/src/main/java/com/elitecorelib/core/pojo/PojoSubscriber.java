package com.elitecorelib.core.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;

public class PojoSubscriber extends RealmObject implements Parcelable {


    private static final long serialVersionUID = 3280394759140733685L;
    private String firstName;
    private String lastName;
    private String userName;
    private Integer age;
    private String ageRange;
    private String language;
    private String locale;
    private String location;
    private Date birthDate;
    private String sport;
    private String education;
    private String gender;
    private String relationshipStatus;
    private String registerWith;
    private Date registrationDate;
    private Date lastUsedTime;
    private Date uninstallDate;
    private String status;
    private String city;
    private String state;
    private String country;
    private long postalCode;
    private String geoAddress;
    private String imsi;
    private String imei;
    private String manufacturer;
    private String model;
    private String simOperator;
    private String brand;
    private String networkOperator;
    private String operatingSystem;
    private String OSVersion;
    private String registeredDeviceId;
    private String subscriberProfileType;
    private String msisdn;
    private int screenHeight;
    private int screenWidth;
    private int screenDensity;
    private int countryCode;
    private int MCC;
    private int MNC;
    private int cellId;
    private int LAC;
    private String SecretKey;
    private String imageURL;
    private int isNotificationOn;
    private String appVersion;

    protected PojoSubscriber(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        userName = in.readString();
        if (in.readByte() == 0) {
            age = null;
        } else {
            age = in.readInt();
        }
        ageRange = in.readString();
        language = in.readString();
        locale = in.readString();
        location = in.readString();
        sport = in.readString();
        education = in.readString();
        gender = in.readString();
        relationshipStatus = in.readString();
        registerWith = in.readString();
        status = in.readString();
        city = in.readString();
        state = in.readString();
        country = in.readString();
        postalCode = in.readLong();
        geoAddress = in.readString();
        imsi = in.readString();
        imei = in.readString();
        manufacturer = in.readString();
        model = in.readString();
        simOperator = in.readString();
        brand = in.readString();
        networkOperator = in.readString();
        operatingSystem = in.readString();
        OSVersion = in.readString();
        registeredDeviceId = in.readString();
        subscriberProfileType = in.readString();
        msisdn = in.readString();
        screenHeight = in.readInt();
        screenWidth = in.readInt();
        screenDensity = in.readInt();
        countryCode = in.readInt();
        MCC = in.readInt();
        MNC = in.readInt();
        cellId = in.readInt();
        LAC = in.readInt();
        SecretKey = in.readString();
        imageURL = in.readString();
        isNotificationOn = in.readInt();
        appVersion = in.readString();
        appLanguage = in.readString();
    }

    public PojoSubscriber() {

    }

    public static final Creator<PojoSubscriber> CREATOR = new Creator<PojoSubscriber>() {
        @Override
        public PojoSubscriber createFromParcel(Parcel in) {
            return new PojoSubscriber(in);
        }

        @Override
        public PojoSubscriber[] newArray(int size) {
            return new PojoSubscriber[size];
        }
    };

    public String getAppLanguage() {
        return appLanguage;
    }

    public void setAppLanguage(String appLanguage) {
        this.appLanguage = appLanguage;
    }

    private String appLanguage;

    public String getSubscriberProfileType() {
        return subscriberProfileType;
    }

    public void setSubscriberProfileType(String subscriberProfileType) {
        this.subscriberProfileType = subscriberProfileType;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getIsNotificationOn() {
        return isNotificationOn;
    }

    public void setIsNotificationOn(int isNotificationOn) {
        this.isNotificationOn = isNotificationOn;
    }

    public String getSecretKey() {
        return SecretKey;
    }

    public void setSecretKey(String secretKey) {
        SecretKey = secretKey;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getOSVersion() {
        return OSVersion;
    }

    public void setOSVersion(String oSVersion) {
        OSVersion = oSVersion;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public int getMCC() {
        return MCC;
    }

    public void setMCC(int mCC) {
        MCC = mCC;
    }

    public int getMNC() {
        return MNC;
    }

    public void setMNC(int mNC) {
        MNC = mNC;
    }

    public int getCellId() {
        return cellId;
    }

    public void setCellId(int cellId) {
        this.cellId = cellId;
    }

    public int getLAC() {
        return LAC;
    }

    public void setLAC(int lAC) {
        LAC = lAC;
    }

    public int getScreenDensity() {
        return screenDensity;
    }

    public void setScreenDensity(int screenDensity) {
        this.screenDensity = screenDensity;
    }


    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }


    public String getGeoAddress() {
        return geoAddress;
    }

    public void setGeoAddress(String geoAddress) {
        this.geoAddress = geoAddress;
    }

    public long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(long postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRegisteredDeviceId() {
        return registeredDeviceId;
    }

    public void setRegisteredDeviceId(String registeredDeviceId) {
        this.registeredDeviceId = registeredDeviceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getRegisterWith() {
        return registerWith;
    }

    public void setRegisterWith(String registerWith) {
        this.registerWith = registerWith;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getSimOperator() {
        return simOperator;
    }

    public void setSimOperator(String simOperator) {
        this.simOperator = simOperator;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNetworkOperator() {
        return networkOperator;
    }

    public void setNetworkOperator(String networkOperator) {
        this.networkOperator = networkOperator;
    }


    public String getRelationshipStatus() {
        return relationshipStatus;
    }

    public void setRelationshipStatus(String relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getLastUsedTime() {
        return lastUsedTime;
    }

    public void setLastUsedTime(Date lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    public Date getUninstallDate() {
        return uninstallDate;
    }

    public void setUninstallDate(Date uninstallDate) {
        this.uninstallDate = uninstallDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(userName);
        if (age == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(age);
        }
        parcel.writeString(ageRange);
        parcel.writeString(language);
        parcel.writeString(locale);
        parcel.writeString(location);
        parcel.writeString(sport);
        parcel.writeString(education);
        parcel.writeString(gender);
        parcel.writeString(relationshipStatus);
        parcel.writeString(registerWith);
        parcel.writeString(status);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(country);
        parcel.writeLong(postalCode);
        parcel.writeString(geoAddress);
        parcel.writeString(imsi);
        parcel.writeString(imei);
        parcel.writeString(manufacturer);
        parcel.writeString(model);
        parcel.writeString(simOperator);
        parcel.writeString(brand);
        parcel.writeString(networkOperator);
        parcel.writeString(operatingSystem);
        parcel.writeString(OSVersion);
        parcel.writeString(registeredDeviceId);
        parcel.writeString(subscriberProfileType);
        parcel.writeString(msisdn);
        parcel.writeInt(screenHeight);
        parcel.writeInt(screenWidth);
        parcel.writeInt(screenDensity);
        parcel.writeInt(countryCode);
        parcel.writeInt(MCC);
        parcel.writeInt(MNC);
        parcel.writeInt(cellId);
        parcel.writeInt(LAC);
        parcel.writeString(SecretKey);
        parcel.writeString(imageURL);
        parcel.writeInt(isNotificationOn);
        parcel.writeString(appVersion);
        parcel.writeString(appLanguage);
    }
}
