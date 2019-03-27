package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class PricingDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String chargeId;
	private String chargeName;
	private String chargeType;
	private String billingType;
	private Long quantity;
	private Long price;
	private Long totalAmount;
	
	private BillingStatus billingStatus;
	private Boolean proration;
	private String suppression;
	
	private TimePeriod validfor;

	public String getChargeId() {
		return chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

	public String getChargeName() {
		return chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getBillingType() {
		return billingType;
	}

	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BillingStatus getBillingStatus() {
		return billingStatus;
	}

	public void setBillingStatus(BillingStatus billingStatus) {
		this.billingStatus = billingStatus;
	}

	public String getSuppression() {
		return suppression;
	}

	public void setSuppression(String suppression) {
		this.suppression = suppression;
	}

	public Boolean getProration() {
		return proration;
	}

	public void setProration(Boolean proration) {
		this.proration = proration;
	}

	public TimePeriod getValidfor() {
		return validfor;
	}

	public void setValidfor(TimePeriod validfor) {
		this.validfor = validfor;
	}

	@Override
	public String toString() {
		return "PricingDetail [chargeId=" + chargeId + ", chargeName=" + chargeName + ", chargeType=" + chargeType
				+ ", billingType=" + billingType + ", quantity=" + quantity + ", price=" + price + ", totalAmount="
				+ totalAmount + ", billingStatus=" + billingStatus + ", proration=" + proration + ", suppression="
				+ suppression + ", validfor=" + validfor + "]";
	}

	
}
