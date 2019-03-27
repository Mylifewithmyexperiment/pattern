package com.sterlite.dccmappfordealersterlite.model.dto.searchproducts;

import java.io.Serializable;

public  class ProductWsDTO  implements Serializable
{

 	/** Default serialVersionUID value. */

 	private static final long serialVersionUID = 1L;


    private String code;
	private String name;
	private StockWsDTO stock;
	private Boolean availableForPickup;
	private PriceWsDTO price;
	private Integer numberOfReviews;
	private PriceWsDTO oneTimeProductPrice;

	public ProductWsDTO()
	{
		// default constructor
	}

	public PriceWsDTO getOneTimeProductPrice() {
		return oneTimeProductPrice;
	}

	public void setOneTimeProductPrice(PriceWsDTO oneTimeProductPrice) {
		this.oneTimeProductPrice = oneTimeProductPrice;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StockWsDTO getStock() {
		return stock;
	}

	public void setStock(StockWsDTO stock) {
		this.stock = stock;
	}

	public Boolean getAvailableForPickup() {
		return availableForPickup;
	}

	public void setAvailableForPickup(Boolean availableForPickup) {
		this.availableForPickup = availableForPickup;
	}

	public PriceWsDTO getPrice() {
		return price;
	}

	public void setPrice(PriceWsDTO price) {
		this.price = price;
	}

	public Integer getNumberOfReviews() {
		return numberOfReviews;
	}

	public void setNumberOfReviews(Integer numberOfReviews) {
		this.numberOfReviews = numberOfReviews;
	}
}