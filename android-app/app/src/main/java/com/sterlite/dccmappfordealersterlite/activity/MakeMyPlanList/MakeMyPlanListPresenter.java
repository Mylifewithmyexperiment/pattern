package com.sterlite.dccmappfordealersterlite.activity.MakeMyPlanList;


import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel.MMPBase;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel.MakeMyPlanItems;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel.MakeMyPlanLists;

/**
 * Created by etech3 on 27/6/18.
 */

public class MakeMyPlanListPresenter<V extends MakeMyPlanListContract.View> extends BasePresenter<V> implements MakeMyPlanListContract.Presenter<V> {

    // 20180830

    MakeMyPlanLists makeMyPlanLists;

    @Override
    public void onAttach(V baseView) {
        super.onAttach(baseView);

    }


    @Override
    public void init() {
        if (getView() == null) return;
        getView().setUpView();
        if (Constants.IS_DUMMY_MODE) {
            startApiCall();
            DCCMDealerSterlite.getDataManager().getMakeMyPlan(new ApiHelper.OnApiCallback<MakeMyPlanLists>() {
                @Override
                public void onSuccess(MakeMyPlanLists baseResponse) {
                    if (getView() == null) return;
                    MakeMyPlanListPresenter.this.makeMyPlanLists = baseResponse;
                    createMakeMyPlanList();

                }

                @Override
                public void onFailed(int code, String message) {
                    MakeMyPlanListPresenter.super.onFaild(code, message);
                }
            });
        }
    }

    @Override
    public void applyFilter(int min, int max) {
        if (makeMyPlanLists != null) {
            ArrayList<MakeMyPlanItems> arrFilterList = getFilterArrayList(min, max);
            if (arrFilterList.size() > 0) {
                if (getView() == null) return;
                getView().loadFilterDataToView(arrFilterList);
            }
        }
    }

    @Override
    public void applyReset() {
        if (getView() == null) return;
        getView().loadDataToView(MakeMyPlanLists.sortArray(makeMyPlanLists.getArrBasicPlan()));
    }

    private ArrayList<MakeMyPlanItems> getFilterArrayList(int min, int max) {
        ArrayList<MakeMyPlanItems> arrList = new ArrayList<>();
        for (MakeMyPlanItems items : makeMyPlanLists.getArrBasicPlan()) {
            int price = items.getOneTimeProductPrice().getValue();
            if (price >= min && price <= max) {
                arrList.add(items);
            }
        }
        return arrList;
    }

    private void createMakeMyPlanList() {
        for (MakeMyPlanItems items : makeMyPlanLists.getArrBasicPlan()) {
            items.setArrDataAddOn(getDataAddOnList(items.getOneTimeProductPrice().getValue()));
            items.setArrDataSubAddOn(getDataSubAddOnList(items.getOneTimeProductPrice().getValue()));
            items.setArrMinAddOn(getMinAddOnList(items.getOneTimeProductPrice().getValue()));
            items.setArrSMSAddOn(getSMSAddOnList(items.getOneTimeProductPrice().getValue()));
            items.setArrRoamingAddOn(getRoamingAddOnList(items.getOneTimeProductPrice().getValue()));

            // create list for benefits that display horizontally in view.
            ArrayList<MMPBase> arrUpToBenefits = new ArrayList<>();
            if (items.getArrDataAddOn().getArrPlan() != null && items.getArrDataAddOn().getArrPlan().size() > 0) {
                MMPBase mmpBase = new MMPBase();
                mmpBase.setItemImageId(R.drawable.ic_mmp_internet);
                mmpBase.setLastItem(items.getArrDataAddOn().getArrPlan().get(items.getArrDataAddOn().getArrPlan().size() - 1));
                arrUpToBenefits.add(mmpBase);
            }
            if (items.getArrMinAddOn().getArrPlan() != null && items.getArrMinAddOn().getArrPlan().size() > 0) {
                MMPBase mmpBase = new MMPBase();
                mmpBase.setItemImageId(R.drawable.ic_mmp_call);
                mmpBase.setLastItem(items.getArrMinAddOn().getArrPlan().get(items.getArrMinAddOn().getArrPlan().size() - 1));
                arrUpToBenefits.add(mmpBase);
            }
            if (items.getArrSMSAddOn().getArrPlan() != null && items.getArrSMSAddOn().getArrPlan().size() > 0) {
                MMPBase mmpBase = new MMPBase();
                mmpBase.setItemImageId(R.drawable.ic_mmp_sms);
                mmpBase.setLastItem(items.getArrSMSAddOn().getArrPlan().get(items.getArrSMSAddOn().getArrPlan().size() - 1));
                arrUpToBenefits.add(mmpBase);
            }
            if (items.getArrRoamingAddOn().getArrPlan() != null && items.getArrRoamingAddOn().getArrPlan().size() > 0) {
                MMPBase mmpBase = new MMPBase();
                mmpBase.setItemImageId(R.drawable.ic_mmp_roaming);
                mmpBase.setLastItem(items.getArrRoamingAddOn().getArrPlan().get(items.getArrRoamingAddOn().getArrPlan().size() - 1));
                arrUpToBenefits.add(mmpBase);
            }
            items.setArrUpToBenefits(arrUpToBenefits);
        }
        if (getView() == null) return;
        MakeMyPlanListPresenter.super.onSuccess();
        getView().loadDataToView(MakeMyPlanLists.sortArray(makeMyPlanLists.getArrBasicPlan()));

    }


    private MMPBase getDataAddOnList(int price) {
        ArrayList<MakeMyPlanItems> arrDataAddOnList = new ArrayList<>();
        for (MakeMyPlanItems items : makeMyPlanLists.getArrDataAddOn()) {
            if (items.getOneTimeProductPrice().getValue() <= price) {
                arrDataAddOnList.add(items);
            }
        }
        MMPBase mmpBase = new MMPBase();
        mmpBase.setItemTitle(DCCMDealerSterlite.appContext.getString(R.string.lbl_internet));
            mmpBase.setAddOnCategory(Constants.MMP_ADDON_CATEGORY_DATA);
        mmpBase.setArrPlan(arrDataAddOnList);
        MakeMyPlanLists.sortArray(arrDataAddOnList);
        return mmpBase;
    }

    private MMPBase getDataSubAddOnList(int price) {
        ArrayList<MakeMyPlanItems> arrDataAddSubOnList = new ArrayList<>();
        for (MakeMyPlanItems items : makeMyPlanLists.getArrDataSubAddOn()) {
            if (items.getOneTimeProductPrice().getValue() <= price) {
                arrDataAddSubOnList.add(items);
            }
        }
        MMPBase mmpBase = new MMPBase();
        mmpBase.setItemTitle(DCCMDealerSterlite.appContext.getString(R.string.lbl_internet_mb));
        mmpBase.setAddOnCategory(Constants.MMP_ADDON_CATEGORY_DATA_SUB);
        mmpBase.setArrPlan(arrDataAddSubOnList);
        MakeMyPlanLists.sortArray(arrDataAddSubOnList);
        return mmpBase;
    }

    private MMPBase getMinAddOnList(int price) {
        ArrayList<MakeMyPlanItems> arrMinAddOnList = new ArrayList<>();
        for (MakeMyPlanItems items : makeMyPlanLists.getArrMinAddOn()) {
            if (items.getOneTimeProductPrice().getValue() <= price) {
                arrMinAddOnList.add(items);
            }
        }
        MMPBase mmpBase = new MMPBase();
        mmpBase.setItemTitle(DCCMDealerSterlite.appContext.getString(R.string.lbl_call));
        mmpBase.setAddOnCategory(Constants.MMP_ADDON_CATEGORY_VOICE);
        mmpBase.setArrPlan(arrMinAddOnList);
        MakeMyPlanLists.sortArray(arrMinAddOnList);
        return mmpBase;
    }

    private MMPBase getSMSAddOnList(int price) {
        ArrayList<MakeMyPlanItems> arrSMSAddOnList = new ArrayList<>();
        for (MakeMyPlanItems items : makeMyPlanLists.getArrSMSAddOn()) {
            if (items.getOneTimeProductPrice().getValue() <= price) {
                arrSMSAddOnList.add(items);
            }
        }
        MMPBase mmpBase = new MMPBase();
        mmpBase.setItemTitle(DCCMDealerSterlite.appContext.getString(R.string.lbl_sms));
        mmpBase.setAddOnCategory(Constants.MMP_ADDON_CATEGORY_SMS);
        mmpBase.setArrPlan(arrSMSAddOnList);
        MakeMyPlanLists.sortArray(arrSMSAddOnList);
        return mmpBase;
    }

    private MMPBase getRoamingAddOnList(int price) {
        ArrayList<MakeMyPlanItems> arrRoamingAddOnList = new ArrayList<>();
        for (MakeMyPlanItems items : makeMyPlanLists.getArrRoamingAddOn()) {
            if (items.getOneTimeProductPrice().getValue() <= price) {
                arrRoamingAddOnList.add(items);
            }
        }
        MMPBase mmpBase = new MMPBase();
        mmpBase.setItemTitle(DCCMDealerSterlite.appContext.getString(R.string.lbl_roaming));
        mmpBase.setAddOnCategory(Constants.MMP_ADDON_CATEGORY_ROM);
        mmpBase.setArrPlan(arrRoamingAddOnList);
        MakeMyPlanLists.sortArray(arrRoamingAddOnList);
        return mmpBase;
    }

}
