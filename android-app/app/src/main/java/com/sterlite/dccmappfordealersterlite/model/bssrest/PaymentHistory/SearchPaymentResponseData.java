/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.sterlite.dccmappfordealersterlite.model.bssrest.PaymentHistory;

import java.io.Serializable;
import java.util.List;

public class SearchPaymentResponseData implements Serializable {
   private String billingAccountNumber;
   private String billingAccountName;
   private List<PaymentDetailData> paymentDetailDatas;

   public void setBillingAccountNumber(String billingAccountNumber) {
      this.billingAccountNumber = billingAccountNumber;
   }

   public String getBillingAccountNumber() {
      return this.billingAccountNumber;
   }

   public void setBillingAccountName(String billingAccountName) {
      this.billingAccountName = billingAccountName;
   }

   public String getBillingAccountName() {
      return this.billingAccountName;
   }

   public void setPaymentDetailDatas(List<PaymentDetailData> paymentDetailDatas) {
      this.paymentDetailDatas = paymentDetailDatas;
   }

   public List<PaymentDetailData> getPaymentDetailDatas() {
      return this.paymentDetailDatas;
   }
}