

package com.sterlite.dccmappfordealersterlite.data;


import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.data.db.DbHelper;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.data.network.model.DeviceToken;
import com.sterlite.dccmappfordealersterlite.data.network.model.Pagging;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.data.prefs.PreferencesHelper;
import com.sterlite.dccmappfordealersterlite.model.Activation.ActivationResponseModel;
import com.sterlite.dccmappfordealersterlite.model.CustomerListWsDTO;
import com.sterlite.dccmappfordealersterlite.model.FindSubscriptionByEmailModel;
import com.sterlite.dccmappfordealersterlite.model.HomeFragment.BalanceData;
import com.sterlite.dccmappfordealersterlite.model.HomeFragment.SubscriberServicewiseBalance;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel.MakeMyPlanItems;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel.MakeMyPlanLists;
import com.sterlite.dccmappfordealersterlite.model.MyOrdersListing.MyOrdersListResponseModel;
import com.sterlite.dccmappfordealersterlite.model.MyOrdersListing.Order;
import com.sterlite.dccmappfordealersterlite.model.OrderStatus;
import com.sterlite.dccmappfordealersterlite.model.OrderTracking.OrderTrackingResponseModel;
import com.sterlite.dccmappfordealersterlite.model.PPP4U.PostPaidPlansForYouDTO;
import com.sterlite.dccmappfordealersterlite.model.Store;
import com.sterlite.dccmappfordealersterlite.model.SubscribeAddOn.SubscribeAddOnResponceModel;
import com.sterlite.dccmappfordealersterlite.model.Ticket;
import com.sterlite.dccmappfordealersterlite.model.Track;
import com.sterlite.dccmappfordealersterlite.model.PlanPackage;
import com.sterlite.dccmappfordealersterlite.model.UpdateCustomer.GetUpdateCustomerRequestData;
import com.sterlite.dccmappfordealersterlite.model.UpdateCustomer.GetUpdateCustomerResponseData;
import com.sterlite.dccmappfordealersterlite.model.bssrest.BillingDetailData;
import com.sterlite.dccmappfordealersterlite.model.bssrest.PaymentHistory.SearchPaymentResponseData;
import com.sterlite.dccmappfordealersterlite.model.bssrest.ServiceInstanceDetail;
import com.sterlite.dccmappfordealersterlite.model.customer.BaseResponse;

public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    public enum ClearCatchType {
        CLEAR_ALL,
        CLEAR_RECENT_CLOSE,
        CLEAR_EMERGENCY_CONTACT
    }

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    public AppDataManager(Context context,
                          DbHelper dbHelper,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }


    @Override
    public void getMakeMyPlan(final OnApiCallback<MakeMyPlanLists> onApiCallback) {
        final MakeMyPlanLists makeMyPlanLists = new MakeMyPlanLists();
        getMakeMyPlanBasicPlan(new OnApiCallback<Pagging<MakeMyPlanItems>>() {
            @Override
            public void onSuccess(Pagging<MakeMyPlanItems> baseResponse) {
                makeMyPlanLists.setArrBasicPlan(baseResponse.getArrList());

                getMakeMyPlanDataAddOn(new OnApiCallback<Pagging<MakeMyPlanItems>>() {
                    @Override
                    public void onSuccess(Pagging<MakeMyPlanItems> baseResponse) {
                        makeMyPlanLists.setArrDataAddOn(baseResponse.getArrList());

                        getMakeMyPlanDataSubAddOn(new OnApiCallback<Pagging<MakeMyPlanItems>>() {
                            @Override
                            public void onSuccess(Pagging<MakeMyPlanItems> baseResponse) {
                                makeMyPlanLists.setArrDataSubAddOn(baseResponse.getArrList());

                                getMakeMyPlanMinAddOn(new OnApiCallback<Pagging<MakeMyPlanItems>>() {
                                    @Override
                                    public void onSuccess(Pagging<MakeMyPlanItems> baseResponse) {
                                        makeMyPlanLists.setArrMinAddOn(baseResponse.getArrList());

                                        getMakeMyPlanSMSAddOn(new OnApiCallback<Pagging<MakeMyPlanItems>>() {
                                            @Override
                                            public void onSuccess(Pagging<MakeMyPlanItems> baseResponse) {
                                                makeMyPlanLists.setArrSMSAddOn(baseResponse.getArrList());

                                                getMakeMyPlanRoamingAddOn(new OnApiCallback<Pagging<MakeMyPlanItems>>() {
                                                    @Override
                                                    public void onSuccess(Pagging<MakeMyPlanItems> baseResponse) {
                                                        makeMyPlanLists.setArrRoamingAddOn(baseResponse.getArrList());
                                                        onApiCallback.onSuccess(makeMyPlanLists);
                                                    }

                                                    @Override
                                                    public void onFailed(int code, String message) {
                                                        onApiCallback.onFailed(code, message);
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onFailed(int code, String message) {
                                                onApiCallback.onFailed(code, message);
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailed(int code, String message) {
                                        onApiCallback.onFailed(code, message);
                                    }
                                });

                            }

                            @Override
                            public void onFailed(int code, String message) {
                                onApiCallback.onFailed(code, message);
                            }
                        });


                    }

                    @Override
                    public void onFailed(int code, String message) {
                        onApiCallback.onFailed(code, message);
                    }
                });
            }

            @Override
            public void onFailed(int code, String message) {
                onApiCallback.onFailed(code, message);
            }
        });
    }


    /**
     * API METHODS START
     */
    @Override
    public void doGetMyAccountData(String subscriberIdentifier, String tenantId, final OnApiCallback callback) {
        mApiHelper.doGetMyAccountData(subscriberIdentifier, tenantId, new OnApiCallback<SubscriberServicewiseBalance>() {
            @Override
            public void onSuccess(SubscriberServicewiseBalance baseResponse) {
                makeTotalOfTime(baseResponse);
            }

            private void makeTotalOfTime(SubscriberServicewiseBalance baseResponse) {

                BalanceData balanceDataTime = null;
                BalanceData balanceDataVolume = null;
                BalanceData balanceData3gVolume = null;
                BalanceData balanceDataEvent = null;
                BalanceData balanceDataAmount = null;


                for (BalanceData data : baseResponse.getBalanceDatas()) {
                    if (data.getBalanceType().equalsIgnoreCase("TIME")) {
                        if (balanceDataTime == null) {
                            balanceDataTime = data;
                            continue;
                        }
                        balanceDataTime.setAvailableBalance(balanceDataTime.getAvailableBalance() + data.getAvailableBalance());
                        balanceDataTime.setTotalUsageBalance(balanceDataTime.getTotalUsageBalance() + data.getTotalUsageBalance());
                    } else if (data.getBalanceType().equalsIgnoreCase("VOLUME")) {
                        if(data.getServiceAlias().equalsIgnoreCase("DATA")){
//                        if(data.getTotalUsageBalance() == 2147483648f){
                            if (balanceData3gVolume == null) {
                                balanceData3gVolume = data;
                                continue;
                            }
                            balanceData3gVolume.setAvailableBalance(balanceData3gVolume.getAvailableBalance() + data.getAvailableBalance());
                            balanceData3gVolume.setTotalUsageBalance(balanceData3gVolume.getTotalUsageBalance() + data.getTotalUsageBalance());
                        }
                        else {
                            if (balanceDataVolume == null) {
                                balanceDataVolume = data;
                                continue;
                            }
                            balanceDataVolume.setAvailableBalance(balanceDataVolume.getAvailableBalance() + data.getAvailableBalance());
                            balanceDataVolume.setTotalUsageBalance(balanceDataVolume.getTotalUsageBalance() + data.getTotalUsageBalance());
                        }
                    } else if (data.getBalanceType().equalsIgnoreCase("EVENT")) {
                        if (balanceDataEvent == null) {
                            balanceDataEvent = data;
                            continue;
                        }
                        balanceDataEvent.setAvailableBalance(balanceDataEvent.getAvailableBalance() + data.getAvailableBalance());
                        balanceDataEvent.setTotalUsageBalance(balanceDataEvent.getTotalUsageBalance() + data.getTotalUsageBalance());
                    } else if (data.getBalanceType().equalsIgnoreCase("AMOUNT")) {
                        if (balanceDataAmount == null) {
                            balanceDataAmount = data;
                            continue;
                        }
                        balanceDataAmount.setAvailableBalance(balanceDataAmount.getAvailableBalance() + data.getAvailableBalance());
                        balanceDataAmount.setTotalUsageBalance(balanceDataAmount.getTotalUsageBalance() + data.getTotalUsageBalance());
                    }
                }

                List<BalanceData> arrDataList = new ArrayList<>();
                TypedValue typedValue =  new TypedValue();
                mContext.getTheme().resolveAttribute(R.attr.colorMain,typedValue,true);

                if (balanceDataTime != null) {
                    balanceDataTime.setBalanceType("TIME");
                    balanceDataTime.setMenuResId(R.drawable.ic_minutes);
                    balanceDataTime.setMenuCirleColor(typedValue.resourceId);
                    balanceDataTime.setMenuName(DCCMDealerSterlite.appContext.getString(R.string.minutes));
                    arrDataList.add(balanceDataTime);
                }
                if (balanceData3gVolume != null) {
                    balanceData3gVolume.setBalanceType("VOLUME");
                    balanceData3gVolume.setMenuResId(R.drawable.ic_data);
                    balanceData3gVolume.setMenuCirleColor(typedValue.resourceId);
                    balanceData3gVolume.setMenuName(DCCMDealerSterlite.appContext.getString(R.string.three_g_data));
                    arrDataList.add(balanceData3gVolume);
                }
                if (balanceDataVolume != null) {
                    balanceDataVolume.setBalanceType("VOLUME");
                    balanceDataVolume.setMenuResId(R.drawable.ic_data);
                    balanceDataVolume.setMenuCirleColor(typedValue.resourceId);
                    balanceDataVolume.setMenuName(DCCMDealerSterlite.appContext.getString(R.string.data));
                    arrDataList.add(balanceDataVolume);
                }
                if (balanceDataEvent != null) {
                    balanceDataEvent.setBalanceType("EVENT");
                    balanceDataEvent.setMenuResId(R.drawable.ic_message);
                    balanceDataEvent.setMenuCirleColor(typedValue.resourceId);
                    balanceDataEvent.setMenuName(DCCMDealerSterlite.appContext.getString(R.string.sms));
                    arrDataList.add(balanceDataEvent);
                }
                if (balanceDataAmount != null) {
                    balanceDataAmount.setBalanceType("AMOUNT");
                    balanceDataAmount.setMenuResId(R.drawable.ic_shop_prepaid);
                    balanceDataAmount.setMenuCirleColor(typedValue.resourceId);
                    balanceDataAmount.setMenuName(DCCMDealerSterlite.appContext.getString(R.string.balance));
                    arrDataList.add(balanceDataAmount);
                }
                baseResponse.setBalanceDatas(arrDataList);

                if (arrDataList.size() > 0) {
                    Date validTodate = new Date((long) arrDataList.get(0).getValidToDate());
                    Date today = new Date();
                    baseResponse.setDaysLeft(AppUtils.getDifferenceDays(today, validTodate));
                } else {
                    baseResponse.setDaysLeft(AppUtils.getDifferenceDays(new Date(), new Date()));
                }
                callback.onSuccess(baseResponse);
            }

            @Override
            public void onFailed(int code, String message) {
                callback.onFailed(code, message);
            }
        });
    }

    @Override
    public void getMakeMyPlanBasicPlan(OnApiCallback<Pagging<MakeMyPlanItems>> onApiCallback) {
        mApiHelper.getMakeMyPlanBasicPlan(onApiCallback);
    }

    @Override
    public void getMakeMyPlanDataAddOn(OnApiCallback<Pagging<MakeMyPlanItems>> onApiCallback) {
        mApiHelper.getMakeMyPlanDataAddOn(onApiCallback);
    }

    @Override
    public void getMakeMyPlanDataSubAddOn(OnApiCallback<Pagging<MakeMyPlanItems>> onApiCallback) {
        mApiHelper.getMakeMyPlanDataSubAddOn(onApiCallback);
    }

    @Override
    public void getMakeMyPlanMinAddOn(OnApiCallback<Pagging<MakeMyPlanItems>> onApiCallback) {
        mApiHelper.getMakeMyPlanMinAddOn(onApiCallback);
    }

    @Override
    public void getMakeMyPlanSMSAddOn(OnApiCallback<Pagging<MakeMyPlanItems>> onApiCallback) {
        mApiHelper.getMakeMyPlanSMSAddOn(onApiCallback);
    }

    @Override
    public void getMakeMyPlanRoamingAddOn(OnApiCallback<Pagging<MakeMyPlanItems>> onApiCallback) {
        mApiHelper.getMakeMyPlanRoamingAddOn(onApiCallback);
    }

    @Override
    public void doActivateMyOrder(String orderNo, String serviceNo, OnApiCallback<ActivationResponseModel> apiCallback) {
        mApiHelper.doActivateMyOrder(orderNo, serviceNo, apiCallback);
    }

    @Override
    public void doGetOrderTrackingDetails(String serialNo, final OnApiCallback<OrderTrackingResponseModel> onApiCallback) {
        mApiHelper.doGetOrderTrackingDetails(serialNo, new OnApiCallback<OrderTrackingResponseModel>() {
            @Override
            public void onSuccess(OrderTrackingResponseModel baseResponse) {
                String currentStatus = baseResponse.getCurrentOrderStatus();
                boolean isSetStatusCompeleted = true;
                boolean isSetStatusActive = false;
                for (int i = 0; i < baseResponse.getStatuses().size(); i++) {
                    Track track = new Track();
                    String messsage = null;
                    if (isSetStatusCompeleted) {
                        track.setStatus(OrderStatus.COMPLETED);
                    } else if (isSetStatusActive) {
                        track.setStatus(OrderStatus.ACTIVE);
                    } else {
                        track.setStatus(OrderStatus.INACTIVE);
                    }
                    switch (baseResponse.getStatuses().get(i)) {
                        case "CONFIRMED":
                            messsage = DCCMDealerSterlite.appContext.getString(R.string.order_confirm);
                            break;
                        case "ASSIGNED_FOR_DELIVERY":
                            messsage = DCCMDealerSterlite.appContext.getString(R.string.order_assign_for_delivery);
                            break;
                        case "APPOINTMENT_CONFIRMED":
                            messsage = DCCMDealerSterlite.appContext.getString(R.string.order_appointment_confirm);
                            break;
                        case "OUT_FOR_DELIVERY":
                            messsage = DCCMDealerSterlite.appContext.getString(R.string.order_out_for_delivery);
                            break;
                        case "SIM_DELIVERED":
                            messsage = DCCMDealerSterlite.appContext.getString(R.string.order_sim_delivered);
                            break;
                        case "SIM_ACTIVATED":
                            messsage = DCCMDealerSterlite.appContext.getString(R.string.order_sim_activated);
                            break;
                        default:
                            messsage = baseResponse.getStatuses().get(i);
                            break;
                    }
                    track.setMessage(messsage);
                    if (isSetStatusCompeleted && currentStatus.equalsIgnoreCase(baseResponse.getStatuses().get(i))) {
                        isSetStatusCompeleted = false;
                        isSetStatusActive = true;
                    } else if (isSetStatusActive) {
                        isSetStatusActive = false;
                    }
                    baseResponse.getTrackArrayList().add(track);
                }
                String currentOrderStatusMessage;
                switch (baseResponse.getCurrentOrderStatus()) {
                    case "CONFIRMED":
                        currentOrderStatusMessage = DCCMDealerSterlite.appContext.getString(R.string.order_confirm);
                        break;
                    case "ASSIGNED_FOR_DELIVERY":
                        currentOrderStatusMessage = DCCMDealerSterlite.appContext.getString(R.string.order_assign_for_delivery);
                        break;
                    case "APPOINTMENT_CONFIRMED":
                        currentOrderStatusMessage = DCCMDealerSterlite.appContext.getString(R.string.order_appointment_confirm);
                        break;
                    case "OUT_FOR_DELIVERY":
                        currentOrderStatusMessage = DCCMDealerSterlite.appContext.getString(R.string.order_out_for_delivery);
                        break;
                    case "SIM_DELIVERED":
                        currentOrderStatusMessage = DCCMDealerSterlite.appContext.getString(R.string.order_sim_delivered);
                        break;
                    case "SIM_ACTIVATED":
                        currentOrderStatusMessage = DCCMDealerSterlite.appContext.getString(R.string.order_sim_activated);
                        break;
                    default:
                        currentOrderStatusMessage = baseResponse.getCurrentOrderStatus();
                        break;
                }
                baseResponse.setCurrentOrderStatusMessage(currentOrderStatusMessage);
                onApiCallback.onSuccess(baseResponse);
            }

            @Override
            public void onFailed(int code, String message) {
                onApiCallback.onFailed(code, message);
            }
        });
    }

    @Override
    public void getPlanPackageInternet(OnApiCallback<Pagging<PlanPackage>> onApiCallback) {
        mApiHelper.getPlanPackageInternet(onApiCallback);
    }

    @Override
    public void getPlanPackageRoaming(OnApiCallback<Pagging<PlanPackage>> onApiCallback) {
        mApiHelper.getPlanPackageRoaming(onApiCallback);
    }

    @Override
    public void getPlanPackageEntertainment(OnApiCallback<Pagging<PlanPackage>> onApiCallback) {
        mApiHelper.getPlanPackageEntertainment(onApiCallback);
    }

    @Override
    public void getPlanPackageSms(OnApiCallback<Pagging<PlanPackage>> onApiCallback) {
        mApiHelper.getPlanPackageSms(onApiCallback);
    }

    @Override
    public void doSubscribeAddOn(String paymentMode,String subscriptionNumber, String productName, OnApiCallback<SubscribeAddOnResponceModel> onApiCallback) {
        mApiHelper.doSubscribeAddOn(paymentMode,subscriptionNumber, productName, onApiCallback);
    }

    @Override
    public void getMyOrders(final OnApiCallback<MyOrdersListResponseModel> onApiCallback) {
        mApiHelper.getMyOrders(new OnApiCallback<MyOrdersListResponseModel>() {
            @Override
            public void onSuccess(MyOrdersListResponseModel baseResponse) {
                for (Order order : baseResponse.getOrders()) {
                    order.setTotal(order.getObjTotal().getFormattedValue());
                }
                onApiCallback.onSuccess(baseResponse);
            }

            @Override
            public void onFailed(int code, String message) {
                onApiCallback.onFailed(code, message);
            }
        });
    }


    @Override
    public void getMyCart(final OnApiCallback<String> onApiCallback) {
        mApiHelper.getMyCart(new OnApiCallback<String>() {
            @Override
            public void onSuccess(String baseResponse) {
                if (!TextUtils.isEmpty(baseResponse))
                    DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.PREF_CART_IDS, baseResponse);
                onApiCallback.onSuccess(baseResponse);
            }

            @Override
            public void onFailed(int code, String message) {
                onApiCallback.onFailed(code, message);
            }
        });
    }

    @Override
    public void doRegisterDeviceTokenToServer(DeviceToken device, OnApiCallback<DeviceToken> onApiCallback) {
        mApiHelper.doRegisterDeviceTokenToServer(device, onApiCallback);
    }
    /*=============API METHODS END===============*/


    /*
     * DATABASE METHODS START
     */


    /*=============DATABASE METHODS END===============*/

    /**
     * PREFERENCE METHODS START
     */

    @Override
    public String get(String key, String defaultValue) {
        return mPreferencesHelper.get(key, defaultValue);
    }

    @Override
    public void set(String key, String value) {
        mPreferencesHelper.set(key, value);
    }

    @Override
    public void setBoolean(String key, boolean value) {
        mPreferencesHelper.setBoolean(key, value);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return mPreferencesHelper.getBoolean(key, defaultValue);
    }

    @Override
    public void setint(String key, int value) {
        mPreferencesHelper.setint(key, value);
    }

    @Override
    public int getint(String key, int defaultValue) {
        return mPreferencesHelper.getint(key, defaultValue);
    }

    @Override
    public void clear() {
        mPreferencesHelper.clear();
    }

    @Override
    public void remove(String key) {
        mPreferencesHelper.remove(key);
    }

    @Override
    public void setAppPref(String key, String value) {
        mPreferencesHelper.setAppPref(key, value);
    }

    @Override
    public String getAppPref(String key, String defaultValue) {
        return mPreferencesHelper.getAppPref(key, defaultValue);
    }

    @Override
    public void setBooleanAppPref(String key, boolean value) {
        mPreferencesHelper.setBooleanAppPref(key, value);
    }

    @Override
    public boolean getBooleanAppPref(String key, boolean defaultValue) {
        return mPreferencesHelper.getBooleanAppPref(key, defaultValue);
    }

    @Override
    public Map<String,String> loadMap(String key1) {
        return mPreferencesHelper.loadMap(key1);
    }

    @Override
    public void saveMap(String key,Map<String,String> defaultValue) {
         mPreferencesHelper.saveMap(key, defaultValue);
    }


    /*=============PREFERENCE METHODS END===============*/

    @Override

    public void findSubscriptionByEmailId(String userId, OnApiCallback<FindSubscriptionByEmailModel> onApiCallback) {
        mApiHelper.findSubscriptionByEmailId(userId, onApiCallback);

    }

    @Override
    public void getCustomerDetails(String userId,OnApiCallback<ServiceInstanceDetail[]> onApiCallback){
        mApiHelper.getCustomerDetails(userId,onApiCallback);
    }

    @Override
    public void getInventoryNumber(String userId,OnApiCallback<String> onApiCallback){
        mApiHelper.getInventoryNumber(userId,onApiCallback);
    }


    @Override
    public void findStore(String pincode,OnApiCallback<ArrayList<Store>> onApiCallback){
        mApiHelper.findStore(pincode,onApiCallback);
    }


    @Override
    public void getSIDetails(String accountNumber, final OnApiCallback<ServiceInstanceDetail> onApiCallback){
        mApiHelper.getSIDetails(accountNumber,onApiCallback);
    }

    @Override
    public void doUnSubscribeAddOn(String subscriptionNumber, String externalSystemOfferId, final OnApiCallback<String> onApiCallback){
        mApiHelper.doUnSubscribeAddOn(subscriptionNumber,externalSystemOfferId,onApiCallback);
    }

    public void getProductbyCode(String productCode,final OnApiCallback<String> onApiCallback) {
        mApiHelper.getProductbyCode(productCode,onApiCallback);

    }

    public void getBillDetails(String billingAccountNumber, final OnApiCallback<BillingDetailData[]> onApiCallback){

        mApiHelper.getBillDetails(billingAccountNumber,onApiCallback);

    }

    public void downloadBill(String billNumber, final OnApiCallback<String> onApiCallback)
    {
        mApiHelper.downloadBill(billNumber,onApiCallback);

    }

    @Override
    public void getPaymentHistory(String billingAccNo, OnApiCallback<SearchPaymentResponseData> onApiCallback){
        mApiHelper.getPaymentHistory(billingAccNo,onApiCallback);
    }
    @Override
    public void uploadDocumentAPI(String customerID,String imageKey ,String imagePath, final OnApiCallback<Integer> onApiCallback) {
        mApiHelper.uploadDocumentAPI(customerID,imageKey,imagePath, onApiCallback);
    }

    /*public void deleteMyCart(final String cartId,final OnApiCallback<String> onApiCallback) {
        mApiHelper.deleteMyCart(cartId, onApiCallback);

    }*/
    @Override
    public void getPostPaidPlansForYou(String customerID, String categoryCode, OnApiCallback<PostPaidPlansForYouDTO> onApiCallback) {
        mApiHelper.getPostPaidPlansForYou(customerID, categoryCode, onApiCallback);
    }

    @Override
    public void changePlan(String planName, OnApiCallback<BaseResponse> onApiCallback){
        mApiHelper.changePlan(planName,onApiCallback);
    }

    public void findCustomerByDealerId(String dealerID, final OnApiCallback<CustomerListWsDTO> onApiCallback) {
        mApiHelper.findCustomerByDealerId(dealerID,onApiCallback);

    }

    @Override
    public void createTicket(Ticket ticket, final OnApiCallback<Ticket> onApiCallback) {
        mApiHelper.createTicket(ticket,onApiCallback);
    }
    @Override
    public void getTicketCategory(final OnApiCallback<ArrayList<String>> onApiCallback) {
        mApiHelper.getTicketCategory(onApiCallback);
    }
    @Override
    public void getTicketSubCategory(String category , final OnApiCallback<ArrayList<String>> onApiCallback){
        mApiHelper.getTicketSubCategory(category,onApiCallback);
    }

    @Override
    public void getTicketByCustomer(String customerNumber , final OnApiCallback<ArrayList<Ticket>> onApiCallback){
        mApiHelper.getTicketByCustomer(customerNumber ,onApiCallback);
    }

    @Override
    public void updateCustomerProfileDetails(final GetUpdateCustomerRequestData getUpdateCustomerRequestData, final OnApiCallback<GetUpdateCustomerResponseData> onApiCallback) {
        mApiHelper.updateCustomerProfileDetails(getUpdateCustomerRequestData ,onApiCallback);

    }


    @Override
    public void getProfileDetailsbyCustomerNumber(String customerNumber , final OnApiCallback<GetUpdateCustomerResponseData> onApiCallback)
    {
        mApiHelper.getProfileDetailsbyCustomerNumber(customerNumber ,onApiCallback);

    }

}

