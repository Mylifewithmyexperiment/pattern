package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;


public class AccountSearchRequest extends BaseRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Action action;
	private String accountNumber;
	private String accountName;
	private String accountType;
	private String networkServiceIdentifier; 
	private String serviceUserName;
	private String channelPartnerNumber;
	private String billingCycleName;
	private String billingareaid;
	private long tenantid;
	private AdvanceSearchParameter advanceSearchParameter;
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getNetworkServiceIdentifier() {
		return networkServiceIdentifier;
	}
	public void setNetworkServiceIdentifier(String networkServiceIdentifier) {
		this.networkServiceIdentifier = networkServiceIdentifier;
	}
	public String getServiceUserName() {
		return serviceUserName;
	}
	public void setServiceUserName(String serviceUserName) {
		this.serviceUserName = serviceUserName;
	}
	public String getChannelPartnerNumber() {
		return channelPartnerNumber;
	}
	public void setChannelPartnerNumber(String channelPartnerNumber) {
		this.channelPartnerNumber = channelPartnerNumber;
	}
	public String getBillingCycleName() {
		return billingCycleName;
	}
	public void setBillingCycleName(String billingCycleName) {
		this.billingCycleName = billingCycleName;
	}
	public String getBillingareaid() {
		return billingareaid;
	}
	public void setBillingareaid(String billingareaid) {
		this.billingareaid = billingareaid;
	}
	public long getTenantid() {
		return tenantid;
	}
	public void setTenantid(long tenantid) {
		this.tenantid = tenantid;
	}
	public AdvanceSearchParameter getAdvanceSearchParameter() {
		return advanceSearchParameter;
	}
	public void setAdvanceSearchParameter(AdvanceSearchParameter advanceSearchParameter) {
		this.advanceSearchParameter = advanceSearchParameter;
	}
	@Override
	public String toString() {
		return "AccountSearchRequest [action=" + action + ", accountNumber=" + accountNumber + ", accountName="
				+ accountName + ", accountType=" + accountType + ", networkServiceIdentifier="
				+ networkServiceIdentifier + ", serviceUserName=" + serviceUserName + ", channelPartnerNumber="
				+ channelPartnerNumber + ", billingCycleName=" + billingCycleName + ", billingareaid=" + billingareaid
				+ ", tenantid=" + tenantid + ", advanceSearchParameter=" + advanceSearchParameter + "]";
	}
}
