package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

public class DepositDetail implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String type;
	
	private AccountReference billingAccountRef;
	private AccountReference serviceInstanceRef;
	
	private Long depositAmount;
	private Long remainingDepositAmount;
	private Long transferredRefundAmount;
	
	private String depositStatus;
	private String autoTransferredStatus;
	
	private DepositAutoTransferDetail autoTransferDepositDetail;
	private List<InvoiceReference> transferInvoiceRef;
	private List<DepositRefundDetail> depositRefundDetail;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public AccountReference getBillingAccountRef() {
		return billingAccountRef;
	}
	public void setBillingAccountRef(AccountReference billingAccountRef) {
		this.billingAccountRef = billingAccountRef;
	}
	public AccountReference getServiceInstanceRef() {
		return serviceInstanceRef;
	}
	public void setServiceInstanceRef(AccountReference serviceInstanceRef) {
		this.serviceInstanceRef = serviceInstanceRef;
	}
	public Long getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(Long depositAmount) {
		this.depositAmount = depositAmount;
	}
	public Long getRemainingDepositAmount() {
		return remainingDepositAmount;
	}
	public void setRemainingDepositAmount(Long remainingDepositAmount) {
		this.remainingDepositAmount = remainingDepositAmount;
	}
	public Long getTransferredRefundAmount() {
		return transferredRefundAmount;
	}
	public void setTransferredRefundAmount(Long transferredRefundAmount) {
		this.transferredRefundAmount = transferredRefundAmount;
	}
	public String getDepositStatus() {
		return depositStatus;
	}
	public void setDepositStatus(String depositStatus) {
		this.depositStatus = depositStatus;
	}
	public String getAutoTransferredStatus() {
		return autoTransferredStatus;
	}
	public void setAutoTransferredStatus(String autoTransferredStatus) {
		this.autoTransferredStatus = autoTransferredStatus;
	}
	public DepositAutoTransferDetail getAutoTransferDepositDetail() {
		return autoTransferDepositDetail;
	}
	public void setAutoTransferDepositDetail(DepositAutoTransferDetail autoTransferDepositDetail) {
		this.autoTransferDepositDetail = autoTransferDepositDetail;
	}
	public List<InvoiceReference> getTransferInvoiceRef() {
		return transferInvoiceRef;
	}
	public void setTransferInvoiceRef(List<InvoiceReference> transferInvoiceRef) {
		this.transferInvoiceRef = transferInvoiceRef;
	}
	public List<DepositRefundDetail> getDepositRefundDetail() {
		return depositRefundDetail;
	}
	public void setDepositRefundDetail(List<DepositRefundDetail> depositRefundDetail) {
		this.depositRefundDetail = depositRefundDetail;
	}
	@Override
	public String toString() {
		return "DepositDetail [id=" + id + ", type=" + type + ", billingAccountRef=" + billingAccountRef
				+ ", serviceInstanceRef=" + serviceInstanceRef + ", depositAmount=" + depositAmount
				+ ", remainingDepositAmount=" + remainingDepositAmount + ", transferredRefundAmount="
				+ transferredRefundAmount + ", depositStatus=" + depositStatus + ", autoTransferredStatus="
				+ autoTransferredStatus + ", autoTransferDepositDetail=" + autoTransferDepositDetail
				+ ", transferInvoiceRef=" + transferInvoiceRef + ", depositRefundDetail=" + depositRefundDetail + "]";
	}
	

}
