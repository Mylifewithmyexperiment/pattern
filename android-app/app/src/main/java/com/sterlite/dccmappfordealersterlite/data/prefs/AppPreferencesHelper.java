
package com.sterlite.dccmappfordealersterlite.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class AppPreferencesHelper implements PreferencesHelper {

    public static final String PREF_SUBSCRIPTION_NUMBER = "subscriptionNumber";
    public static final String PREF_IS_USER_LOGGED_IN = "is_user_registered";
    public static final String PREF_IS_FIREBASE_TOKEN_CHANGED = "is_firebase_token_changed";

    //    public static final String PREF_USER_ID = "user_id";
    //    public static final String PREF_SUBSCRIBER_IDENTIFIER="subscriberIdentifier";
    public static final String PREF_IS_HOME_DELIVERY = "is_home_delivery";
    public static final String INVENTORY_DATA = "inventory_data";

    public static final String CUSTOMER_UID = "customerUID";
    public static final String P_CUSTOMER_EMAIL = "p_customerEMAIL";
    public static final String P_CUSTOMER_MOBILE = "p_customerMobile";
    public static final String P_CUSTOMER_NAME = "p_customerName";


    public static final String EMAIL_ID = "emailId";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String PARTNER_NAME = "partner_name";

    public static final String MOBILE_NO = "mobileNo";
    public static final String PREF_CART_IDS = "cartIds";
    public static final String PARTNER = "partner";


    public static final String CART_ID = "cartId";
    //    public static final String INVENTORY_NO = "inventory_no";
//    public static final String PRODUCT_CODE = "product_code";
//    public static final String INVENTORY_TYPE = "inventory_type";
//    public static final String PRODUCT_NAME = "product_name";
//    public static final String PRODUCT_PRICE = "product_price";
//    public static final String PRODUCT_BENEFITS2 = "product_banefits2";
//    public static final String PRODUCT_BENEFITS3 = "product_banefits3";
//    public static final String PRODUCT_BENEFITS1 = "product_banefits1";
//    public static final String INVENTORY_PRICE = "inventory_price";
//    public static final String PRODUCT_FREE_BENEFITS = "inventory_free_banefits";
    public static final String AUTH__TOKEN = "auth_token";
    //    public static final String MMP_PLAN_DETAIL = "mmp_plan_details";
    public static final String ARG_FROM_MMP = "arg_from_mmp";
    public static final String ADDON_SIZE = "addon_size";
    public static final String MMP_ADDON_CODES = "mmp_addon_code";
    public static final String MMP_ADDON_CATEGORY = "mmp_addon_category";
    public static final String CITY = "city";
    public static final String DELIVERY_ADDRESS = "delivery_address";
    public static final String PREF_DN_NUMBER ="dnnumber" ;
//    public static final String DN_NUMBER ="dnNumber_per_order" ;
    public static final String PRODUCT_PLAN = "product_plan";

    public static final String LOCALE = "locale";
    public static final String BALANCE = "balance";
    public static final String IS_BB = "argFromBB";
    public static final String BB_TYPE = "bbType";
    public static final String PICK_UP_STORE ="pickupstore" ;
    public static final String STORE_ADDRESS = "storeaddress";
    public static final String PARTNER_UID ="PartnerID" ;
    public static final String PARTNER_CUSTOMER ="partner_customer" ;
    public static final String ADMIN ="admin" ;


    private static String prefFile = "Jibudo_Pref";
    private static String prefFileApp = "Jibudo_Pref_App";
    public static String SERVICE_INSTANCE_LIST="service_instance_list";
    public static String BILLING_DETAIL_MAP ="BILLING_DETAIL_MAP" ;
    public static String CHARGING_PATTERN_MAP ="CHARGING_PATTERN_MAP" ;

    private final SharedPreferences mPrefs;
    private final SharedPreferences mPrefsApp;


    //App Preference
    public static final String BSS_SETTINGS = "bssadapter";
    public static final String DCCM_SETTINGS = "dccm";
    public static final String CRM_SETTINGS = "crm";

    public static final String THEME_SETTINGS = "theme";
    public static final String PRE_TO_POST_SETTINGS ="pre2post" ;




    public static final String BSS_ADAPTER_IP ="bss_adapter_ip" ;
    public static final String BSS_ADAPTER_PORT ="bss_adapter_port" ;
    public static final String BSS_ADAPTER_SECURITY ="bss_security" ;
    public static final String KEY_PREF_PRE_TO_POST ="perform_pre2post" ;


    public static final String DCCM_IP ="dccm_ip" ;
    public static final String DCCM_PORT ="dccm_port" ;
    public static final String DCCM_SECURITY ="dccm_security" ;

    public static final String CRM_IP ="crm_ip" ;
    public static final String CRM_PORT ="crm_port" ;
    public static final String CRM_SECURITY ="crm_security" ;

    public static final String APP_THEME ="app_theme" ;


    public AppPreferencesHelper(Context context) {

        mPrefs = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
        mPrefsApp = context.getSharedPreferences(prefFileApp, Context.MODE_PRIVATE);
    }

    @Override
    public String get(String key, String defaultValue) {
        return mPrefs.getString(key, defaultValue);
    }

    @Override
    public void set(String key, String value) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(key, value);
        editor.commit();

    }

    @Override
    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return mPrefs.getBoolean(key, defaultValue);
    }

    @Override
    public void setint(String key, int value) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    @Override
    public int getint(String key, int defaultValue) {
        return mPrefs.getInt(key, defaultValue);
    }

    @Override
    public void clear() {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public void remove(String key) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.remove(key);
        editor.commit();
    }

    @Override
    public void setAppPref(String key, String value) {
        SharedPreferences.Editor editor = mPrefsApp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    @Override
    public String getAppPref(String key, String defaultValue) {
        return mPrefsApp.getString(key, defaultValue);
    }

    @Override
    public void setBooleanAppPref(String key, boolean value) {
        SharedPreferences.Editor editor = mPrefsApp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    @Override
    public boolean getBooleanAppPref(String key, boolean defaultValue) {
        return mPrefsApp.getBoolean(key, defaultValue);
    }

    @Override
    public void saveMap(String key,Map<String,String> inputMap){
       // SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("MyVariables", Context.MODE_PRIVATE);
      //  if (pSharedPref != null){
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = mPrefsApp.edit();
            editor.remove(key).commit();
            editor.putString(key, jsonString);
            editor.commit();
        //}
    }

    @Override
    public Map<String,String> loadMap(String key1){
        Map<String,String> outputMap = new HashMap<>();
     //   SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("MyVariables", Context.MODE_PRIVATE);
        try{
          //  if (pSharedPref != null){
                String jsonString = mPrefsApp.getString(key1, (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while(keysItr.hasNext()) {
                    String key = keysItr.next();
                    String value = (String) jsonObject.get(key);
                    outputMap.put(key, value);
                }
            //}
        }catch(Exception e){
            e.printStackTrace();
        }
        return outputMap;
    }

}
