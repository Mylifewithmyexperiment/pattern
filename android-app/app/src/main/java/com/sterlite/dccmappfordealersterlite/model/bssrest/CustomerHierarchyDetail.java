package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

public class CustomerHierarchyDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private CustomerAccountDetail customerAccountDetail;
	private List<BillingAccountDetail> billingAccountDetail;
	private List<ServiceAccountDetail> serviceAccountDetail;
	private List<ServiceInstanceDetail> serviceInstanceDetail;
	private List<BillingAggregatorDetail> billingAggregatorDetail;
	
	public CustomerAccountDetail getCustomerAccountDetail() {
		return customerAccountDetail;
	}
	public void setCustomerAccountDetail(CustomerAccountDetail customerAccountDetail) {
		this.customerAccountDetail = customerAccountDetail;
	}
	public List<BillingAccountDetail> getBillingAccountDetail() {
		return billingAccountDetail;
	}
	public void setBillingAccountDetail(List<BillingAccountDetail> billingAccountDetail) {
		this.billingAccountDetail = billingAccountDetail;
	}
	public List<ServiceAccountDetail> getServiceAccountDetail() {
		return serviceAccountDetail;
	}
	public void setServiceAccountDetail(List<ServiceAccountDetail> serviceAccountDetail) {
		this.serviceAccountDetail = serviceAccountDetail;
	}
	public List<ServiceInstanceDetail> getServiceInstanceDetail() {
		return serviceInstanceDetail;
	}
	public void setServiceInstanceDetail(List<ServiceInstanceDetail> serviceInstanceDetail) {
		this.serviceInstanceDetail = serviceInstanceDetail;
	}
	public List<BillingAggregatorDetail> getBillingAggregatorDetail() {
		return billingAggregatorDetail;
	}
	public void setBillingAggregatorDetail(List<BillingAggregatorDetail> billingAggregatorDetail) {
		this.billingAggregatorDetail = billingAggregatorDetail;
	}
	@Override
	public String toString() {
		return "CustomerDetail [customerAccountDetail=" + customerAccountDetail + ", billingAccountDetail="
				+ billingAccountDetail + ", serviceAccountDetail=" + serviceAccountDetail + ", serviceInstanceDetail="
				+ serviceInstanceDetail + ", billingAggregatorDetail=" + billingAggregatorDetail + "]";
	}
	
}
