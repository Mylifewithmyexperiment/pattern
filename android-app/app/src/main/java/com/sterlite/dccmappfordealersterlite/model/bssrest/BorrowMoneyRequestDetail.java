package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;

public class BorrowMoneyRequestDetail extends BaseRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Action action;
	private long tenantId;
	private String mobileNumber;
	private List<Characteristic> additionalParameters;
	
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public long getTenantId() {
		return tenantId;
	}
	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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
		builder.append("BorrowMoneyRequestDetail [action=").append(action).append(", tenantId=").append(tenantId)
				.append(", mobileNumber=").append(mobileNumber).append(", additionalParameters=")
				.append(additionalParameters).append("]");
		return builder.toString();
	}
	
}
