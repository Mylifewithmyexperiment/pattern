package com.elitecore.wifi.api;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.elitecore.wifi.pojo.PojoConnection;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.utility.SharedPreferencesTask;

final class EliteEAPWiFiSettings implements IEliteWiFiconfigurator {
    private String INT_ENTERPRISEFIELD_NAME = "android.net.wifi.WifiConfiguration$EnterpriseField";
    private String INT_IDENTITY = "identity";
    private String INT_ANONYMOUS_IDENTITY = "anonymous_identity";
    private String INT_CA_CERT = "ca_cert";
    private String INT_CLIENT_CERT = "client_cert";
    private String INT_PRIVATE_KEY = "private_key";
    private String INT_PHASE2 = "phase2";
    private String INT_PASSWORD = "password";
    private String MODULE = "EliteEAPWiFiSettings";
    private static final String INT_EAP = "eap";
    private int wifiNetworkId = -1;
    private String TTLS = "TTLS";
    private String SIM = "SIM";
    private SharedPreferencesTask spTask=LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();

    @Override
    public int createWiFiSettings(Context context, PojoConnection connection) {

        final String ENTERPRISE_CLIENT_CERT = "";
        final String ENTERPRISE_PRIV_KEY = "";
        String ENTERPRISE_PHASE2 = "auth=" + connection.getPhase2Authentication();// phaseAuth
        final String ENTERPRISE_ANON_IDENT = "";
        final String ENTERPRISE_CA_CERT = "";
        /******************************** Configuration Strings ****************************************************/
        EliteSession.eLog.d(MODULE + " createWiFiSettings invked");
        String protoCol = "";
        String userIdentity = "";
        EliteSession.eLog.d(MODULE + " createWiFiSettings TTLS connection appending realm in user id");
        protoCol = TTLS;
        if(spTask.getString(CoreConstants.ENABLE_OTHERAAA).compareTo(CoreConstants.ENABLE_OTHERAAA_VALUE)!=0){
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            connection.setIdentity(tm.getSubscriberId());
            connection.setPhase2Authentication(EliteWiFIConstants.PHASE2AUTHETICATIONMSCHAPV2);
            ENTERPRISE_PHASE2 = "auth=" + connection.getPhase2Authentication();

            if(spTask.getString(CoreConstants.Realm_Eapttls).equals("")){
                userIdentity=connection.getIdentity()+EliteWiFIConstants.REALM_EAPTTLS;
            }else{
                userIdentity=connection.getIdentity()+spTask.getString(CoreConstants.Realm_Eapttls);
            }
//            userIdentity = connection.getIdentity() + EliteWiFIConstants.REALM_EAPTTLS;
            connection.setPassword(LibraryApplication.getLibraryApplication().getSomething());
        }
        else
        {
            userIdentity=connection.getIdentity();
        }
        final String ENTERPRISE_EAP = protoCol;
        /******************************** Configuration Strings ****************************************************/

        try {

            EliteSession.eLog.d(MODULE + " ==============Revised WiFi Connection Parameters======================");
            EliteSession.eLog.d(MODULE + " " + connection.toString());

            EliteSession.eLog.d(MODULE + " createWiFiSettings Creating wifi configuration");
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            EliteSession.eLog.d(MODULE + " createWiFiSettings Turning on WiFi if disabled.");
            CommonWiFiUtility.turnOnWiFi(wifiManager);
            CommonWiFiUtility.removeNetwork(wifiManager, connection.getSsid());
            ;

			/* AP Name */
            wifiConfiguration.SSID = "\"" + connection.getSsid() + "\"";

			/* Priority */
            // wifiConfiguration.priority = 40;
            wifiConfiguration.priority = CommonWiFiUtility.getMaxPriority(wifiManager) + 1;
            EliteSession.eLog.d(MODULE + " wifiConfiguration.priority " + wifiConfiguration.priority);
            // wifiConfiguration.priority = 3;
			/* Set value for Hidden SSID */
            if (connection.isDefault()) {
                wifiConfiguration.hiddenSSID = true;
            } else {
                wifiConfiguration.hiddenSSID = false;
            }
            // wifiConfiguration.hiddenSSID = false;

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

            EliteSession.eLog.d(MODULE + " createWiFiSettings Creating enterprise wifi configuration with reflection");

            EliteSession.eLog.d(MODULE + " createWiFiSettings checking enterprise inner class availability  in wificonfiguration class");
            Class[] wcClasses = WifiConfiguration.class.getClasses();
            // null for some java compiler
            Class wcEnterpriseField = null;
            for (Class wcClass : wcClasses) {
                EliteSession.eLog.d(MODULE + " createWiFiSettings  class name is :" + wcClass.getName());
                if (wcClass.getName().equals(INT_ENTERPRISEFIELD_NAME)) {
                    EliteSession.eLog.d(MODULE + " createWiFiSettings  Enterprise Class available");
                    wcEnterpriseField = wcClass;
                    break;
                }
            }
            boolean noEnterpriseFieldType = false;
            if (wcEnterpriseField == null)
                noEnterpriseFieldType = true;

            Field wcefAnonymousId = null, wcefCaCert = null, wcefClientCert = null, wcefEap = null, wcefIdentity = null, wcefPassword = null, wcefPhase2 = null, wcefPrivateKey = null;
            Field[] wcefFields = WifiConfiguration.class.getFields();

            // Dispatching Field vars
            for (Field wcefField : wcefFields) {
                if (wcefField.getName().equals(INT_ANONYMOUS_IDENTITY)) {
                    wcefAnonymousId = wcefField;
                } else if (wcefField.getName().equals(INT_CA_CERT)) {
                    wcefCaCert = wcefField;
                } else if (wcefField.getName().equals(INT_CLIENT_CERT)) {
                    wcefClientCert = wcefField;
                } else if (wcefField.getName().equals(INT_EAP)) {
                    wcefEap = wcefField;
                } else if (wcefField.getName().equals(INT_IDENTITY)) {
                    wcefIdentity = wcefField;
                } else if (wcefField.getName().equals(INT_PASSWORD)) {
                    wcefPassword = wcefField;
                } else if (wcefField.getName().equals(INT_PHASE2)) {
                    wcefPhase2 = wcefField;
                } else if (wcefField.getName().equals(INT_PRIVATE_KEY)) {
                    wcefPrivateKey = wcefField;
                }
            }

            Method wcefSetValue = null;
            if (!noEnterpriseFieldType) {
                for (Method m : wcEnterpriseField.getMethods())
                    if (m.getName().trim().equals("setValue")) {
                        wcefSetValue = m;
                        break;
                    }
            }

			/* EAP Method */
            if (!noEnterpriseFieldType) {
                if (wcefEap != null)
                    wcefSetValue.invoke(wcefEap.get(wifiConfiguration), ENTERPRISE_EAP);
            }
			/* EAP Phase 2 Authentication */
            if (!noEnterpriseFieldType) {
                if (wcefPhase2 != null)
                    wcefSetValue.invoke(wcefPhase2.get(wifiConfiguration), ENTERPRISE_PHASE2);
            }

			/* EAP Anonymous Identity */
            if (!noEnterpriseFieldType) {
                if (wcefAnonymousId != null)
                    wcefSetValue.invoke(wcefAnonymousId.get(wifiConfiguration), ENTERPRISE_ANON_IDENT);
            }

			/* EAP CA Certificate */
            if (!noEnterpriseFieldType) {
                if (wcefCaCert != null)
                    wcefSetValue.invoke(wcefCaCert.get(wifiConfiguration), ENTERPRISE_CA_CERT);
            }

			/* EAP Private key */
            if (!noEnterpriseFieldType) {
                if (wcefPrivateKey != null)
                    wcefSetValue.invoke(wcefPrivateKey.get(wifiConfiguration), ENTERPRISE_PRIV_KEY);
            }

			/* EAP Identity */
            if (!noEnterpriseFieldType) {
                if (wcefIdentity != null)
                    wcefSetValue.invoke(wcefIdentity.get(wifiConfiguration), userIdentity);
            }

			/* EAP Password */
            if (!noEnterpriseFieldType) {
                if (wcefPassword != null)
                    wcefSetValue.invoke(wcefPassword.get(wifiConfiguration), connection.getPassword());
            }

			/* EAp Client certificate */
            if (!noEnterpriseFieldType) {
                if (wcefClientCert != null)
                    wcefSetValue.invoke(wcefClientCert.get(wifiConfiguration), ENTERPRISE_CLIENT_CERT);
            }
            EliteSession.eLog.d(MODULE + " Created wifi Configuration " + wifiConfiguration.toString());
            EliteSession.eLog.d(MODULE + " createWiFiSettings adding wifi in to network");
            wifiNetworkId = wifiManager.addNetwork(wifiConfiguration);
            EliteSession.eLog.d(MODULE + " createWiFiSettings Netwirk Id is " + wifiNetworkId);
            wifiManager.saveConfiguration();

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + " " + e.getMessage());
            wifiNetworkId = -1;
        }
        return wifiNetworkId;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "EliteEAPWiFiSettings Factory Created";
    }

}
