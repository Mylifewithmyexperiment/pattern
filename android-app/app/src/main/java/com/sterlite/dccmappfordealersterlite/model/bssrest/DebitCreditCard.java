package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class DebitCreditCard implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String cardHolderName;
	private String cardType;
	private String cardNumber;
	private int expiryMonth;
	private int expiryYear;
	private String bankOrBranchName;
	
	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public int getExpiryMonth() {
		return expiryMonth;
	}
	public void setExpiryMonth(int expiryMonth) {
		this.expiryMonth = expiryMonth;
	}
	public int getExpiryYear() {
		return expiryYear;
	}
	public void setExpiryYear(int expiryYear) {
		this.expiryYear = expiryYear;
	}
	
	public String getBankOrBranchName() {
		return bankOrBranchName;
	}
	public void setBankOrBranchName(String bankOrBranchName) {
		this.bankOrBranchName = bankOrBranchName;
	}
	
	@Override
	public String toString() {
		return "DebitCreditCard [cardHolderName=" + cardHolderName + ", cardType=" + cardType + ", cardNumber="
				+ cardNumber + ", expiryMonth=" + expiryMonth + ", expiryYear=" + expiryYear + ", bankOrBranchName="
				+ bankOrBranchName + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}
