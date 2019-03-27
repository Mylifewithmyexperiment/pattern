package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

public class AddonStatusResponseDetail implements Serializable{
	private static final long serialVersionUID = 1L;	
	private String serviceInstanceNumber;
	private List<String> successUpdateDetails;
	private List<ResponseMessage> failureUpdateDetails;
	public String getServiceInstanceNumber() {
		return serviceInstanceNumber;
	}
	public void setServiceInstanceNumber(String serviceInstanceNumber) {
		this.serviceInstanceNumber = serviceInstanceNumber;
	}
	public List<String> getSuccessUpdateDetails() {
		return successUpdateDetails;
	}
	public void setSuccessUpdateDetails(List<String> successUpdateDetails) {
		this.successUpdateDetails = successUpdateDetails;
	}
	public List<ResponseMessage> getFailureUpdateDetails() {
		return failureUpdateDetails;
	}
	public void setFailureUpdateDetails(List<ResponseMessage> failureUpdateDetails) {
		this.failureUpdateDetails = failureUpdateDetails;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(300);
		builder.append("AddonStatusResponseDetail [serviceInstanceNumber=");
		builder.append(serviceInstanceNumber);
		builder.append(", successUpdateDetails=");
		builder.append(successUpdateDetails);
		builder.append(", failureUpdateDetails=");
		builder.append(failureUpdateDetails);
		builder.append("]");
		return builder.toString();
	}
	
	
}
