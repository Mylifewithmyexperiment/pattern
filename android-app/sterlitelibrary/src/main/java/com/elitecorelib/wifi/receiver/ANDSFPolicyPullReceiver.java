package com.elitecorelib.wifi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.elitecore.wifi.api.CommonWiFiUtility;
import com.elitecore.wifi.listener.OnInternetCheckCompleteListner;
import com.elitecorelib.andsf.pojo.ANDSFDiscoveryInformations;
import com.elitecorelib.andsf.pojo.ANDSFPolicies;
import com.elitecorelib.andsf.pojo.ANDSFPolicyResponse;
import com.elitecorelib.andsf.utility.ANDSFUtility;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.pojo.PojoServerKeyMapping;
import com.elitecorelib.core.realm.RealmOperation;
import com.elitecorelib.core.services.ConnectionManagerCompleteListner;
import com.elitecorelib.core.services.ConnectionManagerListner;
import com.elitecorelib.core.services.ConnectionManagerTaskNew;
import com.elitecorelib.core.services.InterNetAvailabilityCheckTask;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.SharedPreferencesConstant;
import com.elitecorelib.core.utility.SharedPreferencesTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.elitecore.wifi.api.EliteWiFIConstants.RESPONSECODE;
import static com.elitecore.wifi.api.EliteWiFIConstants.RESPONSEDATA;
import static com.elitecore.wifi.api.EliteWiFIConstants.RESPONSEMESSAGE;
import static com.elitecorelib.core.CoreConstants.ANDSF_ACCESS_TOKEN;
import static com.elitecorelib.core.CoreConstants.ANDSF_LASTSYNCTIME;
import static com.elitecorelib.core.CoreConstants.KEY_MCC;
import static com.elitecorelib.core.CoreConstants.KEY_MNC;
import static com.elitecorelib.core.CoreConstants.MONETIZATION_ANDSFGETPOLICY_REQUESTID;
import static com.elitecorelib.core.CoreConstants.MONETIZATION_ANDSFGETTOKEN_REQUESTID;
import static com.elitecorelib.core.CoreConstants.MONETIZATION_GET_DYNAMIC_PARAMETER_REQUESTID;
import static com.elitecorelib.core.CoreConstants.Seprator;
import static com.elitecorelib.core.utility.SharedPreferencesConstant.ANDSF_PULLINTERVAL_VALUE;
import static com.elitecorelib.core.utility.SharedPreferencesConstant.ANDSF_USERPARAMETERINTERVAL_VALUE;
import static com.elitecorelib.core.utility.SharedPreferencesConstant.KEY_CURRENTTRIGGERCOUNT;

public class ANDSFPolicyPullReceiver extends BroadcastReceiver {

    private final String MODULE = "ANDSFPolicyPullReceiver";
    private final SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();
    private Context mContext;
    private ConnectionManagerTaskNew connectionManagerTaskNew;
    private final Gson mGson = new Gson();
    private RealmOperation realmOperation;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;

        int policyPullInterval = Integer.parseInt(ElitelibUtility.getKeyPairvalue(SharedPreferencesConstant.ANDSF_PULLINTERVAL, ANDSF_PULLINTERVAL_VALUE));
        int parameterPullInterval = Integer.parseInt(ElitelibUtility.getKeyPairvalue(SharedPreferencesConstant.ANDSF_USERPARAMETERINTERVAL, ANDSF_USERPARAMETERINTERVAL_VALUE));

        double parameterTrigger = Math.ceil(parameterPullInterval / policyPullInterval);
        int currentTriggerCount = spTask.getInt(KEY_CURRENTTRIGGERCOUNT);

        EliteSession.eLog.i(MODULE, " policyPullInterval - "+policyPullInterval +" , parameterPullInterval - "+parameterPullInterval+
                " , parameterTrigger - "+parameterTrigger+ " , currentTriggerCount - "+currentTriggerCount);

        if (currentTriggerCount >= parameterTrigger) {

            new InterNetAvailabilityCheckTask(new OnInternetCheckCompleteListner() {
                @Override
                public void isInterNetAvailable(int requestId, String status, String json) {
                    if (status.equals(CoreConstants.INTERNET_CHECK_SUCCESS)) {
                        EliteSession.eLog.i(MODULE, "Internet Available, Invoke Dynamic Parameters Process Start");
                        ANDSFUtility.invokeDynamicParameter(connectionManagerListner);
                    } else {
                        EliteSession.eLog.i(MODULE, "Please check Internet Connection");
                    }
                }
            }, CoreConstants.INTERNET_CHECK_URL).execute();
            currentTriggerCount = 1;
        } else {
            currentTriggerCount++;
        }
        spTask.saveInt(KEY_CURRENTTRIGGERCOUNT, currentTriggerCount);


        new InterNetAvailabilityCheckTask(new OnInternetCheckCompleteListner() {
            @Override
            public void isInterNetAvailable(int requestId, String status, String json) {
                if (status.equals(CoreConstants.INTERNET_CHECK_SUCCESS)) {
                    EliteSession.eLog.i(MODULE, "Internet Available, Policy Pull Process Start");
                    callPolicyFromServer();
                } else {
                    EliteSession.eLog.i(MODULE, "Please check Internet Connection");
                }
            }
        }, CoreConstants.INTERNET_CHECK_URL).execute();
    }

    private final ConnectionManagerListner connectionManagerListner = new ConnectionManagerListner() {
        @Override
        public void onConnectionSuccess(String result, int requestID) {

            if (result == null || result.equals("")) {
                EliteSession.eLog.e(MODULE, "Server Not Reachable or Call after some time");
            } else {

                if (requestID == MONETIZATION_GET_DYNAMIC_PARAMETER_REQUESTID) {

                    EliteSession.eLog.i(MODULE, "GetDynamic Parameter response");
                    EliteSession.eLog.i(MODULE, result);
                    try {

                        PojoServerKeyMapping resObj = new Gson().fromJson(result, PojoServerKeyMapping.class);
                        if (resObj != null) {
                            if (resObj.getResponseCode() == 1) {
                                // set values in preferences from getparameter response

                                spTask.saveString(SharedPreferencesConstant.PREV_ANDSF_PULLINTERVAL,
                                        ElitelibUtility.getKeyPairvalue(SharedPreferencesConstant.ANDSF_PULLINTERVAL, "1440"));

                                spTask.saveString(SharedPreferencesConstant.PREV_ANDSF_EVALUATEINTERVAL,
                                        ElitelibUtility.getKeyPairvalue(SharedPreferencesConstant.ANDSF_EVALUATEINTERVAL, "900"));

                                ElitelibUtility.setKeyPairValue(resObj.getResponseData());

                                if (CommonWiFiUtility.checkValidOperator(ElitelibUtility.getKeyPairvalue(KEY_MCC, KEY_MCC), ElitelibUtility.getKeyPairvalue(KEY_MNC, KEY_MNC), Seprator)) {

                                    spTask.saveLong(SharedPreferencesConstant.NEXT_USERPARAMETERINTERVAL, System.currentTimeMillis() +
                                            Integer.parseInt(spTask.getString(SharedPreferencesConstant.ANDSF_USERPARAMETERINTERVAL)) * 60 * 1000);

                                    int pullinterval = Integer.parseInt(ElitelibUtility.getKeyPairvalue(SharedPreferencesConstant.ANDSF_PULLINTERVAL, "1440")); //15 min
                                    int evalutioninterval = Integer.parseInt(ElitelibUtility.getKeyPairvalue(SharedPreferencesConstant.ANDSF_EVALUATEINTERVAL, "900")); //15 min

                                    int prev_pullinterval = Integer.parseInt(spTask.getString(SharedPreferencesConstant.PREV_ANDSF_PULLINTERVAL));
                                    int prev_evalutioninterval = Integer.parseInt(spTask.getString(SharedPreferencesConstant.PREV_ANDSF_EVALUATEINTERVAL));

                                    EliteSession.eLog.i(MODULE, "Current Pull Interval : " + pullinterval +
                                            " Previous pull Interval : " + prev_pullinterval);

                                    EliteSession.eLog.i(MODULE, "Current Evalution Interval : " + evalutioninterval +
                                            " Previous Evalution Interval : " + prev_evalutioninterval);

                                    if (pullinterval != prev_pullinterval) {
                                        ANDSFUtility.updatePolicyPull(mContext, pullinterval);
                                    }
                                    if (evalutioninterval != prev_evalutioninterval) {
                                        ANDSFUtility.updatePolicyyEvaluation(mContext, evalutioninterval);
                                    }

                                } else {
                                    EliteSession.eLog.i(MODULE, "MCC/MNC not Valid, Active Sim Not Valid operator");
                                }

                                spTask.saveBoolean(CoreConstants.DO_REGISTER, true);
                            } else {
                                EliteSession.eLog.d(MODULE, resObj.getResponseMessage());
                            }
                        } else {
                            EliteSession.eLog.e(MODULE, "getDynamicParameter result null");
                        }
                    } catch (JsonParseException e) {
                        EliteSession.eLog.e(MODULE, "getDynamicParameter -" + e.getMessage());
                    } catch (Exception e) {
                        EliteSession.eLog.e(MODULE, "getDynamicParameter -" + e.getMessage());
                    }
                }
            }
        }

        @Override
        public void onConnectionFailure(String message, int resultCode) {
            EliteSession.eLog.i(MODULE, message);
        }
    };

    private void callPolicyFromServer() {
        try {
            EliteSession.eLog.d(MODULE, "***********************ANDSF pull policy Call*****************************");
            EliteSession.eLog.i(MODULE, "invoke Token Service for ANDSF");

            JsonObject mJsonObject = new JsonObject();
            mJsonObject.addProperty(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
            mJsonObject.addProperty(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
            mJsonObject.addProperty(CoreConstants.USERIDENTITY, spTask.getString(CoreConstants.USERIDENTITY));


            if (CommonWiFiUtility.checkValidOperator(ElitelibUtility.getKeyPairvalue(CoreConstants.KEY_MCC, CoreConstants.KEY_MCC)
                    , ElitelibUtility.getKeyPairvalue(CoreConstants.KEY_MNC, CoreConstants.KEY_MNC), CoreConstants.Seprator)) {


                connectionManagerTaskNew = new ConnectionManagerTaskNew(listner, MONETIZATION_ANDSFGETTOKEN_REQUESTID);
                connectionManagerTaskNew.execute(mJsonObject.toString(), spTask.getString(CoreConstants.ANDSF_URL) + CoreConstants.ANDSFAccessToken);

            }

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error when invoke Token for ANDSF - " + e.getMessage());
        }
    }

    private ConnectionManagerCompleteListner listner = new ConnectionManagerCompleteListner() {

        @Override
        public void onConnnectionManagerTaskComplete(String result, int requestId) {

            if (result == null || result.equals("")) {
                EliteSession.eLog.e(MODULE, "Server Not Reachable or Call after some time");
            } else {

                if (requestId == MONETIZATION_ANDSFGETTOKEN_REQUESTID) {
                    EliteSession.eLog.i(MODULE, "Access token response");
                    try {
                        JSONObject object = new JSONObject(result);

                        if (object.getInt(RESPONSECODE) == 1) {
                            String mAccessToken = object.getJSONObject(RESPONSEDATA).getString(ANDSF_ACCESS_TOKEN);
                            EliteSession.eLog.i(MODULE, "ANDSF Access Token is -- " + mAccessToken);

                            JsonObject mJsonObject = new JsonObject();
                            mJsonObject.addProperty(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
                            mJsonObject.addProperty(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
                            mJsonObject.addProperty(CoreConstants.USERIDENTITY, spTask.getString(CoreConstants.USERIDENTITY));
                            mJsonObject.addProperty(ANDSF_ACCESS_TOKEN, mAccessToken);
                            mJsonObject.addProperty(ANDSF_LASTSYNCTIME, 0);

                            connectionManagerTaskNew = new ConnectionManagerTaskNew(listner, MONETIZATION_ANDSFGETPOLICY_REQUESTID);
                            connectionManagerTaskNew.execute(mJsonObject.toString(), spTask.getString(CoreConstants.ANDSF_URL) + CoreConstants.ANDSFGetPolicy);

                        } else {
                            EliteSession.eLog.i(MODULE, "Message - " + object.getString(RESPONSEMESSAGE));
                        }
                    } catch (Exception e) {
                        EliteSession.eLog.e(MODULE, "JsonExeption in ANDSF getToken -" + e.getMessage());
                    }
                } else if (requestId == MONETIZATION_ANDSFGETPOLICY_REQUESTID) {

                    EliteSession.eLog.i(MODULE, "ANDSF policy getting");
                    try {
                        JSONObject object = new JSONObject(result);

                        if (object.getInt(RESPONSECODE) == 1) {
                            JSONObject policyResponse = object.getJSONObject(RESPONSEDATA);
                            ANDSFPolicyResponse mPolicyResponse = mGson.fromJson(policyResponse.toString(), ANDSFPolicyResponse.class);

                            realmOperation = RealmOperation.with(mContext);
                            EliteSession.eLog.d(MODULE, "Delete All Policies and Discovery Information");
                            realmOperation.deleteAll(ANDSFDiscoveryInformations.class);
                            realmOperation.deleteAll(ANDSFPolicies.class);

                            EliteSession.eLog.d(MODULE, "Instert All Policy");
                            ArrayList<String> currentPLMN = ElitelibUtility.getMccMncFromActiveNetworkSim();
                            String devicePLMN = currentPLMN.get(0) + currentPLMN.get(1);
                            EliteSession.eLog.d(MODULE, "Device Active Sim Network PLMN - " + devicePLMN);

                            int policyCount = 0;
                            for (ANDSFPolicies policies : mPolicyResponse.policies) {
                                if (policies.enable && policies.getPLMN().equals(devicePLMN)) {
                                    EliteSession.eLog.i(MODULE, "Policy for insert -" + policies.policyName);
                                    realmOperation.insertData(policies);
                                    policyCount++;
                                }
                            }

                            if (policyCount > 0) {
                                EliteSession.eLog.d(MODULE, "Insert All Discovery information = " + mPolicyResponse.discoveryInformations.size());
                                for (ANDSFDiscoveryInformations informations : mPolicyResponse.discoveryInformations) {
                                    realmOperation.insertData(informations);
                                }
                            } else {
                                EliteSession.eLog.d(MODULE, "Any Policy not Enable or Not any PLMN (" + devicePLMN + ") Valid");
                            }
                        } else {
                            EliteSession.eLog.i(MODULE, "Not Getting Policy, Message - " + object.getString(RESPONSEMESSAGE));
                        }
                    } catch (Exception e) {
                        EliteSession.eLog.e(MODULE, "JsonExeption in ANDSF getPolicy -" + e.getMessage());
                    }
                }
            }
        }
    };
}
