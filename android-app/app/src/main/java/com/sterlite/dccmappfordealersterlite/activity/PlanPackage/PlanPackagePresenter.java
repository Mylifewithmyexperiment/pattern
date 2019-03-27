package com.sterlite.dccmappfordealersterlite.activity.PlanPackage;

import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.data.network.model.Pagging;
import com.sterlite.dccmappfordealersterlite.model.PlanPackage;


/**
 * Created by etech3 on 27/6/18.
 */

public class PlanPackagePresenter<V extends PlanPackageContract.View> extends BasePresenter<V> implements PlanPackageContract.Presenter<V>, ApiHelper.OnApiCallback<Pagging<PlanPackage>> {

    @Override
    public void init(String planType) {
        getView().setUpView(false);
        startApiCall();
        if (planType.equals(Constants.PLAN_PACK_INTERNET)) {
            DCCMDealerSterlite.getDataManager().getPlanPackageInternet(this);
        } else if (planType.equals(Constants.PLAN_PACK_ENTERTAINMENT)) {
            DCCMDealerSterlite.getDataManager().getPlanPackageEntertainment(this);
        } else if (planType.equals(Constants.PLAN_PACK_SMS)) {
            DCCMDealerSterlite.getDataManager().getPlanPackageSms(this);
        } else if (planType.equals(Constants.PLAN_PACK_ROAMING)) {
            DCCMDealerSterlite.getDataManager().getPlanPackageRoaming(this);
        }

    }

    @Override
    public void onSuccess(Pagging<PlanPackage> baseResponse) {
        getView().loadDataToView(PlanPackage.sortArray(baseResponse.getArrList()));
        PlanPackagePresenter.super.onSuccess();
    }

    @Override
    public void onFailed(int code, String message) {
        PlanPackagePresenter.super.onFaild(code, message);
    }
}
