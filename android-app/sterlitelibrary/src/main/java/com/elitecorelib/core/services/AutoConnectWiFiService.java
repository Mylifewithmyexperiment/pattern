package com.elitecorelib.core.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.elitecore.wifi.api.CommonWiFiUtility;
import com.elitecore.wifi.api.EliteWiFiAPI;
import com.elitecore.wifi.api.IWiFiConfiguration;
import com.elitecore.wifi.listener.OnWiFiTaskCompleteListner;
import com.elitecore.wifi.pojo.PojoConnection;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.LocationServiceCheck;
import com.elitecorelib.core.utility.SharedPreferencesTask;
import com.elitecorelib.wifi.api.WiFiConstants;

import java.util.List;

import static com.elitecorelib.core.CoreConstants.KEY_CHECK_SIM_OPERATOR;
import static com.elitecorelib.core.CoreConstants.KEY_LOCAL_MCC;
import static com.elitecorelib.core.CoreConstants.KEY_LOCAL_MNC;
import static com.elitecorelib.core.CoreConstants.KEY_LOCAL_SIM_OPERATOR;
import static com.elitecorelib.core.CoreConstants.KEY_MCC;
import static com.elitecorelib.core.CoreConstants.KEY_MNC;
import static com.elitecorelib.core.CoreConstants.KEY_OFFLOADSSID;
import static com.elitecorelib.core.CoreConstants.KEY_OFFLOADSSID_LOCAL;
import static com.elitecorelib.core.CoreConstants.Seprator;
import static com.elitecorelib.core.CoreConstants.TYPE_OFFLOAD_WIFI;

/**
 * Created by Chirag Parmar on 10-Apr-17.
 */

public class AutoConnectWiFiService extends IntentService implements OnWiFiTaskCompleteListner {

    private final String MODULE = "AutoConnectWiFiService";
    private final SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();
    private final IWiFiConfiguration api = new EliteWiFiAPI(this);
    private LocationServiceCheck locationServiceCheck;
    private PojoConnection activateConnection;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param
     */
    public AutoConnectWiFiService() {
        super("");
        locationServiceCheck = new LocationServiceCheck(this);
        activateConnection = ElitelibUtility.setUP_BSNLWIFI(TYPE_OFFLOAD_WIFI);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        bsnlMCCMNCvalidate();
    }

    // check Bsnl simcard user or not and validate BSNL MCC & MNC
    private void bsnlMCCMNCvalidate() {
        if (ElitelibUtility.getKeyPairvalue(KEY_CHECK_SIM_OPERATOR, spTask.getString(KEY_LOCAL_SIM_OPERATOR)).equals("YES")) {
            boolean validOperator = CommonWiFiUtility.checkValidOperator(ElitelibUtility.getKeyPairvalue(KEY_MCC, spTask.getString(KEY_LOCAL_MCC)),
                    ElitelibUtility.getKeyPairvalue(KEY_MNC, spTask.getString(KEY_LOCAL_MNC)), Seprator);
            if (validOperator) {
                if (ElitelibUtility.isAlreadyConnected(ElitelibUtility.getKeyPairvalue(KEY_OFFLOADSSID, spTask.getString(KEY_OFFLOADSSID_LOCAL)))) {
                    EliteSession.eLog.d(MODULE, "SSID Allready Connected.");
                } else {
                    if (locationServiceCheck.isAboveMarshMellow()) {
                        if (locationServiceCheck.checkLocationEnable()) {
                            checkWiFiStatus();
                        } else {
                            EliteSession.eLog.d(MODULE, "Location Service Disable. NOt To Search WiFi");
                        }
                    } else {
                        checkWiFiStatus();
                    }
                }
            } else {
                EliteSession.eLog.d(MODULE, "MCC MNC Not validate.");
            }

        } else {
            if (ElitelibUtility.isAlreadyConnected(ElitelibUtility.getKeyPairvalue(KEY_OFFLOADSSID, spTask.getString(KEY_OFFLOADSSID_LOCAL)))) {
                EliteSession.eLog.d(MODULE, "SSID Allready Connected.");
            } else {
                // check permission for above marshmallow os devices
                if (locationServiceCheck.isAboveMarshMellow()) {
                    if (locationServiceCheck.checkLocationEnable()) {
                        checkWiFiStatus();
                    } else {
                        EliteSession.eLog.d(MODULE, "Location Service Disable. NOt To Search WiFi");
                    }
                } else {
                    checkWiFiStatus();
                }
            }
        }
    }

    // check wifi SSID in range or not in user redius
    private void checkWiFiStatus() {
        EliteSession.eLog.d(MODULE, " Checking WiFi Status");
        IWiFiConfiguration api = new EliteWiFiAPI(this);
        try {
            if (activateConnection != null)
                api.isWiFiInRange(this, activateConnection.getSsid());
            else {
                api.isWiFiInRange(this, ElitelibUtility.getKeyPairvalue(KEY_OFFLOADSSID, spTask.getString(KEY_OFFLOADSSID_LOCAL)));
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, " Error while checking wifi is in range " + e.getMessage());
        }
    }

    @Override
    public void onWiFiTaskComplete(String result) {
        EliteSession.eLog.i(MODULE, " WiFI complete task status is " + result);
        try {
            if (result != null && (result.equals(WiFiConstants.CONNECTED) || result.equals(WiFiConstants.ALREADYCONNECTED))) {
                EliteSession.eLog.d(MODULE, "WiFi Connected Successfully.");
            } else {
                EliteSession.eLog.d(MODULE, "WiFi not Connected Successfully.");
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    @Override
    public void isWiFiInRange(boolean status) {

        EliteSession.eLog.i(MODULE, " WiFI range status is= " + status);
        if (status) {
            try {
                api.connectToWiFi(this, activateConnection, this, true, false);
            } catch (Exception e) {
                EliteSession.eLog.e(MODULE, "Error while cheking wifi range " + e.getMessage());
            }
        } else {
            EliteSession.eLog.d(MODULE, "SSID Not in range.");
        }
    }

    @Override
    public void getResponseData(String responseData) {

    }

    @Override
    public void onWiFiScanComplete(List<String> ssidList) {

    }
}
