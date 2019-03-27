package com.sterlite.dccmappfordealersterlite.model.bssrest.receivablemethod;


import java.io.Serializable;
import java.util.List;


/**
 * @author amit.patel
 * @version 1.0
 * Created on September 23, 2016
 */

public class VoucherMethod extends BaseReceivableMethod implements Serializable
{
	private static final long serialVersionUID = 1L;
	private List<String> listVoucherNumber;
		
	public List<String> getListVoucherNumber() {
		return listVoucherNumber;
	}
	public void setListVoucherNumber(List<String> listVoucherNumber) {
		this.listVoucherNumber = listVoucherNumber;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VoucherMethod [voucherMethod=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
}
