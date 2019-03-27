package com.sterlite.dccmappfordealersterlite.fragment.dashBoard;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.adapter.DashboardAdapter;
import com.sterlite.dccmappfordealersterlite.base.BaseFragment;
import com.sterlite.dccmappfordealersterlite.databinding.FragmentDashBoardBinding;


public class DashBoardFragment extends BaseFragment implements DashBoardFragmentContract.View {

    public DashBoardFragment() {
        // Required empty public constructor
    }

    public static DashBoardFragment newInstance() {
        return newInstance(null);
    }

    public static DashBoardFragment newInstance(Bundle bundle) {
        DashBoardFragment fragment = new DashBoardFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    private FragmentDashBoardBinding binding;
    private DashboardAdapter dashboardAdapter;


    private DashBoardFragmentContract.Presenter<DashBoardFragmentContract.View> presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.sendAnalytics(activity, getClass().getSimpleName());
        presenter = new DashBoardFragmentPresenter<>();
        presenter.onAttach(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dash_board, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity.setTitle(R.string.menu_dashboard);
    }

    @Override
    public void onRetryClicked() {
        initReset();
    }

    private void initReset() {

    }

    @Override
    public void onReceiverNotification(Context context, String type, Intent intent) {

    }


    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}
