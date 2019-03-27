package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.model.customer.BaseRequest;

public class SharedAddonGroupDetail extends BaseRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Action action;
	private String number;
	private AccountReference ownerDetail;
	private List<AccountReference> memberDetail; 
	private String productOfferName;
	private Long productSubscriptionId; 
	private TimePeriod validFor;
	
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public AccountReference getOwnerDetail() {
		return ownerDetail;
	}
	public void setOwnerDetail(AccountReference ownerDetail) {
		this.ownerDetail = ownerDetail;
	}
	public List<AccountReference> getMemberDetail() {
		return memberDetail;
	}
	public void setMemberDetail(List<AccountReference> memberDetail) {
		this.memberDetail = memberDetail;
	}
	public String getProductOfferName() {
		return productOfferName;
	}
	public void setProductOfferName(String productOfferName) {
		this.productOfferName = productOfferName;
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(100);
		builder.append("SharedAddonGroupDetail [action=").append(action).append(", number=").append(number)
				.append(", ownerDetail=").append(ownerDetail).append(", memberDetail=").append(memberDetail)
				.append(", productOfferName=").append(productOfferName).append(", productSubscriptionId=")
				.append(productSubscriptionId).append(", validFor=").append(validFor).append("]");
		return builder.toString();
	}
	
}
