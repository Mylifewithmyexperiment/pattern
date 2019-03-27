package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class BillingAccountBalance  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BalanceType balanceType;
	private double openingBalance;
	private double totalAmount;
	
	public BalanceType getBalanceType() {
		return balanceType;
	}
	
	public void setBalanceType(BalanceType balanceType) {
		this.balanceType = balanceType;
	}
	
	public double getOpeningBalance() {
		return openingBalance;
	}
	
	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}
	
	public double getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(80);
		builder.append("BillingAccountBalance [balanceType=").append(balanceType).append(", openingBalance=")
				.append(openingBalance).append(", totalAmount=").append(totalAmount).append("]");
		return builder.toString();
	}

}
