package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class IdentificationReference implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected String type;
	protected String identificationId;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIdentificationId() {
		return identificationId;
	}
	public void setIdentificationId(String identificationId) {
		this.identificationId = identificationId;
	}
	@Override
	public String toString() {
		return "IdentificationReference [type=" + type + ", identificationId=" + identificationId + "]";
	}
	
	
}
