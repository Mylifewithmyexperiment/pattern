package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.DateTimeUtils;
import com.sterlite.dccmappfordealersterlite.Utils.VectorDrawableUtils;
import com.sterlite.dccmappfordealersterlite.databinding.ItemTimelineBinding;
import com.sterlite.dccmappfordealersterlite.model.OrderStatus;
import com.sterlite.dccmappfordealersterlite.model.Track;

/**
 * Created by HP-HP on 05-12-2015.
 */
public class TrackingDetailAdapter extends RecyclerView.Adapter<TrackingDetailAdapter.TimeLineViewHolder> {

    private List<Track> mFeedList = new ArrayList<>();
    private Context mContext;

    public TrackingDetailAdapter(Context context) {
        mContext = context;
    }

    public void addItem(ArrayList<Track> mFeedList) {
        this.mFeedList.addAll(mFeedList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemTimelineBinding itemBinding = ItemTimelineBinding.inflate(layoutInflater, parent, false);
        return new TimeLineViewHolder(itemBinding, viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {

        Track Track = mFeedList.get(position);
        if (Track.getStatus() == OrderStatus.INACTIVE) {
            holder.binding.timeMarker.setStartLine(R.color.color_inactive, getItemViewType(position));
            holder.binding.timeMarker.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_inactive, R.color.color_inactive));
            holder.binding.timeMarker.setEndLine(R.color.color_inactive, getItemViewType(position));
        } else if (Track.getStatus() == OrderStatus.ACTIVE) {
            holder.binding.timeMarker.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_default_status, R.color.color_inactive));
            holder.binding.timeMarker.setEndLine(R.color.color_inactive, getItemViewType(position));
        } else {
            TypedValue typedValue =  new TypedValue();
            mContext.getTheme().resolveAttribute(R.attr.colorMain,typedValue,true);

            holder.binding.timeMarker.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_correct, typedValue.resourceId));
        }

                if (!AppUtils.isEmpty(Track.getmDescription())) {
            holder.binding.textDescription.setText(Track.getmDescription());
            AppUtils.setVisibility(holder.binding.textDescription, View.VISIBLE);
        } else {
            AppUtils.setVisibility(holder.binding.textDescription, View.GONE);
        }

        if (!AppUtils.isEmpty(Track.getDate())) {
            holder.binding.textTimelineDate.setVisibility(View.VISIBLE);
            holder.binding.textTimelineDate.setText(DateTimeUtils.parseDateTime(Track.getDate(), "yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy"));
        } else
            holder.binding.textTimelineDate.setVisibility(View.GONE);

        holder.binding.textTimelineTitle.setText(Track.getMessage());
    }

    @Override
    public int getItemCount() {
        return (mFeedList != null ? mFeedList.size() : 0);
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {

        ItemTimelineBinding binding;

        public TimeLineViewHolder(ItemTimelineBinding binding, int viewType) {
            super(binding.getRoot());
            binding.timeMarker.initLine(viewType);
            this.binding = binding;
        }
    }

}
