package com.elitecorelib.core.services;

public interface ConnectionManagerCompleteListner {
    // it will call when background process finish
    public void onConnnectionManagerTaskComplete(String result, int requestId);
}