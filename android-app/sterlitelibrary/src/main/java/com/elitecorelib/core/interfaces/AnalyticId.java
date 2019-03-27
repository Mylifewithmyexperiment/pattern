package com.elitecorelib.core.interfaces;

/**
 * Created by Chirag Parmar on 02-Sep-16.
 * <p>
 * This class contain analytic id whose pass in @eventId in server.
 */
public interface AnalyticId {

    int OPENAPP = 1;
    int MAKEWIFIADAPTERON = 2;
    int WIFICONNECTION = 3;
    int MONETIZATIONSERVERNOTREACHBLE = 4;
    int OPENDEALCATEGORIES = 5;
    int OPENDEALSPAGE = 6;
    int VIEWOFFER = 7;
    int PURCHASEDEAL = 8;
    int REDEEMVOUCHER = 9;
    int SWITCHOFFNOTIFICATION = 10;
    int OPENNEARBYHOTSPOT = 11;
    int CLICKONHOTSPOT = 12;
    int OPENPREFERENCEPAGE = 13;
    int SYNCHOTSPOT = 14;
    int OPENLANGUAGEPAGE = 15;
    int CHANGELANGUAGE = 16;
    int LOGIN = 17;
    int LOGOUT = 18;
    int OPENPROFILEDOWNLOADPAGE = 19;
    int OPENPROFILEHISTORY = 20;
    int VOUCHERLOGIN = 21;
    int OTPVALIDATIONREQUEST = 22;
    int OPENWIFIPACKAGE = 23;
    int OPENFAQPAGE = 24;
    int OPENCONTACTUSPAGE = 25;
    int OPENPARTNERPAGE = 26;


    int WiFi_Status = 102;
    int WiFi_Range = 103;
    int WiFi_Connection = 104;

    String WiFi_Status_value = "WiFi_Status";
    String WiFi_Range_value = "WiFi_Range";
    String WiFi_Connection_value = "WiFi_Connection";


    // BSNL MDO Application Analytic Constant

    int SIM_STATUS = 201;
    String SIM_STATUS_DES = "To check the BSNL SIM card";
    int OPEN_MDO_LOGIN_SUCCESS_SCREEN = 203;
    int SWITCH_NETWORK_STATUS = 204;
    int DISCONNECT_STATUS = 205;

    // BSNL WiFi Application Analytic Constant

    int OPEN_BROADBAND_LOGIN_SCREEN = 201;
    int BROADBAND_USER_AUTH_STATUS = 202;
    int OPEN_CAPTIVE_BRODBAND = 203;
    int OPEN_BROADBAND_LOGOUT_SCREEN = 204;
    int BROADBAND_LOGOUT_STATUS = 205;
    int OPEN_WIFI_LOGIN_SCREEN = 206;
    int WIFI_LOGIN_STATUS = 207;
    int OPEN_WIFI_LOGOUT_SCREEN = 208;
    int WIFI_LOGOUT_STATUS = 209;
    int OPEN_BUYPLAN_SCREEN = 210;
    int SUBSCRIBE_PLAN_STATUS = 211;

    // General Analytic Constant

    int OPEN_APP = 101;
    int OPEN_HOTSPOT_FINDER_LIST_SCREEN = 105;
    int OPEN_HOTSPOT_FINDER_MAPVIEW_SCREEN = 106;
    int OPEN_HOTSPOT_DETAIL_POPUP = 107;
    int OPEN_HOTSPOT_SEARCH_SCREEN = 108;
    int OPEN_PREFERENCES_SCREEN = 109;
    int UPDATE_HOTSPOT_STATUS = 110;
    int UPDATE_CONFIGURATION_STATUS = 111;
    int OPEN_ABOUT_SCREEN = 112;
    int OPEN_TERMS_SCREEN = 113;
    int OPEN_REPORT_PROBLEM_SCREEN = 114;

    String SUCCESS = "Success";
    String FAIL = "Fail";
    String SWITCHNETWORK = "SwitchNetwork";
    String DISCONNECT = "Disconnect";
    String FREE = "Free";
    String PREMIUM = "Premium";
    String COUPON = "Coupon";
    String ONLINE = "Online";
}
