package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.util.List;

public class ServiceInstanceRef {
	private String serviceInstancenumber;
	private List<Long> productSubscriptionIds;
	public String getServiceInstancenumber() {
		return serviceInstancenumber;
	}
	public void setServiceInstancenumber(String serviceInstancenumber) {
		this.serviceInstancenumber = serviceInstancenumber;
	}
	public List<Long> getProductSubscriptionIds() {
		return productSubscriptionIds;
	}
	public void setProductSubscriptionIds(List<Long> productSubscriptionIds) {
		this.productSubscriptionIds = productSubscriptionIds;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(300);
		builder.append("ServiceInstanceRef [serviceInstancenumber=").append(serviceInstancenumber)
				.append(", productSubscriptionIds=").append(productSubscriptionIds).append("]");
		return builder.toString();
	}
	
	
}
