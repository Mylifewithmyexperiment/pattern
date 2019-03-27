package com.sterlite.dccmappfordealersterlite.model.dto.recharge;

import java.io.Serializable;
import java.util.List;

public class RechargeDetailResponseData implements Serializable {
    private long responseCode;
	private String responseMessage;
	private String rechargeRequestNumber;
	private Double availableBalance;
	private Double expiryDate;
	private Double quota;
	private String balanceType;
	private List<RechargeDetailData> rechargeDetailData;

	public void setResponseCode(long responseCode) {
		this.responseCode = responseCode;
	}

	public long getResponseCode() {
		return this.responseCode;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getResponseMessage() {
		return this.responseMessage;
	}

	public void setRechargeRequestNumber(String rechargeRequestNumber) {
		this.rechargeRequestNumber = rechargeRequestNumber;
	}

	public String getRechargeRequestNumber() {
		return this.rechargeRequestNumber;
	}

	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public Double getAvailableBalance() {
		return this.availableBalance;
	}

	public Double getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Double expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setQuota(Double quota) {
		this.quota = quota;
	}

	public Double getQuota() {
		return this.quota;
	}

	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}

	public String getBalanceType() {
		return this.balanceType;
	}

	public void setRechargeDetailData(List<RechargeDetailData> rechargeDetailData) {
		this.rechargeDetailData = rechargeDetailData;
	}

	public List<RechargeDetailData> getRechargeDetailData() {
		return this.rechargeDetailData;
	}
}