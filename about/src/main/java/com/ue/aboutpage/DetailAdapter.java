package com.ue.aboutpage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hawk on 2017/11/23.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private List<DetailItem> items;

    public DetailAdapter(List<DetailItem> items) {
        this.items = items;
    }

    public void setItems(List<DetailItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View layoutView = LayoutInflater.from(context).inflate(R.layout.item_detail, parent, false);
        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DetailItem item = items.get(position);
        holder.tvDetItemTitle.setText(item.title);
        holder.tvDetItemContent.setText(item.content);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDetItemTitle;
        private TextView tvDetItemContent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDetItemTitle = itemView.findViewById(R.id.tvDetItemTitle);
            tvDetItemContent = itemView.findViewById(R.id.tvDetItemContent);
        }
    }
}
