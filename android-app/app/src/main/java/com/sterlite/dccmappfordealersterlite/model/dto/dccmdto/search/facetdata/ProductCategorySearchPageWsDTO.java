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
package com.sterlite.dccmappfordealersterlite.model.dto.dccmdto.search.facetdata;

import com.sterlite.dccmappfordealersterlite.model.dto.dccmdto.product.CategoryWsDTO;

import java.util.List;

/**
 * POJO containing the result page for product or category search.
 */
public  class ProductCategorySearchPageWsDTO extends ProductSearchPageWsDTO 
{

 

	/** <i>Generated property</i> for <code>ProductCategorySearchPageWsDTO.subCategories</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private List<CategoryWsDTO> subCategories;
	
	public ProductCategorySearchPageWsDTO()
	{
		// default constructor
	}
	
		
	
	public void setSubCategories(final List<CategoryWsDTO> subCategories)
	{
		this.subCategories = subCategories;
	}

		
	
	public List<CategoryWsDTO> getSubCategories() 
	{
		return subCategories;
	}
	


}
