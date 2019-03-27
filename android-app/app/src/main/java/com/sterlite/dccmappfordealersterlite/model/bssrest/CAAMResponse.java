package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseResponse;


public class CAAMResponse<T> extends BaseResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<ResponseMessageDetail> responseMessageDetails;
	private T responseObject; //NOSONAR

	public CAAMResponse() {
	}

	/**
	 * Instantiates a CAAM response.
	 *
	 * @param responseMessage the response message
	 */
	public CAAMResponse(List<ResponseMessageDetail> responseMessage) {
		this.responseMessageDetails = responseMessage;
	}

	public CAAMResponse(List<ResponseMessageDetail> responseMessageDetails, T responseObject) {
		this.responseMessageDetails = responseMessageDetails;
		this.responseObject = responseObject;
	}

	public List<ResponseMessageDetail> getResponseMessageDetails() {
		return responseMessageDetails;
	}

	public void setResponseMessageDetails(List<ResponseMessageDetail> responseMessageDetails) {
		this.responseMessageDetails = responseMessageDetails;
	}

	public T getResponseObject() {
		return responseObject;
	}

	public void setResponseObject(T responseObject) {
		this.responseObject = responseObject;
	}

	@Override
	public String toString() {
		return "CamResponse [responseMessageDetails=" + responseMessageDetails + ", responseObject=" + responseObject + ", responseCode=" + getResponseCode() + ", responseMessage=" + getResponseMessage() + ", exception=" + getException() + "]";
	}

}
