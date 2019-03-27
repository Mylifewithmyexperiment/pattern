package com.sterlite.dccmappfordealersterlite.activity.Kyc;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.ApiHelper;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;

/**
 * Created by etech-10 on 31/8/18.
 */

public class KycPresenter<V extends KycContract.View> extends BasePresenter<V> implements KycContract.Presenter<V> {

    String filePath = null;
    long totalSize = 0;

    @Override
    public void validateIdNumber(String id, String filePath) {
       // startApiCall();

        uploadImage(filePath,id);
        String strJson = AppUtils.loadJSONFromAsset("kycDetail.json");
        try {
            JSONObject jsonObject = new JSONObject(strJson);
            if (jsonObject.has(id)) {
                getView().gotoKycSuccessScreen();
            } else {
                getView().showInvalidDialog();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            getView().showInvalidDialog();
        }
    }


    public void uploadImage(String filepath,String id) {
        this.filePath = filepath;
      DCCMDealerSterlite.getDataManager().uploadDocumentAPI(DCCMDealerSterlite.getDataManager().get(AppPreferencesHelper.CUSTOMER_UID,null), "file", filepath, new ApiHelper.OnApiCallback<Integer>() {
          @Override
          public void onSuccess(Integer baseResponse) {

              Log.e("baseResponse::DIV", baseResponse+"");
          }

          @Override
          public void onFailed(int code, String message) {

          }
      });

    }


}



















