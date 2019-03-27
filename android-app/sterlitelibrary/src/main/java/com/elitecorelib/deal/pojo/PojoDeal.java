package com.elitecorelib.deal.pojo;

import java.io.Serializable;


public class PojoDeal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long dealId;
	private String dealName;
	private String dealStatus;
	private String dealImagepath;
	private String dealThumbail_imagepath;
	private String dealHeadline;
	private Long dealcost_for_merchant;
	private Long price;
	private String offer;
	private Long merchantPrice;
	private Long operatorcost_per_voucher;
	private String reedmptionDetails;
	private Long voucherLimits;
	private String offerDescription;
	private String dealDescription;
	private String dealStartdate;
	private String dealStopdate;
	private String isVoucher;
	private String redirectURL;

	public String getIsVoucher() {
		return isVoucher;
	}

	public void setIsVoucher(String isVoucher) {
		this.isVoucher = isVoucher;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}


	public String getDealDescription() {
		return dealDescription;
	}

	public void setDealDescription(String dealDescription) {
		this.dealDescription = dealDescription;
	}

	public Long getDealId() {
		return dealId;
	}

	public void setDealID(Long dealId) {
		this.dealId = dealId;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	public String getDealImagepath() {
		return dealImagepath;
	}

	public void setDealImagepath(String dealImagepath) {
		this.dealImagepath = dealImagepath;
	}

	public String getDealThumbail_imagepath() {
		return dealThumbail_imagepath;
	}

	public void setDealThumbail_imagepath(String dealThumbail_imagepath) {
		this.dealThumbail_imagepath = dealThumbail_imagepath;
	}

	public String getDealHeadline() {
		return dealHeadline;
	}

	public void setDealHeadline(String dealHeadline) {
		this.dealHeadline = dealHeadline;
	}

	public Long getDealcost_for_merchant() {
		return dealcost_for_merchant;
	}

	public void setDealcost_for_merchant(Long dealcost_for_merchant) {
		this.dealcost_for_merchant = dealcost_for_merchant;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}

	public Long getMerchantPrice() {
		return merchantPrice;
	}

	public void setMerchantPrice(Long merchantPrice) {
		this.merchantPrice = merchantPrice;
	}

	public Long getOperatorcost_per_voucher() {
		return operatorcost_per_voucher;
	}

	public void setOperatorcost_per_voucher(Long operatorcost_per_voucher) {
		this.operatorcost_per_voucher = operatorcost_per_voucher;
	}

	public String getReedmptionDetails() {
		return reedmptionDetails;
	}

	public void setReedmptionDetails(String reedmptionDetails) {
		this.reedmptionDetails = reedmptionDetails;
	}

	public Long getVoucherLimits() {
		return voucherLimits;
	}

	public void setVoucherLimits(Long voucherLimits) {
		this.voucherLimits = voucherLimits;
	}

	public String getOfferDescription() {
		return offerDescription;
	}

	public void setOfferDescription(String offerDescription) {
		this.offerDescription = offerDescription;
	}



	public String getDealStartdate() {
		return dealStartdate;
	}

	public void setDealStartdate(String dealStartdate) {
		this.dealStartdate = dealStartdate;
	}

	public String getDealStopdate() {
		return dealStopdate;
	}

	public void setDealStopdate(String dealStopdate) {
		this.dealStopdate = dealStopdate;
	}

	@Override
	public String toString() {
		return "PojoDeal [dealID=" + dealId + ", dealName=" + dealName + ", dealStatus=" + dealStatus + ", dealImagepath=" + dealImagepath
				+ ", dealThumbail_imagepath=" + dealThumbail_imagepath + ", dealHeadline=" + dealHeadline + ", dealcost_for_merchant="
				+ dealcost_for_merchant + ", price=" + price + ", offer=" + offer + ", merchantPrice=" + merchantPrice
				+ ", operatorcost_per_voucher=" + operatorcost_per_voucher + ", reedmptionDetails=" + reedmptionDetails + ", voucherLimits="
				+ voucherLimits + ", offerDescription=" + offerDescription + ", dealDescription=" + dealDescription + ", dealStartdate=" + dealStartdate
				+ ", isVoucher=" + isVoucher + ", redirectURL=" + redirectURL
				+ ", dealStopdate=" + dealStopdate + "]";
	}

}
