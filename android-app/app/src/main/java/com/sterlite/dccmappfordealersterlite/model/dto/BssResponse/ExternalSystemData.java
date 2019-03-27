package com.sterlite.dccmappfordealersterlite.model.dto.BssResponse;

import java.io.Serializable;

public  class ExternalSystemData  implements Serializable 
{

 	/** Default serialVersionUID value. */
 
 	private static final long serialVersionUID = 1L;

	/** <i>Generated property</i> for <code>ExternalSystemData.externalSystemId</code> property defined at extension <code>telcofacades</code>. */

    private String externalSystemId;

	/** <i>Generated property</i> for <code>ExternalSystemData.planName</code> property defined at extension <code>telcofacades</code>. */
		
	private String planName;

	/** <i>Generated property</i> for <code>ExternalSystemData.subscriptionOfferId</code> property defined at extension <code>telcofacades</code>. */
		
	private String subscriptionOfferId;

	/** <i>Generated property</i> for <code>ExternalSystemData.responseCode</code> property defined at extension <code>telcofacades</code>. */
		
	private String responseCode;

	/** <i>Generated property</i> for <code>ExternalSystemData.responseMessage</code> property defined at extension <code>telcofacades</code>. */
		
	private String responseMessage;
	
	public ExternalSystemData()
	{
		// default constructor
	}
	
		
	
	public void setExternalSystemId(final String externalSystemId)
	{
		this.externalSystemId = externalSystemId;
	}

		
	
	public String getExternalSystemId() 
	{
		return externalSystemId;
	}
	
		
	
	public void setPlanName(final String planName)
	{
		this.planName = planName;
	}

		
	
	public String getPlanName() 
	{
		return planName;
	}
	
		
	
	public void setSubscriptionOfferId(final String subscriptionOfferId)
	{
		this.subscriptionOfferId = subscriptionOfferId;
	}

		
	
	public String getSubscriptionOfferId() 
	{
		return subscriptionOfferId;
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
	


}
