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
package com.sterlite.dccmappfordealersterlite.model.dto.dccmdto.product;

import java.io.Serializable;
import java.util.Collection;

public  class VariantValueCategoryWsDTO  implements Serializable 
{

 	/** Default serialVersionUID value. */
 
 	private static final long serialVersionUID = 1L;

	/** <i>Generated property</i> for <code>VariantValueCategoryWsDTO.name</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String name;

	/** <i>Generated property</i> for <code>VariantValueCategoryWsDTO.sequence</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Integer sequence;

	/** <i>Generated property</i> for <code>VariantValueCategoryWsDTO.superCategories</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Collection<VariantCategoryWsDTO> superCategories;
	
	public VariantValueCategoryWsDTO()
	{
		// default constructor
	}
	
		
	
	public void setName(final String name)
	{
		this.name = name;
	}

		
	
	public String getName() 
	{
		return name;
	}
	
		
	
	public void setSequence(final Integer sequence)
	{
		this.sequence = sequence;
	}

		
	
	public Integer getSequence() 
	{
		return sequence;
	}
	
		
	
	public void setSuperCategories(final Collection<VariantCategoryWsDTO> superCategories)
	{
		this.superCategories = superCategories;
	}

		
	
	public Collection<VariantCategoryWsDTO> getSuperCategories() 
	{
		return superCategories;
	}
	


}
