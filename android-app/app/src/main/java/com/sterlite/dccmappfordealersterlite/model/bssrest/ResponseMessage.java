package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

public class ResponseMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<ResponseMessageDetail> responseMessageDetails;
	private String detail;

	public ResponseMessage() {}
	
	public ResponseMessage(List<ResponseMessageDetail> responseMessage) {
		this.responseMessageDetails = responseMessage;
	}
	
	public ResponseMessage(List<ResponseMessageDetail> responseMessageDetails , String detail){
		this.responseMessageDetails = responseMessageDetails;
		this.detail = detail;
	}

	public List<ResponseMessageDetail> getResponseMessageDetails() {
		return responseMessageDetails;
	}

	public void setResponseMessageDetails(List<ResponseMessageDetail> responseMessageDetails) {
		this.responseMessageDetails = responseMessageDetails;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "ResponseMessage [responseMessageDetails=" + responseMessageDetails + ", detail=" + detail + "]";
	}
	
	
}
