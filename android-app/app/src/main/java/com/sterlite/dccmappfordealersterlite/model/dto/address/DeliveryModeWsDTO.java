package com.sterlite.dccmappfordealersterlite.model.dto.address;

import java.io.Serializable;

import com.sterlite.dccmappfordealersterlite.model.dto.price.PriceWsDTO;

public  class DeliveryModeWsDTO  implements Serializable 
{

 	/** Default serialVersionUID value. */
 
 	private static final long serialVersionUID = 1L;

	/** <i>Generated property</i> for <code>DeliveryModeWsDTO.code</code> property defined at extension <code>commercewebservicescommons</code>. */

    private String code;

	/** <i>Generated property</i> for <code>DeliveryModeWsDTO.name</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String name;

	/** <i>Generated property</i> for <code>DeliveryModeWsDTO.description</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String description;

	/** <i>Generated property</i> for <code>DeliveryModeWsDTO.deliveryCost</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private PriceWsDTO deliveryCost;
	
	public DeliveryModeWsDTO()
	{
		// default constructor
	}
	
		
	
	public void setCode(final String code)
	{
		this.code = code;
	}

		
	
	public String getCode() 
	{
		return code;
	}
	
		
	
	public void setName(final String name)
	{
		this.name = name;
	}

		
	
	public String getName() 
	{
		return name;
	}
	
		
	
	public void setDescription(final String description)
	{
		this.description = description;
	}

		
	
	public String getDescription() 
	{
		return description;
	}
	
		
	
	public void setDeliveryCost(final PriceWsDTO deliveryCost)
	{
		this.deliveryCost = deliveryCost;
	}

		
	
	public PriceWsDTO getDeliveryCost() 
	{
		return deliveryCost;
	}
	


}