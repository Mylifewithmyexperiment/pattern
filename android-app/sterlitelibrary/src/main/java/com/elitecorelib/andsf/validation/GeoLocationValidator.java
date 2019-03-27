package com.elitecorelib.andsf.validation;

import com.elitecorelib.andsf.api.DeviceDetail;
import com.elitecorelib.andsf.pojo.ANDSFCircular;
import com.elitecorelib.andsf.pojo.ANDSFGeoLocation;
import com.elitecorelib.andsf.pojo.ANDSFPolicies;
import com.elitecorelib.andsf.utility.CustomConstant;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.utility.ElitelibUtility;

public class GeoLocationValidator implements IValidationHandler {
	
	private IValidationHandler nextValidator;

	
	public void setNextValidator(IValidationHandler nextValidationHandler) {
		this.nextValidator = nextValidationHandler;
		
	}

	
	public int validate(ANDSFPolicies policy) {
		int validationStatus = CustomConstant.NOT_PROVIDED;
		
		DeviceDetail devicelocation = DeviceDetail.getInstance();
		float devicelatitude = 0;
		float devicelongitude = 0;
		float locationlatitude = 0;
		float locationlongitude = 0;
		float radius = 0 ;
		
		
		
		if(policy.validityArea==null || policy.validityArea.geo_Location_==null || policy.validityArea.geo_Location_.isEmpty()){
			EliteSession.eLog.d(CustomConstant.ApplicationTag,"GEO Locations is empty,so no need to validate GEO Location area.");
			validationStatus = CustomConstant.NOT_FOUND_IN_POLICY;
			return validationStatus;
		}
		
		if(devicelocation.getUeGeo_Location() == null){			
			EliteSession.eLog.d(CustomConstant.ApplicationTag,"Device Geo Location Information is empty,so no need to validate Geo Location.");
			validationStatus = CustomConstant.NOT_FOUND_IN_UE;
			return validationStatus;			
		}
		
		for(ANDSFGeoLocation geoLocation_1: policy.validityArea.geo_Location_){
			
			if(geoLocation_1!=null && !geoLocation_1.circular.isEmpty()){
				
				for(ANDSFCircular wifiLocation : geoLocation_1.circular){
										
					validationStatus = CustomConstant.NOT_MATCHED;
				
					if(wifiLocation.radius!=null && !"".equals(wifiLocation.radius) &&
							wifiLocation.latitude!=null && !"".equals(wifiLocation.latitude) &&
							wifiLocation.longitude!=null && !"".equals(wifiLocation.longitude) &&
							devicelocation.getUeGeo_Location().latitude!=null && !"".equals(devicelocation.getUeGeo_Location().latitude) &&
							devicelocation.getUeGeo_Location().longitude!=null && !"".equals(devicelocation.getUeGeo_Location().longitude) 
							){
						
						try{
							devicelatitude = Float.parseFloat(devicelocation.getUeGeo_Location().latitude);
							devicelongitude = Float.parseFloat(devicelocation.getUeGeo_Location().longitude);
							locationlatitude = Float.parseFloat(wifiLocation.latitude);
							locationlongitude = Float.parseFloat(wifiLocation.longitude);							
							radius = Float.parseFloat(wifiLocation.radius);
							
						}catch(NumberFormatException nfe){
							EliteSession.eLog.e(CustomConstant.ApplicationTag,"Exception in parsing location attributes"+nfe.getMessage());
							continue;
						}
						
						if(isValidLocation(devicelatitude, devicelongitude, locationlatitude, locationlongitude, radius)){
							validationStatus = CustomConstant.MATCHED;
							EliteSession.eLog.d(CustomConstant.ApplicationTag,"Location is matched.");
							break;
						}
						
					}
															
				}
				
			}
			//To break Outer FOR loop
			if(validationStatus == CustomConstant.MATCHED){
				EliteSession.eLog.d(CustomConstant.ApplicationTag,"UE Location is found with in valid HOTSPOT ,so skipping next geo locations specified into validity areas.");
				break;
			}
			
		}
								
		return validationStatus;
	}
	
	private boolean isValidLocation(double ueLatitude,double ueLongitude,double locationLatitude,double locationLongitude,double radius){
		boolean isWithinLocation = false;
		double distance = ElitelibUtility.getDistanceBetweenLocations(ueLatitude, ueLongitude, locationLatitude, locationLongitude, 'M');
		
		if(distance<radius){
			isWithinLocation = true;
			EliteSession.eLog.d(CustomConstant.ApplicationTag,"Device is with in specified locations distance:"+distance+" and radius:"+radius);
		}
		return isWithinLocation;
	}

}
