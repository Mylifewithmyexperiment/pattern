package com.elitecore.wifi.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.elitecore.wifi.listener.OnWiFiTaskCompleteListner;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by viratsinh.parmar on 12/18/2015.
 */
public class WiFiReciever extends BroadcastReceiver {
    final Context context;
    final WifiManager wifiManager;
    private String MODULE = "WIFIRECEIVER";
    private boolean isRegistered;
    private OnWiFiTaskCompleteListner listner;

    WiFiReciever(OnWiFiTaskCompleteListner listner) {
        this.listner = listner;
        context = LibraryApplication.getLibraryApplication().getLibraryContext();
        wifiManager= (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        scanWiFiInRangeAndProcess();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            EliteSession.eLog.d(MODULE, " On receive method invoked in wifi receiver.");
            final List<String> wifiList = new ArrayList<String>();
            if (!isRegistered) {
                EliteSession.eLog.d(MODULE, " Reciever not registered yet");
                return;
            }
            EliteSession.eLog.d(MODULE, " inside onRecceive");
            wifiList.clear();
            // TODO Auto-generated method stub
            EliteSession.eLog.d(MODULE, " getting scan result");
            List<ScanResult> scanResults = wifiManager.getScanResults();

            if (scanResults != null && scanResults.size() > 0) {
                EliteSession.eLog.d(MODULE, " scan result not null");
                for (int i = 0; i < scanResults.size(); i++) {
                    ScanResult scanResult = scanResults.get(i);
                    wifiList.add(scanResult.SSID);
                }
                try {
                    EliteSession.eLog.d(MODULE, " converting to JSON response");
                    String json = new Gson().toJson(wifiList);
                    JSONArray mJSONArray = new JSONArray(json);
                    EliteSession.eLog.d(MODULE, " List found, " + mJSONArray.toString());
                } catch (JSONException e) {
                    EliteSession.eLog.e(MODULE, e.getMessage());
                    listner.onWiFiScanComplete(null);
                } catch (Exception e) {
                    EliteSession.eLog.e(MODULE, e.getMessage());
                    listner.onWiFiScanComplete(null);
                }
            } else {
                EliteSession.eLog.d(MODULE, "Scan result null");
            }
            EliteSession.eLog.d(MODULE, " Un Register Recevier");
            isRegistered = false;
            try {
                context.unregisterReceiver(this);
                EliteSession.eLog.d(MODULE, " Sending call back to onWiFiScanComplete");
                listner.onWiFiScanComplete(wifiList);
            } catch (Exception e) {
                EliteSession.eLog.e(MODULE, e.getMessage());
                listner.onWiFiScanComplete(null);
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, " OnRecevier, receiver exception");
            EliteSession.eLog.e(MODULE, e.getMessage());
            listner.onWiFiScanComplete(null);
        }
    }
    private void scanWiFiInRangeAndProcess() {
        try {
            EliteSession.eLog.d(MODULE, " scanWiFiInRangeAndProcess invoked from ");
            EliteSession.eLog.d(MODULE, " scanWiFiInRangeAndProcess Check wifi state");
             if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
                while (wifiManager.isWifiEnabled()) {
                    if (wifiManager.isWifiEnabled())
                        break;
                }
            }
            EliteSession.eLog.d(MODULE, " scanWiFiInRangeAndProcess start scan");
            wifiManager.startScan();
            EliteSession.eLog.d(MODULE, " scanWiFiInRangeAndProcess done");
            EliteSession.eLog.d(MODULE, " scanWiFiInRangeAndProcess set isRegister true");
            isRegistered = true;
            EliteSession.eLog.d(MODULE, " scanWiFiInRangeAndProcess Register reciver");
            IntentFilter iFilter=new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
            EliteSession.eLog.d(MODULE, " setting priority");
            iFilter.setPriority(999);
            EliteSession.eLog.d(MODULE, " Registering receiver");
            context.registerReceiver(this, iFilter);
            //context.getApplicationContext().registerReceiver(this, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        }catch (IllegalArgumentException ae){
            EliteSession.eLog.e(MODULE,"IllegalArgumentException"+ ae.getMessage());
            listner.onWiFiScanComplete(null);
        }
        catch (Exception e) {
            EliteSession.eLog.e(MODULE,"Exception"+ e.getMessage());
            listner.onWiFiScanComplete(null);
        }
    }
}
