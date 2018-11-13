package com.example.ama.viewer.presentation.list.mvp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ama.viewer.R

class MainListAdapter : RecyclerView.Adapter<MainListViewHolder>() {
    var items = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MainListViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_view_item, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(viewHolder: MainListViewHolder, id: Int) {
        viewHolder.bind(items[id])
    }

    fun setItems(data: List<String>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    fun appendItemToList(item: String) {
        items.add(item)
        notifyItemInserted(items.lastIndex)
    }
}