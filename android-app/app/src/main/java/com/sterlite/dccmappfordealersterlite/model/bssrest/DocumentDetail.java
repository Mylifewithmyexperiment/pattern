package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.Date;

public class DocumentDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String documentNumber;
	private String documentCategory;
	private String documentType;
	private Date postingDate;
	private double amount;
	
	public String getDocumentNumber() {
		return documentNumber;
	}
	
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	
	public String getDocumentCategory() {
		return documentCategory;
	}
	
	public void setDocumentCategory(String documentCategory) {
		this.documentCategory = documentCategory;
	}
	
	public String getDocumentType() {
		return documentType;
	}
	
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	public Date getPostingDate() {
		return postingDate;
	}
	
	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(100);
		builder.append("DocumentDetail [documentNumber=").append(documentNumber).append(", documentCategory=")
				.append(documentCategory).append(", documentType=").append(documentType).append(", postingDate=")
				.append(postingDate).append(", amount=").append(amount).append("]");
		return builder.toString();
	}
	
}
