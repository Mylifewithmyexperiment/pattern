package com.elitecore.wifi.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.elitecore.elitesmp.utility.EliteSMPUtility;
import com.elitecore.wifi.listener.OnWiFiTaskCompleteListner;
import com.elitecore.wifi.pojo.PojoConnection;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.interfaces.AnalyticId;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.SharedPreferencesTask;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.elitecorelib.core.CoreConstants.WIFI_CONNECTION_TIMEOUT;


final class WIFIConnection implements OnWiFiTaskCompleteListner {

    private static final String MODULE = "WIFIConnection";
    private static WIFIConnection wifiConnectionobj;
    private static long timerStartTime;
    private final SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();
    public int networkId = -1;
    private OnWiFiTaskCompleteListner callback;
    private WifiManager wifiManager;
    private WifiInfo wifiInfo;
    private SupplicantState supplicantStatus;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private DetailedState detailedState;
    private String result;
    private boolean isConnectionTaskCompleted;
    private PojoConnection mPojoConnection;
    private boolean keepWiFiOn;
    Context mContext;

    private WIFIConnection() {
    }

    public static WIFIConnection getWIFIConnection() {
        if (wifiConnectionobj == null) {
            wifiConnectionobj = new WIFIConnection();
        }
        return wifiConnectionobj;
    }

    public void doWiFiConnection(Context context, int NetworkID, PojoConnection mPojoConnection, OnWiFiTaskCompleteListner callback, boolean keepWiFiOn) {
        this.mPojoConnection = mPojoConnection;
        this.callback = callback;
        this.networkId = NetworkID;
        this.keepWiFiOn = keepWiFiOn;
        mContext = context;
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean state = getConnectionState();
        EliteSession.eLog.d(MODULE, "Wifi enable and connected configured wifi status= " + state);
        if (!state) {
            connectToWifi();
        } else {
            result = WiFiConstants.ALREADYCONNECTED;
            sendResult();
        }
    }

    private void connectToWifi() {
        if (networkId != -1) {
            doConnetion();
        } else {
            EliteSession.eLog.d(MODULE, " Not Connection because Network ID is  " + networkId);
            result = WiFiConstants.NOTCONNECTED;
        }
    }

    private boolean getConnectionState() {

        try {
            /*wifiInfo = wifiManager.getConnectionInfo();
            supplicantStatus = wifiInfo.getSupplicantState();
            if (connectivityManager == null) {

            }
            networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            detailedState = networkInfo.getDetailedState();
            if (supplicantStatus != null && detailedState != null) {
                EliteSession.eLog.d(MODULE, "  getConnectionState() supplicantStatus=" + supplicantStatus + " detailedState=" + detailedState);
                try {
                    if (wifiInfo != null) {
                        EliteSession.eLog.d(MODULE, " WiFi is " + wifiInfo.getSSID());
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }*/
            if (CommonWiFiUtility.isAlreadyConnected(mPojoConnection.getSsid()) && EliteSMPUtility.getIPAddress(LibraryApplication.getLibraryApplication().getLibraryContext()).compareTo("0.0.0.0") != 0) {
                EliteSession.eLog.d(MODULE, "  Checked from Utility , already connected");
                return true;

            }

          /*  if (wifiInfo != null && wifiInfo.getSSID() != null && !wifiInfo.getSSID().equals("") && wifiInfo.getSSID().charAt(0) == '"'
                    && wifiInfo.getSSID().charAt(wifiInfo.getSSID().length() - 1) == '"') {
                if (supplicantStatus == SupplicantState.COMPLETED
                        && detailedState == DetailedState.CONNECTED
                        && ssidName.equalsIgnoreCase(
                        wifiInfo.getSSID().substring(1, wifiInfo.getSSID().length() - 1))) {
                    EliteSession.eLog.d(MODULE, "  getConnectionState() supplicantStatus/detailedState status true ");
                    return true;
                } else {
                    EliteSession.eLog.d(MODULE, "  getConnectionState() supplicantStatus/detailedState status false ");
                    return false;
                }
            } else {
                if (wifiInfo != null && wifiInfo.getSSID() != null && supplicantStatus == SupplicantState.COMPLETED
                        && detailedState == DetailedState.CONNECTED
                        && ssidName.equalsIgnoreCase(wifiInfo.getSSID())) {
                    EliteSession.eLog.d(MODULE, "  getConnectionState() detailedState/supplicantStatus status true ");
                    return true;
                } else {
                    EliteSession.eLog.d(MODULE, "  getConnectionState() detailedState/supplicantStatus status false ");
                    return false;
                }
            }*/
            return false;
        }catch (SecurityException e){
            EliteSession.eLog.e(MODULE, e.getMessage());
            return false;
        }catch (Exception e){
            EliteSession.eLog.e(MODULE, e.getMessage());
            return false;
        }
    }

    private synchronized void lockConnectionThread() {
        while (!isConnectionTaskCompleted) {
            try {
                wait();
            } catch (InterruptedException e) {
            } catch (Exception e) {
            }
        }
    }

    private synchronized void unlockConnectionThread() {
        isConnectionTaskCompleted = true;
        notifyAll();
    }

    private void doConnetion() {
        EliteSession.eLog.d(MODULE, " doConnection method invoked to enable network");
        wifiManager.disconnect();
        if (wifiManager.enableNetwork(networkId, true)) {
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Calendar calendar = Calendar.getInstance();
                    if (getConnectionState()) {
                        unlockConnectionThread();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        EliteSession.eLog.d(MODULE, " doConnection Method: Connection established : Canceling timer.");
                        cancelTimer(true, false);
                    }
                    EliteSession.eLog.d(MODULE, "Wifi Time Out Interval - " + spTask.getInt(WIFI_CONNECTION_TIMEOUT));

                    if (spTask.getInt(WIFI_CONNECTION_TIMEOUT) == 0)
                        spTask.saveInt(CoreConstants.WIFI_CONNECTION_TIMEOUT, CoreConstants.WIFI_CONNECTION_DEFAULT_TIMEOUT);

                    if ((System.currentTimeMillis() - timerStartTime) > (1000 * spTask.getInt(WIFI_CONNECTION_TIMEOUT))) {
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        EliteSession.eLog.d(MODULE, "  doConnetion() Connection time out");
                        cancelTimer(false, true);
                    }
                }

                private void cancelTimer(boolean status, boolean showNextscreen) {
                    if (status) {
                        result = WiFiConstants.CONNECTED;
                    } else {
                        EliteSession.eLog.d(MODULE, "  Connection fail disabling Wifi");
                        if (!keepWiFiOn)
                            CommonWiFiUtility.turnOffWiFi(wifiManager);
                        result = WiFiConstants.NOTCONNECTED;
                    }
                    sendResult();
                    cancel();

                }
            };
            timerStartTime = System.currentTimeMillis();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timerStartTime);
            timer.scheduleAtFixedRate(timerTask, 0, 1000);
            isConnectionTaskCompleted = false;
            lockConnectionThread();
        } else {
            EliteSession.eLog.d(MODULE, " doConnection method rror in connection");

        }
    }

    private void sendResult() {
        if (callback != null) {
            try {
                EliteSession.eLog.d(MODULE, " sendResult invoked , reuslt is =" + result);
                if(result.equals(WiFiConstants.NOTCONNECTED) && spTask.getString(CoreConstants.IFFAILTRYWITHANOTHERMETHOD).equalsIgnoreCase("yes")){
                    EliteSession.eLog.d(MODULE, "request to EAP-TTSL");

                    spTask.saveString(CoreConstants.IFFAILTRYWITHANOTHERMETHOD,"no");

                    PojoConnection activateConnection = new PojoConnection();
                    activateConnection.setSsid(mPojoConnection.getSsid());
                    if(mPojoConnection.isDefault()) {
                        EliteSession.eLog.d(MODULE, "request to EAP-TTSL & is default true");
                        activateConnection.setDefault(true);
                    }
                    ElitelibUtility.addAnalytic(AnalyticId.WiFi_Connection, AnalyticId.WiFi_Connection_value, this.mPojoConnection.getSsid(), result, this.networkId + "");

                    activateConnection.setSecurity(EliteWiFIConstants.WIFI_EAPTTLS);
                    IWiFiConfiguration  api = new EliteWiFiAPI(this);
                    api.connectToWiFi(mContext, activateConnection, callback, this.keepWiFiOn, false);

                }else{
                    EliteSession.eLog.d(MODULE, "request to EAP-SIM");
                    ElitelibUtility.addAnalytic(AnalyticId.WiFi_Connection, AnalyticId.WiFi_Connection_value, this.mPojoConnection.getSsid(), result, this.networkId + "");
                    callback.onWiFiTaskComplete(result);
                }

            } catch (Exception e) {
                EliteSession.eLog.e(MODULE, "Error during call back onTaskComplete " + e.getMessage());
            }
        }
    }

    @Override
    public void onWiFiTaskComplete(String result) {
        callback.onWiFiTaskComplete(result);
    }

    @Override
    public void isWiFiInRange(boolean status) {

    }

    @Override
    public void getResponseData(String responseData) {

    }

    @Override
    public void onWiFiScanComplete(List<String> ssidList) {

    }
}
