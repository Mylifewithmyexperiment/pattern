package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

public class ContactMedium implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private ContactMediumType type;
    private List<Medium> medium;
    
	public ContactMediumType getType() {
		return type;
	}
	public void setType(ContactMediumType type) {
		this.type = type;
	}
	public List<Medium> getMedium() {
		return medium;
	}
	public void setMedium(List<Medium> medium) {
		this.medium = medium;
	}
	@Override
	public String toString() {
		return "ContactMedium [type=" + type + ", medium=" + medium + "]";
	}
    
}
