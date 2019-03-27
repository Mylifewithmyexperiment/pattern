package com.elitecore.wifi.api;


public final class EliteWiFIConstants {

	
	
	
	
	/*
	 * Realm For EliteEAPWiFiSettings connection for TTLS  request
	 * 
	 */
	
	public static final String REALM_EAPTTLS_ECONET="#eliteconnect.com";
	public static final String REALM_EAPTTLS="@eliteconnect.com";


	/*
	 * Security types for the Wifi Connections
	 */
	public static final String WIFI_EAPSIM="EAP-SIM";
	public static final String WIFI_EAPTTLS="EAP-TTLS";
	public static final String WIFI_EAPAKA="EAP-AKA";
	public static final String WIFI_EAPPEAP="EAP-PEAP";
	public static final String WIFI_WPA="WPA";
	public static final String WIFI_WPAWPA2="WPA/WPA2";
	public static final String WIFI_WPAWPA2PSK="WPA/WPA2 PSK";
	public static final String WIFI_WPA2PSK="WPA2 PSK";
	public static final String WIFI_OPEN="OPEN";
	public static final String WIFI_AKA="EAPAKA";
	public static final String WIFI_SIM="EAPSIM";

	/*
	 * PHASE2 AUTHETICATION METHODS FOR EAP-TTLS CONNECTION
	 */
	public static final String PHASE2AUTHETICATIONMSCHAPV2="MSCHAPV2";
	public static final  String PHASE2AUTHETICATIONGTC="GTC";
	public static final  String PHASE2AUTHETICATIONMSCHAP="MSCHAP";
	public static final  String PHASE2AUTHETICATIONNONE="NONE";
	public static final  String PHASE2AUTHETICATIONPAP="PAP";


	public static final String RESPONSECODE = "responseCode";
	public static final String RESPONSECODE1 = "ResponseCode";
	public static final String RESPONSEMESSAGE = "responseMessage";
	public static final String RESPONSEMESSAGE1 = "ResponseMessage";
	public static final String REQUESTID = "RequestId";
	public static final String RESPONSEDATA = "responseData";
	public static final String RESPONSEDATA1 = "ResponseData";
	public static final int SUCCESSCODE = 1;
	public static final int FAILURECODE = -1;

	public static final String KEY_SSID_NAME="KEY_SSID_NAME";
	public static final String KEY_IS_NEARBY="KEY_IS_NEARBY";
	public static final String KEY_IS_CONNECTED="KEY_IS_CONNECTED";
	public static final String KEY_HAS_INTERNET="KEY_HAS_INTERNET";
	public static final String KEY_HOTSPOT_NAME="KEY_HOTSPOT_NAME";
	public static final String KEY_PING_URL="KEY_PING_URL";
	public static final String VALUE_SERVER_URL = "VALUE_SERVER_URL";
	public static final String VALUE_OPERATOR_NAME="VALUE_OPERATOR_NAME";
	public static final String KEY_HD_HOTSPOTNAME="KEY_HD_HOTSPOTNAME";
	public static final String KEY_UPLOAD_SPEED = "uploadSpeed";
	public static final String KEY_DOWNLOAD_SPEED="downloadSpeed";
	public static final String VALUE_DOWNLOADSPEED_URL="VALUE_DOWNLOADSPEED_URL";
	public static final String KEY_LOCATION="KEY_LOCATION";
	public static final String KEY_REMAINING_QUOTATIME="KEY_REMAINING_QUOTATIME";
	public static final String KEY_REMAINING_QUOTAVOLUME="KEY_REMAINING_QUOTAVOLUME";
	public static final String VALUE_MONETIZATION_URL = "VALUE_MONETIZATION_URL";

	//General
	public static final int FAILURE_CODE_APPLICATIONIDWRONG=100;
	public static final String FAILURE_MESSAGE_APPLICATIONIDWRONG="Application ID is not Valid";
	//Autodetect
	public static final int FAILURE_CODE_NOWIFI1=101;
	public static final String FAILURE_MESSAGE_NOWIFI1="There is no any operator Wi-Fi in range";


	public static final String SUCCESS_MESSAGE_AUTODETECT="Successfully detected Wi-Fi";

	public static final int FAILURE_CODE_FAILTOGET=102;
	public static final String FAILURE_MESSAGE_FAILTOGET="Fail to get Wi-Fi List";

	public static final int FAILURE_CODE_FAILTODETECT=103;
	public static final String FAILURE_MESSAGE_FAILTODETECT="Fail to auto detect Wi-Fi.";

	//QOS
	public static final int FAILURE_CODE_NOTCONNECTEDWIFI=201;
	public static final String FAILURE_MESSAGE_NOTCONNECTEDWIFI="Wi-Fi is not connected";
	public static final int FAILURE_CODE_FAILTOGETSPEED=202;
	public static final String FAILURE_MESSAGE_FAILTOGETSPEED ="Fail get Wi-Fi upload download speed";

	//Addpersoanl
	public static final int FAILURE_CODE_ALREADYEXIST=301;
	public static final String FAILURE_MESSAGE_ALREADYEXIST="Same Name Wi-Fi information already exist";
	public static final int FAILURE_CODE_FAILTOADD=302;
	public static final String FAILURE_MESSAGE_FAILTOADD="Fail to add PersonalWifi";
	public static final String SUCCESS_MESSAGED_WIFIADDED="Wi-Fi added successfully";

	//updateWifiPriority
	public static final int FAILURE_CODE_UPDATEFAIL=401;
	public static final String FAILURE_MESSAGE_UPDATEFAIL="Fail to update WiFI Priority";

	//Autologin
	public static final int FAILURE_CODE_PARAMMISSING=501;
	public static final String FAILURE_MESSAGE_PARAMMISSING="Request parameter is missing";
	public static final int FAILURE_CODE_OFFLOADMISSING=502;
	public static final String FAILURE_MESSAGE_OFFLOADMISSING="Offload parameter is missing";
	public static final int FAILURE_CODE_PHONEMISSING=503;
	public static final String FAILURE_MESSAGE_PHONEMISSING="Phone parameter is missing or invalid";
	public static final int FAILURE_CODE_OTPMISSING=504;
	public static final String FAILURE_MESSAGE_OTPMISSING="OTP parameter is missing";
	public static final int FAILURE_CODE_PACKAGEMISSING=505;
	public static final String FAILURE_MESSAGE_PACKAGEMISSING="packageId parameter is missing";
	public static final int FAILURE_CODE_CHANNELMISSING=506;
	public static final String FAILURE_MESSAGE_CHANNELMISSING="channel parameter is missing or not valid value";

	public static final int FAILURE_CODE_NOWIFI2=507;
	public static final String FAILURE_MESSAGE_NOWIFI2="No any wifi in range";

	public static final int FAILURE_CODE_NOSUBSCRIBER=508;
	public static final String FAILURE_MESSAGE_NOSUBSCRIBER="Not a operator";
	public static final int FAILURE_CODE_FAILTOCONNECT=509;
	public static final String FAILURE_MESSAGE_FAILTOCONNECT="Fail to connect wifi";


	public static final int FAILURE_CODE_NOOPERATORWIFI=510;
	public static final String FAILURE_MESSAGE_NOOPERATORWIFI="There is no any operator Wi-Fi in range";
	public static final int FAILURE_CODE_NOPERSONALWIFI=511;
	public static final String FAILURE_MESSAGE_NOPERSONALWIFI="There is no any personal Wi-Fi in range";

	public static final int FAILURE_CODE_FAILTOLOGIN=512;
	public static final String FAILURE_MESSAGE_FAILTOLOGIN="fail to login to internet";


	public static final int FAILURE_CODE_REQUESTITMEOUT=513;
	public static final String FAILURE_MESSAGE_REQUESTITMEOUT="Request Time out";

	public static final int FAILURE_CODE_PGLOGIN=901;
	public static final String FAILURE_PGLOGIN_REQUESTITMEOUT="Request Time out";

	public static final int FAILURE_CODE_PGLOGOOUT=1001;
	public static final String FAILURE_PGLOGOOUT_REQUESTITMEOUT="Request Time out";


	//Get Upload Download Speed Error and success messages and constants
	public static final int FAILURE_CODE_SSIDEMPTY=2001;
	public static final String FAILURE_MESSAGE_SSIDEMPTY="SSID name is missing";

	public static final int FAILURE_CODE_SSIDNOTCONNECTED=2002;
	public static final String FAILURE_MESSAGE_SSIDNOTCONNECTED="SSID is not connected";

	public static final int FAILURE_CODE_INTERNETNOTAVAILABLE=2003;
	public static final String FAILURE_MESSAGE_INTERNETNOTAVAILABLE="SSID connected, but Internet is not available";

	public static final int FAILURE_CODE_NETWORKSPEED=2004;
	public static final String FAILURE_MESSAGE_NETWORKSPEED="Fail on getting Download and Upload speed";

	public static final int SUCCESS_CODE_NETWORKSPEED=2005;
	public static final String SUCCESS_MESSAGE_NETWORKSPEED="Success on getting Download and Upload speed";

	public static final int FAILURE_CODE_LATENCYCHECK=2111;
	public static final String FAILURE_MESSAGE_LATENCYCHECK="Fail on getting Latency Check";

	public static final int SUCCESS_CODE_LATENCYCHECK=2112;
	public static final String SUCCESS_MESSAGE_LATENCYCHECK="Success on getting Latency Check";

	public static final String DOWNLOAD_SPEED = "Download_speed";
	public static final String UPLOAD_SPEED = "Upload_speed";

	//Get ALl SSID information API Error and Success message and Constants
	public static final int FAILURE_CODE_ALLSSID=3001;
	public static final String FAILURE_MESSAGE_ALLSSID="Error while getting all ssid";

	public static final int SUCCESS_CODE_ALLSSID=3002;
	public static final String SUCCESS_MESSAGE_ALLSSID="Success on getting SSID information";


	//Stand alone SDK invocation REQUEST ID
	public static final int ADD_PERSONAL_WIFI = 1;
	public static final int AUTO_DETECT = 2;
	public static final int AUTO_LOGIN_FREE_WIFI = 3;
	public static final int CONNECT_TO_WIFI = 4;
	public static final int AUTO_LOGIN_OFFLOAD = 5;
	public static final int DELETEWIFIINFORMATION = 6;
	public static final int UPDATEWIFIPRIORITY = 7;
	public static final int GETQOSFORWIFI = 8;
	public static final int GETMONETIZATIONFORWIFI = 9;


	public static final int PGLOGIN = 9;
	public static final int PGLOGOOUT = 10;

	//	All ssid listing constance key
	public static final String WIFI_BSSID="BSSID";
	public static final String WIFI_SSID="SSID";
	public static final String WIFI_CAPABILITIES="capabilities";
	public static final String WIFI_FREQUENCY="frequency";
	public static final String WIFI_LEVEL="level";
	public static final String WIFI_UNTRUSTED="untrusted";

	//Latency Constant Keys

    public static final String ANDSF_LATENCYVALUE="value";
    public static final String ANDSF_LATENCYPACKETSENT="packetsent";
    public static final String ANDSF_LATENCYPACKETRECIEVE="packetreceive";

}
