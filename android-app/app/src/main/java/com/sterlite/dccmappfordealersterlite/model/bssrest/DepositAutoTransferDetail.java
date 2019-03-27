package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class DepositAutoTransferDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long totalCycle;
	private Long remainingCycle;
	private Long amount;
	private String mode;
	
	public Long getTotalCycle() {
		return totalCycle;
	}
	public void setTotalCycle(Long totalCycle) {
		this.totalCycle = totalCycle;
	}
	public Long getRemainingCycle() {
		return remainingCycle;
	}
	public void setRemainingCycle(Long remainingCycle) {
		this.remainingCycle = remainingCycle;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	@Override
	public String toString() {
		return "DepositAutoTransferDetail [totalCycle=" + totalCycle + ", remainingCycle=" + remainingCycle
				+ ", amount=" + amount + ", mode=" + mode + "]";
	}
	
	
}
