package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class ServiceDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String alias;
	private String status;
	private TimePeriod validFor;
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public TimePeriod getValidFor() {
		return validFor;
	}
	public void setValidFor(TimePeriod validFor) {
		this.validFor = validFor;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "ServiceDetail [id=" + id + ", alias=" + alias + ", status=" + status + ", validFor=" + validFor + "]";
	}
	
}
