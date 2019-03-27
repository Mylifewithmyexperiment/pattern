package com.sterlite.dccmappfordealersterlite.activity.NewConnection;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.activity.UserDetail.UserDetailActivity;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityNewConnectionBinding;


public class NewConnectionActivity extends BaseActivity {


    private ActivityNewConnectionBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_connection);
        setUpView(binding.toolbar.toolbar, binding.extraView, getString(R.string.lbl_new_connection), true);


        binding.btnPostPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewConnectionActivity.this, UserDetailActivity.class));
            }
        });
        binding.btnPrepaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewConnectionActivity.this, UserDetailActivity.class));
            }
        });

    }

}
