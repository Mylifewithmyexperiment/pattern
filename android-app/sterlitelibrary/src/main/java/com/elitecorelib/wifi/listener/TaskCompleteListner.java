package com.elitecorelib.wifi.listener;

import java.util.List;

import android.net.wifi.ScanResult;

public abstract interface TaskCompleteListner {
    // it will call when background process finish
    public abstract void onTaskComplete(String result);
    public abstract void onConnectionComplete(String result);
    public abstract void onWiFiScanComplete(List<String> scanResult);
    public abstract void onWiFiDetailScanComplete(List<ScanResult> scanResult);
}