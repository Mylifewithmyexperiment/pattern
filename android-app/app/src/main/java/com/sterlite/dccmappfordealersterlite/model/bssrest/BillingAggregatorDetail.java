package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

public class BillingAggregatorDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Action action;
	private String refId;
	private String accountNumber;
	private String fullName;
	private String createDate;
	private Long tenantId;
	
	private String statementCycleName;
	private String statementDay;
	private String billPresentationMedia;
	private String chargingPattern;
	private String currencyCode;
	private BillingStatus status;
	
	private AccountReference customerAccountRef;
	private List<AccountReference> billingAccountRef;
	
	private List<NotificationReference> notificationRef;
	private List<ContactMedium> contactMedium;
	
	private List<Characteristic> additionalParameters;

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public String getStatementCycleName() {
		return statementCycleName;
	}

	public void setStatementCycleName(String statementCycleName) {
		this.statementCycleName = statementCycleName;
	}

	public String getStatementDay() {
		return statementDay;
	}

	public void setStatementDay(String statementDay) {
		this.statementDay = statementDay;
	}

	public String getBillPresentationMedia() {
		return billPresentationMedia;
	}

	public void setBillPresentationMedia(String billPresentationMedia) {
		this.billPresentationMedia = billPresentationMedia;
	}

	public String getChargingPattern() {
		return chargingPattern;
	}

	public void setChargingPattern(String chargingPattern) {
		this.chargingPattern = chargingPattern;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BillingStatus getStatus() {
		return status;
	}

	public void setStatus(BillingStatus status) {
		this.status = status;
	}

	public AccountReference getCustomerAccountRef() {
		return customerAccountRef;
	}

	public void setCustomerAccountRef(AccountReference customerAccountRef) {
		this.customerAccountRef = customerAccountRef;
	}

	public List<AccountReference> getBillingAccountRef() {
		return billingAccountRef;
	}

	public void setBillingAccountRef(List<AccountReference> billingAccountRef) {
		this.billingAccountRef = billingAccountRef;
	}

	public List<NotificationReference> getNotificationRef() {
		return notificationRef;
	}

	public void setNotificationRef(List<NotificationReference> notificationRef) {
		this.notificationRef = notificationRef;
	}

	public List<ContactMedium> getContactMedium() {
		return contactMedium;
	}

	public void setContactMedium(List<ContactMedium> contactMedium) {
		this.contactMedium = contactMedium;
	}

	public List<Characteristic> getAdditionalParameters() {
		return additionalParameters;
	}

	public void setAdditionalParameters(List<Characteristic> additionalParameters) {
		this.additionalParameters = additionalParameters;
	}

	@Override
	public String toString() {
		return "BillingAggregatorDetail [action=" + action + ", refId=" + refId 
				+ ", accountNumber=" + accountNumber + ", fullName=" + fullName + ", createDate=" + createDate
				+ ", tenantId=" + tenantId + ", statementCycleName=" + statementCycleName + ", statementDay="
				+ statementDay + ", billPresentationMedia=" + billPresentationMedia + ", chargingPattern="
				+ chargingPattern + ", currencyCode=" + currencyCode + ", status=" + status + ", customerAccountRef="
				+ customerAccountRef + ", billingAccountRef=" + billingAccountRef + ", notificationRef="
				+ notificationRef + ", contactMedium=" + contactMedium + ", additionalParameters="
				+ additionalParameters + "]";
	}
	
	
}
