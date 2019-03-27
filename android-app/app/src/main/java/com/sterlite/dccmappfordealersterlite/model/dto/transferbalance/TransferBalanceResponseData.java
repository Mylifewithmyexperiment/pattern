package com.sterlite.dccmappfordealersterlite.model.dto.transferbalance;

import java.io.Serializable;

public class TransferBalanceResponseData implements Serializable {
    private long responseCode;
	private String responseMessage;
	private long balanceTransferAmount;
	private long sourceAfterBalance;
	private long sourcePreviousBalance;
	private String sourceTransactionID;
	private String targetTransactionID;
	private long targetAfterBalance;
	private long targetPreviousBalance;

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

	public void setBalanceTransferAmount(long balanceTransferAmount) {
		this.balanceTransferAmount = balanceTransferAmount;
	}

	public long getBalanceTransferAmount() {
		return this.balanceTransferAmount;
	}

	public void setSourceAfterBalance(long sourceAfterBalance) {
		this.sourceAfterBalance = sourceAfterBalance;
	}

	public long getSourceAfterBalance() {
		return this.sourceAfterBalance;
	}

	public void setSourcePreviousBalance(long sourcePreviousBalance) {
		this.sourcePreviousBalance = sourcePreviousBalance;
	}

	public long getSourcePreviousBalance() {
		return this.sourcePreviousBalance;
	}

	public void setSourceTransactionID(String sourceTransactionID) {
		this.sourceTransactionID = sourceTransactionID;
	}

	public String getSourceTransactionID() {
		return this.sourceTransactionID;
	}

	public void setTargetTransactionID(String targetTransactionID) {
		this.targetTransactionID = targetTransactionID;
	}

	public String getTargetTransactionID() {
		return this.targetTransactionID;
	}

	public void setTargetAfterBalance(long targetAfterBalance) {
		this.targetAfterBalance = targetAfterBalance;
	}

	public long getTargetAfterBalance() {
		return this.targetAfterBalance;
	}

	public void setTargetPreviousBalance(long targetPreviousBalance) {
		this.targetPreviousBalance = targetPreviousBalance;
	}

	public long getTargetPreviousBalance() {
		return this.targetPreviousBalance;
	}

	@Override
	public String toString() {
		return "TransferBalanceResponseData{" +
				"responseCode=" + responseCode +
				", responseMessage='" + responseMessage + '\'' +
				", balanceTransferAmount=" + balanceTransferAmount +
				", sourceAfterBalance=" + sourceAfterBalance +
				", sourcePreviousBalance=" + sourcePreviousBalance +
				", sourceTransactionID='" + sourceTransactionID + '\'' +
				", targetTransactionID='" + targetTransactionID + '\'' +
				", targetAfterBalance=" + targetAfterBalance +
				", targetPreviousBalance=" + targetPreviousBalance +
				'}';
	}
}