package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;

public class AddonStatusRequestDetail extends BaseRequest implements Serializable{
	private static final long serialVersionUID = 1L;	
	private Action action;
	private String billingAccountNumber;
	private String tokenNo;
	private String newStatus;
	private List<ServiceInstanceRef> serviceInstanceRefs;
	private boolean isBillingEffectRequired = true;
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public String getBillingAccountNumber() {
		return billingAccountNumber;
	}
	public void setBillingAccountNumber(String billingAccountNumber) {
		this.billingAccountNumber = billingAccountNumber;
	}
	public String getTokenNo() {
		return tokenNo;
	}
	public void setTokenNo(String tokenNo) {
		this.tokenNo = tokenNo;
	}
	public String getNewStatus() {
		return newStatus;
	}
	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}
	public List<ServiceInstanceRef> getServiceInstanceRefs() {
		return serviceInstanceRefs;
	}
	public void setServiceInstanceRefs(List<ServiceInstanceRef> serviceInstanceRefs) {
		this.serviceInstanceRefs = serviceInstanceRefs;
	}
	public boolean isBillingEffectRequired() {
		return isBillingEffectRequired;
	}
	public void setBillingEffectRequired(boolean isBillingEffectRequired) {
		this.isBillingEffectRequired = isBillingEffectRequired;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(500);
		builder.append("AddonStatusRequestDetail [action=").append(action).append(", billingAccountNumber=")
				.append(billingAccountNumber).append(", tokenNo=").append(tokenNo).append(", newStatus=")
				.append(newStatus).append(", serviceInstanceRefs=").append(serviceInstanceRefs)
				.append(", isBillingEffectRequired=").append(isBillingEffectRequired).append("]");
		return builder.toString();
	}
	
	
}
