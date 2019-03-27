package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class BalanceRequestDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String packageName;
	private String serviceAlias;
	private String creditAttribute;
	private String creditAttributeValue;
	private String ratingPolicyName;
	private String timeBand;
	
	
	public String getPackageName() {
		return packageName;
	}
	
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public String getServiceAlias() {
		return serviceAlias;
	}
	
	public void setServiceAlias(String serviceAlias) {
		this.serviceAlias = serviceAlias;
	}
	
	public String getCreditAttribute() {
		return creditAttribute;
	}
	
	public void setCreditAttribute(String creditAttribute) {
		this.creditAttribute = creditAttribute;
	}
	
	public String getCreditAttributeValue() {
		return creditAttributeValue;
	}
	
	public void setCreditAttributeValue(String creditAttributeValue) {
		this.creditAttributeValue = creditAttributeValue;
	}
	
	public String getRatingPolicyName() {
		return ratingPolicyName;
	}
	
	public void setRatingPolicyName(String ratingPolicyName) {
		this.ratingPolicyName = ratingPolicyName;
	}
	
	public String getTimeBand() {
		return timeBand;
	}
	
	public void setTimeBand(String timeBand) {
		this.timeBand = timeBand;
	}
	
}
