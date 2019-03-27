package com.elitecorelib.deal.pojo;

import java.io.Serializable;


public class PojoDealVoucher implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3203947594073385L;
	private Long dealVoucherDetailId;
	private String dealHeadline;
	public String getDealHeadLine() {
		return dealHeadline;
	}
	public void setDealHeadLine(String dealHeadLine) {
		this.dealHeadline = dealHeadLine;
	}
	public String getDealThumbImage() {
		return dealThumbImage;
	}
	public void setDealThumbImage(String dealThumbImage) {
		this.dealThumbImage = dealThumbImage;
	}
	private String dealThumbImage;
	//private Long voucherId;
	private Long dealId;
	private Long merchantId;
	private String voucherCode;
	private String password;
	private String imsi;
	private String imei;
	private String msisdn;
	private String createDate;
	private String expiryDate;
	private Long price;
	private String usedDate;
	private String status;
	private String merchentId;
	
	public String getMerchentId() {
		return merchentId;
	}
	public void setMerchentId(String merchentId) {
		this.merchentId = merchentId;
	}
	public Long getDealVoucherDetailId() {
		return dealVoucherDetailId;
	}
	public void setDealVoucherDetailId(Long dealVoucherDetailId) {
		this.dealVoucherDetailId = dealVoucherDetailId;
	}
//	public Long getVoucherId() {
//		return voucherId;
//	}
//	public void setVoucherId(Long voucherId) {
//		this.voucherId = voucherId;
//	}
	public Long getDealId() {
		return dealId;
	}
	public void setDealId(Long dealId) {
		this.dealId = dealId;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getUsedDate() {
		return usedDate;
	}
	public void setUsedDate(String usedDate) {
		this.usedDate = usedDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/*@Override
	public String toString() {
		return "DealVoucherDetailData [dealVoucherDetailId="
				+ dealVoucherDetailId + "\n voucherId=" + voucherId
				+ "\n dealId=" + dealId + "\n merchantId=" + merchantId
				+ "\n voucherCode=" + voucherCode + "\n password=" + password
				+ "\n imsi=" + imsi + "\n imei=" + imei + "\n msisdn=" + msisdn
				+ "\n createDate=" + createDate + "\n expiryDate=" + expiryDate
				+ "\n price=" + price + "\n usedDate=" + usedDate + "\n status="
				+ status + "]";
	}*/
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((dealId == null) ? 0 : dealId.hashCode());
		result = prime
				* result
				+ ((dealVoucherDetailId == null) ? 0 : dealVoucherDetailId
						.hashCode());
		result = prime * result
				+ ((expiryDate == null) ? 0 : expiryDate.hashCode());
		result = prime * result + ((imei == null) ? 0 : imei.hashCode());
		result = prime * result + ((imsi == null) ? 0 : imsi.hashCode());
		result = prime * result
				+ ((merchantId == null) ? 0 : merchantId.hashCode());
		result = prime * result + ((msisdn == null) ? 0 : msisdn.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((usedDate == null) ? 0 : usedDate.hashCode());
		result = prime * result
				+ ((voucherCode == null) ? 0 : voucherCode.hashCode());
//		result = prime * result
//				+ ((voucherId == null) ? 0 : voucherId.hashCode());
		return result;
	}
	@Override
	public String toString() {
		return "DealVoucherDetailData [dealVoucherDetailId="
				+ dealVoucherDetailId 
				+ ", dealId=" + dealId + ", merchantId=" + merchantId
				+ ", voucherCode=" + voucherCode + ", password=" + password
				+ ", imsi=" + imsi + ", imei=" + imei + ", msisdn=" + msisdn
				+ ", createDate=" + createDate + ", expiryDate=" + expiryDate
				+ ", price=" + price + ", usedDate=" + usedDate + ", status="
				+ status + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PojoDealVoucher other = (PojoDealVoucher) obj;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (dealId == null) {
			if (other.dealId != null)
				return false;
		} else if (!dealId.equals(other.dealId))
			return false;
		if (dealVoucherDetailId == null) {
			if (other.dealVoucherDetailId != null)
				return false;
		} else if (!dealVoucherDetailId.equals(other.dealVoucherDetailId))
			return false;
		if (expiryDate == null) {
			if (other.expiryDate != null)
				return false;
		} else if (!expiryDate.equals(other.expiryDate))
			return false;
		if (imei == null) {
			if (other.imei != null)
				return false;
		} else if (!imei.equals(other.imei))
			return false;
		if (imsi == null) {
			if (other.imsi != null)
				return false;
		} else if (!imsi.equals(other.imsi))
			return false;
		if (merchantId == null) {
			if (other.merchantId != null)
				return false;
		} else if (!merchantId.equals(other.merchantId))
			return false;
		if (msisdn == null) {
			if (other.msisdn != null)
				return false;
		} else if (!msisdn.equals(other.msisdn))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (usedDate == null) {
			if (other.usedDate != null)
				return false;
		} else if (!usedDate.equals(other.usedDate))
			return false;
		if (voucherCode == null) {
			if (other.voucherCode != null)
				return false;
		} else if (!voucherCode.equals(other.voucherCode))
			return false;
//		if (voucherId == null) {
//			if (other.voucherId != null)
//				return false;
//		} else if (!voucherId.equals(other.voucherId))
//			return false;
		return true;
	}

	

}
