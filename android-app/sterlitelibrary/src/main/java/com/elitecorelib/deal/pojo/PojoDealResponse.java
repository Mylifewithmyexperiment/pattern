package com.elitecorelib.deal.pojo;

import java.io.Serializable;
import java.util.List;

public class PojoDealResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8457556229058895569L;
	private String responseMessage;
	private int responseCode;
	private String requestType;
	private List<PojoDeal> responseData;

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public List<PojoDeal> getResponseData() {
		return responseData;
	}

	public void setResponseData(List<PojoDeal> responseData) {
		this.responseData = responseData;
	}

}
