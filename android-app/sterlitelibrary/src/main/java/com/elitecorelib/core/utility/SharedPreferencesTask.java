package com.elitecorelib.core.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.LibraryApplication;

public class SharedPreferencesTask {

    Context context = LibraryApplication.getLibraryApplication().getLibraryContext();


    public void saveInt(String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CoreConstants.APPLICATION_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getIntDefaultMinus1(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CoreConstants.APPLICATION_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, -1);
    }

    public int getInt(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CoreConstants.APPLICATION_PREFERENCE,
                Context.MODE_PRIVATE);

        if (sharedPreferences.contains(key)) {
            return sharedPreferences.getInt(key, 0);
        } else {
            return 0;
        }
    }

    public void saveLong(String key, long value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CoreConstants.APPLICATION_PREFERENCE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLong(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CoreConstants.APPLICATION_PREFERENCE,
                Context.MODE_PRIVATE);

        if (sharedPreferences.contains(key)) {
            return sharedPreferences.getLong(key, 0);
        } else {
            return 0;
        }
    }

    public void saveBoolean(String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CoreConstants.APPLICATION_PREFERENCE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CoreConstants.APPLICATION_PREFERENCE,
                Context.MODE_PRIVATE);
        if (sharedPreferences.contains(key)) {
            return sharedPreferences.getBoolean(key, false);
        } else {
            return true;
        }
    }

    public boolean getBooleanFirstFalse(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CoreConstants.APPLICATION_PREFERENCE,
                Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public void saveString(String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CoreConstants.APPLICATION_PREFERENCE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CoreConstants.APPLICATION_PREFERENCE,
                Context.MODE_PRIVATE);
        String str = "";
        if (sharedPreferences.contains(key)) {
            str = sharedPreferences.getString(key, "");
        }
        return str;
    }

    public void clearPreferences() {
        context.getSharedPreferences(CoreConstants.APPLICATION_PREFERENCE, 0).edit().clear().commit();
    }

    public void storeLong(String key, long value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(CoreConstants.APPLICATION_PREFERENCE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void removePreference(String key) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(CoreConstants.APPLICATION_PREFERENCE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public long retrieveLong(String key) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(CoreConstants.APPLICATION_PREFERENCE,
                Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0);
    }
}
