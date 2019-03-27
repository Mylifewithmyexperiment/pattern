package com.elitecore.wifi.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.elitecore.wifi.listener.OnInternetCheckCompleteListner;
import com.elitecore.wifi.listener.OnWiFiTaskCompleteListner;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.services.InterNetAvailabilityCheckTask;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.SharedPreferenceConstants;

import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author viratsinh.parmar
 *         Common WiFi utility class will contains method for the common WiFi features.
 */

final public class CommonWiFiUtility {


    private static CommonWiFiUtility commonWiFiUtilityObj;
    private static String MODULE = "CommonWiFiUtility";
    private static boolean isCurrentTimeSet;
    private static boolean isTurnOnBycode;
    private static WifiInfo wifiInfo;
    private static long timerStartTime;
    private static List<WifiConfiguration> wifiConfigList;
    private static boolean removeStatus = false;
    private static BroadcastReceiver wifiReciver;
    private static int returnVal = 0;
    private static Process process = null;
    private static boolean isRegistered;

    private CommonWiFiUtility() {
        if (commonWiFiUtilityObj == null) {
            commonWiFiUtilityObj = new CommonWiFiUtility();
        }
    }

    /**
     * @param wifiManager
     * @return int value of maximum priority for available all WiFi in configuration list
     */

    public static int getMaxPriority(final WifiManager wifiManager) {

        final List<WifiConfiguration> configurations = wifiManager.getConfiguredNetworks();
        int pri = 0;
        if (configurations != null) {
            for (final WifiConfiguration config : configurations) {
                if (config.priority > pri) {
                    pri = config.priority;
                }
            }
        } else {
            EliteSession.eLog.d(MODULE, " Configuratin Null while checking priority.");
        }
        EliteSession.eLog.d(MODULE, " maximum priority for configured wifi is " + pri);
        return pri;
    }

    /**
     * @param wifiManager
     */
    public static void toggleWiFi(final WifiManager wifiManager) {
        EliteSession.eLog.d(MODULE, " toggleWiFi executed");
        if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);

        } else {
            wifiManager.setWifiEnabled(true);
            while (!wifiManager.isWifiEnabled()) {
                if (wifiManager.isWifiEnabled())
                    break;
            }
        }
        EliteSession.eLog.d(MODULE, " toggleWiFi done");

    }

    /**
     * @param wifiManager
     */
    public static void turnOffWiFi(final WifiManager wifiManager) {
        EliteSession.eLog.d(MODULE, " turnOffWiFi executed");
        wifiManager.setWifiEnabled(false);
        EliteSession.eLog.d(MODULE, " turnOffWiFi done");

    }

    /**
     * @param wifiManager
     */
    public static void turnOnWiFi(final WifiManager wifiManager) {
        EliteSession.eLog.d(MODULE, " turnOnWiFi executed");
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
            while (!wifiManager.isWifiEnabled()) {
                if (wifiManager.isWifiEnabled())
                    break;
            }
        }

        EliteSession.eLog.d(MODULE, " turnOnWiFi done");
    }

    /**
     * @param wifiManager
     * @param ssidname
     * @return boolean value true if SSID available in list and removed else false
     */
    public static boolean removeNetwork(final WifiManager wifiManager, final String ssidname) {
        boolean isWiFiOn;
        boolean status = false;
        EliteSession.eLog.d(MODULE, "Checking wifi status");
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
            while (wifiManager.isWifiEnabled()) {
                if (wifiManager.isWifiEnabled())
                    break;
            }
            isWiFiOn = false;
            EliteSession.eLog.d(MODULE, "WiFi enabled by api");
        } else {
            isWiFiOn = true;
            EliteSession.eLog.d(MODULE, "WiFi already enabled");
        }
        try {
            List<WifiConfiguration> wifiConfigList = wifiManager.getConfiguredNetworks();
            if (wifiConfigList != null) {
                for (WifiConfiguration wifi_config : wifiConfigList) {
                    if ((wifi_config.SSID.compareTo("\"" + ssidname + "\"") == 0) || wifi_config.SSID.equals(ssidname)) {
                        EliteSession.eLog.d(MODULE, "SSID found");
                        LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveBoolean(SharedPreferenceConstants.ISWIFISETTINGSDELETED, true);
                        status = wifiManager.removeNetwork(wifi_config.networkId);
                    }
                }
            } else {
                EliteSession.eLog.d(MODULE, "WIFI config list null or empty");
            }

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, " " + e.getMessage());
        }
        if (!isWiFiOn) {
            wifiManager.setWifiEnabled(false);
        }
        EliteSession.eLog.d(MODULE, "Returning status");
        return status;
    }

    public static void scheduleWiFiRemoveTask(final WifiManager wifiManager, final String ssidName, final int timeInterval) {
        isCurrentTimeSet = false;
        Timer removeTimer = new Timer();
        removeTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                try {
                    if (!isCurrentTimeSet) {
                        timerStartTime = System.currentTimeMillis();
                        isCurrentTimeSet = true;
                        if (!wifiManager.isWifiEnabled()) {
                            isTurnOnBycode = true;
                            wifiManager.setWifiEnabled(true);
                            while (wifiManager.isWifiEnabled()) {
                                if (wifiManager.isWifiEnabled())
                                    break;
                                EliteSession.eLog.e(MODULE, " " + "enabling");
                            }
                        } else {
                            if (getConnectionState(wifiManager, ssidName)) {
                                cancel();
                                return;
                            }
                            isTurnOnBycode = false;
                        }
                    }
                    try {
                        List<WifiConfiguration> wifiConfigList = wifiManager.getConfiguredNetworks();
                        if (wifiConfigList == null) {
                            Thread.sleep(1000);
                            wifiConfigList = wifiManager.getConfiguredNetworks();
                        }
                        if (wifiConfigList != null) {
                            for (WifiConfiguration wifi_config : wifiConfigList) {
                                if ((wifi_config.SSID.compareTo("\"" + ssidName + "\"") == 0) || wifi_config.SSID.equals(ssidName)) {
                                    removeStatus = wifiManager.removeNetwork(wifi_config.networkId);
                                }
                            }
                        } else {
                            EliteSession.eLog.d(MODULE, "WiFi scan is empty");
                        }
                    } catch (Exception e) {
                        EliteSession.eLog.e(MODULE, " " + e.getMessage());
                    }
                    EliteSession.eLog.d(MODULE, " " + "************************Remove WiFi Status  " + removeStatus);
                    if (removeStatus) {
                        LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveBoolean(SharedPreferenceConstants.ISWIFISETTINGSDELETED, true);
                        wifiManager.saveConfiguration();
                        EliteSession.eLog.d(MODULE, " " + "************disabling wifi");
                        if (isTurnOnBycode)
                            wifiManager.setWifiEnabled(false);
                        cancel();
                    }
                    if ((System.currentTimeMillis() - timerStartTime) > (1000 * 2)) {
                        wifiManager.saveConfiguration();
                        EliteSession.eLog.d(MODULE, " " + "************disabling wifi");
                        if (isTurnOnBycode)
                            wifiManager.setWifiEnabled(false);
                        cancel();
                    }
                } catch (Exception e) {
                    EliteSession.eLog.e(MODULE, " " + e.getMessage());
                }
            }
        }, (timeInterval * 1000), 100);
        EliteSession.eLog.d(MODULE, " " + "************Wifi remove task scheduled and will be executed");
    }

    private static boolean getConnectionState(final WifiManager wifiManager, String requiredConnection) {
        //		EliteSession.eLog.d(MODULE," Checking Connection State");
        SupplicantState supplicantStatus;
        WifiInfo wifiInfo;
        NetworkInfo networkInfo;
        DetailedState detailedState;


        wifiInfo = wifiManager.getConnectionInfo();
        supplicantStatus = wifiInfo.getSupplicantState();

        ConnectivityManager connectivityManager = (ConnectivityManager) LibraryApplication.getLibraryApplication().getLibraryContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        detailedState = networkInfo.getDetailedState();


        if (wifiInfo != null && wifiInfo.getSSID() != null && !wifiInfo.getSSID().equals("")
                && wifiInfo.getSSID().charAt(0) == '"'
                && wifiInfo.getSSID().charAt(wifiInfo.getSSID().length() - 1) == '"') {
            return supplicantStatus == SupplicantState.COMPLETED
                    && detailedState == DetailedState.CONNECTED
                    && requiredConnection.equalsIgnoreCase(wifiInfo.getSSID().substring(1, wifiInfo.getSSID().length() - 1));

        } else {
            return wifiInfo != null && wifiInfo.getSSID() != null
                    && supplicantStatus == SupplicantState.COMPLETED
                    && detailedState == DetailedState.CONNECTED
                    && requiredConnection.equalsIgnoreCase(wifiInfo.getSSID());
        }
    }

    public static void scanWiFiInRangeAndProcess(final OnWiFiTaskCompleteListner onWiFiTaskCompleteListner) {


        new WiFiReciever(onWiFiTaskCompleteListner);

     /*   EliteSession.eLog.d(MODULE, " scanWiFiInRangeAndProcess invoked from " + getCallingMethodName() + " method ");
        //		if(!isLicenseValidated)
        //			throw new Exception("Application ID is not Valid");

        final List<String> wifiList = new ArrayList<String>();
        final Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
        final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
            while (wifiManager.isWifiEnabled()) {
                if (wifiManager.isWifiEnabled())
                    break;
            }
        }
        wifiManager.startScan();
        wifiReciver = new BroadcastReceiver() {

            @Override
            public void onReceive(final Context context, Intent intent) {
                if (!isRegistered) {
                    EliteSession.eLog.d(MODULE, " Reciever not registered yet");
                    return;
                }
                EliteSession.eLog.i(MODULE, " inside onRecceive");
                wifiList.clear();
                // TODO Auto-generated method stub
                List<ScanResult> scanResults = wifiManager.getScanResults();

                if (scanResults != null && scanResults.size() > 0) {
                    for (int i = 0; i < scanResults.size(); i++) {
                        ScanResult scanResult = scanResults.get(i);
                        wifiList.add(scanResult.SSID);

                    }
                    EliteSession.eLog.d(MODULE, " List found " + wifiList.toString());
                } else {
                    EliteSession.eLog.d(MODULE, "Scan result null");
                }

                EliteSession.eLog.d(MODULE, " Un Register Recevier");
                isRegistered = false;
                try {
                  *//*  context.unregisterReceiver(wifiReciver);
                    wifiReciver = null;*//*
                    EliteSession.eLog.d(MODULE, " Sending call back to onWiFiScanComplete");
                    onWiFiTaskCompleteListner.onWiFiScanComplete(wifiList);
                } catch (Exception e) {
                    EliteSession.eLog.e(MODULE, e.getMessage());
                }


            }
        };
        isRegistered = true;
        context.registerReceiver(CommonWiFiUtility.wifiReciver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try
                    {
                        if (!isRegistered) {
                            if (wifiReciver != null) {
                                context.unregisterReceiver(wifiReciver);
                                wifiReciver = null;
                                break;
                            }
                        }
                    }
                    catch (Exception e)
                    {
//                        e.printStackTrace();
                    }

                }
            }
        }).start();
        EliteSession.eLog.d(MODULE, " scanWiFiInRangeAndProcess done");
*/
    }

    public static String getConnectionSSIDName() {
        EliteSession.eLog.d(MODULE, " isAlreadyConnected invoked");
        Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
        final WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null && wifiManager.getConnectionInfo() != null) {
            EliteSession.eLog.d(MODULE, " Connected with " + wifiManager.getConnectionInfo().getSSID());
            if (wifiManager.getConnectionInfo().getSSID() != null) {
                return wifiManager.getConnectionInfo().getSSID().replace("\"", "");
            }
        }
        return "";
    }

    public static boolean isMobileDataConnected() {
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                EliteSession.eLog.d(MODULE, "Checking mobile data connection");
                Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
                ConnectivityManager localConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                EliteSession.eLog.d(MODULE, "getting Mobile State");
                NetworkInfo info = localConnectivityManager.getActiveNetworkInfo();
                if (info != null) {
                    if (info.getType() == ConnectivityManager.TYPE_MOBILE && info.getState() == NetworkInfo.State.CONNECTED) {
                        EliteSession.eLog.d(MODULE, "getting Mobile State connected");
                        return true;
                    }
                }
            } else {
                return false;
            }
           /* NetworkInfo.State mobile = localConnectivityManager.getNetworkInfo(0).getState();

            if (mobile == NetworkInfo.State.CONNECTED) {
                EliteSession.eLog.d(MODULE, "getting Mobile State connected");
                return true;
            }*/

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error while getting Mobile Data" + e.getMessage());
        }

        return false;
    }

    private static String getCallingMethodName() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if (stackTraceElements.length >= 4) {
            return stackTraceElements[3].getMethodName();
        } else {
            return stackTraceElements[2].getMethodName();
        }
    }

    //    /**
//     * This method is use to check either internet connectivity available or not
//     *
//     * @param url ex:- www.google.com
//     * @return true if internet connectivity available
//     */
    private static int status =1;
    public static Boolean isConnectionAvailable() {
        try {

            EliteSession.eLog.i(MODULE, "Check internet connection");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        EliteSession.eLog.i(MODULE, "Before Waiting for connection");

                        process = Runtime.getRuntime().exec("/system/bin/ping -c 1 8.8.8.8");
                        returnVal = process.waitFor();

                        EliteSession.eLog.i(MODULE, "After Waiting for connection");

                        status = -1;
                    } catch (Exception e) {
                        EliteSession.eLog.e(MODULE, e.getMessage());
                        status =-1;
                    }
                }
            }).start();


            while(status > 0){
                Thread.sleep(100);
            }
            status = 1;

            boolean reachable = (returnVal == 0);
            if (reachable) {
                EliteSession.eLog.i(MODULE, "Connection Successful");
            } else {
                EliteSession.eLog.e(MODULE, "No Internet access");
            }
            return reachable;
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        } finally {
            if (process != null)
                process.destroy();
        }
        return false;
    }

    public static boolean isInternetAvailable(String URL) {
        try {
            EliteSession.eLog.d(MODULE, "Callling internet check task");
            String result = new InterNetAvailabilityCheckTask(null, URL).execute().get();
            if (result.compareTo(CoreConstants.INTERNET_CHECK_SUCCESS) == 0) {
                return true;
            }

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        }
        return false;
    }

    public static boolean isInternetAvailable(String URL, OnInternetCheckCompleteListner listner) {
        try {
            EliteSession.eLog.d(MODULE, "Callling internet check task");
            new InterNetAvailabilityCheckTask(listner, URL).execute();
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        }
        return false;
    }

    public static String getDeviceMacAddress() {
        String macAddress = null;
        try {
            EliteSession.eLog.d(MODULE, "Getting device Mac address");
            WifiManager wifiManager = (WifiManager) LibraryApplication.getLibraryApplication().getLibraryContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wInfo = wifiManager.getConnectionInfo();
            macAddress = wInfo.getMacAddress();


        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
        return macAddress;

    }

    /*  public static String getDeviceMacAddressOnlyText() {
          String macAddress=null;
          try {
              EliteSession.eLog.d(MODULE, "Getting device Mac address");
              WifiManager wifiManager = (WifiManager) LibraryApplication.getLibraryApplication().getLibraryContext().getSystemService(Context.WIFI_SERVICE);
              WifiInfo wInfo = wifiManager.getConnectionInfo();
              macAddress = wInfo.getMacAddress();
              macAddress=macAddress.replace("-","");
              macAddress=macAddress.replace(":","");
              macAddress=macAddress.toLowerCase();

          } catch (Exception e) {
              EliteSession.eLog.e(MODULE,e.getMessage());
          }
          return macAddress;

      }*/
    public static String getDeviceMacAddressOnlyText() {
        String macAddress = "";
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                macAddress = res1.toString();
                macAddress = macAddress.replace("-", "");
                macAddress = macAddress.replace(":", "");
                macAddress = macAddress.toLowerCase();
                return macAddress;
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    public static boolean isAlreadyConnected(String SSIDName) {
        EliteSession.eLog.d(MODULE, " isAlreadyConnected invoked");
        Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
        final WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null && wifiManager.getConnectionInfo() != null) {
            EliteSession.eLog.d(MODULE, " Connected with " + wifiManager.getConnectionInfo().getSSID());
            return wifiManager.getConnectionInfo().getSSID() != null && (wifiManager.getConnectionInfo().getSSID().compareTo(SSIDName) == 0 || wifiManager.getConnectionInfo().getSSID().compareTo("\"" + SSIDName + "\"") == 0);
        }
        return false;
    }

    public static boolean isMobileDataEnabled(Context paramContext) {
        EliteSession.eLog.d(MODULE, "isMobileDataEnabled Invoked");
        ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            EliteSession.eLog.d(MODULE, "service checked");
            Method localMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled");
            localMethod.setAccessible(true);
            EliteSession.eLog.d(MODULE, "set method accessible");
            boolean bool = ((Boolean) localMethod.invoke(localConnectivityManager, new Object[0])).booleanValue();
            EliteSession.eLog.d(MODULE, "getObject value");
            return bool;
        } catch (Exception localException) {
            EliteSession.eLog.e(MODULE, "Error while checking mobile data");
        }
        return true;
    }

    public static void setMobileDataEnabled(Context paramContext, boolean paramBoolean) {
        EliteSession.eLog.d(MODULE, "setMobileDataEnabled Invoked");
        ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            EliteSession.eLog.d(MODULE, "service checked");
            Class[] arrayOfClass = new Class[1];
            arrayOfClass[0] = Boolean.TYPE;
            EliteSession.eLog.d(MODULE, "type checked");
            Method localMethod = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", arrayOfClass);
            localMethod.setAccessible(true);
            EliteSession.eLog.d(MODULE, "set true");
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Boolean.valueOf(paramBoolean);
            EliteSession.eLog.d(MODULE, "set invoked");
            localMethod.invoke(localConnectivityManager, arrayOfObject);
            return;
        } catch (Exception localException) {
            EliteSession.eLog.e(MODULE, "Error while checking mobile data");
        }
    }

    public static int getExistingNetworkId(String SSID) {
        Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
        final WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        if (configuredNetworks != null) {
            EliteSession.eLog.d(MODULE, "configuredNetworks available");
            for (WifiConfiguration existingConfig : configuredNetworks) {
                if (existingConfig.SSID.compareTo(SSID) == 0 || existingConfig.SSID.compareTo("\"" + SSID + "\"") == 0) {
                    EliteSession.eLog.d(MODULE, "Network already configured");
                    return existingConfig.networkId;
                }
            }
        }
        return -1;
    }

      /*
This method is to check that provided MCC and MNC is a valid mobile operator or not.
MCC - List of MCC which can be multiple by adding comma seperator
MNC- List of MNC which can be multiple by adding comma seperator
   */

    public static boolean checkValidOperator(String MCCList, String MNCList, String seperator) {

        boolean validOperator = false;

        try {
            ArrayList<String> mccMNC = ElitelibUtility.getMccMncFromActiveNetworkSim();
            EliteSession.eLog.d(MODULE, "MCC :" + mccMNC.get(0));
            EliteSession.eLog.d(MODULE, "MNC :" + mccMNC.get(1));

            List<String> mccList = Arrays.asList(MCCList.split(seperator));
            List<String> mncList = Arrays.asList(MNCList.split(seperator));

            if (mccList.contains(mccMNC.get(0)) && mncList.contains(mccMNC.get(1))) {
                EliteSession.eLog.d(MODULE, "MCC and MNC are valid and  match");
                validOperator = true;
            } else {
                EliteSession.eLog.d(MODULE, "MCC and MNC are not valid and not match");
            }

        } catch (Exception e) {
            validOperator = false;
            EliteSession.eLog.e(MODULE, "Error while getting MCC and MNC");
        }
        return validOperator;
    }

    public static boolean isIPAddressIPV4(String ipAddress){

        Pattern VALID_IPV4_PATTERN = null;
        Pattern VALID_IPV6_PATTERN = null;
        final String ipv4Pattern = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
        final String ipv6Pattern = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";

        Matcher m1 = VALID_IPV4_PATTERN.matcher(ipAddress);
        if (m1.matches()) {
            return true;
        }
        Matcher m2 = VALID_IPV6_PATTERN.matcher(ipAddress);
        if (m2.matches()) {
            return false;
        }

        return  false;
    }
}

