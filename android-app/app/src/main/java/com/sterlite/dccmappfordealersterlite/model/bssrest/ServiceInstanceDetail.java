package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;


public class ServiceInstanceDetail extends BaseRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Action action;
	private String refId;
	private String accountNumber;
	private String createDate;
	private Long tenantId;
	private String username;
	private String password;
	private TimePeriod validFor;
	private String businessHierarchy;
	private String chargingPattern;
	private String currencyCode;
	private Boolean isInvoicePaymentRequired;
	private AccountReference customerAccountRef;
	private AccountReference billingAccountRef;
	private AccountReference serviceAccountRef;
	
	private AccountStatus serviceInstanceStatus;
	private BillingStatus billingStatus;
	private Boolean billingStatusEffectType = true;
	private Boolean dunningExclusion;
	private Boolean safeCustodyStatus;
	private Double unbilledUsageAmount;
	private String downgradedProductDetail;
	
	private List<NotificationReference> notificationRef;
	private List<ContactMedium> contactMedium;
	
	private ProductOfferDetail basicProduct;
	private List<ProductOfferDetail> addonProducts;
	private List<DepositReference> depositRef;
	private PaymentDetail paymentDetail;
	private RefundDetail refundDetail;
	private BalanceDetail moneyBalanceDetail;
	private List<DiscountReference> discountRef;
	private List<FutureEventDetail> futureEventDetail;
	
	private List<Characteristic> additionalParameters;
	
	private int daysForExtension;
	
	public Double getUnbilledUsageAmount() {
		return unbilledUsageAmount;
	}

	public void setUnbilledUsageAmount(Double unbilledUsageAmount) {
		this.unbilledUsageAmount = unbilledUsageAmount;
	}

	public Boolean getSafeCustodyStatus() {
		return safeCustodyStatus;
	}

	public void setSafeCustodyStatus(Boolean safeCustodyStatus) {
		this.safeCustodyStatus = safeCustodyStatus;
	}
	
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

	public TimePeriod getValidFor() {
		return validFor;
	}

	public void setValidFor(TimePeriod validFor) {
		this.validFor = validFor;
	}

	public String getBusinessHierarchy() {
		return businessHierarchy;
	}

	public void setBusinessHierarchy(String businessHierarchy) {
		this.businessHierarchy = businessHierarchy;
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

	public AccountReference getCustomerAccountRef() {
		return customerAccountRef;
	}

	public void setCustomerAccountRef(AccountReference customerAccountRef) {
		this.customerAccountRef = customerAccountRef;
	}

	public AccountReference getBillingAccountRef() {
		return billingAccountRef;
	}

	public void setBillingAccountRef(AccountReference billingAccountRef) {
		this.billingAccountRef = billingAccountRef;
	}

	public AccountReference getServiceAccountRef() {
		return serviceAccountRef;
	}

	public void setServiceAccountRef(AccountReference serviceAccountRef) {
		this.serviceAccountRef = serviceAccountRef;
	}

	public AccountStatus getServiceInstanceStatus() {
		return serviceInstanceStatus;
	}

	public void setServiceInstanceStatus(AccountStatus serviceInstanceStatus) {
		this.serviceInstanceStatus = serviceInstanceStatus;
	}

	public BillingStatus getBillingStatus() {
		return billingStatus;
	}

	public void setBillingStatus(BillingStatus billingStatus) {
		this.billingStatus = billingStatus;
	}

	public Boolean getDunningExclusion() {
		return dunningExclusion;
	}

	public void setDunningExclusion(Boolean dunningExclusion) {
		this.dunningExclusion = dunningExclusion;
	}

	public String getDowngradedProductDetail() {
		return downgradedProductDetail;
	}

	public void setDowngradedProductDetail(String downgradedProductDetail) {
		this.downgradedProductDetail = downgradedProductDetail;
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

	public ProductOfferDetail getBasicProduct() {
		return basicProduct;
	}

	public void setBasicProduct(ProductOfferDetail basicProduct) {
		this.basicProduct = basicProduct;
	}

	public List<ProductOfferDetail> getAddonProducts() {
		return addonProducts;
	}

	public void setAddonProducts(List<ProductOfferDetail> addonProducts) {
		this.addonProducts = addonProducts;
	}

	public List<DepositReference> getDepositRef() {
		return depositRef;
	}

	public void setDepositRef(List<DepositReference> depositRef) {
		this.depositRef = depositRef;
	}

	public PaymentDetail getPaymentDetail() {
		return paymentDetail;
	}

	public void setPaymentDetail(PaymentDetail paymentDetail) {
		this.paymentDetail = paymentDetail;
	}

	public BalanceDetail getMoneyBalanceDetail() {
		return moneyBalanceDetail;
	}

	public void setMoneyBalanceDetail(BalanceDetail moneyBalanceDetail) {
		this.moneyBalanceDetail = moneyBalanceDetail;
	}

	public List<DiscountReference> getDiscountRef() {
		return discountRef;
	}

	public void setDiscountRef(List<DiscountReference> discountRef) {
		this.discountRef = discountRef;
	}

	public List<FutureEventDetail> getFutureEventDetail() {
		return futureEventDetail;
	}

	public void setFutureEventDetail(List<FutureEventDetail> futureEventDetail) {
		this.futureEventDetail = futureEventDetail;
	}

	public List<Characteristic> getAdditionalParameters() {
		return additionalParameters;
	}

	public void setAdditionalParameters(List<Characteristic> additionalParameters) {
		this.additionalParameters = additionalParameters;
	}
	
	public Boolean getIsInvoicePaymentRequired() {
		return isInvoicePaymentRequired;
	}

	public void setIsInvoicePaymentRequired(Boolean isInvoicePaymentRequired) {
		this.isInvoicePaymentRequired = isInvoicePaymentRequired;
	}

	public Boolean isBillingStatusEffectType() {
		return billingStatusEffectType;
	}

	public void setBillingStatusEffectType(Boolean billingStatusEffectType) {
		this.billingStatusEffectType = billingStatusEffectType;
	}
	
	public RefundDetail getRefundDetail() {
		return refundDetail;
	}

	public void setRefundDetail(RefundDetail refundDetail) {
		this.refundDetail = refundDetail;
	}

	public int getDaysForExtension() {
		return daysForExtension;
	}

	public void setDaysForExtension(int daysForExtension) {
		this.daysForExtension = daysForExtension;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(1500);
		builder.append("ServiceInstanceDetail [action=");
		builder.append(action);
		builder.append(", refId=");
		builder.append(refId);
		builder.append(", accountNumber=");
		builder.append(accountNumber);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", tenantId=");
		builder.append(tenantId);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", validFor=");
		builder.append(validFor);
		builder.append(", businessHierarchy=");
		builder.append(businessHierarchy);
		builder.append(", chargingPattern=");
		builder.append(chargingPattern);
		builder.append(", currencyCode=");
		builder.append(currencyCode);
		builder.append(", isInvoicePaymentRequired=");
		builder.append(isInvoicePaymentRequired);
		builder.append(", customerAccountRef=");
		builder.append(customerAccountRef);
		builder.append(", billingAccountRef=");
		builder.append(billingAccountRef);
		builder.append(", serviceAccountRef=");
		builder.append(serviceAccountRef);
		builder.append(", serviceInstanceStatus=");
		builder.append(serviceInstanceStatus);
		builder.append(", billingStatus=");
		builder.append(billingStatus);
		builder.append(", billingStatusEffectType=");
		builder.append(billingStatusEffectType);
		builder.append(", dunningExclusion=");
		builder.append(dunningExclusion);
		builder.append(", safeCustodyStatus=");
		builder.append(safeCustodyStatus);
		builder.append(", unbilledUsageAmount=");
		builder.append(unbilledUsageAmount);
		builder.append(", downgradedProductDetail=");
		builder.append(downgradedProductDetail);
		builder.append(", notificationRef=");
		builder.append(notificationRef);
		builder.append(", contactMedium=");
		builder.append(contactMedium);
		builder.append(", basicProduct=");
		builder.append(basicProduct);
		builder.append(", addonProducts=");
		builder.append(addonProducts);
		builder.append(", depositRef=");
		builder.append(depositRef);
		builder.append(", paymentDetail=");
		builder.append(paymentDetail);
		builder.append(", refundDetail=");
		builder.append(refundDetail);
		builder.append(", moneyBalanceDetail=");
		builder.append(moneyBalanceDetail);
		builder.append(", discountRef=");
		builder.append(discountRef);
		builder.append(", futureEventDetail=");
		builder.append(futureEventDetail);
		builder.append(", additionalParameters=");
		builder.append(additionalParameters);
		builder.append(", daysForExtension=");
		builder.append(daysForExtension);
		builder.append("]");
		return builder.toString();
	}

}
