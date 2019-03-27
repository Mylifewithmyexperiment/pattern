package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;

public class ChangeChargeStatusRequestDetail extends BaseRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Action action;
	private String serviceInstanceNumber;
	private Long customerPackageHistoryId;
	private String chargeName;
	private TimePeriod validFor;
	private List<Characteristic> additionalParameters;
	
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public String getServiceInstanceNumber() {
		return serviceInstanceNumber;
	}
	public void setServiceInstanceNumber(String serviceInstanceNumber) {
		this.serviceInstanceNumber = serviceInstanceNumber;
	}
	public Long getCustomerPackageHistoryId() {
		return customerPackageHistoryId;
	}
	public void setCustomerPackageHistoryId(Long customerPackageHistoryId) {
		this.customerPackageHistoryId = customerPackageHistoryId;
	}
	public String getChargeName() {
		return chargeName;
	}
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
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
		StringBuilder builder = new StringBuilder();
		builder.append("ChangeChargeStatusRequestDetail [action=").append(action).append(", serviceInstanceNumber=")
				.append(serviceInstanceNumber).append(", customerPackageHistoryId=").append(customerPackageHistoryId)
				.append(", chargeId=").append(chargeName).append(", validFor=").append(validFor)
				.append(", additionalParameters=").append(additionalParameters).append("]");
		return builder.toString();
	}
	
	
}
