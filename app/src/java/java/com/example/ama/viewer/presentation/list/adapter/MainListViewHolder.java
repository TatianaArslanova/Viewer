package com.example.ama.viewer.presentation.list.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ama.viewer.R;

public class MainListViewHolder extends RecyclerView.ViewHolder {
    MainListViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    void bind(String itemText) {
        ((TextView) itemView.findViewById(R.id.tv_item)).setText(itemText);
    }
}
