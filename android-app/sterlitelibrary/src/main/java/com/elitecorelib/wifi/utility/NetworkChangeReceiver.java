package com.elitecorelib.wifi.utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.wifi.api.WiFiConstants;

public class NetworkChangeReceiver extends BroadcastReceiver {
	private final String MODULE = "NetworkChangeReceiver";
	@Override
	public void onReceive(final Context context, final Intent intent) {

		try {
			int status = NetworkUtil.getConnectivityStatus(context);
			if (status == NetworkUtil.TYPE_NOT_CONNECTED || status == NetworkUtil.TYPE_MOBILE) {
				LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveString(WiFiConstants.CURRENT_STATE, WiFiConstants.WIFI_DISABLED);
			} else if (status == NetworkUtil.TYPE_WIFI) {
				LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveString(WiFiConstants.CURRENT_STATE, WiFiConstants.WIFI_ENABLED);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			EliteSession.eLog.d(MODULE + " " + " Error while getting status " + e.getMessage());
		}
		try {
			//Sending broadcast to client application for state changed
			Intent i = new Intent();
			i.putExtra(WiFiConstants.CURRENT_STATE, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(WiFiConstants.CURRENT_STATE));
			i.setAction(LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(WiFiConstants.STATE_CHANGE_RECEIVER_ACTION));
			context.sendBroadcast(i);
		} catch (Exception e) {
			// TODO: handle exception
			EliteSession.eLog.d(MODULE + " " + " Error while Sending Status broadcast " + e.getMessage());
		}


		//Toast.makeText(context, status, Toast.LENGTH_LONG).show();
	}
}