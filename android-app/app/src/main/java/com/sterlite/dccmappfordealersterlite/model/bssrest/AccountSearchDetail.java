package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

public class AccountSearchDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String accountNumber;
	private String accountStatus;
	private String accountType;
	private String accountName;
	private Long tenantId;
	private String customerCategory;
	private List<AccountAttributes> accountAttributesList;
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public String getCustomerCategory() {
		return customerCategory;
	}
	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}
	public List<AccountAttributes> getAccountAttributesList() {
		return accountAttributesList;
	}
	public void setAccountAttributesList(List<AccountAttributes> accountAttributesList) {
		this.accountAttributesList = accountAttributesList;
	}
	@Override
	public String toString() {
		return "AccountSearchDetail [accountNumber=" + accountNumber + ", accountStatus=" + accountStatus
				+ ", accountType=" + accountType + ", accountName=" + accountName + ", tenantId=" + tenantId
				+ ", customerCategory=" + customerCategory + ", accountAttributesList=" + accountAttributesList + "]";
	}
}
