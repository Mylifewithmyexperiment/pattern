package com.sterlite.dccmappfordealersterlite.activity.Feedback;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.SubscribeAddOn.Feedback;

public class UserFeedbackContract {
    interface View extends BaseContractView{

        void successfullyFeedbackSent(Feedback feedback);

        void onErrorIn(String reason);

        void finishActivity();

    }
    interface Presenter<V extends View> extends BaseContractPresenter<V> {

        void sendFeedback(Feedback feedback);
    }
}
