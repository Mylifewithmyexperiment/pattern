package com.sterlite.dccmappfordealersterlite.activity.LandingPage;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;

import com.amplifyreach.chatsdk.ARActivity;
import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.activity.Recharge.RechargeActivity;
import com.sterlite.dccmappfordealersterlite.activity.SelectLanguage.SelectLanguageActivity;
import com.sterlite.dccmappfordealersterlite.activity.UserDetail.UserDetailActivity;
import com.sterlite.dccmappfordealersterlite.activity.loginnew.NewLoginActivity;
import com.sterlite.dccmappfordealersterlite.adapter.LandingPageAdapter;
import com.sterlite.dccmappfordealersterlite.app.DCCMDealerSterlite;
import com.sterlite.dccmappfordealersterlite.base.BaseActivity;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.data.prefs.AppPreferencesHelper;
import com.sterlite.dccmappfordealersterlite.databinding.ActivityLandingPageBinding;
import com.sterlite.dccmappfordealersterlite.model.LandingPageItem;

import static android.Manifest.permission.MANAGE_DOCUMENTS;
import static android.Manifest.permission.MODIFY_AUDIO_SETTINGS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;


public class LandingPageActivity extends BaseActivity implements LandingPageContract.View {


    private ActivityLandingPageBinding binding;
    private LandingPageAdapter landingPageAdapter;
    private LandingPageContract.Presenter<LandingPageContract.View> presenter;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(this, getClass().getSimpleName());
        setAppTheme();
     /*   getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_landing_page);

        if(Constants.APP.equalsIgnoreCase(Constants.MTN)) {
            binding.companyLogo.setImageResource(R.drawable.mtn_logo);
        }else if(Constants.APP.equalsIgnoreCase(Constants.TELKOMSEL)) {

            binding.companyLogo.setImageResource(R.drawable.company_logo);
        }
        else if(Constants.APP.equalsIgnoreCase(Constants.VODAFONE)) {

            binding.companyLogo.setImageResource(R.drawable.ic_vodafone1);
        }else
        binding.companyLogo.setVisibility(View.INVISIBLE);

        presenter = new LandingPagePresenter<>();
        presenter.onAttach(this);
        presenter.init();

        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHideSelectionView(false);
            }
        });
        binding.ivChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPageActivity.this, SelectLanguageActivity.class);
                startActivity(intent);
            }
        });
        binding.ivChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatBox();

              /*  Intent intent = new Intent(LandingPageActivity.this, SelectLanguageActivity.class);
                startActivity(intent);*/
            }
        });

        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

      //  getSupportActionBar().hide();
    }

    private void chatBox() {
        requestPermission();
        Intent intent = new Intent(LandingPageActivity.this, ARActivity.class);
        Bundle bundle = new Bundle();

        //Set client ID
        // uncomment following lines, for more information contact at sales@amplifyreach.com
        bundle.putLong("clientId",1410);

        //Set bot ID
        // uncomment following line, for more information contact at sales@amplifyreach.com
        bundle.putString("botId", "d8519054092fdbcf");

        //set Authentication Token
        bundle.putString("authToken","YOUR_AUTH_TOKEN");

        intent.putExtras(bundle);
        LandingPageActivity.this.startActivity(intent);
    }



    private void goToUserDetailScreen(String value, String extra) {
      /*  if(!getAdmin())
        AppUtils.removePrefrences();
        else
        {
            //setProperties Admin want
        }*/
        Intent intent = new Intent(LandingPageActivity.this, UserDetailActivity.class);
        intent.putExtra(UserDetailActivity.ARG_FROM, value);
        intent.putExtra(UserDetailActivity.ARG_FROM_EXTRA, extra);
        startActivity(intent);
    }


    private void goToLoginScreen(String value) {
        Intent intent = new Intent(LandingPageActivity.this, NewLoginActivity.class);
        intent.putExtra(UserDetailActivity.ARG_FROM, value);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }


    @Override
    public void setUpView(boolean isReset) {
        if (isReset) {
            landingPageAdapter = null;
        }
        binding.rvHomeItem.setLayoutManager(new GridLayoutManager(this, 2));
        if (landingPageAdapter == null) {
            landingPageAdapter = new LandingPageAdapter(this, new OnRecyclerViewItemClickListener<LandingPageItem>() {
                @Override
                public void onClicked(LandingPageItem bean, View view, int position, ViewType viewType) {
                    if (bean.getMenuId() == 1) {

                        showSelectionView(getString(R.string.lbl_make_my_plan), R.drawable.ic_shop_my_plan, getString(R.string.msg_make_my_plan), getString(R.string.nav_menu_prepaid), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                goToUserDetailScreen("makeMyPlan", Constants.PLAN_PREPAID);
                            }
                        }, getString(R.string.nav_menu_postpaid), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                goToUserDetailScreen("makeMyPlan", Constants.PLAN_POSTPAID);
                            }
                        });
                    } else if (bean.getMenuId() == 2) {
                        showSelectionView(getString(R.string.lbl_new_connection), R.drawable.ic_shop_prepaid_to_postpaid, getString(R.string.msg_new_connection), getString(R.string.nav_menu_prepaid), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                goToUserDetailScreen("newConnection", Constants.PLAN_PREPAID);
                            }
                        }, getString(R.string.nav_menu_postpaid), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                goToUserDetailScreen("newConnection", Constants.PLAN_POSTPAID);
                            }
                        });

                    } else if (bean.getMenuId() == 3) {
                        goToUserDetailScreen("newfixedConnection", Constants.BROADBAND_PLAN_POSTPAID);

                       /* showSelectionView(getString(R.string.lbl_new_broadband_connection), R.drawable.ic_router, getString(R.string.msg_new_bb_plan), getString(R.string.nav_menu_prepaid), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                goToUserDetailScreen("newfixedConnection", Constants.BROADBAND_PLAN_PREPAID);
                            }
                        }, getString(R.string.nav_menu_postpaid), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                goToUserDetailScreen("newfixedConnection", Constants.BROADBAND_PLAN_POSTPAID);
                            }
                        });*/

                    } else if (bean.getMenuId() == 4) {
                        goToLoginScreen("myAccount");
                    }/* else if (bean.getMenuId() == 5) {
                        goToLoginScreen("activation");
                    }*/ else if (bean.getMenuId() == 5) {
                        startActivity(new Intent(LandingPageActivity.this, RechargeActivity.class));
                    } else if (bean.getMenuId() == 6) {
                        goToLoginScreen("orderTracking");
                    } else if (bean.getMenuId() == 7) {
                        goToLoginScreen("balanceTransfer");
                        //startActivity(new Intent(LandingPageActivity.this, BalanceTransferActivity.class));
                    } /*else if (bean.getMenuId() == 8) {
                        Intent intent = new Intent(LandingPageActivity.this, InputPaymentInformationActivity.class);
                        startActivity(intent);
                    } */else {

                    }

                }

                @Override
                public void onLastItemReached() {

                }
            });
            binding.rvHomeItem.setAdapter(landingPageAdapter);
        } else {
            binding.rvHomeItem.setAdapter(landingPageAdapter);
        }

    }


    @Override
    public void loadDataToView(ArrayList<LandingPageItem> list) {
        landingPageAdapter.addItems(createLandingPageMenu());
    }


    // 20180830
    private ArrayList<LandingPageItem> createLandingPageMenu() {
        ArrayList<LandingPageItem> arrLandingPageItems = new ArrayList<>();
//        arrLandingPageItems.add(getLandingPageItem(8, "Payment", R.drawable.ic_language, R.drawable.home_menu_bg_1, R.color.app_shop_color8));
        TypedValue typedValue =  new TypedValue();
        getTheme().resolveAttribute(R.attr.colorMain,typedValue,true);


        /*Log.e("color_main:::",typedValue.coerceToString().toString());
        getTheme().resolveAttribute(R.attr.colorIcon,typedValue,true);

        Log.e("color_icon:::",typedValue.coerceToString().toString());*/
        arrLandingPageItems.add(getLandingPageItem(1, getString(R.string.lbl_make_my_plan), R.drawable.ic_shop_my_plan, R.drawable.home_menu_bg_1, typedValue.resourceId));
        arrLandingPageItems.add(getLandingPageItem(2, getString(R.string.lbl_new_connection), R.drawable.ic_shop_prepaid_to_postpaid, R.drawable.home_menu_bg_2, typedValue.resourceId));
        arrLandingPageItems.add(getLandingPageItem(3, getString(R.string.lbl_new_broadband_connection), R.drawable.ic_router, R.drawable.home_menu_bg_bb, typedValue.resourceId));
        arrLandingPageItems.add(getLandingPageItem(4, getString(R.string.lbl_my_account), R.drawable.ic_shop_my_account, R.drawable.home_menu_bg_3, typedValue.resourceId));
     //   arrLandingPageItems.add(getLandingPageItem(5, getString(R.string.lbl_activation), R.drawable.ic_shop_device, R.drawable.home_menu_bg_4, R.color.app_shop_color4));
        arrLandingPageItems.add(getLandingPageItem(5, getString(R.string.lbl_recharge), R.drawable.ic_shop_recharge, R.drawable.home_menu_bg_5, typedValue.resourceId));
        arrLandingPageItems.add(getLandingPageItem(6, getString(R.string.lbl_order_tracking), R.drawable.ic_shop_track_order, R.drawable.home_menu_bg_6, typedValue.resourceId));
        arrLandingPageItems.add(getLandingPageItem(7, getString(R.string.title_balance_transfer), R.drawable.ic_shop_prepaid, R.drawable.home_menu_bg_7, typedValue.resourceId));
        return arrLandingPageItems;
    }

    private LandingPageItem getLandingPageItem(int id, String name, int resId, int bgCard, int circleColor) {
        LandingPageItem item1 = new LandingPageItem();
        item1.setMenuId(id);
        item1.setMenuName(name);
        item1.setMenuResId(resId);
        item1.setMenuNameColor(bgCard);
        item1.setMenuCirleColor(circleColor);
        return item1;
    }

    private void showSelectionView(String title, int menuResId, String description, String btn1Text, View.OnClickListener btn1OnClickListener) {
        showSelectionView(title, menuResId, description, btn1Text, btn1OnClickListener, null, null);
    }

    private void showSelectionView(String title, int menuResId, String description, String btn1Text, final View.OnClickListener btn1OnClickListener, String btn2Text, final View.OnClickListener btn2OnClickListener) {
        binding.tvTitle.setText(title);
        binding.ivMenuItem.setImageResource(menuResId);
        binding.tnMenuDescription.setText(description);

        if (!AppUtils.isEmpty(btn1Text)) {
            binding.btn1.setText(btn1Text);
            if (btn1OnClickListener != null) {
                binding.btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn1OnClickListener.onClick(v);
                        showHideSelectionView(false);
                    }
                });
            }
            AppUtils.setVisibility(binding.btn1, View.VISIBLE);
        } else {
            AppUtils.setVisibility(binding.btn1, View.GONE);
        }

        if (!AppUtils.isEmpty(btn2Text)) {
            binding.btn2.setText(btn2Text);
            if (btn2OnClickListener != null) {
                binding.btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn2OnClickListener.onClick(v);
                        showHideSelectionView(false);
                    }
                });
            }
            AppUtils.setVisibility(binding.btn2, View.VISIBLE);
        } else {
            AppUtils.setVisibility(binding.btn2, View.GONE);
        }
        showHideSelectionView(true);
    }

    private void showHideSelectionView(boolean isShow) {
        if (isShow) {
            AppUtils.setVisibility(binding.relSelectionView, View.VISIBLE);
        } else {
            AppUtils.setVisibility(binding.relSelectionView, View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.relSelectionView.getVisibility() == View.VISIBLE) {
            showHideSelectionView(false);
        } else {
            super.onBackPressed();
        }
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO,MODIFY_AUDIO_SETTINGS,MANAGE_DOCUMENTS, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }
}
