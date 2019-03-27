package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;
import java.util.List;


public class CUGGroupDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Action action;
	private String groupNumber;
	private String groupName;
	private String productOfferName;
	private List<CUGMemberDetail> memberDetail;
	
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public String getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getProductOfferName() {
		return productOfferName;
	}
	public void setProductOfferName(String productOfferName) {
		this.productOfferName = productOfferName;
	}
	public List<CUGMemberDetail> getMemberDetail() {
		return memberDetail;
	}
	public void setMemberDetail(List<CUGMemberDetail> memberDetail) {
		this.memberDetail = memberDetail;
	}
	@Override
	public String toString() {
		return "CUGGroupDetail [action=" + action + ", groupNumber=" + groupNumber + ", groupName=" + groupName
				+ ", productOfferName=" + productOfferName + ", memberDetail=" + memberDetail + "]";
	}
	
	
}
