package com.sterlite.dccmappfordealersterlite.activity.InternetPackSummery;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.activity.InputPaymentInformation.InputPaymentInformationActivity;
import com.sterlite.dccmappfordealersterlite.activity.OrederReceiveSuccess.OrderReceiveSuccess;
import com.sterlite.dccmappfordealersterlite.activity.PostPaidPlanForYou.PostPaidPlanForYouActivity;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityInternetPackSummeryBinding;

public class InternetPackSummeryActivity extends BaseActivity implements InternetPackSummeryContract.View {

    public static final String ARG_PLAN_NAME="argPlanDetails";
    public static final String ARG_PLAN_PRICE="argPlanPrice";
    public static final String ARG_PLAN_DESCRIPTION="argPlanDescription";
    public static final String ARG_FROM="argFrom";
    private boolean isPostPaidPlan = false;
    List<String> payMode= new ArrayList<>();

    ActivityInternetPackSummeryBinding binding;
    InternetPackSummeryContract.Presenter<InternetPackSummeryContract.View> presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding= DataBindingUtil.setContentView(this,R.layout.activity_internet_pack_summery);
        init();
        if(isPostPaidPlan) {
            setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_chage_plan), true);
        }else {
            setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_pack_summery), true);
        }
        presenter=new InternetPackSummeryPresenter<>();
        presenter.onAttach(this);
        initUI();
    }

    private void init() {
        String from = getIntent().getStringExtra(ARG_FROM);
        if (!TextUtils.isEmpty(from) && from.equalsIgnoreCase(PostPaidPlanForYouActivity.class.getName())) {
            isPostPaidPlan = true;
        }
    }
    private void initUI() {
        String packName=getIntent().getStringExtra(ARG_PLAN_NAME);
        String price=getIntent().getStringExtra(ARG_PLAN_PRICE);
        String description=getIntent().getStringExtra(ARG_PLAN_DESCRIPTION);
        if(TextUtils.isEmpty(packName)){
            binding.tvPlanName.setVisibility(View.GONE);
        }else {
            binding.tvPlanName.setText(packName);
        }
        if(TextUtils.isEmpty(price)){
            binding.tvLblAmount.setVisibility(View.GONE);
            binding.tvAmount.setVisibility(View.GONE);
        }else{
            binding.tvAmount.setText(AppUtils.getPriceWithSymbol(price));
        }
        if(TextUtils.isEmpty(description)){
            binding.tvDescription.setVisibility(View.GONE);
        }else {
            binding.tvDescription.setText(description);
        }
        payMode.add(getString(R.string.online));
        payMode.add(getString(R.string.balance));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, payMode);
        binding.spinnerBalance.setAdapter(dataAdapter);
        setClickListerners();
    }

    private void setClickListerners() {
        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String planName=getIntent().getStringExtra(ARG_PLAN_NAME);
                if(isPostPaidPlan){
                    presenter.changePlan(planName);
                }else {
                    String paymentMode = binding.spinnerBalance.getSelectedItem().toString().trim();
                    if (paymentMode.equalsIgnoreCase(getString(R.string.online))) {
                        presenter.doSubscribeAddOn(planName, paymentMode);

                    } else if (paymentMode.equalsIgnoreCase(getString(R.string.balance))) {
                        presenter.doSubscribeAddOn(planName, paymentMode);
                    }
                }
                //   planName="10Days Bucket";
            }
        });
    }


    @Override
    public void gotoSuccessScreen(String message) {
        if(binding.spinnerBalance.getSelectedItem().toString().trim().equalsIgnoreCase(getString(R.string.online))) {
            Intent intent = new Intent(InternetPackSummeryActivity.this, InputPaymentInformationActivity.class);
            intent.putExtra(OrderReceiveSuccess.ARG_SUCCESS_MESSAGE, message);
            startActivity(intent);
        }else{
            Intent intent = new Intent(InternetPackSummeryActivity.this, OrderReceiveSuccess.class);
            intent.putExtra(OrderReceiveSuccess.ARG_SUCCESS_MESSAGE, message);
            startActivity(intent);
        }
    }

    @Override
    public void gotoFailScreen(){
        AppUtils.showToast(InternetPackSummeryActivity.this,getString(R.string.msg_something_went_wrong));
    }

}
