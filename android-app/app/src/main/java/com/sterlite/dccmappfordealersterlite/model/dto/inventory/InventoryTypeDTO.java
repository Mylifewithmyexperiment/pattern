package com.sterlite.dccmappfordealersterlite.model.dto.inventory;

import java.io.Serializable;

public  class InventoryTypeDTO  implements Serializable 
{

 	/** Default serialVersionUID value. */
 
 	private static final long serialVersionUID = 1L;

	/** <i>Generated property</i> for <code>InventoryTypeDTO.tenantId</code> property defined at extension <code>vfoccaddon</code>. */

    private String tenantId;

	/** <i>Generated property</i> for <code>InventoryTypeDTO.inventoryTypeId</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String inventoryTypeId;

	/** <i>Generated property</i> for <code>InventoryTypeDTO.inventoryTypeName</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String inventoryTypeName;

	/** <i>Generated property</i> for <code>InventoryTypeDTO.inventoryGroup</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String inventoryGroup;
	
	public InventoryTypeDTO()
	{
		// default constructor
	}
	
		
	
	public void setTenantId(final String tenantId)
	{
		this.tenantId = tenantId;
	}

		
	
	public String getTenantId() 
	{
		return tenantId;
	}
	
		
	
	public void setInventoryTypeId(final String inventoryTypeId)
	{
		this.inventoryTypeId = inventoryTypeId;
	}

		
	
	public String getInventoryTypeId() 
	{
		return inventoryTypeId;
	}
	
		
	
	public void setInventoryTypeName(final String inventoryTypeName)
	{
		this.inventoryTypeName = inventoryTypeName;
	}

		
	
	public String getInventoryTypeName() 
	{
		return inventoryTypeName;
	}
	
		
	
	public void setInventoryGroup(final String inventoryGroup)
	{
		this.inventoryGroup = inventoryGroup;
	}

		
	
	public String getInventoryGroup() 
	{
		return inventoryGroup;
	}

	@Override
	public String toString() {
		return "InventoryTypeDTO{" +
				"tenantId='" + tenantId + '\'' +
				", inventoryTypeId='" + inventoryTypeId + '\'' +
				", inventoryTypeName='" + inventoryTypeName + '\'' +
				", inventoryGroup='" + inventoryGroup + '\'' +
				'}';
	}
}