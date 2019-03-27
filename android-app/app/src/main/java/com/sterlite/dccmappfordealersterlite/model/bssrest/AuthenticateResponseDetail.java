package com.sterlite.dccmappfordealersterlite.model.bssrest;

public class AuthenticateResponseDetail  {

	private static final long serialVersionUID = 1L;
	String accountNumber;
	String accountType;
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
}
