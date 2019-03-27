package com.sterlite.dccmappfordealersterlite.model.bssrest.receivablemethod;

import java.io.Serializable;


/**
 * @author amit.patel
 * @version 1.0
 * Created on September 23, 2016
 */

public class OnlineMethod extends BaseReceivableMethod  implements Serializable{
	private static final long serialVersionUID = 1L;
	protected boolean isOnlinePayment = true;
	
	private String instituteMasterName;
	private String instituteBranchName;
	private long instituteMasterId;
	private String txnid;
	
	public String getTxnid() {
		return txnid;
	}
	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}

	public String getInstituteMasterName() {
		return instituteMasterName;
	}
	public void setInstituteMasterName(String instituteMasterName) {
		this.instituteMasterName = instituteMasterName;
	}

	public String getInstituteBranchName() {
		return instituteBranchName;
	}
	public void setInstituteBranchName(String instituteBranchName) {
		this.instituteBranchName = instituteBranchName;
	}

	public long getInstituteMasterId() {
		return instituteMasterId;
	}
	public void setInstituteMasterId(long instituteMasterId) {
		this.instituteMasterId = instituteMasterId;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OnlineMethod [isOnlinePayment=");
		builder.append(isOnlinePayment);
		builder.append(", instituteMasterName=");
		builder.append(instituteMasterName);
		builder.append(", instituteBranchName=");
		builder.append(instituteBranchName);
		builder.append(", instituteMasterId=");
		builder.append(instituteMasterId);
		builder.append(", txnid=");
		builder.append(txnid);
		builder.append(",");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
}