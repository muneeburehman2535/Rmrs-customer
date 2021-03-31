package com.teletaleem.rmrs_customer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.filtersearch.FilterSearch


class FilterCategoryAdapter(val requireContext: Context, val categoryList: ArrayList<FilterSearch>):
    RecyclerView.Adapter<FilterCategoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.layout_filter,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtCategoryName.text= categoryList[position].name
    }

    override fun getItemCount(): Int {
       return categoryList.size
    }

    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {

        val txtCategoryName=itemView.findViewById<TextView>(R.id.txt_category_lf)

    }
}