package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class AccountHistoryDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String historyDetailId;
	private String event;
	private String eventDate;
	private String remarks;
	private String oldValue;
	private String newValue;
	private String reason;
	private Long productSubscriptionId; //CPHId
	
	public String getHistoryDetailId() {
		return historyDetailId;
	}
	public void setHistoryDetailId(String historyDetailId) {
		this.historyDetailId = historyDetailId;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getProductSubscriptionId() {
		return productSubscriptionId;
	}
	public void setProductSubscriptionId(Long productSubscriptionId) {
		this.productSubscriptionId = productSubscriptionId;
	}
	@Override
	public String toString() {
		return "AccountHistoryDetail [historyDetailId=" + historyDetailId + ", event=" + event + ", eventDate="
				+ eventDate + ", remarks=" + remarks + ", oldValue=" + oldValue + ", newValue=" + newValue + ", reason="
				+ reason + ", productSubscriptionId=" + productSubscriptionId + "]";
	}
	
	
}
