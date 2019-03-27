package com.sterlite.dccmappfordealersterlite.activity.pincode;

import android.util.Log;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.data.network.AppApiHelper2;
import com.sterlite.dccmappfordealersterlite.model.dto.pos.VMSPointOfServiceDataWSDTO;


/**
 * Created by etech3 on 27/6/18.
 */

public class PincodePresenter<V extends PincodeContract.View> extends BasePresenter<V> implements PincodeContract.Presenter<V> {

    @Override
    public void doValidation(String pincode) {
//        if (getView() == null) return;
//        if (Constants.IS_DUMMY_MODE) {
//            getView().showProgressBar();
//            getView().hideProgressBar();
//            getView().onSuccessValidate();
//        }
        Log.e("Pincode",";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; "+pincode);
        if (getView() == null) return;
        getView().showProgressBar();
        String ISOCODE=Constants.ISOCODE;


        AppApiHelper2.getWebServices().validatePincode(pincode, ISOCODE,new Callback<VMSPointOfServiceDataWSDTO>() {

            @Override
            public void success(VMSPointOfServiceDataWSDTO vmsPointOfServiceDataWSDTO, Response response) {
                getView().hideProgressBar();
                Log.e("","vmsPointOfServiceDataWSDTO code:: "+vmsPointOfServiceDataWSDTO.getResponseCode());
                Log.e("","vmsPointOfServiceDataWSDTO  Message:: "+vmsPointOfServiceDataWSDTO.getResponseMessage());
                if(vmsPointOfServiceDataWSDTO.getResponseMessage().equalsIgnoreCase("success"))
                {
                    Log.e("Pincode"," :::: "+vmsPointOfServiceDataWSDTO.getResponseMessage());
                    if (getView() == null) return;
                    getView().hideProgressBar();
                    getView().onSuccessValidate();
                }else {
                    Log.e("Pincode"," :::: "+vmsPointOfServiceDataWSDTO.getResponseMessage());
                    if (getView() == null) return;
                    getView().hideProgressBar();
                    getView().onFailValidate();
                   /* if (Constants.IS_DUMMY_MODE) {
                        getView().showProgressBar();
                        getView().hideProgressBar();
                        getView().onSuccessValidate();
                    }else
                        getView().onFailValidate();*/
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("PincodePresenter", " " + error);
                if (getView() == null) return;
                getView().hideProgressBar();
//                if (Constants.IS_DUMMY_MODE) {
//                    getView().showProgressBar();
//                    getView().hideProgressBar();
//                    getView().onSuccessValidate();
//                }else
//                    getView().onFailValidate();
            }
        });
    }
}
