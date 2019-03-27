package com.elitecore.wifi.api;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;

import com.elitecore.wifi.pojo.PojoConnection;
import com.elitecorelib.core.EliteSession;


final class EAPAKAWiFiSettings implements IEliteWiFiconfigurator {
    private String MODULE = "EAPAKAWiFiSettings";
    private int wifiNetworkId;

    @TargetApi(22)
    @Override
    public int createWiFiSettings(Context context, PojoConnection connection) {
        try {
            //Getting WiFi Service from system
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            //Getting Telephony service to read the IMSI

            String userIdentity = connection.getIdentity();

            EliteSession.eLog.d(MODULE + " createWiFiSettings invked");

            EliteSession.eLog.d(MODULE + " Turning on wifi if disabled");
            CommonWiFiUtility.turnOnWiFi(wifiManager);
            EliteSession.eLog.d(MODULE + " Removing network= " + connection.getSsid() + " if already configured");
            CommonWiFiUtility.removeNetwork(wifiManager, connection.getSsid());
            EliteSession.eLog.d(MODULE + " createWiFiSettings createing wificonfiguration");
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            String phaseAuth = connection.getPhase2Authentication();
            WifiEnterpriseConfig defaultWifiEnterpriseConfig;
            defaultWifiEnterpriseConfig = new WifiEnterpriseConfig();
            if (userIdentity != null)
                defaultWifiEnterpriseConfig.setIdentity(userIdentity);
            if (connection.getPassword() != null)
                defaultWifiEnterpriseConfig.setPassword(connection.getPassword());
            //EAP SIM constant value is 4, and support above API LEVEL 21
            EliteSession.eLog.d(MODULE + " Turning on wifi if disabled");
            defaultWifiEnterpriseConfig.setEapMethod(WifiEnterpriseConfig.Eap.AKA);

           /* *//* Setting SIM priority in dual SIM*//*
            try {
                EliteSession.eLog.d(MODULE + " Setting SIM priority in dual SIM");
                Field wcefsim_slot = null;
                Field[] wcefFields = WifiConfiguration.class.getFields();
                EliteSession.eLog.d(MODULE + " Setting SIM priority in dual SIM, getting fields");
                for (Field wcefField : wcefFields) {
                    if (wcefField.getName().equals("sim_slot")) {
                        wcefsim_slot = wcefField;
                    }
                }
                EliteSession.eLog.d(MODULE + " Setting SIM priority in dual SIM, setting priority passed into pojo connection");
                wcefsim_slot.setInt(wifiConfiguration,connection.getSimSlot());
            }
            catch (Exception e)
            {
                EliteSession.eLog.e(MODULE + " Error while setting dual sim priority ,"+ e.getMessage());
            }*/
            wifiConfiguration.enterpriseConfig = defaultWifiEnterpriseConfig;
            /* AP Name */
            wifiConfiguration.SSID = "\"" + connection.getSsid() + "\"";

			/* Priority */
            wifiConfiguration.priority = CommonWiFiUtility.getMaxPriority(wifiManager) + 1;
            EliteSession.eLog.d(MODULE + " wifiConfiguration.priority " + wifiConfiguration.priority);
            if (connection.isDefault()) {
                wifiConfiguration.hiddenSSID = true;
            } else {
                wifiConfiguration.hiddenSSID = false;
            }

			/* Key Mgmnt */
            wifiConfiguration.allowedKeyManagement.clear();
            wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.IEEE8021X);
            wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);

			/* Group Ciphers : Encryption methods */
            wifiConfiguration.allowedGroupCiphers.clear();
            wifiConfiguration.allowedGroupCiphers // to be deleted
                    .set(WifiConfiguration.GroupCipher.CCMP);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            wifiConfiguration.allowedGroupCiphers // to be deleted
                    .set(WifiConfiguration.GroupCipher.WEP104);
            wifiConfiguration.allowedGroupCiphers // to be deleted
                    .set(WifiConfiguration.GroupCipher.WEP40);

			/* Pairwise ciphers : Encryption methods */
            wifiConfiguration.allowedPairwiseCiphers.clear();
            wifiConfiguration.allowedPairwiseCiphers // to be deleted
                    .set(WifiConfiguration.PairwiseCipher.CCMP);
            wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);

			/* Protocols */
            wifiConfiguration.allowedProtocols.clear();
            wifiConfiguration.allowedProtocols // to be deleted
                    .set(WifiConfiguration.Protocol.RSN);
            wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);

            wifiConfiguration.status = WifiConfiguration.Status.ENABLED;
            EliteSession.eLog.d(MODULE + " Created wifi Configuration " + wifiConfiguration.toString());
            EliteSession.eLog.d(MODULE, " createWiFiSettings adding network");
            wifiNetworkId = wifiManager.addNetwork(wifiConfiguration);
            EliteSession.eLog.d(MODULE + " Netowrk id is " + wifiNetworkId);
            return wifiNetworkId;
        } catch (Exception e) {
            wifiNetworkId = -1;
            EliteSession.eLog.e(MODULE + " " + e.getMessage());
        }
        return wifiNetworkId;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "EAPAKAWiFiSettings Factory Created";
    }

}
