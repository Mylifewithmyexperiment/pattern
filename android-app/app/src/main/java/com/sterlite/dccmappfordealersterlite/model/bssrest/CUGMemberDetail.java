package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;


public class CUGMemberDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String memberNumber;
	private String memberName;
	private String memeberType;
	private String productOfferName;
	private String shortCode;
	private TimePeriod validFor;
	
	public String getMemberNumber() {
		return memberNumber;
	}
	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemeberType() {
		return memeberType;
	}
	public void setMemeberType(String memeberType) {
		this.memeberType = memeberType;
	}
	public String getProductOfferName() {
		return productOfferName;
	}
	public void setProductOfferName(String productOfferName) {
		this.productOfferName = productOfferName;
	}
	public String getShortCode() {
		return shortCode;
	}
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
	public TimePeriod getValidFor() {
		return validFor;
	}
	public void setValidFor(TimePeriod validFor) {
		this.validFor = validFor;
	}
	@Override
	public String toString() {
		return "CUGMemberDetail [memberNumber=" + memberNumber + ", memberName=" + memberName + ", memeberType="
				+ memeberType + ", productOfferName=" + productOfferName + ", shortCode=" + shortCode + ", validFor="
				+ validFor + "]";
	}
	
	
}
