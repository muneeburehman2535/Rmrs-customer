package com.teletaleem.rmrs_customer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R

class DealsAdapter(requireContext: Context) : RecyclerView.Adapter<DealsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealsAdapter.ViewHolder {
        val contactView=LayoutInflater.from(parent.context).inflate(R.layout.layout_deals,parent,false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: DealsAdapter.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 7
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

    }
}