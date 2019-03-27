package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class AccountReference implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AccountRelationType relationType;
	private String accountNumber;
	private String refId;
	
	public AccountRelationType getRelationType() {
		return relationType;
	}
	public void setRelationType(AccountRelationType relationType) {
		this.relationType = relationType;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	@Override
	public String toString() {
		return "AccountReference [relationType=" + relationType + ", accountNumber=" + accountNumber + ", refId="
				+ refId + "]";
	}
	
}
