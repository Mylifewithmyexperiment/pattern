package com.sterlite.dccmappfordealersterlite.model.bssrest;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;

public class AuthenticateRequestDetail extends BaseRequest {

	private static final long serialVersionUID = 1L;
	private String userName;
	private String passsword;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPasssword() {
		return passsword;
	}
	public void setPasssword(String passsword) {
		this.passsword = passsword;
	}	



}
