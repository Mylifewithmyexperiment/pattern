package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;


public class ProductOfferDetail extends BaseRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Action action;
	private String id;
	private String name;
	private String category;
	private Long rentalAmount;
	private String billStartDate;
	private Long priority;
	private Long productSubscriptionId;
	private TimePeriod validFor;
	
	private BillingStatus billingStatus;
	private AccountStatus serviceStatus;
	private String overridden;
	private ProductOfferType type;
	
	private List<PricingDetail> pricingDetail;
	private List<ServiceDetail> serviceDetail;
	private List<DiscountReference> discountRef;
	
	private SharedAddonGroupDetail sharedAddonGroupDetail;
	private CUGReference cugRef;
	private FNFDetail fnfDetail;
	private List<BalanceDetail> nonMoneyBalanceDetail;
	
	private AppliedInvoiceAdjustmentDetail appliedInvoiceAdjustmentDetail;
	
	private List<Characteristic> additionalParameters;
	private List<InventoryDetail> inventoryDetail;
	
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Long getRentalAmount() {
		return rentalAmount;
	}
	public void setRentalAmount(Long rentalAmount) {
		this.rentalAmount = rentalAmount;
	}
	public String getBillStartDate() {
		return billStartDate;
	}
	public void setBillStartDate(String billStartDate) {
		this.billStartDate = billStartDate;
	}
	public Long getPriority() {
		return priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
	}
	public Long getProductSubscriptionId() {
		return productSubscriptionId;
	}
	public void setProductSubscriptionId(Long productSubscriptionId) {
		this.productSubscriptionId = productSubscriptionId;
	}
	public TimePeriod getValidFor() {
		return validFor;
	}
	public void setValidFor(TimePeriod validFor) {
		this.validFor = validFor;
	}
	public BillingStatus getBillingStatus() {
		return billingStatus;
	}
	public void setBillingStatus(BillingStatus billingStatus) {
		this.billingStatus = billingStatus;
	}
	public AccountStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(AccountStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public String getOverridden() {
		return overridden;
	}
	public void setOverridden(String overridden) {
		this.overridden = overridden;
	}
	public ProductOfferType getType() {
		return type;
	}
	public void setType(ProductOfferType type) {
		this.type = type;
	}
	public List<PricingDetail> getPricingDetail() {
		return pricingDetail;
	}
	public void setPricingDetail(List<PricingDetail> pricingDetail) {
		this.pricingDetail = pricingDetail;
	}
	public List<ServiceDetail> getServiceDetail() {
		return serviceDetail;
	}
	public void setServiceDetail(List<ServiceDetail> serviceDetail) {
		this.serviceDetail = serviceDetail;
	}
	public List<DiscountReference> getDiscountRef() {
		return discountRef;
	}
	public void setDiscountRef(List<DiscountReference> discountRef) {
		this.discountRef = discountRef;
	}
	public SharedAddonGroupDetail getSharedAddonGroupDetail() {
		return sharedAddonGroupDetail;
	}
	public void setSharedAddonGroupDetail(SharedAddonGroupDetail sharedAddonGroupDetail) {
		this.sharedAddonGroupDetail = sharedAddonGroupDetail;
	}
	public CUGReference getCugRef() {
		return cugRef;
	}
	public void setCugRef(CUGReference cugRef) {
		this.cugRef = cugRef;
	}
	public FNFDetail getFnfDetail() {
		return fnfDetail;
	}
	public void setFnfDetail(FNFDetail fnfDetail) {
		this.fnfDetail = fnfDetail;
	}
	public List<Characteristic> getAdditionalParameters() {
		return additionalParameters;
	}
	public void setAdditionalParameters(List<Characteristic> additionalParameters) {
		this.additionalParameters = additionalParameters;
	}
	
	public AppliedInvoiceAdjustmentDetail getAppliedInvoiceAdjustmentDetail() {
		return appliedInvoiceAdjustmentDetail;
	}
	public void setAppliedInvoiceAdjustmentDetail(AppliedInvoiceAdjustmentDetail appliedInvoiceAdjustmentDetail) {
		this.appliedInvoiceAdjustmentDetail = appliedInvoiceAdjustmentDetail;
	}
	
	public List<BalanceDetail> getNonMoneyBalanceDetail() {
		return nonMoneyBalanceDetail;
	}
	public void setNonMoneyBalanceDetail(List<BalanceDetail> nonMoneyBalanceDetail) {
		this.nonMoneyBalanceDetail = nonMoneyBalanceDetail;
	}
	
	public List<InventoryDetail> getInventoryDetail() {
		return inventoryDetail;
	}

	public void setInventoryDetail(List<InventoryDetail> inventoryDetail) {
		this.inventoryDetail = inventoryDetail;
	}
	
	/*@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(400);
		builder.append("ProductOfferDetail [action=").append(action).append(", id=").append(id).append(", name=")
				.append(name).append(", category=").append(category).append(", rentalAmount=").append(rentalAmount)
				.append(", billStartDate=").append(billStartDate).append(", priority=").append(priority)
				.append(", productSubscriptionId=").append(productSubscriptionId).append(", validFor=").append(validFor)
				.append(", billingStatus=").append(billingStatus).append(", serviceStatus=").append(serviceStatus)
				.append(", overridden=").append(overridden).append(", type=").append(type).append(", pricingDetail=")
				.append(pricingDetail).append(", serviceDetail=").append(serviceDetail).append(", discountRef=")
				.append(discountRef).append(", sharedAddonGroupDetail=").append(sharedAddonGroupDetail)
				.append(", cugRef=").append(cugRef).append(", fnfDetail=").append(fnfDetail)
				.append(", nonMoneyBalanceDetail=").append(nonMoneyBalanceDetail)
				.append(", appliedInvoiceAdjustmentDetail=").append(appliedInvoiceAdjustmentDetail)
				.append(", additionalParameters=").append(additionalParameters).append(", inventoryDetail=")
				.append(inventoryDetail).append("]");
		return builder.toString();
	}*/

	@Override
	public String toString() {
		return name;
	}
}
