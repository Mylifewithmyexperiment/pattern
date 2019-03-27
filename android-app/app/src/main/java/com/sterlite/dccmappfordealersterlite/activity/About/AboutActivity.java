package com.sterlite.dccmappfordealersterlite.activity.About;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.model.Language;

import java.util.List;

public class AboutActivity extends PreferenceActivity  {
    SharedPreferences sharedPreferences;
  /*  public static final String
            KEY_PREF_PRE_TO_POST = "perform_pre2post";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_about);
        addPreferencesFromResource(R.xml.preference_about);
      /*  SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        Boolean switchPref = sharedPref.getBoolean
                (AboutActivity.KEY_PREF_PRE_TO_POST, false);
        Log.e("value",switchPref+" ");
*/
      setPreferences();
    }

    private void setPreferences() {
        Preference preference = findPreference(AppPreferencesHelper.KEY_PREF_PRE_TO_POST);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences = this.getSharedPreferences(AppPreferencesHelper.PRE_TO_POST_SETTINGS, MODE_PRIVATE);
        preference.setSummary("Value: " + sharedPreferences.getString(AppPreferencesHelper.KEY_PREF_PRE_TO_POST, Constants.PRE_TO_POST_DEFAULT));
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // TODO Auto-generated method stub
                SharedPreferences sharedPreferences = getSharedPreferences(AppPreferencesHelper.PRE_TO_POST_SETTINGS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(AppPreferencesHelper.KEY_PREF_PRE_TO_POST, newValue.toString());
                editor.commit();
                preference.setDefaultValue(newValue);
                preference.setSummary("Value: " + newValue);
                boolean pretopost;
                if (newValue.toString().equalsIgnoreCase("false"))
                {
                    pretopost=false;
                }
                else {
                    pretopost=true;

                }
                DCCMDealerSterlite.getDataManager().setBooleanAppPref(AppPreferencesHelper.KEY_PREF_PRE_TO_POST, pretopost);

                return true;
            }
        });

    }

}
