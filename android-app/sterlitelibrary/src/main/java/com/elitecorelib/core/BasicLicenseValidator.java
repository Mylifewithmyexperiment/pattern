package com.elitecorelib.core;

import java.util.Date;

import android.content.Context;



class BasicLicenseValidator implements LicenseValidator{
	private static LicenseData licenseData ;
	private static int UsageCounter = 0;
	private String MODULE= "BasicLicenseValidator";
		
	static{
		try {
			licenseData = LicenseUtility.parseLicense();
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
	public boolean isValidLicense(Context context) {	
		UsageCounter++;
		if(licenseData==null){
			return false;
		}
		
		//Now check for Date 
		if(licenseData.getLastDateForUsage()!=null){
			if(licenseData.getLastDateForUsage().before(new Date())){
				 EliteSession.eLog.d(MODULE,"Date expired");
				return false;
			}
		}
		
		
		if(licenseData.getNoOfUsage()!=-1 && CoreConstants.ENABLE_LIMITED_API_USAGE){
			if( licenseData.getNoOfUsage() < UsageCounter){
				 EliteSession.eLog.d(MODULE,"No of usage Expired");
				return false;
			}
		}
		
		
		if(!licenseData.getAndroidPackageName().equals(context.getPackageName())){
			 EliteSession.eLog.d(MODULE,"Package Not valid");
			return false;
		}
		
		return true;
	}	
	
}
