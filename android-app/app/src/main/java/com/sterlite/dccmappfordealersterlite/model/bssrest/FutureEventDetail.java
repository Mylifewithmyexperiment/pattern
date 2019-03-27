package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.Date;

public class FutureEventDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected String id;
	protected String accountNumber;
	protected String productSubscriptionId;
	protected String event;
	protected String subEvent;
	protected Date createDate;
	protected Date executionDate;
	protected String status;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getProductSubscriptionId() {
		return productSubscriptionId;
	}
	public void setProductSubscriptionId(String productSubscriptionId) {
		this.productSubscriptionId = productSubscriptionId;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getSubEvent() {
		return subEvent;
	}
	public void setSubEvent(String subEvent) {
		this.subEvent = subEvent;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "FutureEventDetail [id=" + id + ", accountNumber=" + accountNumber + ", productSubscriptionId="
				+ productSubscriptionId + ", event=" + event + ", subEvent=" + subEvent + ", createDate=" + createDate
				+ ", executionDate=" + executionDate + ", status=" + status + "]";
	}
	
	
}
