/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN!
 * --- Generated at 7 Sep, 2018 6:27:18 PM
 * ----------------------------------------------------------------
 *
 * [y] hybris Platform
 * 
 * Copyright (c) 2000-2016 SAP SE
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of SAP 
 * Hybris ("Confidential Information"). You shall not disclose such 
 * Confidential Information and shall use it only in accordance with the 
 * terms of the license agreement you entered into with SAP Hybris.
 */
package com.sterlite.dccmappfordealersterlite.model.dto.dccmdto.order;

import com.sterlite.dccmappfordealersterlite.model.dto.dccmdto.user.AddressWsDTO;

public  class DeliveryOrderEntryGroupWsDTO extends OrderEntryGroupWsDTO 
{

 

	/** <i>Generated property</i> for <code>DeliveryOrderEntryGroupWsDTO.deliveryAddress</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private AddressWsDTO deliveryAddress;
	
	public DeliveryOrderEntryGroupWsDTO()
	{
		// default constructor
	}
	
		
	
	public void setDeliveryAddress(final AddressWsDTO deliveryAddress)
	{
		this.deliveryAddress = deliveryAddress;
	}

		
	
	public AddressWsDTO getDeliveryAddress() 
	{
		return deliveryAddress;
	}
	


}
