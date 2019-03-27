package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;

public class BillingSummary extends BaseRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long lastPaymentAmount;
	private Long lastBillAmount;
	private Long outstandingAmount;
	
	public Long getLastPaymentAmount() {
		return lastPaymentAmount;
	}
	
	public void setLastPaymentAmount(Long lastPaymentAmount) {
		this.lastPaymentAmount = lastPaymentAmount;
	}
	
	public Long getLastBillAmount() {
		return lastBillAmount;
	}
	
	public void setLastBillAmount(Long lastBillAmount) {
		this.lastBillAmount = lastBillAmount;
	}
	
	public Long getOutstandingAmount() {
		return outstandingAmount;
	}
	
	public void setOutstandingAmount(Long outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}
	
	@Override
	public String toString() {
		return "BillingAccountDetail [outstandingAmount=" +outstandingAmount
				+ ", lastPaymentAmount= " + lastPaymentAmount
				+ ", lastBillAmount= " +lastBillAmount 
				+ "]";
	}
}
