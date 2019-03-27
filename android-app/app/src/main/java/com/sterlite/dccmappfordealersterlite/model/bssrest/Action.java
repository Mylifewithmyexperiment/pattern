package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;


public class Action implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected ActionAlias actionAlias;
	protected String reason;
	protected String remarks;
	protected String sourceChannel;
	
	public ActionAlias getActionAlias() {
		return actionAlias;
	}
	public void setActionAlias(ActionAlias actionAlias) {
		this.actionAlias = actionAlias;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSourceChannel() {
		return sourceChannel;
	}
	public void setSourceChannel(String sourceChannel) {
		this.sourceChannel = sourceChannel;
	}
	
	@Override
	public String toString() {
		return "Action [actionAlias=" + actionAlias + ", reason=" + reason + ", remarks=" + remarks + ", sourceChannel="
				+ sourceChannel + "]";
	}
	

}
