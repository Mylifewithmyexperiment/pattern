package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;

public class BalanceTransferRequestDetail extends BaseRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String fromUserName;
	private String toUserName;
	private String fromNetworkIdentifier;
	private String toNetworkIdentifier;
	private Long debitAmount;
	private Long creditAmount;
	private Integer balanceTypeId;
	private Long fromUserChargeAmount;
	private BalanceRequestDetail fromBalanceData;
	private BalanceRequestDetail toBalanceData;
	
	public String getFromUserName() {
		return fromUserName;
	}
	
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	
	public String getToUserName() {
		return toUserName;
	}
	
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	
	public String getFromNetworkIdentifier() {
		return fromNetworkIdentifier;
	}
	
	public void setFromNetworkIdentifier(String fromNetworkIdentifier) {
		this.fromNetworkIdentifier = fromNetworkIdentifier;
	}
	
	public String getToNetworkIdentifier() {
		return toNetworkIdentifier;
	}
	
	public void setToNetworkIdentifier(String toNetworkIdentifier) {
		this.toNetworkIdentifier = toNetworkIdentifier;
	}
	
	public Long getDebitAmount() {
		return debitAmount;
	}
	
	public void setDebitAmount(Long debitAmount) {
		this.debitAmount = debitAmount;
	}
	
	public Long getCreditAmount() {
		return creditAmount;
	}
	
	public void setCreditAmount(Long creditAmount) {
		this.creditAmount = creditAmount;
	}
	
	public Integer getBalanceTypeId() {
		return balanceTypeId;
	}
	
	public void setBalanceTypeId(Integer balanceTypeId) {
		this.balanceTypeId = balanceTypeId;
	}
	
	public Long getFromUserChargeAmount() {
		return fromUserChargeAmount;
	}
	
	public void setFromUserChargeAmount(Long fromUserChargeAmount) {
		this.fromUserChargeAmount = fromUserChargeAmount;
	}
	
	public BalanceRequestDetail getFromBalanceData() {
		return fromBalanceData;
	}
	
	public void setFromBalanceData(BalanceRequestDetail fromBalanceData) {
		this.fromBalanceData = fromBalanceData;
	}
	
	public BalanceRequestDetail getToBalanceData() {
		return toBalanceData;
	}
	
	public void setToBalanceData(BalanceRequestDetail toBalanceData) {
		this.toBalanceData = toBalanceData;
	}
	
	
}
