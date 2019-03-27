package com.sterlite.dccmappfordealersterlite.activity.SelectLanguage;

import java.util.List;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.Language;

public class SelectLanguageContract {
    interface View extends BaseContractView{
        void setUpView(boolean isReset);
        void setNoRecordsFoundView(boolean isActive);
        void loadDataToView(List<Language> languages);
    }
    interface Presenter<V extends View> extends BaseContractPresenter<V>{
        void init();
        void reset();
        void loadRecords();
    }
}
