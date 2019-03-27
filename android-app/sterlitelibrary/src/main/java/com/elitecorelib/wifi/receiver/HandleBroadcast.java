package com.elitecorelib.wifi.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;

public class HandleBroadcast {

	private static Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
//	private static BootReceiver bootReceiver;
	private static BatteryReceiver betteryReceiver;
//	private static WifiScanReceiver wifiScanReceiver;
	private static String MODULE = "HandleBroadcast";
	public static void registerReceivers() {

		EliteSession.eLog.e(MODULE, "HandleBroadcast invoked");
//		bootReceiver = new BootReceiver();
		betteryReceiver = new BatteryReceiver();
//		wifiScanReceiver = new WifiScanReceiver();

//		context.registerReceiver(bootReceiver, new IntentFilter(
//				Intent.ACTION_BOOT_COMPLETED));
		context.registerReceiver(betteryReceiver, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));
//		context.registerReceiver(wifiScanReceiver, new IntentFilter(
//				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
	}

	public static void deregisterReceivers() {
//		context.unregisterReceiver(wifiScanReceiver);
//		context.unregisterReceiver(bootReceiver);
		try{
		context.unregisterReceiver(betteryReceiver);
		}catch(Exception e){
			EliteSession.eLog.e(MODULE, e.getMessage());
		}
	}
}
