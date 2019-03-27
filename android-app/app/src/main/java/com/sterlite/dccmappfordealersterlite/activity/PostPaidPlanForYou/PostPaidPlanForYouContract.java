package com.sterlite.dccmappfordealersterlite.activity.PostPaidPlanForYou;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.base.BaseContractPresenter;
import com.sterlite.dccmappfordealersterlite.base.BaseContractView;
import com.sterlite.dccmappfordealersterlite.model.Plan;

public interface PostPaidPlanForYouContract {

    interface View extends BaseContractView {
        void setUpView(boolean isReset);

        void loadDataToView(String rechargeAmt, String dataUsage, String voiceUseage, String netflixUsage, String primeUsage, String youtubeUseage, ArrayList<Plan> plans);
    }
    interface Presenter<V extends View> extends BaseContractPresenter<V> {
        void init();

    }
}
