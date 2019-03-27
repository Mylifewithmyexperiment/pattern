package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;

public class FNFDetail extends BaseRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String number;
	private Date lockedTillDate;
	private List<FNFMemberDetail> fnfMemberDetail; // NOSONAR
	private long tenantId;
	private String billingAreaId;
	private String planname;
	private String accountNumber;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Date getLockedTillDate() {
		return lockedTillDate;
	}
	public void setLockedTillDate(Date lockedTillDate) {
		this.lockedTillDate = lockedTillDate;
	}
	public List<FNFMemberDetail> getFnfMemberDetail() {
		return fnfMemberDetail;
	}
	public void setFnfMemberDetail(List<FNFMemberDetail> fnfMemberDetail) {
		this.fnfMemberDetail = fnfMemberDetail;
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
	
	public String getPlanname() {
		return planname;
	}
	public void setPlanname(String planname) {
		this.planname = planname;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	@Override
	public String toString() {
		return "FNFDetail [id=" + id + ", number=" + number + ", lockedTillDate=" + lockedTillDate
				+ ", fnfMemberDetail=" + fnfMemberDetail + ", tenantId=" + tenantId + ", billingAreaId=" + billingAreaId
				+ ", planname=" + planname + ", accountNumber=" + accountNumber + "]";
	}
}
