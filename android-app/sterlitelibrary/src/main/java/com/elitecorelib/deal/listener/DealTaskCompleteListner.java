package com.elitecorelib.deal.listener;

import java.util.List;

import com.elitecorelib.deal.pojo.PojoDeal;
import com.elitecorelib.deal.pojo.PojoDealAll;
import com.elitecorelib.deal.pojo.PojoDealTag;
import com.elitecorelib.deal.pojo.PojoDealVoucherResponse;
import com.elitecorelib.deal.pojo.PojoRedeemResponse;

public  interface DealTaskCompleteListner {

	   void onDealTagComplete(List<PojoDealTag> dealTags);
	   void onDealComplete(List<PojoDeal> deals);
	   void onAllDealComplete(List<PojoDealAll> deals);
	   void onDealVoucherComplete(PojoDealVoucherResponse dealVoucherResponse);
	   void onRedeemVoucherComplete(PojoRedeemResponse redeemResponse);
}
