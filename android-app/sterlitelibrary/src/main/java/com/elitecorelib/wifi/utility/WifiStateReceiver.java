package com.elitecorelib.wifi.utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.utility.SharedPreferenceConstants;
import com.elitecorelib.wifi.api.WiFiConstants;

public class WifiStateReceiver extends BroadcastReceiver {


	private final String MODULE = "WifiStateReceiver";
	@Override
	public void onReceive(Context context, Intent intent) {
		try{

			if (intent.getAction() == WifiManager.WIFI_STATE_CHANGED_ACTION) {

				int previous_state = intent.getIntExtra(WifiManager.EXTRA_PREVIOUS_WIFI_STATE, 0);

				if (previous_state == WifiManager.WIFI_STATE_DISABLED) {
					
					EliteSession.eLog.d(MODULE +" "+ " Wifi Enabled");
					LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveString(WiFiConstants.CURRENT_STATE, WiFiConstants.WIFI_ENABLED);
				} else if (previous_state == WifiManager.WIFI_STATE_ENABLED) {
					EliteSession.eLog.d(MODULE +" "+ " Wifi Disabled");
					LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveString(WiFiConstants.CURRENT_STATE, WiFiConstants.WIFI_DISABLED);
					try {
						if(LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getBoolean(SharedPreferenceConstants.DELETE_AFTER_WIFI_OFF) && WifiUtility.isDeleted!=true)
						{
							WifiUtility.scheduleWiFiRemoveTask(LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(SharedPreferenceConstants.SSIDNAME));

						}	
					} catch (Exception e) {
						// TODO: handle exception
						EliteSession.eLog.d(MODULE +" "+ " Error to schedule wifi delete timer "+e.getMessage());
					}
				}
				try {
					//Sending broadcast to client application for state changed
					Intent i= new Intent();
					i.putExtra(WiFiConstants.CURRENT_STATE, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(WiFiConstants.CURRENT_STATE));
					i.setAction(LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(WiFiConstants.STATE_CHANGE_RECEIVER_ACTION));
					context.sendBroadcast(i);
				} catch (Exception e) {
					// TODO: handle exception
					EliteSession.eLog.d(MODULE +" "+ " Error while Sending Status broadcast "+e.getMessage());
				}
			}
		}catch(Exception e){
			EliteSession.eLog.e(MODULE+" "+ e);
			EliteSession.eLog.e(MODULE+" "+ e.getMessage());
		}
	}
}
