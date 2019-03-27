package com.sterlite.dccmappfordealersterlite.model.dto.searchproducts;

import java.io.Serializable;

public  class StockWsDTO  implements Serializable 
{

 	/** Default serialVersionUID value. */
 
 	private static final long serialVersionUID = 1L;

	/** <i>Generated property</i> for <code>StockWsDTO.stockLevelStatus</code> property defined at extension <code>commercewebservicescommons</code>. */

    private String stockLevelStatus;

	/** <i>Generated property</i> for <code>StockWsDTO.stockLevel</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Long stockLevel;
	
	public StockWsDTO()
	{
		// default constructor
	}
	
		
	
	public void setStockLevelStatus(final String stockLevelStatus)
	{
		this.stockLevelStatus = stockLevelStatus;
	}

		
	
	public String getStockLevelStatus() 
	{
		return stockLevelStatus;
	}
	
		
	
	public void setStockLevel(final Long stockLevel)
	{
		this.stockLevel = stockLevel;
	}

		
	
	public Long getStockLevel() 
	{
		return stockLevel;
	}
	


}
