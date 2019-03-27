package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ServiceAccountDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Action action;
	private String refId;
	private String accountNumber;
	private String fullName;
	private Date createDate;
	private Long tenantId;
	private AccountStatus status;
	private String preferredLanguageId;
	
	private AccountReference customerAccountRef;
	private List<AccountReference> serviceInstanceRef;
	private List<AccountReference> sharedGroupAccountRef;

	private List<NotificationReference> notificationRef;
	private List<ContactMedium> contactMedium;
	
	private BalanceDetail sharedMoneyBalanceDetail;
	
	private List<Characteristic> demographicParameters;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public AccountStatus getStatus() {
		return status;
	}
	public void setStatus(AccountStatus status) {
		this.status = status;
	}
	public AccountReference getCustomerAccountRef() {
		return customerAccountRef;
	}
	public void setCustomerAccountRef(AccountReference customerAccountRef) {
		this.customerAccountRef = customerAccountRef;
	}
	public List<AccountReference> getServiceInstanceRef() {
		return serviceInstanceRef;
	}
	public void setServiceInstanceRef(List<AccountReference> serviceInstanceRef) {
		this.serviceInstanceRef = serviceInstanceRef;
	}
	public List<AccountReference> getSharedGroupAccountRef() {
		return sharedGroupAccountRef;
	}
	public void setSharedGroupAccountRef(List<AccountReference> sharedGroupAccountRef) {
		this.sharedGroupAccountRef = sharedGroupAccountRef;
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
	public BalanceDetail getSharedMoneyBalanceDetail() {
		return sharedMoneyBalanceDetail;
	}
	public void setSharedMoneyBalanceDetail(BalanceDetail sharedMoneyBalanceDetail) {
		this.sharedMoneyBalanceDetail = sharedMoneyBalanceDetail;
	}
	public List<Characteristic> getDemographicParameters() {
		return demographicParameters;
	}
	public void setDemographicParameters(List<Characteristic> demographicParameters) {
		this.demographicParameters = demographicParameters;
	}
	public List<Characteristic> getAdditionalParameters() {
		return additionalParameters;
	}
	public void setAdditionalParameters(List<Characteristic> additionalParameters) {
		this.additionalParameters = additionalParameters;
	}
	
	public String getPreferredLanguageId() {
		return preferredLanguageId;
	}
	public void setPreferredLanguageId(String preferredLanguageId) {
		this.preferredLanguageId = preferredLanguageId;
	}
	@Override
	public String toString() {
		return "ServiceAccountDetail [action=" + action + ", refId=" + refId
				+ ", accountNumber=" + accountNumber + ", fullName=" + fullName + ", createDate=" + createDate
				+ ", tenantId=" + tenantId + ", status=" + status + ", customerAccountRef=" + customerAccountRef
				+ ", serviceInstanceRef=" + serviceInstanceRef + ", sharedGroupAccountRef=" + sharedGroupAccountRef
				+ ", notificationRef=" + notificationRef + ", contactMedium=" + contactMedium
				+ ", sharedMoneyBalanceDetail=" + sharedMoneyBalanceDetail + ", demographicParameters="
				+ demographicParameters + ", additionalParameters=" + additionalParameters 
				+ ", preferredLanguageId=" + preferredLanguageId +"]";
	}
	
	
}
