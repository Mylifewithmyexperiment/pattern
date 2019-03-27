package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;

public class BillingAccountDetail extends BaseRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Action action;
	private String refId;
	private String accountNumber;
	private String fullName;
	private String preferredLanguageId;
	private Date createDate;
	private Long tenantId;
	
	private String billingCycleSpecificationName;
	private String billFormat;
	private String billPresentationMedia;
	private String chargingPattern;
	private String currencyCode;
	private String restrictionPolicy;
	
	private AccountStatus accountStatus;
	private Boolean dunningStatus;
	private Boolean billingOnHoldStatus;
	
	private AccountReference customerAccountRef;
	private AccountReference aggregatorAccountRef;
	private List<AccountReference> serviceInstanceRef;
	private BillingSummary billingSummary;
	
	private DirectDebitDetail directDebitDetail;
	private CreditClassReference creditClassRef;
	private List<DepositReference> depositRef;
	private List<DiscountReference> discountRef;
	private List<PromiseToPayReference> promiseToPayRef;
	private List<FutureEventDetail> futureEventDetail;
	
	private List<NotificationReference> notificationRef;
	private List<ContactMedium> contactMedium;
	
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
	public String getBillingCycleSpecificationName() {
		return billingCycleSpecificationName;
	}
	public void setBillingCycleSpecificationName(String billingCycleSpecificationName) {
		this.billingCycleSpecificationName = billingCycleSpecificationName;
	}
	public String getBillFormat() {
		return billFormat;
	}
	public void setBillFormat(String billFormat) {
		this.billFormat = billFormat;
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
	public String getRestrictionPolicy() {
		return restrictionPolicy;
	}
	public void setRestrictionPolicy(String restrictionPolicy) {
		this.restrictionPolicy = restrictionPolicy;
	}
	public AccountStatus getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}
	public Boolean getDunningStatus() {
		return dunningStatus;
	}
	public void setDunningStatus(Boolean dunningStatus) {
		this.dunningStatus = dunningStatus;
	}
	public Boolean getBillingOnHoldStatus() {
		return billingOnHoldStatus;
	}
	public void setBillingOnHoldStatus(Boolean billingOnHoldStatus) {
		this.billingOnHoldStatus = billingOnHoldStatus;
	}
	public AccountReference getCustomerAccountRef() {
		return customerAccountRef;
	}
	public void setCustomerAccountRef(AccountReference customerAccountRef) {
		this.customerAccountRef = customerAccountRef;
	}
	public AccountReference getAggregatorAccountRef() {
		return aggregatorAccountRef;
	}
	public void setAggregatorAccountRef(AccountReference aggregatorAccountRef) {
		this.aggregatorAccountRef = aggregatorAccountRef;
	}
	public List<AccountReference> getServiceInstanceRef() {
		return serviceInstanceRef;
	}
	public void setServiceInstanceRef(List<AccountReference> serviceInstanceRef) {
		this.serviceInstanceRef = serviceInstanceRef;
	}
	public DirectDebitDetail getDirectDebitDetail() {
		return directDebitDetail;
	}
	public void setDirectDebitDetail(DirectDebitDetail directDebitDetail) {
		this.directDebitDetail = directDebitDetail;
	}
	public CreditClassReference getCreditClassRef() {
		return creditClassRef;
	}
	public void setCreditClassRef(CreditClassReference creditClassRef) {
		this.creditClassRef = creditClassRef;
	}
	public List<DepositReference> getDepositRef() {
		return depositRef;
	}
	public void setDepositRef(List<DepositReference> depositRef) {
		this.depositRef = depositRef;
	}
	public List<DiscountReference> getDiscountRef() {
		return discountRef;
	}
	public void setDiscountRef(List<DiscountReference> discountRef) {
		this.discountRef = discountRef;
	}
	public List<PromiseToPayReference> getPromiseToPayRef() {
		return promiseToPayRef;
	}
	public void setPromiseToPayRef(List<PromiseToPayReference> promiseToPayRef) {
		this.promiseToPayRef = promiseToPayRef;
	}
	public List<FutureEventDetail> getFutureEventDetail() {
		return futureEventDetail;
	}
	public void setFutureEventDetail(List<FutureEventDetail> futureEventDetail) {
		this.futureEventDetail = futureEventDetail;
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
	
	public BillingSummary getBillingSummary() {
		return billingSummary;
	}
	public void setBillingSummary(BillingSummary billingSummary) {
		this.billingSummary = billingSummary;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(500);
		builder.append("BillingAccountDetail [action=").append(action).append(", refId=").append(refId)
				.append(", accountNumber=").append(accountNumber).append(", fullName=").append(fullName)
				.append(", preferredLanguageId=").append(preferredLanguageId).append(", createDate=").append(createDate)
				.append(", tenantId=").append(tenantId).append(", billingCycleSpecificationName=")
				.append(billingCycleSpecificationName).append(", billFormat=").append(billFormat)
				.append(", billPresentationMedia=").append(billPresentationMedia).append(", chargingPattern=")
				.append(chargingPattern).append(", currencyCode=").append(currencyCode).append(", restrictionPolicy=")
				.append(restrictionPolicy).append(", accountStatus=").append(accountStatus).append(", dunningStatus=")
				.append(dunningStatus).append(", billingOnHoldStatus=").append(billingOnHoldStatus)
				.append(", customerAccountRef=").append(customerAccountRef).append(", aggregatorAccountRef=")
				.append(aggregatorAccountRef).append(", serviceInstanceRef=").append(serviceInstanceRef)
				.append(", billingSummary=").append(billingSummary).append(", directDebitDetail=")
				.append(directDebitDetail).append(", creditClassRef=").append(creditClassRef).append(", depositRef=")
				.append(depositRef).append(", discountRef=").append(discountRef).append(", promiseToPayRef=")
				.append(promiseToPayRef).append(", futureEventDetail=").append(futureEventDetail)
				.append(", notificationRef=").append(notificationRef).append(", contactMedium=").append(contactMedium)
				.append(", demographicParameters=").append(demographicParameters).append(", additionalParameters=")
				.append(additionalParameters).append("]");
		return builder.toString();
	}
		
}
