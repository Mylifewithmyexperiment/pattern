package com.sterlite.dccmappfordealersterlite.model.bssrest.receivablemethod;

import java.io.Serializable;
import java.util.Date;


/**
 * @author amit.patel
 * @version 1.0
 * Created on September 23, 2016
 */

public class CreditCardMethod extends BaseReceivableMethod  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String cardHolderName;
	private String cardNumber;
	private String cardType;
	private Date expiryDate;
	private String firstName;
	private String lastName;
	private String addressOne;
	private String addressTwo;
	private String cardHolderCity;
	private String cardHolderState;
	private String cardHolderCountry;
	private String cardHolderCVVNumber;
	private String transactionCode;
	private String transactionNumber;
	private String transactionType;
	
	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	public Date getExpiryDate() {
		return expiryDate != null? new Date(expiryDate.getTime()):null;
	}
	
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate != null? new Date(expiryDate.getTime()):null;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getAddressOne() {
		return addressOne;
	}
	public void setAddressOne(String addressOne) {
		this.addressOne = addressOne;
	}
	
	public String getAddressTwo() {
		return addressTwo;
	}
	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
	}
	
	public String getCardHolderCity() {
		return cardHolderCity;
	}
	public void setCardHolderCity(String cardHolderCity) {
		this.cardHolderCity = cardHolderCity;
	}
	
	public String getCardHolderState() {
		return cardHolderState;
	}
	public void setCardHolderState(String cardHolderState) {
		this.cardHolderState = cardHolderState;
	}
	
	public String getCardHolderCountry() {
		return cardHolderCountry;
	}
	public void setCardHolderCountry(String cardHolderCountry) {
		this.cardHolderCountry = cardHolderCountry;
	}
	
	public String getCardHolderCVVNumber() {
		return cardHolderCVVNumber;
	}
	public void setCardHolderCVVNumber(String cardHolderCVVNumber) {
		this.cardHolderCVVNumber = cardHolderCVVNumber;
	}
	
	public String getTransactionCode() {
		return transactionCode;
	}
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	
	public String getTransactionNumber() {
		return transactionNumber;
	}
	
	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	
	public String getTransactionType() {
		return transactionType;
	}
	
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CreditCardMethod [cardHolderName=");
		builder.append(cardHolderName);
		builder.append(", cardType=");
		builder.append(cardType);
		builder.append(", expiryDate=");
		builder.append(expiryDate);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", addressOne=");
		builder.append(addressOne);
		builder.append(", addressTwo=");
		builder.append(addressTwo);
		builder.append(", cardHolderCity=");
		builder.append(cardHolderCity);
		builder.append(", cardHolderState=");
		builder.append(cardHolderState);
		builder.append(", cardHolderCountry=");
		builder.append(cardHolderCountry);
		builder.append(", cardHolderCVVNumber=");
		builder.append(cardHolderCVVNumber);
		builder.append(", transactionCode=");
		builder.append(transactionCode);
		builder.append(", transactionNumber=");
		builder.append(transactionNumber);
		builder.append(", transactionType=");
		builder.append(transactionType);
		builder.append(",");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
		
}