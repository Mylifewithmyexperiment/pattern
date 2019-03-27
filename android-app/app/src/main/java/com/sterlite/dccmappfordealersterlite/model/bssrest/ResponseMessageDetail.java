package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;


public class ResponseMessageDetail  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	 private Long code;
	 private String message;
	    
	 public ResponseMessageDetail(Long code, String message) {
		 this.code=code;
	     this.message=message;
	 }

	 public ResponseMessageDetail() {}
	 
	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ResponseMessageDetail [code=" + code + ", message=" + message + "]";
	}
}
