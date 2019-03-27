package com.sterlite.dccmappfordealersterlite.model.dto.addProductToCart;

import java.io.Serializable;

public  class CartModificationWsDTO  implements Serializable 
{

 	/** Default serialVersionUID value. */
 
 	private static final long serialVersionUID = 1L;

	/** <i>Generated property</i> for <code>CartModificationWsDTO.statusCode</code> property defined at extension <code>commercewebservicescommons</code>. */

    private String statusCode;

	/** <i>Generated property</i> for <code>CartModificationWsDTO.quantityAdded</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Long quantityAdded;

	/** <i>Generated property</i> for <code>CartModificationWsDTO.quantity</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Long quantity;

	/** <i>Generated property</i> for <code>CartModificationWsDTO.entry</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private OrderEntryWsDTO entry;

	/** <i>Generated property</i> for <code>CartModificationWsDTO.deliveryModeChanged</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Boolean deliveryModeChanged;

	/** <i>Generated property</i> for <code>CartModificationWsDTO.statusMessage</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String statusMessage;
	
	public CartModificationWsDTO()
	{
		// default constructor
	}
	
		
	
	public void setStatusCode(final String statusCode)
	{
		this.statusCode = statusCode;
	}

		
	
	public String getStatusCode() 
	{
		return statusCode;
	}
	
		
	
	public void setQuantityAdded(final Long quantityAdded)
	{
		this.quantityAdded = quantityAdded;
	}

		
	
	public Long getQuantityAdded() 
	{
		return quantityAdded;
	}
	
		
	
	public void setQuantity(final Long quantity)
	{
		this.quantity = quantity;
	}

		
	
	public Long getQuantity() 
	{
		return quantity;
	}
	
		
	
	public void setEntry(final OrderEntryWsDTO entry)
	{
		this.entry = entry;
	}

		
	
	public OrderEntryWsDTO getEntry() 
	{
		return entry;
	}
	
		
	
	public void setDeliveryModeChanged(final Boolean deliveryModeChanged)
	{
		this.deliveryModeChanged = deliveryModeChanged;
	}

		
	
	public Boolean getDeliveryModeChanged() 
	{
		return deliveryModeChanged;
	}
	
		
	
	public void setStatusMessage(final String statusMessage)
	{
		this.statusMessage = statusMessage;
	}

		
	
	public String getStatusMessage() 
	{
		return statusMessage;
	}
	


}