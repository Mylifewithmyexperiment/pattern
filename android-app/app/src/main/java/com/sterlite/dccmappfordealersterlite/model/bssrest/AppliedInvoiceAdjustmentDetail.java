package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

public class AppliedInvoiceAdjustmentDetail implements Serializable{

	private final static long serialVersionUID = 1L;
	
	private String invoiceNumber;
	private String paymentNumber;
	private List<String> refundCreditAdjustments;

	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getPaymentNumber() {
		return paymentNumber;
	}
	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
	}
	public List<String> getRefundCreditDocuments() {
		return refundCreditAdjustments;
	}
	public void setRefundCreditDocuments(List<String> refundCreditDocuments) {
		this.refundCreditAdjustments = refundCreditDocuments;
	}
	@Override
	public String toString() {
		return "AppliedInvoiceAdjustmentDetail [invoiceNumber=" + invoiceNumber + ", paymentNumber=" + paymentNumber
				+ ", refundCreditDocuments=" + refundCreditAdjustments + "]";
	}
	
}
