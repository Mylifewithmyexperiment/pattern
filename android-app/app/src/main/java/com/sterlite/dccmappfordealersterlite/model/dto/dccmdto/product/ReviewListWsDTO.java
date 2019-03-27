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
import java.util.List;

public  class ReviewListWsDTO  implements Serializable 
{

 	/** Default serialVersionUID value. */
 
 	private static final long serialVersionUID = 1L;

	/** <i>Generated property</i> for <code>ReviewListWsDTO.reviews</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private List<ReviewWsDTO> reviews;
	
	public ReviewListWsDTO()
	{
		// default constructor
	}
	
		
	
	public void setReviews(final List<ReviewWsDTO> reviews)
	{
		this.reviews = reviews;
	}

		
	
	public List<ReviewWsDTO> getReviews() 
	{
		return reviews;
	}
	


}
