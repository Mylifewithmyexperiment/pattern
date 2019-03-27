package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;


public class AccountReferenceWrapper extends BaseRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<AccountReference> accountReference;

	public List<AccountReference> getAccountReference() {
		return accountReference;
	}

	public void setAccountReference(List<AccountReference> accountReference) {
		this.accountReference = accountReference;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountReferenceWrapper [accountReference=");
		builder.append(accountReference);
		builder.append("]");
		return builder.toString();
	}
}
