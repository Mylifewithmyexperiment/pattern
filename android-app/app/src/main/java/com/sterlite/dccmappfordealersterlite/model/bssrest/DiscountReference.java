package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;

public class DiscountReference extends BaseRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Action action;
	private AccountReference serviceInstanceRef;
	private String discountId;
	private String discountName;
	private Double value;
	private Long totalCycle;
	
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public AccountReference getServiceInstanceRef() {
		return serviceInstanceRef;
	}
	public void setServiceInstanceRef(AccountReference serviceInstanceRef) {
		this.serviceInstanceRef = serviceInstanceRef;
	}
	public String getDiscountId() {
		return discountId;
	}
	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}
	public String getDiscountName() {
		return discountName;
	}
	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Long getTotalCycle() {
		return totalCycle;
	}
	public void setTotalCycle(Long totalCycle) {
		this.totalCycle = totalCycle;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(80);
		builder.append("DiscountReference [serviceInstanceRef=").append(serviceInstanceRef).append(", discountId=")
				.append(discountId).append(", discountName=").append(discountName).append(", value=").append(value)
				.append(", totalCycle=").append(totalCycle).append("]");
		return builder.toString();
	}
	
}
