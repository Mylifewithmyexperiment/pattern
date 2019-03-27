package com.sterlite.dccmappfordealersterlite.activity.Kyc;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;

/**
 * Created by etech-10 on 31/8/18.
 */

public class KycContract {
    interface View extends BaseContractView{
        void gotoKycSuccessScreen();
        void showInvalidDialog();
    }
    interface Presenter<V extends View> extends BaseContractPresenter<V>{
        void validateIdNumber(String id, String filePath);
    }
}
