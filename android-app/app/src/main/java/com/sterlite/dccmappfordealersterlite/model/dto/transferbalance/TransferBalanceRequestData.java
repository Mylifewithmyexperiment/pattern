package com.sterlite.dccmappfordealersterlite.model.dto.transferbalance;

import java.io.Serializable;

import com.sterlite.dccmappfordealersterlite.model.dto.recharge.ChannelInitiatorRequestData;

public class TransferBalanceRequestData implements Serializable {
    private ChannelInitiatorRequestData channelInitiatorRequestData;
	private long transferAmount;
	private String fromUserName;
	private String toUserName;
	private String fromMobileNumber;
	private String toMobileNumber;

	public void setChannelInitiatorRequestData(ChannelInitiatorRequestData channelInitiatorRequestData) {
		this.channelInitiatorRequestData = channelInitiatorRequestData;
	}

	public ChannelInitiatorRequestData getChannelInitiatorRequestData() {
		return this.channelInitiatorRequestData;
	}

	public void setTransferAmount(long transferAmount) {
		this.transferAmount = transferAmount;
	}

	public long getTransferAmount() {
		return this.transferAmount;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getFromUserName() {
		return this.fromUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getToUserName() {
		return this.toUserName;
	}

	public void setFromMobileNumber(String fromMobileNumber) {
		this.fromMobileNumber = fromMobileNumber;
	}

	public String getFromMobileNumber() {
		return this.fromMobileNumber;
	}

	public void setToMobileNumber(String toMobileNumber) {
		this.toMobileNumber = toMobileNumber;
	}

	public String getToMobileNumber() {
		return this.toMobileNumber;
	}
}