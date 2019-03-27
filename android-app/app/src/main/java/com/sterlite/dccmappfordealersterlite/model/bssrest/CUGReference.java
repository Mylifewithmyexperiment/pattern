package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;

public class CUGReference extends BaseRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String accountNumber;
	private String groupNumber;
	private String memberNumber;
	private String name;
	private String shortCode;
	private String memberType;
	private long tenantId;
	private String billingAreaId;
	
	public String getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}
	public String getMemberNumber() {
		return memberNumber;
	}
	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortCode() {
		return shortCode;
	}
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public long getTenantId() {
		return tenantId;
	}
	public void setTenantId(long tenantId) {
		this.tenantId = tenantId;
	}
	public String getBillingAreaId() {
		return billingAreaId;
	}
	public void setBillingAreaId(String billingAreaId) {
		this.billingAreaId = billingAreaId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(80);
		builder.append("CUGReference [groupNumber=").append(groupNumber).append(", accountNumber=").append(accountNumber).append(", memberNumber=").append(memberNumber)
				.append(", name=").append(name).append(", shortCode=").append(shortCode).append(", memberType=")
				.append(memberType).append(", tenantId=").append(tenantId).append(", billingAreaId=")
				.append(billingAreaId).append("]");
		return builder.toString();
	}

}
