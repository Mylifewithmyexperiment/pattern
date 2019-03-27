package com.elitecorelib.deal.api;

import java.util.List;

import com.elitecorelib.deal.pojo.PojoDealAll;
import com.elitecorelib.deal.pojo.PojoDealTag;

public interface IDealAPI {
	
	public List<PojoDealTag> getDealTagList(String appLanguage) throws Exception;
	public List<PojoDealTag> getAllDeals(String appLanguage) throws Exception;
	public List<PojoDealAll> getAllDetailDeals(String appLanguage) throws Exception;
	public List<PojoDealTag> getNearByDeals() throws Exception;
	public List<PojoDealTag> getDealsFromTag(long tagId) throws Exception;
	public void getDealVoucher(String msisdn, long dealId, String imsi, String imei) throws Exception;
	public void getMyVoucher(String msisdn) throws Exception;
	public void getDealInfo(long dealId) throws Exception;
	public void redeemDeal(String voucherId, String voucherCode, String merchentId, String merchentPin) throws Exception;
	
}
