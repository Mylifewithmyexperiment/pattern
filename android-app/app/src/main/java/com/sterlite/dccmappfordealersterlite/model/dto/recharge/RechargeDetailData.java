package com.sterlite.dccmappfordealersterlite.model.dto.recharge;

import java.io.Serializable;

public class RechargeDetailData implements Serializable {
    private Double balanceValue;
	private String balanceType;
	private String attributeName;
	private String attributeValue;

	public void setBalanceValue(Double balanceValue) {
		this.balanceValue = balanceValue;
	}

	public Double getBalanceValue() {
		return this.balanceValue;
	}

	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}

	public String getBalanceType() {
		return this.balanceType;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeName() {
		return this.attributeName;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getAttributeValue() {
		return this.attributeValue;
	}
}