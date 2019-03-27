package com.sterlite.dccmappfordealersterlite.fragment.more;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.activity.Activation.ActivationActivity;
import com.sterlite.dccmappfordealersterlite.adapter.MoreAdapter;
import com.sterlite.dccmappfordealersterlite.base.BaseFragment;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.FragmentMoreBinding;
import com.sterlite.dccmappfordealersterlite.model.MoreItem;


public class MoreFragment extends BaseFragment implements MoreFragmentContract.View {

    public static MoreFragment newInstance() {
        return newInstance(null);
    }

    public static MoreFragment newInstance(Bundle bundle) {
        MoreFragment fragment = new MoreFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    private MoreAdapter moreAdapter;
    private FragmentMoreBinding binding;
    private MoreFragmentContract.Presenter<MoreFragmentContract.View> presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(activity, getClass().getSimpleName());
        presenter = new MoreFragmentPresenter<>();
        presenter.onAttach(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        activity.setTitle(R.string.menu_more);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);
        presenter.init();
        return binding.getRoot();
    }

    @Override
    public void onRetryClicked() {

    }

    @Override
    public void setUpView(boolean isReset) {
        if (isReset) {
            moreAdapter = null;
        }
        if (moreAdapter == null) {
            moreAdapter = new MoreAdapter(activity, new OnRecyclerViewItemClickListener<MoreItem>() {
                @Override
                public void onClicked(MoreItem bean, View view, int position, ViewType viewType) {
                    if (bean.getMoreId() == 1) {
                        startActivity(new Intent(activity, ActivationActivity.class).putExtra(ActivationActivity.ARG_IS_TRACK,true));
                    } else if (bean.getMoreId() == 2) {
                        activity.finishAffinity();
                    } else {
                        AppUtils.showUnderDevelopmentToast(activity);
                    }
                }

                @Override
                public void onLastItemReached() {

                }
            });
            binding.rvHomeItem.setAdapter(moreAdapter);
        } else {
            binding.rvHomeItem.setAdapter(moreAdapter);
        }

    }

    @Override
    public void loadDataToView(ArrayList<MoreItem> list) {
        moreAdapter.addItems(createHomeMenu());
    }


    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    // 20180830
    private ArrayList<MoreItem> createHomeMenu() {
        ArrayList<MoreItem> arrHomeItems = new ArrayList<>();
        arrHomeItems.add(getMoreItem(1, getString(R.string.lbl_more_order_history), R.drawable.ic_more_order_history));
        arrHomeItems.add(getMoreItem(2, getString(R.string.lbl_more_logout), R.drawable.ic_more_logout));
        return arrHomeItems;
    }

    private MoreItem getMoreItem(int id, String name, int resId) {
        MoreItem item1 = new MoreItem();
        item1.setMoreId(id);
        item1.setMoreName(name);
        item1.setMoreResId(resId);
        return item1;
    }
}
