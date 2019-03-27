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
package com.sterlite.dccmappfordealersterlite.model.dto.dccmdto.search.pagedata;

/**
 * POJO representation of search results pagination.
 *
 * @deprecated Since version 6.5 use de.hybris.platform.webservicescommons.dto.PaginationWsDTO instead
 */
@Deprecated
public  class PaginationWsDTO extends PageableWsDTO 
{

 

	/** The total number of pages. This is the number of pages, each of pageSize, required to display
				the totalResults.
			<br/><br/><i>Generated property</i> for <code>PaginationWsDTO.totalPages</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Integer totalPages;

	/** The total number of matched results across all pages.<br/><br/><i>Generated property</i> for <code>PaginationWsDTO.totalResults</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Long totalResults;
	
	public PaginationWsDTO()
	{
		// default constructor
	}
	
		
	
	public void setTotalPages(final Integer totalPages)
	{
		this.totalPages = totalPages;
	}

		
	
	public Integer getTotalPages() 
	{
		return totalPages;
	}
	
		
	
	public void setTotalResults(final Long totalResults)
	{
		this.totalResults = totalResults;
	}

		
	
	public Long getTotalResults() 
	{
		return totalResults;
	}
	


}
