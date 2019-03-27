package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;

public class UpdateDunningStatusRequestDetail extends BaseRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<String> debitDocumentNumberList;
	private String dunningStatus;

	public List<String> getDebitDocumentNumberList()
	{
		return debitDocumentNumberList;
	}

	public void setDebitDocumentNumberList(List<String> debitDocumentNumberList)
	{
		this.debitDocumentNumberList = debitDocumentNumberList;
	}

	public String getDunningStatus()
	{
		return dunningStatus;
	}

	public void setDunningStatus(String dunningStatus)
	{
		this.dunningStatus = dunningStatus;
	}

	@Override
	public String toString()
	{
		return "UpdateDunningStatusRequestDetail [debitDocumentNumber =" + debitDocumentNumberList + ", dunningStatus =" + dunningStatus + "]";
	}

}
