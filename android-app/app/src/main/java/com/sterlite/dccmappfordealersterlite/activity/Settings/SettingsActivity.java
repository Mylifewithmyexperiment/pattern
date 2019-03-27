package com.sterlite.dccmappfordealersterlite.activity.Settings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;

public class SettingsActivity extends PreferenceActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_settings);
        String theme=DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.APP_THEME, Constants.DEFAULT_APP_THEME);
        Log.e("Default Theme:::",theme);

        if (theme.equalsIgnoreCase(Constants.APP_THEME))
        {
            setTheme(R.style.Telkomsel);
        }
        else {
            setTheme(R.style.Sterlite);

        }
        addPreferencesFromResource(R.xml.preferences_settings);
        setPreferences();

    }

    private void setPreferences() {
        Preference preference = findPreference(AppPreferencesHelper.BSS_ADAPTER_IP);
         sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
         sharedPreferences = this.getSharedPreferences(AppPreferencesHelper.BSS_SETTINGS, MODE_PRIVATE);
        preference.setSummary("BSS Adapter IP : " + sharedPreferences.getString(AppPreferencesHelper.BSS_ADAPTER_IP, Constants.BSS_Adapter_IP));
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // TODO Auto-generated method stub
                SharedPreferences sharedPreferences = getSharedPreferences(AppPreferencesHelper.BSS_SETTINGS,MODE_PRIVATE );
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(AppPreferencesHelper.BSS_ADAPTER_IP, newValue.toString());
                editor.commit();
                preference.setDefaultValue(newValue);
                preference.setSummary("BSS Adapter IP : " + newValue);
                DCCMDealerSterlite.getDataManager().setAppPref(AppPreferencesHelper.BSS_ADAPTER_IP, newValue.toString());
                return true;
            }
        });


        preference = findPreference(AppPreferencesHelper.BSS_ADAPTER_PORT);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
         sharedPreferences = this.getSharedPreferences(AppPreferencesHelper.BSS_SETTINGS, MODE_PRIVATE);
        preference.setSummary("BSS Adapter PORT : " + sharedPreferences.getString(AppPreferencesHelper.BSS_ADAPTER_PORT, Constants.BSS_Adapter_PORT));
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // TODO Auto-generated method stub
                SharedPreferences sharedPreferences = getSharedPreferences(AppPreferencesHelper.BSS_SETTINGS,MODE_PRIVATE
                );
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(AppPreferencesHelper.BSS_ADAPTER_PORT, newValue.toString());
                editor.commit();
                preference.setDefaultValue(newValue);
                preference.setSummary("BSS Adapter PORT: " + newValue);
                DCCMDealerSterlite.getDataManager().setAppPref(AppPreferencesHelper.BSS_ADAPTER_PORT, newValue.toString());
               // Toast.makeText(SettingsActivity.this,"Thanks1", Toast.LENGTH_LONG).show();
                return true;
            }
        });


        preference = findPreference(AppPreferencesHelper.BSS_ADAPTER_SECURITY);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
         sharedPreferences = this.getSharedPreferences(AppPreferencesHelper.BSS_SETTINGS, MODE_PRIVATE);
        preference.setSummary("Security : " + sharedPreferences.getString(AppPreferencesHelper.BSS_ADAPTER_SECURITY, Constants.BSS_SECURITY));
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // TODO Auto-generated method stub
                SharedPreferences sharedPreferences = getSharedPreferences(AppPreferencesHelper.BSS_SETTINGS,MODE_PRIVATE
                );
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(AppPreferencesHelper.BSS_ADAPTER_SECURITY, newValue.toString());
                editor.commit();
                preference.setDefaultValue(newValue);
                preference.setSummary("Security : " + newValue);
                DCCMDealerSterlite.getDataManager().setAppPref(AppPreferencesHelper.BSS_ADAPTER_SECURITY, newValue.toString());
                return true;
            }
        });




        preference = findPreference(AppPreferencesHelper.DCCM_IP);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences = this.getSharedPreferences(AppPreferencesHelper.DCCM_SETTINGS, MODE_PRIVATE);
        preference.setSummary("DCCM IP : " + sharedPreferences.getString(AppPreferencesHelper.DCCM_IP, Constants.DCCM_IP));
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // TODO Auto-generated method stub
                SharedPreferences sharedPreferences = getSharedPreferences(AppPreferencesHelper.DCCM_SETTINGS,MODE_PRIVATE
                );
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(AppPreferencesHelper.DCCM_IP, newValue.toString());
                editor.commit();
                preference.setDefaultValue(newValue);
                Log.e("TEST IP1",DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.DCCM_IP,Constants.DCCM_IP));

                preference.setSummary("DCCM IP : " + newValue);
                DCCMDealerSterlite.getDataManager().setAppPref(AppPreferencesHelper.DCCM_IP, newValue.toString());
                Log.e("TEST IP2",DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.DCCM_IP,Constants.DCCM_IP));

                return true;
            }
        });


        preference = findPreference(AppPreferencesHelper.DCCM_PORT);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
         sharedPreferences = this.getSharedPreferences(AppPreferencesHelper.DCCM_SETTINGS, MODE_PRIVATE);
        preference.setSummary("DCCM PORT : " + sharedPreferences.getString(AppPreferencesHelper.DCCM_PORT, Constants.DCCM_PORT));
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // TODO Auto-generated method stub
                SharedPreferences sharedPreferences = getSharedPreferences(AppPreferencesHelper.DCCM_SETTINGS,MODE_PRIVATE
                );
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(AppPreferencesHelper.DCCM_PORT, newValue.toString());
                editor.commit();
                preference.setDefaultValue(newValue);
                preference.setSummary("DCCM PORT: " + newValue);
                DCCMDealerSterlite.getDataManager().setAppPref(AppPreferencesHelper.DCCM_PORT, newValue.toString());

                return true;
            }
        });


        preference = findPreference(AppPreferencesHelper.DCCM_SECURITY);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
         sharedPreferences = this.getSharedPreferences(AppPreferencesHelper.DCCM_SETTINGS, MODE_PRIVATE);
        preference.setSummary("Security : " + sharedPreferences.getString(AppPreferencesHelper.DCCM_SECURITY, Constants.DCCM_SECURITY));
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // TODO Auto-generated method stub
                SharedPreferences sharedPreferences = getSharedPreferences(AppPreferencesHelper.DCCM_SETTINGS,MODE_PRIVATE
                );
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(AppPreferencesHelper.DCCM_SECURITY, newValue.toString());
                editor.commit();
                preference.setDefaultValue(newValue);
                preference.setSummary("Security : " + newValue);
                DCCMDealerSterlite.getDataManager().setAppPref(AppPreferencesHelper.DCCM_SECURITY, newValue.toString());
                return true;
            }
        });

        //

        preference = findPreference(AppPreferencesHelper.CRM_IP);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences = this.getSharedPreferences(AppPreferencesHelper.CRM_SETTINGS, MODE_PRIVATE);
        preference.setSummary("CRM IP : " + sharedPreferences.getString(AppPreferencesHelper.CRM_IP, Constants.CRM_IP));
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // TODO Auto-generated method stub
                SharedPreferences sharedPreferences = getSharedPreferences(AppPreferencesHelper.CRM_SETTINGS,MODE_PRIVATE
                );
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(AppPreferencesHelper.CRM_IP, newValue.toString());
                editor.commit();
                preference.setDefaultValue(newValue);
                Log.e("TEST IP1",DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.CRM_IP,Constants.CRM_IP));

                preference.setSummary("CRM IP : " + newValue);
                DCCMDealerSterlite.getDataManager().setAppPref(AppPreferencesHelper.CRM_IP, newValue.toString());
                Log.e("TEST IP2",DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.CRM_IP,Constants.CRM_IP));

                return true;
            }
        });


        preference = findPreference(AppPreferencesHelper.CRM_PORT);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences = this.getSharedPreferences(AppPreferencesHelper.CRM_SETTINGS, MODE_PRIVATE);
        preference.setSummary("CRM PORT : " + sharedPreferences.getString(AppPreferencesHelper.CRM_PORT, Constants.CRM_PORT));
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // TODO Auto-generated method stub
                SharedPreferences sharedPreferences = getSharedPreferences(AppPreferencesHelper.CRM_SETTINGS,MODE_PRIVATE
                );
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(AppPreferencesHelper.CRM_PORT, newValue.toString());
                editor.commit();
                preference.setDefaultValue(newValue);
                preference.setSummary("CRM PORT: " + newValue);
                DCCMDealerSterlite.getDataManager().setAppPref(AppPreferencesHelper.CRM_PORT, newValue.toString());

                return true;
            }
        });


        preference = findPreference(AppPreferencesHelper.CRM_SECURITY);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences = this.getSharedPreferences(AppPreferencesHelper.CRM_SETTINGS, MODE_PRIVATE);
        preference.setSummary("Security : " + sharedPreferences.getString(AppPreferencesHelper.CRM_SECURITY, Constants.CRM_SECURITY));
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // TODO Auto-generated method stub
                SharedPreferences sharedPreferences = getSharedPreferences(AppPreferencesHelper.CRM_SETTINGS,MODE_PRIVATE
                );
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(AppPreferencesHelper.CRM_SECURITY, newValue.toString());
                editor.commit();
                preference.setDefaultValue(newValue);
                preference.setSummary("Security : " + newValue);
                DCCMDealerSterlite.getDataManager().setAppPref(AppPreferencesHelper.CRM_SECURITY, newValue.toString());
                return true;
            }
        });




        preference = findPreference(AppPreferencesHelper.APP_THEME);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
            sharedPreferences = this.getSharedPreferences(AppPreferencesHelper.THEME_SETTINGS, MODE_PRIVATE);
        preference.setSummary("APP THEME : " + sharedPreferences.getString(AppPreferencesHelper.APP_THEME, Constants.DEFAULT_APP_THEME));
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // TODO Auto-generated method stub
                SharedPreferences sharedPreferences = getSharedPreferences(AppPreferencesHelper.THEME_SETTINGS,MODE_PRIVATE
                );
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(AppPreferencesHelper.APP_THEME, newValue.toString());
                editor.commit();
                preference.setDefaultValue(newValue);
                preference.setSummary("APP THEME : " + newValue);
                DCCMDealerSterlite.getDataManager().setAppPref(AppPreferencesHelper.APP_THEME, newValue.toString());
                Log.e("APP THEME ",DCCMDealerSterlite.getDataManager().getAppPref(AppPreferencesHelper.APP_THEME,Constants.DEFAULT_APP_THEME));

                if (newValue.toString().equalsIgnoreCase(Constants.APP_THEME))
                   setTheme(R.style.Telkomsel);
               else
                   setTheme(R.style.Sterlite);
                return true;
            }
        });

        //    <color name="color_palettes_500">#389cc3</color>
        //    <color name="color_palettes_400">#389cc3</color>





    }
}
