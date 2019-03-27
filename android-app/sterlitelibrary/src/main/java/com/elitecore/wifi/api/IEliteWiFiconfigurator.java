package com.elitecore.wifi.api;

import android.content.Context;

import com.elitecore.wifi.pojo.PojoConnection;



interface IEliteWiFiconfigurator {
	
	public   int createWiFiSettings(Context context, PojoConnection connection);
}
