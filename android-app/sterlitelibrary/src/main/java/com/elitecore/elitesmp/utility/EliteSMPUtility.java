package com.elitecore.elitesmp.utility;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.elitecore.elitesmp.api.EliteSMPConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.utility.ElitelibUtility;

public class EliteSMPUtility {

	List<String> loginExlureParam = new ArrayList<String>();
	private static final String MODULE = "EliteSMPUtility";
	public static List<String> getExcludeParam(int reqId){
		List<String> list = new ArrayList<String>() ;
		if(EliteSMPConstants.ELITESMP_REQUEST_DOLOGIN == reqId){
			list.add(EliteSMPUtilConstants.OPERATION);
			list.add(EliteSMPUtilConstants.SUBENCRYPTIONTYPE);
			list.add(EliteSMPUtilConstants.RESPONSETIME);
			list.add(EliteSMPUtilConstants.PASSWORD);
			list.add(EliteSMPUtilConstants.SUBSESSIONCOUNT);
			list.add(EliteSMPUtilConstants.SUBPASSWORD);
			list.add(EliteSMPUtilConstants.SUBLOGINSERVICEPOLICY);
			list.add(EliteSMPUtilConstants.ACCESSDEVICEIP);
			list.add(EliteSMPUtilConstants.SUBBALANCE);
			list.add(EliteSMPUtilConstants.SUBSUBSCRIBERID);
			list.add(EliteSMPUtilConstants.SUBSOURCEPORT);
		}else if(EliteSMPConstants.ELITESMP_REQUEST_DOLOGOUT == reqId){
			list.add(EliteSMPUtilConstants.OPERATION);
			list.add(EliteSMPUtilConstants.SUBVOLUMEBASEDUSEDQUOTA);
			list.add(EliteSMPUtilConstants.SUBVOLUMEBASEDTOTALQUOTA);
			list.add(EliteSMPUtilConstants.SUBENCRYPTIONTYPE);
			list.add(EliteSMPUtilConstants.SUBTIMEBASEDUNUSEDQUOTA);
			list.add(EliteSMPUtilConstants.RESPONSETIME);
			list.add(EliteSMPUtilConstants.SUBFAILUREATTEMPT);
			list.add(EliteSMPUtilConstants.SUBSESSIONCOUNT);
			list.add(EliteSMPUtilConstants.SUBUSERNAME);
			list.add(EliteSMPUtilConstants.ACCESSDEVICEIP);
			list.add(EliteSMPUtilConstants.SUBBALANCE);
			list.add(EliteSMPUtilConstants.SUBPASSWORDVALIDITY);
			list.add(EliteSMPUtilConstants.SUBSUBSCRIBERID);
			list.add(EliteSMPUtilConstants.SUBVOLUMEBASEDUNUSEDQUOTA);
			list.add(EliteSMPUtilConstants.SUBTIMEBASEDTOTALQUOTA);
			list.add(EliteSMPUtilConstants.SUBTIMEBASEDUSEDQUOTA);
			list.add(EliteSMPUtilConstants.SUBSOURCEPORT);
		} else if(EliteSMPConstants.ELITESMP_REQUEST_REGISTERACCOUNT == reqId){
			list.add(EliteSMPUtilConstants.OPERATION);
			list.add(EliteSMPUtilConstants.SUBENCRYPTIONTYPE);
			list.add(EliteSMPUtilConstants.SUBTIMEBASEDUNUSEDQUOTA);
			list.add(EliteSMPUtilConstants.VOUCHERCODE);
			list.add(EliteSMPUtilConstants.SUBFAILUREATTEMPT);
			list.add(EliteSMPUtilConstants.SUBUSERNAME);
			list.add(EliteSMPUtilConstants.OPERATIONMODE);
			list.add(EliteSMPUtilConstants.SUBBALANCE);
			list.add(EliteSMPUtilConstants.VOUCHERPIN);
			list.add(EliteSMPUtilConstants.SUBSUBSCRIBERID);
			list.add(EliteSMPUtilConstants.SUBVOLUMEBASEDUNUSEDQUOTA);
			list.add(EliteSMPUtilConstants.SUBSOURCEPORT);
			list.add(EliteSMPUtilConstants.SUBVOLUMEBASEDUSEDQUOTA);
			list.add(EliteSMPUtilConstants.RESPONSETIME);
			list.add(EliteSMPUtilConstants.SUBSESSIONCOUNT);
			list.add(EliteSMPUtilConstants.SUBVOUCHERCODE);
			list.add(EliteSMPUtilConstants.SUBPASSWORDVALIDITY);
			list.add(EliteSMPUtilConstants.SUBTIMEBASEDUSEDQUOTA);
			list.add(EliteSMPUtilConstants.SUBSUBSCRIBERIDENTITY);
		} else if(EliteSMPConstants.ELITESMP_REQUEST_GETPACKAGE == reqId){
			list.add(EliteSMPUtilConstants.OPERATION);
			list.add(EliteSMPUtilConstants.SUBENCRYPTIONTYPE);
			list.add(EliteSMPUtilConstants.SUBTIMEBASEDUNUSEDQUOTA);
			list.add(EliteSMPUtilConstants.VOUCHERCODE);
			list.add(EliteSMPUtilConstants.SUBFAILUREATTEMPT);
			list.add(EliteSMPUtilConstants.SUBUSERNAME);
			list.add(EliteSMPUtilConstants.OPERATIONMODE);
			list.add(EliteSMPUtilConstants.SUBBALANCE);
			list.add(EliteSMPUtilConstants.VOUCHERPIN);
			list.add(EliteSMPUtilConstants.SUBSUBSCRIBERID);
			list.add(EliteSMPUtilConstants.SUBVOLUMEBASEDUNUSEDQUOTA);
			list.add(EliteSMPUtilConstants.SUBSOURCEPORT);
			list.add(EliteSMPUtilConstants.SUBVOLUMEBASEDUSEDQUOTA);
			list.add(EliteSMPUtilConstants.RESPONSETIME);
			list.add(EliteSMPUtilConstants.SUBSESSIONCOUNT);
			list.add(EliteSMPUtilConstants.SUBVOUCHERCODE);
			list.add(EliteSMPUtilConstants.SUBPASSWORDVALIDITY);
			list.add(EliteSMPUtilConstants.SUBTIMEBASEDUSEDQUOTA);
			list.add(EliteSMPUtilConstants.SUBSUBSCRIBERIDENTITY);
		} 
		return list;
	}	
	public static String filterResponse(String response,int reqId){
		try {
			if(response==null)
				return null;

			Object json = new JSONTokener(response).nextValue();
			JSONObject jsonObject = null;
			if (json instanceof JSONObject) {
				jsonObject = new JSONObject(response);
			} else if (json instanceof JSONArray) {
				return response;
			} else {

			}

			EliteSession.eLog.d(MODULE,"filterResponse excluding params");
			List<String> exlureParam = EliteSMPUtility.getExcludeParam(reqId);
			EliteSession.eLog.d(MODULE,"Excluded params found");
//			for (String string : exlureParam) {
//				if(jsonObject.has(string)){
//					jsonObject.remove(string);
//				}
//			}
			EliteSession.eLog.d(MODULE,"Excluded Paramters removed from the app");

			if(jsonObject == null)
				return response;
			else
				return jsonObject.toString();
		} catch (JSONException e) {
			EliteSession.eLog.e(MODULE,e.getMessage());
			e.printStackTrace();
			return null;
		}
		catch (Exception e)
		{
			EliteSession.eLog.e(MODULE,e.getMessage());
			return  null;
		}
		
	}	
	public static String getIPAddress(Context context)
	{
		String ip="";
		EliteSession.eLog.d(MODULE,"Checking IP address");
		try
		{
			WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInf = wifiMan.getConnectionInfo();
			int ipAddress = wifiInf.getIpAddress();
			ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff),(ipAddress >> 8 & 0xff),(ipAddress >> 16 & 0xff),(ipAddress >> 24 & 0xff));
			EliteSession.eLog.d(MODULE,"IP value is "+ip);

		}
		catch (Exception e)
		{
			EliteSession.eLog.e(MODULE,e.getMessage());
		}

		if(ElitelibUtility.isProbablyArabic(ip)){
			EliteSession.eLog.d(MODULE,"IP value is arabic language.");
			ip = ElitelibUtility.arabicToenglish(ip);
		}

		return ip;
	}
}
