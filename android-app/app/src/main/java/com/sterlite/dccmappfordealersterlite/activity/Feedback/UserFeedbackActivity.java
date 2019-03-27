package com.sterlite.dccmappfordealersterlite.activity.Feedback;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.activity.LandingPage.LandingPageActivity;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityUserFeedbackBinding;
import com.sterlite.dccmappfordealersterlite.model.SubscribeAddOn.Feedback;

public class UserFeedbackActivity extends BaseActivity implements UserFeedbackContract.View, View.OnClickListener {
    UserFeedbackContract.Presenter<UserFeedbackContract.View> presenter;
    ActivityUserFeedbackBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        init();

        AppUtils.showAdd(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, Constants.USER_FEEDBACK_SCREEN);
    }

    private void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_feedback);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_feedback), true);
        setClickListeners();
        presenter = new UserFeedbackPresenter<>();
        presenter.onAttach(this);
    }

    private void setClickListeners() {
        binding.btnProceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProceed:
                if (checkValidation()) {
                    presenter.sendFeedback(new Feedback());
                } else {
                    AppUtils.showToast(this, getString(R.string.msg_invaild_feedback));
                }
                break;
        }
    }

    private boolean checkValidation() {
        if (binding.rbStar.getRating() == 0)
            return false;
        if (binding.smileRating.getRating() == 0)
            return false;
        return true;
    }


    @Override
    public void successfullyFeedbackSent(Feedback feedback) {
        AppUtils.showToast(this, getString(R.string.msg_thanks_feedback));
        redirectUserToScreen();

       /* if (getPartner())
        {
            gotoDashBoard();

        }
        else {
                    redirectUserToScreen();

        }*/
    }

    public void redirectUserToScreen() {
        try {
            if (AppUtils.isUserRegistered())
                gotoDashBoard();
            else
                gotoLandingPage();
        } catch (Exception e) {
            Log.e("OrderRecSucc", e + " ");
        }
    }

    private void gotoLandingPage() {
        Intent newIntent = new Intent(this, LandingPageActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newIntent);
        finish();
    }

    private void gotoDashBoard() {
        Intent newIntent = new Intent(this, com.sterlite.dccmappfordealersterlite.activity.dashboard.DashBoardActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        newIntent.putExtra(com.sterlite.dccmappfordealersterlite.activity.dashboard.DashBoardActivity.ARG_CURRENT, "2");
        startActivity(newIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        redirectUserToScreen();
    }


    @Override
    public void onErrorIn(String reason) {
        AppUtils.showToast(this, reason);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}
