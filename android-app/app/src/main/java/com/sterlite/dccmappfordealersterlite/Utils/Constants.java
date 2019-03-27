package com.sterlite.dccmappfordealersterlite.Utils;

import android.os.Environment;

import com.sterlite.dccmappfordealersterlite.BuildConfig;

/**
 * Created by etech10 on 17/6/17.
 */

public class Constants {

    public static String Preffered_Language="en";
    public static String Product_PREFIX="PRD";
    public static final String DOWNLOAD = "DOWNLOAD";
    public static final int SUCCESS_CODE = 1;
    public static final int FAIL_CODE = 0;
    public static final int CANCEL_CODE = 2;
    public static final int FAIL_INTERNET_CODE = 3;

    public static final String BUILD_VERSION = "6";

    public static final String RES_MSG_KEY = "responseMessage";
    public static final String RES_CODE_KEY = "responseCode";

    //Category Code

    //Basic
    //MTN

       public static final String CURRANCY_SYMBOL = "$";
  public static final String BASIC_PREPAID_PLAN_TYPE = "MTN_PREPAID";
    public static final String BASIC_POSTPAID_PLAN_TYPE = "MTN_POST";
    //FixedLine
    public static final String BB_PLAN_TYPE = "MTN_FTTH";
    //addon packs
    public static final String PLAN_PACK_INTERNET = "pack_internet";
    public static final String PLAN_PACK_ROAMING = "pack_roaming";
    public static final String PLAN_PACK_ENTERTAINMENT = "pack_entertainment";
    public static final String PLAN_PACK_SMS = "pack_sms";

    public static final String PLAN_PACK_INTERNET_PRE = "MTN_DATAADDON_PRE";
    public static final String PLAN_PACK_ROAMING_PRE = "pack_roaming";
    public static final String PLAN_PACK_ENTERTAINMENT_PRE = "MTN_MINADDON_PRE";
    public static final String PLAN_PACK_SMS_PRE = "MTN_SMSADDON_PRE";

    public static final String PLAN_PACK_INTERNET_POST = "MTN_DATAADDON_POST";
    public static final String PLAN_PACK_ROAMING_POST = "pack_roaming";
    public static final String PLAN_PACK_ENTERTAINMENT_POST = "MTN_MINADDON_PRE";
    public static final String PLAN_PACK_SMS_POST = "MTN_SMSADDON_POST";


    public static final String BUNDLE_TEMPLATE_MMP = "MAKEMYPLANSERVICEPLAN";
    public static final String BUNDLE_TEMPLATE_NEW_CONNECTION = "POSTPAID_SERVICE_PLAN";


    //MMP BASIC
    public static final String MMP_BASIC = "MTNSERVICEPLAN";

    //MMP ADDON
    public static final String MMP_ADDON_CATEGORY_DATA = "MAKEMYPLANDATAADDONMTN";
    public static final String MMP_ADDON_CATEGORY_DATA_SUB = "MAKEMYPLANMBADDONMTN";
    public static final String MMP_ADDON_CATEGORY_VOICE = "MAKEMYPLANVOICEADDONMTN";
    public static final String MMP_ADDON_CATEGORY_SMS = "MAKEMYPLANSMSADDONMTN";
    public static final String MMP_ADDON_CATEGORY_ROM = "MAKEMYPLANROAMINGADDONMTN";

    //Product ID
    public static final String CODE1 = "PRD00852";
    public static final String CODE2 = "PRD00875";
    public static final String CODE3 = "PRD00876";

    public static final String CODE4 = "PRD00855";
    public static final String CODE5 = "PRD00860";
    public static final String CODE6 = "PRD00861";

    //Data
  public static final String CODE7 = "PRD01022";
  public static final String CODE8 = "PRD01023";
  public static final String CODE9 = "PRD01024";
  public static final String CODE10 = "PRD01025";
  public static final String CODE11 = "PRD01026";
  public static final String CODE12 = "PRD01027";
  public static final String CODE13 = "PRD01028";

  //Postpaid
  public static final String CODE14 = "PRD01274";
  public static final String CODE15 = "PRD01272";
  public static final String CODE16 = "PRD01262";
  public static final String CODE17 = "PRD01263";
  public static final String CODE24 = "PRD01305";
  public static final String CODE25 = "PRD01306";
  public static final String CODE26 = "PRD01304";


  //Prepaid
  public static final String CODE18 = "PRD01282";
  public static final String CODE19 = "PRD01285";
  public static final String CODE20 = "PRD01276";
  public static final String CODE21 = "PRD01281";
  public static final String CODE22 = "PRD01286";
  public static final String CODE23 = "PRD01294";



  public static final String CODE_BB = "PRD00819";
    public static final String CODE_MODEM = "PRD00826";
    public static final String CODE_IPTV = "PRD00818";


//TELKOMSEL
  /*  public static final String CURRANCY_SYMBOL = "Rp";
    public static final String BASIC_PREPAID_PLAN_TYPE = "TSEL_PREPAID";
    public static final String BASIC_POSTPAID_PLAN_TYPE = "TSEL_POSTPAID";
    public static final String BB_PLAN_TYPE = "MTN_POST";

    public static final String MMP_BASIC = "SERVICEPLAN";

    public static final String MMP_ADDON_CATEGORY_DATA = "MAKEMYPLANDATAADDON";
    public static final String MMP_ADDON_CATEGORY_DATA_SUB = "MAKEMYPLANMBADDON";
    public static final String MMP_ADDON_CATEGORY_VOICE = "MAKEMYPLANVOICEADDON";
    public static final String MMP_ADDON_CATEGORY_SMS = "MAKEMYPLANSMSADDON";
    public static final String MMP_ADDON_CATEGORY_ROM = "MAKEMYPLANROAMINGADDON";


    public static final String PLAN_PACK_INTERNET = "pack_internet";
    public static final String PLAN_PACK_ROAMING = "pack_roaming";
    public static final String PLAN_PACK_ENTERTAINMENT = "pack_entertainment";
    public static final String PLAN_PACK_SMS = "pack_sms";

    public static final String PLAN_PACK_INTERNET_PRE = "pack_internet";
    public static final String PLAN_PACK_ROAMING_PRE = "pack_roaming";
    public static final String PLAN_PACK_ENTERTAINMENT_PRE = "pack_entertainment";
    public static final String PLAN_PACK_SMS_PRE = "pack_sms";

    public static final String PLAN_PACK_INTERNET_POST = "pack_internet";
    public static final String PLAN_PACK_ROAMING_POST = "pack_roaming";
    public static final String PLAN_PACK_ENTERTAINMENT_POST = "pack_entertainment";
    public static final String PLAN_PACK_SMS_POST = "pack_sms";


    //prepaid
    public static final String CODE1 = "PRD00742";
    public static final String CODE2 = "PRD00745";
    public static final String CODE3 = "PRD00746";
    public static final String CODE4 = "PRD00747";
    public static final String CODE5 = "PRD00791";
    //    //postpaid
    public static final String CODE6 = "PRD01021";
    public static final String CODE7 = "PRD01022";
    public static final String CODE8 = "PRD01023";
    public static final String CODE9 = "PRD01024";
    public static final String CODE10 = "PRD01025";
    public static final String CODE11 = "PRD01026";
    public static final String CODE12 = "PRD01027";
    public static final String CODE13 = "PRD01028";

    public static final String CODE_BB = "PRD00819";
    public static final String CODE_MODEM = "PRD00820";
    public static final String CODE_IPTV = "PRD00818";*/


    public static final String NETFLIX = "netflix";
    public static final String YOUTUBE = "youtube";
    public static final String PRIME = "prime";
    public static final String DELIVERED = "SIM_DELIVERED";
    public static final String MY_ACCOUNT_PAGE = "myAccount";
    public static final String ACTIVATION_PAGE = "activation";
    public static final String ORDER_TRACKING_PAGE = "orderTracking";
    public static final String BALANCE_TRANSFER_PAGE = "balanceTransfer";
    public static final String VIDEO = "video";
    public static final String VOICE = "voice";
    public static final String SMS = "sms";
    public static final String DATA = "data";
    public static final String ROUTER = "router";

    public static final String MONETARY = "monetary";
    public static final String POSTPAID = "Postpaid";
    public static final String PREPAID = "Prepaid";

    public static final String INV_PREMIUM = "Gold";
    public static final String INV_FREE = "Default";
    public static final String CASH ="Online" ;
    public static final String BALANCE ="Balance" ;
    public static final String PLACEORDER ="placeorder" ;
    public static final String MTN = "MTN";
    public static final String TELKOMSEL = "TELKOMSEL";
  public static final String VODAFONE = "VODAFONE";

  public static final String STERLITE = "STERLITE";

    public static final String APP = STERLITE;

    public static final String TYPE_BROADBAND = "broadband";
    public static final String TYPE_BROADBAND_MODEM = "broadband_modem";
    public static final String TYPE_IPTV = "iptv";

    public static final String PIN_CODE ="pincode" ;

    public static int PERMISSION_CODE = 101;
    public static int PERMISSION_CODE_LOCATION_MAP = 103;

    public static final String LANGUAGE = "language";

    public final static int DEFAULT_DISTANCE_FOR_PATH_DRAW = 100;

    public static String APP_HOME = Environment.getExternalStorageDirectory().getPath() + "/Missing1";
    public static String DIR_MEDIA = APP_HOME + "/media/";

    public static final String INTENT_NOTIFFICATION_TYPE = "notification_Type";
    public static final String INTENT_FILTER_RECEIVER_NOTIFICATION = BuildConfig.APPLICATION_ID + "." + "CUSTOM_NOTIFICATION";

    public static Boolean IS_DUMMY_MODE = true;

    public static String PLAN_POSTPAID = "postpaid";
    public static String PLAN_PREPAID = "prepaid";
    public static final String BROADBAND_PLAN_PREPAID = "Broadband_Prepaid_Plan";
    public static final String BROADBAND_PLAN_POSTPAID = "Broadband_Postpaid_Plan";
    public static String PLAN_CART = "planCart";

    public static int HTTP_TIMEOUT = 120000;// Request Timeout
    public static final String RECHARGE_TYPE = "DENOMINATION";
    public static final String SOURCE = "DCCM";

    public static final String ISOCODE = "MUM";
  public static final String USER_FEEDBACK_SCREEN = "USER_FEEDBACK";

    //    public static final String CODE6= "PRD00727";
//    public static final String CODE5= "PRD00634";
    public static final String PROFILE = "profile";

    /*public static final String IP = "103.23.140.225";
    public static final String BSS_Adapter_IP = "103.23.140.225";
     */
    // public static final String IP = "103.23.140.225";//---218
    //public static final String IP = "103.23.140.238";//192.168.2.5
//  public static final String IP = "103.23.140.242";//10.151.1.143

  //public static final String IP = "10.151.5.84"; //103.23.140.224

  public static final String DCCM_IP = "103.23.140.224"; //10.151.5.84
  public static final String DCCM_PORT = "9002";
  public static final String DCCM_SECURITY = "https://";
  //public static final String CRM_IP = "103.23.140.235";
  public static final String CRM_IP = "10.151.5.84";
  public static final String CRM_PORT = "58081";
  public static final String CRM_SECURITY = "http://";


  public static final String BSS_Adapter_IP = "103.23.140.225";//192.168.0.218
  public static final String BSS_Adapter_PORT = "18080";
  public static final String BSS_SECURITY = "http://";

  public static final String PRE_TO_POST_DEFAULT = "true";


  public static final String APP_THEME = "TELKOMSEL";
  public static final String DEFAULT_APP_THEME = "STERLITE";

  //public static final String IP = "103.23.140.224"; //10.151.5.84
 // public static final String BSS_Adapter_IP = "103.23.140.225";

  public static final String ADMIN_USER = "admin";
  public static final String ADMIN_PASS = "0000000000000";

}


