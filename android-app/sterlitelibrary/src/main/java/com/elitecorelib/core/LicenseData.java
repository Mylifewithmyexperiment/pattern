package com.elitecorelib.core;

import java.util.Date;

class LicenseData {
	private int noOfUsage;
	private Date lastDateForUsage;
	private String androidPackageName;
	
	public LicenseData(int noOfUsage, Date lastDateForUsage,
			String androidPackageName,String validationKey) throws Exception {
		super();
		if(validationKey!=null && !"".equals(validationKey)  && "gt50981".equals(validationKey)){
			this.noOfUsage = noOfUsage;
			this.lastDateForUsage = lastDateForUsage;
			this.androidPackageName = androidPackageName;
		}else{
			throw new Exception("Please Mention Correct Validation Key for License Data");
		}
	}

	public int getNoOfUsage() {
		return noOfUsage;
	}

	public Date getLastDateForUsage() {
		return lastDateForUsage;
	}

	public String getAndroidPackageName() {
		return androidPackageName;
	}
			
	
}
