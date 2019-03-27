package com.sterlite.dccmappfordealersterlite.activity.BalanceSummery;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.activity.OrederReceiveSuccess.OrderReceiveSuccess;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityBalanceTransferSummeryBinding;
import com.sterlite.dccmappfordealersterlite.model.Recharge;
import com.sterlite.dccmappfordealersterlite.model.dto.transferbalance.TransferBalanceResponseData;


public class BalanceTransferSummeryActivity extends BaseActivity implements BalanceTransferSummeryContract.View {

    public static final String ARF_TRANSFER ="argTransfer";

    private Recharge transfer;

    private ActivityBalanceTransferSummeryBinding binding;

    private BalanceTransferSummeryContract.Presenter<BalanceTransferSummeryContract.View> presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_balance_transfer_summery);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.title_transfer_summary), true);
        transfer = (Recharge) getIntent().getSerializableExtra(ARF_TRANSFER);
        binding.tvNumber.setText(transfer.getNumber());
        binding.tvAmount.setText(AppUtils.getPriceWithSymbol(transfer.getAmount()));
        if(TextUtils.isEmpty(transfer.getComment())){
            binding.tvLblComment.setVisibility(View.GONE);
            binding.tvComment.setVisibility(View.GONE);
        }else {
            binding.tvComment.setText(transfer.getComment());
        }
        setClickListners();
        presenter = new BalanceTransferSummeryPresenter<>();
        presenter.onAttach(this);


    }

    private void setClickListners() {
        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.balanceTransfer(transfer);

            }
        });
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void balanceTransferApiCallSuccess(TransferBalanceResponseData transferBalanceResponseData) {
        if(transferBalanceResponseData!=null && transferBalanceResponseData.getResponseCode() >= 0){
            Intent intent = new Intent(BalanceTransferSummeryActivity.this, OrderReceiveSuccess.class);
            intent.putExtra(OrderReceiveSuccess.ARG_ORDER_NO,(transferBalanceResponseData.getSourceAfterBalance()/100.0f)+"");
            intent.putExtra(OrderReceiveSuccess.ARG_TRANSACTION_NO,transferBalanceResponseData.getSourceTransactionID());
            intent.putExtra(OrderReceiveSuccess.ARG_PAID, transfer.getAmount());
            intent.putExtra(OrderReceiveSuccess.ARG_SUCCESS_MESSAGE, BalanceTransferSummeryActivity.this.getString(R.string.msg_transfer_successful));
            intent.putExtra(OrderReceiveSuccess.ARG_IS_RECHARGE, true);
            startActivity(intent);
        }else{
            AppUtils.showToast(this,BalanceTransferSummeryActivity.this.getString(R.string.msg_somtehing_went_wrong));
        }
    }

}
