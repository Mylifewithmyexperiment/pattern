package com.sterlite.dccmappfordealersterlite.model.bssrest;

import java.io.Serializable;

import com.sterlite.dccmappfordealersterlite.model.bssrest.receivablemethod.CashMethod;
import com.sterlite.dccmappfordealersterlite.model.bssrest.receivablemethod.ChequeMethod;
import com.sterlite.dccmappfordealersterlite.model.bssrest.receivablemethod.CreditCardMethod;
import com.sterlite.dccmappfordealersterlite.model.bssrest.receivablemethod.DebitCardMethod;
import com.sterlite.dccmappfordealersterlite.model.bssrest.receivablemethod.DemandDraftMethod;
import com.sterlite.dccmappfordealersterlite.model.bssrest.receivablemethod.OnlineMethod;
import com.sterlite.dccmappfordealersterlite.model.bssrest.receivablemethod.VoucherMethod;


public class PaymentDetail implements Serializable{

	private static final long serialVersionUID = 1L;

	private PaymentMode paymentMode;
	private Long paymentAmount;
	private CashMethod cashMethod;
	private ChequeMethod chequeMethod;
	private CreditCardMethod creditCardMethod;

	private DebitCardMethod debitCardMethod;
	private OnlineMethod onlineMethod;
	private DemandDraftMethod demandDraftMethod;
	private VoucherMethod voucherMethod;

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public CashMethod getCashMethod() {
		return cashMethod;
	}
	public void setCashMethod(CashMethod cashMethod) {
		this.cashMethod = cashMethod;
	}
	public ChequeMethod getChequeMethod() {
		return chequeMethod;
	}
	public void setChequeMethod(ChequeMethod chequeMethod) {
		this.chequeMethod = chequeMethod;
	}
	public CreditCardMethod getCreditCardMethod() {
		return creditCardMethod;
	}
	public void setCreditCardMethod(CreditCardMethod creditCardMethod) {
		this.creditCardMethod = creditCardMethod;
	}
	public DebitCardMethod getDebitCardMethod() {
		return debitCardMethod;
	}
	public void setDebitCardMethod(DebitCardMethod debitCardMethod) {
		this.debitCardMethod = debitCardMethod;
	}
	public OnlineMethod getOnlineMethod() {
		return onlineMethod;
	}
	public void setOnlineMethod(OnlineMethod onlineMethod) {
		this.onlineMethod = onlineMethod;
	}
	public DemandDraftMethod getDemandDraftMethod() {
		return demandDraftMethod;
	}
	public void setDemandDraftMethod(DemandDraftMethod demandDraftMethod) {
		this.demandDraftMethod = demandDraftMethod;
	}
	public VoucherMethod getVoucherMethod() {
		return voucherMethod;
	}
	public void setVoucherMethod(VoucherMethod voucherMethod) {
		this.voucherMethod = voucherMethod;
	}

	public Long getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	@Override
	public String toString() {
		return "PaymentDetail [paymentMode=" + paymentMode + ", paymentAmount=" + paymentAmount
				+ ", cashMethod=" + cashMethod + ", chequeMethod=" + chequeMethod
				+ ", creditCardMethod=" + creditCardMethod + ", debitCardMethod=" + debitCardMethod + ", onlineMethod="
				+ onlineMethod + ", demandDraftMethod=" + demandDraftMethod + ", voucherMethod=" + voucherMethod + "]";
	}


}
