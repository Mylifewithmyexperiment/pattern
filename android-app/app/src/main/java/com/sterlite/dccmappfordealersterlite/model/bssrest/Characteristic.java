package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class Characteristic implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Characteristic [name=" + name + ", value=" + value + "]";
	}
	
	
}
