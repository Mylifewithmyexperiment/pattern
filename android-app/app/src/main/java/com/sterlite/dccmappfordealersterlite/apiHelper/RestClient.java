package com.sterlite.dccmappfordealersterlite.apiHelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;


/**
 * Created by etech7 on 19/6/17.
 */

public class RestClient {

    Call<String> call = null;
    ApiInterface apiInterface = null;


    public void execute(RestRequest restRequest, final RestClientListener listener) {
        final RestResponse res = new RestResponse();
        res.setRequest(restRequest);
        ApiInterface apiInterface = getBody(restRequest.getBaseUrl()).create(ApiInterface.class);





        ApiInterface apiInterfacejson = getResponseUsingGson(restRequest.getBaseUrl()).create(ApiInterface.class);

        if (restRequest.getReqMethod() == RestConst.RequestMethod.METHOD_GET) {

            if (restRequest.getHeader() != null && restRequest.getParams() != null) {

                call = apiInterface.executeGetRequest(restRequest.getAction(), restRequest.getParams(), restRequest.getHeader());

            } else if (restRequest.getHeader() == null && restRequest.getParams() != null) {

                call = apiInterface.executeGetRequest(restRequest.getAction(), restRequest.getParams());
            } else if (restRequest.getParams() == null && restRequest.getHeader() == null) {

                call = apiInterface.executeGetRequest(restRequest.getAction());

            } else if (restRequest.getHeader() != null) {

                call = apiInterface.executeGetRequestWithHeader(restRequest.getAction(), restRequest.getHeader());
            }

        } else if (restRequest.getReqMethod() == RestConst.RequestMethod.METHOD_POST) {

            if (restRequest.getContentType() == RestConst.ContentType.CONTENT_FORMDATA) {

                if (restRequest.getHeader() == null && restRequest.getParams() == null) {

                    call = apiInterface.executePostRequest(restRequest.getAction());

                } else if (restRequest.getHeader() != null && restRequest.getParams() != null) {
                    call = apiInterface.executePostRequest(restRequest.getAction(), restRequest.getParams(), restRequest.getHeader());

                } else if (restRequest.getParams() != null) {

                    call = apiInterface.executePostRequest(restRequest.getAction(), restRequest.getParams());
                } else if (restRequest.getHeader() != null) {

                    call = apiInterface.executePostRequestWithHeader(restRequest.getAction(), restRequest.getHeader());
                }


            } else if (restRequest.getContentType() == RestConst.ContentType.CONTENT_JSON) {
                apiInterface = getBody(restRequest.getBaseUrl()).create(ApiInterface.class);
                Gson gson = new Gson();
                String json = null;

                try {
                    JSONObject jsondata = restRequest.getJsonObject();
                    if (jsondata != null) {
                        //slash sign use for reduce slash in array after and before key and value
                        json = jsondata.toString().replaceAll("\\\\", "");
                        Log.d("json", json.toString() + "");
                    }
                    else  if(restRequest.getJsonArray()!=null)
                    {
                        json= restRequest.getJsonArray().toString().replaceAll("\\\\", "");
                    }
                    else {
                        json = gson.toJson(restRequest.getParams());
                        Log.d("location", json + "");
                        if (json.contains("nameValuePairs")) {

                            Log.d("Restclient ::", " nameValuePairs");
                            JSONObject jsonObject = null;
                            jsonObject = getJsonFromMap(restRequest.getParams());
                            json = jsonObject.toString();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestBody body = RequestBody.create(MediaType.parse("Application/json"), json);
                if (restRequest.getHeader() != null && json != null) {
                    call = apiInterface.executePostRequestWithJsonData(restRequest.getAction(), body, restRequest.getHeader());
                } else if (json != null && restRequest.getHeader() == null) {
                    call = apiInterface.executePostRequestWithJsonData(restRequest.getAction(), json);
                } else if (restRequest.getHeader() != null && json == null) {
                    call = apiInterface.executePostRequestWithJsonData(restRequest.getAction(), restRequest.getHeader());
                }

            } else if (restRequest.getContentType() == RestConst.ContentType.CONTENT_MULTIPART) {

                MultipartBody.Part[] fileToUpload = null;
                File file = null;
                MultipartBody.Part filetoupload = null;

                try {

                    if (restRequest.getMeadiapatharray() != null && restRequest.getMeadiapatharray().size() > 0) {


                        int multipartCount = 0;

                        if (restRequest.getMeadiapatharray() != null && restRequest.getImageKey() != null) {
                            multipartCount = restRequest.getMeadiapatharray().size();
                        }

                        if (restRequest.getMultipartData() != null)
                            multipartCount = multipartCount + restRequest.getMultipartData().size();

                        fileToUpload = new MultipartBody.Part[multipartCount];

                        int i = 0;

                        if (restRequest.getMeadiapatharray() != null && restRequest.getImageKey() != null) {
                            for (; i < restRequest.getMeadiapatharray().size(); i++) {
                                file = new File(restRequest.getMeadiapatharray().get(i));
                                RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                                fileToUpload[i] = MultipartBody.Part.createFormData(restRequest.getImageKey() + "[]", file.getName(), requestBody);
                            }
                        }

//                   /* if (restRequest.getMultipartData() != null) {
//                        Set<String> keyData = restRequest.getMultipartData().keySet();
//                        for (String key : keyData) {
//                            File file = new File(restRequest.getMultipartData().get(key));
//                            RequestBody requestBody = RequestBody.create(MediaType.parse("**/*//*"), file);
//                            fileToUpload[i] = MultipartBody.Part.createFormData(key, file.getName(), requestBody);
//                            i++;
//                        }
//                    }*/
                    } else {
                        //single image
                        String imagekey = restRequest.getImageKey();
                        if (imagekey != null) {
                            file = new File(restRequest.getImagepath());
                            if (file.exists()) {
                                RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
                                filetoupload = MultipartBody.Part.createFormData(restRequest.getImageKey(), file.getName(), requestBody);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Error in Uploading File", e.toString());
                }
                Map<String, String> newMap = null;
                if (restRequest.getParams() != null) {

                    newMap = new HashMap<String, String>();
                    for (Map.Entry<String, Object> entry : restRequest.getParams().entrySet()) {
                        if (entry.getValue() instanceof String) {
                            newMap.put(entry.getKey(), (String) entry.getValue());
                        }
                    }
                }
                if (newMap == null && restRequest.getHeader() == null) {
                    call = apiInterface.exexuteuploadFile(restRequest.getAction(), filetoupload);
                } else if (restRequest.getHeader() != null && newMap == null) {
                    call = apiInterface.exexuteuploadFileforonlyheader(restRequest.getAction(), filetoupload, restRequest.getHeader());
                } else if (restRequest.getHeader() == null && newMap != null) {
                    if (restRequest.getMeadiapatharray() != null && restRequest.getMeadiapatharray().size() > 0) {
                        call = apiInterface.exexuteuploadFiles(restRequest.getAction(), newMap, fileToUpload);
                    } else {
                        call = apiInterface.exexuteuploadFile(restRequest.getAction(), newMap, filetoupload);
                    }
                } else if (restRequest.getHeader() != null && newMap != null) {
                    call = apiInterface.exexuteuploadFile(restRequest.getAction(), newMap, filetoupload, restRequest.getHeader());

                }
            }


        }

        if (call != null) {

            if (AppUtils.isConnectingToInternet()) {
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        int statusCode = response.code();
                        String responsestring = response.body();
                        Log.d("onResponse()", " " + statusCode + " Rest HomeResponse: " + responsestring);
                        res.setResString(responsestring);

                        if (response.raw().body().contentType() != null && response.raw().body().contentType().toString().equalsIgnoreCase("image/*")) {
                            res.setResType(RestConst.ResponseType.RES_TYPE_IMAGE);

                            byte[] bytes = new byte[0];
                            try {
                              //  res.setByteArray(response.body().getBytes());
                                bytes = response.body().getBytes();
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                                //  Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                res.setBitmap(bitmap);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            res.setResType(RestConst.ResponseType.RES_TYPE_JSON);
                        }

                        if (response.isSuccessful()) {
                            res.setResString(responsestring);

                        } else {

                            try {
                                String str = response.errorBody().string();
                                res.setError(str);
                               /* JSONObject resError = new JSONObject(str);
                                res.setError(resError.getString("error"));
                                res.setResString(str);
                                Log.d("RES: ", " " + res.getError());
*/
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        try {
                            Gson gson = new Gson();
                            Type type = new TypeToken<HashMap<String, Object>>() {
                            }.getType();
                            HashMap<String, Object> myMap = gson.fromJson(res.getResString(), type);
                            res.setResObjectMap(myMap);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        listener.onRequestComplete(RestConst.ResponseCode.SUCCESS, res);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        Log.d("Failure", "Call Not Executed" + call.toString());
                        Log.d("Failure", "Call Not Executed" + t.toString());
                        if (t.toString().equalsIgnoreCase("java.net.SocketTimeoutException")) {
                            res.setError("Internal server error");
                        } else {
                            res.setError(t.toString());
                        }
                        if (call.isCanceled()) {
                            listener.onRequestComplete(RestConst.ResponseCode.CANCEL, res);
                        } else {
                            listener.onRequestComplete(RestConst.ResponseCode.ERROR, res);
                        }
                    }
                });
            }

        } else {
            Log.e("Failure", "Call not initialized");
//            res.setError(context.getString(R.string.alert_not_initalize));
            listener.onRequestComplete(RestConst.ResponseCode.ERROR, res);
        }


    }

    public void cancelRequest() {
        call.cancel();
    }

    public Retrofit getBody(String baseurl) {
        Retrofit retrofit = null;
        try {


            OkHttpClient client = getUnsafeOkHttpClient();
//            OkHttpClient client = new OkHttpClient.Builder()
//            connectTimeout(30, TimeUnit.SECONDS)
//            readTimeout(30, TimeUnit.SECONDS).build();


            if (retrofit == null) {
                Retrofit.Builder builder = new Retrofit.Builder();
                builder.baseUrl(baseurl);
                builder.client(client);
                builder.addConverterFactory(ScalarsConverterFactory.create());
                retrofit = builder.build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retrofit;
    }

    public OkHttpClient getUnsafeOkHttpClient()  {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            builder.connectTimeout(30, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS).build();

            OkHttpClient okHttpClient = builder.build();

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Retrofit getResponseUsingGson(String baseurl) {
        Retrofit retrofit = null;
        try {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            if (retrofit == null) {

                Retrofit.Builder builder = new Retrofit.Builder();
                builder.baseUrl(baseurl);
                builder.addConverterFactory(GsonConverterFactory.create(gson));

                retrofit = builder.build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return retrofit;

    }





    public interface ApiInterface {

        @GET
        Call<String> executeGetRequest(@Url String url);


        @GET
        Call<String> executeGetRequest(@Url String url, @QueryMap Map<String, Object> param);

        @GET
        Call<String> executeGetRequestWithHeader(@Url String url, @HeaderMap Map<String, String> headers);


        @GET
        Call<String> executeGetRequest(@Url String url, @QueryMap Map<String, Object> param, @HeaderMap Map<String, String> headers);


        @POST
            // @FormUrlEncoded
        Call<String> executePostRequest(@Url String url);

        @POST
        @FormUrlEncoded
        Call<String> executePostRequest(@Url String url, @FieldMap Map<String, Object> param);

        @POST
        Call<String> executePostRequestWithHeader(@Url String url, @HeaderMap Map<String, String> headers);


        @POST
        @FormUrlEncoded
        Call<String> executePostRequest(@Url String url, @FieldMap Map<String, Object> param, @HeaderMap Map<String, String> headers);


        @POST
        Call<String> executePostRequestWithJsonData(@Url String url, @Body String object);

        @POST
        Call<String> executePostRequestWithJsonData(@Url String url, @Body RequestBody object, @HeaderMap Map<String, String> headers);

        @POST
        Call<String> executePostRequestWithJsonData(@Url String url, @HeaderMap Map<String, String> headers);


        @Multipart
        @POST
        Call<String> exexuteuploadFile(@Url String url, @Part MultipartBody.Part file);

        @Multipart
        @POST
        Call<String> exexuteuploadFile(@Url String url, @PartMap Map<String, String> param, @Part MultipartBody.Part file);

        @Multipart
        @POST
        Call<String> exexuteuploadFile(@Url String url, @PartMap Map<String, String> param, @Part MultipartBody.Part file, @HeaderMap Map<String, String> headers);

        @Multipart
        @POST
        Call<String> exexuteuploadFileforonlyheader(@Url String url, @Part MultipartBody.Part file, @HeaderMap Map<String, String> headers);

        @Multipart
        @POST
        Call<String> exexuteuploadFiles(@Url String url, @PartMap Map<String, String> param, @Part MultipartBody.Part[] files);


    }

    private JSONObject getJsonFromMap(Map<String, Object> map) throws JSONException {
        JSONObject jsonData = new JSONObject();
        for (String key : map.keySet()) {
            Object value = map.get(key);
            if (value instanceof Map<?, ?>) {
                value = getJsonFromMap((Map<String, Object>) value);
            }
            jsonData.put(key, value);
        }
        return jsonData;
    }


}


