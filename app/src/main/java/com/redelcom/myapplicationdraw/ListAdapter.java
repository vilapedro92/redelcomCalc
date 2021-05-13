package com.redelcom.myapplicationdraw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Integer> mData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ListAdapter(List<Integer> itemList, DialogHistory context) {
        this.layoutInflater = LayoutInflater.from(context.getContext());
        this.context = context.getContext();
        this.mData = itemList;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.dialog_history, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<Integer> items) {
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView valueNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            valueNumber = itemView.findViewById(R.id.valueCardTextView);
        }

        void bindData(final Integer item) {
            valueNumber.setText(item.toString());
        }
    }

}
