package com.elitecore.wifi.listener;

import java.util.List;

public interface OnWiFiTaskCompleteListner {
    // it will call when background process finish
    void onWiFiTaskComplete(String result);

    void isWiFiInRange(boolean status);

    void getResponseData(String responseData);

    void onWiFiScanComplete(List<String> ssidList);
}