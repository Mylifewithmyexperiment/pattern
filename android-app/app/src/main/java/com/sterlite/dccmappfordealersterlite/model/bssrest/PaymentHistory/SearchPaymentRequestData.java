/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.sterlite.dccmappfordealersterlite.model.bssrest.PaymentHistory;

import java.io.Serializable;
import java.util.Date;

public class SearchPaymentRequestData implements Serializable {
	private String billingAccountNumber;
	private Date fromDate;
	private Date toDate;
	private String paymentMode;
	private String paymentNumber;
	private String tenantId;

	public void setBillingAccountNumber(String billingAccountNumber) {
		this.billingAccountNumber = billingAccountNumber;
	}

	public String getBillingAccountNumber() {
		return this.billingAccountNumber;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getFromDate() {
		return this.fromDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getToDate() {
		return this.toDate;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPaymentMode() {
		return this.paymentMode;
	}

	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	public String getPaymentNumber() {
		return this.paymentNumber;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantId() {
		return this.tenantId;
	}
}