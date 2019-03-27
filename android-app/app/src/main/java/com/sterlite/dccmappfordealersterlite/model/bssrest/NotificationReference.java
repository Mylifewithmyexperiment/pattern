package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class NotificationReference implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String type;
	private String value;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "NotificationReference [type=" + type + ", value=" + value + "]";
	}
	

}
