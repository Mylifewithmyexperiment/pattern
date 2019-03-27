package com.elitecore.elitesmp.api;

import android.content.Context;

import com.elitecore.elitesmp.listener.OnEliteSMPTaskCompleteListner;
import com.elitecore.elitesmp.pojo.PojoQueryData;
import com.elitecore.elitesmp.pojo.UserRegistrationDetail;

import java.util.HashMap;

public interface IEliteSMPAPI {

	 void intializeURL(String webinURL) throws Exception;

	void doLogout(int requestId, String userName, String serviceType, String... args)
			throws Exception;

	void doLogin(int requestId,
                 String... args) throws Exception;//String userName, String password,String serviceType

	void doVoucherregisterAccount(int requestId,
                                  String voucherCode, String name, String useraName,
                                  String password, String email, String phone) throws Exception;
	void rechargeAccount(int requestId,
                         String userName, String password, String vocherId) throws Exception;

	void doOnlineRegisterAccount(int requestId,
                                 String name, String useraName,
                                 String password, String email, String phone, String packageId, String paymentGateway, String redirectURL) throws Exception;

	void doOnlineRechargeAccount(int requestId,
                                 String userName, String password, String packageId, String paymentGateway, String redirectURL) throws Exception;

	void doFreeregisterAccount(int requestId, String pakcageId,
                               String name, String useraName, String password, String email, String phone, String... serviceType) throws Exception;

	void getPackages(int requestId, String OperationType) throws Exception;
	void getPaymentGateway(int requestId) throws Exception;


	void voucherVerification(int requestId, String voucherCode) throws Exception;

	void getBrandData(int requestId) throws Exception;
	void getPartnerData(int requestId) throws Exception;

	void getSubscriberData(int requestId, String userName, String serviceType, String... args) throws Exception;
	void verifyPasswordPolicy(int requestId, String password, String serviceType) throws Exception;

	void sendOTP(int requestId, String mobileNumber, String operation) throws Exception;

	void verifyOTP(int requestId, String mobileNumber, String otpCode) throws Exception;
	 void doRedirectRequest(Context context, OnEliteSMPTaskCompleteListner callback,
                            int requestId, String url) throws Exception;

	void doDbScanOperation(int requestId,
                           String name, String operation, String baseSearch, String recordFetchCount, PojoQueryData queryData, String... reqType) throws Exception;

	void doPremiumRegisterAccout(int requestID, UserRegistrationDetail userDetails) throws Exception;

	void doPremiumLogin(int requestId, UserRegistrationDetail userDetail) throws Exception;

	void doPremiumLogout(int requestId, UserRegistrationDetail userDetail) throws Exception;

	void doAutoLogin(int requestId, String phone, String otp, String packageId, String channel, String offload, String ipAddress) throws  Exception;

	void doFreeRenewalAccount(int requestId, String pakcageId,
                              String serviceType, String name, String useraName, String password, String email, String phone, String... args) throws Exception;

	void doPGLogIn(int requestId, String PhoneNo, String Channel, String IPAddress)
			throws Exception;

	void doPGLogOut(int requestId, String PhoneNo, String Channel, String IPAddress)
			throws Exception;

	void doSendNotification(int requestId, String QueryString, String JsonString)
			throws Exception;

	void forgetPassword(int requestId, String userIdentity, String ServiceType, String brand) throws Exception;

	void genericAPI(int requestId, String methodName, HashMap<String, String> queryParams, String jsonParams, String contentType) throws Exception;
}

