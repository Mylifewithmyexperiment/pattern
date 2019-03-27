package com.sterlite.dccmappfordealersterlite.activity.SelectLanguage;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.activity.LandingPage.LandingPageActivity;
import com.sterlite.dccmappfordealersterlite.activity.dashboard.DashBoardActivity;
import com.sterlite.dccmappfordealersterlite.adapter.LanguageAdapter;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivitySelectLanguageBinding;
import com.sterlite.dccmappfordealersterlite.model.Language;

public class SelectLanguageActivity extends BaseActivity implements SelectLanguageContract.View {

    SelectLanguageContract.Presenter<SelectLanguageContract.View> presenter;
    ActivitySelectLanguageBinding binding;
    LanguageAdapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppTheme();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_language);

        setUpView(binding.toolbar.toolbar,binding.extraView,getString(R.string.title_select_language),true);
        setClickListeners();
        presenter = new SelectLanguagePresenter<>();
        presenter.onAttach(this);
        presenter.init();
    }

    private void setClickListeners() {
        binding.btnSelectLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adapter!=null){
                    AppUtils.setLocale(SelectLanguageActivity.this,adapter.getSelectedItem().getLanguageCode());
                    if(DCCMDealerSterlite.getDataManager().getBoolean(AppPreferencesHelper.PREF_IS_USER_LOGGED_IN,false)){
                        Intent intent = new Intent(SelectLanguageActivity.this, DashBoardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(SelectLanguageActivity.this, LandingPageActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    finish();
                }
            }
        });
    }

    @Override
    public void setUpView(boolean isReset) {
        if (isReset) {
            adapter = null;
            layoutManager = null;
        }
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }
        if (adapter == null) {
            adapter = new LanguageAdapter(this);
        }
        binding.rvLanguage.setLayoutManager(layoutManager);
        binding.rvLanguage.setAdapter(adapter);
    }

    @Override
    public void setNoRecordsFoundView(boolean isActive) {
        if (isActive) {
            binding.tvNotFound.setVisibility(View.VISIBLE);
        } else {
            binding.tvNotFound.setVisibility(View.GONE);

        }
    }

    @Override
    public void loadDataToView(List<Language> languages) {
        adapter.addItems((ArrayList<Language>) languages);
    }

}
