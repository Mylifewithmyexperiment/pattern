package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;

public class DirectDebitDetailReference  extends BaseRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long directDebitId;
	private DirectDebitPaymentMode paymentmode; 
	private String verificationStatus;
	
	public Long getDirectDebitId() {
		return directDebitId;
	}
	public void setDirectDebitId(Long directDebitId) {
		this.directDebitId = directDebitId;
	}
	public DirectDebitPaymentMode getPaymentmode() {
		return paymentmode;
	}
	public void setPaymentmode(DirectDebitPaymentMode paymentmode) {
		this.paymentmode = paymentmode;
	}
	public String getVerificationStatus() {
		return verificationStatus;
	}
	public void setVerificationStatus(String verificationStatus) {
		this.verificationStatus = verificationStatus;
	}
	@Override
	public String toString() {
		return "DirectDebitDetailReference [directDebitId=" + directDebitId + ", paymentmode=" + paymentmode
				+ ", verificationStatus=" + verificationStatus + "]";
	}
	
	
}
