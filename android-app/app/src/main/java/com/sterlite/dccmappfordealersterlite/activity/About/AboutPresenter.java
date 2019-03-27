package com.sterlite.dccmappfordealersterlite.activity.About;

import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.Utils.DummyLists;
import com.sterlite.dccmappfordealersterlite.activity.SelectLanguage.SelectLanguageContract;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.model.Language;

import java.util.ArrayList;

/**
 * Created by elitecore on 18/12/18.
 */

public class AboutPresenter<V extends AboutContract.View> extends BasePresenter<V> implements AboutContract.Presenter<V> {
    @Override
    public void init() {
        if(getView()==null) return;
        getView().setUpView(false);
        loadRecords();
    }

    @Override
    public void reset() {
        if(getView()==null) return;
        getView().setUpView(true);
        loadRecords();
    }

    @Override
    public void loadRecords() {
        if(Constants.IS_DUMMY_MODE){
            getView().setNoRecordsFoundView(false);
            ArrayList<Language> languages= (ArrayList<Language>) DummyLists.getLanguages();
            String lang= DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.LOCALE,AppPreferencesHelper.LOCALE);
            for(Language language:languages){
                if(lang.equalsIgnoreCase(language.getLanguageCode())){
                    language.setSelected(true);
                    break;
                }
            }
            getView().loadDataToView(languages);
        }
    }
}

