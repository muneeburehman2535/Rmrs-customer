package com.teletaleem.rmrs_customer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R

class RestaurantAdapter(requireContext: Context) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val contactView=LayoutInflater.from(parent.context).inflate(R.layout.layout_restaurant,parent,false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
       return 7
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

    }


}