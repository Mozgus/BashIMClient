package com.berryjam.bashim

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.berryjam.bashim.data.SourceOfQuotes
import kotlinx.android.synthetic.main.source_item.view.*

class SourceOfQuotesAdapter(list: MutableList<SourceOfQuotes>) : RecyclerView.Adapter<SourceOfQuotesAdapter.ViewHolder>() {
    private val mItems: MutableList<SourceOfQuotes> = list

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent!!.context)
        val view = layoutInflater.inflate(R.layout.source_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mItems[position]
        holder.tvTitle.text = item.desc
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle = view.tv_title!!
    }

}
