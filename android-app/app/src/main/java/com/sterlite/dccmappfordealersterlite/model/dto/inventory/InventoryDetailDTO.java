package com.sterlite.dccmappfordealersterlite.model.dto.inventory;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public  class InventoryDetailDTO  implements Serializable
{

 	/** Default serialVersionUID value. */
 
 	private static final long serialVersionUID = 1L;

	/** response code<br/><br/><i>Generated property</i> for <code>InventoryDetailDTO.responseCode</code> property defined at extension <code>vfoccaddon</code>. */

    private String responseCode;

	/** response message<br/><br/><i>Generated property</i> for <code>InventoryDetailDTO.responseMessage</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String responseMessage;

	/** <i>Generated property</i> for <code>InventoryDetailDTO.inventoryID</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String inventoryID;

	/** <i>Generated property</i> for <code>InventoryDetailDTO.inventorybatchID</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String inventorybatchID;

	/** <i>Generated property</i> for <code>InventoryDetailDTO.inventoryType</code> property defined at extension <code>vfoccaddon</code>. */
		
	private InventoryTypeDTO inventoryType;

	/** <i>Generated property</i> for <code>InventoryDetailDTO.inventoryNumber</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String inventoryNumber;

	/** <i>Generated property</i> for <code>InventoryDetailDTO.status</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String status;

	/** <i>Generated property</i> for <code>InventoryDetailDTO.InventoryOwner</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String inventoryOwner;

	/** <i>Generated property</i> for <code>InventoryDetailDTO.Warehouse</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String Warehouse;

	/** <i>Generated property</i> for <code>InventoryDetailDTO.VanityCategory</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String vanityCategory;

	/** <i>Generated property</i> for <code>InventoryDetailDTO.Statuschangedate</code> property defined at extension <code>vfoccaddon</code>. */
		
	private Date Statuschangedate;

	/** <i>Generated property</i> for <code>InventoryDetailDTO.inventoryDetailPlanCode</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String inventoryDetailPlanCode;

	/** <i>Generated property</i> for <code>InventoryDetailDTO.inventoryDetailPlanPrice</code> property defined at extension <code>vfoccaddon</code>. */
		
	private Double inventoryDetailPlanPrice;

	/** <i>Generated property</i> for <code>InventoryDetailDTO.inventoryCircle</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String inventoryCircle;

	/** <i>Generated property</i> for <code>InventoryDetailDTO.inventoryChargingPattern</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String inventoryChargingPattern;

	/** <i>Generated property</i> for <code>InventoryDetailDTO.variants</code> property defined at extension <code>vfoccaddon</code>. */
		
	private List<InventoryDetailDTO> variants;

	/** <i>Generated property</i> for <code>InventoryDetailDTO.luckyNumber</code> property defined at extension <code>vfoccaddon</code>. */
		
	private String luckyNumber;
	
	public InventoryDetailDTO()
	{
		// default constructor
	}
	
		
	
	public void setResponseCode(final String responseCode)
	{
		this.responseCode = responseCode;
	}

		
	
	public String getResponseCode() 
	{
		return responseCode;
	}
	
		
	
	public void setResponseMessage(final String responseMessage)
	{
		this.responseMessage = responseMessage;
	}

		
	
	public String getResponseMessage() 
	{
		return responseMessage;
	}
	
		
	
	public void setInventoryID(final String inventoryID)
	{
		this.inventoryID = inventoryID;
	}

		
	
	public String getInventoryID() 
	{
		return inventoryID;
	}
	
		
	
	public void setInventorybatchID(final String inventorybatchID)
	{
		this.inventorybatchID = inventorybatchID;
	}

		
	
	public String getInventorybatchID() 
	{
		return inventorybatchID;
	}
	
		
	
	public void setInventoryType(final InventoryTypeDTO inventoryType)
	{
		this.inventoryType = inventoryType;
	}

		
	
	public InventoryTypeDTO getInventoryType() 
	{
		return inventoryType;
	}
	
		
	
	public void setInventoryNumber(final String inventoryNumber)
	{
		this.inventoryNumber = inventoryNumber;
	}

		
	
	public String getInventoryNumber() 
	{
		return inventoryNumber;
	}
	
		
	
	public void setStatus(final String status)
	{
		this.status = status;
	}

		
	
	public String getStatus() 
	{
		return status;
	}
	
		
	
	public void setInventoryOwner(final String InventoryOwner)
	{
		this.inventoryOwner = InventoryOwner;
	}

		
	
	public String getInventoryOwner() 
	{
		return inventoryOwner;
	}
	
		
	
	public void setWarehouse(final String Warehouse)
	{
		this.Warehouse = Warehouse;
	}

		
	
	public String getWarehouse() 
	{
		return Warehouse;
	}
	
		
	
	public void setVanityCategory(final String vanityCategory)
	{
		this.vanityCategory = vanityCategory;
	}

		
	
	public String getVanityCategory() 
	{
		return vanityCategory;
	}
	
		
	
	public void setStatuschangedate(final Date Statuschangedate)
	{
		this.Statuschangedate = Statuschangedate;
	}

		
	
	public Date getStatuschangedate() 
	{
		return Statuschangedate;
	}
	
		
	
	public void setInventoryDetailPlanCode(final String inventoryDetailPlanCode)
	{
		this.inventoryDetailPlanCode = inventoryDetailPlanCode;
	}

		
	
	public String getInventoryDetailPlanCode() 
	{
		return inventoryDetailPlanCode;
	}
	
		
	
	public void setInventoryDetailPlanPrice(final Double inventoryDetailPlanPrice)
	{
		this.inventoryDetailPlanPrice = inventoryDetailPlanPrice;
	}

		
	
	public Double getInventoryDetailPlanPrice() 
	{
		return inventoryDetailPlanPrice;
	}
	
		
	
	public void setInventoryCircle(final String inventoryCircle)
	{
		this.inventoryCircle = inventoryCircle;
	}

		
	
	public String getInventoryCircle() 
	{
		return inventoryCircle;
	}
	
		
	
	public void setInventoryChargingPattern(final String inventoryChargingPattern)
	{
		this.inventoryChargingPattern = inventoryChargingPattern;
	}

		
	
	public String getInventoryChargingPattern() 
	{
		return inventoryChargingPattern;
	}
	
		
	
	public void setVariants(final List<InventoryDetailDTO> variants)
	{
		this.variants = variants;
	}

		
	
	public List<InventoryDetailDTO> getVariants() 
	{
		return variants;
	}
	
		
	
	public void setLuckyNumber(final String luckyNumber)
	{
		this.luckyNumber = luckyNumber;
	}

		
	
	public String getLuckyNumber() 
	{
		return luckyNumber;
	}


	@Override
	public String toString() {
		return "InventoryDetailDTO{" +
				"responseCode='" + responseCode + '\'' +
				", responseMessage='" + responseMessage + '\'' +
				", inventoryID='" + inventoryID + '\'' +
				", inventorybatchID='" + inventorybatchID + '\'' +
				", inventoryType=" + inventoryType +
				", inventoryNumber='" + inventoryNumber + '\'' +
				", status='" + status + '\'' +
				", InventoryOwner='" + inventoryOwner + '\'' +
				", Warehouse='" + Warehouse + '\'' +
				", VanityCategory='" + vanityCategory + '\'' +
				", Statuschangedate=" + Statuschangedate +
				", inventoryDetailPlanCode='" + inventoryDetailPlanCode + '\'' +
				", inventoryDetailPlanPrice=" + inventoryDetailPlanPrice +
				", inventoryCircle='" + inventoryCircle + '\'' +
				", inventoryChargingPattern='" + inventoryChargingPattern + '\'' +
				", variants=" + variants +
				", luckyNumber='" + luckyNumber + '\'' +
				'}';
	}
}