package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class DailyBurnRateResponseDetail implements Serializable {
	 private static final long serialVersionUID = 1L;
	 
	 private String serviceInstanceNumber;

	 private double amount;

	 private int requestedNoOfDays;

	public String getServiceInstanceNumber() {
		return serviceInstanceNumber;
	}

	public void setServiceInstanceNumber(String serviceInstanceNumber) {
		this.serviceInstanceNumber = serviceInstanceNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getRequestedNoOfDays() {
		return requestedNoOfDays;
	}

	public void setRequestedNoOfDays(int requestedNoOfDays) {
		this.requestedNoOfDays = requestedNoOfDays;
	}
	 
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(150);
		builder.append("DailyBurnRateResponseDetail [serviceInstanceNumber=").append(serviceInstanceNumber)
		.append(", amount=").append(amount).append(", requestedNoOfDays=").append(requestedNoOfDays)
		.append("]");
		return builder.toString();
	}
	
	
}
