package com.sterlite.dccmappfordealersterlite.data.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.Utils.DummyLists;
import com.sterlite.dccmappfordealersterlite.activity.About.AboutActivity;
import com.sterlite.dccmappfordealersterlite.apiHelper.RestHelper;
import com.sterlite.dccmappfordealersterlite.apiHelper.RestResponse;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.data.network.model.DeviceToken;
import com.sterlite.dccmappfordealersterlite.data.network.model.Pagging;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.model.Activation.ActivationResponseModel;
import com.sterlite.dccmappfordealersterlite.model.CustomerListWsDTO;
import com.sterlite.dccmappfordealersterlite.model.FindSubscriptionByEmailModel;
import com.sterlite.dccmappfordealersterlite.model.HomeFragment.SubscriberServicewiseBalance;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel.MakeMyPlanItems;
import com.sterlite.dccmappfordealersterlite.model.MyOrdersListing.MyOrdersListResponseModel;
import com.sterlite.dccmappfordealersterlite.model.OrderTracking.OrderTrackingResponseModel;
import com.sterlite.dccmappfordealersterlite.model.PPP4U.PostPaidPlansForYouDTO;
import com.sterlite.dccmappfordealersterlite.model.PlanPackage;
import com.sterlite.dccmappfordealersterlite.model.Store;
import com.sterlite.dccmappfordealersterlite.model.SubscribeAddOn.SubscribeAddOnResponceModel;
import com.sterlite.dccmappfordealersterlite.model.Ticket;
import com.sterlite.dccmappfordealersterlite.model.UpdateCustomer.GetUpdateCustomerRequestData;
import com.sterlite.dccmappfordealersterlite.model.UpdateCustomer.GetUpdateCustomerResponseData;
import com.sterlite.dccmappfordealersterlite.model.bssrest.BillingDetailData;
import com.sterlite.dccmappfordealersterlite.model.bssrest.PaymentHistory.SearchPaymentResponseData;
import com.sterlite.dccmappfordealersterlite.model.bssrest.ServiceInstanceDetail;
import com.sterlite.dccmappfordealersterlite.model.customer.BaseResponse;

public class AppApiHelper implements ApiHelper {


 //   public static final String BASE_URL = "http://" + Constants.IP + ":9001/";



  //  public static final String BASE_HTTPS_URL = "https://" + Constants.IP + ":9002/";
    public static final String BASE_TOKEN_URL = "http://dccm.etechservices.biz.md-in-56.webhostbox.net/";
    public static final String CRM_BASE_URL = "http://" + Constants.CRM_IP + "/";

//    public static final String BSS_ADAPTER_BASE_URL = "http://" + Constants.BSS_Adapter_IP + ":18080/";
    public static final String BSS_ADAPTER = "bssadapter/services/";
 //   public static final String CRM = "stlcrm/";


    private static final String BASE_URL_ADDITITON = "telcocommercewebservices/";
    private static final String API_SEARCH_PRODUCT_CATEGORY = "v2/b2ctelco/products/searchCategoryProduct?categoryCode=";
    private static final String API_SEARCH_PRODUCT_CODE = BASE_URL_ADDITITON + "v2/b2ctelco/products/";


    private static final String API_REGISTER_DEVICE_TOKEN = "api/User/registerDevice";
    private static final String API_MAKE_MY_PLAN_BASIC_PLAN = BASE_URL_ADDITITON + API_SEARCH_PRODUCT_CATEGORY + Constants.MMP_BASIC;
    private static final String API_MAKE_MY_PLAN_DATA_ADDON = BASE_URL_ADDITITON + API_SEARCH_PRODUCT_CATEGORY + Constants.MMP_ADDON_CATEGORY_DATA;
    private static final String API_MAKE_MY_PLAN_DATA_SUB_ADDON = BASE_URL_ADDITITON + API_SEARCH_PRODUCT_CATEGORY + Constants.MMP_ADDON_CATEGORY_DATA_SUB;
    private static final String API_MAKE_MY_PLAN_MIN_ADDON = BASE_URL_ADDITITON + API_SEARCH_PRODUCT_CATEGORY + Constants.MMP_ADDON_CATEGORY_VOICE;
    private static final String API_MAKE_MY_PLAN_SMS_ADDON = BASE_URL_ADDITITON + API_SEARCH_PRODUCT_CATEGORY + Constants.MMP_ADDON_CATEGORY_SMS;
    private static final String API_MAKE_MY_PLAN_ROAMING_ADDON = BASE_URL_ADDITITON + API_SEARCH_PRODUCT_CATEGORY + Constants.MMP_ADDON_CATEGORY_ROM;

    private static final String API_PLAN_PACK_INTERNET_PRE = BASE_URL_ADDITITON + API_SEARCH_PRODUCT_CATEGORY + Constants.PLAN_PACK_INTERNET_PRE;
    private static final String API_PLAN_PACK_ENTERTAINMENT_PRE = BASE_URL_ADDITITON + API_SEARCH_PRODUCT_CATEGORY + Constants.PLAN_PACK_ENTERTAINMENT_PRE;
    private static final String API_PLAN_PACK_SMS_PRE = BASE_URL_ADDITITON + API_SEARCH_PRODUCT_CATEGORY + Constants.PLAN_PACK_SMS_PRE;
    private static final String API_PLAN_PACK_ROAMING_PRE = BASE_URL_ADDITITON + API_SEARCH_PRODUCT_CATEGORY + Constants.PLAN_PACK_ROAMING_PRE;

    private static final String API_PLAN_PACK_INTERNET_POST = BASE_URL_ADDITITON + API_SEARCH_PRODUCT_CATEGORY + Constants.PLAN_PACK_INTERNET_POST;
    private static final String API_PLAN_PACK_ENTERTAINMENT_POST = BASE_URL_ADDITITON + API_SEARCH_PRODUCT_CATEGORY + Constants.PLAN_PACK_ENTERTAINMENT_POST;
    private static final String API_PLAN_PACK_SMS_POST = BASE_URL_ADDITITON + API_SEARCH_PRODUCT_CATEGORY + Constants.PLAN_PACK_SMS_POST;
    private static final String API_PLAN_PACK_ROAMING_POST = BASE_URL_ADDITITON + API_SEARCH_PRODUCT_CATEGORY + Constants.PLAN_PACK_ROAMING_POST;


    private static final String API_FIND_STORE = BASE_URL_ADDITITON + "v2/b2ctelco/stores";
    private static final String API_ORDER_TRACKING = BASE_URL_ADDITITON + "v2/b2ctelco/customer/getOrderTrackStatus";

    private static final String API_SUBSCRIBE_PACKAGE = BSS_ADAPTER + "ManageSubscription/subscribeAddon";
    private static final String API_ACTIVATION = BSS_ADAPTER + "ManageCustomer/changeServiceInstnceStatus";
    private static final String API_MY_ACCOUNT_DATA = BSS_ADAPTER + "ManageSubscription/viewSubscriberServicewiseBalance";
    private static final String API_MY_ORDERS = BASE_URL_ADDITITON + "v2/b2ctelco/users/";
    private static final String API_MY_CART = BASE_URL_ADDITITON + "v2/b2ctelco/users/";
    private static final String API_POSTPAID_PLANS_FOR_YOU = BASE_URL_ADDITITON + "v2/b2ctelco/products/searchPostpaidProductByRule";
    private static final String API_GET_ALL_CUSTOMER_BY_DEALERID = BASE_URL_ADDITITON + "v2/b2ctelco/customer/findAllCustomer?parentUID=";


    public static final String API_FIND_SUBSCRIPTION_BY_EMAIL_ID = BASE_URL_ADDITITON + "v2/b2ctelco/customer/findSubscriptionsByEmailID";


    // Mac start
    private static final String API_ORDER_DETAIL_PATH = "v2/b2ctelco/users/";
    public static final String API_ORDER_DETAIL = BASE_URL_ADDITITON + API_ORDER_DETAIL_PATH;


    //customer
    private static final String API_GET_CUSTOMER_DETAILS = BSS_ADAPTER + "ManageCustomer/getCustomerDetails?accountNumber=";
    private static final String API_GET_INVENTORY_DETAILS = BSS_ADAPTER + "ManageInventory/getAssociatedInventory?accountNumber=";

    private static final String API_GET_BILL_PDF_DATA = BSS_ADAPTER + "ManageBillingAccount/downloadBillForApp?strBillNumber=";

    //unsubscribe addon
    private static final String API_GET_SI_DETAILS = BSS_ADAPTER + "ManageCustomer/getSIDetails?accountNumber=";
    private static final String API_UNSUBSCRIBE_ADDON = BSS_ADAPTER + "ManageSubscription/unSubscribeAddon";
    private static final String API_GET_BILL_DETAILS = BSS_ADAPTER + "ManageBillingAccount/searchBillingDetail";

    //payment history
    private static final String API_PAYMENT_HISTORY = BSS_ADAPTER + "ManagePayment/searchPaymentDetail";


    private static final String API_UPLOAD_DATA = BASE_URL_ADDITITON + "v2/b2ctelco/Orderstatus/uploadProof";

    //DELETE CART
    // https://192.168.2.5:9002/telcocommercewebservices/v2/b2ctelco/users/{UserId}/carts/{CartId}?access_token={accesstoken}

    private static final String API_DELETE_CART = BASE_URL_ADDITITON + "v2/b2ctelco/users/";
    private static final String API_CHANGE_PLAN = BSS_ADAPTER + "ManageCustomer/changePlan";

    private static final String API_CREATE_TICKET =  "/createTicket.php";
    private static final String API_GET_TICKET_CATEGORY = "/getTicketCategory.php";
    private static final String API_GET_TICKET_SUB_CATEGORY ="/getTicketSubCategory.php?category=";
    private static final String API_GET_TICKET =  "/getTicketByCustomer.php";

    private static final String API_CRM_UPDATE_CUSTOMER_DETAILS = "/updateCustomerAccount.php";
    private static final String API_CRM_GET_CUSTOMER_DETAILS = "/getCustomerDetails.php";



    @Override
    public void getMakeMyPlanBasicPlan(final OnApiCallback<Pagging<MakeMyPlanItems>> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        restHelper.sendRequest(2, API_MAKE_MY_PLAN_BASIC_PLAN, API_MAKE_MY_PLAN_BASIC_PLAN, new RestHelper.RestHelperCallback() {
            @Override
            public void onRequestCallback(int code, String message, RestResponse restResponse) {
                Log.d("request sent:response:", restResponse.toString());
                final JSONObject jsonObject = AppUtils.checkResponse(code, message, restResponse);
                if (jsonObject != null) {
                    try {
                        Pagging<MakeMyPlanItems> makeMyPlanPagging = new Pagging<>();

                        ArrayList<MakeMyPlanItems> list = MakeMyPlanItems.parseValueFromServer(jsonObject, "products");
                        if (list != null && list.size() > 0) {
                            makeMyPlanPagging.addItemToList(list);
                        }
                        onApiCallback.onSuccess(makeMyPlanPagging);

                    } catch (Exception e) {
                        onApiCallback.onFailed(code, message);
                        e.printStackTrace();
                    }
                } else {
                    onApiCallback.onFailed(code, message);
                }
            }
        });
    }

    @Override
    public void getMakeMyPlanDataAddOn(final OnApiCallback<Pagging<MakeMyPlanItems>> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        restHelper.sendRequest(2, API_MAKE_MY_PLAN_DATA_ADDON, API_MAKE_MY_PLAN_DATA_ADDON, new RestHelper.RestHelperCallback() {
            @Override
            public void onRequestCallback(int code, String message, RestResponse restResponse) {

                final JSONObject jsonObject = AppUtils.checkResponse(code, message, restResponse);
                if (jsonObject != null) {
                    try {
                        Pagging<MakeMyPlanItems> makeMyPlanPagging = new Pagging<>();
                        String categoryCode = Constants.MMP_ADDON_CATEGORY_DATA;
                        ArrayList<MakeMyPlanItems> list = MakeMyPlanItems.parseValueFromServer(jsonObject, "products");
                        if (list != null && list.size() > 0) {
                            for (MakeMyPlanItems mmpi : list) {
                                mmpi.setCategoryCode(categoryCode);
                            }
                            makeMyPlanPagging.addItemToList(list);
                        }
                        onApiCallback.onSuccess(makeMyPlanPagging);

                    } catch (Exception e) {
                        onApiCallback.onFailed(code, message);
                        e.printStackTrace();
                    }
                } else {
                    onApiCallback.onFailed(code, message);
                }
            }
        });
    }

    @Override
    public void getMakeMyPlanDataSubAddOn(final OnApiCallback<Pagging<MakeMyPlanItems>> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        restHelper.sendRequest(2, API_MAKE_MY_PLAN_DATA_SUB_ADDON, API_MAKE_MY_PLAN_DATA_SUB_ADDON, new RestHelper.RestHelperCallback() {
            @Override
            public void onRequestCallback(int code, String message, RestResponse restResponse) {

//                // this the temp line remove after actual api comes
//                restResponse.setResString(loadJSONFromAsset("temp_data_sub.json"));

                final JSONObject jsonObject = AppUtils.checkResponse(code, message, restResponse);
                if (jsonObject != null) {
                    try {
                        Pagging<MakeMyPlanItems> makeMyPlanPagging = new Pagging<>();
                        String categoryCode = Constants.MMP_ADDON_CATEGORY_DATA_SUB;
                        ArrayList<MakeMyPlanItems> list = MakeMyPlanItems.parseValueFromServer(jsonObject, "products");
                        if (list != null && list.size() > 0) {
                            for (MakeMyPlanItems mmpi : list) {
                                mmpi.setCategoryCode(categoryCode);
                            }
                            makeMyPlanPagging.addItemToList(list);
                        }
                        onApiCallback.onSuccess(makeMyPlanPagging);

                    } catch (Exception e) {
                        onApiCallback.onFailed(code, message);
                        e.printStackTrace();
                    }
                } else {
                    onApiCallback.onFailed(code, message);
                }
            }
        });
    }

    @Override
    public void getMakeMyPlanMinAddOn(final OnApiCallback<Pagging<MakeMyPlanItems>> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        restHelper.sendRequest(2, API_MAKE_MY_PLAN_MIN_ADDON, API_MAKE_MY_PLAN_MIN_ADDON, new RestHelper.RestHelperCallback() {
            @Override
            public void onRequestCallback(int code, String message, RestResponse restResponse) {

                final JSONObject jsonObject = AppUtils.checkResponse(code, message, restResponse);
                if (jsonObject != null) {
                    try {
                        Pagging<MakeMyPlanItems> makeMyPlanPagging = new Pagging<>();
                        String categoryCode = Constants.MMP_ADDON_CATEGORY_VOICE;

                        ArrayList<MakeMyPlanItems> list = MakeMyPlanItems.parseValueFromServer(jsonObject, "products");
                        if (list != null && list.size() > 0) {
                            for (MakeMyPlanItems mmpi : list) {
                                mmpi.setCategoryCode(categoryCode);
                            }
                            makeMyPlanPagging.addItemToList(list);
                        }
                        onApiCallback.onSuccess(makeMyPlanPagging);

                    } catch (Exception e) {
                        onApiCallback.onFailed(code, message);
                        e.printStackTrace();
                    }
                } else {
                    onApiCallback.onFailed(code, message);
                }
            }
        });
    }

    @Override
    public void getMakeMyPlanSMSAddOn(final OnApiCallback<Pagging<MakeMyPlanItems>> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        restHelper.sendRequest(2, API_MAKE_MY_PLAN_SMS_ADDON, API_MAKE_MY_PLAN_SMS_ADDON, new RestHelper.RestHelperCallback() {
            @Override
            public void onRequestCallback(int code, String message, RestResponse restResponse) {

                final JSONObject jsonObject = AppUtils.checkResponse(code, message, restResponse);
                if (jsonObject != null) {
                    try {
                        Pagging<MakeMyPlanItems> makeMyPlanPagging = new Pagging<>();
                        String categoryCode = Constants.MMP_ADDON_CATEGORY_SMS;

                        ArrayList<MakeMyPlanItems> list = MakeMyPlanItems.parseValueFromServer(jsonObject, "products");
                        if (list != null && list.size() > 0) {
                            for (MakeMyPlanItems mmpi : list) {
                                mmpi.setCategoryCode(categoryCode);
                            }
                            makeMyPlanPagging.addItemToList(list);
                        }
                        onApiCallback.onSuccess(makeMyPlanPagging);

                    } catch (Exception e) {
                        onApiCallback.onFailed(code, message);
                        e.printStackTrace();
                    }
                } else {
                    onApiCallback.onFailed(code, message);
                }
            }
        });
    }

    @Override
    public void getMakeMyPlanRoamingAddOn(final OnApiCallback<Pagging<MakeMyPlanItems>> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        restHelper.sendRequest(2, API_MAKE_MY_PLAN_ROAMING_ADDON, API_MAKE_MY_PLAN_ROAMING_ADDON, new RestHelper.RestHelperCallback() {
            @Override
            public void onRequestCallback(int code, String message, RestResponse restResponse) {

                final JSONObject jsonObject = AppUtils.checkResponse(code, message, restResponse);
                if (jsonObject != null) {
                    try {
                        Pagging<MakeMyPlanItems> makeMyPlanPagging = new Pagging<>();
                        String categoryCode = Constants.MMP_ADDON_CATEGORY_ROM;

                        ArrayList<MakeMyPlanItems> list = MakeMyPlanItems.parseValueFromServer(jsonObject, "products");
                        if (list != null && list.size() > 0) {
                            for (MakeMyPlanItems mmpi : list) {
                                mmpi.setCategoryCode(categoryCode);
                            }
                            makeMyPlanPagging.addItemToList(list);
                        }
                        onApiCallback.onSuccess(makeMyPlanPagging);

                    } catch (Exception e) {
                        onApiCallback.onFailed(code, message);
                        e.printStackTrace();
                    }
                } else {
                    onApiCallback.onFailed(code, message);
                }
            }
        });
    }

//    @Override
//    public void doLogin(String countryCode, final String username, final String password, final OnApiCallback<User> onApiCallback) {
//        RestHelper restHelper = new RestHelper();
//
//        HashMap<String, Object> params = new HashMap<>();
//        params.put("mobile", countryCode + " " + username);
//        params.put("password", password);
//
//        restHelper.sendRequest(API_LOGIN_URL, API_LOGIN_URL, params, new RestHelper.RestHelperCallback() {
//            @Override
//            public void onRequestCallback(int code, String message, RestResponse restResponse) {
//
//                final JSONObject jsonObject = AppUtils.checkResponse(code, message, restResponse);
//                if (jsonObject != null) {
//                    try {
//
//                        JSONObject jsonObj = jsonObject.getJSONObject(Constants.RES_OBJ_KEY);
//                        ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//                        User user = mapper.readValue(jsonObj.toString(), User.class);
////                        user.setCountryCode(user.getNumber().substring(0,user.getNumber().indexOf(" ")));
////                        user.setCountryCode2(user.getNumber2().substring(0,user.getNumber2().indexOf(" ")));
//                        user.setPassword(password);
//                        onApiCallback.onSuccess(user);
//
//                    } catch (Exception e) {
//                        onApiCallback.onFailed(code, message);
//                        e.printStackTrace();
//                    }
//                } else {
//                    onApiCallback.onFailed(code, message);
//                }
//            }
//        });
//
//
//    }


    @Override
    public void doGetMyAccountData(String subscriberIdentifier, String tenantId, final OnApiCallback<SubscriberServicewiseBalance> onApiCallback) {
        try {
            RestHelper restHelper = new RestHelper();
            final JSONObject params = new JSONObject();
            params.put("subscriberIdentifier", DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_SUBSCRIPTION_NUMBER, null));
            params.put("tenantId", tenantId);
            restHelper.sendRequest(1, API_MY_ACCOUNT_DATA, API_MY_ACCOUNT_DATA, new RestHelper.RestHelperCallback() {
                @Override
                public void onRequestCallback(int code, String message, RestResponse restResponse) {
                    final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
                    if (finalJsonObject != null) {
                        try {
                            ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                            SubscriberServicewiseBalance subscriberServicewiseBalance = mapper.readValue(finalJsonObject.toString(), SubscriberServicewiseBalance.class);
                            onApiCallback.onSuccess(subscriberServicewiseBalance);
                        } catch (Exception e) {
                            onApiCallback.onFailed(code, message);
                            e.printStackTrace();
                        }
                    } else {
                        onApiCallback.onFailed(code, message);
                    }
                }
            }, params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doActivateMyOrder(String orderNo, String serviceNo, final OnApiCallback<ActivationResponseModel> onApiCallback) {
        try {
            RestHelper restHelper = new RestHelper();
            final JSONObject params = new JSONObject();
            params.put("serviceInstanceNumner", serviceNo);
            restHelper.sendRequest(1, API_ACTIVATION, API_ACTIVATION, new RestHelper.RestHelperCallback() {
                @Override
                public void onRequestCallback(int code, String message, RestResponse restResponse) {
                    final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);

                    if (finalJsonObject != null) {
                        try {
                            ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                            ActivationResponseModel activationResponseModel = mapper.readValue(finalJsonObject.toString(), ActivationResponseModel.class);
                            onApiCallback.onSuccess(activationResponseModel);
                        } catch (Exception e) {
                            onApiCallback.onFailed(code, message);
                            e.printStackTrace();
                        }
                    } else {
                        onApiCallback.onFailed(code, message);
                    }
                }
            }, params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGetOrderTrackingDetails(String serialNo, final OnApiCallback<OrderTrackingResponseModel> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        final HashMap<String, String> params = new HashMap<>();
        params.put("orderId", serialNo);
        restHelper.sendRequest(2, RestHelper.REQUEST_TYPE_GET, API_ORDER_TRACKING, API_ORDER_TRACKING, RestHelper.CONTENT_TYPE_FROMDATA, params, null, null, new RestHelper.RestHelperCallback() {
            @Override
            public void onRequestCallback(int code, String message, RestResponse restResponse) {
                try {
                    final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
                    if (finalJsonObject != null) {
                        ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        OrderTrackingResponseModel orderTrackingResponseModel = mapper.readValue(finalJsonObject.toString(), OrderTrackingResponseModel.class);
                        onApiCallback.onSuccess(orderTrackingResponseModel);
                    } else {
                        onApiCallback.onFailed(code, message);
                    }
                } catch (Exception e) {
                    onApiCallback.onFailed(code, message);
                    e.printStackTrace();
                }
            }
        }, null);
    }

    @Override
    public void getPlanPackageInternet(final OnApiCallback<Pagging<PlanPackage>> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        String dnNumber = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_DN_NUMBER, null);
        Map<String, String> chargingPatternMap = DCCMDealerSterlite.getDataManager().loadMap(AppPreferencesHelper.CHARGING_PATTERN_MAP);
        String chargingType = chargingPatternMap.get(dnNumber);
        Log.e("chargingType::",chargingType+" ");
        String URL = API_PLAN_PACK_INTERNET_POST;
        if (chargingType != null) {
            if (chargingType.equalsIgnoreCase(Constants.PREPAID)) {
                URL = API_PLAN_PACK_INTERNET_PRE;
            } else {
                URL = API_PLAN_PACK_INTERNET_POST;
            }
        }

        restHelper.sendRequest(2, URL, URL, new RestHelper.RestHelperCallback() {
            @Override
            public void onRequestCallback(int code, String message, RestResponse restResponse) {

                final JSONObject jsonObject = AppUtils.checkResponse(code, message, restResponse);
                if (jsonObject != null) {
                    try {
                        Pagging<PlanPackage> planPackagePagging = new Pagging<>();

                        ArrayList<PlanPackage> list = PlanPackage.parseValueFromServer(jsonObject, "products");
                        if (list != null && list.size() > 0) {
                            planPackagePagging.addItemToList(list);
                        }


                        for (PlanPackage planPackage : list) {
                            try {
                                JSONObject codeObj = getJsonObjectFromPropertyFile(planPackage.getCode());
                                if (codeObj != null) {
                                    planPackage.setLeftMsg(codeObj.getString("leftMsg"));
                                    planPackage.setTopMsg(codeObj.getString("topMsg"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        onApiCallback.onSuccess(planPackagePagging);

                    } catch (Exception e) {
                        onApiCallback.onFailed(code, message);
                        e.printStackTrace();
                    }
                } else {
                    onApiCallback.onFailed(code, message);
                }
            }
        });
    }

    @Override
    public void getPlanPackageRoaming(final OnApiCallback<Pagging<PlanPackage>> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        String dnNumber = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_DN_NUMBER, null);
        Map<String, String> chargingPatternMap = DCCMDealerSterlite.getDataManager().loadMap(AppPreferencesHelper.CHARGING_PATTERN_MAP);
        String chargingType = chargingPatternMap.get(dnNumber);
        String URL = API_PLAN_PACK_ROAMING_POST;
        if (chargingType != null) {

            if (chargingType.equalsIgnoreCase(Constants.PREPAID)) {
                URL = API_PLAN_PACK_ROAMING_PRE;
            } else {
                URL = API_PLAN_PACK_ROAMING_POST;
            }
        }
        restHelper.sendRequest(2, URL, URL, new RestHelper.RestHelperCallback() {
            @Override
            public void onRequestCallback(int code, String message, RestResponse restResponse) {

                final JSONObject jsonObject = AppUtils.checkResponse(code, message, restResponse);
                if (jsonObject != null) {
                    try {
                        Pagging<PlanPackage> planPackagePagging = new Pagging<>();

                        ArrayList<PlanPackage> list = PlanPackage.parseValueFromServer(jsonObject, "products");
                        if (list != null && list.size() > 0) {
                            planPackagePagging.addItemToList(list);
                        }
                        for (PlanPackage planPackage : list) {
                            try {
                                JSONObject codeObj = getJsonObjectFromPropertyFile(planPackage.getCode());
                                if (codeObj != null) {
                                    planPackage.setLeftMsg(codeObj.getString("leftMsg"));
                                    planPackage.setTopMsg(codeObj.getString("topMsg"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        onApiCallback.onSuccess(planPackagePagging);

                    } catch (Exception e) {
                        onApiCallback.onFailed(code, message);
                        e.printStackTrace();
                    }
                } else {
                    onApiCallback.onFailed(code, message);
                }
            }
        });
    }

    @Override
    public void getPlanPackageEntertainment(final OnApiCallback<Pagging<PlanPackage>> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        String dnNumber = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_DN_NUMBER, null);
        Map<String, String> chargingPatternMap = DCCMDealerSterlite.getDataManager().loadMap(AppPreferencesHelper.CHARGING_PATTERN_MAP);
        String chargingType = chargingPatternMap.get(dnNumber);
        Log.e("Charging Pattern", chargingType + " ");
        String URL = API_PLAN_PACK_ENTERTAINMENT_POST;
        if (chargingType != null) {

            if (chargingType.equalsIgnoreCase(Constants.PREPAID)) {
                URL = API_PLAN_PACK_ENTERTAINMENT_PRE;
            } else {
                URL = API_PLAN_PACK_ENTERTAINMENT_POST;
            }
        }
        restHelper.sendRequest(2, URL, URL, new RestHelper.RestHelperCallback() {
            @Override
            public void onRequestCallback(int code, String message, RestResponse restResponse) {

                final JSONObject jsonObject = AppUtils.checkResponse(code, message, restResponse);
                if (jsonObject != null) {
                    try {
                        Pagging<PlanPackage> planPackagePagging = new Pagging<>();

                        ArrayList<PlanPackage> list = PlanPackage.parseValueFromServer(jsonObject, "products");
                        if (list != null && list.size() > 0) {
                            planPackagePagging.addItemToList(list);
                        }
                        for (PlanPackage planPackage : list) {
                            try {
                                JSONObject codeObj = getJsonObjectFromPropertyFile(planPackage.getCode());
                                if (codeObj != null) {
                                    planPackage.setLeftMsg(codeObj.getString("leftMsg"));
                                    planPackage.setTopMsg(codeObj.getString("topMsg"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        onApiCallback.onSuccess(planPackagePagging);

                    } catch (Exception e) {
                        onApiCallback.onFailed(code, message);
                        e.printStackTrace();
                    }
                } else {
                    onApiCallback.onFailed(code, message);
                }
            }
        });
    }

    @Override
    public void getPlanPackageSms(final OnApiCallback<Pagging<PlanPackage>> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        String dnNumber = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_DN_NUMBER, null);
        Map<String, String> chargingPatternMap = DCCMDealerSterlite.getDataManager().loadMap(AppPreferencesHelper.CHARGING_PATTERN_MAP);
        String chargingType = chargingPatternMap.get(dnNumber);
        String URL = API_PLAN_PACK_SMS_POST;
        if (chargingType != null) {

            if (chargingType.equalsIgnoreCase(Constants.PREPAID)) {
                URL = API_PLAN_PACK_SMS_PRE;
            } else {
                URL = API_PLAN_PACK_SMS_POST;
            }
        }
        restHelper.sendRequest(2, URL, URL, new RestHelper.RestHelperCallback() {
            @Override
            public void onRequestCallback(int code, String message, RestResponse restResponse) {
                final JSONObject jsonObject = AppUtils.checkResponse(code, message, restResponse);
                if (jsonObject != null) {
                    try {
                        Pagging<PlanPackage> planPackagePagging = new Pagging<>();

                        ArrayList<PlanPackage> list = PlanPackage.parseValueFromServer(jsonObject, "products");
                        if (list != null && list.size() > 0) {
                            planPackagePagging.addItemToList(list);
                        }
                        for (PlanPackage planPackage : list) {
                            try {
                                JSONObject codeObj = getJsonObjectFromPropertyFile(planPackage.getCode());
                                if (codeObj != null) {
                                    planPackage.setLeftMsg(codeObj.getString("leftMsg"));
                                    planPackage.setTopMsg(codeObj.getString("topMsg"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        onApiCallback.onSuccess(planPackagePagging);

                    } catch (Exception e) {
                        onApiCallback.onFailed(code, message);
                        e.printStackTrace();
                    }
                } else {
                    onApiCallback.onFailed(code, message);
                }
            }
        });
    }


    private JSONObject getJsonObjectFromPropertyFile(String code) {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("planPackProperties.json"));
            JSONObject codeObj = obj.getJSONObject("AddOnProducts");
            JSONObject addOnProductObj = codeObj.getJSONObject(code);

            return addOnProductObj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void getMyOrders(final OnApiCallback<MyOrdersListResponseModel> onApiCallback) {

        AppApiHelper2.getAuthToken(new ApiHelper2.OnApiCallback() {
            @Override
            public void onSuccess(Object baseResponse) {
                RestHelper restHelper = new RestHelper();
                //les30081800014/orders?access_token=ae43dbd8-9b54-4ebd-826e-520634d3249d
                String uid = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, null);
                String authToken = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.AUTH__TOKEN, null);
                String ORDER_URL = API_MY_ORDERS + uid + "/orders";

                final HashMap<String, String> params = new HashMap<>();
                //  params.put("access_token","ae43dbd8-9b54-4ebd-826e-520634d3249d");
                params.put("access_token", authToken);
                restHelper.sendRequest(2, RestHelper.REQUEST_TYPE_GET, ORDER_URL, ORDER_URL, RestHelper.CONTENT_TYPE_FROMDATA, params, null, null, new RestHelper.RestHelperCallback() {
                    @Override
                    public void onRequestCallback(int code, String message, RestResponse restResponse) {
                        try {
                            final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
                            if (finalJsonObject != null) {
                                ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                                MyOrdersListResponseModel myOrdersListResponseModel = mapper.readValue(finalJsonObject.toString(), MyOrdersListResponseModel.class);
                                onApiCallback.onSuccess(myOrdersListResponseModel);
                            } else {
                                onApiCallback.onFailed(code, message);
                            }
                        } catch (Exception e) {
                            onApiCallback.onFailed(code, message);
                            e.printStackTrace();
                        }
                    }
                }, null);
            }

            @Override
            public void onFaild(Object baseResponse) {

            }
        });
    }

    @Override
    public void getMyCart(final OnApiCallback<String> onApiCallback) {
        AppApiHelper2.getAuthToken(new ApiHelper2.OnApiCallback() {
            @Override
            public void onSuccess(Object baseResponse) {
                RestHelper restHelper = new RestHelper();
                //les30081800014/orders?access_token=ae43dbd8-9b54-4ebd-826e-520634d3249d
                String uid = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, null);
                String authToken = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.AUTH__TOKEN, null);
                String ORDER_URL = API_MY_CART + uid + "/carts";

                final HashMap<String, String> params = new HashMap<>();
                //  params.put("access_token","ae43dbd8-9b54-4ebd-826e-520634d3249d");
                params.put("access_token", authToken);
                restHelper.sendRequest(2, RestHelper.REQUEST_TYPE_GET, ORDER_URL, ORDER_URL, RestHelper.CONTENT_TYPE_FROMDATA, params, null, null, new RestHelper.RestHelperCallback() {
                    @Override
                    public void onRequestCallback(int code, String message, RestResponse restResponse) {
                        try {
                            JSONObject finalJsonObject = new JSONObject(restResponse.getResString());
                            String productIds = "";
                            JSONArray jsonCarts = finalJsonObject.getJSONArray("carts");
                            if (jsonCarts != null && jsonCarts.length() > 0) {
                                JSONObject jsonobj = jsonCarts.getJSONObject(0);
                                JSONArray jsonEntries = jsonobj.getJSONArray("entries");
                                if (jsonEntries != null && jsonEntries.length() > 0) {
                                    for (int i = 0; i < jsonEntries.length(); i++) {
                                        JSONObject jsonobjEnteries = jsonEntries.getJSONObject(i);

                                        JSONObject jsonobjProduct = jsonobjEnteries.getJSONObject("product");
                                        productIds = jsonobjProduct.getString("code");
                                        if (productIds.toUpperCase().startsWith(Constants.Product_PREFIX)) {
                                            onApiCallback.onSuccess(productIds);
                                            return;
                                        }
                                    }
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        onApiCallback.onFailed(code, message);
                    }
                }, null);
            }

            @Override
            public void onFaild(Object baseResponse) {

            }
        });
    }


    //uid="les20091800003";


    public static String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = DCCMDealerSterlite.appContext.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    @Override
    public void findSubscriptionByEmailId(String userId, final OnApiCallback<FindSubscriptionByEmailModel> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        final HashMap<String, String> params = new HashMap<>();
        params.put("uid", userId);
        restHelper.sendRequest(0, RestHelper.REQUEST_TYPE_POST, API_FIND_SUBSCRIPTION_BY_EMAIL_ID, API_FIND_SUBSCRIPTION_BY_EMAIL_ID, RestHelper.CONTENT_TYPE_FROMDATA, params, null, null, new RestHelper.RestHelperCallback() {
            @Override
            public void onRequestCallback(int code, String message, RestResponse restResponse) {
                try {
                    final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
                    if (finalJsonObject != null) {
                        ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        FindSubscriptionByEmailModel findSubscriptionByEmailModel = mapper.readValue(finalJsonObject.toString(), FindSubscriptionByEmailModel.class);
                        onApiCallback.onSuccess(findSubscriptionByEmailModel);
                    } else {
                        onApiCallback.onFailed(code, message);
                    }
                } catch (Exception e) {
                    onApiCallback.onFailed(code, message);
                    e.printStackTrace();
                }
            }
        }, null);
    }

    @Override
    public void doRegisterDeviceTokenToServer(final DeviceToken device, final OnApiCallback<DeviceToken> onApiCallback) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", device.getUserId());
        params.put("os", "android");
        params.put("deviceToken", device.getDeviceToken());
        params.put("deviceId", "" + device.getDeviceId());
        params.put("deviceName", "" + device.getDeviceName());
        params.put("deviceModel", "" + device.getDeviceModel());
        params.put("deviceVersion", "" + device.getDeviceVersion());
        params.put("environment", device.getEnvironment());
        params.put("isBadgeEnable", "1");
        params.put("isAlertEnable", "1");
        params.put("isSoundEnable", "1");
        params.put("language", device.getLanguage());
        RestHelper restHelper = new RestHelper();
        restHelper.sendRequest(3, API_REGISTER_DEVICE_TOKEN, API_REGISTER_DEVICE_TOKEN, params, new RestHelper.RestHelperCallback() {
            @Override
            public void onRequestCallback(int code, String message, RestResponse restResponse) {
                JSONObject jsonObject = AppUtils.checkResponse(code, message, restResponse);
                if (jsonObject != null) {
                    try {
                        onApiCallback.onSuccess(device);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    onApiCallback.onFailed(code, message);
                }
            }
        });
    }


    @Override
    public void doSubscribeAddOn(String paymentMode, String subscriptionNumber, String productName, final OnApiCallback<SubscribeAddOnResponceModel> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        JSONObject params = new JSONObject();
        try {
            params.put("subscriptionNumber", subscriptionNumber);
            if (paymentMode.equalsIgnoreCase(Constants.BALANCE)) {
                params.put("externalSystemOfferId", paymentMode);
            }
            JSONObject product = new JSONObject();
            product.put("name", productName);
            params.put("product", product);
            JSONArray array = new JSONArray();
            array.put(params);
            Log.e("subscribeAddon Req", array.toString());
//            public void sendRequest(int reqType, String identifier, String action, int contentType, HashMap<String, String> qryparams, HashMap<String, Object> params, HashMap<String, String> headers, RestHelper.RestHelperCallback
//            callback, String imageKey, String imagePath, JSONObject json)
//            {
//                String baseurl = AppApiHelper.BASE_URL;
//                sendRequest(baseurl,reqType,identifier,action,contentType,qryparams,params,headers,callback,imageKey,imagePath,json);
//            }

            restHelper.sendRequest(1, API_SUBSCRIBE_PACKAGE, API_SUBSCRIBE_PACKAGE, new RestHelper.RestHelperCallback() {
                @Override
                public void onRequestCallback(int code, String message, RestResponse restResponse) {
                    final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
                    if (finalJsonObject != null) {
                        try {
                            ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                            SubscribeAddOnResponceModel subscribeAddOnResponceModel = mapper.readValue(finalJsonObject.toString(), SubscribeAddOnResponceModel.class);
                            onApiCallback.onSuccess(subscribeAddOnResponceModel);
                        } catch (Exception e) {
                            onApiCallback.onFailed(code, message);
                            e.printStackTrace();
                        }
                    } else {
                        onApiCallback.onFailed(code, message);
                    }
                }
            }, array);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void getCustomerDetails(String userId, final OnApiCallback<ServiceInstanceDetail[]> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        //   userId = "LES25091800021";
        if (userId != null) {
            String URL = API_GET_CUSTOMER_DETAILS.trim() + userId;
            Log.e("URL", URL);
            try {
                restHelper.sendRequest(1, URL, URL, new RestHelper.RestHelperCallback() {
                    @Override
                    public void onRequestCallback(int code, String message, RestResponse restResponse) {
                        final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
                        if (finalJsonObject != null) {
                            try {

                                Gson gson = new Gson();

                                ServiceInstanceDetail[] serviceInstanceDetailList =
                                        gson.fromJson(finalJsonObject.
                                                        getJSONObject("responseObject").
                                                        getJSONArray("serviceInstanceDetail").toString(),
                                                ServiceInstanceDetail[].class);
                                Log.e("getCustomerDetails::", serviceInstanceDetailList.toString());
                                /*List<String> serviceInstanceList= new ArrayList<>();

                                for (ServiceInstanceDetail serviceInstance:serviceInstanceDetailList
                                     ) {
                                    serviceInstanceList.add(serviceInstance.getAccountNumber());
                                    Log.e("serviceInstance",serviceInstance.toString());
                                }

                                List<ServiceInstanceDetail> serviceInstanceDetailList1 = new ArrayList<>();*/
                                onApiCallback.onSuccess(serviceInstanceDetailList);
                            } catch (Exception e) {
                                onApiCallback.onFailed(code, message);
                                e.printStackTrace();
                            }
                        } else {
                            onApiCallback.onFailed(code, message);
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void getInventoryNumber(String userId, final OnApiCallback<String> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        // userId = "LES25091800021";
        if (userId != null) {
            String URL = API_GET_INVENTORY_DETAILS.trim() + userId;
            Log.e("URL", URL);
            try {
                restHelper.sendRequest(1, URL, URL, new RestHelper.RestHelperCallback() {
                    @Override
                    public void onRequestCallback(int code, String message, RestResponse restResponse) {
                        final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
                        if (finalJsonObject != null) {
                            try {

                                Gson gson = new Gson();
                                String inventoryDetail = gson.fromJson(finalJsonObject.getJSONArray("responseObject").getJSONObject(0).getString("inventoryNumber"), String.class);
                                Log.e("getInventoryNumber::", inventoryDetail);

                                // List<ServiceInstanceDetail> serviceInstanceDetailList = subscribeAddOnResponceModel2.getServiceInstanceDetail();
                                onApiCallback.onSuccess(inventoryDetail);
                            } catch (Exception e) {
                                onApiCallback.onFailed(code, message);
                                e.printStackTrace();
                            }
                        } else {
                            onApiCallback.onFailed(code, message);
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void findStore(final String pincode, final OnApiCallback<ArrayList<Store>> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        // userId = "LES25091800021";
        restHelper.sendRequest(2, API_FIND_STORE, API_FIND_STORE, new RestHelper.RestHelperCallback() {
            @Override
            public void onRequestCallback(int code, String message, RestResponse restResponse) {
                final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
                if (finalJsonObject != null) {
                    try {

                                /*String inventoryDetail = gson.fromJson(finalJsonObject.getJSONArray("responseObject").getJSONObject(0).getString("inventoryNumber"), String.class);
                                Log.e("getInventoryNumber::", inventoryDetail);*/
                        Log.e("findStore::", finalJsonObject.toString());


                        JSONArray dccmStoreArray = finalJsonObject.getJSONArray("stores");
                        ArrayList<Store> storeArrayList = new ArrayList<>();
                        Store store;
                        for (int i = 0, size = dccmStoreArray.length(); i < size; i++) {
                            store = new Store();
                            JSONObject objectInArray = dccmStoreArray.getJSONObject(i);

                            String apiPostalCode = objectInArray.getJSONObject("address").getString("postalCode");
                            //if(pincode.startsWith(apiPostalCode.substring(0,1))) {
                            store.setStoreName(objectInArray.has("displayName") ? objectInArray.get("displayName").toString() : "");

                            if (objectInArray.has("address")) {
                                store.setStoreID(objectInArray.getJSONObject("address").has("id") ? objectInArray.getJSONObject("address").getString("id") : "");
                                store.setStoreAddress((objectInArray.getJSONObject("address").has("line1") ? objectInArray.getJSONObject("address").getString("line1") : "") + " " + (objectInArray.getJSONObject("address").has("line2") ? objectInArray.getJSONObject("address").getString("line2") : ""));
                                store.setPostalCode(objectInArray.getJSONObject("address").has("postalCode") ? objectInArray.getJSONObject("address").getString("postalCode") : "");
                            }
                            if (objectInArray.has("geoPoint")) {
                                store.setLat(objectInArray.getJSONObject("geoPoint").getDouble("latitude"));
                                store.setLng(objectInArray.getJSONObject("geoPoint").getDouble("longitude"));

                            }
                            storeArrayList.add(store);
                            //}
                            Log.e("findStore::" + i + ":::", store.toString());

                        }
                        onApiCallback.onSuccess(storeArrayList);
                    } catch (Exception e) {
                        onApiCallback.onFailed(code, message);
                        e.printStackTrace();
                    }
                } else {
                    onApiCallback.onFailed(code, message);
                }
            }
        });

    }


    @Override
    public void getSIDetails(String accountNUmber, final OnApiCallback<ServiceInstanceDetail> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        //   userId = "LES25091800021";
        if (accountNUmber != null) {
            String URL = API_GET_SI_DETAILS.trim() + accountNUmber;
            Log.e("URL", URL);
            try {
                restHelper.sendRequest(1, URL, URL, new RestHelper.RestHelperCallback() {
                    @Override
                    public void onRequestCallback(int code, String message, RestResponse restResponse) {
                        final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
                        if (finalJsonObject != null) {
                            try {

                                Gson gson = new Gson();

                                ServiceInstanceDetail serviceInstanceDetail =
                                        gson.fromJson(finalJsonObject.
                                                        getJSONObject("responseObject").toString(),
                                                ServiceInstanceDetail.class);
                                Log.e("SI::", serviceInstanceDetail.toString());

                                onApiCallback.onSuccess(serviceInstanceDetail);
                            } catch (Exception e) {
                                onApiCallback.onFailed(code, message);
                                e.printStackTrace();
                            }
                        } else {
                            onApiCallback.onFailed(code, message);
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void doUnSubscribeAddOn(String subscriptionNumber, String externalSystemOfferId, final OnApiCallback<String> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        JSONObject params = new JSONObject();
        try {
            params.put("subscriptionNumber", subscriptionNumber);
            params.put("externalSystemOfferId", externalSystemOfferId);
            JSONArray array = new JSONArray();
            array.put(params);
            Log.e("doUnSubscribeAddOn", array.toString());
            restHelper.sendRequest(1, API_UNSUBSCRIBE_ADDON, API_UNSUBSCRIBE_ADDON, new RestHelper.RestHelperCallback() {
                @Override
                public void onRequestCallback(int code, String message, RestResponse restResponse) {
                    final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
                    if (finalJsonObject != null) {
                        try {
                            //  ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                            //String responseCode = gson.fromJson(finalJsonObject.getString("responseMessage"), String.class);
                            String responseCode = finalJsonObject.getString("responseMessage");

                            onApiCallback.onSuccess(responseCode);
                        } catch (Exception e) {
                            onApiCallback.onFailed(code, message);
                            e.printStackTrace();
                        }
                    } else {
                        onApiCallback.onFailed(code, message);
                    }
                }
            }, array);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getProductbyCode(String productCode, final OnApiCallback<String> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        //      productCode="PRD00747";
        // userId = "LES25091800021";
        if (Constants.IS_DUMMY_MODE) {
            onApiCallback.onSuccess(null);
            return;
        }

        String URL = API_SEARCH_PRODUCT_CODE + productCode;
        Log.e("description", "getProductbyCode");

        restHelper.sendRequest(2, URL, URL, new RestHelper.RestHelperCallback() {
            @Override
            public void onRequestCallback(int code, String message, RestResponse restResponse) {
                Log.d("request sent:response:", restResponse.toString());
                final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
                if (finalJsonObject != null) {
                    try {

                        Log.e("finalJsonObject", finalJsonObject + "");

                        String description = finalJsonObject.getString("description");

                        Log.e("description", description);
                        onApiCallback.onSuccess(description);
                    } catch (Exception e) {
                        onApiCallback.onFailed(code, message);
                        e.printStackTrace();
                    }
                } else {
                    onApiCallback.onFailed(code, message);
                }
            }
        });

    }


    @Override
    public void downloadBill(String billNumber, final OnApiCallback<String> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        //   userId = "LES25091800021";
        //    billNumber="REG0000000005016";
        if (billNumber != null) {
            String URL = API_GET_BILL_PDF_DATA.trim() + billNumber;
            Log.e("URL", URL);
            try {
                restHelper.sendRequest(1, URL, URL, new RestHelper.RestHelperCallback() {
                    @Override
                    public void onRequestCallback(int code, String message, RestResponse restResponse) {
                        final String finalJsonObject = restResponse.getResString();
                        if (finalJsonObject != null) {
                            try {

                                Log.e("Response:DownloadBill", finalJsonObject);
                                //Gson gson = new Gson();

                             /*   ServiceInstanceDetail serviceInstanceDetail  =
                                        gson.fromJson(finalJsonObject.
                                                        getJSONObject("responseObject").toString(),
                                                ServiceInstanceDetail.class);
                                Log.e("SI::", serviceInstanceDetail.toString());
*/
                                onApiCallback.onSuccess(finalJsonObject);
                            } catch (Exception e) {
                                onApiCallback.onFailed(code, message);
                                e.printStackTrace();
                            }
                        } else {
                            onApiCallback.onFailed(code, message);
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void getBillDetails(String billingAccountNumber, final OnApiCallback<BillingDetailData[]> onApiCallback) {
        try {
            RestHelper restHelper = new RestHelper();
            //   billingAccountNumber="TEL0135035";
            // billingAccountNumber="STMBA00000175";
            JSONObject params = new JSONObject();
            params.put("billingAccountNumber", billingAccountNumber);
            List<String> tenantIds = new ArrayList<>();
            tenantIds.add("0");
            params.put("tenantIds", tenantIds.toString());

            restHelper.sendRequest(1, API_GET_BILL_DETAILS, API_GET_BILL_DETAILS, new RestHelper.RestHelperCallback() {
                @Override
                public void onRequestCallback(int code, String message, RestResponse restResponse) {
                    final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);

                    if (finalJsonObject != null) {
                        try {
                            //  ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                            //ActivationResponseModel activationResponseModel = mapper.readValue(finalJsonObject.toString(), ActivationResponseModel.class);
                            //  if (finalJsonObject.getString())
                            //  String responseCode= finalJsonObject.getString("responseCode");
                            //  Log.e("responseCode::",responseCode+"");
                            //   if (responseCode!=null && !responseCode.isEmpty() && !responseCode.equalsIgnoreCase(null)) {

                            Log.e("Response:getBillDetails", finalJsonObject.toString());

                            Gson gson = new Gson();
                            BillingDetailData[] billingDetailData =
                                    gson.fromJson(finalJsonObject.getJSONArray("billingDetailDatas").toString(),
                                            BillingDetailData[].class);
                            if (billingDetailData[0].getBillNumber() != null && !billingDetailData[0].getBillNumber().equalsIgnoreCase("null")) {

                                Log.e("Response:getBillDetails", billingDetailData + "");

                                onApiCallback.onSuccess(billingDetailData);
                            } else {
                                onApiCallback.onFailed(code, message);
                            }
                        } catch (Exception e) {
                            onApiCallback.onFailed(code, message);
                            e.printStackTrace();
                        }
                    } else {
                        onApiCallback.onFailed(code, message);
                    }
                }
            }, params);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getPaymentHistory(String accNo, final OnApiCallback<SearchPaymentResponseData> onApiCallback) {
        Log.e("AppApiHelper", "getPaymentHistory start");
        RestHelper restHelper = new RestHelper();
        try {
            JSONObject par = new JSONObject();
            par.put("billingAccountNumber", accNo);
            par.put("tenantId", "master");

            restHelper.sendRequest(1, API_PAYMENT_HISTORY, API_PAYMENT_HISTORY, new RestHelper.RestHelperCallback() {
                @Override
                public void onRequestCallback(int code, String message, RestResponse restResponse) {
                    Log.d("request sent:response:", restResponse.toString());
                    final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
                    if (finalJsonObject != null) {
                        try {

                            Log.e("finalJsonObject", finalJsonObject + "");

                            SearchPaymentResponseData responseData
                                    = AppUtils.getObjFromString(finalJsonObject.toString(), SearchPaymentResponseData.class);
                            onApiCallback.onSuccess(responseData);
                        } catch (Exception e) {
                            onApiCallback.onFailed(code, message);
                            e.printStackTrace();
                        }
                    } else {
                        onApiCallback.onFailed(code, message);
                    }
                }
            }, par);
        } catch (Exception e) {
            AppUtils.errorLog("AppApiHelper", "getPaymentHistory err");
            AppUtils.errorLog("err", e + " ");
        }
    }


    @Override
    public void uploadDocumentAPI(String customerID, String imageKey, String imagePath, final OnApiCallback<Integer> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        //  HashMap<String, String> hashMap = new HashMap<>();

        //   hashMap.put("file", imagePath);
        HashMap<String, Object> params = new HashMap<>();
        //customerID="les02111800033";
        params.put("customerId", customerID);
        params.put("file", imagePath);

        HashMap<String, String> headers = new HashMap<>();
        //headers.put("Content-Type", "application/json");
        restHelper.sendRequest(2, RestHelper.REQUEST_TYPE_POST, API_UPLOAD_DATA, API_UPLOAD_DATA, RestHelper.CONTENT_TYPE_MULTIPART, null, params, headers, new RestHelper.RestHelperCallback() {
            @Override
            public void onRequestCallback(int code, String message, RestResponse restResponse) {
               /* if (Constants.IS_DUMMY_MODE) {
                    code = Constants.SUCCESS_CODE;
                    message = "sucess";
                    restResponse.setResString(loadJSONFromAsset("temp_post_paid_plan_4_u.json"));
                }*/

                try {
                    final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
                    Log.e("upload::code::", code + "");
                    onApiCallback.onSuccess(code);

                } catch (Exception e) {
                    onApiCallback.onFailed(code, message);
                    e.printStackTrace();
                }
            }
        }, imageKey, imagePath, null);
    }



    /*@Override
    public void deleteMyCart(final String cartId, final OnApiCallback<String> onApiCallback) {
        AppApiHelper2.getAuthToken(new ApiHelper2.OnApiCallback() {
            @Override
            public void onSuccess(Object baseResponse) {
                RestHelper restHelper = new RestHelper();
                //les30081800014/orders?access_token=ae43dbd8-9b54-4ebd-826e-520634d3249d
                String uid = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, null);
                String authToken = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.AUTH__TOKEN, null);
                String DELETE_CART_URL = API_DELETE_CART + uid + "/carts/"+cartId;

                final HashMap<String, String> params = new HashMap<>();
                //  params.put("access_token","ae43dbd8-9b54-4ebd-826e-520634d3249d");
                params.put("access_token", authToken);
                restHelper.sendRequest(2, RestHelper.REQUEST_TYPE_, DELETE_CART_URL, DELETE_CART_URL, RestHelper.CONTENT_TYPE_FROMDATA, params, null, null, new RestHelper.RestHelperCallback() {
                    @Override
                    public void onRequestCallback(int code, String message, RestResponse restResponse) {
                        try {
                            JSONObject finalJsonObject = new JSONObject(restResponse.getResString());
                            String productIds = "";
                            JSONArray jsonCarts = finalJsonObject.getJSONArray("carts");
                            if (jsonCarts != null && jsonCarts.length() > 0) {
                                JSONObject jsonobj = jsonCarts.getJSONObject(0);
                                JSONArray jsonEntries = jsonobj.getJSONArray("entries");
                                if (jsonEntries != null && jsonEntries.length() > 0) {
                                    for (int i=0;i<jsonEntries.length();i++) {
                                        JSONObject jsonobjEnteries = jsonEntries.getJSONObject(i);

                                        JSONObject jsonobjProduct = jsonobjEnteries.getJSONObject("product");
                                        productIds = jsonobjProduct.getString("code");
                                        if (productIds.toUpperCase().startsWith(Constants.Product_PREFIX)){
                                            onApiCallback.onSuccess(productIds);
                                            return;
                                        }
                                    }
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        onApiCallback.onFailed(code, message);
                    }
                }, null);
            }

            @Override
            public void onFaild(Object baseResponse) {

            }
        });
    }*/


    @Override
    public void getPostPaidPlansForYou(String customerID, String categoryCode, final OnApiCallback<PostPaidPlansForYouDTO> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("customerID", customerID);
        hashMap.put("categoryCode", categoryCode);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        restHelper.sendRequest(2, RestHelper.REQUEST_TYPE_GET, API_POSTPAID_PLANS_FOR_YOU, API_POSTPAID_PLANS_FOR_YOU, RestHelper.CONTENT_TYPE_FROMDATA, hashMap, null, headers, new RestHelper.RestHelperCallback() {
            @Override
            public void onRequestCallback(int code, String message, RestResponse restResponse) {
//                if (Constants.IS_DUMMY_MODE) {
//                    code = Constants.SUCCESS_CODE;
//                    message = "sucess";
//                    restResponse.setResString(loadJSONFromAsset("temp_post_paid_plan_4_u.json"));
//                }
                final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
                if (finalJsonObject != null) {
                    try {
                        ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        PostPaidPlansForYouDTO postPaidPlansForYouDTO = mapper.readValue(finalJsonObject.toString(), PostPaidPlansForYouDTO.class);
                        getDetails(System.currentTimeMillis(), postPaidPlansForYouDTO, onApiCallback);

                    } catch (Exception e) {
                        onApiCallback.onFailed(code, message);
                        e.printStackTrace();
                    }
                } else {
                    onApiCallback.onFailed(code, message);
                }
            }
        }, null, null, null);
    }


    @Override
    public void changePlan(String planName, final OnApiCallback<BaseResponse> onApiCallback) {
        Log.e("AppApiHelper", "changePlan start");
        String subscriberIdentifier = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_SUBSCRIPTION_NUMBER, null);
        Map<String, String> bAcc = DCCMDealerSterlite.getDataManager().loadMap(AppPreferencesHelper.BILLING_DETAIL_MAP);
        String billingAccountNumber = bAcc.get(subscriberIdentifier);
        RestHelper restHelper = new RestHelper();
        try {
            JSONObject par = new JSONObject();
            par.put("serviceInstanceDetail", subscriberIdentifier);
            par.put("billingAccountRef", billingAccountNumber);
            par.put("basicProduct", planName);
            AppUtils.errorLog("changePlan :", "JSONObject " + par.toString());
            restHelper.sendRequest(1, API_CHANGE_PLAN, API_CHANGE_PLAN, new RestHelper.RestHelperCallback() {
                @Override
                public void onRequestCallback(int code, String message, RestResponse restResponse) {
                    Log.d("request sent:response:", restResponse.toString());
                    final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
                    if (finalJsonObject != null) {
                        try {
                            Log.e("finalJsonObject", finalJsonObject + "");
                            BaseResponse responseData
                                    = AppUtils.getObjFromString(finalJsonObject.toString(), BaseResponse.class);
                            onApiCallback.onSuccess(responseData);
                        } catch (Exception e) {
                            onApiCallback.onFailed(code, message);
                            e.printStackTrace();
                        }
                    } else {
                        onApiCallback.onFailed(code, message);
                    }
                }
            }, par);
        } catch (Exception e) {
            AppUtils.errorLog("AppApiHelper", "changePlan err");
            AppUtils.errorLog("err", e + " ");
        }
    }


    private void getDetails(final long l, final PostPaidPlansForYouDTO postPaidPlansForYouDTO, final OnApiCallback<PostPaidPlansForYouDTO> onApiCallback) {
        final Context context = DCCMDealerSterlite.appContext;
        productCount.put(l, 0);
        for (final PlanPackage plan : postPaidPlansForYouDTO.getProduct().getProducts())
            getProductbyCode(plan.getCode(), new ApiHelper.OnApiCallback<String>() {
                @Override
                public void onSuccess(String desc) {
                    Log.e("Success", "Product by code");
                    Integer cnt = (productCount.get(l));
                    cnt++;
                    productCount.put(l, cnt);
                    if (desc != null) {
                        Log.e("getDescription", desc);
                        String description = desc;
                        Log.e("Plan Code desc", desc + "");
                        String[] arrOfStr = desc.split("#");
                        postPaidPlansForYouDTO.getProduct().getGeneratedPlans().add(DummyLists.getPlan(plan.getCode(), postPaidPlansForYouDTO.getProduct().getCategoryCode(),
                                plan.getName(),
                                plan.getOneTimeProductPrice().getValue() + "",
//                                plan.getPrice().getValue() + "",
                                DummyLists.getData(plan.getCode(), context),
                                "Rollover upto 200GB",
                                DummyLists.getFreeBenefits(plan.getCode()),
                                DummyLists.getPlansBenefits(plan.getCode(), context),
                                DummyLists.getAdditionalBenefits(plan.getCode(), context),
                                arrOfStr[0],
                                arrOfStr[1],
                                arrOfStr[2], context));
                    } else {

                        postPaidPlansForYouDTO.getProduct().getGeneratedPlans().add(DummyLists.getPlan(plan.getCode(), postPaidPlansForYouDTO.getProduct().getCategoryCode(),
                                plan.getName(),
                                plan.getOneTimeProductPrice().getValue() + "",
//                                plan.getPrice().getValue() + "",
                                DummyLists.getData(plan.getCode(), context),
                                "Rollover upto 200GB",
                                DummyLists.getFreeBenefits(plan.getCode()),
                                DummyLists.getPlansBenefits(plan.getCode(), context),
                                DummyLists.getAdditionalBenefits(plan.getCode(), context),
                                DummyLists.getCallRates(plan.getCode()),
                                DummyLists.getSMSRates(plan.getCode()),
                                DummyLists.getValiditys(plan.getCode()), context));

                    }

                    if (cnt == (postPaidPlansForYouDTO.getProduct().getProducts().size())) {
                        onApiCallback.onSuccess(postPaidPlansForYouDTO);
                    }
                }

                /*  public Plan getPlan(String id, String type, String title, String price, String data, String rollover, String freeBenefits, ArrayList<PlanBenefit> planBenefits, ArrayList<String> additionalBenefits, String callRates, String sms, String validity) {
                      Plan plan = new Plan();
                      if (id.equalsIgnoreCase(Constants.CODE_MODEM)) {
                          plan.setPlanId(id);
                          plan.setPlanType(type);
                          plan.setPlanTitle(title);
                          plan.setPricePerMonth(price);
                          plan.setDataInGB(data);
                          plan.setRolloverDataInGB(rollover);
                          plan.setFreeBenefitInRupee(freeBenefits);
                          plan.setArrBenefits(planBenefits);
                          plan.setArrAdditionalBenefits(additionalBenefits);
                          plan.setCallRate("Speed @100Mbps");
                          plan.setSms("");
                          plan.setValidity(getValidity(validity));
                      } else {
                          plan.setPlanId(id);
                          plan.setPlanType(type);
                          plan.setPlanTitle(title);
                          plan.setPricePerMonth(price);
                          plan.setDataInGB(data);
                          plan.setRolloverDataInGB(rollover);
                          plan.setFreeBenefitInRupee(freeBenefits);
                          plan.setArrBenefits(planBenefits);
                          plan.setArrAdditionalBenefits(additionalBenefits);
                          plan.setCallRate(getCallRate(callRates));
                          plan.setSms(getSMSRate(sms));
                          plan.setValidity(getValidity(validity));
                      }

                      return plan;
                  }

                  public String getData(String code) {

                      String data = null;

                      if (code.equalsIgnoreCase(Constants.CODE1)) {

                          data = context.getString(R.string.twelve_gb);

                      } else if (code.equalsIgnoreCase(Constants.CODE2)) {

                          data = context.getString(R.string.twenty_gb);

                      } else if (code.equalsIgnoreCase(Constants.CODE3)) {

                          data = context.getString(R.string.ten_gb);

                      } else if (code.equalsIgnoreCase(Constants.CODE4)) {

                          data = context.getString(R.string.two_gb);
                      } else if (code.equalsIgnoreCase(Constants.CODE5)) {

                          data = context.getString(R.string.four_gb);
                      } else if (code.equalsIgnoreCase(Constants.CODE6)) {

                          data = context.getString(R.string.two_gb);
                      } else if (code.equalsIgnoreCase(Constants.CODE7)) {

                          data = context.getString(R.string.twenty_gb);
                      } else if (code.equalsIgnoreCase(Constants.CODE8)) {

                          data = context.getString(R.string.fourteen_gb);
                      } else if (code.equalsIgnoreCase(Constants.CODE9)) {

                          data = context.getString(R.string.sixty_six_gb);
                      } else if (code.equalsIgnoreCase(Constants.CODE10)) {

                          data = context.getString(R.string.two_gb);
                      } else if (code.equalsIgnoreCase(Constants.CODE11)) {

                          data = context.getString(R.string.twenty_two_gb);
                      } else if (code.equalsIgnoreCase(Constants.CODE12)) {

                          data = context.getString(R.string.fourteen_gb);
                      } else if (code.equalsIgnoreCase(Constants.CODE13)) {

                          data = context.getString(R.string.sixty_six_gb);
                      } else if (code.equalsIgnoreCase(Constants.CODE_BB)) {

                          data = context.getString(R.string.three_gb);
                      } else if (code.equalsIgnoreCase(Constants.CODE_MODEM)) {

                          data = context.getString(R.string.four_gb);
                      } else if (code.equalsIgnoreCase(Constants.CODE_IPTV)) {

                          data = context.getString(R.string.two_gb);
                      }

                      return data;
                  }

                  public String getFreeBenefits(String code) {

                      String freeBenefits = null;

                      if (code.equalsIgnoreCase(Constants.CODE1)) {

                          freeBenefits = "500";

                      } else if (code.equalsIgnoreCase(Constants.CODE2)) {

                          freeBenefits = "1000";

                      } else if (code.equalsIgnoreCase(Constants.CODE3)) {

                          freeBenefits = "200";

                      } else if (code.equalsIgnoreCase(Constants.CODE4)) {

                          freeBenefits = "100";
                      } else if (code.equalsIgnoreCase(Constants.CODE5)) {

                          freeBenefits = "200";

                      }else if (code.equalsIgnoreCase(Constants.CODE6)) {
                          freeBenefits = "100";

                      } else if (code.equalsIgnoreCase(Constants.CODE7)) {
                          freeBenefits = "400";

                      } else if (code.equalsIgnoreCase(Constants.CODE8)) {
                          freeBenefits = "300";

                      } else if (code.equalsIgnoreCase(Constants.CODE9)) {
                          freeBenefits = "200";

                      } else if (code.equalsIgnoreCase(Constants.CODE10)) {
                          freeBenefits = "100";

                      } else if (code.equalsIgnoreCase(Constants.CODE11)) {
                          freeBenefits = "400";

                      } else if (code.equalsIgnoreCase(Constants.CODE12)) {
                          freeBenefits = "300";

                      } else if (code.equalsIgnoreCase(Constants.CODE13)) {
                          freeBenefits = "200";

                      }  else if (code.equalsIgnoreCase(Constants.CODE_IPTV)) {

                          freeBenefits = "200";

                      } else if (code.equalsIgnoreCase(Constants.CODE_BB)) {

                          freeBenefits = "300";

                      } else if (code.equalsIgnoreCase(Constants.CODE_MODEM)) {

                          freeBenefits = "400";

                      } else {

                          freeBenefits = "100";

                      }

                      return freeBenefits;
                  }

                  public ArrayList<PlanBenefit> getPlansBenefits(String code) {
                      ArrayList<PlanBenefit> arrBenefts = new ArrayList<>();
                      if (code.equalsIgnoreCase(Constants.CODE1)) {

                          arrBenefts.add(getPlanBenefit(Constants.VIDEO, context.getString(R.string.video_max), context.getString(R.string.two_gb_video_max)));
                      } else if (code.equalsIgnoreCase(Constants.CODE2)) {

                          arrBenefts.add(getPlanBenefit(Constants.VIDEO, context.getString(R.string.video_max), context.getString(R.string.two_gb_video_max)));
                          arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.fifty_min_voice)));
                          arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.fifty_sms)));


                      } else if (code.equalsIgnoreCase(Constants.CODE3)) {

                          arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.thirty_min_voice)));
                      } else if (code.equalsIgnoreCase(Constants.CODE4)) {
                          arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.fifty_sms)));
                      } else if (code.equalsIgnoreCase(Constants.CODE5)) {
                          arrBenefts.add(getPlanBenefit(Constants.DATA, "", context.getString(R.string.two_gb_3g_data)));
                      }else if (code.equalsIgnoreCase(Constants.CODE6)) {
                          arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.fifty_sms)));

                      } else if (code.equalsIgnoreCase(Constants.CODE7)) {
                          arrBenefts.add(getPlanBenefit(Constants.VIDEO, context.getString(R.string.video_max), context.getString(R.string.two_gb_video_max)));
                          arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.fiftyfive_min_voice)));
                          arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.fifty_sms)));

                      } else if (code.equalsIgnoreCase(Constants.CODE8)) {
                          arrBenefts.add(getPlanBenefit(Constants.VIDEO, context.getString(R.string.video_max), context.getString(R.string.two_gb_video_max)));

                      } else if (code.equalsIgnoreCase(Constants.CODE9)) {
                          arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.thirty_three_min_voice)));
                          arrBenefts.add(getPlanBenefit(Constants.NETFLIX, context.getString(R.string.arrbenefits_netflix), context.getString(R.string.eleven_gb)));
                          arrBenefts.add(getPlanBenefit(Constants.PRIME, context.getString(R.string.arrbenefits_prime), context.getString(R.string.thirty_gb)));
                          arrBenefts.add(getPlanBenefit(Constants.YOUTUBE, context.getString(R.string.arrbenefits_youtube), context.getString(R.string.twenty_two_gb)));

                      }  else if (code.equalsIgnoreCase(Constants.CODE10)) {
                          arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.fifty_sms)));

                      } else if (code.equalsIgnoreCase(Constants.CODE11)) {
                          arrBenefts.add(getPlanBenefit(Constants.VIDEO, context.getString(R.string.video_max), context.getString(R.string.two_gb_video_max)));
                          arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.fifty_min_voice)));
                          arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.fifty_sms)));

                      } else if (code.equalsIgnoreCase(Constants.CODE12)) {
                          arrBenefts.add(getPlanBenefit(Constants.VIDEO, context.getString(R.string.video_max), context.getString(R.string.two_gb_video_max)));

                      } else if (code.equalsIgnoreCase(Constants.CODE13)) {
                          arrBenefts.add(getPlanBenefit(Constants.VOICE, context.getString(R.string.arrbenefits_voice), context.getString(R.string.thirty_min_voice)));

                      }  else if (code.equalsIgnoreCase(Constants.CODE_IPTV)) {
                          arrBenefts.add(getPlanBenefit(Constants.ROUTER, "", context.getString(R.string.hundred_mbps)));
                      } else if (code.equalsIgnoreCase(Constants.CODE_BB)) {
                          arrBenefts.add(getPlanBenefit(Constants.ROUTER, "", context.getString(R.string.Fifty_mbps)));
                      } else if (code.equalsIgnoreCase(Constants.CODE_MODEM)) {
                          arrBenefts.add(getPlanBenefit(Constants.ROUTER, "", context.getString(R.string.hundred_mbps)));
                      } else {
                          arrBenefts.add(getPlanBenefit(Constants.SMS, context.getString(R.string.arrbenefits_sms), context.getString(R.string.fifty_sms)));

                      }

                      return arrBenefts;
                  }

                  public PlanBenefit getPlanBenefit(String image, String title, String des) {
                      PlanBenefit planBenefit = new PlanBenefit();
                      planBenefit.setBenefit_Image(image);
                      planBenefit.setTitle(title);
                      planBenefit.setDescription(des);
                      return planBenefit;
                  }

                  public ArrayList<String> getAdditionalBenefits() {
                      ArrayList<String> arrAddBenefits = new ArrayList<>();
                      if (Constants.APP.equalsIgnoreCase(Constants.MTN)) {
                          arrAddBenefits.add(context.getString(R.string.arrbenefits_download1) + Constants.MTN + context.getString(R.string.arrbenefits_download2));
                          arrAddBenefits.add(context.getString(R.string.arrbenefits_south_africa));

                      } else {
                          arrAddBenefits.add(context.getString(R.string.arrbenefits_download1) + Constants.TELKOMSEL + context.getString(R.string.arrbenefits_download2));
                          arrAddBenefits.add(context.getString(R.string.arrbenefits_indonesia));

                      }
                      return arrAddBenefits;
                  }

                  public ArrayList<String> getAdditionalBenefits(String code) {
                      ArrayList<String> arrAddBenefits = new ArrayList<>();
                      if (Constants.APP.equalsIgnoreCase(Constants.MTN)) {
                          switch (code) {
                              case Constants.CODE_MODEM:
                                  arrAddBenefits.add("IPTV having different type of contents like HDTV and future coming 3D TV");
                                  arrAddBenefits.add("Bandwidth on Demand (User and or service configurable)");
                                  arrAddBenefits.add("TV over IP Service (MPEG2).");
                                  break;
                              default:
                                  arrAddBenefits.add(context.getString(R.string.arrbenefits_download1) + Constants.MTN + context.getString(R.string.arrbenefits_download2));
                                  arrAddBenefits.add(context.getString(R.string.arrbenefits_south_africa));
                                  break;
                          }


                      } else {
                          arrAddBenefits.add(context.getString(R.string.arrbenefits_download1) + Constants.TELKOMSEL + context.getString(R.string.arrbenefits_download2));
                          arrAddBenefits.add(context.getString(R.string.arrbenefits_indonesia));

                      }
                      return arrAddBenefits;
                  }


                  public String getValiditys(String code) {
                      switch (code) {
                          case Constants.CODE1:
                              return "1";
                          case Constants.CODE2:
                              return "1";
                          case Constants.CODE3:
                              return "1";
                          case Constants.CODE4:
                              return "1";
                          case Constants.CODE5:
                              return "1";
                          default:
                              return "1";
  //                        case Constants.CODE4:
  //                            return "1";
  //                        case Constants.CODE5:
  //                            return "1";
                      }
                  }

                  public String getSMSRates(String code) {
                      switch (code) {
                          case Constants.CODE1:
                              return "0.700";
                          case Constants.CODE2:
                              return "0.500";
                          case Constants.CODE3:
                              return "0.700";
                          case Constants.CODE4:
                              return "0.500";
                          case Constants.CODE5:
                              return "0.700";
                          case Constants.CODE7:
                              return "0.500";
                          case Constants.CODE11:
                              return "0.500";
                          default:
                              return "0.700";
  //                        case Constants.CODE4:
  //                        return "0.500";
  //                        case Constants.CODE5:
  //                        return "0.700";
                      }
                  }

                  public String getCallRates(String code) {
                      switch (code) {
                          case Constants.CODE1:
                              return "1.100";
                          case Constants.CODE2:
                              return "1.000";
                          case Constants.CODE3:
                              return "1.100";
                          case Constants.CODE4:
                              return "1.000";
                          case Constants.CODE5:
                              return "1.100";
                          case Constants.CODE7:
                              return "1.000";
                          case Constants.CODE11:
                              return "1.000";
                          default:
                              return "1.100";
  //                        case Constants.CODE4:
  //                            return "1.000";
  //                        case Constants.CODE5:
  //                            return "1.100";
                      }
                  }

                  public String getSMSRate(String sms) {
                      return context.getString(R.string.callrate_sms1) + sms + context.getString(R.string.callrate_sms2);
                  }

                  public String getCallRate(String callRates) {
                      return context.getString(R.string.callrate_minute1) + callRates + context.getString(R.string.callrate_minute2);
                  }

                  public String getValidity(String validity) {
                      return context.getString(R.string.plan_validity) + validity + context.getString(R.string.month);
                  }
  */
                @Override
                public void onFailed(int code, String message) {
                    Integer cnt = (productCount.get(l));
                    cnt++;
                    productCount.put(l, cnt);
                    if (cnt == (postPaidPlansForYouDTO.getProduct().getProducts().size())) {
                        onApiCallback.onSuccess(postPaidPlansForYouDTO);
                    }
                }
            });
    }

    private static HashMap<Long, Integer> productCount = new HashMap<>();


    @Override
    public void findCustomerByDealerId(String dealerID, final OnApiCallback<CustomerListWsDTO> onApiCallback) {
        RestHelper restHelper = new RestHelper();
       // dealerID="les24121800503";
        if (dealerID != null) {
            String URL = API_GET_ALL_CUSTOMER_BY_DEALERID.trim() + dealerID.toLowerCase();
            Log.e("URL", URL);
            try {
                    restHelper.sendRequest(2, URL, URL, new RestHelper.RestHelperCallback() {
                //restHelper.sendRequest(2, RestHelper.REQUEST_TYPE_GET, API_GET_ALL_CUSTOMER_BY_DEALERID, API_GET_ALL_CUSTOMER_BY_DEALERID, RestHelper.CONTENT_TYPE_FROMDATA, null, null, null, new RestHelper.RestHelperCallback() {
                    @Override
                    public void onRequestCallback(int code, String message, RestResponse restResponse) {
                        try {
                            final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
                            if (finalJsonObject != null) {
                                ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                                CustomerListWsDTO customerListWsDTO = mapper.readValue(finalJsonObject.toString(), CustomerListWsDTO.class);
                                Log.e("customerListWsDTO::",customerListWsDTO.toString());
                                onApiCallback.onSuccess(customerListWsDTO);

                            } else {
                                onApiCallback.onFailed(code, message);
                            }
                        } catch (Exception e) {
                            onApiCallback.onFailed(code, message);
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {

            }
        }

    }

    @Override
    public void createTicket(final Ticket ticket, final OnApiCallback<Ticket> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        try {
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String jsonString = mapper.writeValueAsString(ticket);
            JSONObject jsonObject = new JSONObject(jsonString);
            Log.e("createTicket::","jsonString : "+ jsonString);
            restHelper.sendRequest(4, API_CREATE_TICKET,API_CREATE_TICKET, new RestHelper.RestHelperCallback() {
                @Override
                public void onRequestCallback(int code, String message, RestResponse restResponse) {
                    try {
                        final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
                        if (finalJsonObject != null) {
                            ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                            Ticket response= mapper.readValue(finalJsonObject.toString(), Ticket.class);
                            Log.e("createTicket::","Ticket response :" +response.printResponse());
                            onApiCallback.onSuccess(response);
                        } else {
                            onApiCallback.onFailed(code, message);
                        }
                    } catch (Exception e) {
                        onApiCallback.onFailed(code, message);
                        e.printStackTrace();
                    }
                }
            },jsonObject);
        } catch (Exception e) {
            Log.e("createTicket :"," Exception :" + e);
        }
    }

    @Override
    public void getTicketCategory(final OnApiCallback<ArrayList<String>> onApiCallback) {
        Log.e("AppApiHelper","getTicketCategory : start");
        final RestHelper restHelper = new RestHelper();
        try{
            restHelper.sendRequest(4, API_GET_TICKET_CATEGORY,API_GET_TICKET_CATEGORY, new RestHelper.RestHelperCallback() {
                @Override
                public void onRequestCallback(int code, String message, RestResponse restResponse) {
                    try {
                        final JSONObject categoryJsonObject = AppUtils.checkResponse(code, message, restResponse);
                        if (categoryJsonObject != null) {
                            Log.e("AppApiHelper::","getTicketCategory response category :" +categoryJsonObject);
                            int i=0;
                            ArrayList<String> categories = new ArrayList<>();
                            while(true){
                                String key = i+"";
                                if(categoryJsonObject.has(key)){
                                    String category = categoryJsonObject.get(key).toString();
                                    categories.add(category);
                                }else{
                                    break;
                                }
                                i++;
                            }
                            Log.e("getTicketCategory :","categories " + categories);
                            onApiCallback.onSuccess(categories);
                        } else {
                            onApiCallback.onFailed(code, message);
                        }
                    } catch (Exception e) {
                        onApiCallback.onFailed(code, message);
                        e.printStackTrace();
                        Log.e("createTicket :","category Exception :" + e);
                    }
                }
            });
        }catch(Exception e){
            Log.e("createTicket :"," Exception :" + e);
        }
    }

    @Override
    public void getTicketSubCategory(String category , final OnApiCallback<ArrayList<String>> onApiCallback) {
        Log.e("AppApiHelper","getTicketSubCategory : start");
        final RestHelper restHelper = new RestHelper();
        try{
            restHelper.sendRequest(4, API_GET_TICKET_SUB_CATEGORY+category,API_GET_TICKET_SUB_CATEGORY+category,new RestHelper.RestHelperCallback() {
                @Override
                public void onRequestCallback(int code, String message, RestResponse restResponse) {
                    try {
                        final JSONObject subCategoryJsonObject = AppUtils.checkResponse(code, message, restResponse);
                        if (subCategoryJsonObject != null) {
                            Log.e("AppApiHelper::","getTicketCategory response sub category :" +subCategoryJsonObject);
                            int i=0;
                            ArrayList<String> subCategories = new ArrayList<>();
                            while(true){
                                String key = i+"";
                                if(subCategoryJsonObject.has(key)){                                                        ;
                                    subCategories.add(subCategoryJsonObject.get(key).toString());
                                }else{
                                    break;
                                }
                                i++;
                            }
                            Log.e("getTicketSubCategory :","categories " + subCategories);
                            onApiCallback.onSuccess(subCategories);
                        } else {
                            onApiCallback.onFailed(code, message);
                        }
                    } catch (Exception e) {
                        onApiCallback.onFailed(code, message);
                        e.printStackTrace();
                        Log.e("getTicketSubCategory :","sub Category Exception :" + e);
                    }
                }
            });
        }catch(Exception e){
            Log.e("getTicketSubCategory :"," Exception :" + e);
        }
    }

    @Override
    public void getTicketByCustomer(String customerNumber , final OnApiCallback<ArrayList<Ticket>> onApiCallback) {
        Log.e("AppApiHelper","getTicketByCustomer : start");
        final RestHelper restHelper = new RestHelper();
        HashMap<String, String> qryparams = new HashMap<>();
        qryparams.put("customerNumber",customerNumber);
        try{
            restHelper.sendRequest(4, API_GET_TICKET,API_GET_TICKET,qryparams,null,null,new RestHelper.RestHelperCallback() {
                @Override
                public void onRequestCallback(int code, String message, RestResponse restResponse) {
                    try {
                        final JSONObject baseResponse = AppUtils.checkResponse(code, message, restResponse);
                        if (baseResponse != null) {
                            Log.e("AppApiHelper::","getTicketByCustomer response sub category :" +baseResponse);

                            JSONArray array = baseResponse.getJSONArray("ticket");
                            ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                            ArrayList<Ticket> list = mapper.readValue(array.toString(), new TypeReference<ArrayList<Ticket>>() {});
                            onApiCallback.onSuccess(list);
                        } else {
                            onApiCallback.onFailed(code, message);
                        }
                    } catch (Exception e) {
                        onApiCallback.onFailed(code, message);
                        e.printStackTrace();
                        Log.e("getTicketSubCategory :","sub Category Exception :" + e);
                    }
                }
            });
        }catch(Exception e){
            Log.e("getTicketSubCategory :"," Exception :" + e);
        }
    }


    @Override
    public void updateCustomerProfileDetails(final GetUpdateCustomerRequestData getUpdateCustomerRequestData, final OnApiCallback<GetUpdateCustomerResponseData> onApiCallback) {
        RestHelper restHelper = new RestHelper();
        try {
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String jsonString = mapper.writeValueAsString(getUpdateCustomerRequestData);
            JSONObject jsonObject = new JSONObject(jsonString);
            Log.e("update Profile Details:","jsonString : "+ jsonString);
            restHelper.sendRequest(4, API_CRM_UPDATE_CUSTOMER_DETAILS,API_CRM_UPDATE_CUSTOMER_DETAILS, new RestHelper.RestHelperCallback() {
                @Override
                public void onRequestCallback(int code, String message, RestResponse restResponse) {
                    try {
                        final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
                        if (finalJsonObject != null) {
                            ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                            GetUpdateCustomerResponseData response= mapper.readValue(finalJsonObject.toString(), GetUpdateCustomerResponseData.class);
                            Log.e("update Profile Details:","update Profile response: :" +response.toString());
                            onApiCallback.onSuccess(response);
                        } else {
                            onApiCallback.onFailed(code, message);
                        }
                    } catch (Exception e) {
                        onApiCallback.onFailed(code, message);
                        e.printStackTrace();
                    }
                }
            },jsonObject);
        } catch (Exception e) {
            Log.e("update Profile Details:"," Exception :" + e);
        }
    }


    @Override
    public void getProfileDetailsbyCustomerNumber(String customerNumber , final OnApiCallback<GetUpdateCustomerResponseData> onApiCallback) {
        Log.e("AppApiHelper","getProfileDetailsbyCustomerNumber : start");
        final RestHelper restHelper = new RestHelper();
        HashMap<String, String> qryparams = new HashMap<>();
        qryparams.put("customerNumber",customerNumber);
        try{
            restHelper.sendRequest(4, API_CRM_GET_CUSTOMER_DETAILS,API_CRM_GET_CUSTOMER_DETAILS,qryparams,null,null,new RestHelper.RestHelperCallback() {
                @Override
                public void onRequestCallback(int code, String message, RestResponse restResponse) {
                    try {
                        final JSONObject finalJsonObject = AppUtils.checkResponse(code, message, restResponse);
                        if (finalJsonObject != null) {
                            Log.e("AppApiHelper::","getProfilebyCustNumber response:" +finalJsonObject);

                            ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                            GetUpdateCustomerResponseData response= mapper.readValue(finalJsonObject.toString(), GetUpdateCustomerResponseData.class);
                            onApiCallback.onSuccess(response);
                        } else {
                            onApiCallback.onFailed(code, message);
                        }
                    } catch (Exception e) {
                        onApiCallback.onFailed(code, message);
                        e.printStackTrace();
                        Log.e("getProfilebyCustNumber:","Exception :" + e);
                    }
                }
            });
        }catch(Exception e){
            Log.e("getProfilebyCustNumber:"," Exception :" + e);
        }
    }


}






