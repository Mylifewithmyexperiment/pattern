package com.elitecorelib.core;

import android.os.Build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static com.elitecorelib.core.utility.ElitelibUtility.encrypt;

public interface CoreConstants {


    boolean LOGGER_CONSOLE_ENABLE = true;
    boolean LOGGER_ENCRYPTION_ENABLE = true;
    String LIBRARY_VERSION = "3.0.0";
    String KEY_OPTINOUTPORTALURL = "optInOutPortalURL";
    String REGISTEREDDEVICEID = "registeredDeviceId";
    int intervalSnackbar = 5000;
    int MESSAGE_READING_TIME = 10;
    String HOTSPOT_SYNC_STATUS = "HOTSPOT_SYNC_STATUS";
    String IS_FIRSTTIME_SYNC = "is_firsttime_sync";
    String VERSION = "version";
    String WIFISETTINGS_SERVER = "SERVER";
    String AD_CLICK_URL = "add_click_url";
    String CONFIG_FILE_NAME = "configuration.properties";
    int MAX_CHAR_WRITE_LIMIT = 10000;
    String DEFAULT_LOCATION = "default";

    String WIFI_SECURITY_EAP = "WPA/WPA2 Enterprise";
    String WIFI_SECURITY_WPAWPA2 = "WPA/WPA2";
    String WIFI_SECURITY_OPEN = "Open";
    String WIFI_EAP_SIM = "EAP-SIM";
    String WIFI_EAP_ELITESIM = "Elite EAPSIM";
    String WIFI_USERNAME_VARIABLE = "{IMSI}";
    String POJOLOCATION_COUNT = "pojolocaion_count";
    String POJOLOCATION = "pojolocation";
    String TERMCONDITION = "SUCCESS";
    String KEY_PACKAGETYPE = "packageType";
    String KEY_CVVLENGTH = "cvvLength";
    String KEY_CREDITCARDLENGTH = "creditCardLength";
    String GOOGLE_SENDER_ID = "GOOGLE_SENDER_ID";
    String BLACKLIST_CHECK_MCCREDITCARD = "blacklistCheckMC";
    String BLACKLIST_CHECK_ECCREDITCARD = "blacklistCheckEC";
    String MAP_RECIEVER = "MapLoad";
    String MAPTYPE = "mapType";
    String CLUSTOR_MAP = "ClustorMap";
    String NEARBY_MAP = "NearByMap";
    // Silent notification constant
    String Silent_Notification = "$Silent";

    public static final String ANDSF_DOWNLOADSPEED_VALUE = "2";
    public static final String ANDSF_UPLOADSPEED_VALUE = "2";
    public static final String ISSPEEDTHRESHOLDENABLE_VALUE = "true";
    public static final String rangeJioMessage = "rangeJioMessage";

    String LAC_VALUE = "lac_Value";
    String CELL_ID_VALUE = "cell_id_value";

    /*
     * email 'from' and 'to' sender details
     * 'from' array for emailid and password
     * 'to' array for multiple user to send
     */
    String KEY_WOWCARDLENGTH = "wowCardLength";
    String KEY_CREDITCARDCVVENCRYPTPASSWORD = "creditCardCVVEncryptPassword";
    String KEY_LOCATIONTARGETEDKEYFORAD = "locationTargetedKeyForAd";
    int drawer_width_percentage = 80;
    /**
     * If this configuration is enable it will append realm for Elitecore TTLS request and pass default password
     */
    String ENABLE_OTHERAAA = "ENABLE_OTHERAAA";
    String ENABLE_OTHERAAA_VALUE = "ENABLE";
    boolean ENABLE_LIMITED_API_USAGE = false;
    //**************************Application
    String ELITECORE_PASSWORD = "JTSZit90/IG+5g1xZXpJcQ==";
    String APPLICATION_PREFERENCE = "ELITECONNECT_PREFERENCES";
    String CORE_PACKAGE_NAME = "PACKAGE_NAME";
    //****************** License
    String SECRETKEY = "SecretKey";
    String LICENSE_LOCAL = "License Local";
    String LICENSE_SERVER = "License Server";
    String CURRENT_LICENSE_MECHANISAM = CoreConstants.LICENSE_SERVER;
    boolean ENABLE_MONETIZATION_REGISTRATION = false;
    //Configuration
    String ELITECORE_LICENSE_KEY = "ElitecoreSDK_license_key";
    String ELITECORE_SERIAL_KEY = "ElitecoreSDK_serial_key";
    // gradle property
    String GRADLE_MONETIZATION_SERVER_URL = "GRADLE_MONETIZATION_SERVER_URL";
    String GRADLE_SMP_SERVER_URL = "GRADLE_SMP_SERVER_URL";
    //THIS NEED TO BE CONFIGURE TO CHANGE THE LICENSE MECHANISAM,  LOCAL OR SERVER
    String isAlreadyLogin = "isAlreadyLogin";
    String DEVICEID = "deviceId";
    String MSISDN = "msisdn";
    String OTP = "OTP";
    String LICENSE_SECURE_KEY = "tiC61m5/ZGE=";
    String DEVICECATEGORY = "deviceCategory";
    String REGISTERWITH = "Elite Connect";
    String OTP_FLOW_ENABLE = "isOTP_FLOW_ENABLED";

    String REGISTERWITH_GOOGLE = "Google";
    String REGISTERWITH_FACEBOOK = "Facebook";

    String GENDERMALE = "Male";
    String GENDERFEMALE = "Female";
    String GENDEROTHER = "Other";

    String GoogleFbName = "fbgoogleName";
    String FbLogin = " (Facebook)";
    String GoogleLogin = " (Google)";
    long MILLIS_PER_DAY = 1000 * 60 * 60 * 24;
    long MILLIS_PER_YEAR = 365 * MILLIS_PER_DAY;
    /**
     * Advertizement
     */
    String AD_URLPARAMETER = "n";
    HashMap<String, String> androidUrlSaveHashMap = new HashMap<String, String>();
    ArrayList<HashMap<String, String>> advertisementURLList = new ArrayList<HashMap<String, String>>();
    String SCREEN_FOOTER = "SCREENFOOTER";
    String SCREEN_HEADER = "SCREENHEADER";
    String SCREEN_FULLSCREEN = "SCREENFULLSCREEN";
    String BASE_URL_AD = "http://103.23.140.229:8443/monetization/services/MonetizationWS/";
    String GETDYNAMICADVERTISEMENT = "getDynamicAdvertise";
    String ADLOCATIONFULLSCREEN = "FULLSCREEN";
    String ADLOCATIONHEADER = "HEADER";
    String ADLOCATIONFOOTER = "FOOTER";
    String ADLOCATIONNODISPLAY = "NODISPLAY";
    String KEY_GENERICSERVICETYPE = "genericServiceType";
    String KEY_NORMALFREEWIFIINTERVAL = "normalFreeWiFiInterval";
    String KEY_PREMIUMFREEWIFIINTERVAL = "premiumFreeWiFiInterval";
    String NORMALFREEWIFIINTERVAL_DEFAULT = "60";
    String PREMIUMFREEWIFIINTERVAL_DEFAULT = "65";
    String MODULE_DYNAMICADVERTISEMENT = "DynamicAdvertisement";
    String MODULE_WIFIMANAGEMENT = "WifiManagement";
    String MODULE_LOCATION = "Location";
    String MODULE_SERVERCONFIG = "ServerConfig";
    String MODULE_VERSION = "Version";
    String MODULE_APPCONFIGURATIONPARAM = "AppConfigurationParam";
    String Key_LOCATION = "location";
    //****************************Logger
    String LOGGER_TAG = "ELITECORELIB";
    String LOGGER_FILE_NAME = ".txt";
    String LOGGER_FOLDER_NAME = "ELITECORE";
    boolean LOGGER_FILE_ENABLE = true;
    String LOGGER_MODE = "loggerMode";
    String DEVELOPMENT_MODE = "DEVELOPMENT_MODE";
    String RELEASE_MODE = "RELEASE_MODE";
    String MAIL_HOST = "smtp.gmail.com"; // default smtp server
    String MAIL_PORT = "465"; // default smtp port
    String MAIL_SOCKETPORT = "465"; // default socketfactory port
    String LOGGER_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String LOGGER_DATETIME_FILE_FORMAT = "dd-MM-yyyy";
    String DATE_FORMAT = "MM/dd/yyyy";
    int LOOGGER_FILE_DELETE_NUMBERDAYS = 3;
    String LOGGER_ENCRYPTION_KEY = "tiC61m5/ZGE=";
    String LOGGER_ENCRYPTION_AES_KEY = "monetizationwifi";
    String SCHEMA_ENCRYPTIONKEY = encrypt("sterlite@123sterlite@123sterlite@123sterlite@123");
    String VERSION_ALREADYUPDATED = "You have already latest updated version";
    String WIFISETTINGS_APPLICATION = "APPLICATION";

    String NEED_DEVICEID_UPDATED = "need_deviceId_updated";
    String DEVICEID_UPDATED = "deviceId_updated";
    String VERSION_FORCEUPDATE_FALSE = "FALSE";
    String VERSION_FORCEUPDATE_TRUE = "TRUE";
    /**
     * Share Preference Constant
     */
    String APPLICATION_NOTIFICATION = "APPLICATION_NOTIFICATION";
    String ENABLE = "ENABLE";
    String DISABLE = "DISABLE";
    String TRACK_LOCATION = "TRACK_LOCATION";
    String AUTO_CONNECT = "AUTO_CONNECT";
    String CURRENT_SCREEN = "CURRENT_SCREEN";
    String APPCOLOR = "AppColor";
    String DATABASE_TYPE = "databaseType";
    String SQLITE = "SQLite";
    String REALM = "realms";

    String VALUE_UNLIMITED = "UNLIMITED";

    //ANDSF constants
    String ISANDSFENABLED = "isANDSFEnabled";
    int MONETIZATION_ANDSFGETTOKEN_REQUESTID = 27;
    int MONETIZATION_ANDSFGETPOLICY_REQUESTID = 28;

    String GETANDSFURL = "http://103.23.140.229:8443/monetization/services/ANDSFWS/";
    String ANDSFAccessToken = "getANDSFAccessToken";
    String ANDSFGetPolicy = "getANDSFPolicy";
    String MONETIZATION_URL = "MONETIZATION_URL";
    String ANDSF_URL = "ANDSF_URL";
    String LIBRARY_PACKAGE_NAME = "packageName";

    /*
     * Default Allowed IMSI list
     * Normally if enable to check operator name before WIFI connection, it wont allow if you are not user of specific provider
     * These imsi wont be checked,These are internal users for Elitecore testing environment and will be ingoned.
     */

    String[] defaultIMSIArray = {"404989884546297", "404240024638036", "405060018493075", "404240027566171", "405030012801813"};
    /*
     * Web service constants
     */
    String MONETIZATION_REGISTERUSERPROFILE = "registerUserProfile";
    String MONETIZATION_SENDNOTIFICATIONBYLOCATION = "sendNotificationByLocation";
    String MONETIZATION_SYNCLOCATION = "syncLocation";
    String MONETIZATION_SYNCDATA = "syncData";
    String MONETIZATION_SEARCHHOTSPOT = "searchHotspot";
    String MONETIZATION_GETTERMSANDCONDITION = "getTermsAndCondition";
    String MONETIZATION_GETBLACKLISTSSID = "getBlacklistSSID";
    String MONETIZATION_GETDYNAMICADVERTISE = "getDynamicAdvertise";
    String MONETIZATION_GETSYSTEMCONFIGURATION = "getSystemConfiguration";
    String MONETIZATION_GETWIFIDATA = "getWifiData";
    String MONETIZATION_GETLATESTVERSION = "getLatestVersion";
    String MONETIZATION_UPDATEDEVICETOKEN = "updateDeviceToken";
    String MONETIZATION_CHANGEUSERCONFIGURATION = "changeUserConfiguration";
    String MONETIZATION_GET_FEEDBACKURL = "getSurvey";
    String MONETIZATION_SUBMIT_FEEDBACKURL = "submitSurvey";
    String MONETIZATION_GET_DYNAMIC_PARAMETER_URL = "getParameters";
    String ANDSF_ACCESS_TOKEN = "accessToken";
    String ANDSF_LASTSYNCTIME = "lastSyncTime";

    //Deals
    String MONETIZATION_GETDEALTAGLIST = "getDealTagList";
    String MONETIZATION_GETALLDEALS = "getAllDeals";
    String MONETIZATION_GETDEALFROMTAGID = "getDealFromTagId";
    String MONETIZATION_GETFILTEREDDEALS = "getFilteredDeals";
    String MONETIZATION_GETDEALVOUCHERCODE = "getDealVoucherCode";
    String MONETIZATION_GETVOUCHERLIST = "getVoucherList";
    String MONETIZATION_GETDEALINFO = "getDealInfo";
    String MONETIZATION_GETTAGIMAGEPATH = "tagimages/";
    String MONETIZATION_GETDEALIMAGEPATH = "dealimages/";
    String MONETIZATION_REDEEMDEALVOUCHERCODE = "redeemDealVoucherCode";
    String MONETIZATION_GETDEALLIST = "getDealList";
    String MONETIZATION_SENDOTP = "sendOTP";
    String MONETIZATION_VERIFYOTP = "verifyOTP";
    String MONETIZATION_UPDATE_SUBSCRIBER_MOBILENUMBER = "updateSubscriberMobileNumber";
    String MONETIZATION_ADDEVENT = "addEvent";
    String EVENTDATA = "eventData";

    // Request ID for core web services.
    int MONETIZATION_REGISTERUSERPROFILE_REQUESTID = 1;
    int MONETIZATION_SENDNOTIFICATIONBYLOCATION_REQUESTID = 2;
    int MONETIZATION_SYNCLOCATION_REQUESTID = 3;
    int MONETIZATION_SYNCDATA_REQUESTID = 4;
    int MONETIZATION_SEARCHHOTSPOT_REQUESTID = 5;
    int MONETIZATION_GETTERMSANDCONDITION_REQUESTID = 6;
    int MONETIZATION_GETDYNAMICADVERTISE_REQUESTID = 7;
    int MONETIZATION_GETGOOGLEREVERSE_LOCATION_REQUESTID = 8;
    int MONETIZATION_GETGOOGLESERACH_REQUESTID = 9;
    int MONETIZATION_GETSYSTEMCONFIGURATION_REQUESTID = 10;
    int MONETIZATION_GETWIFIDATA_REQUESTID = 11;
    int MONETIZATION_GETLATESTVERSION_REQUESTID = 12;
    int MONETIZATION_UPDATEDEVICETOKEN_REQUESTID = 13;
    int MONETIZATION_SENDOTP_REQUESTID = 14;
    int MONETIZATION_VERIFYOTP_REQUESTID = 15;
    int MONETIZATION_UPDATE_SUBSCRIBER_MOBILENUMBER__REQUESTID = 16;
    int MONETIZATION_GETBLACKLISTSSID_REQUESTID = 17;


    int MONETIZATION_GET_FEEDBACK_REQUESTID = 18;
    int MONETIZATION_SUBMIT_FEEDBACK_REQUESTID = 19;

    int MONETIZATION_GETINTERNET_REQUESTID = 20;
    int MONETIZATION_GETINTERNET_RUNING_REQUESTID = 21;
    int MONETIZATION_CHANGEUSERCONFIGURATION_REQUESTID = 22;
    int MONETIZATION_ANALYTICSYNC_REQUESTID = 23;
    int MONETIZATION_GETINTERNETBASE_REQUESTID = 24;
    int MONETIZATION_GETINTERNET_BRAND_REQUESTID = 25;
    int MONETIZATION_GET_DYNAMIC_PARAMETER_REQUESTID = 26;
    int MONETIZATION_GETINTERNET_DOWNUPSPEED_REQUESTID = 27;
    int ANALYTIC_REQUEST_CODE = 121212;

    String EVENTANALYTICSENABLE = "eventAnalyticsEnable";
    String EVENTANALYTICSMODE = "eventAnalyticsMode";
    String EVENTANALYTICSINTERVAL = "eventAnalyticsInterval";
    String NOTIFICATIONTRIGGERRESET = "notificationTriggerReset";
    String NOTIFICATIONTRIGGERRESET_VALUE = "86400000";


    //************************ UserIdentity
    String USERIDENTITY = "userIdentity";
    int SUBSCRIBERPROFILETYPE_VALUE = 1;

    String ANSWERDATA = "answerData";
    String QUESTIONID = "questionId";
    String QUESTION = "question";
    String QUESTIONTYPE = "questionType";
    String ANSWER = "answer";
    String RATE = "Rate";

    String MESSAGETYPE = "messageType";
    String MESSAGETYPEVALUE = "leaveMessage";

    /*
        Constants check to internet availability during Captive Portal

     */
    String INTERNET_CHECK_URL = "http://connectivitycheck.android.com/generate_204";
    int INTERNET_CHECK_TIMEOUT = 10000;
    String INTERNET_CHECK_HEADER = "Connection";
    String INTERNET_CHECK_HEADER_CLOSE = "close";
    String INTERNET_CHECK_SUCCESS = "success";
    String INTERNET_CHECK_FAIL = "fail";

    String SMS_SENDER_NAME = "smsSender";
    String SMS_SENDER = "BT-BSWIFI";
    String REGISTRATIONJSON = "RegistrationJSON";
    String LOGGER_NAME = "LoggerName";

    String Realm_Eapttls = "RealmEapttls";
    String HotspotSearchMechanisam = "hotspot_search_Mechanisam";
    String SEARCH_HOTSPOT_METHOD_LOCAL = "Local";

    String OTPMESSAGE_VALUE = "Your PIN to access BSNL-Fi is ######@@Welcome to BSNL Wi-Fi. Your PIN to access BSNL Wi-Fi is ######";
    String KEY_HIDDENPARAMS = "hiddenParams";

    HashMap<String, Boolean> androidAdActiveHashMap = new HashMap<String, Boolean>();

    String POWER_BUTTON = "POWER_BUTTON";
    Boolean ISPOWER_ON = true;
    Boolean ISPOWER_OFF = false;

    String DO_REGISTER = "DO_REGISTER";
    String NOT_REGISTER = "You need to invoke doRegister method first to access this API";

    //    String mail_subject = "Subject";
//    String mail_body = "Body";
    String imei = "imei";
    String MENU_TITLE_PREF = "MENU_TITLE_PREF";
    String NOTIFICATIONCOUNT = "notificationCount";
    String NOTIFICATIONMESSAGE = "notificationMessage";
    //Message seperator to identify multiple notification from receiving message
    String MESSAGESEPERATOR = "{MS}";
    String REGEXMESSAGESEPERATOR = "\\{MS\\}";
    String KEY_LOGO = "logo";
    String KEY_NOTIFICATION_LOGO = "Notification_logo";
    String KEY_IS_REGISTRAION = "is_registration";
    String NOTIFICATION_MODE = "NOTIFICATION_MODE";
    String NOTIFICATION_MODE_FCM = "NOTIFICATION_MODE_FCM";
    String SMP_BRAND = "SMP_BRAND";
    //location
    String CURRENT_LATITUDE = "CURRENT_LATITUDE";
    String CURRENT_LONGITUDE = "CURRENT_LONGITUDE";
    String KEY_REPORT_PROBLEM_EMAIL = "reportProblemEmail";
    String KEY_REPORT_PROBLEM_PASSWORD = "reportPorblemPassword";
    String KEY_REDIRECTIONURL = "RedirectionURL";
    String KEY_URL = "url";
    String SERVERUNREACHBLEMESSAGE = "serverUnreachbleMessage";
    String TERMS_MODE_PREFERENCE = "termsAndConditionMode";
    String NFCALLBACKMODE = "nfCallBackMode";
    String TERMS_AND_CONDITION_VALUE = "termsandconditionvalue";
    String SYNCINTERVALTIME = "syncIntervalTime";
    String LOCATIONBASENOTIFICATION = "locationBaseNotification";
    String NEXT_SYNCINTERVALTIME = "next_syncIntervalTime";
    String NFCALLBACKINTERVAL = "nfCallBackInterval";
    String LOCATIONSYNCRANGE = "locationSyncRange";
    String ADREFRESHINTERVAL = "adRefreshInterval";
    String COMMUNICATIONMODE = "communicationMode";
    String ADENABLE = "adEnable";
    String WIFISETTINGS_FROM = "wifiSetting";
    String WIFI_CONNECTION_TIMEOUT = "wifiConnectionTimeout";
    int WIFI_CONNECTION_DEFAULT_TIMEOUT = 20;
    String WIFISETTINGS_NOCONNECTION = "NOCONNECTION";
    String EMAILPATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    String IFFAILTRYWITHANOTHERMETHOD = "IF_FAIL_TRY_WITH_ANOTHER_METHOD";

    String KEY_LOGINDETAIL = "loginDetailMap";
    String KEY_RESULTCODE = "ResultCode";
    String KEY_PAKCAGETYPE = "packageType";
    String NFCALLBACKMODETIME_VALUE = "1";
    String NFCALLBACKMODEDISTANCE_VALUE = "2";

    String KEY_RESPONSEDATA = "responseData";
    String KEY_RESPONSEMESSAGE = "responseMessage";

    // Constant Value for Application if not getting from server
    String NFCALLBACK_TIMEMODE_VALUE = "1";
    String NFCALLBACK_LOCATIONMODE_VALUE = "2";
    // Milliseconds per second
    int MILLISECONDS_PER_SECOND = 1000;
    // Update frequency in seconds
    int UPDATE_INTERVAL_IN_SECONDS = 30;
    // Update frequency in milliseconds
    long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // The fastest update frequency, in seconds
    int FASTEST_INTERVAL_IN_SECONDS = 30;
    // A fast frequency ceiling in milliseconds
    long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
    int NFCALLBACKINTERVAL_VALUE = 10;
    int WS_COMMUNICATION = 1;
    int NUMBEROFNOTIFICATIONFORSAMELOCATION_VALUE = 3;
    String imsi = "imsi";
    String LOCATIONID = "locationId";
    String operatingSystemValue = "ANDROID";
    String operatingSystem = "operatingSystem";
    String APPLANGUAGE = "appLanguage";
    String NOTIFICATION_REDIRECT_CLASS = "notificationredirectclass";
    String BROADBAND_USERNAME = "broadband_username";
    String BROADBAND_PASSWORD = "broadband_password";

    // Monetization Key Value Pair Costant Key
    String KEY_OFFLOADSSID = "offloadSSID";
    String KEY_OFFLOADSSID_LOCAL = "offloadSSID_local";
    String KEY_BROADBANDSSID = "broadbandSSID";
    String KEY_OPENSSID = "openSSID";
    String KEY_NOTIFICATIONEVENTBRAND = "notificationEventBrand";
    String KEY_NOTIFICATIONEVENT = "notificationEvent";
    String KEY_PAIDSERVICETYPE = "paidServiceType";
    String KEY_FREEWIFIPACKAGEID = "freeWiFiPackageID";
    String KEY_FREESERVICETYPE = "freeServiceType";
    String KEY_REPORTPROBLEMEMAIL = "reportProblemEmail";
    String KEY_MCC = "MCC";
    String KEY_MNC = "MNC";
    String KEY_LOCAL_MCC = "LOCAL_MCC";
    String KEY_LOCAL_MNC = "LOCAL_MNC";
    String KEY_CHECK_SIM_OPERATOR = "checkSIMOperator";
    String KEY_LOCAL_SIM_OPERATOR = "checklocalSIMOperator";
    String KEY_ISPAYMENTGATEWAYDISPLAY = "isPaymentGatewayDisplay";
    String KEY_DEFAULTPAYMENTGATEWAY = "defaultPaymentGateway";
    String KEY_ISLOCATIONSENT = "isLocationSent";
    String KEY_DEFAULTLOCATIONVALUE = "defaultLocationValue";
    String KEY_BROADBANDSERVICETYPE = "broadbandServiceType";
    String KEY_BROADBANDDOMAIN = "broadbandDomain";
    String KEY_BROADBANDAUTHPROTOCOL = "broadbandAuthProtocol";
    String KEY_PLANSTATUS = "planStatus";
    String KEY_PLANOPERATION = "planOperation";
    String KEY_FREEWIFIPACKAGETYPE = "freeWiFiPackageType";
    String KEY_PAIDWIFIPACKAGETYPE = "paidWiFiPackageType";

    int TYPE_OFFLOAD_WIFI = 1;
    int TYPE_BROADBAND_WIFI = 2;
    int TYPE_OPEN_WIFI = 3;
    String APIDENTITY = "APIdentity";
    String REG_LOC = "REG_LOC";
    String CALLEDSTATIONID = "CalledStationId";
    String NAS_IDENTIFIER = "NAS_IDENTIFIER";
    String Seprator = "#";

    String DEFAULT_PROFILE_NAME = "Profile_name";
    String DEFAULT_SSID = "Ssid_name";
    String DEFAULT_SECURITY = "Security_Method";
    String DEFAULT_AUTHENTICATION_METHOD = "Authentication_Method";
    String DEFAULT_PHASE2_AUTHENTICATION = "Phase2_authentication";
    String DEFAULT_PASSWORD = "Password";
    String ENCRYPTION_ALGORITHM = "PBEWithMD5AndDES";
    String SIMINSERTFIRSTSLOT = "sim_insert_first_slot";
    String SIMOPERATORNAME = "SimOperatorName";
    String NOSIMCARDMSG = "nosimcardmessage";
    String NOSIMINDEVICE = "No SIM Card in Device";
    String ISSTANDALONE = "isStandAloneApp";
    String OUT_RANGE_MESSAGE = "out_range_message";
    String OTPCOUNT = "otpCount";
    String OTPCOUNT_VALUE = "6";
    String NEARBYDISTANCE = "nearByDistance";
    String NEARBYDISTANCE_VALUE = "5000";
    String CURRENTLOCATION = "currentlocation";
    String WIFILAUNCHED = "WIFILAUNCHED";


    Locale LOCALE_UK = new Locale("en", "UK");
    String LOCALE_UK_STR = "English";
    String LOCALE_AR_STR = "Arabic";
    String LOCALE_HI_STR = "Hindi";
    String LOCALE_GU_STR = "Gujarati";
    Locale LOCALE_AR = new Locale("ar", "AR");
    Locale LOCALE_HI = new Locale("hi", "HI");
    Locale LOCALE_GU = new Locale("gu", "GU");
    String LOCALE_KEY = "LOCALE_KEY";
    String ENGLISH_CODE = "en";

    String Key_Header_Date = "Date";
    String ISNOTIFICATIONON = "isNotificationOn";
    String APPVERSION = "appVersion";

    String KEY_COMPLIMENTARYSERVICETYPE = "complimentaryServiceType";
    String KEY_CONTINUECOMPLEMENTARYTYPE = "continueComplementaryType";
    String KEY_PINLENGTH = "pinLength";
    String KEY_PINRE = "pinRE";
    String KEY_NAMERE = "nameRE";
    String KEY_USERBLOCKTIME = "userBlockTime";

    String REGEXSTR = "^[0-9]*$";
    String OTPMESSAGE = "otpMessage";
    String LOCAL_OTP_FORMAT = "localotpformat";
    String OTP_READ_TIMEOUT = "otpreadtime";
    String OTP_READ_TIMEOUT_VALUE = "5";
    String ADVERTISEMENT_LANGUAGE = "ADVERTISEMENT_LANGUAGE";
    String ADVERTISEMENT_AGERANGE = "ADVERTISEMENT_AGERANGE";
    String ADVERTISEMENT_LOCATION = "ADVERTISEMENT_LOCATION";
    String ADVERTISEMENT_LIVEIN = "ADVERTISEMENT_LIVEIN";
    String ADVERTISEMENT_COUNTRY = "ADVERTISEMENT_COUNTRY";
    String ADVERTISEMENT_GENDER = "ADVERTISEMENT_GENDER";

    String M_CATEGORY_OTHER = "Other";
    String KEY_PHONENORE = "phoneNoRE";


    /*
     *Advertisement Screen Constants
     */
    String TERMS_AND_CONDITION_SCREEN = "TERMS_AND_CONDITION";
    String ABOUT_SCREEN = "ABOUT";
    String NEAR_BY_HOTSPOT_SCREEN = "NEAR_BY_HOTSPOT";
    String PREFERENCE_SCREEN = "PREFERENCE";
    String DASHBOARD_SCREEN = "DASHBOARD";
    String INVENTORY_SCREEN = "INVENTORY";
    String PLAN_SCREEN = "PLAN";
    String KYC_SCREEN = "KYC";
    String KYC_DETAIL_SCREEN = "KYC_DETAIL";
    String MAKANI_ID_SCREEN = "MAKANI_ID";
    String ADDRESS_SCREEN = "ADDRESS";
    String ORDER_CONFIRM_SCREEN = "ORDER_CONFIRM";
    String THANK_YOU_SCREEN = "THANK_YOU";
    String USER_FEEDBACK_SCREEN = "USER_FEEDBACK";
    String OTHER_SCREEN = "OTHER";
    String ENTER_MOBILENO_SCREEN = "ENTER_MOBILENO";
    String FREE_LOGOUT_SCREEN = "FREE_LOGOUT";
    String PAID_WIFI_LOGIN_SCREEN = "PAID_WIFI_LOGIN";
    String PAID_RECHARGE_SCREEN = "PAID_RECHARGE";
    String PREMIUM_LOGOUT_SCREEN = "PREMIUM_LOGOUT";
    String PREMIUM_SURVEY_SCREEN = "PREMIUM_SURVEY";
    //wifi marker category
    String M_CATEGORY_CAFE = "Cafe";
    String M_CATEGORY_BUSINESS_HUB = "Business Hub";
    String M_CATEGORY_METRO_STATION = "Metro Station";
    String M_CATEGORY_ENTERTAINMENT = "Entertainment";
    String M_CATEGORY_MALL = "Mall";
    String M_CATEGORY_GARDEN = "Garden";
    String M_CATEGORY_INSTITUTE = "Institute";
    String M_CATEGORY_COMMERCIAL_BUILDING = "Commercial Building";
    String M_CATEGORY_RESEIDENCIAL_BUILDING = "Residential Building";
    String M_CATEGORY_OFFICE_PREMISES = "Office Premises";
    String M_CATEGORY_AMUSEMENT_PARK = "Amusement Park";
    String M_CATEGORY_RESORT = "Resort";
    String M_CATEGORY_DUSHOP = "Du Shop";
    String M_CATEGORY_FOUNTAIN = "Fountain";
    String M_CATEGORY_TRAM = "Tram Station";
    String M_CATEGORY_THEMEPARK = "Theme Park";
    String M_CATEGORY_EMAEBOULVARD = "Emaar Boulevard";
    String M_CATEGORY_RESTAURANT = "Restaurant";
    String M_CATEGORY_SHOPPING_CENTER = "Shopping Center";
    String M_CATEGORY_OFFICES = "Offices";
    String M_CATEGORY_HOTEL = "Hotel";
    String M_CATEGORY_SPORTS_CLUB = "Sports Club";
    String M_CATEGORY_COLLEGE = "College";
    String M_CATEGORY_AIRPORT = "Airport";
    String M_CATEGORY_RESIDENTIAL_FLATS = "Residential Flats";
    String M_CATEGORY_ECONET_SHOP = "Econet Shop";
    String M_CATEGORY_HOSPITAL = "Hospital";
    String M_CATEGORY_UNIVERSITY = "University";
    String M_CATEGORY_SHOPPING_MALL = "Shopping Mall";
    String M_CATEGORY_CITY_CENTER = "City Center";
    String M_CATEGORY_STADIUM = "Stadium";
    String M_CATEGORY_MINE = "Mine";
    String M_CATEGORY_OUTDOOR_RECREATIONAL_PLACE = "Outdoor Recreational Place";
    String M_CATEGORY_BOADER_POST = "Boader Post";
    String M_CATEGORY_ANIMAL_CONSERVATORY = "Animal Conservatory";
    String M_CATEGORY_LTE = "LTE";
    String Request_Timeout = "Request timeout";
    //    String M_CATEGORY_OTHER = "Other";
    String Sharedkey_mandatory = "Shared key is mandatory";
    String countryCode[] = {"93,AF", "355,AL", "213,DZ", "376,AD", "244,AO", "672,AQ", "54,AR", "374,AM", "297,AW", "61,AU", "43,AT", "994,AZ", "973,BH", "880,BD", "375,BY", "32,BE", "501,BZ", "229,BJ", "975,BT", "591,BO", "387,BA", "267,BW", "55,BR", "673,BN", "359,BG", "226,BF", "95,MM", "257,BI", "855,KH", "237,CM", "1,CA", "238,CV", "236,CF", "235,TD", "56,CL", "86,CN", "61,CX", "61,CC", "57,CO", "269,KM", "242,CG", "243,CD", "682,CK", "506,CR", "385,HR", "53,CU", "357,CY", "420,CZ", "45,DK", "253,DJ", "670,TL", "593,EC", "20,EG", "503,SV", "240,GQ", "291,ER", "372,EE", "251,ET", "500,FK", "298,FO", "679,FJ", "358,FI", "33,FR", "689,PF", "241,GA", "220,GM", "995,GE", "49,DE", "233,GH", "350,GI", "30,GR", "299,GL", "502,GT", "224,GN", "245,GW", "592,GY", "509,HT", "504,HN", "852,HK", "36,HU", "91,IN", "62,ID", "98,IR", "964,IQ", "353,IE", "44,IM", "972,IL", "39,IT", "225,CI", "81,JP", "962,JO", "7,KZ", "254,KE", "686,KI", "965,KW", "996,KG", "856,LA", "371,LV", "961,LB", "266,LS", "231,LR", "218,LY", "423,LI", "370,LT", "352,LU", "853,MO", "389,MK", "261,MG", "265,MW", "60,MY", "960,MV", "223,ML", "356,MT", "692,MH", "222,MR", "230,MU", "262,YT", "52,MX", "691,FM", "373,MD", "377,MC", "976,MN", "382,ME", "212,MA", "258,MZ", "264,NA", "674,NR", "977,NP", "31,NL", "599,AN", "687,NC", "64,NZ", "505,NI", "227,NE", "234,NG", "683,NU", "850,KP", "47,NO", "968,OM", "92,PK", "680,PW", "507,PA", "675,PG", "595,PY", "51,PE", "63,PH", "870,PN", "48,PL", "351,PT", "1,PR", "974,QA", "40,RO", "7,RU", "250,RW", "590,BL", "685,WS", "378,SM", "239,ST", "966,SA", "221,SN", "381,RS", "248,SC", "232,SL", "65,SG", "421,SK", "386,SI", "677,SB", "252,SO", "27,ZA", "82,KR", "34,ES", "94,LK", "290,SH", "508,PM", "249,SD", "597,SR", "268,SZ", "46,SE", "41,CH", "963,SY", "886,TW", "992,TJ", "255,TZ", "66,TH", "228,TG", "690,TK", "676,TO", "216,TN", "90,TR", "993,TM", "688,TV", "971,AE", "256,UG", "44,GB", "380,UA", "598,UY", "1,US", "998,UZ", "678,VU", "39,VA", "58,VE", "84,VN", "681,WF", "967,YE", "260,ZM", "263,ZW"};
    int STANDARD_SIZE = 480;
    int BANNER_SIZE = 50;
    String AD_LANGUAGE = "{language}";
    String AD_AGERANGE = "{agerange}";
    String AD_LOCATION = "{location}";
    String AD_GEOGRAPHICS = "{geographic}";
    String AD_LIVEIN = "{livein}";
    String AD_GENDER = "{gender}";
    String AD_RANDOMKEY = "{rn}";

    // ANDSF jar's String
    String range_jio_wifi = "You are in range of JIO Wi-Fi, enjoy seamless services.";

    enum Codenames {
        BASE, BASE_1,
        CUPCAKE,
        DONUT,
        ECLAIR, ECLAIR_MR1, ECLAIR_MR2,
        FROYO,
        GINGERBREAD, GINGERBREAD_MR1,
        HONEYCOMB, HONEYCOMB_MR1, HONEYCOMB_MR2,
        ICE_CREAM_SANDWICH, ICE_CREAM_SANDWICH_MR1,
        JELLY_BEAN, KITKAT, LOLLIPOP, MARSHMALLOW, NOUGAT;

        public static Codenames getCodename() {
            int api = Build.VERSION.SDK_INT;

            switch (api) {
                case 1:

                    return BASE;
                case 2:

                    return BASE_1;
                case 3:

                    return CUPCAKE;
                case 4:

                    return DONUT;
                case 5:

                    return ECLAIR;
                case 6:

                    return ECLAIR_MR1;
                case 7:

                    return ECLAIR_MR2;
                case 8:

                    return FROYO;
                case 9:

                    return GINGERBREAD;
                case 10:

                    return GINGERBREAD_MR1;
                case 11:

                    return HONEYCOMB;
                case 12:

                    return HONEYCOMB_MR1;
                case 13:

                    return HONEYCOMB_MR2;
                case 14:

                    return ICE_CREAM_SANDWICH;
                case 15:

                    return ICE_CREAM_SANDWICH_MR1;
                case 16:

                    return JELLY_BEAN;
                case 17:

                    return JELLY_BEAN;
                case 18:

                    return JELLY_BEAN;
                case 19:

                    return KITKAT;

                case 20:

                    return KITKAT;

                case 21:

                    return LOLLIPOP;
                case 22:

                    return LOLLIPOP;
                case 23:

                    return MARSHMALLOW;
                case 24:

                    return NOUGAT;
                case 25:
                    return NOUGAT;
                default:
                    return NOUGAT;
            }
        }

    }

    // Facebook permission Types
    String[] fb_permission_type = new String[]{"user_birthday", "email", "user_likes", "user_status", "public_profile", "user_friends", "user_relationships"};
    String Pref_IMAGEURL = "Pref_imageUrl";

    //Google Relationship Constant
    HashMap<Integer, String> googleRelationShipHashMap = new HashMap<Integer, String>();

    //SharedPreferance Constants

    public static final String WIFISCANINTERVAL = "WIFISCANINTERVAL";
    public static final String NOTIFICATIONINTERVAL = "NOTIFICATIONINTERVAL";
    public static final String NUMBEROFNOTIFICATION = "NumberOfNotification";
    public static final String CHKWIFITOGGLE = "CHKWIFITOGGLE";
    public static final String ISCHECKEDWIFISCANINTERVAL = "isCheckedWifiScanInterval";
    public static final String ISCHECKEDWIFINOTIFICATION = "isCheckedWifiNotification";
    public static final String PROCESSID = "PROCESSID";
    public static final String DEFAULTCONNECTION = "DEFAULTCONNECTION";
    public static final String ACTIVEPROFILE = "ACTIVEPROFILE";
    public static final String ACTIVECONNECTION = "ACTIVECONNECTION";
    public static final String ISCONNECTED = "ISCONNECTED";
    public static final String ISCONNECTIONTHROUGHTNOTIFICATION = "ISCONNECTIONTHROUGHTNOTIFICATION";
    public static final String ISSIGNALASSISTANCE = "ISSIGNALASSISTANCE";
    //public static final String ISAPPUSED = "ISAPPUSED";
    public static final String NETWORKID = "NETWORKID";
    public static final String HANDLESCREENLOCK = "HANDLESCREENLOCK";
    public static final String TIMEATSTARTUP = "TIMEATSTARTUP";
    public static final String ISDATAUSAGETIMEOUT = "ISDATAUSAGETIMEOUT";
    public static final String ISBETTERYTHRESHOLDENABLE = "ISBETTERYTHRESHOLDENABLE";
    public static final String SESSIONSTARTTIME = "SESSIONSTARTTIME";
    public static final String SESSIONENDTIME = "SESSIONENDTIME";
    public static final String DATAUSAGETIMEOUT = "DATAUSAGETIMEOUT";
    public static final String SESSIONSTARTDATAUSAGE = "SESSIONSTARTDATAUSAGE";
    public static final String CURRENTDATAUSAGE = "CURRENTDATAUSAGE";
    public static final String SESSIONENDDATAUSAGE = "SESSIONENDDATAUSAGE";
    public static final String CURRENTUSEDTIME = "CURRENTUSEDTIME";
    public static final String DATAUSAGEATSTARTUP = "DATAUSAGEATSTARTUP";
    public static final String DOWNLOADDATAATSTARTUP = "DOWNLOADDATAATSTARTUP";
    public static final String UPLOADDATAATSTARTUP = "UPLOADDATAATSTARTUP";
    public static final String CURRENTWIFIDOWNLOADDATA = "CURRENTWIFIDOWNLOADDATA";
    public static final String CURRENTWIFIUPLOADDATA = "CURRENTWIFIUPLOADDATA";
    public static final String PROGRESS_BATTERY = "PROGRESS_BATTERY";
    public static final String REQUIREDSIGNALLEVEL = "REQUIREDSIGNALLEVEL";
    public static final String REQUIREDSIGNALLEVEL_USER = "REQUIREDSIGNALLEVEL_LOW";
    public static final String ISBATTERYLEVELLOW = "ISBATTERYLEVELLOW";
    public static final String AUTOSTART = "AUTOSTART";
    public static final String NETWORKTONOTIFY = "NETWORKTONOTIFY";
    public static final String TOTALUSEDTIME = "TOTALUSEDTIME";
    public static final String TOTALAPPLICATIONDATAUSAGE = "TOTALAPPLICATIONDATAUSAGE";
    public static final String ISINITIALIZED = "ISINITIALIZED";
    public static final String ISDEVICE_DETAILS_SAVED = "ISDEVICEDETAILSSAVED";
    public static final String DIALOGMESSAGE = "DIALOGMESSAGE";
    public static final String DIALOGTITLE = "DIALOGTITLE";
    public static final String NOTIFICAITIONDISPLAYEINSAMELOCATION = "NOTIFICAITIONDISPLAYEINSAMELOCATION";
    public static final String LASTKNOWLOCATIONID = "LASTKNOWLOCATIONID";
    public static final String LASTNEARBYHOTSPOTLOCATION = "LASTNEARBYHOTSPOTLOCATION";
    public static final String NOTIFICATIONTRIGGERTIME = "NOTIFICATIONTRIGGERTIME";
    public static final String SSID = "SSID";
    public static final String PACKAGENAME = "PACKAGE_NAME";
    public static final String LAUNCHAPPMSG = "LAUNCHAPPMSG";

    public static final String PROGRESS_SPEED = "PROGRESS_SPEED";
    public static final String ISSPEEDTHRESHOLDENABLE = "ISSPEEDTHRESHOLDENABLE";

    public static final String ANDSF_POLICY_UPDATE_TIME = "ANDSF_POLICY_UPDATETIME";

    //ADDED BY VIRAT FOR REPORTS

    public static final String FULLSTARTUPTIME = "FULLSTARTUPTIME";
    public static final String ISON = "ISON";

    //ANDSF CONSTANTS
    public static final String ANDSF_ISENABLE = "ANDSF_ISENABLE";
    public static final String ANDSF_INTERVALSET = "ANDSF_INTERVALSET";
    public static final String ANDSF_PULLINTERVAL = "ANDSF_PULLINTERVAL";
    public static final String ANDSF_SERVERCONFIGURED = "ANDSF_SERVERCONFIGURED";
    public static final String ANDSF_SERVER_IP = "ANDSF_SERVER_IP";
    public static final String ANDSF_SERVER_PORT = "ANDSF_SERVER_PORT";
    public static final String ANDSF_SERVER_USERNAME = "ANDSF_SERVER_USERNAME";
    public static final String ANDSF_SERVER_PASSWORD = "ANDSF_SERVER_PASSWORD";
    public static final String ANDSF_SERVER_PROTOCOL = "ANDSF_SERVER_PROTOCOL";
    public static final String ANDSF_SERVER_ALLPOLICY = "ANDSF_SERVER_ALLPOLICY";
    public static final String ANDSF_SERVER_PEFEVIOUSCONNECTION = "ANDSF_SERVER_PEFEVIOUSCONNECTION";
    public static final String ANDSF_SERVER_PEFEVIOUSPROFILE = "ANDSF_SERVER_PEFEVIOUSPROFILE";
    public static final String ANDSF_TTLS_ENABLE = "ANDSF_TTLS_ENABLE";
    public static final String ANDSF_TTLS_USERNAME = "ANDSF_TTLS_USERNAME";
    public static final String ANDSF_TTLS_PASSWORD = "ANDSF_TTLS_PASSWORD";

    public static final String ANDSF_SECURITY_AUTH = "ANDSF_SECURITY_AUTH";

    public static final String ANDSF_DOWNLOADSPEED = "ANDSF_DOWNLOADSPEED";
    public static final String ANDSF_UPLOADSPEED = "ANDSF_UPLOADSPEED";
    public static final String ANDSF_USERPREFERENCE = "userPreference";

    public static final String ANDSF_ISCONNECTED = "is_connected";

    public static final String ANDSF_NOTIFICATION_MSG = "Messages for You!!";

     public static final String minLteRsrpThreshhold = "minLteRsrpThreshhold";
    public static final String minLteSinrThreshhold = "minLteSinrThreshhold";

    public static final String LTE_RSRP_Threshhold = "LTE_RSRP_Threshhold";
    public static final String LTE_SINR_Threshhold = "LTE_SINR_Threshhold";

    //version upgrade module

    public static final String VERSION_ALREADY_UPDATED = "VERSION_ALREADYUPDATED";
    public static final String VERSION_NEW_FROM_SERVER = "VERSION_NEW_FROM_SERVER";
    public static final String VERSION_FORCE_UPGRADE = "VERSION_FORCE_UPGRADE";
    public static final String VERSION_UPDATE_URL = "VERSION_FORCE_URL";

    //location
    // public static final String CURRENT_LATITUDE = "CURRENT_LATITUDE";
    // public static final String CURRENT_LONGITUDE = "CURRENT_LONGITUDE";
    public static final String CELL_ID = "Cell_Id_value";
    public static final String LAC = "lac_value";


    //SMP login
    public static final String VERIFY_OTP = "VERIFY_OTP";
    public static final String OTP_GENERATED = "OTP_GENERATED";
    public static final String MOBILE_NUMBER = "MOBILE_NUMBER";
    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String EMAIL = "EMAIL";
    public static final String SMP_USERNAME = "SMP_USERNAME";

    String GET_LAST_CONNECTION_SSID = "get_last_connection_ssid";

    //========================= EliteWiFIConstants =====================

    /*
     * Security types for the Wifi Connections
     */
    public static final String WIFI_EAPSIM="EAP-SIM";
    public static final String WIFI_EAPTTLS="EAP-TTLS";
    public static final String WIFI_EAPAKA="EAP-AKA";
    public static final String WIFI_WPA="WPA";
    public static final String WIFI_WPAWPA2="WPA/WPA2";
    public static final String WIFI_WPAWPA2PSK="WPA/WPA2 PSK";
    public static final String WIFI_WPA2PSK="WPA2 PSK";
    public static final String WIFI_OPEN="OPEN";
    public static final String WIFI_AKA="EAPAKA";
    public static final String WIFI_SIM="EAPSIM";

    /*
     * PHASE2 AUTHETICATION METHODS FOR EAP-TTLS CONNECTION
     */
    public static final String PHASE2AUTHETICATIONMSCHAPV2="MSCHAPV2";
    public static final  String PHASE2AUTHETICATIONGTC="GTC";
    public static final  String PHASE2AUTHETICATIONMSCHAP="MSCHAP";
    public static final  String PHASE2AUTHETICATIONNONE="NONE";
    public static final  String PHASE2AUTHETICATIONPAP="PAP";


    public static final String RESPONSECODE = "responseCode";
    public static final String RESPONSECODE1 = "ResponseCode";
    public static final String RESPONSEMESSAGE = "responseMessage";
    public static final String RESPONSEMESSAGE1 = "ResponseMessage";
    public static final String REQUESTID = "RequestId";
    public static final String RESPONSEDATA = "responseData";
    public static final String RESPONSEDATA1 = "ResponseData";
    public static final int SUCCESSCODE = 1;
    public static final int FAILURECODE = -1;

    public static final String KEY_SSID_NAME="KEY_SSID_NAME";
    public static final String KEY_IS_NEARBY="KEY_IS_NEARBY";
    public static final String KEY_IS_CONNECTED="KEY_IS_CONNECTED";
    public static final String KEY_HAS_INTERNET="KEY_HAS_INTERNET";
    public static final String KEY_HOTSPOT_NAME="KEY_HOTSPOT_NAME";
    public static final String KEY_PING_URL="KEY_PING_URL";
    public static final String VALUE_SERVER_URL = "VALUE_SERVER_URL";
    public static final String VALUE_OPERATOR_NAME="VALUE_OPERATOR_NAME";
    public static final String KEY_HD_HOTSPOTNAME="KEY_HD_HOTSPOTNAME";
    public static final String KEY_UPLOAD_SPEED = "uploadSpeed";
    public static final String KEY_DOWNLOAD_SPEED="downloadSpeed";
    public static final String VALUE_DOWNLOADSPEED_URL="VALUE_DOWNLOADSPEED_URL";
    public static final String KEY_LOCATION="KEY_LOCATION";
    public static final String KEY_REMAINING_QUOTATIME="KEY_REMAINING_QUOTATIME";
    public static final String KEY_REMAINING_QUOTAVOLUME="KEY_REMAINING_QUOTAVOLUME";



    //General
    public static final int FAILURE_CODE_APPLICATIONIDWRONG=100;
    public static final String FAILURE_MESSAGE_APPLICATIONIDWRONG="Application ID is not Valid";
    //Autodetect
    public static final int FAILURE_CODE_NOWIFI1=101;
    public static final String FAILURE_MESSAGE_NOWIFI1="There is no any operator Wi-Fi in range";


    public static final String SUCCESS_MESSAGE_AUTODETECT="Successfully detected Wi-Fi";

    public static final int FAILURE_CODE_FAILTOGET=102;
    public static final String FAILURE_MESSAGE_FAILTOGET="Fail to get Wi-Fi List";

    public static final int FAILURE_CODE_FAILTODETECT=103;
    public static final String FAILURE_MESSAGE_FAILTODETECT="Fail to auto detect Wi-Fi.";

    //QOS
    public static final int FAILURE_CODE_NOTCONNECTEDWIFI=201;
    public static final String FAILURE_MESSAGE_NOTCONNECTEDWIFI="Wi-Fi is not connected";
    public static final int FAILURE_CODE_FAILTOGETSPEED=202;
    public static final String FAILURE_MESSAGE_FAILTOGETSPEED ="Fail get Wi-Fi upload download speed";

    //Addpersoanl
    public static final int FAILURE_CODE_ALREADYEXIST=301;
    public static final String FAILURE_MESSAGE_ALREADYEXIST="Same Name Wi-Fi information already exist";
    public static final int FAILURE_CODE_FAILTOADD=302;
    public static final String FAILURE_MESSAGE_FAILTOADD="Fail to add PersonalWifi";
    public static final String SUCCESS_MESSAGED_WIFIADDED="Wi-Fi added successfully";

    //updateWifiPriority
    public static final int FAILURE_CODE_UPDATEFAIL=401;
    public static final String FAILURE_MESSAGE_UPDATEFAIL="Fail to update WiFI Priority";

    //Autologin
    public static final int FAILURE_CODE_PARAMMISSING=501;
    public static final String FAILURE_MESSAGE_PARAMMISSING="Request parameter is missing";
    public static final int FAILURE_CODE_OFFLOADMISSING=502;
    public static final String FAILURE_MESSAGE_OFFLOADMISSING="Offload parameter is missing";
    public static final int FAILURE_CODE_PHONEMISSING=503;
    public static final String FAILURE_MESSAGE_PHONEMISSING="Phone parameter is missing or invalid";
    public static final int FAILURE_CODE_OTPMISSING=504;
    public static final String FAILURE_MESSAGE_OTPMISSING="OTP parameter is missing";
    public static final int FAILURE_CODE_PACKAGEMISSING=505;
    public static final String FAILURE_MESSAGE_PACKAGEMISSING="packageId parameter is missing";
    public static final int FAILURE_CODE_CHANNELMISSING=506;
    public static final String FAILURE_MESSAGE_CHANNELMISSING="channel parameter is missing or not valid value";

    public static final int FAILURE_CODE_NOWIFI2=507;
    public static final String FAILURE_MESSAGE_NOWIFI2="No any wifi in range";

    public static final int FAILURE_CODE_NOSUBSCRIBER=508;
    public static final String FAILURE_MESSAGE_NOSUBSCRIBER="Not a operator";
    public static final int FAILURE_CODE_FAILTOCONNECT=509;
    public static final String FAILURE_MESSAGE_FAILTOCONNECT="Fail to connect wifi";


    public static final int FAILURE_CODE_NOOPERATORWIFI=510;
    public static final String FAILURE_MESSAGE_NOOPERATORWIFI="There is no any operator Wi-Fi in range";
    public static final int FAILURE_CODE_NOPERSONALWIFI=511;
    public static final String FAILURE_MESSAGE_NOPERSONALWIFI="There is no any personal Wi-Fi in range";

    public static final int FAILURE_CODE_FAILTOLOGIN=512;
    public static final String FAILURE_MESSAGE_FAILTOLOGIN="fail to login to internet";


    public static final int FAILURE_CODE_REQUESTITMEOUT=513;
    public static final String FAILURE_MESSAGE_REQUESTITMEOUT="Request Time out";

    public static final int FAILURE_CODE_PGLOGIN=901;
    public static final String FAILURE_PGLOGIN_REQUESTITMEOUT="Request Time out";

    public static final int FAILURE_CODE_PGLOGOOUT=1001;
    public static final String FAILURE_PGLOGOOUT_REQUESTITMEOUT="Request Time out";

    //Get Upload Download Speed Error and success messages and constants
    public static final int FAILURE_CODE_SSIDEMPTY=2001;
    public static final String FAILURE_MESSAGE_SSIDEMPTY="SSID name is missing";

    public static final int FAILURE_CODE_SSIDNOTCONNECTED=2002;
    public static final String FAILURE_MESSAGE_SSIDNOTCONNECTED="SSID is not connected";

    public static final int FAILURE_CODE_INTERNETNOTAVAILABLE=2003;
    public static final String FAILURE_MESSAGE_INTERNETNOTAVAILABLE="SSID connected, but Internet is not available";

    public static final int FAILURE_CODE_NETWORKSPEED=2004;
    public static final String FAILURE_MESSAGE_NETWORKSPEED="Fail on getting Download and Upload speed";

    public static final int SUCCESS_CODE_NETWORKSPEED=2005;
    public static final String SUCCESS_MESSAGE_NETWORKSPEED="Success on getting Download and Upload speed";

    public static final String DOWNLOAD_SPEED = "Download_speed";
    public static final String UPLOAD_SPEED = "Upload_speed";

    //Stand alone SDK invocation REQUEST ID
    public static final int ADD_PERSONAL_WIFI = 1;
    public static final int AUTO_DETECT = 2;
    public static final int AUTO_LOGIN_FREE_WIFI = 3;
    public static final int CONNECT_TO_WIFI = 4;
    public static final int AUTO_LOGIN_OFFLOAD = 5;
    public static final int DELETEWIFIINFORMATION = 6;
    public static final int UPDATEWIFIPRIORITY = 7;
    public static final int GETQOSFORWIFI = 8;



    public static final int PGLOGIN = 9;
    public static final int PGLOGOOUT = 10;


    String UPLOAD = "up";
    String DOWNLOAD = "down";
    String SYNCTIME = "syncTime";
    String LATENCY_SYNC_TIME = "latency_sync_time";
    String LATENCY = "latency";

}