package com.sterlite.dccmappfordealersterlite.model.bssrest.receivablemethod;

import java.io.Serializable;


/**
 * @author amit.patel
 * @version 1.0
 * Created on September 23, 2016
 */

public class DirectDebitMethod extends BaseReceivableMethod  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String bankAccountNumber;
	private String bankName;
	private String branchName;
	private String documentNumber;
	private long instituteMasterId;
	private String instituteMasterName;
	
	public String getInstituteMasterName() {
		return instituteMasterName;
	}
	
	public void setInstituteMasterName(String strInstituteMasterName) {
		this.instituteMasterName = strInstituteMasterName;
	}
	
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	
	public long getInstituteMasterId() {
		return instituteMasterId;
	}

	public void setInstituteMasterId(long instituteMasterId) {
		this.instituteMasterId = instituteMasterId;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DirectDebitMethod [bankName=");
		builder.append(bankName);
		builder.append(", branchName=");
		builder.append(branchName);
		builder.append(", documentNumber=");
		builder.append(documentNumber);
		builder.append(", instituteMasterId=");
		builder.append(instituteMasterId);
		builder.append(", instituteMasterName=");
		builder.append(instituteMasterName);
		builder.append(",");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

}