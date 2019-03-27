package com.elitecore.elitesmp.api;

import android.content.Context;

import com.elitecore.elitesmp.listener.IBaseEliteListner;
import com.elitecore.elitesmp.listener.OnEliteSMPTaskCompleteListner;
import com.elitecore.elitesmp.pojo.PojoQueryData;
import com.elitecore.elitesmp.pojo.UserRegistrationDetail;
import com.elitecore.elitesmp.utility.ElitePropertiesUtil;
import com.elitecore.elitesmp.utility.EliteSMPUtilConstants;
import com.elitecore.elitesmp.utility.EliteSMPUtility;
import com.elitecore.wifi.api.EliteWiFIConstants;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.pojo.PojoSubscriber;
import com.elitecorelib.core.services.ConnectionManagerCompleteListner;
import com.elitecorelib.core.services.ConnectionManagerTaskNew;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.QueryParams;
import com.elitecorelib.core.utility.SharedPreferencesTask;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.elitecorelib.core.CoreConstants.GRADLE_SMP_SERVER_URL;
import static com.elitecorelib.core.utility.ElitelibUtility.getMetaDataValue;

/**
 * @author viratsinh.parmar
 */
public class EliteSMPAPI implements IEliteSMPAPI,ConnectionManagerCompleteListner {

    private String MODULE = "EliteSMPAPI";
    private IBaseEliteListner contextListener;
    private Context context;
    private String Que = "?";
    PojoSubscriber subscriberData;
    private static SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();
/*
    public EliteSMPAPI() {
		super();
	}*/

    public EliteSMPAPI(IBaseEliteListner contextListener) {
        this.contextListener = contextListener;
        if (contextListener instanceof Context) {
            context = (Context) contextListener;
        }
        subscriberData = new PojoSubscriber();
    }

    @Override
    public void intializeURL(String webinURL)
            throws Exception {
        // TODO Auto-generated method stub
        EliteSession.eLog.d(MODULE, " intializeURL invoked with URL value =" + webinURL);
        LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveString(EliteSMPUtilConstants.ELITESMP_URL, webinURL);
    }

    public void doRegister() {
        if (spTask.getBooleanFirstFalse(CoreConstants.DO_REGISTER) == false) {
            EliteSession.eLog.d(MODULE, " Registration process");
            subscriberData = ElitelibUtility.setUserRegistrationInformation(subscriberData, LibraryApplication.getLibraryApplication().getLibraryContext());
            subscriberData.setRegisterWith(CoreConstants.REGISTERWITH);
            try {
                Gson gson = new Gson();
                ConnectionManagerTaskNew task = new ConnectionManagerTaskNew(EliteSMPAPI.this,
                        CoreConstants.MONETIZATION_REGISTERUSERPROFILE_REQUESTID);
                task.execute(gson.toJson(subscriberData), LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() +
                        CoreConstants.MONETIZATION_REGISTERUSERPROFILE);
            } catch (Exception e) {
                EliteSession.eLog.e(MODULE, " Registration request error " + e.getMessage());
            }
        }
    }

    @Override
    public void onConnnectionManagerTaskComplete(String result, int requestId) {
        if (result != null && requestId == CoreConstants.MONETIZATION_REGISTERUSERPROFILE_REQUESTID) {
            EliteSession.eLog.d(MODULE, " Registration request invoked");
            if (result.trim().compareTo("") != 0) {
                EliteSession.eLog.i(MODULE, " Registration response found " + result);
                Gson gson = new Gson();
                PojoConfigModelResponse resObj = gson.fromJson(result, PojoConfigModelResponse.class);
                if (resObj.getResponseCode() == 1) {
                    spTask.saveBoolean(CoreConstants.DO_REGISTER, true);
                }
            }
        }
    }

    @Override
    public void getPackages(int requestId, String OperationType) throws Exception {
        EliteSession.eLog.i(MODULE, " getPackages invoked");
        methodValidation("getPackages");
        QueryParams qp = new QueryParams();
        String queryString = getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSGETPACKAGE + Que;
        if (OperationType != null && !OperationType.isEmpty()) {
            try
            {
                qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_OPERATION), OperationType);
            }
            catch (Exception e)
            {
                EliteSession.eLog.e(MODULE,e.getMessage());
            }
            queryString += qp.toQueryString();

        }

        EliteSMPHelper.getEliteSMPHelper().getPackages((OnEliteSMPTaskCompleteListner) contextListener, requestId, queryString);
    }

    @Override
    public void getPaymentGateway(int requestId) throws Exception {
        EliteSession.eLog.i(MODULE, " getPaymentGateway invoked");
        methodValidation("getPaymentGateway");
        String queryString = getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSGETPAYMENTGATEWAY ;
        EliteSMPHelper.getEliteSMPHelper().getPaymentGateway((OnEliteSMPTaskCompleteListner) contextListener, requestId, queryString);

    }

    /**
     * This method is use to login normal user
     *
     * @param requestId
     * @param args
     * @throws Exception
     */
    @Override
    public void doLogin(int requestId, String... args)//String userName, String password,String servicetype
            throws Exception {

        methodValidation("doLogin");
        EliteSession.eLog.i(MODULE, " doLogin invoked");
        QueryParams qp = new QueryParams();
        String queryString = getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSLOGIN + Que;

        if (args[0] != null && !args[0].isEmpty()) {
            qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_USERIDENTITY), args[0]);
        }
        if (args[1] != null && !args[1].isEmpty()) {
            qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PASSWORD), args[1]);
        }
        queryString += qp.toQueryString();
        Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
        EliteSMPHelper.getEliteSMPHelper().doLogin( (OnEliteSMPTaskCompleteListner) contextListener, requestId, queryString, EliteSMPUtility.getIPAddress(context), args);


    }

    /**
     * This method is use to logout normal user
     *
     * @param requestId
     * @param userName
     * @param serviceType
     * @throws Exception
     */
    @Override
    public void doLogout(int requestId, String userName, String serviceType,String ... args) throws Exception {
        EliteSession.eLog.i(MODULE, " ddoLogoutoLogin invoked");
        methodValidation("doLogout");
        QueryParams qp = new QueryParams();
        String queryString = getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSLOGOUT + Que;

        if (userName != null && !userName.isEmpty()) {
            qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_USERIDENTITY), userName);
        }
        queryString += qp.toQueryString();

        EliteSMPHelper.getEliteSMPHelper().doLogout( (OnEliteSMPTaskCompleteListner) contextListener, requestId, queryString, userName, EliteSMPUtility.getIPAddress(context), serviceType,args);

    }


    @Override
    public void doVoucherregisterAccount(int requestId,
                                         String voucherCode, String name, String useraName, String password,
                                         String email, String phone) throws Exception {
        EliteSession.eLog.i(MODULE, " doVoucherregisterAccount invoked");
        methodValidation("doVoucherregisterAccount");
        EliteSMPHelper.getEliteSMPHelper().doVoucherregisterAccount( (OnEliteSMPTaskCompleteListner) contextListener, requestId, getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSREGISTERACCOUNT, voucherCode, name, useraName, password, email, phone);

    }

    @Override
    public void rechargeAccount(int requestId,
                                String userName, String password, String vocherId) throws Exception {
        EliteSession.eLog.i(MODULE, " rechargeAccount invoked");
        methodValidation("rechargeAccount");
        EliteSMPHelper.getEliteSMPHelper().rechargeAccount( (OnEliteSMPTaskCompleteListner) contextListener, requestId, getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSRECHARGEACCOUNT, userName, password, vocherId);
    }

    @Override
    public void doOnlineRegisterAccount(int requestId, String name, String useraName, String password, String email, String phone, String packageId, String paymentGateway, String redirectURL) throws Exception {
        EliteSession.eLog.i(MODULE, " doOnlineRegisterAccount invoked");
        methodValidation("doOnlineRegisterAccount");
        EliteSMPHelper.getEliteSMPHelper().doOnlineRegisterAccount( (OnEliteSMPTaskCompleteListner) contextListener, requestId, getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSREGISTERACCOUNT, name, useraName, password, email, phone, packageId, paymentGateway, redirectURL);
    }

    @Override
    public void doOnlineRechargeAccount(int requestId, String userName, String password, String packageId, String paymentGateway, String redirectURL) throws Exception {
        EliteSession.eLog.i(MODULE, " doOnlineRechargeAccount invoked");
        methodValidation("doOnlineRechargeAccount");
        EliteSMPHelper.getEliteSMPHelper().doOnlineRechargeAccount( (OnEliteSMPTaskCompleteListner) contextListener, requestId, getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSRECHARGEACCOUNT, userName, password, packageId, paymentGateway, redirectURL);
    }

    @Override
    public void voucherVerification(int requestId,
                                    String voucherCode) throws Exception {
        EliteSession.eLog.i(MODULE, " voucherVerification invoked");
        methodValidation("voucherVerification");
        String append = EliteSMPUtilConstants.ELITESMP_URL_WSVOUCHERVERIFICATION+"?voucherCode="+voucherCode;
        String url = getCommonURL() + append;
        EliteSMPHelper.getEliteSMPHelper().voucherVerification( (OnEliteSMPTaskCompleteListner) contextListener, requestId, url);
    }

    @Override
    public void getBrandData(int requestId)
            throws Exception {
        EliteSession.eLog.i(MODULE, " getBrandData invoked");
        methodValidation("getBrandData");
        EliteSMPHelper.getEliteSMPHelper().getBrandData( (OnEliteSMPTaskCompleteListner) contextListener, requestId, getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSGetBrandData, EliteSMPUtility.getIPAddress(context));
    }

    @Override
    public void getPartnerData(int requestId)
            throws Exception {
        EliteSession.eLog.i(MODULE, " getPartnerData invoked");
        methodValidation("getPartnerData");
        EliteSMPHelper.getEliteSMPHelper().getPartnerData( (OnEliteSMPTaskCompleteListner) contextListener, requestId, getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSGETPARTNERDATA, EliteSMPUtility.getIPAddress(context));
    }


    @Override
    public void doFreeregisterAccount(int requestId,
                                      String pakcageId, String name, String useraName, String password,
                                      String email, String phone, String ... serviceType) throws Exception {
        EliteSession.eLog.i(MODULE, " doFreeregisterAccount invoked");
        methodValidation("doFreeregisterAccount");
        EliteSMPHelper.getEliteSMPHelper().doFreeregisterAccount( (OnEliteSMPTaskCompleteListner) contextListener, requestId, getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSREGISTERACCOUNT, pakcageId, name, useraName, password, email, phone, serviceType);


    }

    /**
     * This method is use to get subscriber data
     *
     * @param requestId
     * @param userName
     * @param serviceType
     * @throws Exception
     */
    @Override
    public void getSubscriberData(int requestId,
                                  String userName, String serviceType,String ... args) throws Exception {
        EliteSession.eLog.i(MODULE, " getSubscriberData invoked");
        methodValidation("getSubscriberData");
        QueryParams qp = new QueryParams();
        String queryString = getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSGETSUBSCRIBERDATA + Que;

        if (userName != null && !userName.isEmpty()) {
            qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_USERIDENTITY), userName);
        }
        queryString += qp.toQueryString();
        EliteSMPHelper.getEliteSMPHelper().getSubscriberData( (OnEliteSMPTaskCompleteListner) contextListener, requestId, queryString, serviceType, args);
    }

    @Override
    public void verifyPasswordPolicy(int requestId, String password, String serviceType) throws Exception {
        EliteSession.eLog.d(MODULE, " verifyPasswordPolicy invoked");
        methodValidation("verifyPasswordPolicy");
        QueryParams qp = new QueryParams();
        String queryString = getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSPASSWORDVERIFICATION + Que;

        if (password != null && !password.isEmpty()) {
            qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PASSWORD), password);
        }
        queryString += qp.toQueryString();
        EliteSMPHelper.getEliteSMPHelper().verifyPasswordPolicy( (OnEliteSMPTaskCompleteListner) contextListener, requestId, queryString, serviceType);

    }

    /**
     * This method is use to send otp to user
     *
     * @param requestId
     * @param mobileNumber
     * @throws Exception
     */
    @Override
    public void sendOTP(int requestId, String mobileNumber, String operation) throws Exception {
        EliteSession.eLog.i(MODULE, " getSubscriberData invoked");
        methodValidation("sendOTP");
        QueryParams qp = new QueryParams();
        String queryString = getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSSENDOTP + Que;

        if (mobileNumber != null && !mobileNumber.isEmpty()) {
            qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_MOBILENUMBER), mobileNumber);
        }
        queryString += qp.toQueryString();
        EliteSMPHelper.getEliteSMPHelper().sendOTP( (OnEliteSMPTaskCompleteListner) contextListener, requestId, operation, queryString);
    }

    @Override
    public void verifyOTP(int requestId,
                          String mobileNumber, String otpCode) throws Exception {
        // TODO Auto-generated method stub
        EliteSession.eLog.i(MODULE, " verifyOTP invoked");
        methodValidation("verifyOTP");
//        String append = EliteSMPUtilConstants.ELITESMP_URL_WSVERIFYOTP.replace("VALUE1", mobileNumber).replace("VALUE2", otpCode);
        String append = EliteSMPUtilConstants.ELITESMP_URL_WSVERIFYOTP + "?mobileNumber="+mobileNumber+"&otp="+otpCode;
        String url = getCommonURL() + append;

        EliteSMPHelper.getEliteSMPHelper().verifyOTP( (OnEliteSMPTaskCompleteListner) contextListener, requestId, url);

    }

    @Override
    public void doRedirectRequest(Context context, OnEliteSMPTaskCompleteListner callback, int requestId, String url) throws Exception {
        EliteSession.eLog.i(MODULE, " doRedirectRequest invoked");
        methodValidation("doRedirectRequest");
        EliteSMPHelper.getEliteSMPHelper().doRedirectRequest(callback, requestId, url);
    }

    @Override
    public void doDbScanOperation(int requestId, String name, String operation, String baseSearch, String recordFetchCount, PojoQueryData queryData,String... reqType) throws Exception {
        EliteSession.eLog.i(MODULE, " doDbScanOperation invoked");
        methodValidation("doDbScanOperation");
        String queryString = "";
        Gson gson = new Gson();
        queryString = queryString + getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSDBSCAN + Que;
//		if(getConfigProperty(EliteSMPUtilConstants.KEY_BASESEARCH).trim().compareTo("")!=0)
//			queryString=queryString+Que+getConfigProperty(EliteSMPUtilConstants.KEY_BASESEARCH)+"="+baseSearch;
//
//		if(getConfigProperty(EliteSMPUtilConstants.KEY_NAME).trim().compareTo("")!=0)
//			queryString=queryString+"&" + getConfigProperty(EliteSMPUtilConstants.KEY_NAME)+"=" + name;
//		if(getConfigProperty(EliteSMPUtilConstants.KEY_OPERATION).trim().compareTo("")!=0)
//			queryString=queryString+"&"+getConfigProperty(EliteSMPUtilConstants.KEY_OPERATION)+"="+operation;
//		if(getConfigProperty(EliteSMPUtilConstants.KEY_RECORDFETCHCOUNT).trim().compareTo("")!=0)
//			queryString=queryString+"&"+getConfigProperty(EliteSMPUtilConstants.KEY_RECORDFETCHCOUNT)+"="+recordFetchCount;
//		if(getConfigProperty(EliteSMPUtilConstants.KEY_QUERYDATA).trim().compareTo("")!=0)
//			queryString=queryString+"&"+getConfigProperty(EliteSMPUtilConstants.KEY_QUERYDATA)+"="+gson.toJson(queryData, PojoQueryData.class);

//		queryString=queryString.replaceAll("\"", "\\\\\"");
        EliteSession.eLog.i(MODULE, " doDbScanOperation resolving query paramters");
        QueryParams qp = new QueryParams();

        if (baseSearch != null)
            qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_BASESEARCH), baseSearch);
        if (name != null)
            qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_NAME), name);
        if (operation != null)
            qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_OPERATION), operation);
        if (recordFetchCount != null)
            qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_RECORDFETCHCOUNT), recordFetchCount);
        if (queryData != null)
            qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_QUERYDATA), gson.toJson(queryData, PojoQueryData.class));
        queryString = queryString + qp.toQueryString();
        EliteSession.eLog.i(MODULE, " doDbScanOperation  query paramters resolved");
        EliteSMPHelper.getEliteSMPHelper().doDbScanOperation( (OnEliteSMPTaskCompleteListner) contextListener, requestId, queryString,reqType);


    }

    /**
     * This method is use to Register premium user by credit card or voucher
     *
     * @param requestId
     * @param userDetails
     * @throws Exception
     */
    @Override
    public void doPremiumRegisterAccout(int requestId, UserRegistrationDetail userDetails) throws Exception {
        EliteSession.eLog.i(MODULE, " doPremiumRegisterAccout invoked");
        methodValidation("doPremiumRegisterAccout");

        if (userDetails == null) {
            throw new Exception("User details object is null which is required for registration ");
        }
        String queryString = getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSREGISTERACCOUNT;

        EliteSMPHelper.getEliteSMPHelper().doPremiumRegisterAccount( (OnEliteSMPTaskCompleteListner) contextListener, requestId, queryString, EliteSMPUtility.getIPAddress(context), userDetails);

    }

    /**
     * This method is use to login premium user
     *
     * @param requestId
     * @param userDetail
     * @throws Exception
     */
    public void doPremiumLogin(int requestId, UserRegistrationDetail userDetail) throws Exception {
        EliteSession.eLog.i(MODULE, " doPremiumLogin invoked");
        methodValidation("doPremiumLogin");
        if (userDetail == null) {
            throw new Exception("User details object is null which is required for login ");
        }
        EliteSession.eLog.i(MODULE, " doPremiumLogin resolving query paramters");
        QueryParams qp = new QueryParams();
        String queryString = getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSLOGIN + Que;

        if (userDetail.getUserName() != null && !userDetail.getUserName().isEmpty()) {
            qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_USERIDENTITY), userDetail.getUserName());
        }
        if (userDetail.getPassWord() != null && !userDetail.getPassWord().isEmpty()) {
            qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PASSWORD), userDetail.getPassWord());
        }
        queryString += qp.toQueryString();
        EliteSession.eLog.i(MODULE, " doPremiumLogin  query paramters resolved");
        EliteSMPHelper.getEliteSMPHelper().doPremiumLogin( (OnEliteSMPTaskCompleteListner) contextListener, requestId, queryString, EliteSMPUtility.getIPAddress(context), userDetail);
    }

    /**
     * This method is use to logout premium user
     *
     * @param requestId
     * @param userDetail
     * @throws Exception
     */
    public void doPremiumLogout(int requestId, UserRegistrationDetail userDetail) throws Exception {
        EliteSession.eLog.i(MODULE, " doPremiumLogout invoked");
        methodValidation("doPremiumLogout");
        if (userDetail == null) {
            throw new Exception("User details object is null which is required for logout");
        }
        EliteSession.eLog.i(MODULE, " doPremiumLogout resolving query paramters");
        QueryParams qp = new QueryParams();
        String queryString = getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSLOGOUT + Que;

        if (userDetail.getUserName() != null && !userDetail.getUserName().isEmpty()) {
            qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_USERIDENTITY), userDetail.getUserName());
        }
        queryString += qp.toQueryString();
        EliteSMPHelper.getEliteSMPHelper().doPremiumLogout( (OnEliteSMPTaskCompleteListner) contextListener, requestId, queryString, EliteSMPUtility.getIPAddress(context), userDetail);
        EliteSession.eLog.i(MODULE, " doPremiumLogout  query paramters resolved");

    }

    @Override
    public void doAutoLogin(int requestId, String phone, String otp, String packageId, String channel, String offload, String ipAddress) throws Exception {
        methodValidation("doAutoLogin");
        EliteSession.eLog.i(MODULE, " Setting query parameters.");
        QueryParams qp = new QueryParams();
        String queryString = getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSLOGIN + Que;

        qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PHONE), phone);
        qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_OTP), otp);
        qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PACKAGEID), packageId);
        qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_CHANNEL), channel);
        if (ipAddress != null && ipAddress.trim().compareTo("") != 0)
            qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_IPADDRESS), ipAddress);// EliteSMPUtility.getIPAddress(context)
        else
            qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_IPADDRESS), EliteSMPUtility.getIPAddress(context));// EliteSMPUtility.getIPAddress(context)

        qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_OFFLOAD_FLAG), offload);
        queryString += qp.toQueryString();
        EliteSession.eLog.d(MODULE, "Setting query parameters completed");
        EliteSMPHelper.getEliteSMPHelper().doAutoLogin( (OnEliteSMPTaskCompleteListner) contextListener, requestId, queryString);

    }

    @Override
    public void doFreeRenewalAccount(int requestId, String pakcageId, String serviceType, String name, String useraName, String password, String email, String phone,String ... args) throws Exception {
        EliteSession.eLog.i(MODULE, " doFreeRenewalAccount invoked");
        methodValidation("doFreeRenewalAccount");
        EliteSMPHelper.getEliteSMPHelper().doFreeRenewalAccount( (OnEliteSMPTaskCompleteListner) contextListener,
                requestId,
                getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSRENEWACCOUNT,
                EliteSMPUtility.getIPAddress(context),
                pakcageId,
                serviceType,
                name,
                useraName,
                password,
                email,
                phone,
                args);

    }

    @Override
    public void doPGLogIn(int requestId,String PhoneNo, String Channel, String IPAddress) throws Exception {
        EliteSession.eLog.i(MODULE, " doPGLogin invoked");
        QueryParams qp = new QueryParams();
        String queryString = getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSPGLOGIN;

        queryString += qp.toQueryString();
        EliteSession.eLog.i(MODULE, " doPGLogin query string " + queryString);
        Context context = LibraryApplication.getLibraryApplication().getLibraryContext();

        if(IPAddress.isEmpty() && IPAddress == null){
            EliteSession.eLog.i(MODULE, " doPGLogin invoked ipaddress null");
            IPAddress = EliteSMPUtility.getIPAddress(context);
        }

        EliteSMPHelper.getEliteSMPHelper().doPGLogin( (OnEliteSMPTaskCompleteListner) contextListener, requestId, queryString, PhoneNo, Channel, IPAddress);
    }

    @Override
    public void doPGLogOut(int requestId,String PhoneNo, String Channel, String IPAddress) throws Exception {
        EliteSession.eLog.i(MODULE, " doPGLogout invoked");
        QueryParams qp = new QueryParams();
        String queryString = getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSPGLOGOUT;

        queryString += qp.toQueryString();
        EliteSession.eLog.i(MODULE, " doPGLogout query string " + queryString);
        if(IPAddress.isEmpty() && IPAddress == null){
            EliteSession.eLog.i(MODULE, " doPGLogout invoked ipaddress null");
            IPAddress = EliteSMPUtility.getIPAddress(context);
        }

        EliteSMPHelper.getEliteSMPHelper().doPGLogout( (OnEliteSMPTaskCompleteListner) contextListener, requestId, queryString, PhoneNo, Channel, IPAddress);

    }
    @Override
    public void forgetPassword(int requestId, String userIdentity, String ServiceType, String brand) throws Exception {
        EliteSession.eLog.i(MODULE, " forgetPassword invoked");
        methodValidation("forgetPassword");
        QueryParams qp = new QueryParams();
        String queryString = getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSFORGOTPASSWORD+Que;
        if (userIdentity != null && !userIdentity.isEmpty()) {
            qp.addParam(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_USERIDENTITY), userIdentity);
        }
        queryString += qp.toQueryString();
        EliteSession.eLog.i(MODULE, "Query String "+queryString);
        EliteSMPHelper.getEliteSMPHelper().forgetPassword( (OnEliteSMPTaskCompleteListner) contextListener,
                requestId,
                queryString,
                ServiceType,
                brand);
    }

    /**
     * Call Generic Type API using this API we can call ALL TYPE of the Webservice Api
     * @param requestId  using this we can get call back method
     * @param methodName set the methodName which is we need to call
     * @param queryParams pass the data to set the Query params which is append in URL in GET method
     * @param jsonParams pass the data in post method usign JSONParmas
     * @param contentType identyfy the contenttYpe like Application/Json or Text
     * @throws Exception
     */
    @Override
    public void genericAPI(int requestId, String methodName, HashMap<String, String> queryParams, String jsonParams, String contentType) throws Exception {

        methodValidation(methodName);
        EliteSession.eLog.i(MODULE, "Call Generic method api.");
        QueryParams qp = new QueryParams();

        EliteSession.eLog.i(MODULE, "SMP Method name: " +methodName);

        Pattern p = Pattern.compile("@", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(methodName);
        boolean b = m.find();

        EliteSession.eLog.i(MODULE, "matcher boolean: " + b);

        String queryString="";
        if (b) {
            List<String> myList = new ArrayList<String>(Arrays.asList(methodName.split("@")));
            queryString = myList.get(1).toString() + Que;
            System.out.println("There is a special character in my string");
        }else {
            queryString = getCommonURL() + methodName + Que;
            System.out.println("There is not a special character in my string");
        }

//        String queryString = getCommonURL() + methodName + Que;
        // append the query params in URL
        if(queryParams != null && queryParams.size() > 0) {
          for (String paramsKey : queryParams.keySet()) {
                qp.addParam(paramsKey, queryParams.get(paramsKey));
            }
        }
        queryString += qp.toQueryString();
        EliteSession.eLog.i(MODULE, "Query result:: " + queryString);
        EliteSMPHelper.getEliteSMPHelper().genericAPICall((OnEliteSMPTaskCompleteListner) contextListener,requestId,queryString,jsonParams,contentType);
    }

    @Override
    public void doSendNotification(int requestId, String QueryString, String JsonString) throws Exception {
        methodValidation("doSendNotification");
        EliteSession.eLog.i(MODULE, " Setting query parameters.");
        String queryString = getCommonURL() + EliteSMPUtilConstants.ELITESMP_URL_WSSENDNOTIFICATION + Que + QueryString;

        EliteSMPHelper.getEliteSMPHelper().doSendNotification( (OnEliteSMPTaskCompleteListner) contextListener, requestId, queryString,JsonString);
    }

    private String getCommonURL() {
        if (LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(EliteSMPUtilConstants.ELITESMP_URL) == null) {
            return getMetaDataValue(GRADLE_SMP_SERVER_URL);
        } else if (LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(EliteSMPUtilConstants.ELITESMP_URL) != null
                && LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(EliteSMPUtilConstants.ELITESMP_URL).isEmpty()) {
            return getMetaDataValue(GRADLE_SMP_SERVER_URL);
        }
        return LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(EliteSMPUtilConstants.ELITESMP_URL);

    }

    private void methodValidation(String methodName) throws Exception {
        EliteSession.eLog.d(MODULE, methodName + " invoked");
        if (context == null)
            context = LibraryApplication.getLibraryApplication().getLibraryContext();
        if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_LOCAL) == 0 && !LibraryApplication.isLicenseValidated(context))
            throw new Exception("Application ID is not Valid");

        if (CoreConstants.ENABLE_MONETIZATION_REGISTRATION && LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_SERVER) == 0 && spTask.getBooleanFirstFalse(CoreConstants.DO_REGISTER) != true) {
            throw new Exception(CoreConstants.NOT_REGISTER);
        }

        if (getCommonURL() == null || (getCommonURL() != null && getCommonURL().isEmpty()))
            throw new Exception("EliteSMP IP port not initialize");
    }

    public class PojoConfigModelResponse implements Serializable {

        private String responseMessage;
        private int responseCode;

        public String getResponseMessage() {
            return responseMessage;
        }
        public void setResponseMessage(String responseMessage) {
            this.responseMessage = responseMessage;
        }
        public int getResponseCode() {
            return responseCode;
        }
        public void setResponseCode(int responseCode) {
            this.responseCode = responseCode;
        }

    }
}
