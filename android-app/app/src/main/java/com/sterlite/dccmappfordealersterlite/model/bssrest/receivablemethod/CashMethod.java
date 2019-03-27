package com.sterlite.dccmappfordealersterlite.model.bssrest.receivablemethod;

import java.io.Serializable;


/**
 * @author amit.patel
 * @version 1.0
 * Created on September 23, 2016
 */

public class CashMethod extends BaseReceivableMethod  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected boolean isOnlinePayment = true;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CashMethod [isOnlinePayment=");
		builder.append(isOnlinePayment);
		builder.append(",");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	

}