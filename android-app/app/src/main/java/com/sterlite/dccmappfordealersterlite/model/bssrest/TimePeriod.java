package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class TimePeriod implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String startDateTime;
	private String endDateTime;
	
	public String getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}
	public String getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}
	@Override
	public String toString() {
		return "TimePeriod [startDateTime=" + startDateTime + ", endDateTime=" + endDateTime + "]";
	}

	
}
