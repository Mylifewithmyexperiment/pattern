package com.sterlite.dccmappfordealersterlite.model.bssrest.receivablemethod;

import java.io.Serializable;
import java.util.Date;


/**
 * @author amit.patel
 * @version 1.0
 * Created on September 23, 2016
 */

public class DemandDraftMethod extends BaseReceivableMethod implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String demandDraftNumber;
	private String recievableInstitute;
	private String instituteMasterName;
	private Date demandDraftDate;
	private long instituteMasterId = 0L;
	private Date expiryDate;
	private String instituteBranchName;
	
	public String getInstituteBranchName() {
		return instituteBranchName;
	}
	public void setInstituteBranchName(String instituteBranchName) {
		this.instituteBranchName = instituteBranchName;
	}
	
	public String getDemandDraftNumber() {
		return demandDraftNumber;
	}

	public void setDemandDraftNumber(String demandDraftNumber) {
		this.demandDraftNumber = demandDraftNumber;
	}

	public Date getDemandDraftDate() {
		return demandDraftDate != null? new Date(demandDraftDate.getTime()):null;
	}

	public void setDemandDraftDate(Date demandDraftDate) {
		this.demandDraftDate = demandDraftDate != null? new Date(demandDraftDate.getTime()):null;
	}

	public String getRecievableInstitute() {
		return recievableInstitute;
	}

	public void setRecievableInstitute(String recievableInstitute) {
		this.recievableInstitute = recievableInstitute;
	}

	public long getInstituteMasterId() {
		return instituteMasterId;
	}

	public void setInstituteMasterId(long instituteMasterId) {
		this.instituteMasterId = instituteMasterId;
	}

	public String getInstituteMasterName() {
		return instituteMasterName;
	}

	public void setInstituteMasterName(String instituteMasterName) {
		this.instituteMasterName = instituteMasterName;
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
		builder.append("DemandDraftMethod [recievableInstitute=");
		builder.append(recievableInstitute);
		builder.append(", instituteMasterName=");
		builder.append(instituteMasterName);
		builder.append(", demandDraftDate=");
		builder.append(demandDraftDate);
		builder.append(", instituteMasterId=");
		builder.append(instituteMasterId);
		builder.append(", expiryDate=");
		builder.append(expiryDate);
		builder.append(", instituteBranchName=");
		builder.append(instituteBranchName);
		builder.append(",");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}	
}
