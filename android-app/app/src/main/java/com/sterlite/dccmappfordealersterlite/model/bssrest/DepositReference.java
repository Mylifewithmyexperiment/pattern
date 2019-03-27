package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class DepositReference implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String type;
	private Long amount;
	private String depositStatus;
	private String autoTransferStatus;
	private TimePeriod validFor;
	private DepositAutoTransferDetail depositAutoTransferDetail;
	private String billingType;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getDepositStatus() {
		return depositStatus;
	}
	public void setDepositStatus(String depositStatus) {
		this.depositStatus = depositStatus;
	}
	public String getAutoTransferStatus() {
		return autoTransferStatus;
	}
	public void setAutoTransferStatus(String autoTransferStatus) {
		this.autoTransferStatus = autoTransferStatus;
	}
	public TimePeriod getValidFor() {
		return validFor;
	}
	public void setValidFor(TimePeriod validFor) {
		this.validFor = validFor;
	}
	public DepositAutoTransferDetail getDepositAutoTransferDetail() {
		return depositAutoTransferDetail;
	}
	public void setDepositAutoTransferDetail(DepositAutoTransferDetail depositAutoTransferDetail) {
		this.depositAutoTransferDetail = depositAutoTransferDetail;
	}
	public String getBillingType() {
		return billingType;
	}
	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}
	@Override
	public String toString() {
		return "DepositReference [type=" + type + ", amount=" + amount + ", depositStatus=" + depositStatus
				+ ", autoTransferStatus=" + autoTransferStatus + ", validFor=" + validFor + ", billingType=" + billingType
				+ ", depositAutoTransferDetail=" + depositAutoTransferDetail + "]";
	}
	
	
	
}
