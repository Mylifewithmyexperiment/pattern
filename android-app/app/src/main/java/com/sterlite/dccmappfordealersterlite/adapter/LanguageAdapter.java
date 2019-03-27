package com.sterlite.dccmappfordealersterlite.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.ArrayList;

import com.sterlite.dccmappfordealersterlite.callback.OnRecyclerViewItemClickListener;
import com.sterlite.dccmappfordealersterlite.databinding.ItemSelectLanguageBinding;
import com.sterlite.dccmappfordealersterlite.model.Language;

public class LanguageAdapter extends BaseMainAdpter {
    private ArrayList<Language> languages = new ArrayList<>();
    int lastCheckedItem;


    public LanguageAdapter(Context context) {
        init(context, languages, new OnRecyclerViewItemClickListener() {
            @Override
            public void onClicked(Object bean, View view, int position, ViewType viewType) {

            }

            @Override
            public void onLastItemReached() {

            }
        });
    }

    public void addItems(ArrayList<Language> languageArrayList) {
        languages.clear();
        languages.addAll(languageArrayList);
        for (int i = 0; i < languages.size(); i++) {
            if (languages.get(i).isSelected()) {
                lastCheckedItem = i;
            }
        }
        notifyDataSetChanged();
    }

    public Language getSelectedItem() {
        for (Language language : languages) {
            if (language.isSelected())
                return language;
        }
        return null;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemSelectLanguageBinding itemBinding = ItemSelectLanguageBinding.inflate(layoutInflater, parent, false);
            viewHolder = new ViewHolder(itemBinding);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof LanguageAdapter.ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.binding.radio.setText(languages.get(position).getLanguageToDisplay());
            viewHolder.binding.radio.setChecked(languages.get(position).isSelected());
            viewHolder.binding.radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        int temp = lastCheckedItem;
                        lastCheckedItem = holder.getAdapterPosition();
                        languages.get(holder.getAdapterPosition()).setSelected(true);
                        languages.get(temp).setSelected(false);
                        notifyItemChanged(temp);
                    }
                }
            });

        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemSelectLanguageBinding binding;

        public ViewHolder(ItemSelectLanguageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
