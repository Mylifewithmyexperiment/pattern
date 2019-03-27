package com.elitecore.wifi.api;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.elitecore.wifi.pojo.PojoConnection;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.utility.SharedPreferencesTask;

/**
 * Created by Chirag Parmar on 06-Mar-17.
 */

public final class EAPPEAPWiFiSetting implements IEliteWiFiconfigurator {

    private final String MODULE = "EAPPEAPWiFiSetting";
    private int wifiNetworkId;
    private SharedPreferencesTask spTask= LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();

    @Override
    public int createWiFiSettings(Context context, PojoConnection connection) {

        try {
            //Getting WiFi Service from system
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);

            //Getting Telephony service to read the IMSI

            String userIdentity=connection.getIdentity();
            if(spTask.getString(CoreConstants.ENABLE_OTHERAAA).compareTo(CoreConstants.ENABLE_OTHERAAA_VALUE)!=0)
            {
                TelephonyManager tm =(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                connection.setIdentity(tm.getSubscriberId());
                EliteSession.eLog.d(MODULE + " createWiFiSettings TTLS connection appending realm in user id");

                if(spTask.getString(CoreConstants.Realm_Eapttls).equals("")){
                    userIdentity=connection.getIdentity()+EliteWiFIConstants.REALM_EAPTTLS;
                }else{
                    userIdentity=connection.getIdentity()+spTask.getString(CoreConstants.Realm_Eapttls);
                }

                connection.setPhase2Authentication(EliteWiFIConstants.PHASE2AUTHETICATIONMSCHAPV2);
                connection.setPassword(LibraryApplication.getLibraryApplication().getSomething());

            }

            EliteSession.eLog.d(MODULE + " createWiFiSettings invked");

            EliteSession.eLog.d(MODULE+ " Turning on wifi if disabled");
            CommonWiFiUtility.turnOnWiFi(wifiManager);
            EliteSession.eLog.d(MODULE+ " Removing network= "+connection.getSsid() +" if already configured");
            CommonWiFiUtility.removeNetwork(wifiManager, connection.getSsid());
            EliteSession.eLog.d(MODULE+ " createWiFiSettings createing wificonfiguration");
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            String phaseAuth=connection.getPhase2Authentication();
            if(phaseAuth!=null && phaseAuth.compareTo("")!=0)
            {
                WifiEnterpriseConfig defaultWifiEnterpriseConfig;
                defaultWifiEnterpriseConfig = new WifiEnterpriseConfig();
                defaultWifiEnterpriseConfig.setIdentity(userIdentity);
                defaultWifiEnterpriseConfig.setPassword(connection.getPassword());
                defaultWifiEnterpriseConfig.setEapMethod(WifiEnterpriseConfig.Eap.PEAP);
                if (phaseAuth.equals(EliteWiFIConstants.PHASE2AUTHETICATIONMSCHAPV2)) {
                    defaultWifiEnterpriseConfig.setPhase2Method(WifiEnterpriseConfig.Phase2.MSCHAPV2);
                } else if (phaseAuth.equals(EliteWiFIConstants.PHASE2AUTHETICATIONGTC)) {
                    defaultWifiEnterpriseConfig.setPhase2Method(WifiEnterpriseConfig.Phase2.GTC);
                } else if (phaseAuth.equals(EliteWiFIConstants.PHASE2AUTHETICATIONMSCHAP)) {
                    defaultWifiEnterpriseConfig.setPhase2Method(WifiEnterpriseConfig.Phase2.MSCHAP);
                } else if (phaseAuth.equals(EliteWiFIConstants.PHASE2AUTHETICATIONNONE)) {
                    defaultWifiEnterpriseConfig.setPhase2Method(WifiEnterpriseConfig.Phase2.NONE);
                } else if (phaseAuth.equals(EliteWiFIConstants.PHASE2AUTHETICATIONPAP)) {
                    defaultWifiEnterpriseConfig.setPhase2Method(WifiEnterpriseConfig.Phase2.PAP);
                }
                wifiConfiguration.enterpriseConfig = defaultWifiEnterpriseConfig;
            }
			/* AP Name */
            wifiConfiguration.SSID = "\"" + connection.getSsid() + "\"";

			/* Priority */
            wifiConfiguration.priority = CommonWiFiUtility.getMaxPriority(wifiManager)+1;
            EliteSession.eLog.d(MODULE+ " wifiConfiguration.priority "+wifiConfiguration.priority );
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
            EliteSession.eLog.d(MODULE+" Created wifi Configuration "+wifiConfiguration.toString());
            EliteSession.eLog.d(MODULE, " createWiFiSettings adding network");
            wifiNetworkId = wifiManager.addNetwork(wifiConfiguration);
            EliteSession.eLog.d(MODULE+ " Netowrk id is "+wifiNetworkId);
            return wifiNetworkId;
        } catch (Exception e) {
            wifiNetworkId = -1;
            EliteSession.eLog.e(MODULE+" "+ e.getMessage());
        }
        return wifiNetworkId;
    }
}
