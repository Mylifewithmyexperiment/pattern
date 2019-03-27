package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class InvoiceReference implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String invoiceNumber;
	private Long amount;
	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "InvoiceReference [invoiceNumber=" + invoiceNumber + ", amount=" + amount + "]";
	}
	
	
}
