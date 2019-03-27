package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class FNFMemberDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected Long subscriberIdentifier;
	protected TimePeriod validFor;
	
	public Long getSubscriberIdentifier() {
		return subscriberIdentifier;
	}
	public void setSubscriberIdentifier(Long subscriberIdentifier) {
		this.subscriberIdentifier = subscriberIdentifier;
	}
	public TimePeriod getValidFor() {
		return validFor;
	}
	public void setValidFor(TimePeriod validFor) {
		this.validFor = validFor;
	}
	@Override
	public String toString() {
		return "FNFMemberDetail [subscriberIdentifier=" + subscriberIdentifier + ", validFor=" + validFor + "]";
	}
	
	
}
