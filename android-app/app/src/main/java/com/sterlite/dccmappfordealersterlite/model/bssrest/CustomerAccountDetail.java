package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;

public class CustomerAccountDetail extends BaseRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Action action;
	private String refId;
	private String accountNumber;
	private String fullName;
	private Date createDate;
	private String username;
	private String password;
	private String billingArea;
	private String channelPartnerId;
	private Long tenantId;
	private String customerCategory;
	private String parentCustomerName;
	private String parentCustomerNumber;

	private AccountStatus customerStatus;
	private RestrictionStatus restrictionStatus;
	private String wscLogin;
	
	private List<NotificationReference> notificationRef;
	private List<IdentificationReference> identificationRef;
	private List<ContactMedium> contactMedium;
	
	private List<DirectDebitDetail> directDebitDetail;
	private BalanceDetail sharedMoneyBalanceDetail;
	
	private List<Characteristic> demographicParameters;
	private List<Characteristic> additionalParameters;
	private String preferredLanguageId;
	
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
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBillingArea() {
		return billingArea;
	}
	public void setBillingArea(String billingArea) {
		this.billingArea = billingArea;
	}
	public String getChannelPartnerId() {
		return channelPartnerId;
	}
	public void setChannelPartnerId(String channelPartnerId) {
		this.channelPartnerId = channelPartnerId;
	}
	public String getCustomerCategory() {
		return customerCategory;
	}
	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}
	public AccountStatus getCustomerStatus() {
		return customerStatus;
	}
	public void setCustomerStatus(AccountStatus customerStatus) {
		this.customerStatus = customerStatus;
	}
	public RestrictionStatus getRestrictionStatus() {
		return restrictionStatus;
	}
	public void setRestrictionStatus(RestrictionStatus restrictionStatus) {
		this.restrictionStatus = restrictionStatus;
	}
	public String getWscLogin() {
		return wscLogin;
	}
	public void setWscLogin(String wscLogin) {
		this.wscLogin = wscLogin;
	}
	public List<NotificationReference> getNotificationRef() {
		return notificationRef;
	}
	public void setNotificationRef(List<NotificationReference> notificationRef) {
		this.notificationRef = notificationRef;
	}
	public List<IdentificationReference> getIdentificationRef() {
		return identificationRef;
	}
	public void setIdentificationRef(List<IdentificationReference> identificationRef) {
		this.identificationRef = identificationRef;
	}
	public List<ContactMedium> getContactMedium() {
		return contactMedium;
	}
	public void setContactMedium(List<ContactMedium> contactMedium) {
		this.contactMedium = contactMedium;
	}
	
	public List<DirectDebitDetail> getDirectDebitDetail() {
		return directDebitDetail;
	}
	public void setDirectDebitDetail(List<DirectDebitDetail> directDebitDetail) {
		this.directDebitDetail = directDebitDetail;
	}
	public Long getTenantId() {
		return tenantId;
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
	
	public String getParentCustomerName() {
		return parentCustomerName;
	}
	public void setParentCustomerName(String parentCustomerName) {
		this.parentCustomerName = parentCustomerName;
	}
	public String getParentCustomerNumber() {
		return parentCustomerNumber;
	}
	public void setParentCustomerNumber(String parentCustomerNumber) {
		this.parentCustomerNumber = parentCustomerNumber;
	}
	
	@Override
	public String toString() {
		return "CustomerAccountDetail [action=" + action + ", refId=" + refId + ", accountNumber=" + accountNumber
				+ ", fullName=" + fullName + ", createDate=" + createDate + ", username=" + username + ", password="
				+ password + ", billingArea=" + billingArea + ", channelPartnerId=" + channelPartnerId + ", tenantId="
				+ tenantId + ", customerCategory=" + customerCategory + ", parentCustomerName=" + parentCustomerName
				+ ", parentCustomerNumber=" + parentCustomerNumber + ", customerStatus=" + customerStatus
				+ ", restrictionStatus=" + restrictionStatus + ", wscLogin=" + wscLogin + ", notificationRef="
				+ notificationRef + ", identificationRef=" + identificationRef + ", contactMedium=" + contactMedium
				+ ", directDebitDetail=" + directDebitDetail + ", sharedMoneyBalanceDetail=" + sharedMoneyBalanceDetail
				+ ", demographicParameters=" + demographicParameters + ", additionalParameters=" + additionalParameters
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
}
