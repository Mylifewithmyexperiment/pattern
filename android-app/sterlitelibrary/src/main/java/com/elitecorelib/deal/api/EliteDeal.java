package com.elitecorelib.deal.api;

import android.content.Context;

import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.api.EliteConnectCore;
import com.elitecorelib.core.listner.CoreTaskCompleteListner;
import com.elitecorelib.deal.listener.DealTaskCompleteListner;
import com.elitecorelib.deal.pojo.PojoDealAll;
import com.elitecorelib.deal.pojo.PojoDealAllResponse;
import com.elitecorelib.deal.pojo.PojoDealResponse;
import com.elitecorelib.deal.pojo.PojoDealTag;
import com.elitecorelib.deal.pojo.PojoDealTagResponse;
import com.elitecorelib.deal.pojo.PojoDealVoucherResponse;
import com.elitecorelib.deal.pojo.PojoRedeemResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class EliteDeal implements CoreTaskCompleteListner, IDealAPI {
    private static final String MODULE = "EliteDeal";
    private Context paramContext;
    private DealTaskCompleteListner listner;
    private List<PojoDealTag> dealTags;
    private List<PojoDealAll> allDeals;

    /**
     * @param paramContext
     * @param listner
     */
    public EliteDeal(Context paramContext, DealTaskCompleteListner listner) {
        this.paramContext = paramContext;
        this.listner = listner;

    }

    @Override
    public List<PojoDealTag> getDealTagList(String appLanguage) {
        EliteSession.eLog.i(MODULE + " Enter into get All deal tag ");
        JSONObject jsonObject = new JSONObject();
        try {
            if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_LOCAL) == 0 && !LibraryApplication.isLicenseValidated(paramContext))
                throw new Exception("Application ID is not Valid");
            jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
            jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
            jsonObject.put(DealConstants.APPLANGUAGE, appLanguage);
            jsonObject.put(CoreConstants.USERIDENTITY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.USERIDENTITY));
            EliteSession.eLog.d(MODULE + " Calling ConnectionManagerTaskNew for deal tags");
            EliteConnectCore connectCore = new EliteConnectCore(this, DealConstants.getDealTagRequestId);
            connectCore.invokeService(LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_GETDEALTAGLIST, jsonObject.toString());

        } catch (JSONException e) {
            EliteSession.eLog.e(MODULE + e.getMessage());
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + e.getMessage());
        }
        return dealTags;
    }

    @Override
    public List<PojoDealTag> getAllDeals(String appLanguage) {
        EliteSession.eLog.i(MODULE + " Enter into get All deal method");

        JSONObject jsonObject = new JSONObject();
        try {
            if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_LOCAL) == 0 && !LibraryApplication.isLicenseValidated(paramContext))
                throw new Exception("Application ID is not Valid");
            jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
            jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
            jsonObject.put(DealConstants.PAGENUMBER, 1);
            jsonObject.put(DealConstants.PAGESIZE, 30);
            jsonObject.put(DealConstants.APPLANGUAGE, appLanguage);
            jsonObject.put(CoreConstants.USERIDENTITY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.USERIDENTITY));
            EliteSession.eLog.d(MODULE + " Calling ConnectionManagerTaskNew for all deals");
            EliteConnectCore connectCore = new EliteConnectCore(this, DealConstants.getAllDelsRequestId);
            connectCore.invokeService(LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_GETALLDEALS, jsonObject.toString());

        } catch (JSONException e) {
            EliteSession.eLog.e(MODULE + e.getMessage());
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + e.getMessage());
        }

        return dealTags;

    }

    @Override
    public List<PojoDealAll> getAllDetailDeals(String appLanguage) {
        EliteSession.eLog.i(MODULE + " Enter into get All deal method");

        JSONObject jsonObject = new JSONObject();
        try {
            if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_LOCAL) == 0 && !LibraryApplication.isLicenseValidated(paramContext))
                throw new Exception("Application ID is not Valid");
            jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
            jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
            jsonObject.put(DealConstants.PAGENUMBER, 1);
            jsonObject.put(DealConstants.PAGESIZE, 30);
            jsonObject.put(DealConstants.APPLANGUAGE, appLanguage);
            jsonObject.put(CoreConstants.USERIDENTITY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.USERIDENTITY));
            EliteSession.eLog.d(MODULE + " Calling ConnectionManagerTaskNew for all deals");
            EliteConnectCore connectCore = new EliteConnectCore(this, DealConstants.getAllDealsRequestId);
            connectCore.invokeService(LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_GETDEALLIST, jsonObject.toString());

        } catch (JSONException e) {
            EliteSession.eLog.e(MODULE + e.getMessage());
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + e.getMessage());
        }

        return allDeals;

    }

    @Override
    public List<PojoDealTag> getNearByDeals() {
        EliteSession.eLog.i(MODULE + " Enter into get All deal method");

        JSONObject jsonObject = new JSONObject();
        try {
            if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_LOCAL) == 0 && !LibraryApplication.isLicenseValidated(paramContext))
                throw new Exception("Application ID is not Valid");
            jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
            jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
            jsonObject.put(DealConstants.PAGENUMBER, 1);
            jsonObject.put(DealConstants.PAGESIZE, 30);
            jsonObject.put(DealConstants.FILTERBY, DealConstants.NEARBYDEALS);
            jsonObject.put(DealConstants.LATITUDE, 23.032612699999998);
            jsonObject.put(DealConstants.LONGITUDE, 72.5618779);
            jsonObject.put(DealConstants.DISTANCE, 1000);
            jsonObject.put(CoreConstants.USERIDENTITY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.USERIDENTITY));
            EliteSession.eLog.d(MODULE + " Calling ConnectionManagerTaskNew for nearby deals");
            EliteConnectCore connectCore = new EliteConnectCore(this, DealConstants.getAllDelsRequestId);
            connectCore.invokeService(LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_GETFILTEREDDEALS, jsonObject.toString());

        } catch (JSONException e) {
            EliteSession.eLog.e(MODULE + e.getMessage());
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + e.getMessage());
        }

        return dealTags;
    }

    @Override
    public List<PojoDealTag> getDealsFromTag(long tagId) {
        EliteSession.eLog.i(MODULE + " Enter into get getDealsFromTag");

        JSONObject jsonObject = new JSONObject();
        try {
            if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_LOCAL) == 0 && !LibraryApplication.isLicenseValidated(paramContext))
                throw new Exception("Application ID is not Valid");
            jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
            jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
            jsonObject.put(DealConstants.PAGENUMBER, 1);
            jsonObject.put(DealConstants.PAGESIZE, 30);
            jsonObject.put(DealConstants.TAGID, tagId);
            jsonObject.put(CoreConstants.USERIDENTITY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.USERIDENTITY));
            EliteSession.eLog.d(MODULE + " Calling ConnectionManagerTaskNew for deals from tag");
            EliteConnectCore connectCore = new EliteConnectCore(this, DealConstants.getAllDelsRequestId);
            connectCore.invokeService(LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_GETDEALFROMTAGID, jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + e.getMessage());
        }
        return dealTags;

    }

    @Override
    public void getDealVoucher(String msisdn, long dealId, String imsi, String imei) {
        EliteSession.eLog.i(MODULE + " Enter into get getDealsFromTag");

        JSONObject jsonObject = new JSONObject();
        try {
            if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_LOCAL) == 0 && !LibraryApplication.isLicenseValidated(paramContext))
                throw new Exception("Application ID is not Valid");
            jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
            jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
            jsonObject.put(DealConstants.DEALID, dealId);
            jsonObject.put(DealConstants.MSISDN, msisdn);
            jsonObject.put(DealConstants.IMEI, imsi);
            jsonObject.put(DealConstants.IMSI, imei);
            jsonObject.put(CoreConstants.USERIDENTITY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.USERIDENTITY));
            EliteSession.eLog.d(MODULE + " Calling ConnectionManagerTaskNew for deal voucher");
            EliteConnectCore connectCore = new EliteConnectCore(this, DealConstants.getDealVoucherRequestId);
            connectCore.invokeService(LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_GETDEALVOUCHERCODE, jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + e.getMessage());
        }
    }

    @Override
    public void getMyVoucher(String msisdn) {
        EliteSession.eLog.i(MODULE + " Enter into get My Voucher List");
        JSONObject jsonObject = new JSONObject();
        try {
            if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_LOCAL) == 0 && !LibraryApplication.isLicenseValidated(paramContext)
                    && LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.USERIDENTITY) != null)
                throw new Exception("Application ID is not Valid");
            jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
            jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
            jsonObject.put(DealConstants.MSISDN, msisdn);
            jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
            jsonObject.put(CoreConstants.USERIDENTITY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.USERIDENTITY));
            EliteSession.eLog.d(MODULE + " Calling ConnectionManagerTaskNew for deal My voucher List");
            EliteConnectCore connectCore = new EliteConnectCore(this, DealConstants.getMyVouchersRequestId);
            connectCore.invokeService(LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_GETVOUCHERLIST, jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + e.getMessage());
        }
    }

    @Override
    public void getDealInfo(long dealId) {
        EliteSession.eLog.i(MODULE + " Enter into get Ged Deal Info");

        JSONObject jsonObject = new JSONObject();
        try {
            if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_LOCAL) == 0 && !LibraryApplication.isLicenseValidated(paramContext))
                throw new Exception("Application ID is not Valid");
            jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
            jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
            jsonObject.put(DealConstants.DEALID, dealId);
            jsonObject.put(CoreConstants.USERIDENTITY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.USERIDENTITY));
            EliteSession.eLog.d(MODULE + " Calling ConnectionManagerTaskNew for deal info");
            EliteConnectCore connectCore = new EliteConnectCore(this, DealConstants.getDealInfoRequestId);
            connectCore.invokeService(LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_GETDEALINFO, jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + e.getMessage());
        }
    }

    @Override
    public void redeemDeal(String voucherId, String voucherCode,
                           String merchentId, String merchentPin) throws Exception {
        // TODO Auto-generated method stub
        EliteSession.eLog.i(MODULE + " Enter into Redeem Service");

        JSONObject jsonObject = new JSONObject();
        try {
            if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_LOCAL) == 0 && !LibraryApplication.isLicenseValidated(paramContext)
                    && LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.USERIDENTITY) != null)
                throw new Exception("Application ID is not Valid");
            jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
            jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
            jsonObject.put(DealConstants.DEALVOUCHERDETAILID, voucherId);
            jsonObject.put(DealConstants.VOUCHERCODE, voucherCode);
            jsonObject.put(DealConstants.MERCHENTID, merchentId);
            jsonObject.put(DealConstants.MERCHENTPIN, merchentPin);
            jsonObject.put(CoreConstants.USERIDENTITY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.USERIDENTITY));
            EliteSession.eLog.d(MODULE + " Calling ConnectionManagerTaskNew for Redeem Voucher");
            EliteConnectCore connectCore = new EliteConnectCore(this, DealConstants.redeemVoucherId);
            connectCore.invokeService(LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_REDEEMDEALVOUCHERCODE, jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + e.getMessage());
        }
    }

    /**
     * @param - result : It is json string get by webservice call.
     *          <p>
     *          onServiceTaskComplete method called when webservice call completed.
     *          This method parse the JSON string and translate into List.
     */
    @Override
    public void onServiceTaskComplete(String result, int requestId) {
        EliteSession.eLog.i(MODULE + " onConnnectionManagerTaskComplete Result is " + result);

        try {
            if (requestId == DealConstants.getDealTagRequestId) {
                PojoDealTagResponse resObj = null;
                if (result != null && result.trim().compareTo("") != 0) {
                    try {
                        Gson gson = new Gson();
                        resObj = gson.fromJson(result, PojoDealTagResponse.class);
                        EliteSession.eLog.i(MODULE, "After compelete json process data are " + resObj);
                        dealTags = resObj.getResponseDataList();
                        listner.onDealTagComplete(dealTags);
                    } catch (Exception e) {
                        // TODO: handle exception
                        EliteSession.eLog.e(MODULE, "Error to get deals " + e.getMessage());
                        listner.onDealTagComplete(null);
                    }
                } else {
                    EliteSession.eLog.i(MODULE, "Time out or empty data for getting deal tags " + resObj);
                    listner.onDealTagComplete(null);

                }
            } else if (requestId == DealConstants.getAllDelsRequestId) {
                if (result != null && result.trim().compareTo("") != 0) {

                    try {
                        PojoDealResponse dealResponse = null;
                        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd");
                        Gson gson = gsonBuilder.create();
                        dealResponse = gson.fromJson(result, PojoDealResponse.class);
                        EliteSession.eLog.i(MODULE, "After compelete json process data are " + dealResponse);
                        listner.onDealComplete(dealResponse.getResponseData());
                    } catch (Exception e) {
                        // TODO: handle exception
                        EliteSession.eLog.e(MODULE, "Error while loading deals " + e.getMessage());
                        listner.onDealComplete(null);
                    }
                } else {
                    EliteSession.eLog.i(MODULE, "Timeout or empty Data getting all deal" + result);
                    listner.onDealComplete(null);
                }
            }
            /*
			 * New all deals service 
			 */
            else if (requestId == DealConstants.getAllDealsRequestId || requestId == DealConstants.getDealInfoRequestId) {
                if (result != null && result.trim().compareTo("") != 0) {

                    try {
                        PojoDealAllResponse dealResponse = null;
                        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd");
                        Gson gson = gsonBuilder.create();
                        dealResponse = gson.fromJson(result, PojoDealAllResponse.class);
                        EliteSession.eLog.i(MODULE, "After compelete json process data are " + dealResponse);
                        listner.onAllDealComplete(dealResponse.getResponseData());
                    } catch (Exception e) {
                        // TODO: handle exception
                        EliteSession.eLog.e(MODULE, "Error while loading deals " + e.getMessage());
                        listner.onAllDealComplete(null);
                    }
                } else {
                    EliteSession.eLog.i(MODULE, "Timeout or empty Data getting all deal" + result);
                    listner.onAllDealComplete(null);
                }
            } else if (requestId == DealConstants.getDealVoucherRequestId || requestId == DealConstants.getMyVouchersRequestId) {
                if (result != null && result.trim().compareTo("") != 0) {
                    try {
                        PojoDealVoucherResponse dealVoucherResponse = null;
                        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd");
                        Gson gson = gsonBuilder.create();
                        dealVoucherResponse = gson.fromJson(result, PojoDealVoucherResponse.class);
                        EliteSession.eLog.i(MODULE, "After compelete json process data are " + dealVoucherResponse);
                        listner.onDealVoucherComplete(dealVoucherResponse);
                    } catch (Exception e) {
                        // TODO: handle exception
                        EliteSession.eLog.e(MODULE, "Errow while generating voucher " + e.getMessage());
                        listner.onDealVoucherComplete(null);
                    }

                } else {
                    EliteSession.eLog.i(MODULE, "Timeout or empty Data for deal Voucher" + result);
                    listner.onDealVoucherComplete(null);
                }

            } else if (requestId == DealConstants.redeemVoucherId) {
                if (result != null && result.trim().compareTo("") != 0) {
                    if (result != null && result.trim().compareTo("") != 0) {
                        try {
                            EliteSession.eLog.i(MODULE, " Reuslt for Redeem Voucher id" + result);
                            PojoRedeemResponse dealRedeemResponse = null;
                            GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd");
                            Gson gson = gsonBuilder.create();
                            dealRedeemResponse = gson.fromJson(result, PojoRedeemResponse.class);
                            EliteSession.eLog.i(MODULE, "After compelete json process data are " + dealRedeemResponse);
                            listner.onRedeemVoucherComplete(dealRedeemResponse);
                        } catch (Exception e) {
                            // TODO: handle exception
                            EliteSession.eLog.e(MODULE, "Errow while generating voucher " + e.getMessage());
                            listner.onDealVoucherComplete(null);
                        }

                    } else {
                        EliteSession.eLog.i(MODULE, "Timeout or empty Data for Redeem Voucher" + result);
                        listner.onDealVoucherComplete(null);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
