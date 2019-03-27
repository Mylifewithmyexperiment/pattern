package com.elitecore.wifi.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.utility.SharedPreferenceConstants;
import com.elitecorelib.wifi.api.WiFiConstants;

public class WiFiStatusReciver extends BroadcastReceiver {
	private final String MODULE = "WiFiStatusReciver";
	@Override
	public void onReceive(Context context, Intent intent) {
		try{
			EliteSession.setELiteConnectSession(context);
			//Log.d(MODULE,"*************************************");
			EliteSession.eLog.d(MODULE,"WiFi action");
			if (intent.getAction() == WifiManager.WIFI_STATE_CHANGED_ACTION) {


				int previous_state = intent.getIntExtra(WifiManager.EXTRA_PREVIOUS_WIFI_STATE, 0);

				if (previous_state == WifiManager.WIFI_STATE_DISABLED) {
					
					EliteSession.eLog.d(MODULE +" "+ " Wifi Enabled");
					LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveString(WiFiConstants.CURRENT_STATE, WiFiConstants.WIFI_ENABLED);
				} else if (previous_state == WifiManager.WIFI_STATE_ENABLED) {
					EliteSession.eLog.d(MODULE +" "+ " Wifi Disabled");
					LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveString(WiFiConstants.CURRENT_STATE, WiFiConstants.WIFI_DISABLED);
					try {
						if(!LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getBoolean(SharedPreferenceConstants.ISWIFISETTINGSDELETED) && LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getBoolean(SharedPreferenceConstants.DELETE_AFTER_WIFI_OFF))
						{
							try {
								WifiManager wifiManager=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
								int removeTimerInerval=30;
								if(LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getInt(SharedPreferenceConstants.DELETE_TIME_INTERVAL)>0)
								{
									EliteSession.eLog.d(MODULE,"Timer interval changed from default 30 min");
									removeTimerInerval=LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getInt(SharedPreferenceConstants.DELETE_TIME_INTERVAL);
								}
								CommonWiFiUtility.scheduleWiFiRemoveTask(wifiManager,LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(SharedPreferenceConstants.SSIDNAME),removeTimerInerval);

							} catch (Exception e) {
								EliteSession.eLog.d(MODULE, " Error while scheduling wifi revmoe task : "+e.getMessage());
							}
							
						}
					} catch (Exception e) {
						// TODO: handle exception
						EliteSession.eLog.d(MODULE +" "+ " Error to schedule wifi delete timer "+e.getMessage());
					}
					
				
				}
			}
		}catch(Exception e){
			//if(EliteSession.eLog!=null) {
				EliteSession.eLog.e(MODULE + " " + e);
				EliteSession.eLog.e(MODULE + " " + e.getMessage());
			//}
		}
	}
}
