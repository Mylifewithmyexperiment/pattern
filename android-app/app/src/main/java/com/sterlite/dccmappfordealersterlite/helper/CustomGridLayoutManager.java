package com.sterlite.dccmappfordealersterlite.helper;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by etech3 on 7/6/18.
 */

public class CustomGridLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public CustomGridLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }

    @Override
    public boolean canScrollHorizontally() {
        return isScrollEnabled && super.canScrollHorizontally();
    }
}