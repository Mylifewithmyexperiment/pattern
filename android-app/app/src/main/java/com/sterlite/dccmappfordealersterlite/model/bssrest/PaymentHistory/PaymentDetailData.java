/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.sterlite.dccmappfordealersterlite.model.bssrest.PaymentHistory;

import java.io.Serializable;

public class PaymentDetailData implements Serializable {
	private String currencyName;
	private String externalSystemPaymentNumber;
	private String transactionId;
	private double totalPyamentAmount;
	private double totalUnroundedAmount;
	private String paymentStatus;
	private String transactionPaymentStatus;
	private long paymentDate;
	private String formattedPaymentDate;
	private String reason;
	private String sourceChannel;
	private long billDate;
	private String formattedBillDate;
	private OnlineMethodData onlineMethod;

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCurrencyName() {
		return this.currencyName;
	}

	public void setExternalSystemPaymentNumber(
			String externalSystemPaymentNumber) {
		this.externalSystemPaymentNumber = externalSystemPaymentNumber;
	}

	public String getExternalSystemPaymentNumber() {
		return this.externalSystemPaymentNumber;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionId() {
		return this.transactionId;
	}

	public void setTotalPyamentAmount(double totalPyamentAmount) {
		this.totalPyamentAmount = totalPyamentAmount;
	}

	public double getTotalPyamentAmount() {
		return this.totalPyamentAmount;
	}

	public void setTotalUnroundedAmount(double totalUnroundedAmount) {
		this.totalUnroundedAmount = totalUnroundedAmount;
	}

	public double getTotalUnroundedAmount() {
		return this.totalUnroundedAmount;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setTransactionPaymentStatus(String transactionPaymentStatus) {
		this.transactionPaymentStatus = transactionPaymentStatus;
	}

	public String getTransactionPaymentStatus() {
		return this.transactionPaymentStatus;
	}

	public void setPaymentDate(long paymentDate) {
		this.paymentDate = paymentDate;
	}

	public long getPaymentDate() {
		return this.paymentDate;
	}

	public void setFormattedPaymentDate(String formattedPaymentDate) {
		this.formattedPaymentDate = formattedPaymentDate;
	}

	public String getFormattedPaymentDate() {
		return this.formattedPaymentDate;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return this.reason;
	}

	public void setSourceChannel(String sourceChannel) {
		this.sourceChannel = sourceChannel;
	}

	public String getSourceChannel() {
		return this.sourceChannel;
	}

	public void setBillDate(long billDate) {
		this.billDate = billDate;
	}

	public long getBillDate() {
		return this.billDate;
	}

	public void setFormattedBillDate(String formattedBillDate) {
		this.formattedBillDate = formattedBillDate;
	}

	public String getFormattedBillDate() {
		return this.formattedBillDate;
	}

	public void setOnlineMethod(OnlineMethodData onlineMethod) {
		this.onlineMethod = onlineMethod;
	}

	public OnlineMethodData getOnlineMethod() {
		return this.onlineMethod;
	}
}