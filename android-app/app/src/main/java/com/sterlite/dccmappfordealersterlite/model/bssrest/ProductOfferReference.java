package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class ProductOfferReference  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String productId;
	private String productName;
	private String category;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "ProductOfferReference [productId=" + productId + ", productName=" + productName + ", category="
				+ category + "]";
	}
	
	
}
