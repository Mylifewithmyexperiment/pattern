package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

public class UpdateAccountStatusResponseDetail implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String billingAccountNumber;
	private List<AccountReference> successServiceInstanceRef;
	private List<ResponseMessage> failureServiceInstanceRef;
	
	public String getBillingAccountNumber() {
		return billingAccountNumber;
	}
	public void setBillingAccountNumber(String billingAccountNumber) {
		this.billingAccountNumber = billingAccountNumber;
	}
	public List<AccountReference> getSuccessServiceInstanceRef() {
		return successServiceInstanceRef;
	}
	public void setSuccessServiceInstanceRef(List<AccountReference> successServiceInstanceRef) {
		this.successServiceInstanceRef = successServiceInstanceRef;
	}
	public List<ResponseMessage> getFailureServiceInstanceRef() {
		return failureServiceInstanceRef;
	}
	public void setFailureServiceInstanceRef(List<ResponseMessage> failureServiceInstanceRef) {
		this.failureServiceInstanceRef = failureServiceInstanceRef;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(80);
		builder.append("UpdateAccountStatusResponseDetail [billingAccountNumber=").append(billingAccountNumber)
				.append(", successServiceInstanceRef=").append(successServiceInstanceRef)
				.append(", failureServiceInstanceRef=").append(failureServiceInstanceRef).append("]");
		return builder.toString();
	} 

}
