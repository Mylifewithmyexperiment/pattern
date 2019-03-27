package com.elitecore.wifi.api;

import com.elitecore.wifi.pojo.PojoConnection;
import com.elitecorelib.core.EliteSession;


class WiFISettingsFactory {

    private static final String MODULE = "WiFISettingsFactory";

    private WiFISettingsFactory() {
    }

    public static IEliteWiFiconfigurator getWiFiSettings(PojoConnection connection) throws Exception {
        IEliteWiFiconfigurator wifiConfiguration;
//        if (CoreConstants.ENABLE_ELITECOREAAA && (connection.getSecurity().equals(EliteWiFIConstants.WIFI_EAPTTLS)
//                || connection.getSecurity().equals(EliteWiFIConstants.WIFI_EAPSIM))) {
//            connection.setSecurity(EliteWiFIConstants.WIFI_EAPTTLS);
//            wifiConfiguration = getEAPTTLSSettings();
//        } else {
        if (connection.getSecurity().equals(EliteWiFIConstants.WIFI_EAPSIM)) {
            wifiConfiguration = getEAPSIMSettings();
        } else if (connection.getSecurity().equals(EliteWiFIConstants.WIFI_EAPTTLS)) {
            wifiConfiguration = getEAPTTLSSettings();
        } else if (connection.getSecurity().equals(EliteWiFIConstants.WIFI_EAPPEAP)) {
            wifiConfiguration = getEAPPEAPSettings();
        } else if (connection.getSecurity().equals(EliteWiFIConstants.WIFI_EAPAKA)) {
            wifiConfiguration = getEAPAKASettings();
        } else if (connection.getSecurity().equalsIgnoreCase(EliteWiFIConstants.WIFI_OPEN)) {
            wifiConfiguration = new OPENWiFiSettings();
        } else if (connection.getSecurity().equals(EliteWiFIConstants.WIFI_WPA) ||
                connection.getSecurity().compareTo(EliteWiFIConstants.WIFI_WPA2PSK) == 0 ||
                connection.getSecurity().compareTo(EliteWiFIConstants.WIFI_WPAWPA2) == 0 ||
                connection.getSecurity().compareTo(EliteWiFIConstants.WIFI_WPAWPA2PSK) == 0) {
            wifiConfiguration = new WPAWiFiSettings();
        } else {
            throw new Exception(" Invalid Seucirity Type");
        }

        // }
//         EliteSession.eLog.d(MODULE + " SDK Version is . " + AndroidSDKVersion.CURRENT_API_VERSION + "(" + AndroidSDKVersion.Codenames.getCodename() + ")");
//        try {
//             EliteSession.eLog.d(MODULE, "BRAND :" + Build.BRAND);
//             EliteSession.eLog.d(MODULE, "MANUFACTURER :" + Build.MANUFACTURER);
//             EliteSession.eLog.d(MODULE, "MODEL :" + Build.MODEL);
//             EliteSession.eLog.d(MODULE, "RELEASE :" + Build.VERSION.RELEASE);
//
//        } catch (Exception e) {
//             EliteSession.eLog.e(MODULE, e.getMessage());
//
//        }
        if (wifiConfiguration != null)
            EliteSession.eLog.d(MODULE + " Created Factory for WiFi connection is " + wifiConfiguration.toString());
        return wifiConfiguration;
    }

    private static IEliteWiFiconfigurator getEAPTTLSSettings() {
        IEliteWiFiconfigurator eapTTLSSettings;
        switch (AndroidSDKVersion.CURRENT_API_VERSION) {
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                eapTTLSSettings = new EliteEAPWiFiSettings();
                break;

            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                eapTTLSSettings = new EAPTTLSWiFiSettings();
                break;

            default:
                eapTTLSSettings = new EAPTTLSWiFiSettings();
                break;
        }

        return eapTTLSSettings;
    }

    private static IEliteWiFiconfigurator getEAPPEAPSettings() {
        IEliteWiFiconfigurator eapPEAPSettings;
        switch (AndroidSDKVersion.CURRENT_API_VERSION) {
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                eapPEAPSettings = new EliteEAPWiFiSettings();
                break;

            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                eapPEAPSettings = new EAPPEAPWiFiSetting();
                break;

            default:
                eapPEAPSettings = new EAPPEAPWiFiSetting();
                break;
        }

        return eapPEAPSettings;
    }

    private static IEliteWiFiconfigurator getEAPSIMSettings() {
        IEliteWiFiconfigurator eapSimSettings = null;
        switch (AndroidSDKVersion.CURRENT_API_VERSION) {
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                eapSimSettings = new EliteEAPWiFiSettings();
                break;
            case 18:
            case 19:
            case 20:
                eapSimSettings = new EAPTTLSWiFiSettings();
                break;

            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
                eapSimSettings = new EAPSIMWiFiSettings();
                break;

            default:
                eapSimSettings = new EAPSIMWiFiSettings();
                break;
        }

        return eapSimSettings;
    }

    private static IEliteWiFiconfigurator getEAPAKASettings() {
        IEliteWiFiconfigurator eapSimSettings = null;
        switch (AndroidSDKVersion.CURRENT_API_VERSION) {
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                eapSimSettings = new EliteEAPWiFiSettings();
                break;
            case 18:
            case 19:
            case 20:
                eapSimSettings = new EAPTTLSWiFiSettings();
                break;

            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
                eapSimSettings = new EAPAKAWiFiSettings();
                break;

            default:
                eapSimSettings = new EAPAKAWiFiSettings();
                break;
        }

        return eapSimSettings;
    }
}
