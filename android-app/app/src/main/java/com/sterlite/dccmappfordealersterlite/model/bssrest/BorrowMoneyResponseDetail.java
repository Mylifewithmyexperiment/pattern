package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class BorrowMoneyResponseDetail implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Double borrowedAmount;
	private Double newBalance;
	private Double packageCost;
	private String rechargeRequestNo;
	
	public Double getBorrowedAmount() {
		return borrowedAmount;
	}
	public void setBorrowedAmount(Double borrowedAmount) {
		this.borrowedAmount = borrowedAmount;
	}
	public Double getNewBalance() {
		return newBalance;
	}
	public void setNewBalance(Double newBalance) {
		this.newBalance = newBalance;
	}
	public Double getPackageCost() {
		return packageCost;
	}
	public void setPackageCost(Double packageCost) {
		this.packageCost = packageCost;
	}
	public String getRechargeRequestNo() {
		return rechargeRequestNo;
	}
	public void setRechargeRequestNo(String rechargeRequestNo) {
		this.rechargeRequestNo = rechargeRequestNo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(100);
		builder.append("BorrowMoneyResponseDetail [borrowedAmount=").append(borrowedAmount).append(", newBalance=")
				.append(newBalance).append(", packageCost=").append(packageCost).append(", rechargeRequestNo=")
				.append(rechargeRequestNo).append("]");
		return builder.toString();
	}

}
