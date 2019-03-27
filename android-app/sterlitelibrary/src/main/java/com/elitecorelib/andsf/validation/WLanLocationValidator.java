package com.elitecorelib.andsf.validation;

import com.elitecorelib.andsf.api.DeviceDetail;
import com.elitecorelib.andsf.pojo.ANDSFPolicies;
import com.elitecorelib.andsf.pojo.ANDSFWLANLocation;
import com.elitecorelib.andsf.utility.CustomConstant;
import com.elitecorelib.core.EliteSession;

public class WLanLocationValidator implements IValidationHandler {

    private final static String MODULE = "WLanLocationValidator";
	private IValidationHandler nextValidator;
	
	@Override
	public void setNextValidator(IValidationHandler nextValidationHandler) {
		this.nextValidator = nextValidationHandler;

	}

	@Override
	public int validate(ANDSFPolicies policy) {
		
		int validationStatus = CustomConstant.NOT_MATCHED;
		DeviceDetail devicelocation = DeviceDetail.getInstance();
		
		
		EliteSession.eLog.d(MODULE,"WLAN Location validation started for Policy "+policy.validityArea.WLAN_Location);
						
		if(policy.validityArea==null || policy.validityArea.WLAN_Location==null || policy.validityArea.WLAN_Location.isEmpty()){
			EliteSession.eLog.d(MODULE,"WLAN Location is empty,so no need to validate WLAN area.");
			validationStatus = CustomConstant.NOT_FOUND_IN_POLICY;
			return validationStatus;
		}

		if(devicelocation == null) {

			EliteSession.eLog.d(MODULE, "Device WLAN Information is empty,so no need to validate WLAN area.");
			validationStatus = CustomConstant.NOT_FOUND_IN_UE;
			return validationStatus;
		}else {
            if (devicelocation.getUeWlanLocation() == null) {
                EliteSession.eLog.d(MODULE, "Device WLAN Information is empty,so no need to validate WLAN area.");
                validationStatus = CustomConstant.NOT_FOUND_IN_UE;
                return validationStatus;
            }

			for (ANDSFWLANLocation wlanLocation : policy.validityArea.WLAN_Location) {

				if (wlanLocation.BSSID != null && !"".equals(wlanLocation.BSSID) && devicelocation.getUeWlanLocation().BSSID.equals(wlanLocation.BSSID)) {
					EliteSession.eLog.d(MODULE, " WLAN Validation BSSID tag is matched" + wlanLocation.BSSID);
					validationStatus = CustomConstant.MATCHED;
					break;
				}
				if (wlanLocation.SSID != null && !"".equals(wlanLocation.SSID) && devicelocation.getUeWlanLocation().SSID.equals(wlanLocation.SSID)) {
					EliteSession.eLog.d(MODULE, " WLAN Validation SSID tag is matched" + wlanLocation.SSID);
					validationStatus = CustomConstant.MATCHED;
					break;
				}

				if (wlanLocation.HESSID != null && !"".equals(wlanLocation.HESSID) && devicelocation.getUeWlanLocation().HESSID.equals(wlanLocation.HESSID)) {
					EliteSession.eLog.d(MODULE, " WLAN Validation HESSID tag is matched" + wlanLocation.HESSID);
					validationStatus = CustomConstant.MATCHED;
					break;
				}
			}
		}
		return validationStatus;
	}

}
