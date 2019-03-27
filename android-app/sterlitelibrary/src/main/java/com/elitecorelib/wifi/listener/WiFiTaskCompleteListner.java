package com.elitecorelib.wifi.listener;

public abstract interface WiFiTaskCompleteListner {
    // it will call when background process finish
    public abstract void onWiFiTaskComplete(String result);
}