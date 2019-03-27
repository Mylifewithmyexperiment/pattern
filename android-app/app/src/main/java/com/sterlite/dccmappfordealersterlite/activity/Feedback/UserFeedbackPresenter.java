package com.sterlite.dccmappfordealersterlite.activity.Feedback;

import android.os.Handler;

import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.base.BasePresenter;
import com.sterlite.dccmappfordealersterlite.model.SubscribeAddOn.Feedback;


public class UserFeedbackPresenter<V extends UserFeedbackContract.View> extends BasePresenter<V> implements UserFeedbackContract.Presenter<V> {

    @Override
    public void sendFeedback(final Feedback feedback) {
        if(Constants.IS_DUMMY_MODE){
            if(getView()==null){
                return;
            }
            getView().showProgressBar();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(getView()==null)
                        return;
                    getView().hideProgressBar();
                    getView().successfullyFeedbackSent(feedback);
                }
            },500);
        }
    }
}
