package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class BankAccount implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String bankAccountNumber;
	private String branchName;
	private String accountHolder;
	private Long branchId;
	
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getAccountHolder() {
		return accountHolder;
	}
	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	@Override
	public String toString() {
		return "BankAccount [bankAccountNumber=" + bankAccountNumber + ", branchName=" + branchName + ", accountHolder="
				+ accountHolder + ", branchId=" + branchId + "]";
	}
	
	
}
