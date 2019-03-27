package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

public class RefundDetail implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long refundAmount;

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(50);
		builder.append("RefundDetail [refundAmount=").append(refundAmount).append("]");
		return builder.toString();
	}
	
	

}
