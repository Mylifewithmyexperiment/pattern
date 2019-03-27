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
package com.sterlite.dccmappfordealersterlite.model.dto.dccmdto.product;

import java.io.Serializable;
import com.sterlite.dccmappfordealersterlite.model.dto.dccmdto.order.PromotionOrderEntryConsumedWsDTO;

import java.util.List;

public  class PromotionResultWsDTO  implements Serializable 
{

 	/** Default serialVersionUID value. */
 
 	private static final long serialVersionUID = 1L;

	/** <i>Generated property</i> for <code>PromotionResultWsDTO.description</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String description;

	/** <i>Generated property</i> for <code>PromotionResultWsDTO.promotion</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private PromotionWsDTO promotion;

	/** <i>Generated property</i> for <code>PromotionResultWsDTO.consumedEntries</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private List<PromotionOrderEntryConsumedWsDTO> consumedEntries;
	
	public PromotionResultWsDTO()
	{
		// default constructor
	}
	
		
	
	public void setDescription(final String description)
	{
		this.description = description;
	}

		
	
	public String getDescription() 
	{
		return description;
	}
	
		
	
	public void setPromotion(final PromotionWsDTO promotion)
	{
		this.promotion = promotion;
	}

		
	
	public PromotionWsDTO getPromotion() 
	{
		return promotion;
	}
	
		
	
	public void setConsumedEntries(final List<PromotionOrderEntryConsumedWsDTO> consumedEntries)
	{
		this.consumedEntries = consumedEntries;
	}

		
	
	public List<PromotionOrderEntryConsumedWsDTO> getConsumedEntries() 
	{
		return consumedEntries;
	}
	


}
