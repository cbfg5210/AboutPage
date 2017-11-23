package com.ue.aboutpage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by hawk on 2017/11/23.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private CharSequence[] items;

    public DetailAdapter(CharSequence[] items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View layoutView = LayoutInflater.from(context).inflate(R.layout.item_detail, parent, false);
        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvDetailItem.setText(items[position].toString());
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDetailItem;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDetailItem = itemView.findViewById(R.id.tvDetailItem);
        }
    }
}
