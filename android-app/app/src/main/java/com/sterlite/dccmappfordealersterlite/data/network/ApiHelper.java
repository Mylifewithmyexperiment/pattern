package com.sterlite.dccmappfordealersterlite.data.network;


import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.data.network.model.DeviceToken;
import com.sterlite.dccmappfordealersterlite.data.network.model.Pagging;
import com.sterlite.dccmappfordealersterlite.model.Activation.ActivationResponseModel;
import com.sterlite.dccmappfordealersterlite.model.CustomerListWsDTO;
import com.sterlite.dccmappfordealersterlite.model.FindSubscriptionByEmailModel;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel.MakeMyPlanItems;

import com.sterlite.dccmappfordealersterlite.model.HomeFragment.SubscriberServicewiseBalance;
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

public interface ApiHelper {


    interface OnApiCallback<V> {
        void onSuccess(V baseResponse);

        void onFailed(int code, String message);
    }

    void doGetMyAccountData(String subscriberIdentifier, String tenantId, OnApiCallback<SubscriberServicewiseBalance> callback);

    void getMakeMyPlanBasicPlan(OnApiCallback<Pagging<MakeMyPlanItems>> onApiCallback);

    void getMakeMyPlanDataAddOn(OnApiCallback<Pagging<MakeMyPlanItems>> onApiCallback);

    void getMakeMyPlanDataSubAddOn(OnApiCallback<Pagging<MakeMyPlanItems>> onApiCallback);

    void getMakeMyPlanMinAddOn(OnApiCallback<Pagging<MakeMyPlanItems>> onApiCallback);

    void getMakeMyPlanSMSAddOn(OnApiCallback<Pagging<MakeMyPlanItems>> onApiCallback);

    void getMakeMyPlanRoamingAddOn(OnApiCallback<Pagging<MakeMyPlanItems>> onApiCallback);

    void doActivateMyOrder(String orderNo, String serviceNo, OnApiCallback<ActivationResponseModel> apiCallback);

    void doGetOrderTrackingDetails(String serialNo, OnApiCallback<OrderTrackingResponseModel> onApiCallback);

    void getPlanPackageInternet(OnApiCallback<Pagging<PlanPackage>> onApiCallback);

    void getPlanPackageRoaming(OnApiCallback<Pagging<PlanPackage>> onApiCallback);

    void getPlanPackageEntertainment(OnApiCallback<Pagging<PlanPackage>> onApiCallback);

    void getPlanPackageSms(OnApiCallback<Pagging<PlanPackage>> onApiCallback);

    void doSubscribeAddOn(String paymentMode,String subscriptionNumber, String productName, OnApiCallback<SubscribeAddOnResponceModel> onApiCallback);

    void getMyOrders(OnApiCallback<MyOrdersListResponseModel> onApiCallback);

    void getMyCart(OnApiCallback<String> onApiCallback);

    void findSubscriptionByEmailId(String userId, OnApiCallback<FindSubscriptionByEmailModel> onApiCallback);

    void doRegisterDeviceTokenToServer(DeviceToken device, OnApiCallback<DeviceToken> onApiCallback);

    void getCustomerDetails(String userId,OnApiCallback<ServiceInstanceDetail[]> onApiCallback);

    void getInventoryNumber(String userId,OnApiCallback<String> onApiCallback);

    void findStore(String pincode,OnApiCallback<ArrayList<Store>> onApiCallback);

    void getSIDetails(String accountNUmber, final OnApiCallback<ServiceInstanceDetail> onApiCallback);

    void doUnSubscribeAddOn(String subscriptionNumber, String externalSystemOfferId, final OnApiCallback<String> onApiCallback);

     void getProductbyCode( String productCode,final OnApiCallback<String> onApiCallback);

    void getBillDetails(String billingAccountNumber, final OnApiCallback<BillingDetailData[]> onApiCallback);

    void downloadBill(String billNumber, final OnApiCallback<String> onApiCallback);

    void getPaymentHistory(String billingAccNo, OnApiCallback<SearchPaymentResponseData> onApiCallback);

    void uploadDocumentAPI(String customerID,String imageKey ,String imagePath, final OnApiCallback<Integer> onApiCallback) ;

     //void deleteMyCart(final String cartId,final OnApiCallback<String> onApiCallback);
     void getPostPaidPlansForYou(String customerID, String categoryCode, OnApiCallback<PostPaidPlansForYouDTO>onApiCallback);

     void changePlan(String planName, OnApiCallback<BaseResponse> onApiCallback);

     void findCustomerByDealerId(String dealerID, final OnApiCallback<CustomerListWsDTO> onApiCallback);

    void createTicket(Ticket ticket, final OnApiCallback<Ticket> onApiCallback);

    void getTicketCategory(final OnApiCallback<ArrayList<String>> onApiCallback);

    void getTicketSubCategory(String category , final OnApiCallback<ArrayList<String>> onApiCallback);

    void getTicketByCustomer(String customerNumber , final OnApiCallback<ArrayList<Ticket>> onApiCallback);

     void updateCustomerProfileDetails(final GetUpdateCustomerRequestData getUpdateCustomerRequestData, final OnApiCallback<GetUpdateCustomerResponseData> onApiCallback);

        void getProfileDetailsbyCustomerNumber(String customerNumber , final OnApiCallback<GetUpdateCustomerResponseData> onApiCallback);


    }
