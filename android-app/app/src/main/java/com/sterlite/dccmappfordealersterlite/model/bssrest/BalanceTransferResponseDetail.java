package com.sterlite.dccmappfordealersterlite.model.bssrest;

public class BalanceTransferResponseDetail {

	private String fromNumber;
	private String toNumber;
	private Long balanceTransferAmount;
	private Double sourcePreviousBalance;
	private Double targetPreviousBalance;
	private Double sourceAfterBalance;
	private Double targetAfterBalance;
	private String responseMessege;
	private String strSourceTransactionID;
	private String strTargetTransactionID;
	
	public String getFromNumber() {
		return fromNumber;
	}
	public void setFromNumber(String fromNumber) {
		this.fromNumber = fromNumber;
	}
	public String getToNumber() {
		return toNumber;
	}
	public void setToNumber(String toNumber) {
		this.toNumber = toNumber;
	}
	public Long getBalanceTransferAmount() {
		return balanceTransferAmount;
	}
	public void setBalanceTransferAmount(Long balanceTransferAmount) {
		this.balanceTransferAmount = balanceTransferAmount;
	}
	public Double getSourcePreviousBalance() {
		return sourcePreviousBalance;
	}
	public void setSourcePreviousBalance(Double sourcePreviousBalance) {
		this.sourcePreviousBalance = sourcePreviousBalance;
	}
	public Double getTargetPreviousBalance() {
		return targetPreviousBalance;
	}
	public void setTargetPreviousBalance(Double targetPreviousBalance) {
		this.targetPreviousBalance = targetPreviousBalance;
	}
	public Double getSourceAfterBalance() {
		return sourceAfterBalance;
	}
	public void setSourceAfterBalance(Double sourceAfterBalance) {
		this.sourceAfterBalance = sourceAfterBalance;
	}
	public Double getTargetAfterBalance() {
		return targetAfterBalance;
	}
	public void setTargetAfterBalance(Double targetAfterBalance) {
		this.targetAfterBalance = targetAfterBalance;
	}
	public String getResponseMessege() {
		return responseMessege;
	}
	public void setResponseMessege(String responseMessege) {
		this.responseMessege = responseMessege;
	}
	public String getStrSourceTransactionID() {
		return strSourceTransactionID;
	}
	public void setStrSourceTransactionID(String strSourceTransactionID) {
		this.strSourceTransactionID = strSourceTransactionID;
	}
	public String getStrTargetTransactionID() {
		return strTargetTransactionID;
	}
	public void setStrTargetTransactionID(String strTargetTransactionID) {
		this.strTargetTransactionID = strTargetTransactionID;
	}
	
}
