package com.sterlite.dccmappfordealersterlite.model.bssrest.receivablemethod;

import java.io.Serializable;
import java.util.Date;


/**
 * @author amit.patel
 * @version 1.0
 * Created on September 23, 2016
 */

public class ChequeMethod extends BaseReceivableMethod  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String chequeNumber;
	private String recievableInstitute;
	private Date chequeDate;
	private Date expiryDate;
	private String depositBankName;
	private String instituteMasterName;
	private long instituteMasterId;
	private String instituteBranchName;
	
	public String getInstituteBranchName() {
		return instituteBranchName;
	}
	public void setInstituteBranchName(String instituteBranchName) {
		this.instituteBranchName = instituteBranchName;
	}
	
	public long getInstituteMasterId() {
		return instituteMasterId;
	}
	public void setInstituteMasterId(long lInstituteMasterid) {
		this.instituteMasterId = lInstituteMasterid;
	}
	
	public String getInstituteMasterName() {
		return instituteMasterName;
	}
	public void setInstituteMasterName(String strInstituteMasterName) {
		this.instituteMasterName = strInstituteMasterName;
	}
	
	public String getDepositBankName() {
		return depositBankName;
	}
	public void setDepositBankName(String strDepositBankName) {
		this.depositBankName = strDepositBankName;
	}
	
	public String getChequeNumber() {
		return chequeNumber;
	}
	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}
	
	public String getRecievableInstitute() {
		return recievableInstitute;
	}
	public void setRecievableInstitute(String recievableInstitute) {
		this.recievableInstitute = recievableInstitute;
	}
	
	public Date getChequeDate() {
		return chequeDate != null? new Date(chequeDate.getTime()):null;
	}
	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate != null? new Date(chequeDate.getTime()):null;
	}
	
	public Date getExpiryDate() {
		return expiryDate != null?new Date(expiryDate.getTime()):null;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate != null?new Date(expiryDate.getTime()):null;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChequeMethod [ recievableInstitute=");
		builder.append(recievableInstitute);
		builder.append(", chequeDate=");
		builder.append(chequeDate);
		builder.append(", expiryDate=");
		builder.append(expiryDate);
		builder.append(", depositBankName=");
		builder.append(depositBankName);
		builder.append(", instituteMasterName=");
		builder.append(instituteMasterName);
		builder.append(", instituteMasterId=");
		builder.append(instituteMasterId);
		builder.append(", instituteBranchName=");
		builder.append(instituteBranchName);
		builder.append(",");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}	
}