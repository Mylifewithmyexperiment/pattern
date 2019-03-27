package com.sterlite.dccmappfordealersterlite.activity.About;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.Language;

import java.util.List;

/**
 * Created by elitecore on 18/12/18.
 */


public class AboutContract {
    interface View extends BaseContractView {
        void setUpView(boolean isReset);
        void setNoRecordsFoundView(boolean isActive);
        void loadDataToView(List<Language> languages);
    }
    interface Presenter<V extends com.sterlite.dccmappfordealersterlite.activity.About.AboutContract.View> extends BaseContractPresenter<V> {
        void init();
        void reset();
        void loadRecords();
    }
}