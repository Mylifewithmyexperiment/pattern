package com.sterlite.dccmappfordealersterlite.activity.RechargeSummery;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.Utils.DummyLists;
import com.sterlite.dccmappfordealersterlite.activity.InputPaymentInformation.InputPaymentInformationActivity;
import com.sterlite.dccmappfordealersterlite.activity.OrederReceiveSuccess.OrderReceiveSuccess;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityRechargeSummeryBinding;
import com.sterlite.dccmappfordealersterlite.model.Recharge;
import com.sterlite.dccmappfordealersterlite.model.dto.recharge.RechargeDetailResponseData;


public class RechargeSummeryActivity extends BaseActivity implements RechargeSummeryContract.View {

    public static final String RECHARGE="recharge";

    private Recharge recharge;

    private ActivityRechargeSummeryBinding binding;

    private RechargeSummeryContract.Presenter<RechargeSummeryContract.View> presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recharge_summery);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_recharge_summary), true);
        recharge= (Recharge) getIntent().getSerializableExtra(RECHARGE);
        binding.tvNumber.setText(recharge.getNumber());
        HashMap<String,String> hm = DummyLists.getRechargeSummery(recharge.getAmount());
       // binding.tvData.setText(hm.get(Constants.DATA));
        binding.tvMonetary.setText(AppUtils.getPriceWithSymbol(hm.get(Constants.MONETARY)));
        binding.tvAmount.setText(AppUtils.getPriceWithSymbol(recharge.getAmount()));
        setClickListners();
        presenter = new RechargeSummeryPresenter<>();
        presenter.onAttach(this);

    }

    private void setClickListners() {
        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.doRecharge(recharge);
            }
        });
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void rechargeApiCallSuccess(RechargeDetailResponseData rechargeDetailResponseData) {
        if(rechargeDetailResponseData.getResponseCode() >= 0) {
//            Toast.makeText(RechargeSummeryActivity.this, R.string.recharge_success, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RechargeSummeryActivity.this, InputPaymentInformationActivity.class);
            intent.putExtra(OrderReceiveSuccess.ARG_ORDER_NO, (rechargeDetailResponseData.getAvailableBalance()/100.0f)+"");
            intent.putExtra(OrderReceiveSuccess.ARG_TRANSACTION_NO, rechargeDetailResponseData.getRechargeRequestNumber());
            intent.putExtra(OrderReceiveSuccess.ARG_PAID, recharge.getAmount());
            intent.putExtra(OrderReceiveSuccess.ARG_SUCCESS_MESSAGE, RechargeSummeryActivity.this.getString(R.string.msg_recharge_successful));
            intent.putExtra(OrderReceiveSuccess.ARG_IS_RECHARGE, true);

            startActivity(intent);
        }else{
            Toast.makeText(RechargeSummeryActivity.this, R.string.msg_somtehing_went_wrong, Toast.LENGTH_SHORT).show();
        }
    }

}
