package com.sterlite.dccmappfordealersterlite.data.network;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.model.dto.auth.AuthResponseDTO;

public class AppApiHelper2 implements ApiHelper2 {


//    public static final String BASE_URL = "http://api.demo.missing1.org/";/*local*/
//    private static final String BASE_URL_ADDITITON = "api/";/*local*/


//    public static final String BASE_URL = "http://192.168.1.100/missing1/";
//    private static final String BASE_URL_ADDITITON = "api/";


//    public static final String BASE_URL = "https://10.121.25.109:9002/telcocommercewebservices/v2/b2ctelco";/*live*/
//    private static final String BASE_URL_ADDITITON = "api/";/*live*/
//    private static final String API_LOGIN_URL = BASE_URL_ADDITITON + "customer/registerCustomer";
    public static String dccm_security= DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.DCCM_SECURITY, Constants.DCCM_SECURITY);
    public static String dccm_ip=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.DCCM_IP, Constants.DCCM_IP);
    public static String dccm_port=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.DCCM_PORT, Constants.DCCM_PORT);
    public static String BASE_URL = dccm_security+dccm_ip+":"+dccm_port+"/telcocommercewebservices/v2/b2ctelco";

  //  public static final String BASE_URL = "https://"+ Constants.IP+":9002/telcocommercewebservices/v2/b2ctelco";/*live*/
    public static String bss_security= DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.BSS_ADAPTER_SECURITY, Constants.BSS_SECURITY);
    public static String bss_ip=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.BSS_ADAPTER_IP, Constants.BSS_Adapter_IP);
    public static String bss_port=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.BSS_ADAPTER_PORT, Constants.BSS_Adapter_PORT);
    public static String BSS_ADAPTER_BASE_URL = bss_security+bss_ip+":"+bss_port+"/bssadapter/services";

  //  public static final String BSS_ADAPTER_BASE_URL = "http://"+ Constants.BSS_Adapter_IP+":18080/bssadapter/services";/*live*/
   // public static final String AUTH_URL = "https://"+ Constants.IP+":9002/authorizationserver";
  public static final String AUTH_URL = dccm_security+ dccm_ip+":"+dccm_port+"/authorizationserver";


    public AppApiHelper2() {

    }
    public static DCCMWebServices getWebServices() {
        return getWebServices(BASE_URL);
    }
    public static DCCMWebServices getWebServices(final String url) {
        OkHttpClient okHttpClient = getUnsafeOkHttpClient();
        okHttpClient.setConnectTimeout(Constants.HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.setWriteTimeout(Constants.HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(Constants.HTTP_TIMEOUT, TimeUnit.MILLISECONDS);

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(url)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(new GsonBuilder().create()))
                .setClient(new OkClient(okHttpClient));

        RestAdapter adapter = builder.build();
        return adapter.create(DCCMWebServices.class);
    }
    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }};

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setSslSocketFactory(sslSocketFactory);
            okHttpClient.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void getAuthToken(final OnApiCallback callback) {

        String client_id="authbridge";
        String client_secret="secret";
        String grant_type="client_credentials";
        String b="bhoomi";
        AppApiHelper2.getWebServices(AppApiHelper2.AUTH_URL).getAuthToken(client_id,client_secret,grant_type,b,new Callback<AuthResponseDTO>() {
            @Override
            public void success(AuthResponseDTO authResponseDTO, Response response) {


                Log.e(" In Success Response : ", " From DCCM :: " + authResponseDTO.getAccess_token());

                Log.e("Token : ", authResponseDTO.getAccess_token());
                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.AUTH__TOKEN.toString(), authResponseDTO.getAccess_token());

                if(callback != null)
                    callback.onSuccess(response);
            }

            @Override
            public void failure(RetrofitError error) {
                int e = Log.e("getAuthToken :", "RetrofitError" + error);

                if(callback != null)
                    callback.onFaild(null);
            }
        });
    }
//
//    @Override
//    public void doSignUp(final String name, final String email, final String mobile,final String city ,final OnApiCallback<User> onApiCallback){
////        RestClient restHelper = new RestClient();
////
////        HashMap<String, Object> params = new HashMap<>();
////        params.put("mobile", mobile);
////        params.put("password", name);
////
////        restHelper.sendRequest(API_LOGIN_URL, API_LOGIN_URL, params, new RestClient.RestHelperCallback() {
////            @Override
////            public void onRequestCallback(int code, String message, RestResponse restResponse) {
////
////                final JSONObject jsonObject = AppUtils.checkResponse(code, message, restResponse);
////                if (jsonObject != null) {
////                    try {
////
////                        JSONObject jsonObj = jsonObject.getJSONObject(Constants.RES_OBJ_KEY);
////                        ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
////                        User user = mapper.readValue(jsonObj.toString(), User.class);
//////                        user.setCountryCode(user.getNumber().substring(0,user.getNumber().indexOf(" ")));
//////                        user.setCountryCode2(user.getNumber2().substring(0,user.getNumber2().indexOf(" ")));
////                        user.setPassword(password);
////                        onApiCallback.onSuccess(user);
////
////                    } catch (Exception e) {
////                        onApiCallback.onFaild(code, message);
////                        e.printStackTrace();
////                    }
////                } else {
////                    onApiCallback.onFaild(code, message);
////                }
////            }
////        });
//
//
//    }

}

