package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;

public class CustomerDetail extends BaseRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private CustomerAccountDetail customerAccountDetail;
	private BillingAccountDetail billingAccountDetail;
	private ServiceAccountDetail serviceAccountDetail;
	private ServiceInstanceDetail serviceInstanceDetail;
	
	public CustomerAccountDetail getCustomerAccountDetail() {
		return customerAccountDetail;
	}
	public void setCustomerAccountDetail(CustomerAccountDetail customerAccountDetail) {
		this.customerAccountDetail = customerAccountDetail;
	}
	public BillingAccountDetail getBillingAccountDetail() {
		return billingAccountDetail;
	}
	public void setBillingAccountDetail(BillingAccountDetail billingAccountDetail) {
		this.billingAccountDetail = billingAccountDetail;
	}
	public ServiceAccountDetail getServiceAccountDetail() {
		return serviceAccountDetail;
	}
	public void setServiceAccountDetail(ServiceAccountDetail serviceAccountDetail) {
		this.serviceAccountDetail = serviceAccountDetail;
	}
	public ServiceInstanceDetail getServiceInstanceDetail() {
		return serviceInstanceDetail;
	}
	public void setServiceInstanceDetail(ServiceInstanceDetail serviceInstanceDetail) {
		this.serviceInstanceDetail = serviceInstanceDetail;
	}
	
	@Override
	public String toString() {
		return "CustomerDetail [customerAccountDetail=" + customerAccountDetail + ", billingAccountDetail="
				+ billingAccountDetail + ", serviceAccountDetail=" + serviceAccountDetail + ", serviceInstanceDetail="
				+ serviceInstanceDetail + "]";
	}
	
}
