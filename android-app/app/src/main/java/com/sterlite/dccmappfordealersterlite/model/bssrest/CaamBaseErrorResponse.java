package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

public class CaamBaseErrorResponse implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private List<Long> responseCodeList;
	private List<String> responseMessageList;
	
	public List<Long> getResponseCodeList() {
		return responseCodeList;
	}
	public void setResponseCodeList(List<Long> responseCodeList) {
		this.responseCodeList = responseCodeList;
	}
	
	public List<String> getResponseMessageList() {
		return responseMessageList;
	}
	public void setResponseMessageList(List<String> responseMessageList) {
		this.responseMessageList = responseMessageList;
	}
	
	@Override
	public String toString() {
		return "CaamBaseErrorResponse [responseCodeList=" + responseCodeList + ", responseMessageList="
				+ responseMessageList + "]";
	}
}
