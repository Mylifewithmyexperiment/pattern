/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.sterlite.dccmappfordealersterlite.model.bssrest.PaymentHistory;

import java.io.Serializable;

public class OnlineMethodData implements Serializable {
	private String paymentMode;
	private String instituteBranchName;
	private long instituteMasterId;
	private String instituteMasterName;

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPaymentMode() {
		return this.paymentMode;
	}

	public void setInstituteBranchName(String instituteBranchName) {
		this.instituteBranchName = instituteBranchName;
	}

	public String getInstituteBranchName() {
		return this.instituteBranchName;
	}

	public void setInstituteMasterId(long instituteMasterId) {
		this.instituteMasterId = instituteMasterId;
	}

	public long getInstituteMasterId() {
		return this.instituteMasterId;
	}

	public void setInstituteMasterName(String instituteMasterName) {
		this.instituteMasterName = instituteMasterName;
	}

	public String getInstituteMasterName() {
		return this.instituteMasterName;
	}
}