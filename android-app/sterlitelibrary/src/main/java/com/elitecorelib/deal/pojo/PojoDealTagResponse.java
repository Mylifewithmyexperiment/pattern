package com.elitecorelib.deal.pojo;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;

public class PojoDealTagResponse implements Serializable{

	private static final long serialVersionUID = 3280394759140733685L;
	private String responseMessage;
	private int responseCode;
	private String requestType;
	private RealmList<PojoDealTag> responseData;
	private List<PojoDealTag> responseDataList;

	public List<PojoDealTag> getResponseDataList() {
		return responseDataList;
	}

	public void setResponseDataList(List<PojoDealTag> responseDataList) {
		this.responseDataList = responseDataList;
	}

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

	public RealmList<PojoDealTag> getResponseData() {
		return responseData;
	}

	public void setResponseData(RealmList<PojoDealTag> responseData) {
		this.responseData = responseData;
	}
}
