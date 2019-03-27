package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

public class BillingAccountStatementDetail  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String accountNumber;
	private double openingBalance;
	private double closingBalance;
	private String currencyAlias;
	private TimePeriod timePeriod;
	private List<BillingAccountBalance> accountBalance;
	private List<DocumentDetail> debitDocumentDetail;
	private List<DocumentDetail> creditDocumentDetail;
	private List<DepositDetail> depositDetails;
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public double getOpeningBalance() {
		return openingBalance;
	}
	
	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}
	
	public double getClosingBalance() {
		return closingBalance;
	}
	
	public void setClosingBalance(double closingBalance) {
		this.closingBalance = closingBalance;
	}
	
	public String getCurrencyAlias() {
		return currencyAlias;
	}
	
	public void setCurrencyAlias(String currencyAlias) {
		this.currencyAlias = currencyAlias;
	}
	
	public TimePeriod getTimePeriod() {
		return timePeriod;
	}
	
	public void setTimePeriod(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}
	
	public List<BillingAccountBalance> getAccountBalance() {
		return accountBalance;
	}
	
	public void setAccountBalance(List<BillingAccountBalance> accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	public List<DocumentDetail> getDebitDocumentDetail() {
		return debitDocumentDetail;
	}

	public void setDebitDocumentDetail(List<DocumentDetail> debitDocumentDetail) {
		this.debitDocumentDetail = debitDocumentDetail;
	}

	public List<DocumentDetail> getCreditDocumentDetail() {
		return creditDocumentDetail;
	}

	public void setCreditDocumentDetail(List<DocumentDetail> creditDocumentDetail) {
		this.creditDocumentDetail = creditDocumentDetail;
	}

	public List<DepositDetail> getDepositDetails() {
		return depositDetails;
	}
	
	public void setDepositDetails(List<DepositDetail> depositDetails) {
		this.depositDetails = depositDetails;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(200);
		builder.append("BillingAccountStatementDetail [accountNumber=").append(accountNumber)
				.append(", openingBalance=").append(openingBalance).append(", closingBalance=").append(closingBalance)
				.append(", currencyAlias=").append(currencyAlias).append(", timePeriod=").append(timePeriod)
				.append(", accountBalance=").append(accountBalance).append(", debitDocumentDetail=")
				.append(debitDocumentDetail).append(", creditDocumentDetail=").append(creditDocumentDetail)
				.append(", depositDetails=").append(depositDetails).append("]");
		return builder.toString();
	}
	
}
