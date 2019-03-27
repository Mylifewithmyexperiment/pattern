package com.elitecore.wifi.api;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.elitecore.wifi.pojo.PojoConnection;
import com.elitecorelib.core.EliteSession;


final class WPAWiFiSettings implements IEliteWiFiconfigurator{

	private String MODULE="WPAWiFiSettings";
	private int wifiNetworkId;
	
	@Override
	public int createWiFiSettings(Context context, PojoConnection connection) {
		wifiNetworkId = -1;
		try {
			EliteSession.eLog.d(MODULE+" Creating New wifi Configuration");
			WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
			EliteSession.eLog.d(MODULE+ " createWiFiSettings Turning on WiFi if disabled.");
			CommonWiFiUtility.turnOnWiFi(wifiManager);
			WifiConfiguration wifiConfiguration = new WifiConfiguration();
			wifiConfiguration.SSID = "\"" + connection.getSsid() + "\"";
			wifiConfiguration.status = WifiConfiguration.Status.ENABLED;
			wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
			wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			wifiConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
			wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
			wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
			wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
			wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			wifiConfiguration.preSharedKey = "\"" + connection.getPassword() + "\"";

			EliteSession.eLog.d(MODULE+" Created wifi Configuration "+wifiConfiguration.toString());
			wifiNetworkId = wifiManager.addNetwork(wifiConfiguration);
			wifiManager.saveConfiguration();
		} catch (Exception e) {
			EliteSession.eLog.e(MODULE+" "+ e.getMessage());

		}
		return wifiNetworkId;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "WPAWiFiSettings Factory Created";
	}
}
