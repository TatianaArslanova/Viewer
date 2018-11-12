package com.example.ama.viewer.presentation.list.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ama.viewer.R;

import java.util.ArrayList;
import java.util.List;

public class MainListAdapter extends RecyclerView.Adapter<MainListViewHolder> {

    private List<String> items = new ArrayList<>();

    @NonNull
    @Override
    public MainListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_item, viewGroup, false);
        return new MainListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainListViewHolder mainListViewHolder, int i) {
        mainListViewHolder.bind(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    public void appendItem(String item) {
        items.add(item);
    }
}
