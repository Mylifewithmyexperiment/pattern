package com.sterlite.dccmappfordealersterlite.model.dto.recharge;

import java.io.Serializable;

public class ChannelInitiatorRequestData implements Serializable {
	private ExtraParameterData extraParam;
	private String ipAddress;
	private String remarks;
	private String staffId;
	private String staffName;

	public void setExtraParam(ExtraParameterData extraParam) {
		this.extraParam = extraParam;
	}

	public ExtraParameterData getExtraParam() {
		return this.extraParam;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffName() {
		return this.staffName;
	}
}