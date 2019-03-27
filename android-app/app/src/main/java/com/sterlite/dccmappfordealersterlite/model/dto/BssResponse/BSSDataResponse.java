package com.sterlite.dccmappfordealersterlite.model.dto.BssResponse;

import java.io.Serializable;
import java.util.List;

public  class BSSDataResponse  implements Serializable 
{

 	/** Default serialVersionUID value. */
 
 	private static final long serialVersionUID = 1L;

	/** <i>Generated property</i> for <code>BSSDataResponse.responseCode</code> property defined at extension <code>telcofacades</code>. */

    private String responseCode;

	/** <i>Generated property</i> for <code>BSSDataResponse.responseMessage</code> property defined at extension <code>telcofacades</code>. */
		
	private String responseMessage;

	/** <i>Generated property</i> for <code>BSSDataResponse.accountNumber</code> property defined at extension <code>telcofacades</code>. */
		
	private String accountNumber;

	/** <i>Generated property</i> for <code>BSSDataResponse.responseObject</code> property defined at extension <code>telcofacades</code>. */
		
	private Object responseObject;

	/** <i>Generated property</i> for <code>BSSDataResponse.externalSystemDataList</code> property defined at extension <code>telcofacades</code>. */
		
	private List<ExternalSystemData> externalSystemDataList;
	
	public BSSDataResponse()
	{
		// default constructor
	}
	
		
	
	public void setResponseCode(final String responseCode)
	{
		this.responseCode = responseCode;
	}

		
	
	public String getResponseCode() 
	{
		return responseCode;
	}
	
		
	
	public void setResponseMessage(final String responseMessage)
	{
		this.responseMessage = responseMessage;
	}

		
	
	public String getResponseMessage() 
	{
		return responseMessage;
	}
	
		
	
	public void setAccountNumber(final String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

		
	
	public String getAccountNumber() 
	{
		return accountNumber;
	}
	
		
	
	public void setResponseObject(final Object responseObject)
	{
		this.responseObject = responseObject;
	}

		
	
	public Object getResponseObject() 
	{
		return responseObject;
	}
	
		
	
	public void setExternalSystemDataList(final List<ExternalSystemData> externalSystemDataList)
	{
		this.externalSystemDataList = externalSystemDataList;
	}

		
	
	public List<ExternalSystemData> getExternalSystemDataList() 
	{
		return externalSystemDataList;
	}


	@Override
	public String toString() {
		return "BSSDataResponse{" +
				"responseCode='" + responseCode + '\'' +
				", responseMessage='" + responseMessage + '\'' +
				", accountNumber='" + accountNumber + '\'' +
				", responseObject=" + responseObject +
				", externalSystemDataList=" + externalSystemDataList +
				'}';
	}
}
