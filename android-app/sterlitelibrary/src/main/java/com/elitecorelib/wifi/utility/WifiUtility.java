package com.elitecorelib.wifi.utility;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.elitecore.wifi.api.EliteWiFIConstants;
import com.elitecore.wifi.pojo.PojoConnection;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.utility.SharedPreferencesTask;
import com.elitecorelib.wifi.api.WiFiConstants;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.elitecore.wifi.api.EliteWiFIConstants.WIFI_CAPABILITIES;

@TargetApi(18)
public class WifiUtility {

    // Constants used for different security types
    public static final String WPA2 = "WPA2";
    public static final String WPA = "WPA";
    public static final String WEP = "WEP";
    public static final String OPEN = "OPEN";
    public static final String PSK = "PSK";
    public static final String SIM = "SIM";
    public static final String TTLS = "TTLS";
    public static final String WPAWPA2PSK = "WPA/WPA2 PSK";
    public static final String WPAWPA2 = "WPA/WPA2";
    public static final String WPAWPA2Enterprise = "WPA/WPA2 Enterprise";
    public static final String EAPSIM = "EAP-SIM";
    public static final String EAPAKA = "EAP-AKA";
    public static final String ELITEEAPSIM = "Elite EAPSIM";
    public static final String EAPTTLS = "EAP-TTLS";
    public static final String EAPPEAP = "EAP-PEAP";
    public static final String EAP = "EAP";


    // For EAP Enterprise fields
    public static final String WPA_EAP = "WPA-EAP";
    public static final String WPA2_EAP = "WPA2-EAP";
    public static final String EAP_802_1X = "802.1x EAP";

    static final String[] SECURITY_MODES = {EAP_802_1X, WPA2_EAP, WPA_EAP, EAP, WPA + "/" + WPA2 + " " + PSK, WPA, WPA2, WEP};

    static final String[] ENCRYPTION_MODES = {"TKIP", "AES", "mixed", "40 bit key", "104 bit key"};
    final static String INT_ENTERPRISEFIELD_NAME = "android.net.wifi.WifiConfiguration$EnterpriseField";
    // for default configuration
    private static final String MODULE = "WifiUtility";
    private static final String INT_PRIVATE_KEY = "private_key";
    private static final String INT_PHASE2 = "phase2";
    private static final String INT_PASSWORD = "password";
    private static final String INT_IDENTITY = "identity";
    private static final String INT_EAP = "eap";
    private static final String INT_CLIENT_CERT = "client_cert";
    private static final String INT_CA_CERT = "ca_cert";
    private static final String INT_ANONYMOUS_IDENTITY = "anonymous_identity";
    public static int wifiNetworkId;
    public static boolean isDeleted;
    public static SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();
    private static WifiManager wifiManager;
    private static WifiConfiguration defaultWifiConfiguration;
    private static long timerStartTime;
    private static boolean isCurrentTimeSet;
    private static boolean isTurnOnBycode;
    private static WifiInfo wifiInfo;
    private static SupplicantState supplicantStatus;
    private static ConnectivityManager connectivityManager;
    private static NetworkInfo networkInfo;
    private static DetailedState detailedState;
    private static boolean removeStatus = false;
    private static List<WifiConfiguration> wifiConfigList;

    public static String getWifiAccesspointSecurity(ScanResult scanResult) {

        final String cap = scanResult.capabilities;
        for (int i = 0; i < SECURITY_MODES.length; i++) {
            if (cap.contains(SECURITY_MODES[i])) {
                return "Secured with " + SECURITY_MODES[i];
            }
        }
        return OPEN;
    }

    public static String getWifiScanSecurity(JSONObject scanResult) throws Exception{


            final String cap = scanResult.getString(WIFI_CAPABILITIES);

            EliteSession.eLog.d(MODULE, "Capebilitys - " + cap);
            for (int i = 0; i < SECURITY_MODES.length; i++) {
                if (cap.contains(SECURITY_MODES[i])) {
                    EliteSession.eLog.d(MODULE, "SECURITY_MODES - " + SECURITY_MODES[i]);

                    if (SECURITY_MODES[i].equals(WPA_EAP) ||
                            SECURITY_MODES[i].equals(WPA2_EAP) ||
                            SECURITY_MODES[i].equals(EAP_802_1X)) {
                        EliteSession.eLog.d(MODULE, "Returns - " + WPAWPA2Enterprise);
                        return WPAWPA2Enterprise;
                    } else if (SECURITY_MODES[i].equals(WPA + "/" + WPA2 + " " + PSK) ||
                            SECURITY_MODES[i].equals(WPA) ||
                            SECURITY_MODES[i].equals(WPA2)) {
                        EliteSession.eLog.d(MODULE, "Returns - " + WPAWPA2);
                        return WPAWPA2;
                    } else if (SECURITY_MODES[i].equals(WEP)) {
                        EliteSession.eLog.d(MODULE, "Returns - " + WEP);
                        return WEP;

                    }
                }
            }
        return OPEN;
    }

    public static String getSignalStrength(int level) {

        String signalStrength = null;
        if (level >= -55) {
            signalStrength = WiFiConstants.SIGNALSTRENGTH_EXCELLENT;
        } else if (level >= -66 && level < -55) {
            signalStrength = WiFiConstants.SIGNALSTRENGTH_GOOD;
        } else if (level >= -77 && level < -67) {
            signalStrength = WiFiConstants.SIGNALSTRENGTH_FAIR;
        } else if (level >= -88 && level < -78) {
            signalStrength = WiFiConstants.SIGNALSTRENGTH_POOR;
        } else {
            signalStrength = WiFiConstants.SIGNALSTRENGTH_NO_SIGNAL;
        }
        return signalStrength;
    }

    public static int checkSignalStrength(int level) {

        int signalStrength = 0;
        if (level >= -55) {
            signalStrength = 4;
        } else if (level >= -66 && level < -55) {
            signalStrength = 3;
        } else if (level >= -77 && level < -67) {
            signalStrength = 2;
        } else if (level >= -88 && level < -78) {
            signalStrength = 1;
        }
        return signalStrength;
    }


    public static String getEncryptionType(String ssid) {

        WifiManager wifiManager = (WifiManager) LibraryApplication.getLibraryApplication().getLibraryContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
            while (wifiManager.isWifiEnabled()) {
            }
        }
        List<ScanResult> scanResults = wifiManager.getScanResults();

        if (scanResults != null) {

            for (int i = 0; i < scanResults.size(); i++) {
                ScanResult scanResult = scanResults.get(i);
                if (scanResult.SSID.equals(ssid)) {
                    final String capabilities = scanResult.capabilities;
                    for (int index = 0; index < ENCRYPTION_MODES.length; index++) {
                        if (capabilities.contains(ENCRYPTION_MODES[index])) {
                            return ENCRYPTION_MODES[index];
                        }
                    }
                }
            }
        }
        return "None";
    }

    public static String getCurrentSsid(Context context) {

        String ssid = null;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !connectionInfo.getSSID().trim().equals("")) {
                ssid = connectionInfo.getSSID();
            }
        }
        return ssid;
    }

    public static int createWepAccessPoint(PojoConnection connection) {
        wifiNetworkId = -1;
        try {
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = "\"" + connection.getSsid() + "\""; // IMP! This should be
            // in
            // Quotes!!
            wifiConfiguration.status = WifiConfiguration.Status.ENABLED;
            wifiConfiguration.allowedKeyManagement.set(KeyMgmt.NONE);

            if (connection.getEncryptionMethod().equalsIgnoreCase("104 bit key")) {
                wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            } else {
                wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            }

            wifiConfiguration.wepKeys[0] = "\"" + connection.getPassword() + "\""; // This is
            // the
            // WEP
            // Password
            wifiConfiguration.wepTxKeyIndex = 0;

            WifiManager wifiManag = (WifiManager) LibraryApplication.getLibraryApplication().getLibraryContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiManag.setWifiEnabled(true);
            wifiNetworkId = wifiManag.addNetwork(wifiConfiguration);
            wifiManag.saveConfiguration();
            wifiManag.enableNetwork(wifiNetworkId, true);
        } catch (Exception e) {

            wifiNetworkId = -1;
        }
        return wifiNetworkId;
    }

    public static int createWPAAccessPoint(PojoConnection connection) {
        wifiNetworkId = -1;
        try {
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = "\"" + connection.getSsid() + "\"";
            wifiConfiguration.status = WifiConfiguration.Status.ENABLED;
            wifiConfiguration.allowedKeyManagement.set(KeyMgmt.WPA_PSK);
            wifiConfiguration.allowedKeyManagement.set(KeyMgmt.WPA_EAP);
            wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wifiConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            String encryptionMethod = connection.getEncryptionMethod();
            /*if (encryptionMethod.equalsIgnoreCase("TKIP")) {
                wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			} else if (encryptionMethod.equalsIgnoreCase("AES")) {
				wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			} else/* if (encryptionMethod.equalsIgnoreCase("TKIP")) {
				wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			}*/

            wifiConfiguration.preSharedKey = "\"" + connection.getPassword() + "\"";

            WifiManager wifiManag = (WifiManager) LibraryApplication.getLibraryApplication().getLibraryContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiManag.setWifiEnabled(true);

            wifiNetworkId = wifiManag.addNetwork(wifiConfiguration);
            wifiManag.saveConfiguration();
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + " " + e.getMessage());

        }
        return wifiNetworkId;
    }

    public static int createOpenAccessPoint(String ssid) {
        wifiNetworkId = -1;
        try {
            WifiManager wifiManager = (WifiManager) LibraryApplication.getLibraryApplication().getLibraryContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = "\"" + ssid + "\"";
            wifiConfiguration.allowedKeyManagement.set(KeyMgmt.NONE);
            wifiConfiguration.status = WifiConfiguration.Status.ENABLED;
            wifiConfiguration.priority = getMaxPriority(wifiManager) + 1;
            wifiNetworkId = wifiManager.addNetwork(wifiConfiguration);
            wifiManager.saveConfiguration();
        } catch (Exception e) {
            wifiNetworkId = -1;
            EliteSession.eLog.e(MODULE + " " + e.getMessage());


        }
        return wifiNetworkId;
    }


    public static int createEAPAccessPoint(PojoConnection connection) {
        wifiNetworkId = -1;
        try {
            EliteSession.eLog.d(MODULE + " starting createEAPAccessPoint");
            Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
            wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            /*
             * TelephonyManager tele_manager = (TelephonyManager) context
			 * .getSystemService(Context.TELEPHONY_SERVICE);
			 */
            String ssidname = connection.getSsid();

            String username = connection.getIdentity();
            String password = connection.getPassword();
            String authProtocol = connection.getAuthenticationMethod();
            String phaseAuth = connection.getPhase2Authentication();
            wifiNetworkId = networkConfiguration(ssidname, username, password, authProtocol, phaseAuth, connection);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + " " + e.getMessage());
            wifiNetworkId = -1;
        }
        return wifiNetworkId;
    }

    public static int networkConfiguration(String ssidname, String username, String password, String protocol, String phaseAuth,
                                           PojoConnection connection) {
        EliteSession.eLog.d(MODULE + " in Method  networkConfiguration");
        int networkId = -1;
        try {
            List<WifiConfiguration> wifiConfigList = wifiManager.getConfiguredNetworks();
            if (wifiConfigList != null) {
                for (WifiConfiguration wifi_config : wifiConfigList) {
                    if (wifi_config != null && wifi_config.SSID != null && wifi_config.SSID.equals(ssidname)) {
                        EliteSession.eLog.d(MODULE + " in Method  networkConfiguration Network already for ssid" + ssidname);
                        EliteSession.eLog.d(MODULE + " in Method  networkConfiguration remove network from configuration");
                        wifiManager.removeNetwork(wifi_config.networkId);
                    }
                }
            }
            networkId = saveEapConfig(ssidname, username, password, protocol, phaseAuth, connection);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + " " + e.getMessage());
            networkId = -1;
        }
        return networkId;
    }

    private static int saveEapConfig(String ssidString, String userName, String passString, String protocol, String phaseAuth,
                                     PojoConnection connection) {
        try {
            String userIdentity;
            if (spTask.getString(CoreConstants.Realm_Eapttls).equals("")) {
                userIdentity = userName + EliteWiFIConstants.REALM_EAPTTLS;
            } else {
                userIdentity = userName + spTask.getString(CoreConstants.Realm_Eapttls);
            }
            Thread.sleep(2000);
            /* Create a WifiConfig */
            defaultWifiConfiguration = new WifiConfiguration();
            if (Build.VERSION.SDK_INT >= 18 && protocol.equalsIgnoreCase("TTLS")) {
                EliteSession.eLog.d(MODULE + " Build.VERSION.SDK_INT  validated");
                WifiEnterpriseConfig defaultWifiEnterpriseConfig;
                defaultWifiEnterpriseConfig = new WifiEnterpriseConfig();


                defaultWifiEnterpriseConfig.setIdentity(userIdentity);
                defaultWifiEnterpriseConfig.setPassword(passString);
                defaultWifiEnterpriseConfig.setEapMethod(WifiEnterpriseConfig.Eap.TTLS);
                if (phaseAuth.equals("MSCHAPV2")) {
                    defaultWifiEnterpriseConfig.setPhase2Method(WifiEnterpriseConfig.Phase2.MSCHAPV2);
                } else if (phaseAuth.equals("GTC")) {
                    defaultWifiEnterpriseConfig.setPhase2Method(WifiEnterpriseConfig.Phase2.GTC);
                } else if (phaseAuth.equals("MSCHAP")) {
                    defaultWifiEnterpriseConfig.setPhase2Method(WifiEnterpriseConfig.Phase2.MSCHAP);
                } else if (phaseAuth.equals("NONE")) {
                    defaultWifiEnterpriseConfig.setPhase2Method(WifiEnterpriseConfig.Phase2.NONE);
                } else if (phaseAuth.equals("PAP")) {
                    defaultWifiEnterpriseConfig.setPhase2Method(WifiEnterpriseConfig.Phase2.PAP);
                }
                defaultWifiConfiguration.enterpriseConfig = defaultWifiEnterpriseConfig;
            }
            /* AP Name */
            defaultWifiConfiguration.SSID = "\"" + ssidString + "\"";

			/* Priority */
            // defaultWifiConfiguration.priority = 40;
            defaultWifiConfiguration.priority = getMaxPriority(wifiManager) + 1;
            EliteSession.eLog.d(MODULE + " defaultWifiConfiguration.priority " + defaultWifiConfiguration.priority);
            // defaultWifiConfiguration.priority = 3;
            /* Set value for Hidden SSID */
            defaultWifiConfiguration.hiddenSSID = connection.isDefault();
            // defaultWifiConfiguration.hiddenSSID = false;

			/* Key Mgmnt */
            defaultWifiConfiguration.allowedKeyManagement.clear();
            defaultWifiConfiguration.allowedKeyManagement.set(KeyMgmt.IEEE8021X);
            defaultWifiConfiguration.allowedKeyManagement.set(KeyMgmt.WPA_EAP);

			/* Group Ciphers : Encryption methods */
            defaultWifiConfiguration.allowedGroupCiphers.clear();
            defaultWifiConfiguration.allowedGroupCiphers // to be deleted
                    .set(WifiConfiguration.GroupCipher.CCMP);
            defaultWifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            defaultWifiConfiguration.allowedGroupCiphers // to be deleted
                    .set(WifiConfiguration.GroupCipher.WEP104);
            defaultWifiConfiguration.allowedGroupCiphers // to be deleted
                    .set(WifiConfiguration.GroupCipher.WEP40);

			/* Pairwise ciphers : Encryption methods */
            defaultWifiConfiguration.allowedPairwiseCiphers.clear();
            defaultWifiConfiguration.allowedPairwiseCiphers // to be deleted
                    .set(WifiConfiguration.PairwiseCipher.CCMP);
            defaultWifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);

			/* Protocols */
            defaultWifiConfiguration.allowedProtocols.clear();
            defaultWifiConfiguration.allowedProtocols // to be deleted
                    .set(WifiConfiguration.Protocol.RSN);
            defaultWifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);

			/* Reflection Api to set Enterprise fields */
            setEapEnterprise(userName, passString, protocol, phaseAuth);

            defaultWifiConfiguration.status = WifiConfiguration.Status.ENABLED;
            wifiNetworkId = wifiManager.addNetwork(defaultWifiConfiguration);
            wifiConfigList = wifiManager.getConfiguredNetworks();
            if (wifiConfigList != null)
                EliteSession.eLog.d(MODULE + " " + wifiConfigList.toString());
            if (defaultWifiConfiguration != null)
                EliteSession.eLog.d(MODULE + " in Save EAP Config wifinetworkid=" + defaultWifiConfiguration.toString());
            return wifiNetworkId;
        } catch (Exception e) {
            wifiNetworkId = -1;
            EliteSession.eLog.e(MODULE + " " + e.getMessage());
        }
        return wifiNetworkId;
    }

    @SuppressWarnings("rawtypes")
    private static void setEapEnterprise(String userName, String passString, String protocol, String phaseAuth) {

        /******************************** Configuration Strings ****************************************************/
        final String ENTERPRISE_EAP = protocol; // "TTLS";
        final String ENTERPRISE_CLIENT_CERT = ""; // "keystore://USRCERT_CertificateName"
        final String ENTERPRISE_PRIV_KEY = "";// "keystore://USRPKEY_CertificateName"
        // CertificateName = Name given to the certificate while installing it

        final String ENTERPRISE_PHASE2 = "auth=" + phaseAuth;// phaseAuth
        final String ENTERPRISE_ANON_IDENT = "";
        final String ENTERPRISE_CA_CERT = "";
        /******************************** Configuration Strings ****************************************************/

        // Enterprise Settings
        // Reflection magic here too, need access to non-public APIs
        try {
            // Let the magic start
            Class[] wcClasses = WifiConfiguration.class.getClasses();
            // null for some java compiler
            Class wcEnterpriseField = null;
            for (Class wcClass : wcClasses) {
                if (wcClass.getName().equals(INT_ENTERPRISEFIELD_NAME)) {
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
                    wcefSetValue.invoke(wcefEap.get(defaultWifiConfiguration), ENTERPRISE_EAP);
            }
			/* EAP Phase 2 Authentication */
            if (!noEnterpriseFieldType) {
                if (wcefPhase2 != null)
                    wcefSetValue.invoke(wcefPhase2.get(defaultWifiConfiguration), ENTERPRISE_PHASE2);
            }

			/* EAP Anonymous Identity */
            if (!noEnterpriseFieldType) {
                if (wcefAnonymousId != null)
                    wcefSetValue.invoke(wcefAnonymousId.get(defaultWifiConfiguration), ENTERPRISE_ANON_IDENT);
            }

			/* EAP CA Certificate */
            if (!noEnterpriseFieldType) {
                if (wcefCaCert != null)
                    wcefSetValue.invoke(wcefCaCert.get(defaultWifiConfiguration), ENTERPRISE_CA_CERT);
            }

			/* EAP Private key */
            if (!noEnterpriseFieldType) {
                if (wcefPrivateKey != null)
                    wcefSetValue.invoke(wcefPrivateKey.get(defaultWifiConfiguration), ENTERPRISE_PRIV_KEY);
            }

			/* EAP Identity */
            if (!noEnterpriseFieldType) {
                if (wcefIdentity != null)
                    wcefSetValue.invoke(wcefIdentity.get(defaultWifiConfiguration), userName);
            }

			/* EAP Password */
            if (!noEnterpriseFieldType) {
                if (wcefPassword != null)
                    wcefSetValue.invoke(wcefPassword.get(defaultWifiConfiguration), passString);
            }

			/* EAp Client certificate */
            if (!noEnterpriseFieldType) {
                if (wcefClientCert != null)
                    wcefSetValue.invoke(wcefClientCert.get(defaultWifiConfiguration), ENTERPRISE_CLIENT_CERT);
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + " " + e.getMessage());

        }
    }

    private static int getMaxPriority(final WifiManager wifiManager) {
        final List<WifiConfiguration> configurations = wifiManager.getConfiguredNetworks();
        int pri = 0;
        for (final WifiConfiguration config : configurations) {
            if (config.priority > pri) {
                pri = config.priority;
            }
        }
        EliteSession.eLog.d(MODULE + " maximum priority for configured wifi is " + pri);
        return pri;
    }


    public static boolean getConnectionState(String requiredConnection) {
        //		EliteSession.eLog.d(MODULE+" Checking Connection State");
        Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
        wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();
        supplicantStatus = wifiInfo.getSupplicantState();
        if (connectivityManager == null) {
            connectivityManager = (ConnectivityManager) LibraryApplication.getLibraryApplication().getLibraryContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        detailedState = networkInfo.getDetailedState();


        if (wifiInfo != null && wifiInfo.getSSID() != null && !wifiInfo.getSSID().equals("") && wifiInfo.getSSID().charAt(0) == '"'
                && wifiInfo.getSSID().charAt(wifiInfo.getSSID().length() - 1) == '"') {
            return supplicantStatus == SupplicantState.COMPLETED
                    && detailedState == DetailedState.CONNECTED
                    && requiredConnection.equalsIgnoreCase(
                    wifiInfo.getSSID().substring(1, wifiInfo.getSSID().length() - 1));
        } else {
            return wifiInfo != null && wifiInfo.getSSID() != null && supplicantStatus == SupplicantState.COMPLETED
                    && detailedState == DetailedState.CONNECTED
                    && requiredConnection.equalsIgnoreCase(wifiInfo.getSSID());
        }
    }

    public static void scheduleWiFiRemoveTask(final String ssidName) {


        isCurrentTimeSet = false;
        Timer removeTimer = new Timer();
        removeTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                try {
                    if (!isCurrentTimeSet) {
                        Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
                        wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        timerStartTime = System.currentTimeMillis();
                        isCurrentTimeSet = true;
                        if (!wifiManager.isWifiEnabled()) {
                            isTurnOnBycode = true;
                            wifiManager.setWifiEnabled(true);
                            try {
                                //List<WifiConfiguration> wifiConfigList = wifiManager.getConfiguredNetworks();
                                if (wifiConfigList != null) {
                                    for (WifiConfiguration wifi_config : wifiConfigList) {
                                        if ((wifi_config.SSID.compareTo("\"" + ssidName + "\"") == 0) || wifi_config.SSID.equals(ssidName)) {
                                            removeStatus = wifiManager.removeNetwork(wifi_config.networkId);
                                        }
                                    }
                                }

                            } catch (Exception e) {
                                EliteSession.eLog.e(MODULE + " " + e.getMessage());
                            }
                            while (wifiManager.isWifiEnabled()) {
                                if (wifiManager.isWifiEnabled())
                                    break;
                                EliteSession.eLog.e(MODULE + " " + "enabling");
                            }
                            //Thread.sleep(1000);
                        } else {
                            if (getConnectionState(ssidName)) {
                                cancel();
                                return;
                            }
                            isTurnOnBycode = false;
                        }
                    }

                    try {
                        //List<WifiConfiguration> wifiConfigList = wifiManager.getConfiguredNetworks();
                        if (wifiConfigList != null) {
                            for (WifiConfiguration wifi_config : wifiConfigList) {
                                if ((wifi_config.SSID.compareTo("\"" + ssidName + "\"") == 0) || wifi_config.SSID.equals(ssidName)) {
                                    removeStatus = wifiManager.removeNetwork(wifi_config.networkId);
                                }
                            }
                        }

                    } catch (Exception e) {
                        EliteSession.eLog.e(MODULE + " " + e.getMessage());
                    }
                    //EliteSession.eLog.d(MODULE+" "+ "************************Remove WiFi Status  "+removeStatus);
                    if (removeStatus) {
                        wifiManager.saveConfiguration();
                        EliteSession.eLog.d(MODULE + " " + "************disabling wifi");
                        if (isTurnOnBycode)
                            wifiManager.setWifiEnabled(false);
                        cancel();
                    }
                    if ((System.currentTimeMillis() - timerStartTime) > (1000 * 2)) {
                        wifiManager.saveConfiguration();
                        EliteSession.eLog.d(MODULE + " " + "************disabling wifi");
                        if (isTurnOnBycode)
                            wifiManager.setWifiEnabled(false);
                        cancel();
                    }
                } catch (Exception e) {
                    EliteSession.eLog.e(MODULE + " " + e.getMessage());
                }
            }
        }, 45000, 100);
        EliteSession.eLog.d(MODULE + " " + "************Wifi remove task scheduled and will be executed");
        //wifiManager.setWifiEnabled(false);
        WifiUtility.isDeleted = true;

    }


    public static double getDistanceBetweenLocations(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'M') {
            dist = (dist * 1.609344) * 1000;
        }
        return (dist);
    }

    /* :: This function converts decimal degrees to radians : */
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /* :: This function converts radians to decimal degrees : */
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }



}
