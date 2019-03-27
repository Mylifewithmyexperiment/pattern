package com.elitecorelib.andsf.api;

import com.elitecorelib.andsf.pojo.ANDSFCircular;
import com.elitecorelib.andsf.pojo.ANDSFLocation3GPP;
import com.elitecorelib.andsf.pojo.ANDSFWLANLocation;
import com.elitecorelib.andsf.pojo.ANDSFwiMAXLocation;

import java.util.Date;


/**
 * This class will contain Information about current device status.
 * It will hold mainly below parameters for devices.
 *
 *
 */
public class DeviceDetail {

	private ANDSFCircular ueGeo_Location;
	private ANDSFLocation3GPP ue3GPPLocation;
//	private Location3GPP2 ue3GPP2Location;
	private ANDSFwiMAXLocation ueWiMaxocation;
	private ANDSFWLANLocation ueWlanLocation;
	private Date lastupdated;
	private String IMSI;
	private String IMEI;
	private String MSISDN;
	private boolean isLTEDATANetwork;
	private static DeviceDetail deviceLocation ;
	public static void setInstance(ANDSFCircular ueGeo_Location,
							ANDSFLocation3GPP ue3gppLocation, //Location3GPP2 ue3gpp2Location,
							ANDSFwiMAXLocation ueWiMaxocation, ANDSFWLANLocation ueWlanLocation,
                            Date lastupdated, String IMSI, String IMEI, String MSISDN, boolean isLTEDATANetwork){
		deviceLocation = new DeviceDetail(ueGeo_Location, ue3gppLocation, ueWiMaxocation, ueWlanLocation, lastupdated,IMSI,IMEI,MSISDN, isLTEDATANetwork);
	}
	public DeviceDetail(ANDSFCircular ueGeo_Location,
						ANDSFLocation3GPP ue3gppLocation, //Location3GPP2 ue3gpp2Location,
						ANDSFwiMAXLocation ueWiMaxocation, ANDSFWLANLocation ueWlanLocation,
                        Date lastupdated, String IMSI, String IMEI, String MSISDN, boolean isLTEDATANetwork) {
		super();
		this.ueGeo_Location = ueGeo_Location;
		ue3GPPLocation = ue3gppLocation;
//		ue3GPP2Location = ue3gpp2Location;
		this.ueWiMaxocation = ueWiMaxocation;
		this.ueWlanLocation = ueWlanLocation;
		this.lastupdated = lastupdated;
		this.IMEI = IMEI;
		this.IMSI = IMSI;
		this.MSISDN = MSISDN;
		this.isLTEDATANetwork = isLTEDATANetwork;
		
	}
	
	
	public static DeviceDetail getInstance(){
		return deviceLocation;
	}

	public ANDSFCircular getUeGeo_Location() {
		return ueGeo_Location;
	}

	public ANDSFLocation3GPP getUe3GPPLocation() {
		return ue3GPPLocation;
	}

//	public Location3GPP2 getUe3GPP2Location() {
//		return ue3GPP2Location;
//	}

	public ANDSFwiMAXLocation getUeWiMaxocation() {
		return ueWiMaxocation;
	}

	public ANDSFWLANLocation getUeWlanLocation() {
		return ueWlanLocation;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public String getIMSI() {
		return IMSI;
	}

	public String getIMEI() {
		return IMEI;
	}

	public String getMSISDN() {
		return MSISDN;
	}
	public boolean getIsLTE_DataNetwork() {
		return isLTEDATANetwork;
	}
}
