package com.elitecorelib.wifi.api;


public interface WiFiConstants {


	//Connection status
	public static final String CONNECTED="CONNECTED";
	public static final String NOTCONNECTED="NOTCONNECTED";
	public static final String NOTCONNECTED_IMSINULL="NOTCONNECTED_IMSINULL";
	public static final String ALREADYCONNECTED="ALREADYCONNECTED";
	public static final String NOTVALIDOPERATOR="NOTVALIDOPERATOR";

	public static final String IMSI="IMSI";
	public static final String IMEI="IMEI";
	public static final String NETWORKID = "NETWORKID";
	public static final String WIFI_REALM="@eliteconnect.com";

	//WiFi connection method and type

	public static final String SEUCURITY_TYPE_OPEN="OPEN";
	public static final String SEUCURITY_TYPE_EAP="EAP";
	public static final String SEUCURITY_TYPE_WEP="WEP";
	public static final String SEUCURITY_TYPE_WPA="WPA";

	//WiFi Network Strength
	public static final String SIGNALSTRENGTH_EXCELLENT="Excellent";
	public static final String SIGNALSTRENGTH_GOOD="Good";
	public static final String SIGNALSTRENGTH_FAIR="Fair";
	public static final String SIGNALSTRENGTH_POOR="Poor";
	public static final String SIGNALSTRENGTH_NO_SIGNAL="No signal";
	
	
	
	
	//WiFiState
	public static final String STATE_CHANGE_RECEIVER_ACTION="STATE_CHANGE_RECEIVER_ACTION";
	public static final String CURRENT_STATE="CURRENT_STATE";
	public static final String WIFI_ENABLED="WIFI_ENABLED";
	public static final String WIFI_DISABLED="WIFI_DISABLED";
	




}
