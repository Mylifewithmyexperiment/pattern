package com.sterlite.dccmappfordealersterlite.model.dto.recharge;

import java.io.Serializable;

public class RechargeRequestData implements Serializable {
    private ChannelInitiatorRequestData channelInitiatorRequestData;
	private String sourceChannel;
	private String mobileNumber;
	private String rechargeType;
	private String voucherPIN;
	private String rechargePackageName;
	private String denominationAmount;
	private String tenantName;

	public void setChannelInitiatorRequestData(ChannelInitiatorRequestData channelInitiatorRequestData) {
		this.channelInitiatorRequestData = channelInitiatorRequestData;
	}

	public ChannelInitiatorRequestData getChannelInitiatorRequestData() {
		return this.channelInitiatorRequestData;
	}

	public void setSourceChannel(String sourceChannel) {
		this.sourceChannel = sourceChannel;
	}

	public String getSourceChannel() {
		return this.sourceChannel;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setRechargeType(String rechargeType) {
		this.rechargeType = rechargeType;
	}

	public String getRechargeType() {
		return this.rechargeType;
	}

	public void setVoucherPIN(String voucherPIN) {
		this.voucherPIN = voucherPIN;
	}

	public String getVoucherPIN() {
		return this.voucherPIN;
	}

	public void setRechargePackageName(String rechargePackageName) {
		this.rechargePackageName = rechargePackageName;
	}

	public String getRechargePackageName() {
		return this.rechargePackageName;
	}

	public void setDenominationAmount(String denominationAmount) {
		this.denominationAmount = denominationAmount;
	}

	public String getDenominationAmount() {
		return this.denominationAmount;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getTenantName() {
		return this.tenantName;
	}
}