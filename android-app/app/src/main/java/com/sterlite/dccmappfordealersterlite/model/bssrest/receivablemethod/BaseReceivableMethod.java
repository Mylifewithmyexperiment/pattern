package com.sterlite.dccmappfordealersterlite.model.bssrest.receivablemethod;

import java.io.Serializable;


/**
 * @author amit.patel
 * @version 1.0
 * Created on September 23, 2016
 */


public class BaseReceivableMethod implements Serializable{
	private static final long serialVersionUID = 1L;
	protected String paymentMode;
	

	public BaseReceivableMethod(){
//		Default Constructor
	}
	
	public String getPaymentMode(){
		return paymentMode;
	}

	/**
	 * 
	 * @param strPaymentMode
	 */
	public void setPaymentMode(String strPaymentMode){
		this.paymentMode = strPaymentMode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BaseReceivableMethod [ paymentMode=");
		builder.append(paymentMode);
		builder.append("]");
		return builder.toString();
	}
	
}