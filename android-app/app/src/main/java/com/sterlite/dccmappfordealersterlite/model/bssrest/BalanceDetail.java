package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;


public class BalanceDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String serviceAlias;
	private String creditAttribute;
	private String creditAttributeValue;
	private String uom;
	private Double totalBalance;
	private Double consumedBalance;
	private Double availableBalance;
	private TimePeriod validFor;
	private List<Characteristic> additionalParameters;
	
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
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public Double getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(Double totalBalance) {
		this.totalBalance = totalBalance;
	}
	public Double getConsumedBalance() {
		return consumedBalance;
	}
	public void setConsumedBalance(Double consumedBalance) {
		this.consumedBalance = consumedBalance;
	}
	public Double getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}
	public TimePeriod getValidFor() {
		return validFor;
	}
	public void setValidFor(TimePeriod validFor) {
		this.validFor = validFor;
	}
	public List<Characteristic> getAdditionalParameters() {
		return additionalParameters;
	}
	public void setAdditionalParameters(List<Characteristic> additionalParameters) {
		this.additionalParameters = additionalParameters;
	}
	@Override
	public String toString() {
		return "BalanceDetail [serviceAlias=" + serviceAlias + ", creditAttribute=" + creditAttribute
				+ ", creditAttributeValue=" + creditAttributeValue + ", uom=" + uom + ", totalBalance=" + totalBalance
				+ ", consumedBalance=" + consumedBalance + ", availableBalance=" + availableBalance + ", validFor="
				+ validFor + ", additionalParameters=" + additionalParameters + "]";
	}
	
}
