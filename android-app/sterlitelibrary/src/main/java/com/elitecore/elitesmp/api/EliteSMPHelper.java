package com.elitecore.elitesmp.api;

import android.os.AsyncTask;

import com.elitecore.elitesmp.listener.OnEliteSMPTaskCompleteListner;
import com.elitecore.elitesmp.pojo.Plan;
import com.elitecore.elitesmp.pojo.UserRegistrationDetail;
import com.elitecore.elitesmp.utility.ElitePropertiesUtil;
import com.elitecore.elitesmp.utility.EliteSMPUtilConstants;
import com.elitecore.elitesmp.utility.EliteSMPUtility;
import com.elitecore.wifi.api.CommonWiFiUtility;
import com.elitecore.wifi.api.EliteWiFIConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.api.EliteConnectCore;
import com.elitecorelib.core.listner.CoreTaskCompleteListner;
import com.elitecorelib.core.utility.QueryParams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.elitecorelib.core.CoreConstants.GRADLE_SMP_SERVER_URL;
import static com.elitecorelib.core.utility.ElitelibUtility.getMetaDataValue;

final class EliteSMPHelper implements CoreTaskCompleteListner {
    private static final String MODULE = "EliteSMPHelper";
    private static EliteSMPHelper eliteSMPHelper;
    private OnEliteSMPTaskCompleteListner callback;

    private EliteSMPHelper() {

    }

    public static EliteSMPHelper getEliteSMPHelper() {
        if (eliteSMPHelper == null) {
            eliteSMPHelper = new EliteSMPHelper();
        }
        return eliteSMPHelper;
    }

    public static Map<String, String> jsonToMap(JSONObject json) throws JSONException {

        Map<String, String> retMap = new HashMap<String, String>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static Map<String, String> toMap(JSONObject object) throws JSONException {
        Map<String, String> map = new HashMap<String, String>();
        EliteSession.eLog.d(MODULE, "toMap invoked");
        try {
            Iterator<String> keysItr = object.keys();
            while (keysItr.hasNext()) {
                String key = keysItr.next();
                Object value = object.get(key);

                if (value instanceof JSONObject) {
                    value = toMap((JSONObject) value);
                }
                map.put(key, value.toString());
            }
            EliteSession.eLog.d(MODULE, "toMap invoked iterator");
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }

        return map;
    }

    private void executeAsyncTask(OnEliteSMPTaskCompleteListner callback, final String url, final String jsonString, int requestId) {
        this.callback = callback;
        try {
            final EliteConnectCore eliteConnect = new EliteConnectCore(this, requestId);
            EliteSession.eLog.i(MODULE, "input JSON parameters:- " + jsonString);
            new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    if (jsonString != null && !jsonString.isEmpty()) {
                        EliteSession.eLog.i(MODULE, "input JSON parameters are " + jsonString);
                        eliteConnect.invokeService(url, jsonString);
                    } else {
                        eliteConnect.invokeService(url, "{}");
                    }
                    return null;
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void getPackages(OnEliteSMPTaskCompleteListner callback, final int requestId, final String url) {
        EliteSession.eLog.d(MODULE, "getPackages invoked");
        EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);
        executeAsyncTask(callback, url, "", requestId);
    }

    public void getPaymentGateway(OnEliteSMPTaskCompleteListner callback, final int requestId, final String url) {
        EliteSession.eLog.d(MODULE, "getPaymentGateway invoked");
        EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);
        executeAsyncTask(callback, url, "", requestId);
    }

    public void doVoucherregisterAccount(OnEliteSMPTaskCompleteListner callback, int requestId, final String url, String voucherCode, String name, String userName, String password, String email, String phone) {
        final JSONObject jsonObject = new JSONObject();
        try {
            EliteSession.eLog.d(MODULE, "doVoucherregisterAccount invoked");
            EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);

            //Params pass from user input
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_VOUCHERCODECAP), voucherCode);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_FIRSTNAME), name);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_USERNAME), userName);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SUBUSERNAME), userName);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PASSWORD), password);
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PASSWORD_CAP) != null) {
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PASSWORD_CAP), password);
            }

            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_EMAIL), email);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PHONE), phone);


            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_MSISDN) != null) {
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_MSISDN), phone);
            }


            //params and value added from library
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SERVICETYPE), ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.VALUE_SERVICETYPE_WALKIN));
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_LOCATION), EliteSMPUtilConstants.LOCATION_ALL);
            executeAsyncTask(callback, url, jsonObject.toString(), requestId);
        } catch (JSONException e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void rechargeAccount(OnEliteSMPTaskCompleteListner callback, int requestId, final String url, String userName, String password, String vocherId) {
        EliteSession.eLog.d(MODULE, "rechargeAccount invoked");
        EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);
        final JSONObject jsonObject = new JSONObject();
        try {
            //Params pass from user input
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_USERNAME), userName);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SUBUSERNAME), userName);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PASSWORD), password);
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PASSWORD_CAP) != null) {
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PASSWORD_CAP), password);
            }
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_VOUCHERCODECAP), vocherId);

            //params and value added from library
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SERVICETYPE), ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.VALUE_SERVICETYPE_WALKIN));
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_LOCATION), EliteSMPUtilConstants.LOCATION_ALL);
            executeAsyncTask(callback, url, jsonObject.toString(), requestId);
        } catch (JSONException e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void doOnlineRegisterAccount(OnEliteSMPTaskCompleteListner callback, int requestId, final String url, String name, String useraName, String password, String email, String phone, String packageId, String paymentGateway, String redirectURL) {
        final JSONObject jsonObject = new JSONObject();
        try {
            EliteSession.eLog.d(MODULE, "doOnlineRegisterAccount invoked");
            EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SERVICETYPE), EliteSMPConstants.ELITESMP_WALKINTYPE);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_FIRSTNAME), name);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SUBUSERNAME), useraName);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PASSWORD), password);
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PASSWORD_CAP) != null) {
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PASSWORD_CAP), password);
            }
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_EMAIL), email);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PHONE), phone);
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_MSISDN) != null) {
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_MSISDN), phone);
            }
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_LOCATION), EliteSMPUtilConstants.LOCATION_ALL);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PACKAGE_ID), packageId);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SELECTEDPAYMENT_GATEWAY), paymentGateway);
//            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_WIP_URL), redirectURL);
            executeAsyncTask(callback, url, jsonObject.toString(), requestId);
        } catch (JSONException e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void doOnlineRechargeAccount(OnEliteSMPTaskCompleteListner callback, int requestId, final String url, String useraName, String password, String packageId, String paymentGateway, String redirectURL) {
        final JSONObject jsonObject = new JSONObject();
        try {
            EliteSession.eLog.d(MODULE, "doOnlineRechargeAccount invoked");
            EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SERVICETYPE), EliteSMPConstants.ELITESMP_WALKINTYPE);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SUBUSERNAME), useraName);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PASSWORD), password);
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PASSWORD_CAP) != null) {
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PASSWORD_CAP), password);
            }
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_LOCATION), EliteSMPUtilConstants.LOCATION_ALL);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PACKAGE_ID), packageId);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SELECTEDPAYMENT_GATEWAY), paymentGateway);
//            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_WIP_URL), redirectURL);
            executeAsyncTask(callback, url, jsonObject.toString(), requestId);
        } catch (JSONException e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void doFreeregisterAccount(OnEliteSMPTaskCompleteListner callback, int requestId, final String url, String packageId, String name, String useraName, String password, String email, String phone, String... arg) {

        final JSONObject jsonObject = new JSONObject();
        try {
            EliteSession.eLog.d(MODULE, "doFreeregisterAccount invoked");
            EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);

            if (arg != null && arg.length > 0 && arg[0] != null && arg[0].compareTo("") != 0 &&
                    arg[0].compareTo(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.VALUE_SERVICETYPE_COMPLIMENTARY)) != 0) {
                jsonObject.put(EliteSMPUtilConstants.SERVICETYPE, arg[0]);
            } else if (arg != null && arg.length > 0 && arg[0] != null && arg[0].compareTo("") != 0 &&
                    arg[0].compareTo(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.VALUE_SERVICETYPE_COMPLIMENTARY)) == 0) {
                jsonObject.put(EliteSMPUtilConstants.SERVICETYPE, EliteSMPConstants.ELITESMP_FREETYPE);
            } else {
                jsonObject.put(EliteSMPUtilConstants.SERVICETYPE, EliteSMPConstants.ELITESMP_FREETYPE);
            }

            jsonObject.put(EliteSMPUtilConstants.PACKAGEID, packageId);
            jsonObject.put(EliteSMPUtilConstants.PACKAGEBASE, "true");
            jsonObject.put(EliteSMPUtilConstants.FIRSTNAME, name);
            jsonObject.put(EliteSMPUtilConstants.SUBUSERNAME, useraName);
            jsonObject.put(EliteSMPUtilConstants.PASSWORD, password);

            jsonObject.put(EliteSMPUtilConstants.EMAIL, email);
            jsonObject.put(EliteSMPUtilConstants.PHONE, phone);
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_MSISDN) != null) {
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_MSISDN), phone);
            }
            if (arg != null && arg.length > 0 && arg[1] != null && arg[1].compareTo("") != 0) {
                jsonObject.put(EliteSMPUtilConstants.LOCATION, arg[1]);
            } else {
                jsonObject.put(EliteSMPUtilConstants.LOCATION, EliteSMPUtilConstants.LOCATION_ALL);
            }

            executeAsyncTask(callback, url, jsonObject.toString(), requestId);
        } catch (JSONException e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void doFreeRenewalAccount(OnEliteSMPTaskCompleteListner callback, int requestId,
                                     final String url, String IPAdress, String packageId,
                                     String serviceType, String name,
                                     String useraName, String password, String email, String phone, String... arg) {
        final JSONObject jsonObject = new JSONObject();
        try {
            EliteSession.eLog.d(MODULE, "doFreeRenewalAccount invoked");
            EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_FRAMEDIPADDRESS), IPAdress);
            jsonObject.put(EliteSMPUtilConstants.SERVICETYPE, serviceType);
            jsonObject.put(EliteSMPUtilConstants.PACKAGEID, packageId);
            jsonObject.put(EliteSMPUtilConstants.PACKAGEBASE, "true");
            jsonObject.put(EliteSMPUtilConstants.FIRSTNAME, name);
            jsonObject.put(EliteSMPUtilConstants.SUBUSERNAME, useraName);
            jsonObject.put(EliteSMPUtilConstants.PASSWORD, password);
            jsonObject.put(EliteSMPUtilConstants.EMAIL, email);
            jsonObject.put(EliteSMPUtilConstants.PHONE, phone);
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_MSISDN) != null) {
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_MSISDN), phone);
            }
            if (arg != null && arg.length > 0 && arg[0] != null && arg[0].compareTo("") != 0) {
                jsonObject.put(EliteSMPUtilConstants.LOCATION, arg[0]);
            } else {
                jsonObject.put(EliteSMPUtilConstants.LOCATION, EliteSMPUtilConstants.LOCATION_ALL);
            }
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SUBCUI) != null && arg[1] != null) {
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SUBCUI), arg[1]);
            }
            executeAsyncTask(callback, url, jsonObject.toString(), requestId);
        } catch (JSONException e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void doLogin(OnEliteSMPTaskCompleteListner callback, int requestId, final String url, final String IP, String... args)//final String url,String userName, String password,String servicetype, String sip)
    {
        EliteSession.eLog.d(MODULE, "doLogin invoked");
        EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);

        final JSONObject jsonObject = new JSONObject();
        try {
            //jsonObject.put(EliteSMPUtilConstants.SIP, sip);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_USERNAME), args[0]);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PASSWORD), args[1]);
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PASSWORD_CAP) != null) {
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PASSWORD_CAP), args[1]);
            }
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_FRAMEDIPADDRESS), IP);
            //jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_UAMIP), IP);

            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_IP) != null) {
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_IP), IP);
            } else {
                jsonObject.put("sip", IP);
            }
            //
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SERVICETYPE) != null && args.length > 4)
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SERVICETYPE), args[4]);
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_TYPE) != null && args.length > 2)
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_TYPE), args[2]);
            //	call wsGetPartnerData(IPAddress of WIFI leased to end customer) if location is not required pass "ALL".
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_LOCATION) != null && args.length > 8 && args[8] != null && !args[8].isEmpty() && !args[8].equals("")) {
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_LOCATION), args[8]);
            } else {
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_LOCATION), EliteSMPUtilConstants.LOCATION_ALL);
            }
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_OTP) != null && args.length > 3)
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_OTP), args[3]);
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_USERAGENT_TYPE) != null && args.length > 5)
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_USERAGENT_TYPE), args[5]);
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PHONE) != null && args.length > 6)
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PHONE), args[6]);
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.WEBSERVICES_SUBSCRIBER_EXPIRYDATE) != null && args.length > 7 && args[7].trim().compareTo("") != 0)
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.WEBSERVICES_SUBSCRIBER_EXPIRYDATE), args[7]);

            if (EliteSMPUtilConstants.KEY_BRAND != null)
                jsonObject.put(EliteSMPUtilConstants.KEY_BRAND, ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.VALUE_BRAND));
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PARAM6) != null)
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PARAM6), ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.VALUE_LOGIN_PARAM6));
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PARAM5) != null)
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PARAM5), ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.VALUE_LOGIN_PARAM5));
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_WEBINCWS) != null)
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_WEBINCWS), ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.VALUE_WEBINCWS));
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PARTNER) != null)
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PARTNER), ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.PARTNERNAME));
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_MAC) != null)
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_MAC), CommonWiFiUtility.getDeviceMacAddressOnlyText());

            executeAsyncTask(callback, url, jsonObject.toString(), requestId);
        } catch (JSONException e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void doAutoLogin(OnEliteSMPTaskCompleteListner callback, int requestId, final String url)//final String url,String userName, String password,String servicetype, String sip)
    {
        EliteSession.eLog.d(MODULE, "doAutoLogin invoked");
        EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);
        QueryParams qp = new QueryParams();


        try {
            Map<String, String> query_pairs = new LinkedHashMap<String, String>();
            URL url1 = new URL(url);
            String query = url1.getQuery();
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PHONE), query_pairs.get(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PHONE)));
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_OTP), query_pairs.get(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_OTP)));
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PACKAGEID), query_pairs.get(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PACKAGEID)));
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_CHANNEL), query_pairs.get(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_CHANNEL)));
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_IPADDRESS), query_pairs.get(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_IPADDRESS)));
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_OFFLOAD_FLAG), query_pairs.get(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_OFFLOAD_FLAG)));
            EliteSession.eLog.d(MODULE, "JSON passed for autologin " + jsonObject.toString());
            EliteSession.eLog.d(MODULE, "Final service URL=" + getMetaDataValue(GRADLE_SMP_SERVER_URL) + EliteSMPUtilConstants.ELITESMP_URL_WSLOGIN);
            executeAsyncTask(callback, getMetaDataValue(GRADLE_SMP_SERVER_URL) + EliteSMPUtilConstants.ELITESMP_URL_WSLOGIN, jsonObject.toString(), requestId);
        } catch (Exception e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void doLogout(OnEliteSMPTaskCompleteListner callback, int requestId, final String url, String userName, String sip, String serviceType, String... args) {
        EliteSession.eLog.d(MODULE, "doLogout invoked");
        EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);
        final JSONObject jsonObject = new JSONObject();
        try {
            //jsonObject.put(EliteSMPUtilConstants.SIP, sip);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_FRAMEDIPADDRESS), sip);
            if (ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_IP) != null) {
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_IP), sip);
            } else {
                jsonObject.put("sip", sip);
            }
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SERVICETYPE), serviceType);

            if (args != null && args.length > 0 && args[0] != null && args[0].compareTo("") != 0) {
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_LOCATION), args[0]);
            }

            if (args != null && args.length > 1 && args[1] != null && args[1].compareTo("") != 0) {
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PACKAGE_ID), args[1]);
            }
            executeAsyncTask(callback, url, jsonObject.toString(), requestId);
        } catch (JSONException e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void voucherVerification(OnEliteSMPTaskCompleteListner callback, int requestId, final String url) {
        EliteSession.eLog.d(MODULE, "voucherVerification invoked");
        EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);
        try {
            executeAsyncTask(callback, url, "", requestId);
        } catch (Exception e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void getBrandData(OnEliteSMPTaskCompleteListner callback, int requestId, final String url, String framedIpAddress) {
        EliteSession.eLog.d(MODULE, "get branddata invoked");
        EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(EliteSMPUtilConstants.FRAMEDIPADDRESS, framedIpAddress);
            executeAsyncTask(callback, url, jsonObject.toString(), requestId);
        } catch (JSONException e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void getPartnerData(OnEliteSMPTaskCompleteListner callback, int requestId, final String url, String framedIpAddress) {
        EliteSession.eLog.d(MODULE, "getPartnerData invoked");
        EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(EliteSMPUtilConstants.FRAMEDIPADDRESS, framedIpAddress);
            executeAsyncTask(callback, url, jsonObject.toString(), requestId);
        } catch (JSONException e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void getSubscriberData(OnEliteSMPTaskCompleteListner callback, int requestId,
                                  final String url, String serviceType, String... args) {
        EliteSession.eLog.d(MODULE, "getSubscriberData invoked");
        EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(EliteSMPUtilConstants.SERVICETYPE, serviceType);

            if (args != null && args.length > 0 && args[0] != null && args[0].compareTo("") != 0) {
                jsonObject.put(EliteSMPUtilConstants.LOCATION, args[0]);
            }

            if (args != null && args.length > 1 && args[1] != null && args[1].compareTo("") != 0) {
                jsonObject.put(EliteSMPUtilConstants.PACKAGEID, args[1]);
            }

            jsonObject.put(EliteSMPUtilConstants.SERVICETYPE, serviceType);
            executeAsyncTask(callback, url, jsonObject.toString(), requestId);
        } catch (JSONException e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void verifyPasswordPolicy(OnEliteSMPTaskCompleteListner callback, int requestId,
                                     final String url, String serviceType) {
        EliteSession.eLog.d(MODULE, "verifyPasswordPolicy invoked");
        EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(EliteSMPUtilConstants.SERVICETYPE, serviceType);
            executeAsyncTask(callback, url, jsonObject.toString(), requestId);
        } catch (JSONException e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void sendOTP(OnEliteSMPTaskCompleteListner callback, int requestId, String operation,
                        final String url) {
        EliteSession.eLog.d(MODULE, "sendOTP invoked");
        EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);

        try {
            final JSONObject jsonObject = new JSONObject();
//            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_OTP_OPERATION),
//                    operation);
            executeAsyncTask(callback, url, jsonObject.toString(), requestId);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void verifyOTP(OnEliteSMPTaskCompleteListner callback, int requestId,
                          final String url) {
        EliteSession.eLog.d(MODULE, "verifyOTP invoked");
        EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);
        try {
            executeAsyncTask(callback, url, "", requestId);
        } catch (Exception e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void doRedirectRequest(OnEliteSMPTaskCompleteListner callback, int requestId,
                                  final String url) {
        EliteSession.eLog.d(MODULE, "doRedirectRequest invoked");
        EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);
        try {
            executeAsyncTask(callback, url, "", requestId);
        } catch (Exception e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void doDbScanOperation(OnEliteSMPTaskCompleteListner callback, int requestId,
                                  final String url, final String... reqType) {
        EliteSession.eLog.d(MODULE, "doRedirectRequest invoked");
        EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);
        this.callback = callback;
        try {
            final EliteConnectCore eliteConnect = new EliteConnectCore(this, requestId);
            new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    if (reqType != null && reqType.length > 0)
                        eliteConnect.invokeService(url, "{}", reqType[0]);
                    else
//                        eliteConnect.invokeService(url, "", "text/plain");
                        eliteConnect.invokeService(url, "{}", null);

                    return null;
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    @Override
    public void onServiceTaskComplete(String result, int requestId) {
        // TODO Auto-generated method stub

        EliteSession.eLog.d(MODULE, " onServiceTaskComplete Result for requestId=" + requestId);

        if (requestId == EliteSMPConstants.ELITESMP_REQUEST_GETPACKAGE) {
            if (result != null && result.trim().compareTo("") != 0) {
                EliteSession.eLog.d(MODULE, "preparing response for ELITESMP_REQUEST_GETPACKAGE");

                EliteSession.eLog.d(MODULE, "Response = "+result);

                Gson gson = new Gson();
//                Type listType = new TypeToken<ArrayList<Plan>>() {
//                }.getType();

                Type listType = new TypeToken<List<Plan>>() {

                }.getType();

                List<Plan> planList = (List<Plan>) gson.fromJson(result, listType);

                callback.getPackageList(planList, requestId);


            } else {
                callback.getPackageList(null, requestId);
                return;
            }
        } else {
            if (result != null && result.trim().length() != 0) {
                try {
                    EliteSession.eLog.d(MODULE, "preparing Response MAP");
                    result = EliteSMPUtility.filterResponse(result, requestId);

                    if (result == null)
                        EliteSession.eLog.d(MODULE, "result after filter is" + result);

                    Object json = new JSONTokener(result).nextValue();

                    if (json instanceof JSONObject) {

                        JSONObject jsonObject = new JSONObject(result);
                        Map<String, String> map = jsonToMap(jsonObject);
                        EliteSession.eLog.d(MODULE, "sending call back from response map");
                        callback.getResponseMap(map, requestId);

                    } else if (json instanceof JSONArray) {
                        callback.getGenericResponse(result,requestId);
                    } else {
                        callback.getResponseMap(null, requestId);
                    }

                } catch (Exception e) {
                    EliteSession.eLog.e(MODULE, "Error while preparing response MAP " + e.getMessage());
                    callback.getResponseMap(null, requestId);
                }

            } else {
                EliteSession.eLog.e(MODULE, "Server Connection Error , Response null");
                callback.getResponseMap(null, requestId);
                return;
            }
        }
    }

    public void doPremiumRegisterAccount(OnEliteSMPTaskCompleteListner callback, int requestId, final String url, String frameIP, UserRegistrationDetail userDetails) throws Exception {
        final JSONObject jsonObject = new JSONObject();
        try {
            EliteSession.eLog.d(MODULE, "doPremiumRegisterAccount invoked");
            EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);

            jsonObject.put(EliteSMPUtilConstants.SUBUSERNAME, userDetails.getUserName());
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_OPERATION_CAP), "Registration");
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PASSWORD), userDetails.getPassWord());
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SERVICETYPE), ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.VALUE_SERVICE_TYPE));

            if (userDetails.getCreditCardDetail() != null) {
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PG_SELECTEDPAYGW), userDetails.getCreditCardDetail().getPg_selectedPayGw());
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_CREDITCARDNUMBER), userDetails.getCreditCardDetail().getCreditCardNumber());
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_CVVNUMBER), userDetails.getCreditCardDetail().getCvvNumber());
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PARAM4), userDetails.getCreditCardDetail().getExpMonth());
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PARAM5), userDetails.getCreditCardDetail().getExpYear());
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PARAM6), userDetails.getCurrencyType());
            }
            if (userDetails.getWifiPackageDetail() != null) {
                jsonObject.put(EliteSMPUtilConstants.KEY_PACKAGE_PRICE, userDetails.getWifiPackageDetail().getPrice());
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PACKAGE_VALIDITY), userDetails.getWifiPackageDetail().getValidity());
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PACKAGE_ID), userDetails.getWifiPackageDetail().getId());

            }
            if (userDetails.getWIPURL() != null) {
//                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_WIP_URL), userDetails.getWIPURL());
            }

            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_REGISTRATIONMETHOD), ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.VALUE_PREMIUM_REGISTRATION_METHOD));
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SSID), ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.VALUE_PREMIUM_REGISTRATION_METHOD));
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_APPARAMETER), "Registration");
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PARAM2), userDetails.getCardIdentifyNo());
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PARAM1), userDetails.getPaymentMethod());
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PARAM7), "0500000201     ");
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PARAM6), userDetails.getCurrencyType());
            jsonObject.put(EliteSMPUtilConstants.KEY_BRAND, userDetails.getBrandName());
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PARTNER), userDetails.getPartnerName());
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_WEBINCWS), true);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_FRAMEDIPADDRESS), frameIP);
            //jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PARTNER), "du_public_wifi");


            jsonObject.put(EliteSMPUtilConstants.LOCATION, EliteSMPUtilConstants.LOCATION_ALL);
            executeAsyncTask(callback, url, jsonObject.toString(), requestId);
        } catch (JSONException e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void doPremiumLogin(OnEliteSMPTaskCompleteListner callback, int requestId, final String url, String frameIP, UserRegistrationDetail userDetail) throws Exception {

        EliteSession.eLog.d(MODULE, "doPremiumLogin invoked");
        EliteSession.eLog.d(MODULE, "input requestId=" + requestId + " url=" + url);

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_USERNAME), userDetail.getUserName());
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PASSWORD), userDetail.getPassWord());
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_REGISTRATIONMETHOD), ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.VALUE_PREMIUM_REGISTRATION_METHOD));
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_FRAMEDIPADDRESS), frameIP);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SERVICETYPE), ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.VALUE_SERVICE_TYPE));
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_TYPE), ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.VALUE_PREMIUM_LOGIN_TYPE));
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SUBSCRIBERIDENTITY), userDetail.getUserName());
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_APPARAMETER), "Registration");
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_LOCATION), EliteSMPUtilConstants.LOCATION_ALL);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_USERAGENT_TYPE), userDetail.getUserAgent_Type());

            if (userDetail.getCardIdentifyNo() != null && !userDetail.getCardIdentifyNo().isEmpty())
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PARAM2), userDetail.getCardIdentifyNo());
            if (userDetail.getPaymentMethod() != null && !userDetail.getPaymentMethod().isEmpty())
                jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PARAM1), userDetail.getPaymentMethod());

            jsonObject.put(EliteSMPUtilConstants.KEY_BRAND, userDetail.getBrandName());
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PARTNER), userDetail.getPartnerName());
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_WEBINCWS), true);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SSID), ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.VALUE_PREMIUM_LOGIN_SSID));

            executeAsyncTask(callback, url, jsonObject.toString(), requestId);
        } catch (JSONException e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void doPremiumLogout(OnEliteSMPTaskCompleteListner callback, int requestId, final String url, String frameIP, UserRegistrationDetail userDetail) throws Exception {
        EliteSession.eLog.d(MODULE, "doPremiumLogin invoked");
        EliteSession.eLog.d(MODULE, "input requestId=" + requestId + " url=" + url);
        this.callback = callback;

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_USERNAME), userDetail.getUserName());
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_FRAMEDIPADDRESS), frameIP);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SERVICETYPE),
                    ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.VALUE_SERVICE_TYPE));

            jsonObject.put(EliteSMPUtilConstants.KEY_BRAND, userDetail.getBrandName());
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PARTNER), userDetail.getPartnerName());
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_WEBINCWS), true);

            executeAsyncTask(callback, url, jsonObject.toString(), requestId);
        } catch (JSONException e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void doPGLogin(OnEliteSMPTaskCompleteListner callback, int requestId, final String url, final String PhoneNo, String Channel, String IPAddress)//final String url,String userName, String password,String servicetype, String sip)
    {
        EliteSession.eLog.d(MODULE, "doLogin invoked");
        EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);

        final JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PHONE), PhoneNo);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_CHANNEL), Channel);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_IPADDRESS), IPAddress);


            executeAsyncTask(callback, url, jsonObject.toString(), requestId);
        } catch (JSONException e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void doPGLogout(OnEliteSMPTaskCompleteListner callback, int requestId, final String url, final String PhoneNo, String Channel, String IPAddress) {
        EliteSession.eLog.d(MODULE, "doPGLogout invoked");
        EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);
        final JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_PHONE), PhoneNo);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_CHANNEL), Channel);
            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_IPADDRESS), IPAddress);

            executeAsyncTask(callback, url, jsonObject.toString(), requestId);
        } catch (JSONException e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void doSendNotification(OnEliteSMPTaskCompleteListner callback, int requestId, final String url, final String JsonString) {
        EliteSession.eLog.d(MODULE, "doSendNotification invoked");
        EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);

        try {
            executeAsyncTask(callback, url, JsonString, requestId);
        } catch (Exception e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void forgetPassword(OnEliteSMPTaskCompleteListner callback, int requestId, final String url, final String serviceType, final String brand) {
        EliteSession.eLog.d(MODULE, "forgetPassword invoked");
        EliteSession.eLog.i(MODULE, "input requestId=" + requestId + " url=" + url);
        final JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_SERVICETYPE), serviceType);
            jsonObject.put(EliteSMPUtilConstants.KEY_BRAND, brand);
            EliteSession.eLog.d(MODULE, "JSON params set");

            executeAsyncTask(callback, url, jsonObject.toString(), requestId);
        } catch (JSONException e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }

    public void genericAPICall(OnEliteSMPTaskCompleteListner callback, int requestId, final String url, final String jsonParams, final String contentType) {

        EliteSession.eLog.d(MODULE, "genericAPICall invoked");
        this.callback = callback;

        try {
            final EliteConnectCore eliteConnect = new EliteConnectCore(this, requestId);
            new AsyncTask<String, String, String>() {

                @Override
                protected String doInBackground(String... params) {
                    if (jsonParams != null && !jsonParams.isEmpty()) {
                        EliteSession.eLog.d(MODULE, "genericAPICall invoked jsonparam not null");
                        eliteConnect.invokeService(url, jsonParams, contentType);
                    } else {
                        EliteSession.eLog.d(MODULE, "genericAPICall invoked jsonparam null");
                        eliteConnect.invokeService(url, "{}", contentType);
                    }
                    return null;
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }
}
