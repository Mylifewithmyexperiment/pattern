package com.elitecorelib.andsf.pojo;

import android.content.Context;

public class ANDSFConfig {

    private Context context;
    private String monetizationURL;
    private String ANDSFURL;
    private int pullTimeInterval;
    private String applicationName;
    private int logo;
    private int notificationLogo;
    private String sharedKey;
    private boolean isPolicyCall;
    private String notificationKEY;


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getMonetizationURL() {
        return monetizationURL;
    }

    public void setMonetizationURL(String monetizationURL) {
        this.monetizationURL = monetizationURL;
    }

    public String getANDSFURL() {
        return ANDSFURL;
    }

    public void setANDSFURL(String ANDSFURL) {
        this.ANDSFURL = ANDSFURL;
    }

    public int getPullTimeInterval() {
        return pullTimeInterval;
    }

    public void setPullTimeInterval(int pullTimeInterval) {
        this.pullTimeInterval = pullTimeInterval;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public int getNotificationLogo() {
        return notificationLogo;
    }

    public void setNotificationLogo(int notificationLogo) {
        this.notificationLogo = notificationLogo;
    }

    public boolean isPolicyCall() {
        return isPolicyCall;
    }

    public void setPolicyCall(boolean policyCall) {
        isPolicyCall = policyCall;
    }

    public String getSharedKey() {
        return sharedKey;
    }

    public void setSharedKey(String sharedKey) {
        this.sharedKey = sharedKey;
    }

    public String getNotificationKEY() {
        return notificationKEY;
    }

    public void setNotificationKEY(String notificationKEY) {
        this.notificationKEY = notificationKEY;
    }
}