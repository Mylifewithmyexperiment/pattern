package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class CreditClassReference implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String creditClass;
	private Long creditLimit;
	private String dunningType;
	private String scenarioName;
	
	public String getCreditClass() {
		return creditClass;
	}
	public void setCreditClass(String creditClass) {
		this.creditClass = creditClass;
	}
	public Long getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(Long creditLimit) {
		this.creditLimit = creditLimit;
	}
	public String getDunningType() {
		return dunningType;
	}
	public void setDunningType(String dunningType) {
		this.dunningType = dunningType;
	}
	public String getScenarioName() {
		return scenarioName;
	}
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}
	@Override
	public String toString() {
		return "CreditClassReference [creditClass=" + creditClass + ", creditLimit=" + creditLimit + ", dunningType="
				+ dunningType + ", scenarioName=" + scenarioName + "]";
	}
	
}
