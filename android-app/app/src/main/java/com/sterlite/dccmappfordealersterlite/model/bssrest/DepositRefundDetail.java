package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class DepositRefundDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String refundMode;
	private Long refundAmount;
	private String chequeNumber;
	private TimePeriod chequeValidity;
	private String instituteName;
	private String branchName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRefundMode() {
		return refundMode;
	}
	public void setRefundMode(String refundMode) {
		this.refundMode = refundMode;
	}
	public Long getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getChequeNumber() {
		return chequeNumber;
	}
	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}
	public TimePeriod getChequeValidity() {
		return chequeValidity;
	}
	public void setChequeValidity(TimePeriod chequeValidity) {
		this.chequeValidity = chequeValidity;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	@Override
	public String toString() {
		return "DepositRefundDetail [id=" + id + ", refundMode=" + refundMode + ", refundAmount=" + refundAmount
				+ ", chequeNumber=" + chequeNumber + ", chequeValidity=" + chequeValidity + ", instituteName="
				+ instituteName + ", branchName=" + branchName + "]";
	}
	
	
}
