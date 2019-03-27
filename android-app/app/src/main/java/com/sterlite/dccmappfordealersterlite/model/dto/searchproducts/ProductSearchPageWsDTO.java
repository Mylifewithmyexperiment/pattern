package com.sterlite.dccmappfordealersterlite.model.dto.searchproducts;

import java.io.Serializable;
import java.util.List;

/**
 * POJO containing the result page for product search.
 */
public  class ProductSearchPageWsDTO  implements Serializable 
{
 	private static final long serialVersionUID = 1L;

	private String categoryCode;

	private List<ProductWsDTO> products;

	
	public ProductSearchPageWsDTO()
	{
		// default constructor
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public List<ProductWsDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductWsDTO> products) {
		this.products = products;
	}
}