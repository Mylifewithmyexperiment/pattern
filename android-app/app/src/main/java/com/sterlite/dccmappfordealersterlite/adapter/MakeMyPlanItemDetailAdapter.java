package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.R;
import com.sterlite.dccmappfordealersterlite.Utils.AppUtils;
import com.sterlite.dccmappfordealersterlite.Utils.Constants;
import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ItemMakeMyPlanItemSeekbarDetailLayoutBinding;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel.MMPBase;
import com.sterlite.dccmappfordealersterlite.model.MakeMyPlanModel.MakeMyPlanItems;

/**
 * Created by etech3 on 9/1/18.
 */
public class MakeMyPlanItemDetailAdapter extends BaseMainAdpter {


    private ArrayList<MMPBase> arrMakeMyPlanItems = new ArrayList<>();
    private boolean isOverLimit = false;
    OnSeekBarValueChangeListener onSeekBarValueChangeListener;
    private int lastPosition = 0;
    private boolean isSubVisible = false;

    public MakeMyPlanItemDetailAdapter(Context context, OnRecyclerViewItemClickListener<MMPBase> onRecycleItemClickListener) {
        init(context, arrMakeMyPlanItems, onRecycleItemClickListener);
    }

    public void setOnSeekBarValueChangeListener(OnSeekBarValueChangeListener onSeekBarValueChangeListener) {
        this.onSeekBarValueChangeListener = onSeekBarValueChangeListener;
    }

    public void addItems(ArrayList<MMPBase> arrMakeMyPlanItems) {
        this.arrMakeMyPlanItems.addAll(arrMakeMyPlanItems);
        notifyDataSetChanged();
    }

    public void setOverLimit(boolean overLimit) {
        isOverLimit = overLimit;
        notifyDataSetChanged();
    }

    public MMPBase get(int position) {
        return arrMakeMyPlanItems.get(position);
    }

    public ArrayList<MMPBase> getAll() {
        return arrMakeMyPlanItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemMakeMyPlanItemSeekbarDetailLayoutBinding itemBinding = ItemMakeMyPlanItemSeekbarDetailLayoutBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewHolder(itemBinding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            final MMPBase item = arrMakeMyPlanItems.get(position);
            if (item.isSubItem() && !item.isSetSubVisible()) {
                ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(0, 0);
                viewHolder.itemView.setLayoutParams(layoutParam);
                return;
            } else {
                ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                viewHolder.itemView.setLayoutParams(layoutParam);
            }
            String value = item.getItemTitle();

            viewHolder.binding.tvText.setText(value);

            if (item.getAddOnCategory().equals(Constants.MMP_ADDON_CATEGORY_DATA)) {
                int totalPrice = 0;
                if (item.getCurrentValuePosition() >= 0) {
                    totalPrice = item.getArrPlan().get(item.getCurrentValuePosition()).getOneTimeProductPrice().getValue();
                }
                if (get(position + 1).getCurrentValuePosition() >= 0) {
                    totalPrice = totalPrice + get(position + 1).getArrPlan().get(get(position + 1).getCurrentValuePosition()).getOneTimeProductPrice().getValue();
                }
                String currentStr;
                if (totalPrice >= 0) {
                    currentStr = context.getString(R.string.lbl_price) + totalPrice;
                } else {
                    currentStr = context.getString(R.string.lbl_price) + context.getString(R.string.lbl_zero);
                }
                viewHolder.binding.tvCurrentUsage.setText(currentStr);
            } else {
                String currentStr;
                if (item.getCurrentValuePosition() >= 0) {
                    currentStr = context.getString(R.string.lbl_price) + item.getArrPlan().get(item.getCurrentValuePosition()).getOneTimeProductPrice().getValue();
                } else {
                    currentStr = context.getString(R.string.lbl_price) + context.getString(R.string.lbl_zero);
                }
                viewHolder.binding.tvCurrentUsage.setText(currentStr);
            }

            viewHolder.binding.sbBarData.setMin(0);
            viewHolder.binding.sbBarData.setMax(item.getArrPlan().size());
            viewHolder.binding.sbBarData.setProgress(item.getCurrentValuePosition() + 1);
            ArrayList<String> arrString = new ArrayList<>();
            arrString.add("0");
            for (MakeMyPlanItems myPlanItems : item.getArrPlan()) {
                String temp = myPlanItems.getName().split("_")[0];
                temp = temp.replace(" ", "");
                arrString.add(temp.length() > 6 ? temp.substring(0, 6) : temp);
            }
            viewHolder.binding.sbBarData.customTickTexts(convertToArray(arrString));
            viewHolder.binding.sbBarData.setIndicatorTextFormat("${TICK_TEXT}");
            viewHolder.binding.sbBarData.setTickCount(arrString.size());
            viewHolder.binding.sbBarData.setOnSeekChangeListener(new OnSeekChangeListener() {
                @Override
                public void onSeeking(SeekParams seekParams) {

                }

                @Override
                public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
                    get(position).setTempProgress(seekBar.getProgress());
                }

                @Override
                public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                    lastPosition = position;

                    if (isOverLimit && get(position).getTempProgress() < seekBar.getProgress()) {
                        viewHolder.binding.sbBarData.setProgress(get(position).getTempProgress());
                        AppUtils.showToast(context, context.getString(R.string.msg_make_my_plan_detail_plan_value_over));
                        return;
                    }
                    setCurrentPosition(position, seekBar.getProgress());
                    MMPBase temp = get(position);
                    String currentStr;
                    if (temp.getCurrentValuePosition() >= 0) {
                        currentStr = context.getString(R.string.lbl_price) + temp.getArrPlan().get(temp.getCurrentValuePosition()).getOneTimeProductPrice().getValue();
                    } else {
                        currentStr = context.getString(R.string.lbl_price) + context.getString(R.string.lbl_zero);
                    }
                    viewHolder.binding.tvCurrentUsage.setText(currentStr);

                    if (onSeekBarValueChangeListener != null)
                        onSeekBarValueChangeListener.onValueUpdated();

                    if (temp.getAddOnCategory().equals(Constants.MMP_ADDON_CATEGORY_DATA)) {
                        if (temp.getCurrentValuePosition() == temp.getArrPlan().size() - 1) {
                            resetSubItem(Constants.MMP_ADDON_CATEGORY_DATA_SUB);
                            if (onSeekBarValueChangeListener != null)
                                onSeekBarValueChangeListener.onDataAddAtLastValue();
                        }
                        else {
                            resetSubItemParentPosition(Constants.MMP_ADDON_CATEGORY_DATA_SUB);
                        }
                    }
                }
            });


            if (item.getAddOnCategory().equals(Constants.MMP_ADDON_CATEGORY_DATA)) {
                AppUtils.setVisibility(viewHolder.binding.btnToggleSubView, View.VISIBLE);
            } else {
                AppUtils.setVisibility(viewHolder.binding.btnToggleSubView, View.GONE);
            }


            viewHolder.binding.btnToggleSubView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleSubItemVisibility(Constants.MMP_ADDON_CATEGORY_DATA_SUB);
                    if (onSeekBarValueChangeListener != null)
                        onSeekBarValueChangeListener.onDataAddAtLastValue();
                }
            });

            if ((isOverLimit && !item.isSet())
                    || (item.getAddOnCategory().equals(Constants.MMP_ADDON_CATEGORY_DATA_SUB) && item.isParentAtLast())) {
                viewHolder.binding.sbBarData.setEnabled(false);
            } else {
                viewHolder.binding.sbBarData.setEnabled(true);
            }

            if (item.isSubItem()) {
//                viewHolder.binding.relParent.setBackgroundColor(ContextCompat.getColor(context, R.color.app_mmp_cell_light));
                AppUtils.setVisibility(viewHolder.binding.tvCurrentUsage, View.GONE);
                int padding = AppUtils.dp2px(context, 12);
                viewHolder.binding.relParent.setPadding(padding, -padding, 0, 0);
            } else {
                AppUtils.setVisibility(viewHolder.binding.tvCurrentUsage, View.VISIBLE);
                viewHolder.binding.relParent.setPadding(0, 0, 0, 0);
//                viewHolder.binding.relParent.setBackgroundColor(ContextCompat.getColor(context, R.color.app_mmp_cell_default));
            }
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMakeMyPlanItemSeekbarDetailLayoutBinding binding;

        public ViewHolder(ItemMakeMyPlanItemSeekbarDetailLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnSeekBarValueChangeListener {
        void onValueUpdated();

        void onDataAddAtLastValue();
    }

    private void setCurrentPosition(int position, int currentThumb) {
        get(position).setCurrentValuePosition(currentThumb - 1);
        if (currentThumb == 0) {
            get(position).setSet(false);
        } else {
            get(position).setSet(true);
        }
    }

    private String[] convertToArray(ArrayList<String> strings) {
        String[] arr = new String[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            arr[i] = strings.get(i);
        }
        return arr;
    }

    public boolean checkAndSetRemaingValue(float totalValue) {
        float remiangValue = totalValue - calculateRemainingValue(true);
        boolean isChanged = false;
        MMPBase item = get(lastPosition);
        int index = -1;
        if (remiangValue > 0) {
            for (int i = 0; i < item.getArrPlan().size(); i++) {
                MakeMyPlanItems temp = item.getArrPlan().get(i);
                if (i == 0 && temp.getOneTimeProductPrice().getValue() > remiangValue) {
                    break;
                }
                if (temp.getOneTimeProductPrice().getValue() == remiangValue) {
                    index = i;
                    isChanged = true;
                    break;
                } else if (temp.getOneTimeProductPrice().getValue() > remiangValue) {
                    index = i - 1;
                    isChanged = true;
                    break;
                }
            }
        }
        if (index >= 0 && item.getArrPlan().get(index).getOneTimeProductPrice().getValue() < remiangValue) {
            isChanged = false;
        }
        setCurrentPosition(lastPosition, index + 1);
        notifyDataSetChanged();
        return isChanged;
    }

    public float calculateRemainingValue(boolean isSkipCurrent) {
        float temp = 0;
        for (int i = 0; i < arrMakeMyPlanItems.size(); i++) {
            if (isSkipCurrent && i == lastPosition) continue;
            MMPBase makeMyPlanItem = get(i);
//            if (makeMyPlanItem.isSubItem() && !makeMyPlanItem.isSetSubVisible()) {
//                continue;
//            }
            if (makeMyPlanItem.getCurrentValuePosition() >= 0 && makeMyPlanItem.getArrPlan() != null && makeMyPlanItem.getArrPlan().size() > 0) {
                temp = temp + makeMyPlanItem.getArrPlan().get(makeMyPlanItem.getCurrentValuePosition()).getOneTimeProductPrice().getValue();
            }
        }
        return temp;
    }


    private void resetSubItem(String... arrCategory) {
        if (arrCategory == null) return;

        if (arrMakeMyPlanItems != null && arrMakeMyPlanItems.size() > 0) {
            for (String category : arrCategory) {
                for (MMPBase item : arrMakeMyPlanItems) {
                    if (item.getAddOnCategory().equals(category)) {
                        item.setCurrentValuePosition(-1);
                        item.setSet(false);
                        item.setParentAtLast(true);
                        break;
                    }
                }
            }
        }
    }

    private void resetSubItemParentPosition(String... arrCategory) {
        if (arrCategory == null) return;

        if (arrMakeMyPlanItems != null && arrMakeMyPlanItems.size() > 0) {
            for (String category : arrCategory) {
                for (MMPBase item : arrMakeMyPlanItems) {
                    if (item.getAddOnCategory().equals(category)) {
                        item.setParentAtLast(false);
                        break;
                    }
                }
            }
        }
    }

    public void toggleSubItemVisibility(String... arrCategory) {
        if (arrCategory == null) return;

        boolean isChange = false;
        if (arrMakeMyPlanItems != null && arrMakeMyPlanItems.size() > 0) {
            for (String category : arrCategory) {
                for (MMPBase item : arrMakeMyPlanItems) {
                    if (item.getAddOnCategory().equals(category)) {
                        item.setSetSubVisible(!item.isSetSubVisible());
                        isChange = true;
                        break;
                    }
                }
            }
        }
        if (isChange)
            notifyDataSetChanged();
    }
}
