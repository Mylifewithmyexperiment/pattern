package com.elitecore.wifi.api;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.elitecore.wifi.pojo.PojoConnection;
import com.elitecorelib.core.EliteSession;

final class OPENWiFiSettings implements IEliteWiFiconfigurator {

    private String MODULE = "OPENWiFiSettings";    
    private int wifiNetworkId;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public int createWiFiSettings(Context context, PojoConnection connection) {

        wifiNetworkId = -1;
        try {
            EliteSession.eLog.d(MODULE + " createWiFiSettings Creating wifi invoked");
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            EliteSession.eLog.d(MODULE + " createWiFiSettings Turn on wifi if disabled");
            CommonWiFiUtility.turnOnWiFi(wifiManager);
            EliteSession.eLog.d(MODULE + " createWiFiSettings wifi configuration");
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = "\"" + connection.getSsid() + "\"";
            EliteSession.eLog.d(MODULE + " SSID set");
            wifiConfiguration.allowedKeyManagement.set(KeyMgmt.NONE);
            wifiConfiguration.status = WifiConfiguration.Status.ENABLED;
            EliteSession.eLog.d(MODULE + " Checking Priority");
            wifiConfiguration.priority = CommonWiFiUtility.getMaxPriority(wifiManager) + 1;
            EliteSession.eLog.d(MODULE + " Created wifi Configuration " + wifiConfiguration.toString());
            EliteSession.eLog.d(MODULE + " createWiFiSettings adding wifi into network");
            wifiNetworkId = wifiManager.addNetwork(wifiConfiguration);
            EliteSession.eLog.d(MODULE + " createWiFiSettings Network id is " + wifiNetworkId);
            wifiManager.saveConfiguration();
        } catch (Exception e) {
            wifiNetworkId = -1;
            EliteSession.eLog.e(MODULE + " " + e.getMessage());
        }
        return wifiNetworkId;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "OPENWiFiSettings Factory Created";
    }

}
