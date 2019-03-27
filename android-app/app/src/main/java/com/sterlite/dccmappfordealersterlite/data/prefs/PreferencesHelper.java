
package com.sterlite.dccmappfordealersterlite.data.prefs;


import java.util.Map;

public interface PreferencesHelper {

    String get(String key, String defaultValue);

    void set(String key, String value);

    void setBoolean(String key, boolean value);

    boolean getBoolean(String key, boolean defaultValue);

    void setint(String key, int value);

    int getint(String key, int defaultValue);

    void clear();

    void remove(String key);

    void setAppPref(String key, String value);

    String getAppPref(String key, String defaultValue);

    void setBooleanAppPref(String key, boolean value);

    boolean getBooleanAppPref(String key, boolean defaultValue);

   void saveMap(String key,Map<String,String> inputMap);

    Map<String,String> loadMap(String key1);
    }
