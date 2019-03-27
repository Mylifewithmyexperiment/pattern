package com.elitecore.wifi.api;

import android.app.Activity;
import android.content.Context;

import com.elitecore.wifi.listener.OnWiFiTaskCompleteListner;
import com.elitecore.wifi.pojo.PojoConnection;
import com.elitecorelib.core.pojo.PojoSubscriber;
import com.elitecorelib.core.pojo.PojoWifiAutoLogin;
import com.elitecorelib.core.pojo.PojoWifiInformation;

public interface IWiFiConfiguration {

    void connectToWiFi(Context context, PojoConnection connection, OnWiFiTaskCompleteListner wifiTaskCompleteListener, boolean keepWiFiOn, boolean delteOnTurnOffWiFi) throws Exception;

    boolean removeWiFiSetting(String ssidname) throws Exception;

    void isWiFiInRange(final OnWiFiTaskCompleteListner wifiTaskCompleteListener, final String ssidName) throws Exception;

    PojoWifiInformation getPersonalWifi(String ssidName);

    void addPersonalWifi(PojoWifiInformation pojoWifiInformation);

    void connectToPersonalWifi(boolean onlyPersonl);

    void connectToPersonalWifi(boolean onlyPersonal, boolean considerEAP);

    void autoDetectWifi();

    void deleteWifiInformation(String ssidName);

    void autoLoginToWifi(PojoWifiAutoLogin autoLogin);

    void updateWifiPriority(PojoWifiInformation wifiInformation);

    void getQOSForWifi(String SSIDName);

    void pgLogin(String PhoneNo, String Channel, String IpAddress);
    void pgLogout(String PhoneNo, String Channel, String IpAddress);

    void doRegistration(String sharedKey, PojoSubscriber subscriber);

    void getAllWifiSSID() throws Exception;

    void getDownloadUploadSpeed(String ssidName, OnWiFiTaskCompleteListner wifiTaskCompleteListener);
}
