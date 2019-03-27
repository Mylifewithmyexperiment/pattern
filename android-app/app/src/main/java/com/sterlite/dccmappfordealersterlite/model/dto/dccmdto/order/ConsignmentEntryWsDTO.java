/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN!
 * --- Generated at 7 Sep, 2018 6:27:19 PM
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

import java.io.Serializable;

public  class ConsignmentEntryWsDTO  implements Serializable 
{

 	/** Default serialVersionUID value. */
 
 	private static final long serialVersionUID = 1L;

	/** <i>Generated property</i> for <code>ConsignmentEntryWsDTO.orderEntry</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private OrderEntryWsDTO orderEntry;

	/** <i>Generated property</i> for <code>ConsignmentEntryWsDTO.quantity</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Long quantity;

	/** <i>Generated property</i> for <code>ConsignmentEntryWsDTO.shippedQuantity</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Long shippedQuantity;

	/** <i>Generated property</i> for <code>ConsignmentEntryWsDTO.quantityDeclined</code> property defined at extension <code>warehousingwebservices</code>. */
		
	private Long quantityDeclined;

	/** <i>Generated property</i> for <code>ConsignmentEntryWsDTO.quantityPending</code> property defined at extension <code>warehousingwebservices</code>. */
		
	private Long quantityPending;

	/** <i>Generated property</i> for <code>ConsignmentEntryWsDTO.quantityShipped</code> property defined at extension <code>warehousingwebservices</code>. */
		
	private Long quantityShipped;
	
	public ConsignmentEntryWsDTO()
	{
		// default constructor
	}
	
		
	
	public void setOrderEntry(final OrderEntryWsDTO orderEntry)
	{
		this.orderEntry = orderEntry;
	}

		
	
	public OrderEntryWsDTO getOrderEntry() 
	{
		return orderEntry;
	}
	
		
	
	public void setQuantity(final Long quantity)
	{
		this.quantity = quantity;
	}

		
	
	public Long getQuantity() 
	{
		return quantity;
	}
	
		
	
	public void setShippedQuantity(final Long shippedQuantity)
	{
		this.shippedQuantity = shippedQuantity;
	}

		
	
	public Long getShippedQuantity() 
	{
		return shippedQuantity;
	}
	
		
	
	public void setQuantityDeclined(final Long quantityDeclined)
	{
		this.quantityDeclined = quantityDeclined;
	}

		
	
	public Long getQuantityDeclined() 
	{
		return quantityDeclined;
	}
	
		
	
	public void setQuantityPending(final Long quantityPending)
	{
		this.quantityPending = quantityPending;
	}

		
	
	public Long getQuantityPending() 
	{
		return quantityPending;
	}
	
		
	
	public void setQuantityShipped(final Long quantityShipped)
	{
		this.quantityShipped = quantityShipped;
	}

		
	
	public Long getQuantityShipped() 
	{
		return quantityShipped;
	}
	


}
