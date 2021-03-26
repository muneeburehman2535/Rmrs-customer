package com.teletaleem.rmrs_customer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R

class PastOrdersAdapter(context: Context): RecyclerView.Adapter<PastOrdersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.card_past_orders,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 3
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

    }
}