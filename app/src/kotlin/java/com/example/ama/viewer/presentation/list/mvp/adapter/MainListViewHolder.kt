package com.example.ama.viewer.presentation.list.mvp.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.recycler_view_item.view.*

class MainListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(itemText: String) {
        itemView.tv_item.text = itemText
    }
}