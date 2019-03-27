package com.elitecore.wifi.listener;

/**
 * Created by viratsinh.parmar on 6/14/2016.
 */
public interface OnInternetCheckCompleteListner {

    void isInterNetAvailable(int requestId, String status, String json);
}
