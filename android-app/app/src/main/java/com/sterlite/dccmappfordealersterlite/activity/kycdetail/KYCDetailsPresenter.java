package com.sterlite.dccmappfordealersterlite.activity.kycdetail;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.AppApiHelper;
import com.sterlite.dccmappfordealersterlite.model.Address;

public class KYCDetailsPresenter<V extends KYCReviewContract.View> extends BasePresenter<V> implements KYCReviewContract.Presenter<V> {
    Context context;

    @Override
    public void init(String kycNumber, Context context) {
       /* if (Constants.IS_DUMMY_MODE) {
            if(getView()==null)
                return;
            getView().showProgressBar();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(getView()==null)
                        return;
                    getView().hideProgressBar();
                    getView().setAddress(DummyLists.getOrderDetailPreview());
                }
            },500);

        }*/

        if (getView() == null)
            return;
        JSONObject jsnObj = getJsonObjectFromPropertyFileKYC(kycNumber);
        if (jsnObj == null)
            getView().setAddress(null);
        Address address = new Gson().fromJson(jsnObj.toString(), Address.class);
        getView().setAddress(address);
    }

    private JSONObject getJsonObjectFromPropertyFileKYC(String code) {
        try {
            JSONObject obj = new JSONObject(AppApiHelper.loadJSONFromAsset("kycDetail.json"));
            JSONObject addOnProductObj = obj.getJSONObject(code);

            return addOnProductObj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
