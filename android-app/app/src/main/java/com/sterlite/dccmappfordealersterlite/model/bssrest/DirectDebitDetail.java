package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class DirectDebitDetail implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long directDebitId;
	private String type;
	private DirectDebitPaymentMode paymentmode; 
	private String verificationStatus;
	private String paymentConsiderationFor;
	private TimePeriod validFor;
	private BankAccount bankAccount;
	private DebitCreditCard debitCreditCard;
	
	public Long getDirectDebitId() {
		return directDebitId;
	}
	public void setDirectDebitId(Long directDebitId) {
		this.directDebitId = directDebitId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getPaymentConsiderationFor() {
		return paymentConsiderationFor;
	}
	public void setPaymentConsiderationFor(String paymentConsiderationFor) {
		this.paymentConsiderationFor = paymentConsiderationFor;
	}
	public TimePeriod getValidFor() {
		return validFor;
	}
	public void setValidFor(TimePeriod validFor) {
		this.validFor = validFor;
	}
	public BankAccount getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}
	public DebitCreditCard getDebitCreditCard() {
		return debitCreditCard;
	}
	public void setDebitCreditCard(DebitCreditCard debitCreditCard) {
		this.debitCreditCard = debitCreditCard;
	}
	@Override
	public String toString() {
		return "DirectDebitDetail [directDebitId=" + directDebitId + ", type=" + type + ", paymentmode=" + paymentmode
				+ ", verificationStatus=" + verificationStatus + ", paymentConsiderationFor=" + paymentConsiderationFor
				+ ", validFor=" + validFor + ", bankAccount=" + bankAccount + ", debitCreditCard=" + debitCreditCard
				+ "]";
	}
	
	
	
}
