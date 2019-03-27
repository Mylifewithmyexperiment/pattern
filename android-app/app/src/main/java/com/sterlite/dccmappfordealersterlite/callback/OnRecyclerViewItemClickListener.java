package com.sterlite.dccmappfordealersterlite.callback;

import android.view.View;

/**
 * Created by etech3 on 14/5/18.
 */

public interface OnRecyclerViewItemClickListener<V> {
    public static enum ViewType {
        View,
        Delete,
        Add,
        Remove,
        Active,
        Track
    }

    void onClicked(V bean, View view, int position, ViewType viewType);

    void onLastItemReached();
}
