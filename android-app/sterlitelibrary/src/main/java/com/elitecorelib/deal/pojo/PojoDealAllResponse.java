package com.elitecorelib.deal.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class PojoDealAllResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8457556229058895569L;
	private String responseMessage;
	public int responseCode;
	private String requestType;
	private RealmList<PojoDealAll> responseData;

//	public PojoDealAllResponse() {
//	}
//
//	protected PojoDealAllResponse(Parcel in) {
//		responseMessage = in.readString();
//		responseCode = in.readInt();
//		requestType = in.readString();
//		responseData = in.readArrayList();
//	}



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

	public RealmList<PojoDealAll> getResponseData() {
		return responseData;
	}

	public void setResponseData(RealmList<PojoDealAll> responseData) {
		this.responseData = responseData;
	}
}
