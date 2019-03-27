package com.sterlite.dccmappfordealersterlite.model.bssrest.receivablemethod;

import java.io.Serializable;
import java.util.Date;


/**
 * @author amit.patel
 * @version 1.0
 * Created on September 23, 2016
 */

public class DebitCardMethod extends BaseReceivableMethod  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String cardHolderName;
	private String cardNumber;
	private String cardType;
	private Date expiryDate;
	
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DebitCardMethod [CardHolderName=");
		builder.append(cardHolderName);
		builder.append(", CardType=");
		builder.append(cardType);
		builder.append(", expiryDate=");
		builder.append(expiryDate);
		builder.append(",");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

	
}