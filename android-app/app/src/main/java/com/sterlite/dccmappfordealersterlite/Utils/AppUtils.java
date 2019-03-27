package com.sterlite.dccmappfordealersterlite.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.utility.SharedPreferencesTask;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.apiHelper.RestResponse;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.model.Address;

import static com.elitecorelib.core.CoreConstants.DEFAULT_LOCATION;
import static com.elitecorelib.core.utility.SharedPreferencesConstant.ADVLOCATION_PARAM;
import static com.elitecorelib.core.utility.SharedPreferencesConstant.ADVLOCATION_PARAM_LOCATION;
import static com.sterlite.dccmappfordealersterlite.Utils.AdvertiesConstants.GETADV_LOCATIONKEY_VALUE;


public class AppUtils {
    public static final String TAG = "AppUtils";
    public static final String MODULE = "AppUtils";

    public static String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    public static String[] PERMISSION_STORAGE = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE

    };

    public static String[] PERMISSION_LOCATION = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    public static String[] WRITE_SD_CARD_PERMISSION = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static String[] PHONE_CALL_PERMISSION = new String[]{
            Manifest.permission.CALL_PHONE
    };


    public static String getDefaultLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static void checkOrCreateAppDirectories(Context context) {
        // copy db
        Log.e(AppUtils.class + "", " : checkOrCreateAppDirectories()");
        Storage.verifyDirPath(Constants.APP_HOME);
        Storage.verifyDirPath(Constants.DIR_MEDIA);
    }

    /**
     * True if all permission are granted.
     *
     * @param context
     * @param permission
     * @param requestCode
     * @return
     */
    public static boolean AskAndCheckPermission(Activity context, String permission[], int requestCode) {
        boolean flag = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkFirstTimePermission(permission)) {
                context.requestPermissions(permission, requestCode);
                for (String s : permission) {
                    DCCMDealerSterlite.getDataManager().setBooleanAppPref(s, true);
                }
            } else {
                if (isPermissionGranted(context, permission))
                    return true;
                for (String per : permission) {
                    if (context.checkSelfPermission(per) != PackageManager.PERMISSION_GRANTED && !context.shouldShowRequestPermissionRationale(per)) {
                        flag = true;
                        break;
                    } else {
                        flag = false;
                    }
                }

                if (flag) {
                    flag = false;
//                    CustomAlertDialog.showPermissionAlert(context, context.getResources().getString(R.string.msg_alert_you_have_permission));
                } else {
                    context.requestPermissions(permission, requestCode);
                }
            }
        } else {
            return true;
        }
        return flag;
    }

    private static boolean checkFirstTimePermission(String permission[]) {
        for (String s : permission) {
            if (!DCCMDealerSterlite.getDataManager().getBooleanAppPref(s, false)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPermissionGranted(Activity context, String permission[]) {
        boolean flag = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String per : permission) {
                if (context.checkSelfPermission(per) == PackageManager.PERMISSION_GRANTED) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        } else {
            flag = true;
        }
        return flag;
    }


    public static boolean hasSelfPermission(Activity activity, String[] permissions) {

        // Verify that all the permissions.
        for (String permission : permissions) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isConnectingToInternet() {
        ConnectivityManager cm = (ConnectivityManager) DCCMDealerSterlite.appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static boolean isUserRegistered() throws Exception {
//        todo implement method
        return DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.PREF_IS_USER_LOGGED_IN, false);

//        if (Constants.IS_DUMMY_MODE) {
//        } else {
//            throw new Exception("method implemetation pending");
//        }
    }

    public static void setUserRegistered(boolean var) throws Exception {
        DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.PREF_IS_USER_LOGGED_IN, var);

    }

    public static void disableActivityTouch(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void enableActivityTouch(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void errorLog(String simpleName, Object s) {
        try {
            String subString = simpleName.length() > 20 ? simpleName.substring(0, 20) : simpleName;
            Log.e(subString, s + " ");
        } catch (Exception e) {
            Log.e("AppUtils ", " errorLog : " + e);
        }
    }

    public static boolean validateMobileNoLength(int length) {
        return length < 8
                || length > 13;
    }


    public interface Callback {
        void onSuccess();

        void onError();
    }

    public static void setImageUrl(Context context, String path, final ImageView imageView, final ProgressBar progressBar) {
        setImageUrl(context, path, imageView, progressBar, false);
    }

    public static void setImageUrl(Context context, String path, final ImageView imageView, final ProgressBar progressBar, boolean isLocal) {
        setImageUrl(context, path, imageView, progressBar, isLocal, null, false);
    }

    public static void setImageUrl(Context context, String path, final ImageView imageView, final ProgressBar progressBar, final Callback callback) {
        setImageUrl(context, path, imageView, progressBar, false, callback, false);
    }

    public static void setImageUrl(Context context, String path, final ImageView imageView, final ProgressBar progressBar, boolean isLocal, final Callback callback, boolean isSkipCacheCheck) {
        if (!TextUtils.isEmpty(path)) {
            if (isLocal) {
//                Glide.with(context)
//                        .load(new File(path))
//                        .apply(new RequestOptions().placeholder(R.mipmap.default_media_image_gallery).error(R.mipmap.default_media_image_gallery))
//                        .listener(new RequestListener<Drawable>() {
//                            @Override
//                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                                if (progressBar != null)
//                                    progressBar.setVisibility(View.GONE);
////                    imageView.setImageResource(R.drawable.no_category_available);
//                                if (callback != null)
//                                    callback.onError();
//                                return false;
//                            }
//
//                            @Override
//                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                                if (progressBar != null)
//                                    progressBar.setVisibility(View.GONE);
//                                if (callback != null)
//                                    callback.onSuccess();
//                                return false;
//                            }
//                        }).into(imageView);
            } else {
                RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_image_place_holder).error(R.drawable.ic_image_place_holder);
                if (isSkipCacheCheck && path != null) {
                    requestOptions.skipMemoryCache(true);
                    requestOptions.signature(new ObjectKey(String.valueOf(path)));
                    requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
                }
                Glide.with(context)
                        .load(path)
                        .apply(requestOptions)

                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                if (progressBar != null)
                                    progressBar.setVisibility(View.GONE);
//                    imageView.setImageResource(R.drawable.no_category_available);
                                if (callback != null)
                                    callback.onError();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                if (progressBar != null)
                                    progressBar.setVisibility(View.GONE);
                                if (callback != null)
                                    callback.onSuccess();
                                return false;
                            }
                        })
                        .into(imageView);
            }
        } else {
            if (progressBar != null)
                progressBar.setVisibility(View.GONE);
//            imageView.setImageResource(R.mipmap.default_media_image_gallery);
            if (callback != null)
                callback.onError();
        }

    }

    public static void setMarkerImageUrl(Context context, String path, final ImageView imageView, final Callback callback) {
        if (!TextUtils.isEmpty(path)) {
//            int size = (int) context.getResources().getDimension(R.dimen._40sdp);
//            Glide.with(context)
//                    .load(path)
//                    .apply(new RequestOptions().placeholder(R.mipmap.user_default_icon_round).error(R.mipmap.user_default_icon_round).override(size, size))
//                    .listener(new RequestListener<Drawable>() {
//                        @Override
//                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            if (callback != null)
//                                callback.onError();
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                            imageView.setImageDrawable(resource);
//                            if (callback != null)
//                                callback.onSuccess();
//                            return true;
//                        }
//                    })
//                    .into(imageView);

        } else {
//            imageView.setImageResource(R.mipmap.user_default_icon_round);
            if (callback != null)
                callback.onError();
        }

    }


    /**
     * View Related Utils Methods
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no baseView has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    public static void showUnderDevelopmentToast(Context context) {
        showToast(context, context.getString(R.string.msg_under_developement));
    }


    public static void setVisibility(View view, int visibility) {
        if (view == null) return;
        if (view.getVisibility() != visibility)
            view.setVisibility(visibility);
    }


    //Temp methods End


    public static void addTextChangedListener(EditText e, final TextInputLayout t) {
        e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    if (!TextUtils.isEmpty(t.getError())) {
                        t.setErrorEnabled(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static void addTextFocusChangeListener(final EditText e, final NestedScrollView t) {
        addTextFocusChangeListener(e, t, null);
    }

    public static void addTextFocusChangeListener(final EditText e, final NestedScrollView t, final AppBarLayout appBarLayout) {
        e.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (appBarLayout != null) {
                        appBarLayout.setExpanded(false);
                    }
//                    t.smoothScrollTo(0, (int) e.getY());
                }
            }
        });
    }

    public static ProgressDialog initializeProgressDialog(Context context) {
        ProgressDialog progressDialog = null;

        progressDialog = new ProgressDialog(context);

        progressDialog.setMessage(context.getResources().getString(R.string.msg_progress_dialog));
        progressDialog.setCancelable(false);
        return progressDialog;

    }

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    public static boolean isValidateString(String str) {
        boolean flag = false;
        try {
            if (str != null && !str.isEmpty() && !str.equalsIgnoreCase("null")) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return flag;
    }

    public static void setAddressToTextView(Context context, Address address, TextView textView) {
        String colon = ",";
        String newline = "\n";
        String space = " ";
        String s_name = address.getFname() + space + address.getLname();
        String s_mobile_no = context.getString(R.string.lbl_mob_no) + space + address.getMobileNo();
        String s_street = address.getAddressLine1();
        String s_street2 = address.getAddressLine2();
        String s_city = address.getCity();
        String s_state = address.getState();
        String s_country = address.getCountry();
        String s_pincode = address.getPin();
        String shipping_address =
                (!TextUtils.isEmpty(s_street) ? s_street + colon + newline : "") +
                        (!TextUtils.isEmpty(s_street2) ? s_street2 + colon + newline : "") +
                        (!TextUtils.isEmpty(s_city) ? s_city + colon + newline : "") +
                        (!TextUtils.isEmpty(s_state) ? s_state + colon + newline : "") +
                        (!TextUtils.isEmpty(s_country) ? s_country + colon + newline : "") +
                        (!TextUtils.isEmpty(s_pincode) ? s_pincode + colon + newline : "") +
                        (!TextUtils.isEmpty(s_mobile_no) ? s_mobile_no + colon + " " : "-");
        textView.setText(shipping_address);

    }

    public static void setAddressToTextViewSmall(Context context, Address address, TextView textView) {
        String colon = ",";
        String newline = "\n";
        String space = " ";
        String s_name = address.getFname() + space + address.getLname();
        String s_mobile_no = context.getString(R.string.lbl_mob_no) + space + address.getMobileNo();
        String s_street = address.getAddressLine1();
        String s_street2 = address.getAddressLine2();
        String s_city = address.getCity();
        String s_state = address.getState();
        String s_country = address.getCountry();
        String s_pincode = address.getPin();
        String shipping_address =
                (!TextUtils.isEmpty(s_street) ? s_street + colon : "") +
                        (!TextUtils.isEmpty(s_street2) ? s_street2 + colon + newline : "") +
                        (!TextUtils.isEmpty(s_city) ? s_city + colon : "") +
                        (!TextUtils.isEmpty(s_state) ? s_state + colon : "") +
                        (!TextUtils.isEmpty(s_country) ? s_country + colon : "") +
                        (!TextUtils.isEmpty(s_pincode) ? s_pincode + colon + newline : "") +
                        (!TextUtils.isEmpty(s_mobile_no) ? s_mobile_no + colon + " " : "-");
        textView.setText(shipping_address);

    }

    public static String getPriceWithSymbol(Context context, String price) {
        return Constants.CURRANCY_SYMBOL + " " + price.trim();
    }

    public static String getPriceWithSymbol(String price) {
        return Constants.CURRANCY_SYMBOL + " " + price.trim();
    }

    public static JSONObject checkResponse(int code, String message, RestResponse restResponse) {
        if (code == Constants.SUCCESS_CODE) {
            String flag = null;
            try {
                JSONObject res = null;
                try {
                    res = new JSONObject(restResponse.getResString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               /* if (res != null) {
                    Log.d("response ", res.toString());
                    flag = res.getString(RES_CODE_KEY);
                    String msg = res.getString(RES_MSG_KEY);
                    if (flag.equalsIgnoreCase("1")) {
                    } else {
                        res = null;
                    }
                }*/
                return res;

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (code == Constants.FAIL_CODE) {

        } else {

        }
        return null;
    }




    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static void someThingWentWrong(Context context) {
        showToast(context, context.getResources().getString(R.string.msg_somtehing_went_wrong));
    }

    public static <T> String getStringFromObj(T obj) {
        Gson gson = new Gson();
        String myJson = gson.toJson(obj);
        return myJson;
    }

    public static <T> T getObjFromString(String st, Class<T> c) {
        Gson gson = new Gson();
        T obj = gson.fromJson(st, c);
        return obj;
    }

    public static void sendAnalytics(Activity context, String screenName) {
        DCCMDealerSterlite application = (DCCMDealerSterlite) context.getApplication();
        Tracker mTracker = application.getDefaultTracker();
        Log.d(TAG, "Screen name: " + screenName);
        mTracker.setScreenName(screenName);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
//        mTracker.send(new HitBuilders.EventBuilder().build());
//                .setCategory("Action")
//                .setAction("View")
//                .setLabel(screenName)
//                .setValue(1)
//                .build());
    }

    public static void setLocale(Context context, String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        conf.setLayoutDirection(myLocale);
        res.updateConfiguration(conf, dm);
        DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.LOCALE, lang);
    }

    public static String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = DCCMDealerSterlite.appContext.getAssets().open(filename);
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

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static void removePrefrences() {
        try {
            String PREF_SUBSCRIPTION_NUMBER = null;
            String CUSTOMER_UID = null;
            String CART_ID = null;
            String EMAIL_ID = null;
            String CUSTOMER_NAME = null;
            String MOBILE_NO = null;
            String PREF_DN_NUMBER = null;
            String PARTNER_ID = null;
            String PARTNER_NAME = null;

            Boolean PARTNER=false;

            boolean isReg = false;
            if (AppUtils.isUserRegistered()) {
                PREF_SUBSCRIPTION_NUMBER = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_SUBSCRIPTION_NUMBER, null);
                CUSTOMER_UID = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID, null);
                CART_ID = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CART_ID, null);
                EMAIL_ID = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.EMAIL_ID, null);
                CUSTOMER_NAME = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_NAME, null);
                MOBILE_NO = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.MOBILE_NO, null);
                PREF_DN_NUMBER = DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PREF_DN_NUMBER, null);
                PARTNER_ID=DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PARTNER_UID, null);
                PARTNER=DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.PARTNER_UID, false);
                PARTNER_NAME=DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.PARTNER_NAME, null);
                isReg = true;
            }
            DCCMDealerSterlite.getDataManager().clear();

            if (isReg) {
                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.PREF_SUBSCRIPTION_NUMBER, PREF_SUBSCRIPTION_NUMBER);
                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CART_ID, CART_ID);
                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.EMAIL_ID, EMAIL_ID);
                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.MOBILE_NO, MOBILE_NO);
                DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.PREF_DN_NUMBER, PREF_DN_NUMBER);
                if (PARTNER_ID!=null) {
                    DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.PARTNER_UID, PARTNER_ID);
                    DCCMDealerSterlite.getDataManager().setBoolean(AppPreferencesHelper.PARTNER, PARTNER);
                    DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.PARTNER_NAME, PARTNER_NAME);
                    DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CUSTOMER_NAME, PARTNER_NAME);
                    DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CUSTOMER_UID, PARTNER_ID);
                }
                else {
                    DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CUSTOMER_UID, CUSTOMER_UID);
                    DCCMDealerSterlite.getDataManager().set(AppPreferencesHelper.CUSTOMER_NAME,  CUSTOMER_NAME);
                }
                AppUtils.setUserRegistered(true);
            }
        } catch (Exception e) {
            Log.e("removePrefrences", " " + e);
        }
    }
        public static void showAdd(final Activity activity, final View.OnClickListener onclick, final String screenName) {
//        SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();
//        WebView wv_advertiseMent;
////        if (spTask.getInt(CoreConstants.ADENABLE) == 1) {
//        //Advertisement settings
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        final int height = displayMetrics.heightPixels;
//        final int width = displayMetrics.widthPixels;
//
//        int mBannerHeight = (height * BANNER_SIZE) / STANDARD_SIZE;
//        try {
//            EliteSession.eLog.i(MODULE, "getting advertisement if available");
//
//            if (LibraryApplication.getLibraryApplication().getAdMappingHashMap() != null && LibraryApplication.getLibraryApplication().getAdMappingHashMap().size() > 0) {
//                if (LibraryApplication.getLibraryApplication().getAdMappingHashMap().containsKey(screenName + "_" + SCREEN_FOOTER) &&
//                        LibraryApplication.getLibraryApplication().getAdMappingHashMap().get(screenName + "_" + SCREEN_FOOTER).getScreenLocation().equals(CoreConstants.ADLOCATIONFOOTER)) {
//                    wv_advertiseMent = (WebView) activity.findViewById(R.id.wv_advertiseMent_footer);
//                    if (wv_advertiseMent != null) {
////                            wv_advertiseMent.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//                        wv_advertiseMent.setVisibility(View.VISIBLE);
////                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
////                                    RelativeLayout.LayoutParams.MATCH_PARENT,
////                                    mBannerHeight);
////                            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
////                            wv_advertiseMent.setLayoutParams(params);
//
//
//                        ElitelibUtility.displayAdvertisement(wv_advertiseMent, screenName + "|_" + SCREEN_FOOTER, ElitelibUtility.getDecodedInvocationCode(screenName + "_" + SCREEN_FOOTER), activity, onclick);
//                    } else {
//                        EliteSession.eLog.d(MODULE, "footer component not declared in layout");
//                    }
//                }
//                if (LibraryApplication.getLibraryApplication().getAdMappingHashMap().containsKey(screenName + "_" + SCREEN_HEADER) && LibraryApplication.getLibraryApplication().getAdMappingHashMap().get(screenName + "_" + SCREEN_HEADER).getScreenLocation().equals(CoreConstants.ADLOCATIONHEADER)) {
//                    wv_advertiseMent = (WebView) activity.findViewById(R.id.wv_advertiseMent_header);
//                    if (wv_advertiseMent != null) {
//                        wv_advertiseMent.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//                        wv_advertiseMent.setVisibility(View.VISIBLE);
//                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                                RelativeLayout.LayoutParams.MATCH_PARENT,
//                                mBannerHeight);
//                        wv_advertiseMent.setLayoutParams(params);
//                        ElitelibUtility.displayAdvertisement(wv_advertiseMent, screenName + "|_" + SCREEN_HEADER, ElitelibUtility.getDecodedInvocationCode(screenName + "_" + SCREEN_HEADER), activity, onclick);
//                    } else {
//                        EliteSession.eLog.d(MODULE, "header component not declared in layout");
//                    }
//                }
//                if (LibraryApplication.getLibraryApplication().getAdMappingHashMap().containsKey(screenName + "_" + SCREEN_FULLSCREEN) && LibraryApplication.getLibraryApplication().getAdMappingHashMap().get(screenName + "_" + SCREEN_FULLSCREEN).getScreenLocation().equals(CoreConstants.ADLOCATIONFULLSCREEN)) {
//
//                    if (ElitelibUtility.isAlreadyConnected(getKeyPairvalue(KEY_OPENSSID, OPENSSID_VALUE)) ||
//                            ElitelibUtility.isAlreadyConnected(getKeyPairvalue(KEY_BROADBANDSSID, BROADBANDSSID_VALUE))) {
//                        try {
//                            String mInvocationCode = ElitelibUtility.getDecodedInvocationCode(screenName + "_" + CoreConstants.SCREEN_FULLSCREEN);
//                            if (mInvocationCode != null && !mInvocationCode.equals("")) {
//                                List<String> myInvocationList = new ArrayList<String>(Arrays.asList(mInvocationCode.split("@@")));
//
//                                boolean isVideoFlag = false;
//                                String mStringAllowCancle = "", mStringApiURL = "";
//                                if (myInvocationList != null && myInvocationList.size() > 0) {
//                                    for (int i = 0; i < myInvocationList.size(); i++) {
//                                        if (myInvocationList.get(i).toString().equalsIgnoreCase("video")) {
//                                            isVideoFlag = true;
//                                        } else if (myInvocationList.get(i).toString().equalsIgnoreCase("allowcancle=true")) {
//                                            mStringAllowCancle = "true";
//                                        } else if (myInvocationList.get(i).toString().equalsIgnoreCase("allowcancle=false")) {
//                                            mStringAllowCancle = "false";
//                                        } else {
//                                            mStringApiURL = myInvocationList.get(i).toString();
//                                        }
//                                    }
//                                }
//
//                                if (isVideoFlag == true) {
//                                    String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?";
//                                    Pattern p = Pattern.compile(URL_REGEX);
//                                    Matcher m = p.matcher(mStringApiURL);
//                                    if (m.find()) {
//
//                                        final Intent intent = new Intent(activity, AdvertisementAcitivity.class);
//                                        intent.putExtra("screen_name", screenName);
//                                        intent.putExtra("closed_button", mStringAllowCancle);
//                                        intent.putExtra("api_url", mStringApiURL);
//
//                                        Handler handler = new Handler();
//                                        handler.postDelayed(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                activity.startActivityForResult(intent, 1020);
//                                            }
//                                        }, 1000);
//                                        EliteSession.eLog.d(MODULE, "Url available in invocation code");
//
//                                    } else {
//                                        EliteSession.eLog.d(MODULE, "Url not available from invocation code");
//                                    }
//                                } else {
//                                    EliteSession.eLog.d(MODULE, "video content not available");
//                                    AlertDialog.Builder alert = new AlertDialog.Builder(activity);
//                                    WebView wv = new WebView(activity);
//                                    wv.setLayoutParams(new LinearLayout.LayoutParams(width, height));
//                                    wv.getSettings().setJavaScriptEnabled(true);
//                                    wv.loadDataWithBaseURL(null, ElitelibUtility.getDecodedInvocationCode(screenName + "_" + SCREEN_FULLSCREEN), "text/html", "utf-8", null);
//                                    wv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//                                    wv.setWebViewClient(new WebViewClient() {
//                                        @Override
//                                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                                            view.loadUrl(url);
//                                            return true;
//                                        }
//                                    });
//                                    alert.setView(wv);
//                                    alert.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            dialog.dismiss();
//                                        }
//                                    });
//                                    alert.show();
//                                }
//
//                            }
//                        } catch (Exception e) {
//                            EliteSession.eLog.e(MODULE, "Error " + e.getMessage());
//                        }
//                    }
//
//                }
//            } else {
//                EliteSession.eLog.d(MODULE, "Adv not available in database");
//            }
//        } catch (Exception e) {
//            // TODO: handle exception
//            EliteSession.eLog.e(MODULE, " Error in whilte getting advertisement " + e.getMessage());
//        }
////        } else {
////            EliteSession.eLog.d(MODULE, "Advertisement Disable while loading ads for " + screenName);
////        }


    }



    public static void advLocationSet(Map<String, String> responseMap) {

        SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();
        if (responseMap.containsKey(GETADV_LOCATIONKEY_VALUE)) {
            EliteSession.eLog.d(MODULE, "Advertisement Location Key available in response");

            String advLoc = responseMap.get(GETADV_LOCATIONKEY_VALUE);

            EliteSession.eLog.d(MODULE, "AdvLocation value is - " + advLoc);

            if (advLoc != null && !advLoc.equalsIgnoreCase("null") && advLoc.length() > 0) {

                if (advLoc.equalsIgnoreCase(GETADV_LOCATIONKEY_VALUE)) {
                    EliteSession.eLog.d(MODULE, "AdvLocation key and value same, pass default in Location");
                    spTask.saveString(ADVLOCATION_PARAM, DEFAULT_LOCATION);
                } else if (advLoc.contains("|")) {
                    EliteSession.eLog.d(MODULE, "AdvLocation contain | , choose second value");
                    String advsecLoc = advLoc.split(Pattern.quote("|"))[1];
                    EliteSession.eLog.d(MODULE, "AdvLocation - " + advsecLoc);
                    spTask.saveString(ADVLOCATION_PARAM, advsecLoc);
                    spTask.saveString(ADVLOCATION_PARAM_LOCATION, advsecLoc);

                } else {
                    EliteSession.eLog.d(MODULE, "Pass Location Value");
                    spTask.saveString(ADVLOCATION_PARAM, advLoc);
                    spTask.saveString(ADVLOCATION_PARAM_LOCATION, advLoc);
                }
            } else {
                EliteSession.eLog.d(MODULE, "AdvLocation null in response, Pass default value in Location");
                spTask.saveString(ADVLOCATION_PARAM, DEFAULT_LOCATION);
            }
        } else {
            EliteSession.eLog.d(MODULE, "AdvLocation Key Not available in response, Pass default value in Location");
            spTask.saveString(ADVLOCATION_PARAM, DEFAULT_LOCATION);
        }
    }

    public static String getDeviceResolution() {
        int density = LibraryApplication.getLibraryApplication().getLibraryContext().getResources().getDisplayMetrics().densityDpi;
        switch (density) {
            case DisplayMetrics.DENSITY_MEDIUM:
//                return "MDPI";
            case DisplayMetrics.DENSITY_HIGH:
            case DisplayMetrics.DENSITY_280:
            case DisplayMetrics.DENSITY_360:
                return "HDPI";
            case DisplayMetrics.DENSITY_LOW:
                return "LDPI";
            case DisplayMetrics.DENSITY_XHIGH:
            case DisplayMetrics.DENSITY_400:
                return "XHDPI";
            case DisplayMetrics.DENSITY_TV:
                return "TV";
            case DisplayMetrics.DENSITY_XXHIGH:
            case DisplayMetrics.DENSITY_420:
                return "XXHDPI";
            case DisplayMetrics.DENSITY_560:
            case DisplayMetrics.DENSITY_XXXHIGH:
                return "XXXHDPI";
            default:
                return "OTHER";
        }
    }

}
