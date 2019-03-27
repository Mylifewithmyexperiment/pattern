package com.elitecorelib.core.dao;

public interface DBQueryFieldConstants {

    //DBHelper Common Constants
    String DBNAME = "easyconnect.db";
    public static final String IS_LOCAL = "isLocal";
    //DBHelper Connection  table fields Constants
    String TABLE_NAME_WIFI_INFORMATION = "wifiInformation";
    String WIFI_INFO_ID = "id";
    String SSID_NAME = "ssidName";
    String PASSWORD = "password";
    String SECURITY_METHOD = "securityMethod";
    String PRIORITY = "priority";
    String AUTOLOGIN = "autoLogin";
    String IS_OPERATOR_WIFI = "isOperatorWifi";
    String IDENTITY = "identity";
    String AUTHENTICATION_METHOD = "authenticationMethod";
    String PHASE2_AUTHENTICATION = "phase2Authentication";
    String DELTEONTURNOFFWIFI = "delteOnTurnOffWiFi";
    String AUTOREMOVEALTIMERINTERVAL = "autoRemovealTimerInterval";
    String EVENTID = "eventId";
    String DATETIME = "dateTime";
    String EVENTVALUE = "eventValue";
    String PARAM1 = "param1";
    String PARAM2 = "param2";
    String PARAM3 = "param3";
    String TABLE_NAME_ANALYTIC = "analytic";
    String ID = "id";


    //DBHelper Connection  table query Constants
    String QUERY_CREATE_TABLE_WIFI_INFORMATION = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_WIFI_INFORMATION + "( " + WIFI_INFO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + SSID_NAME + " TEXT UNIQUE," + PRIORITY + " INT," + IS_OPERATOR_WIFI + " CHAR(1)," + AUTOLOGIN + " CHAR(1),"
            + SECURITY_METHOD + " TEXT," + IDENTITY + " TEXT," + PASSWORD + " TEXT," + AUTHENTICATION_METHOD + " TEXT, "
            + PHASE2_AUTHENTICATION + " TEXT," + DELTEONTURNOFFWIFI + " CHAR(1) ," + AUTOREMOVEALTIMERINTERVAL + " INT );";

    //DBHelper Connection  table query Constants
    String QUERY_CREATE_TABLE_ANALYTIC = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ANALYTIC + "( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + EVENTID + " INT," + DATETIME + " LONG," + EVENTVALUE + " TEXT,"
            + PARAM1 + " TEXT," + PARAM2 + " TEXT," + PARAM3 + " TEXT);";


    // new tables adds --------------------------------------------

    //DBHelper Connection  table fields Constants
    public static final String SSID = "ssid";
    public static final String SECURITY = "security";
    public static final String ENCRIPTION = "encryption";
    public static final String AUTHENTICATION = "authentication";
    public static final String PHASE2_AUTHENTICATION_Connection = "phase2authentication";
//    public static final String IDENTITY = "identity";
//    public static final String PASSWORD = "password";
    public static final String SHOW_PASSWORD = "showPassword";
    public static final String IS_DEFAULT = "isDefault";
    public static final String IS_ACTIVE = "isActive";
    public static final String IS_OUTOFRANGE = "isOutOfRange";
    public static final String TABLENAME_CONNECTION = "connection";

    //DBHelper Connection  table query Constants
    public static final String QUERY_CREATE_TABLE_CONNECTION = "CREATE TABLE IF NOT EXISTS " + TABLENAME_CONNECTION + "(" + SSID + " TEXT PRIMARY KEY," + SECURITY
            + " TEXT," + ENCRIPTION + " TEXT," + AUTHENTICATION + " TEXT," + PHASE2_AUTHENTICATION_Connection + " TEXT," + IDENTITY + " TEXT," + PASSWORD
            + " TEXT," + SHOW_PASSWORD + " INTEGER," + IS_DEFAULT + " INTEGER," + IS_ACTIVE + " INTEGER," + IS_OUTOFRANGE + " INTEGER," + IS_LOCAL + " INTEGER" + ");";


    //DBHelperLocation table fields constants
    public static final String LOCATION_ID = "locationId";
    public static final String LOCATION_NAME = "locationName";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String TABLENAME_LOCATION = "location";
    public static final String RADIUS = "radius";
    public static final String CATEGORY = "category";
    public static final String LOCATIONDESCRIPTION = "locationDescription";
    public static final String ZONEID = "zoneid";
    public static final String PARAM1_location = "param1";
    public static final String PARAM2_location = "param2";
    public static final String OFFLINEMESSAGE = "offlineMessage";
    public static final String AGENTID = "agentId";

    //DBHelper Location table query constants
    public static final String QUERY_CREATETABLE_LOCATION = "CREATE TABLE IF NOT EXISTS " + TABLENAME_LOCATION + "(" + LOCATION_ID + " REAL PRIMARY KEY,"
            + LOCATION_NAME + " TEXT," + LATITUDE + " REAL," + LONGITUDE + " REAL," + RADIUS + " REAL," + CATEGORY + " TEXT," + LOCATIONDESCRIPTION + " TEXT,"
            + PARAM1_location + " TEXT," + PARAM2_location + " TEXT," + ZONEID + " INTEGER," + OFFLINEMESSAGE + " TEXT,"+ AGENTID + " TEXT" +");";


    //DBHELPER LOCATION RELATION TABLE FIELDS CONSTANTS
    public static final String PROFILE_NAME = "profileName";
    public static final String TABLENAME_LOCATIONRELATION = "locationrelation";

    //DBHelper Location table query constants
    public static final String QUERY_CREATE_TABLE_LOCATIONRELATION = "CREATE TABLE IF NOT EXISTS " + TABLENAME_LOCATIONRELATION + "(" + PROFILE_NAME + " TEXT," + LOCATION_NAME
            + " TEXT);";


    //DBHELPER PROFILE TABLE FIELDS CONSTANTS
    public static final String TABLENAME_PROFILE = "profile";
    public static final String NAME = "name";

    //DBHELPER PROFILE TABLE query CONSTANTS
//    public static final String QUERY_CREATE_TABLE_PROFILE = "CREATE TABLE IF NOT EXISTS " + TABLENAME_PROFILE + "(" + NAME + " TEXT PRIMARY KEY," + IS_DEFAULT
//            + " NUMERIC," + IS_ACTIVE + " NUMERIC" + ");";
    public static final String QUERY_CREATE_TABLE_PROFILE = "CREATE TABLE IF NOT EXISTS " + TABLENAME_PROFILE + "(" + PROFILE_NAME + " TEXT PRIMARY KEY," + IS_DEFAULT
            + " INTEGER," + IS_ACTIVE + " INTEGER," + IS_LOCAL + " INTEGER" + ");";

    //DBHELPER REGISTRATION  TABLE FIELDS CONSTANTS

    public static final String TABLENAME_REGISTRATION = "registration";
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String USERNAME = "userName";
    public static final String EMPLOYER = "employer";
    public static final String POSITION = "position";
    public static final String LOCATION = "location";
    public static final String BIRTHDATE = "birthDate";
    public static final String LANGUAGE = "language";
    public static final String LINK = "link";
    public static final String SPORT = "sport";
    public static final String EDUCATION = "education";
    public static final String SCHOOLNAME = "schoolName";
    public static final String TIMEZONE = "timeZone";
    public static final String GENDER = "gender";
    public static final String AGERANGE = "ageRange";
    public static final String IMSI = "imsi";
    public static final String SIMOPERATOR = "simOperator";
    public static final String DEVICE = "device";
    public static final String IMEI = "imei";
    public static final String BRAND = "brand";
    public static final String MANUFACTURER = "manufacturer";
    public static final String MODEL = "model";
    public static final String NETWORKOPERATOR = "networkOperator";
    public static final String SOFTWARE = "software";
    public static final String RELATIONSHIPSTATUS = "relationshipStatus";
    public static final String OPERATINGSYSTEM = "operatingSystem";
    public static final String REGISTRATIONDATE = "registrationDate";
    public static final String REGISTERLINK = "registerLink";
    public static final String LASTUSEDTIME = "lastUsedTime";
    public static final String UNINSTALLDATE = "uninstallDate";
    public static final String STATUS = "status";
    public static final String REGISTERWITH = "registerWith";
    public static final String REGISTEREDDEVICEID = "registeredDeviceId";
    public static final String CITY = "city";
    public static final String STATE = "state";
    public static final String COUNTRY = "country";
    public static final String POSTALCODE = "postalCode";
    public static final String GEOADDRESS = "geoAddress";
    public static final String IMAGEURL = "imageURL";

    //DBHELPER REGISTRATION TABLE query
    public static final String QUERY_CREATE_TABLE_REGISTRATION = "CREATE TABLE IF NOT EXISTS " + TABLENAME_REGISTRATION + "(" + IMEI + " TEXT PRIMARY KEY," + FIRSTNAME
            + " TEXT," + LASTNAME + " TEXT," + USERNAME + " TEXT," + EMPLOYER + " TEXT," + POSITION + " TEXT," + LOCATION + " TEXT," + BIRTHDATE + " TEXT," + LANGUAGE + " TEXT," + LINK + " TEXT,"
            + SPORT + " TEXT," + EDUCATION + " TEXT," + SCHOOLNAME + " TEXT," + TIMEZONE + " TEXT," + GENDER + " TEXT," + AGERANGE + " TEXT," + IMSI + " TEXT," + SIMOPERATOR + " TEXT,"
            + DEVICE + " TEXT," + BRAND + " TEXT," + MANUFACTURER + " TEXT," + MODEL + " TEXT," + NETWORKOPERATOR + " TEXT," + SOFTWARE + " TEXT," + RELATIONSHIPSTATUS + " TEXT,"
            + OPERATINGSYSTEM + " TEXT," + REGISTRATIONDATE + " TEXT," + REGISTERLINK + " TEXT," + LASTUSEDTIME + " TEXT," + UNINSTALLDATE + " TEXT," + STATUS + " TEXT," + REGISTERWITH + " TEXT," + REGISTEREDDEVICEID + " TEXT," + CITY + " TEXT," + STATE + " TEXT," + COUNTRY + " TEXT," + POSTALCODE + " REAL," + GEOADDRESS + " TEXT" + ");";


    //DBHelper Relation Table query constants
    public static final String TABLENAME_RELATION = "relation";
    public static final String CONNECTION_NAME = "connectionName";

    //DBHelper Relation Table query
    public static final String QUERY_CREATE_TABLE_RELATION = "CREATE TABLE IF NOT EXISTS " + TABLENAME_RELATION + "(" + PROFILE_NAME + " TEXT," + SSID
            + " TEXT" + ");";

    //DBHelper SSID Table Constants
    public static final String TABLENAME_SSID = "ssid";
    public static final String ISREGEX = "regex";

    //DBHelper SSID Table query
    public static final String QUERY_CREATETABLE_SSID = "CREATE TABLE IF NOT EXISTS " + TABLENAME_SSID + "(" + SSID + " TEXT PRIMARY KEY," + ISREGEX
            + " TEXT" + ");";


    //DBHelper usage detail
    public static final String TABLENAME_USAGE_DETAIL = "usage_detail";
    public static final String SSID_NAME_USAGE = "ssid";
    //TEXT as ISO8601 strings ("YYYY-MM-DD HH:MM:SS.SSS").
    public static final String SESSION_START_TIME = "sessionStarttime";
    public static final String SESSION_END_TIME = "sessionEndtime";
    public static final String SESSION_UPLOAD_DATA = "sessionUploadData";
    public static final String SESSION_DOWNLOAD_DATA = "sessionDownloadData";
    public static final String SESSION_TOTAL_DATA = "sessionTotalData";
    public static final String QUERY_CREATETABLE_USAGE_DETAIL = "CREATE TABLE IF NOT EXISTS " + TABLENAME_USAGE_DETAIL + "(" + SSID_NAME_USAGE + " TEXT ," + SESSION_START_TIME
            + " TEXT," + SESSION_END_TIME + " TEXT," + SESSION_UPLOAD_DATA + " TEXT," + SESSION_DOWNLOAD_DATA + " TEXT," + SESSION_TOTAL_DATA + " TEXT" + ");";


    //DBHelper ad Mapping
    public static final String TABLENAME_AD_MAPPING = "ad_mapping";
    public static final String SCREENLOCATION = "screenLocation";
    public static final String SCREENNAME = "screenName";
    public static final String INVOCATIONCODE = "invocationCode";
    public static final String DEVICECATAGORY = "deviceCatagory";
    public static final String QUERY_CREATETABLE_AD_MAPPING = "CREATE TABLE IF NOT EXISTS " + TABLENAME_AD_MAPPING + "(" + SCREENLOCATION + " TEXT ," + SCREENNAME
            + " TEXT," + INVOCATIONCODE + " TEXT," + DEVICECATAGORY + " TEXT" + ");";


    //DBHELPER sync Data class
    public static final String TABLENAME_SYNCDATA_MAPPING = "SYNCDATA";
    public static final String SYNCDATAID = "syncDataId";
    public static final String MODULENAME = "moduleName";
    public static final String MODIFIEDDATE = "modifiedDate";
    public static final String QUERY_CREATETABLE_SYNCDATA_MAPPING = "CREATE TABLE IF NOT EXISTS " + TABLENAME_SYNCDATA_MAPPING + "(" + SYNCDATAID + " INTEGER ," + MODULENAME
            + " TEXT," + MODIFIEDDATE + " TEXT" + ");";

}
