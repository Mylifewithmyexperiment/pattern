package com.sterlite.dccmappfordealersterlite.model.dto.inventory;

import java.io.Serializable;
import java.util.List;

public  class InventoryDetailWsDTO  implements Serializable
{

 	/** Default serialVersionUID value. */
 
 	private static final long serialVersionUID = 1L;

	/** <i>Generated property</i> for <code>InventoryDetailWsDTO.lstInvenotries</code> property defined at extension <code>vfoccaddon</code>. */

    private List<InventoryDetailDTO> lstInvenotries;
	
	public InventoryDetailWsDTO()
	{
		// default constructor
	}
	
		
	
	public void setLstInvenotries(final List<InventoryDetailDTO> lstInvenotries)
	{
		this.lstInvenotries = lstInvenotries;
	}

		
	
	public List<InventoryDetailDTO> getLstInvenotries() 
	{
		return lstInvenotries;
	}

	@Override
	public String toString() {
		return "InventoryDetailWsDTO{" +
				"lstInvenotries=" + lstInvenotries +
				'}';
	}
}
